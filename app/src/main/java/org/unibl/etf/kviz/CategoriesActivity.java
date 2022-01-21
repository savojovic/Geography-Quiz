package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        finish=findViewById(R.id.finishBtn);
        finish.setOnClickListener(v->{
            SharedPreferences preferences = getSharedPreferences(UsernameActivity.PREFS_USERNAME,MODE_PRIVATE);
            String username = preferences.getString(UsernameActivity.PREFS_USERNAME,"-1");
            int score = getSharedPreferences(PREFS_SCORE,MODE_PRIVATE).getInt(PREFS_SCORE,0);
            new DBHelper(this).saveScore(username, score);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        int score = getSharedPreferences(PREFS_SCORE,MODE_PRIVATE).getInt(PREFS_SCORE,0);
        getSupportActionBar().setTitle(PREFS_SCORE+": "+score);
        capitals = findViewById(R.id.btn_capitals);
        capitals.setOnClickListener(view->{
            Intent intent = new Intent(this, CapitalsActivity.class);
            startActivity(intent);
        });
        flags=findViewById(R.id.btn_flags);
        flags.setOnClickListener(v->{
            Intent intent = new Intent(this, FlagsActivity.class);
            startActivity(intent);
        });
        neighbors=findViewById(R.id.btn_neighbors);
        neighbors.setOnClickListener(v->{
            Intent intent = new Intent(this,NeighboursActivity.class);
            startActivity(intent);
        });
        sights=findViewById(R.id.btn_sights);
        sights.setOnClickListener(v->{
            Intent intent = new Intent(this, SightsActivity.class);
            startActivity(intent);
        });
    }
}