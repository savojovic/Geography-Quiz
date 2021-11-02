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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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