package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.unibl.etf.kviz.helpers.DBHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.unibl.etf.kviz.CategoriesActivity.PREFS_SCORE;

public class FlagsActivity extends AppCompatActivity {

    ImageView flagImage;
    TextView flagQuestion;
    GridLayout flagAnswer;
    GridLayout answersBox;
    ImageButton flagNext;

    String currentCountryName = "";
    ArrayList<String> countryDomains;
    private int questionNumber = 0;

    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flags);
        getReferences();

        score = getSharedPreferences(PREFS_SCORE,MODE_PRIVATE).getInt(PREFS_SCORE,0);
        getSupportActionBar().setTitle("SCORE: "+score);

        countryDomains = new DBHelper(this).getCountryDomains();
        setFlag();
        setAnswers();
        setListeners();
    }
    private String getCountryName(HashMap<String, String> countriesAndDomains){
        for (Map.Entry<String, String> entry : countriesAndDomains.entrySet()) {
            String countryName = entry.getKey();
            String domain = entry.getValue();
            if(countryDomains.get(questionNumber).equals(domain)){
                currentCountryName=countryName;
                return countryName;
            }
        }
        currentCountryName="";
        return "";
    }
    private void checkAnswer(){
        StringBuilder answer= new StringBuilder();
        for(int i=0;i<flagAnswer.getChildCount();i++){
            Button btn = (Button) flagAnswer.getChildAt(i);
            String text = (String) btn.getText();
            answer.append(text);
        }
        if(answer.toString().toLowerCase().equals(currentCountryName.toLowerCase()))
            flagNext.callOnClick();
    }
    private void handleClick(View v){
        answersBox.removeView(v);
        flagAnswer.addView(v);
        v.setOnClickListener(view->{
            flagAnswer.removeView(view);
            answersBox.addView(view);
            view.setOnClickListener(this::handleClick);
        });
        checkAnswer();
    }
    private void setAnswers(){
        View.OnClickListener listener= this::handleClick;
        answersBox.removeAllViews();
        HashMap<String, String> countriesAndDomains = new DBHelper(this).getCountriesAndDomains();
        String letters=getCountryName(countriesAndDomains);
        List<String> shuffledLetters=randomiseLetters(letters);
        for(String letter: shuffledLetters){
            Button btn = new Button(this);
            btn.setText(letter);
            btn.setLayoutParams(new ConstraintLayout.LayoutParams(200, 150));
            btn.setOnClickListener(listener);
            answersBox.addView(btn);
        }
    }
    private List<String> randomiseLetters(String letters){
        List<String> shuffled = Arrays.asList(letters.split(""));
        Collections.shuffle(shuffled);
        return shuffled;
    }
    private void setListeners(){
        flagNext.setOnClickListener(v->{
            if(questionNumber>=countryDomains.size()) {
                //TODO: save score to prefs
                finish();
            }else {
                questionNumber++;
                flagAnswer.removeAllViews();
                setFlag();
                setAnswers();
            }
        });
    }
    private void getReferences(){
        flagImage=findViewById(R.id.flag_img);
        flagQuestion=findViewById(R.id.flag_question);
        flagAnswer=findViewById(R.id.flag_answer);
        answersBox=findViewById(R.id.answer_grid);
        flagNext=findViewById(R.id.flags_next_btn);
    }
    private void setFlag(){
        String flagName = "ic_"+countryDomains.get(questionNumber);
        try {
            Field idFlag = R.drawable.class.getDeclaredField(flagName);
            Drawable flag = getDrawable(idFlag.getInt(null));
            flagImage.setBackground(flag);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}