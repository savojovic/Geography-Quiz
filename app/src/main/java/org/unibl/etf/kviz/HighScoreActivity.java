package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.unibl.etf.kviz.helpers.DBHelper;

import java.util.List;

public class HighScoreActivity extends AppCompatActivity {
    ListView highscoreListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        highscoreListView=findViewById(R.id.highscore_list_view);
        List<String> highscores= new DBHelper(this).getHighscores();
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,highscores);
        highscoreListView.setAdapter(arr);
    }
}