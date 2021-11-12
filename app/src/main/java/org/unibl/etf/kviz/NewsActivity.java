package org.unibl.etf.kviz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.unibl.etf.kviz.helpers.NewsAdapter;
import org.unibl.etf.kviz.helpers.NewsItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    ListView newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new NewsFetcher(this).execute(getIntent().getExtras().getString("domain"));
        setContentView(R.layout.activity_news);
        newsList=findViewById(R.id.news_list_view);
    }

    private class NewsFetcher extends AsyncTask<String, String, JSONArray>{

        Activity activity;
        String urlAddress=getString(R.string.news_url);
        String responseStr;
        JSONArray responseJSON;

        public NewsFetcher(Activity activity){
            this.activity=activity;
        }
        @Override
        protected JSONArray doInBackground(String... strings) {
            String countryCode = strings[0];
            String query = "?access_key="+getString(R.string.api_key_news)+"&countries="+countryCode+"&limit="+getString(R.string.limit_news);
            try {
                URL url = new URL(urlAddress+query);
                HttpURLConnection httpURLConnection=null;

                try{
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    responseStr=streamToData(httpURLConnection.getInputStream());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                responseJSON =  (new JSONObject(responseStr)).getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return responseJSON;
        }
    String streamToData(InputStream stream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            String data, result="";
            while ((data=bufferedReader.readLine())!=null){
                result+=data;
            }
            if(null!=stream)
                stream.close();
            return result;
    }
        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            super.onPostExecute(jsonObject);
            List<NewsItem> items = JSONtoList(jsonObject);
            NewsAdapter adapter = new NewsAdapter(activity,items);
            newsList.setAdapter(adapter);
            newsList.setOnItemClickListener((parent, view, position, id) -> {
               String url = ((NewsItem)adapter.getItem(position)).url;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            });
            //TODO: create a list with news
            System.out.println(jsonObject);
        }
        private List<NewsItem> JSONtoList(JSONArray news){
            List<NewsItem> listNews = new ArrayList<>();
            for(int i =0; i<news.length();i++){
                try {
                    JSONObject newsItem = news.getJSONObject(i);
                    String title = newsItem.getString("title");
                    String description = newsItem.getString("description");
                    String url = newsItem.getString("url");
                    listNews.add(new NewsItem(title,description,url));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return listNews;
        }
    }
}