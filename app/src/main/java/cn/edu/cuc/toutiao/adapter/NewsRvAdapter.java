package cn.edu.cuc.toutiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.bean.Recommendation;

public class NewsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Recommendation.NewsItem> newsList;
    private ArrayList<View> footers = new ArrayList<>();
    private Context context;

    private final int TYPE_0 = 0;
    private final int TYPE_1 = 1;
    private final int TYPE_2 = 2;
    private final int TYPE_3 = 3;
    private final int TYPE_FOOTER = 4;
    float mX, mY, mCurrentX, mCurrentY;


    public NewsRvAdapter(Context context,ArrayList<Recommendation.NewsItem> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public void addFooterView(View v){
        footers.add(v);
        notifyDataSetChanged();
    }
    public void removeFooterView(View v){
        footers.remove(v);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType){
            case TYPE_0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item0, parent, false);
                return new MyHolder0(v);
            case TYPE_1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item1, parent, false);
                return new MyHolder1(v);
            case TYPE_2:

            case TYPE_3:

            case TYPE_FOOTER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loadmore, parent, false);
                return new FooterHolder(v);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyHolder0) {
            ((MyHolder0) holder).title.setText(newsList.get(position).getChineseTitle());
            ((MyHolder0) holder).country.setText(newsList.get(position).getCountry());
        } else if (holder instanceof MyHolder1) {
            ((MyHolder1) holder).title.setText(newsList.get(position).getChineseTitle());
            ((MyHolder1) holder).country.setText(newsList.get(position).getCountry());
            String[] imgs = newsList.get(position).getImgs().split(" ");
            Glide.with(context).load(imgs[0]).into(((MyHolder1) holder).img);
        }else if (holder instanceof MyHolder2) {
            ((MyHolder2) holder).title.setText(newsList.get(position).getChineseTitle());
            ((MyHolder2) holder).country.setText(newsList.get(position).getCountry());
            String[] imgs = newsList.get(position).getImgs().split(" ");
            Glide.with(context).load(imgs[0]).into(((MyHolder2) holder).img1);
            Glide.with(context).load(imgs[0]).into(((MyHolder2) holder).img2);
        }else if (holder instanceof MyHolder3) {
            ((MyHolder3) holder).title.setText(newsList.get(position).getChineseTitle());
            ((MyHolder3) holder).country.setText(newsList.get(position).getCountry());
            String[] imgs = newsList.get(position).getImgs().split(" ");
            Glide.with(context).load(imgs[0]).into(((MyHolder3) holder).img1);
            Glide.with(context).load(imgs[0]).into(((MyHolder3) holder).img2);
            Glide.with(context).load(imgs[0]).into(((MyHolder3) holder).img3);
        }else if(holder instanceof FooterHolder){

        }

    }

    @Override
    public int getItemCount() {
        return newsList.size()+footers.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position>=newsList.size()){
            return TYPE_FOOTER;
        }

        String[] imgs = newsList.get(position).getImgs().split(" ");
        if(imgs.length>3){
            return TYPE_3;
        }else {
            return imgs.length;
        }
    }

    //内部holder类
    public static class MyHolder0 extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView country;

        public MyHolder0(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            country = v.findViewById(R.id.country);
        }
    }

    //内部holder类
    public static class MyHolder1 extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView country;
        public ImageView img;

        public MyHolder1(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            country = v.findViewById(R.id.country);
            img = v.findViewById(R.id.img);
        }
    }

    //内部holder类
    public static class MyHolder2 extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView country;
        public ImageView img1;
        public ImageView img2;

        public MyHolder2(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            country = v.findViewById(R.id.country);
            img1 = v.findViewById(R.id.img1);
            img2 = v.findViewById(R.id.img2);
        }
    }

    //内部holder类
    public static class MyHolder3 extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView country;
        public ImageView img1;
        public ImageView img2;
        public ImageView img3;

        public MyHolder3(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            country = v.findViewById(R.id.country);
            img1 = v.findViewById(R.id.img1);
            img2 = v.findViewById(R.id.img2);
            img3 = v.findViewById(R.id.img3);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {
        public FooterHolder(View v) {
            super(v);
        }
    }
}

