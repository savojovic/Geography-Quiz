package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.unibl.etf.kviz.helpers.Country;
import org.unibl.etf.kviz.helpers.DBHelper;

import java.util.ArrayList;

public class CapitalsActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);
    TextView textViewQuestion ;
    String question;
    ArrayList<Country> countries;
    Button btnA, btnB, btnC, btnD;
    ImageButton btnPreviouse, btnNext;
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
        }else if(questionNumber==5){
            questionNumber=0;
            askQuestion();
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