package org.unibl.etf.kviz.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "db_kviz";
    public static final String TABLE_CAPITALS = "capitals";
    public static final String TABLE_NEIGHBORS = "neghbors";

    public DBHelper(Context context){
        super(context,DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_CAPITALS+" (countryName text primary key, capital text, city1 text, city2 text, city3 text,coa text,domain text)");
        db.execSQL(
                "create table "+TABLE_NEIGHBORS+"(" +
                        "countryName text primary key, neighbor1 text, neighbor2 text, country1 text, country2 text,"+
                        "foreign key(countryName) references "+TABLE_CAPITALS+"(countryName)"+
                        ")"
        );
//        insertCountryCapital("Serbia","Belgrade","Novi Sad","Cacak", "Nis");
    }
    public void insertNeighbors(String countryName, String neighbor1, String neighbor2, String country1, String country2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues params = new ContentValues();
        params.put("countryName", countryName);
        params.put("neighbor1", neighbor1);
        params.put("neighbor2", neighbor2);
        params.put("country1", country1);
        params.put("country2", country2);
        db.insert(TABLE_NEIGHBORS, null, params);
    }
    public JSONArray getNeighbours(){
        JSONArray countries = new JSONArray();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NEIGHBORS,null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            JSONObject country = new JSONObject();
            try {
                country.put("country",res.getString(0));
                country.put("neighbor1",res.getString(1));
                country.put("neighbor2",res.getString(2));
                country.put("country1",res.getString(3));
                country.put("country2",res.getString(4));
                countries.put(country);
                res.moveToNext();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return countries;
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

        while(!res.isAfterLast()){
            countries.add(new Country(
                    res.getString(0),res.getString(1),res.getString(2),
                    res.getString(3), res.getString(4),res.getString(5),res.getString(6)));
            res.moveToNext();
        }
        res.close();
        return countries;
    }

    public ArrayList<String> getCountryDomains(){
        ArrayList<String> domains = new ArrayList<>();
        SQLiteDatabase mydb = this.getReadableDatabase();
        Cursor res = mydb.rawQuery("select domain from "+TABLE_CAPITALS, null);
        res.moveToFirst();
        while (!res.isAfterLast()){
            domains.add(res.getString(0));
            res.moveToNext();
        }
        res.close();
        return domains;
    }
    public HashMap<String, String> getCountriesAndDomains(){
         HashMap<String, String> res = new HashMap<>();
         SQLiteDatabase mydb = this.getReadableDatabase();
         Cursor response = mydb.rawQuery("select countryName, domain from "+TABLE_CAPITALS,null);
         response.moveToFirst();
         while(!response.isAfterLast()){
             res.put(response.getString(0),response.getString(1));
             response.moveToNext();
         }
         return res;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
