package com.example.s02;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivitateRezultat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_activitate_rezultat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();

        Toast.makeText(this, String.format("%s - %d %d",
                extras.getString("text"),
                extras.getInt("val1"),
                extras.getInt("val2")
        ), Toast.LENGTH_SHORT).show();

    }

    public void sendBack(View view) {
        Intent it = new Intent(this, NouaActivitate.class);
        it.putExtra("newMsg", "Alt mesaj");
        int val1 = getIntent().getExtras().getInt("val1");
        int val2 = getIntent().getExtras().getInt("val2");

        it.putExtra("newVal", val1 + val2);

        startActivity(it);
    }
}