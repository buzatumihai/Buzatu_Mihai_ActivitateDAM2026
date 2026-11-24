package com.example.seminar09;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VremeActivity extends AppCompatActivity {

    private static final String API_KEY = "";

    private EditText etOras;
    private Button btnCauta;
    private TextView tvRezultat;
    private Spinner spinnerZile;

    // Executor -> ruleaza codul pe un fir secundar
    private final Executor executor = Executors.newSingleThreadExecutor();
    // Handler -> ne intoarce pe firul principal (UI)
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vreme);

        etOras = findViewById(R.id.etOras);
        btnCauta = findViewById(R.id.btnCauta);
        tvRezultat = findViewById(R.id.tvRezultat);
        spinnerZile = findViewById(R.id.spinnerZile);

        // valorile din Spinner: {1 zi, 5 zile}
        String[] optiuni = {"1 zi", "5 zile"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, optiuni);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZile.setAdapter(adapter);

        btnCauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oras = etOras.getText().toString().trim();
                if (oras.isEmpty()) {
                    tvRezultat.setText("Introduceti numele unui oras");
                    return;
                }
                // pozitia 0 = "1 zi" -> metoda "1day"; pozitia 1 = "5 zile" -> metoda "5day"
                String metoda = spinnerZile.getSelectedItemPosition() == 0 ? "1day" : "5day";
                tvRezultat.setText("Se cauta...");
                cautaOras(oras, metoda);
            }
        });
    }

    private void cautaOras(String oras, String metoda) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String link = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey="
                        + API_KEY + "&q=" + oras;
                try {
                    URL url = new URL(link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
                    if (conn.getResponseCode() != 200) {
                        throw new IOException("City Search a raspuns cu codul HTTP " + conn.getResponseCode());
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder builder = new StringBuilder("");
                    String linie;
                    while ((linie = reader.readLine()) != null) {
                        builder.append(linie);
                    }
                    reader.close();
                    conn.disconnect();

                    // parsare JSON -> luam campul "Key" al primului oras
                    JSONArray array = new JSONArray(builder.toString());
                    String rezultat;
                    if (array.length() > 0) {
                        JSONObject obiect = array.getJSONObject(0);
                        String numeOras = obiect.getString("LocalizedName");
                        String cheieOras = obiect.getString("Key");

                        // al doilea apel: Daily Forecast cu codul orasului
                        // singurul lucru care se schimba e numele metodei: "1day" sau "5day"
                        String linkVreme = "https://dataservice.accuweather.com/forecasts/v1/daily/"
                                + metoda + "/" + cheieOras + "?apikey=" + API_KEY + "&metric=true";
                        URL urlVreme = new URL(linkVreme);
                        HttpURLConnection connVreme = (HttpURLConnection) urlVreme.openConnection();
                        connVreme.setRequestProperty("Authorization", "Bearer " + API_KEY);
                        if (connVreme.getResponseCode() != 200) {
                            throw new IOException("Forecast a raspuns cu codul HTTP " + connVreme.getResponseCode());
                        }
                        BufferedReader readerVreme = new BufferedReader(new InputStreamReader(connVreme.getInputStream()));
                        StringBuilder builderVreme = new StringBuilder("");
                        while ((linie = readerVreme.readLine()) != null) {
                            builderVreme.append(linie);
                        }
                        readerVreme.close();
                        connVreme.disconnect();

                        // parsare JSON: parcurgem TOATE elementele din DailyForecasts
                        // (1 element pentru "1day", 5 elemente pentru "5day")
                        JSONObject raspunsVreme = new JSONObject(builderVreme.toString());
                        JSONArray zile = raspunsVreme.getJSONArray("DailyForecasts");

                        StringBuilder sb = new StringBuilder();
                        sb.append("Codul orasului ").append(numeOras).append(" este: ").append(cheieOras).append("\n");
                        for (int i = 0; i < zile.length(); i++) {
                            JSONObject zi = zile.getJSONObject(i);
                            String data = zi.getString("Date").substring(0, 10); // ex: 2026-11-24
                            JSONObject temperatura = zi.getJSONObject("Temperature");
                            double minim = temperatura.getJSONObject("Minimum").getDouble("Value");
                            double maxim = temperatura.getJSONObject("Maximum").getDouble("Value");
                            sb.append("\n").append(data)
                              .append("\n   Minima: ").append(minim).append(" °C")
                              .append("\n   Maxima: ").append(maxim).append(" °C\n");
                        }
                        rezultat = sb.toString();
                    } else {
                        rezultat = "Orasul nu a fost gasit";
                    }

                    // ne intoarcem pe firul principal ca sa modificam TextView-ul
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvRezultat.setText(rezultat);
                        }
                    });
                } catch (Exception e) {
                    Log.e("VremeActivity", "Eroare la apel AccuWeather", e);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvRezultat.setText("Eroare: " + e.getMessage());
                        }
                    });
                }
            }
        });
    }
}
