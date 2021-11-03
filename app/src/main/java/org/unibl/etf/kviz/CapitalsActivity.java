package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
    ImageButton btnPreviouse, btnNext;
    private final int NUMEBR_OF_ANSWERS=4;
    private int questionNumber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countries = dbHelper.getAllCountries();
        setContentView(R.layout.activity_capitals);
    }

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
        }else if(questionNumber<0){
            questionNumber=4;
            askQuestion();
        }else if(questionNumber==CategoriesActivity.NUMBER_OF_QUESTIONS){
            questionNumber=0;
            askQuestion();
        }
        randomiseAnswers();
//        btnA.setText(countries.get(questionNumber).city1);
//        btnB.setText(countries.get(questionNumber).city2);
//        btnC.setText(countries.get(questionNumber).city3);
//        btnD.setText(countries.get(questionNumber).capital);
    }
    private void randomiseAnswers(){
        int correctAnswerNum = (int)(Math.random()*NUMEBR_OF_ANSWERS+1);
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
    private void getReferences(){
        textViewQuestion = findViewById(R.id.capitals_question);
        btnA=findViewById(R.id.btn_capitals_a);
        btnB = findViewById(R.id.btn_capitals_b);
        btnC= findViewById(R.id.btn_capitals_c);
        btnD = findViewById(R.id.btn_capitals_d);
        btnPreviouse=findViewById(R.id.btn_previouse);
        btnNext=findViewById(R.id.btn_next);
    }
    private void setListeners(){
//        btnA.setOnClickListener(v->{
//            String answer = btnA.getText().toString();
//            if(answer.equals(countries.get(questionNumber).capital)) {
//                btnA.setBackgroundColor(Color.BLACK);
//            }else{
//                btnA.setBackgroundColor(Color.RED);
//            }
//        });
        btnNext.setOnClickListener(v->{
            questionNumber++;
            askQuestion();
        });
        btnPreviouse.setOnClickListener(v->{
            questionNumber--;
            askQuestion();
        });
    }
}