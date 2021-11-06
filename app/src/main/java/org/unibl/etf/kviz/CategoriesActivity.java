package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.unibl.etf.kviz.helpers.Country;
import org.unibl.etf.kviz.helpers.DBHelper;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {
    public static final int NUMBER_OF_QUESTIONS = 5;
    public static final String PREFS_SCORE = "SCORE";

    TextView label;
    Button capitals;
    Button flags;
    Button neighbors;
    Button sights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }

    @Override
    protected void onStart() {
        super.onStart();
        capitals = findViewById(R.id.btn_capitals);
        capitals.setOnClickListener(view->{
            Intent intent = new Intent(this, CapitalsActivity.class);
            startActivity(intent);
        });
    }
}