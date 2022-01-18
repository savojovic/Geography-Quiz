package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.unibl.etf.kviz.helpers.DBHelper;

import static org.unibl.etf.kviz.CategoriesActivity.PREFS_SCORE;
import static org.unibl.etf.kviz.helpers.DBHelper.PREFS_QUESTION_NUMBER;

public class SettingsActivity extends AppCompatActivity {

    Switch numOfQuestionsSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        numOfQuestionsSwitch=findViewById(R.id.num_switch);
        SharedPreferences preferences = getSharedPreferences(PREFS_SCORE,MODE_PRIVATE);
        String numberOfQuestions = preferences.getString(DBHelper.PREFS_QUESTION_NUMBER,"0");

        if("5".equals(numberOfQuestions)){
            numOfQuestionsSwitch.setChecked(false);
            numOfQuestionsSwitch.setText(numberOfQuestions);

        }else{
            numOfQuestionsSwitch.setChecked(true);
            numOfQuestionsSwitch.setText(numberOfQuestions);
        }

        numOfQuestionsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){//10
                    SharedPreferences.Editor editor = getSharedPreferences(PREFS_QUESTION_NUMBER, MODE_PRIVATE).edit();
                    editor.putString(DBHelper.PREFS_QUESTION_NUMBER,"5");
                    editor.apply();
                    editor.commit();
                    numOfQuestionsSwitch.setText("5");

                }else{
                    SharedPreferences.Editor editor = getSharedPreferences(PREFS_QUESTION_NUMBER, MODE_PRIVATE).edit();
                    editor.putString(DBHelper.PREFS_QUESTION_NUMBER,"10");
                    editor.apply();
                    editor.commit();
                    numOfQuestionsSwitch.setText("10");
                }
            }
        });
    }


}