package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void createInitDataBase(){
        DBHelper mydb = new DBHelper(this);
        mydb.insertCountryCapital("Serbia","Belgrade","Novi Sad","Cacak", "Nis");
        mydb.insertCountryCapital("Croatia","Zagreb","Dubrovnik","Rijeka", "Zadar");
        mydb.insertCountryCapital("United Kingdom","London","Glasgow","Manchester", "York");
        mydb.insertCountryCapital("Germany","Berlin","Frankfurt","Koln", "Wolfsburg");
        mydb.insertCountryCapital("Italy","Rome","Venice","Verona", "Trieste");
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