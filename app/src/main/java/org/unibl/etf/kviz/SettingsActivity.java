package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import org.unibl.etf.kviz.helpers.DBHelper;

import java.util.Locale;

import static org.unibl.etf.kviz.CategoriesActivity.PREFS_SCORE;
import static org.unibl.etf.kviz.helpers.DBHelper.PREFS_QUESTION_NUMBER;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class SettingsActivity extends AppCompatActivity {

    Switch numOfQuestionsSwitch;
    RadioButton petPitanja, desetPitanja, englishRadioBtn, serbianRadioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        petPitanja=findViewById(R.id.pet_pitanja);
        desetPitanja=findViewById(R.id.deset_pitanja);
        englishRadioBtn=findViewById(R.id.english_radio_btn);
        serbianRadioBtn=findViewById(R.id.serbian_radio_btn);

        String numberOfQuestions = getSharedPreferences(PREFS_SCORE,MODE_PRIVATE).getString(DBHelper.PREFS_QUESTION_NUMBER,"0");
        String currentLang = getSharedPreferences(UsernameActivity.PREFS_LANGUAGE,MODE_PRIVATE).getString(UsernameActivity.PREFS_LANGUAGE,"non");

        if("en".equals(currentLang)){
            englishRadioBtn.toggle();
        }else if("sr".equals(currentLang)){
            serbianRadioBtn.toggle();
        }
        if("5".equals(numberOfQuestions)){
            petPitanja.toggle();
        }else{
            desetPitanja.toggle();
        }
    }
    public void onQuestionNumChanged(View v){
        String numOfQuestions = ((RadioButton)v).getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_QUESTION_NUMBER, MODE_PRIVATE).edit();
        editor.putString(DBHelper.PREFS_QUESTION_NUMBER,numOfQuestions);
        editor.apply();
    }
    public void onRadioButtonClicked(View v){
        String btnLanguage= ((RadioButton)v).getText().toString();
        String language;
        if("english".equals(btnLanguage.toLowerCase())){
            language="en";
        }else{
            language="sr";
        }

        SharedPreferences.Editor editor = getSharedPreferences(UsernameActivity.PREFS_LANGUAGE, MODE_PRIVATE).edit();
        editor.putString(UsernameActivity.PREFS_LANGUAGE, language);
        editor.apply();

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    protected void attachBaseContext(Context base) {
        // uzimamo trenutni jezik iz SharedPreferences-a
        SharedPreferences shPreferences = PreferenceManager.getDefaultSharedPreferences(base);
        String lang = shPreferences.getString(UsernameActivity.PREFS_LANGUAGE, Locale.getDefault().getLanguage());

        // sacuvamo promjene u konfiguraciji aplikacije
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        base.getResources().updateConfiguration(config,
                base.getResources().getDisplayMetrics());
        super.attachBaseContext(base);
    }
}