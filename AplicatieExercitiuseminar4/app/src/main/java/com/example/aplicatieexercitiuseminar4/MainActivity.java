package com.example.aplicatieexercitiuseminar4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    private TextView tvRezultat;
    private Button btnAdaugaBmw;
    private Button btnAfiseazaLista;

    private ArrayList<BMW> listaBmw = new ArrayList<>();

    private final ActivityResultLauncher<Intent> addBmwLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    if (bundle != null) {
                        BMW bmwPrimit = (BMW) bundle.getSerializable("BMW_OBJECT");
                        if (bmwPrimit != null) {
                            // Adăugare obiect în listă (Cerința 1)
                            listaBmw.add(bmwPrimit);

                            // Afișare în TextView
                            tvRezultat.setText(bmwPrimit.toString());

                            // Afișare prin Toast
                            Toast.makeText(MainActivity.this,
                                    "Obiect adăugat în listă!\nTotal obiecte: " + listaBmw.size(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
    );

    private final ActivityResultLauncher<Intent> listBmwLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    //noinspection unchecked
                    ArrayList<BMW> updatedList = (ArrayList<BMW>) result.getData().getSerializableExtra("LISTA_BMW");
                    if (updatedList != null) {
                        listaBmw = updatedList;
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvRezultat = findViewById(R.id.tvRezultat);
        btnAdaugaBmw = findViewById(R.id.btnAdaugaBmw);
        btnAfiseazaLista = findViewById(R.id.btnAfiseazaLista);


        btnAdaugaBmw.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBmwActivity.class);
            addBmwLauncher.launch(intent);
        });

        btnAfiseazaLista.setOnClickListener(v -> {
            if (listaBmw.isEmpty()) {
                Toast.makeText(MainActivity.this,
                        "Lista este goală! Adăugați mai întâi un obiect BMW.",
                        Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(MainActivity.this, ListBmwActivity.class);
            intent.putExtra("LISTA_BMW", listaBmw);
            listBmwLauncher.launch(intent);
        });
    }
}
