package org.unibl.etf.kviz.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static org.unibl.etf.kviz.CategoriesActivity.PREFS_SCORE;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "db_kviz";
    public static final String TABLE_CAPITALS = "capitals";
    public static final String TABLE_NEIGHBORS = "neghbors";
    public static final String TABLE_SIGHTS = "sights";
    public static final String TABLE_HIGHSCORE = "highscore";
    public static final String PREFS_QUESTION_NUMBER = "QUESTION_NUMBER";
    private int numberOfQuestions;
    Context context;

    SharedPreferences prefs;

    public DBHelper(Context context){
        super(context,DB_NAME, null, 1);
        this.context=context;
        prefs = context.getSharedPreferences(PREFS_QUESTION_NUMBER,MODE_PRIVATE);
        numberOfQuestions = Integer.parseInt(prefs.getString(PREFS_QUESTION_NUMBER,"5"));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_SCORE,MODE_PRIVATE);

        db.execSQL("create table "+TABLE_CAPITALS+" (countryName text primary key, capital text, city1 text, city2 text, city3 text,coa text,domain text)");
        db.execSQL(
                "create table "+TABLE_NEIGHBORS+"(" +
                        "countryName text primary key, neighbor1 text, neighbor2 text, country1 text, country2 text,"+
                        "foreign key(countryName) references "+TABLE_CAPITALS+"(countryName)"+
                        ")"
        );
        db.execSQL("create table "+TABLE_SIGHTS+ "(true text primary key, false1 text, false2 text, false3 text,domain text)");
        db.execSQL("create table "+TABLE_HIGHSCORE+ "(username text primary key, highscore integer)");

//        insertCountryCapital("Serbia","Belgrade","Novi Sad","Cacak", "Nis");
    }
    public void saveScore(String username, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues params = new ContentValues();
        params.put("username",username);
        params.put("highscore",score);
        long res = db.insert(TABLE_HIGHSCORE,null,params);
        if(res==-1){
            String sql = "select highscore from "+TABLE_HIGHSCORE+" where username='"+username+"'";
            Cursor highscoreCursor = db.rawQuery("select highscore from "+TABLE_HIGHSCORE+" where username='"+username+"'",null);
            highscoreCursor.moveToFirst();
            String highcore = highscoreCursor.getString(0);
            if(Integer.parseInt(highcore)<score) {
                db.execSQL("update " + TABLE_HIGHSCORE + " SET highscore='" + score + "' where username='" + username + "'");
            }
        }

    }
    public void insertSights(String correctCountry, String country1, String country2, String country3, String domain){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues params = new ContentValues();
        params.put("true",correctCountry);
        params.put("false1",country1);
        params.put("false2",country2);
        params.put("false3",country3);
        params.put("domain",domain);
        db.insert(TABLE_SIGHTS,null,params);
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
    public List<String> getHighscores(){
        ArrayList<String> array = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_HIGHSCORE+ " order by highscore desc",null);
        res.moveToFirst();
        while(!res.isAfterLast()){
                array.add(res.getString(0)+": "+res.getString(1));
                res.moveToNext();
        }
        return array;
    }
    public JSONArray getNeighbours(){
        JSONArray countries = new JSONArray();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NEIGHBORS + " order by random() limit "+numberOfQuestions,null);
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

        Cursor res = mydb.rawQuery("select * from "+TABLE_CAPITALS+ " order by random() limit "+numberOfQuestions, null);
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
    public JSONArray getAllSightCountries(){
        SQLiteDatabase mydb = this.getReadableDatabase();

        Cursor res = mydb.rawQuery("select * from "+TABLE_SIGHTS+ " order by random() limit "+numberOfQuestions,null);
        res.moveToFirst();
        JSONArray countries = new JSONArray();
        while (!res.isAfterLast()){
            JSONObject country = new JSONObject();
            try {
                country.put("true", res.getString(0));
                country.put("false1", res.getString(1));
                country.put("false2", res.getString(2));
                country.put("false3", res.getString(3));
                country.put("domain", res.getString(4));
                countries.put(country);
                res.moveToNext();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return countries;
    }
    public ArrayList<String> getSightDomains(){
        ArrayList<String> domains = new ArrayList<>();
        SQLiteDatabase mydb = this.getReadableDatabase();
        Cursor res = mydb.rawQuery("select domain from "+TABLE_SIGHTS+ " order by random() limit "+numberOfQuestions,null);
        res.moveToFirst();
        while (!res.isAfterLast()){
            domains.add(res.getString(0));
            res.moveToNext();
        }
        res.close();
        return domains;
    }
    public ArrayList<String> getCountryDomains(){
        ArrayList<String> domains = new ArrayList<>();
        SQLiteDatabase mydb = this.getReadableDatabase();
        Cursor res = mydb.rawQuery("select domain from "+TABLE_CAPITALS+ " order by random() limit "+numberOfQuestions, null);
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
