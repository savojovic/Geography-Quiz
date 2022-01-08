package org.unibl.etf.kviz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.unibl.etf.kviz.helpers.DBHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.unibl.etf.kviz.CategoriesActivity.PREFS_SCORE;

public class SightsActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnA, btnB, btnC, btnD;
    ImageButton nextBtn;
    ImageButton infoBtn;
    TextView question;
    ImageView sightImage;
    int score;
    int questionNumber=0;
    ProgressBar progressBar;
    JSONArray countries;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights);
        score = getSharedPreferences(PREFS_SCORE,MODE_PRIVATE).getInt(PREFS_SCORE,0);
        getSupportActionBar().setTitle("SCORE: "+score);
        getreferences();
        setListeners();
        countries=new DBHelper(this).getAllSightCountries();
        setSight();
        randomiseAnswers();
    }
    public void handleClick(View v){
        try{
        String answer = ((Button)v).getText().toString();
        if(answer.equals(countries.getJSONObject(questionNumber).getString("true"))){
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
        progressBar.setProgress((100/countries.length())*(questionNumber+1));
        }catch (Exception e){

        }
    }
    public void setListeners(){
        btnA.setOnClickListener(this::handleClick);
        btnB.setOnClickListener(this::handleClick);
        btnC.setOnClickListener(this::handleClick);
        btnD.setOnClickListener(this::handleClick);

        nextBtn.setOnClickListener(v->{
            if(questionNumber==CategoriesActivity.NUMBER_OF_QUESTIONS-1) {
                finish();
            }
            else{
                setIsClickable(true);
                questionNumber++;
                setSight();
                randomiseAnswers();
                setNeutralColor();
            }

        });
    }
    public void randomiseAnswers(){
        try {
            int NUMBER_OF_ANSWERS = 4;
            int correctAnswerNum = (int) (Math.random() * NUMBER_OF_ANSWERS);
            if (correctAnswerNum == 0) {
                btnA.setText(countries.getJSONObject(questionNumber).getString("true"));
                btnC.setText(countries.getJSONObject(questionNumber).getString("false1"));
                btnB.setText(countries.getJSONObject(questionNumber).getString("false2"));
                btnD.setText(countries.getJSONObject(questionNumber).getString("false3"));
            } else if (correctAnswerNum == 1) {
                btnA.setText(countries.getJSONObject(questionNumber).getString("false1"));
                btnC.setText(countries.getJSONObject(questionNumber).getString("true"));
                btnB.setText(countries.getJSONObject(questionNumber).getString("false2"));
                btnD.setText(countries.getJSONObject(questionNumber).getString("false3"));
            } else if (correctAnswerNum == 2) {
                btnA.setText(countries.getJSONObject(questionNumber).getString("false1"));
                btnC.setText(countries.getJSONObject(questionNumber).getString("false2"));
                btnB.setText(countries.getJSONObject(questionNumber).getString("true"));
                btnD.setText(countries.getJSONObject(questionNumber).getString("false3"));
            } else if (correctAnswerNum == 3) {
                btnA.setText(countries.getJSONObject(questionNumber).getString("false1"));
                btnC.setText(countries.getJSONObject(questionNumber).getString("false2"));
                btnB.setText(countries.getJSONObject(questionNumber).getString("false3"));
                btnD.setText(countries.getJSONObject(questionNumber).getString("true"));
            }
        }catch (Exception e){
            e.printStackTrace();
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
    private void setIsClickable(boolean isClickable){
        btnA.setClickable(isClickable);
        btnB.setClickable(isClickable);
        btnC.setClickable(isClickable);
        btnD.setClickable(isClickable);
    }
    public void setSight()  {
        try {
            String sightName = countries.getJSONObject(questionNumber).getString("domain");
            Field idSight = R.drawable.class.getDeclaredField(sightName);
            Drawable sight = getDrawable(idSight.getInt(null));
            sightImage.setBackground(sight);
        } catch (NoSuchFieldException | IllegalAccessException | JSONException e ) {
            e.printStackTrace();
        }
    }
    public void getreferences(){
        sightImage=findViewById(R.id.sight_img);
        btnA=findViewById(R.id.btn_a);
        btnB=findViewById(R.id.btn_b);
        btnC=findViewById(R.id.btn_c);
        btnD=findViewById(R.id.btn_d);
        infoBtn=findViewById(R.id.btn_info);
        infoBtn.setVisibility(View.INVISIBLE);
        nextBtn=findViewById(R.id.btn_next);
        question=findViewById(R.id.question);
        progressBar=findViewById(R.id.progres_bar);
    }
}