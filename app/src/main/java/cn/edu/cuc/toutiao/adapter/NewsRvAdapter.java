package cn.edu.cuc.toutiao.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import cn.edu.cuc.toutiao.R;

public class NewsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> newsList;
    private ArrayList<View> footers = new ArrayList<>();

    final int TYPE_0 = 0;
    final int TYPE_1 = 1;
    final int TYPE_2 = 2;
    final int TYPE_3 = 3;
    final int TYPE_FOOTER = 4;
    float mX, mY, mCurrentX, mCurrentY;


    public NewsRvAdapter(ArrayList<String> newsList) {
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
            ((MyHolder0) holder).mTextView.setText(newsList.get(position));
            ((MyHolder0) holder).mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), ((TextView) v).getText() + " clicked", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof MyHolder1) {
            ((MyHolder1) holder).mTextView.setText(newsList.get(position));
            ((MyHolder1) holder).mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), ((TextView) v).getText() + " clicked", Toast.LENGTH_SHORT).show();
                }
            });

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

        return Integer.parseInt(newsList.get(position)) % 2;
    }

    //内部holder类
    public static class MyHolder0 extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public MyHolder0(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.title);
        }
    }

    //内部holder类
    public static class MyHolder1 extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public MyHolder1(View v) {
            super(v);
            mTextView = v.findViewById(R.id.title);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {
        public FooterHolder(View v) {
            super(v);
        }
    }
}

