package com.example.aplicatieexercitiuseminar4;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // 1. Obținerea Intent-ului și a link-ului trimis din ImaginiActivity
        Intent it = getIntent();
        String link = it.getStringExtra("link");

        // 2. Identificarea WebView-ului din layout (activity_web_view.xml)
        WebView wv = findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient()); // Asigură deschiderea paginii în cadrul aplicației

        // 3. Setarea URL-ului pe care WebView-ul să îl deschidă
        if (link != null) {
            wv.loadUrl(link);
        }
    }
}
