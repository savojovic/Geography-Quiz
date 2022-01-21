package org.unibl.etf.kviz.helpers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.unibl.etf.kviz.R;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private List<NewsItem> array;

    public NewsAdapter(Activity activity, List<NewsItem> array){
        this.activity=activity;
        this.array=array;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView=inflater.inflate(R.layout.custom_news_item,null);
        }
        TextView newsTitle = convertView.findViewById(R.id.news_title);
        TextView newsDesc = convertView.findViewById(R.id.news_description);

        NewsItem item = array.get(position);

        newsTitle.setText(item.title);
        newsDesc.setText(item.desc);
        return convertView;
    }
}