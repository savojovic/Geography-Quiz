package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

import static org.unibl.etf.kviz.CategoriesActivity.PREFS_SCORE;

public class UsernameActivity extends AppCompatActivity {

    Button saveUserNameBtn;
    public static String PREFS_USERNAME = "username";
    public static String PREFS_LANGUAGE = "Locale.Helper.Selected.Language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        saveUserNameBtn = findViewById(R.id.save_username_button);
        saveUserNameBtn.setOnClickListener(v->{
            EditText usernameView = findViewById(R.id.editTextUseraname);
            String username = usernameView.getText().toString();
            if(username.length()>0) {
                SharedPreferences preferences = getSharedPreferences(PREFS_USERNAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PREFS_USERNAME, username);
                editor.apply();
                editor.commit();
                startActivity(new Intent(this,MainActivity.class));
            }
        });

    }
    public void setLanguage(){
        SharedPreferences preferences = getSharedPreferences(PREFS_LANGUAGE,MODE_PRIVATE);
        String lang = preferences.getString(PREFS_LANGUAGE,"non");
        if("non".equals(lang)) {
            preferences.edit().putString(PREFS_LANGUAGE, "sr").apply();
            lang="sr";

        }
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//        recreate();
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