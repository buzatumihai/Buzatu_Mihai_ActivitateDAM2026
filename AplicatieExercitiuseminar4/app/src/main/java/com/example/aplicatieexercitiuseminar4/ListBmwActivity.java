package com.example.aplicatieexercitiuseminar4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListBmwActivity extends AppCompatActivity {

    private ListView lvBmw;
    private TextView tvEmpty;
    private ArrayList<BMW> listaBmw;
    private ArrayAdapter<BMW> adapter;

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
            listaBmw = (ArrayList<BMW>) intent.getSerializableExtra("LISTA_BMW");
        }
        if (listaBmw == null) {
            listaBmw = new ArrayList<>();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaBmw);
        lvBmw.setAdapter(adapter);

        lvBmw.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < listaBmw.size()) {
                BMW bmwSelectat = listaBmw.get(position);
                Toast.makeText(getApplicationContext(),
                        "Ați selectat:\n" + bmwSelectat,
                        Toast.LENGTH_LONG).show();
            }
        });

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
