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

public class MainActivity extends AppCompatActivity {

    private TextView tvRezultat;
    private Button btnAdaugaBmw;

    // Launcher pentru primirea obiectului BMW returnat din AddBmwActivity (Punctul 4)
    private final ActivityResultLauncher<Intent> addBmwLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    if (bundle != null) {
                        BMW bmwPrimit = (BMW) bundle.getSerializable("BMW_OBJECT");
                        if (bmwPrimit != null) {
                            // 1. Afișare în TextView
                            tvRezultat.setText(bmwPrimit.toString());

                            // 2. Afișare prin Toast
                            Toast.makeText(MainActivity.this,
                                    "Obiect creat cu succes!\n" + bmwPrimit.getNume(),
                                    Toast.LENGTH_LONG).show();
                        }
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

        // Conectare elemente din XML
        tvRezultat = findViewById(R.id.tvRezultat);
        btnAdaugaBmw = findViewById(R.id.btnAdaugaBmw);

        // Deschidere activitate de adăugare la click pe buton
        btnAdaugaBmw.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBmwActivity.class);
            addBmwLauncher.launch(intent);
        });
    }
}