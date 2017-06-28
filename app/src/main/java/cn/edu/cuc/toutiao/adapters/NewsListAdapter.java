package cn.edu.cuc.toutiao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.edu.cuc.toutiao.R;


import java.util.ArrayList;

/**
 * Created by zhengj on 2017/6/27.
 */

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> newsList;
    private LayoutInflater inflater;

    public NewsListAdapter(Context context, ArrayList<String> newsList) {
        this.context = context;
        this.newsList = newsList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.news_item0,null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(newsList.get(position));

        return convertView;
    }

    public final class ViewHolder {
        public TextView title;
    }
}
