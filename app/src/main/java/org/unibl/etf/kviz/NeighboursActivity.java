package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.unibl.etf.kviz.helpers.Country;
import org.unibl.etf.kviz.helpers.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NeighboursActivity extends AppCompatActivity {

    Button btnA, btnB, btnC, btnD;
    ImageButton nextBtn;
    ImageButton infoBtn;
    TextView question;

    int numOfAnswers=0;
    JSONArray countries;
    int questionNumber=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        countries = new DBHelper(this).getNeighbours();
        getReferences();
        askQuestion();
        try {
            randomiseAnswers();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
    private void handleClick(View v){
        try {
            JSONObject country = countries.getJSONObject(questionNumber);
            Button btn = (Button)v;
            if(btn.getText().toString().equals(country.getString("neighbor1"))){
                btn.setBackgroundColor(Color.GREEN);
            }else if(btn.getText().toString().equals(country.getString("neighbor2"))){
                btn.setBackgroundColor(Color.GREEN);
            }else{
                btn.setBackgroundColor(Color.RED);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        numOfAnswers++;
        if(numOfAnswers==2){
            questionNumber++;
            askQuestion();
            try {
                randomiseAnswers();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            numOfAnswers=0;
        }
    }
    private void getReferences(){
        btnA=findViewById(R.id.btn_a);
        btnB=findViewById(R.id.btn_b);
        btnC=findViewById(R.id.btn_c);
        btnD=findViewById(R.id.btn_d);
        infoBtn=findViewById(R.id.btn_info);
        nextBtn=findViewById(R.id.btn_next);
        question=findViewById(R.id.question);
    }


}