package com.example.aplicatieexercitiuseminar4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImaginiActivity extends AppCompatActivity {

    private ListView lvImagini;
    private List<BMW_imagini> lista = new ArrayList<>();
    private ImaginiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagini);

        lvImagini = findViewById(R.id.imagini);

        // 1. Link-urile pentru descărcarea pozelor (imaginilor)
        List<String> linkuriImagini = new ArrayList<>();
        linkuriImagini.add("https://bmw.scene7.com/is/image/BMW/g09_more-info-bmw-individual-visualizer_fb_ssv?qlt=80&wid=1024&fmt=webp");
        linkuriImagini.add("https://www.bmw.ro/content/dam/bmw/common/all-models/x-series/bmw-x5/2026/images/g65_ice_opengraph-image.jpg");
        linkuriImagini.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT7UKAdD1WQ7-XxSh-wKyFza99_QkID_p_Fwm0Pv9Ovk8NFb1Xk5RU1Fbg&s=10");
        linkuriImagini.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIpnHb4-2DgMN6PvDjfK-rtPjEF0CHHKOIbISiNUR0on3XW4Nsv9pHgCA&s=10");
        linkuriImagini.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhshczJ40B_k54s6kVtjcLKmcfRfqwGOmGg7CQwRZKEg&s=10");

        // 2. Link-urile website-urilor oficiale pentru fiecare model (ce se deschid la click)
        List<String> linkuriWebsite = new ArrayList<>();
        linkuriWebsite.add("https://www.bmw.ro/ro/all-models/m-series/bmw-xm/2022/bmw-xm-overview.html");
        linkuriWebsite.add("https://www.bmw.ro/ro/all-models/x-series/X5/2023/bmw-x5-overview.html");
        linkuriWebsite.add("https://www.bmw.ro/ro/all-models/m-series/m3-sedan/2020/bmw-3-series-sedan-m-automobiles-overview.html");
        linkuriWebsite.add("https://www.bmw.ro/ro/all-models/m-series/m5-sedan/2024/bmw-m5-sedan-overview.html");
        linkuriWebsite.add("https://www.bmw.ro/ro/topics/offers-and-services/bmw-i-service/i8-service.html");

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        List<Bitmap> imagini = new ArrayList<>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (String link : linkuriImagini) {
                    HttpURLConnection con = null;
                    try {
                        URL url = new URL(link);
                        con = (HttpURLConnection) url.openConnection();
                        InputStream is = con.getInputStream();
                        imagini.add(BitmapFactory.decodeStream(is));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            con.disconnect();
                        }
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lista = new ArrayList<>();
                        String[] modele = {"BMW XM", "BMW X5", "BMW M3", "BMW M5", "BMW i8"};

                        for (int i = 0; i < imagini.size(); i++) {
                            String numeModel = (i < modele.length) ? modele[i] : "BMW Model " + (i + 1);
                            String webUrl = (i < linkuriWebsite.size()) ? linkuriWebsite.get(i) : "https://www.bmw.ro";
                            lista.add(new BMW_imagini(numeModel, imagini.get(i), webUrl));
                        }

                        adapter = new ImaginiAdapter(lista, getApplicationContext(), R.layout.item_imagine);
                        lvImagini.setAdapter(adapter);
                    }
                });
            }
        });

        // Event de ItemClick pe ListView -> Deschide WebViewActivity cu link-ul website-ului oficial
        ListView lv = findViewById(R.id.imagini);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getApplicationContext(), WebViewActivity.class);
                it.putExtra("link", lista.get(i).getLink());
                startActivity(it);
            }
        });
    }
}
