package com.example.aplicatieexercitiuseminar4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class AddBmwActivity extends AppCompatActivity {

    private EditText etNume, etKg, etAn;
    private SwitchCompat switchSport;
    private RadioGroup rgCombustibil;
    private CheckBox cbGarantie;
    private Spinner spCuloare;
    private RatingBar rbStare;
    private Button btnSalveaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bmw);
        etNume = findViewById(R.id.etNume);
        etKg = findViewById(R.id.etKg);
        etAn = findViewById(R.id.etAn);
        switchSport = findViewById(R.id.switchSport);
        rgCombustibil = findViewById(R.id.rgCombustibil);
        cbGarantie = findViewById(R.id.cbGarantie);
        spCuloare = findViewById(R.id.spCuloare);
        rbStare = findViewById(R.id.rbStare);
        btnSalveaza = findViewById(R.id.btnSalveaza);


        String[] culori = {"Negru", "Alb", "Albastru", "Gri"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, culori);
        spCuloare.setAdapter(adapter);


        btnSalveaza.setOnClickListener(v -> {
            String nume = etNume.getText().toString().trim();
            String kgStr = etKg.getText().toString().trim();
            String anStr = etAn.getText().toString().trim();


            if (nume.isEmpty() || kgStr.isEmpty() || anStr.isEmpty()) {
                Toast.makeText(this, "Completați toate câmpurile text!", Toast.LENGTH_SHORT).show();
                return;
            }

            int kg = Integer.parseInt(kgStr);
            int an = Integer.parseInt(anStr);
            boolean esteSport = switchSport.isChecked();


            int selectedRadioId = rgCombustibil.getCheckedRadioButtonId();
            RadioButton rbSelected = findViewById(selectedRadioId);
            String combustibil = (rbSelected != null) ? rbSelected.getText().toString() : "Benzina";


            BMW bmwNou = new BMW(nume, kg, an, esteSport, combustibil);


            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("BMW_OBJECT", bmwNou);
            resultIntent.putExtras(bundle);

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
