package com.example.s02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NouaActivitate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_noua_activitate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null) return;
        String message = extras.getString("newMsg");
        int val = extras.getInt("newVal");
        if (val != 0 && message != null) {
            Toast.makeText(this, String.format("%s - %d", message, val), Toast.LENGTH_SHORT).show();
        }

    }

    public void openActivity(View view) {
        Intent it = new Intent(this, ActivitateRezultat.class);
        it.putExtra("text", "text");
        it.putExtra("val1", 5);
        it.putExtra("val2", 3);
        startActivity(it);
    }
}