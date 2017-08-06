package cn.edu.cuc.toutiao.adapter;

import android.content.Context;
import android.media.MediaCodec;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.application.MyApp;

/**
 * Created by jack on 2017/8/6.
 */

public class NewsContentRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String[] contentItems;
    private String[] imgs;
    private ArrayList<String> imgurls;
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_IMG = 1;
    public NewsContentRvAdapter(Context context,String[] contentItems,String[] imgs) {
        this.context = context;
        this.contentItems = contentItems;
        this.imgs = imgs;
        imgurls = new ArrayList<String>(Arrays.asList(imgs));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType){
            case TYPE_TEXT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_text,parent,false);
                return new MyHolderText(v);
            case TYPE_IMG:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_img,parent,false);
                return new MyHolderImg(v);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyHolderText){
            ((MyHolderText)holder).text.setText(contentItems[position]);
        }else if(holder instanceof MyHolderImg){
            String str = contentItems[position];
            final int imgIndex = Integer.parseInt(str.substring(1,str.length()-1));
            Glide.with(context).load(imgs[imgIndex].substring(0,imgs[imgIndex].length()-1)).into(((MyHolderImg)holder).img);
//            ((MyHolderImg)holder).img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    MyApp.browsePhotos(context,imgurls,imgIndex);
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return contentItems.length;
    }

    @Override
    public int getItemViewType(int position) {
        String regex = "^\\[.*\\]$";
        if(contentItems[position].matches(regex)){
            return TYPE_IMG;
        }else {
            return TYPE_TEXT;
        }
    }

    //内部holder类
    public static class MyHolderText extends RecyclerView.ViewHolder {
        public TextView text;

        public MyHolderText(View v) {
            super(v);
            text = v.findViewById(R.id.text);
        }
    }

    //内部holder类
    public static class MyHolderImg extends RecyclerView.ViewHolder {
        public ImageView img;

        public MyHolderImg(View v) {
            super(v);
            img = v.findViewById(R.id.img);
        }
    }
}
