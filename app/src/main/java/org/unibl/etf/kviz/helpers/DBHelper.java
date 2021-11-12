package org.unibl.etf.kviz.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "db_kviz";
    public static final String TABLE_CAPITALS = "capitals";

    public DBHelper(Context context){
        super(context,DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_CAPITALS+" (countryName text primary key, capital text, city1 text, city2 text, city3 text,coa text,domain text)");
//        insertCountryCapital("Serbia","Belgrade","Novi Sad","Cacak", "Nis");
    }

    public void insertCountryCapital(String country, String capital,String city1,String city2, String city3, String coa, String domain){
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues params = new ContentValues();
        params.put("countryName",country);
        params.put("capital", capital);
        params.put("city1",city1);
        params.put("city2",city2);
        params.put("city3", city3);
        params.put("coa",coa);
        params.put("domain", domain);
        mydb.insert(TABLE_CAPITALS, null, params);
    }
    public ArrayList<Country> getAllCountries(){
        ArrayList<Country> countries = new ArrayList<>();

        SQLiteDatabase mydb = this.getReadableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_CAPITALS, null);
        res.moveToFirst();

        while(res.isAfterLast()==false){
            countries.add(new Country(
                    res.getString(0),res.getString(1),res.getString(2),
                    res.getString(3), res.getString(4),res.getString(5),res.getString(6)));
            res.moveToNext();
        }
        res.close();
        return countries;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
