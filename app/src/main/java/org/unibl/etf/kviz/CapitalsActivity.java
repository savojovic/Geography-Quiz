package org.unibl.etf.kviz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.Toast;

import org.unibl.etf.kviz.helpers.Country;
import org.unibl.etf.kviz.helpers.DBHelper;

import java.util.ArrayList;
import java.util.Random;

public class CapitalsActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);
    TextView textViewQuestion ;
    String question;
    ArrayList<Country> countries;
    Button btnA, btnB, btnC, btnD;
    ImageButton btnPreviouse, btnNext, btnInfo;
    ProgressBar progressBar;
    int score=0;
    private int questionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countries = dbHelper.getAllCountries();
        getSupportActionBar().setTitle("SCORE: "+score);

        setContentView(R.layout.activity_capitals);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        getReferences();
        askQuestion();
        setListeners();
    }
    private void askQuestion(){
        if(questionNumber<countries.size()&&questionNumber>=0){
            question= getString(R.string.captial_question)+" "+countries.get(questionNumber).countryName+"?";
            textViewQuestion.setText(question);
            randomiseAnswers();
        }else if(questionNumber<0){
            questionNumber=4;
            askQuestion();
            randomiseAnswers();
        }else if(questionNumber==CategoriesActivity.NUMBER_OF_QUESTIONS){
//            questionNumber=0;
//            askQuestion();
//            randomiseAnswers();
        }

    }
    private void randomiseAnswers(){
        int NUMBER_OF_ANSWERS = 4;
        int correctAnswerNum = (int)(Math.random()* NUMBER_OF_ANSWERS);
        if(correctAnswerNum==0){
            btnA.setText(countries.get(questionNumber).capital);
            btnC.setText(countries.get(questionNumber).city1);
            btnB.setText(countries.get(questionNumber).city2);
            btnD.setText(countries.get(questionNumber).city3);
        }else if(correctAnswerNum==1){
            btnA.setText(countries.get(questionNumber).city1);
            btnC.setText(countries.get(questionNumber).capital);
            btnB.setText(countries.get(questionNumber).city2);
            btnD.setText(countries.get(questionNumber).city3);
        }else if(correctAnswerNum==2){
            btnA.setText(countries.get(questionNumber).city1);
            btnC.setText(countries.get(questionNumber).city2);
            btnB.setText(countries.get(questionNumber).capital);
            btnD.setText(countries.get(questionNumber).city3);
        }else if(correctAnswerNum==3){
            btnA.setText(countries.get(questionNumber).city1);
            btnC.setText(countries.get(questionNumber).city2);
            btnB.setText(countries.get(questionNumber).city3);
            btnD.setText(countries.get(questionNumber).capital);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
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
        textViewQuestion = findViewById(R.id.capitals_question);
        btnA=findViewById(R.id.btn_capitals_a);
        btnB = findViewById(R.id.btn_capitals_b);
        btnC= findViewById(R.id.btn_capitals_c);
        btnD = findViewById(R.id.btn_capitals_d);
        btnPreviouse=findViewById(R.id.btn_previouse);
        btnNext=findViewById(R.id.btn_next);
        progressBar=findViewById(R.id.progres_bar_capitals);
        btnInfo=findViewById(R.id.btn_info);
    }

    private void setIsClickable(boolean isClickable){
        btnA.setClickable(isClickable);
        btnB.setClickable(isClickable);
        btnC.setClickable(isClickable);
        btnD.setClickable(isClickable);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setListeners(){
        View.OnClickListener listener = v -> {
            String answer = ((Button)v).getText().toString();
            if(answer.equals(countries.get(questionNumber).capital)) {
                v.setBackgroundColor(Color.GREEN);
                score++;
                SharedPreferences preferences = getSharedPreferences(CategoriesActivity.PREFS_SCORE,MODE_PRIVATE);
                int oldScore = preferences.getInt(CategoriesActivity.PREFS_SCORE,0);
                SharedPreferences.Editor editor = getSharedPreferences(CategoriesActivity.PREFS_SCORE, MODE_PRIVATE).edit();
                editor.putInt(CategoriesActivity.PREFS_SCORE, oldScore+1);
                editor.apply();
                editor.commit();
                getSupportActionBar().setTitle( CategoriesActivity.PREFS_SCORE+": "+score);
            }else{
                v.setBackgroundColor(Color.RED);
            }
            setIsClickable(false);
            progressBar.setProgress((100/countries.size())*(questionNumber+1));
        };
        btnA.setOnClickListener(listener);
        btnB.setOnClickListener(listener);
        btnC.setOnClickListener(listener);
        btnD.setOnClickListener(listener);
        btnInfo.setOnClickListener((v)->launchMap());
        btnNext.setOnClickListener(v->{
            if(questionNumber==CategoriesActivity.NUMBER_OF_QUESTIONS-1) {
                finish();
            }
            else{
                setIsClickable(true);
                questionNumber++;
                askQuestion();
                setNeutralColor();
            }

        });
//        btnPreviouse.setOnClickListener(v->{
//            setIsClickable(true);
//            questionNumber--;
//            askQuestion();
//            setNeutralColor();
//        });
    }
    void launchMap(){
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("capital",countries.get(questionNumber).capital);
        intent.putExtra("country",countries.get(questionNumber).countryName);
        intent.putExtra("coa", countries.get(questionNumber).capitalCOA);
        intent.putExtra("domain",countries.get(questionNumber).domain);
        startActivity(intent);
    }
}