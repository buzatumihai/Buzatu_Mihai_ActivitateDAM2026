package com.example.aplicatieexercitiuseminar4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListBmwActivity extends AppCompatActivity {

    private ListView lvBmw;
    private TextView tvEmpty;
    private ArrayList<BMW> listaBmw;
    private BMWAdapter adapter;

    // Indexul elementului selectat pentru modificare
    private int idModificat = -1;

    // Launcher pentru modificarea unui obiect BMW în AddBmwActivity
    private final ActivityResultLauncher<Intent> editBmwLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    if (bundle != null && idModificat != -1) {
                        BMW bmwModificat = (BMW) bundle.getSerializable("BMW_OBJECT");
                        if (bmwModificat != null && idModificat < listaBmw.size()) {
                            // 1. Modificăm obiectul în cadrul listei la poziția respectivă
                            listaBmw.set(idModificat, bmwModificat);

                            // 2. Notificăm adapterul că au fost realizate modificări în sursa de date
                            adapter.notifyDataSetChanged();

                            Toast.makeText(getApplicationContext(),
                                    "Obiect modificat cu succes: " + bmwModificat.getNume(),
                                    Toast.LENGTH_SHORT).show();

                            actualizeazaRezultat();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_bmw);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainList), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvBmw = findViewById(R.id.lvBmw);
        tvEmpty = findViewById(R.id.tvEmpty);

        lvBmw.setEmptyView(tvEmpty);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("LISTA_BMW")) {
            //noinspection unchecked
            listaBmw = (ArrayList<BMW>) intent.getSerializableExtra("LISTA_BMW");
        }
        if (listaBmw == null) {
            listaBmw = new ArrayList<>();
        }

        // Folosim BMWAdapter custom cu layout_nou.xml
        adapter = new BMWAdapter(listaBmw, this, R.layout.layout_nou);
        lvBmw.setAdapter(adapter);

        // Click pe un element -> deschidere activitate de modificare/adaugare cu obiectul selectat
        lvBmw.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < listaBmw.size()) {
                idModificat = position;
                Intent intentModifica = new Intent(ListBmwActivity.this, AddBmwActivity.class);
                intentModifica.putExtra("BMW_OBJECT", listaBmw.get(position));
                editBmwLauncher.launch(intentModifica);
            }
        });

        // Long Click -> ștergere din lista de obiecte și din ListView
        lvBmw.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < listaBmw.size()) {
                BMW bmwSters = listaBmw.remove(position);

                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(),
                        "Obiect șters: " + bmwSters.getNume(),
                        Toast.LENGTH_SHORT).show();

                actualizeazaRezultat();
            }
            return true;
        });

        actualizeazaRezultat();
    }

    private void actualizeazaRezultat() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("LISTA_BMW", listaBmw);
        setResult(RESULT_OK, resultIntent);
    }
}
