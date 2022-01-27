package org.unibl.etf.kviz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.unibl.etf.kviz.helpers.Country;
import org.unibl.etf.kviz.helpers.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.unibl.etf.kviz.CategoriesActivity.PREFS_SCORE;

public class NeighboursActivity extends AppCompatActivity {


    Button btnA, btnB, btnC, btnD;
    ImageButton nextBtn;
    ImageButton infoBtn;
    TextView question;
    int greenBtns=0;

    int numOfAnswers=0;
    JSONArray countries;
    int questionNumber=0;
    int score;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        score = getSharedPreferences(PREFS_SCORE,MODE_PRIVATE).getInt(PREFS_SCORE,0);
        getSupportActionBar().setTitle("SCORE: "+score);
        countries = new DBHelper(this).getNeighbours();
        getReferences();
        askQuestion();
        try {
            randomiseAnswers();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        infoBtn.setVisibility(View.INVISIBLE);
        nextBtn.setOnClickListener(v->{
            if(questionNumber+1==countries.length()){
                finish();
            }
            greenBtns=0;
            questionNumber++;
            setNeutralColor();
            askQuestion();
            try {
                randomiseAnswers();
                numOfAnswers=0;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setProgress((100/(countries.length()))*(questionNumber));
        });
    }

    private void askQuestion() {
        try {
            JSONObject country = countries.getJSONObject(questionNumber);
            String countryName = getString(R.string.neighbors_question)+" "+country.getString("country");
            question.setText(countryName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void randomiseAnswers() throws JSONException {
        int NUMBER_OF_ANSWERS = 4;
        int correctAnswerNum = (int)(Math.random()* NUMBER_OF_ANSWERS);
        int correctAnswerNum2 = (int)(Math.random()* NUMBER_OF_ANSWERS);
        while(correctAnswerNum2==correctAnswerNum){
            correctAnswerNum2 = (int)(Math.random()* NUMBER_OF_ANSWERS);
        }

        JSONObject country = countries.getJSONObject(questionNumber);
        HashMap<Integer, Button> adapter = new HashMap<>();
        adapter.put(0,btnA);
        adapter.put(1,btnB);
        adapter.put(2,btnC);
        adapter.put(3,btnD);

        Button correctBtn = adapter.get(correctAnswerNum);
        correctBtn.setOnClickListener(this::handleClick);
        Button correctBtn1 = adapter.get(correctAnswerNum2);
        correctBtn1.setOnClickListener(this::handleClick);
        adapter.remove(correctAnswerNum);
        adapter.remove(correctAnswerNum2);
        correctBtn.setText(country.getString("neighbor1"));
        correctBtn1.setText(country.getString("neighbor2"));
        int count=1;
        for (Button btn : adapter.values()) {
            btn.setText(country.getString("country"+count++));
            btn.setOnClickListener(this::handleClick);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void handleClick(View v){
        try {
            JSONObject country = countries.getJSONObject(questionNumber);
            Button btn = (Button)v;
            btn.setClickable(false);
            if(btn.getText().toString().equals(country.getString("neighbor1"))){
                btn.setBackgroundColor(Color.GREEN);
                SharedPreferences preferences = getSharedPreferences(PREFS_SCORE,MODE_PRIVATE);
                int oldScore = preferences.getInt(PREFS_SCORE,0);
                int newScore = oldScore+1;
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_SCORE, MODE_PRIVATE).edit();
                editor.putInt(PREFS_SCORE, newScore);
                editor.apply();
                editor.commit();
                getSupportActionBar().setTitle( PREFS_SCORE+": "+newScore);
            }else if(btn.getText().toString().equals(country.getString("neighbor2"))){
                btn.setBackgroundColor(Color.GREEN);
                SharedPreferences preferences = getSharedPreferences(PREFS_SCORE,MODE_PRIVATE);
                int oldScore = preferences.getInt(PREFS_SCORE,0);
                int newScore = oldScore+1;
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_SCORE, MODE_PRIVATE).edit();
                editor.putInt(PREFS_SCORE, newScore);
                editor.apply();
                editor.commit();
                getSupportActionBar().setTitle( PREFS_SCORE+": "+newScore);
            }else{
                btn.setBackgroundColor(Color.RED);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        numOfAnswers++;
        if(numOfAnswers==2){
            btnA.setClickable(false);
            btnB.setClickable(false);
            btnC.setClickable(false);
            btnD.setClickable(false);
        }
    }
    private void setNeutralColor(){
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int primaryColor = typedValue.data;
        btnA.setBackgroundColor(primaryColor);
        btnB.setBackgroundColor(primaryColor);
        btnC.setBackgroundColor(primaryColor);
        btnD.setBackgroundColor(primaryColor);
    }
    private void getReferences(){
        btnA=findViewById(R.id.btn_a);
        btnB=findViewById(R.id.btn_b);
        btnC=findViewById(R.id.btn_c);
        btnD=findViewById(R.id.btn_d);
        infoBtn=findViewById(R.id.btn_info);
        nextBtn=findViewById(R.id.btn_next);
        question=findViewById(R.id.question);
        progressBar=findViewById(R.id.progres_bar);
    }


}