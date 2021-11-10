package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.unibl.etf.kviz.helpers.Country;
import org.unibl.etf.kviz.helpers.DBHelper;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createInitDataBase();
        SharedPreferences.Editor editor = getSharedPreferences(CategoriesActivity.PREFS_SCORE, MODE_PRIVATE).edit();
        editor.putInt(CategoriesActivity.PREFS_SCORE, 0);
        editor.apply();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void createInitDataBase(){
        DBHelper mydb = new DBHelper(this);
        mydb.insertCountryCapital("Serbia","Belgrade","Novi Sad","Cacak", "Nis","https://www.beograd.rs/images/logo_en.png");
        mydb.insertCountryCapital("Croatia","Zagreb","Dubrovnik","Rijeka", "Zadar","https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Coat_of_arms_of_Zagreb.svg/225px-Coat_of_arms_of_Zagreb.svg.png");
        mydb.insertCountryCapital("United Kingdom","London","Glasgow","Manchester", "York","https://upload.wikimedia.org/wikipedia/commons/thumb/2/20/Coat_of_Arms_of_The_City_of_London.svg/330px-Coat_of_Arms_of_The_City_of_London.svg.png");
        mydb.insertCountryCapital("Germany","Berlin","Frankfurt","Koln", "Wolfsburg","https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Coat_of_arms_of_Berlin.svg/225px-Coat_of_arms_of_Berlin.svg.png");
        mydb.insertCountryCapital("Italy","Rome","Venice","Verona", "Trieste","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Insigne_Romanum_coronatum.svg/105px-Insigne_Romanum_coronatum.svg.png");
    }
    @Override
    protected void onStart() {
        super.onStart();
        startBtn=findViewById(R.id.btn_start);
        startBtn.setOnClickListener(view->{
            Intent intent = new Intent(this, CategoriesActivity.class);
            startActivity(intent);
        });
    }

}