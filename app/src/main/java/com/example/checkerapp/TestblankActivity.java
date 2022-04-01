package com.example.checkerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TestblankActivity extends AppCompatActivity {
    TextView textView1;
    String stringtoshowintv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testblank);
        textView1 = findViewById(R.id.onlytextview);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            stringtoshowintv = extras.getString("stringtoshow");

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView1.setText(stringtoshowintv);

    }
}