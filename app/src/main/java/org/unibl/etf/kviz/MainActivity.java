package org.unibl.etf.kviz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.unibl.etf.kviz.helpers.Country;
import org.unibl.etf.kviz.helpers.DBHelper;
import org.w3c.dom.Text;

import java.util.ArrayList;

import static org.unibl.etf.kviz.CategoriesActivity.PREFS_SCORE;
import static org.unibl.etf.kviz.helpers.DBHelper.PREFS_QUESTION_NUMBER;

public class MainActivity extends AppCompatActivity {

    Button startBtn;
    Button settingsBtn;
    Button highScoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createInitDataBase();
        SharedPreferences.Editor editor = getSharedPreferences(CategoriesActivity.PREFS_SCORE, MODE_PRIVATE).edit();
        editor.putInt(CategoriesActivity.PREFS_SCORE, 0);
        editor.apply();
//        editor.commit();
        setPreferences();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void setPreferences(){
        SharedPreferences preferences = getSharedPreferences(PREFS_QUESTION_NUMBER,MODE_PRIVATE);
        String number = preferences.getString(PREFS_QUESTION_NUMBER,"0");
        if("0".equals(number)){
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_SCORE, MODE_PRIVATE).edit();
            editor.putString(DBHelper.PREFS_QUESTION_NUMBER,"5");
            editor.apply();
            editor.commit();
        }
    }

    private void createInitDataBase(){
        DBHelper mydb = new DBHelper(this);
        mydb.insertCountryCapital("Serbia","Belgrade","Novi Sad","Cacak", "Nis","https://www.beograd.rs/images/logo_en.png","rs");
        mydb.insertCountryCapital("Croatia","Zagreb","Dubrovnik","Rijeka", "Zadar","https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Coat_of_arms_of_Zagreb.svg/225px-Coat_of_arms_of_Zagreb.svg.png","hr");
        mydb.insertCountryCapital("United Kingdom","London","Glasgow","Manchester", "York","https://upload.wikimedia.org/wikipedia/commons/thumb/2/20/Coat_of_Arms_of_The_City_of_London.svg/330px-Coat_of_Arms_of_The_City_of_London.svg.png","gb");
        mydb.insertCountryCapital("Germany","Berlin","Frankfurt","Koln", "Wolfsburg","https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Coat_of_arms_of_Berlin.svg/225px-Coat_of_arms_of_Berlin.svg.png","de");
        mydb.insertCountryCapital("Italy","Rome","Venice","Verona", "Trieste","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Insigne_Romanum_coronatum.svg/105px-Insigne_Romanum_coronatum.svg.png", "it");

        mydb.insertNeighbors("Serbia","Croatia", "Bulgaria","Austria","Germany");
        mydb.insertNeighbors("Austria","Germany", "Switzerland","Serbia","Spain");
        mydb.insertNeighbors("Ukraine","Russia", "Romania","Portugal","Norway");
        mydb.insertNeighbors("USA","Canada", "Mexico","Panama","Bolivia");
        mydb.insertNeighbors("China","Russia", "India","Ukraine","Belarus");

        mydb.insertSights("United Kingdom","Germany", "France", "USA","uk");
        mydb.insertSights("USA","Germany", "France", "UK","us");
        mydb.insertSights("France","Germany", "Spain", "USA","fr");
        mydb.insertSights("Spain","Germany", "France", "USA","es");
        mydb.insertSights("Russia","Germany", "France", "USA","ru");

    }
    @Override
    protected void onStart() {
        super.onStart();
        startBtn=findViewById(R.id.btn_start);
        startBtn.setOnClickListener(view->{
            Intent intent = new Intent(this, CategoriesActivity.class);
            startActivity(intent);
        });
        settingsBtn=findViewById(R.id.btn_settings);
        settingsBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
        highScoreBtn=findViewById(R.id.btn_highscore);
        highScoreBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, HighScoreActivity.class);
            startActivity(intent);
        });
    }

}