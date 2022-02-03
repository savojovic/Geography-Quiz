package org.unibl.etf.jkviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;

import org.unibl.etf.jkviz.helpers.DBHelper;

import java.util.Locale;

import static org.unibl.etf.jkviz.CategoriesActivity.PREFS_SCORE;
import static org.unibl.etf.jkviz.UsernameActivity.PREFS_LANGUAGE;
import static org.unibl.etf.jkviz.helpers.DBHelper.PREFS_QUESTION_NUMBER;

public class MainActivity extends AppCompatActivity {

    Button startBtn;
    Button settingsBtn;
    Button highScoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        createInitDataBase();
        SharedPreferences.Editor editor = getSharedPreferences(CategoriesActivity.PREFS_SCORE, MODE_PRIVATE).edit();
        editor.putInt(CategoriesActivity.PREFS_SCORE, 0);
        editor.apply();
//        editor.commit();
        setPreferences();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void setLanguage() {
        SharedPreferences preferences = getSharedPreferences(PREFS_LANGUAGE, MODE_PRIVATE);
        String lang = preferences.getString(PREFS_LANGUAGE, "non");
        if ("non".equals(lang)) {
            preferences.edit().putString(PREFS_LANGUAGE, "sr").apply();
            lang = "sr";

        }
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//        recreate();
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
        mydb.insertCountryCapital("Nigeria", "Abuja","Lagos","Kano","Ibadan","https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Coat_of_arms_of_Nigeria.svg/1056px-Coat_of_arms_of_Nigeria.svg.png","ng");
        mydb.insertCountryCapital("South Africa","Cape Town", "Durban","Pretoria","Mbombela","https://upload.wikimedia.org/wikipedia/en/thumb/2/21/Coat_of_arms_of_South_Africa.svg/228px-Coat_of_arms_of_South_Africa.svg.png","za");
        mydb.insertCountryCapital("Argentina","Buenos Aires","La Plata","Salta","Cordoba","https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Coat_of_arms_of_Argentina.svg/225px-Coat_of_arms_of_Argentina.svg.png","ar");
        mydb.insertCountryCapital("Chile","Santiago","Arica","Punta Arenas","Temuco","https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/Coat_of_arms_of_Chile_%28c%29.svg/375px-Coat_of_arms_of_Chile_%28c%29.svg.png","cl");
        mydb.insertCountryCapital("Australia","Canberra","Sydney","Melbourne","Brisbane","https://upload.wikimedia.org/wikipedia/commons/thumb/e/e5/Coat_of_arms_of_Australia.png/375px-Coat_of_arms_of_Australia.png","au");
        mydb.insertCountryCapital("India","Delhi","Mumbai","Kolkata","Kochi","https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Emblem_of_India.svg/330px-Emblem_of_India.svg.png","in");
        mydb.insertCountryCapital("Russia","Moscow","St. Petersburg","Samara","Vladivostok","https://upload.wikimedia.org/wikipedia/commons/thumb/f/f2/Coat_of_Arms_of_the_Russian_Federation.svg/300px-Coat_of_Arms_of_the_Russian_Federation.svg.png","ru");
        mydb.insertCountryCapital("Moldova","Chisinau","Orhei","Cahul","Bender","https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Coat_of_arms_of_Moldova.svg/330px-Coat_of_arms_of_Moldova.svg.png",".md");
        mydb.insertCountryCapital("Libya","Tripoli","Jeddah","Khoms","Benghazi","https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Seal_of_the_Government_of_National_Unity_%28Libya%29.svg/1024px-Seal_of_the_Government_of_National_Unity_%28Libya%29.svg.png","ly");
        mydb.insertCountryCapital("Norway","Oslo","Bergen","Alesund","Tromso","https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Greater_royal_coat_of_arms_of_Norway.svg/368px-Greater_royal_coat_of_arms_of_Norway.svg.png","no");
        mydb.insertCountryCapital("Denmark","Copenhagen","Aarhus","Aalborg","Esbjerg","https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Royal_coat_of_arms_of_Denmark.svg/300px-Royal_coat_of_arms_of_Denmark.svg.png","dk");
        mydb.insertCountryCapital("Netherlands","Amsterdam","The Hague","Utrecht","Rotterdam","https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/Royal_coat_of_arms_of_the_Netherlands.svg/330px-Royal_coat_of_arms_of_the_Netherlands.svg.png","nl");
        mydb.insertCountryCapital("Senegal","Dakar","Kaolack","Saint Louis","Mbour","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3d/Coat_of_arms_of_Senegal.svg/757px-Coat_of_arms_of_Senegal.svg.png","sn");
        mydb.insertCountryCapital("Romania","Bucharest","Brasov","Oradea","Sibiu","https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Coat_of_arms_of_Romania.svg/300px-Coat_of_arms_of_Romania.svg.png","ro");
        mydb.insertCountryCapital("Albania","Tirana","Berat","Vlore","Sarande","https://upload.wikimedia.org/wikipedia/commons/thumb/6/6e/Coat_of_arms_of_Albania.svg/225px-Coat_of_arms_of_Albania.svg.png","al");



        mydb.insertNeighbors("Serbia","Croatia", "Bulgaria","Austria","Germany");
        mydb.insertNeighbors("Austria","Germany", "Switzerland","Serbia","Spain");
        mydb.insertNeighbors("Ukraine","Russia", "Romania","Portugal","Norway");
        mydb.insertNeighbors("USA","Canada", "Mexico","Panama","Bolivia");
        mydb.insertNeighbors("Libya","Egypt", "Tunisia","Syria","France");
        mydb.insertNeighbors("Norway","Sweden", "Finland","Poland","Latvia");
        mydb.insertNeighbors("Latvia","Estonia", "Lithuania","Austria","Germany");
        mydb.insertNeighbors("France","Germany", "Spain","Austria","Denmark");
        mydb.insertNeighbors("Turkey","Greece", "Syria","Saudi Arabia","Turkmenistan");
        mydb.insertNeighbors("Italy","Slovenia", "Austria","Spain","Croatia");
        mydb.insertNeighbors("Paraguay","Brazil", "Bolivia","Uruguay","Chile");
        mydb.insertNeighbors("Argentina","Chile", "Uruguay","USA","Mexico");
        mydb.insertNeighbors("Panama","Colombia", "Costa Rica","Mexico","Bolivia");
        mydb.insertNeighbors("Mauritania","Mali", "Senegal","Kongo","Togo");
        mydb.insertNeighbors("Kenya","Somalia", "Tanzania","Libya","Egypt");
        mydb.insertNeighbors("Myanmar","Laos", "Thailand","India","Cambodia");
        mydb.insertNeighbors("North Korea","China", "South Korea","Japan","Taiwan");
        mydb.insertNeighbors("Mongolia","China", "Russia","Iran","Japan");
        mydb.insertNeighbors("Oman","Yemen", "UAE","Iraq","Iran");
        mydb.insertNeighbors("Slovakia","Hungary", "Austria","Croatia","Slovenia");


        mydb.insertSights("United Kingdom","Germany", "France", "USA","uk");
        mydb.insertSights("USA","Germany", "France", "UK","us");
        mydb.insertSights("France","Germany", "Spain", "USA","fr");
        mydb.insertSights("Spain","Germany", "France", "USA","es");
        mydb.insertSights("Russia","Germany", "France", "USA","ru");
        mydb.insertSights("India","Pakistan", "Turkey", "Iran","in");
        mydb.insertSights("China","India", "Japan", "Mongolia","cn");
        mydb.insertSights("Japan","Somalia", "Egypt", "China","jp");
        mydb.insertSights("Peru","Panama", "Mexico", "Columbia","pe");
        mydb.insertSights("Egypt","Lybia", "Somalia", "Sudan","eg");



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