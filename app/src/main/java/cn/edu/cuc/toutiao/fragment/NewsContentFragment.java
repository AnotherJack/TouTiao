package cn.edu.cuc.toutiao.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.application.MyApp;

public class NewsContentFragment extends Fragment {
    private String title;
    private String content;
    private String[] imgs;


    public NewsContentFragment() {
        // Required empty public constructor
    }

    public static NewsContentFragment newInstance(String title, String content,String imgs) {
        NewsContentFragment fragment = new NewsContentFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("content", content);
        args.putString("imgs",imgs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
            content = getArguments().getString("content");
            imgs = getArguments().getString("imgs").split(" ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_content, container, false);
        TextView content_tv = rootView.findViewById(R.id.content);
        for(int i=0;i<imgs.length;i++){
            content = content.replaceAll("\\["+i+"\\]","<img src='"+imgs[i]+"'/>");
        }
        RichText.from(content.replaceAll("(\r\n)+","<br/><br/>"))
                .borderColor(Color.RED)
                .placeHolder(R.drawable.placeholder)
                .error(R.drawable.error)
                .imageClick(new OnImageClickListener() {
                    @Override
                    public void imageClicked(List<String> imageUrls, int position) {
                        Log.d("RichText------","---------image clicked!");
                        ArrayList<String> urls = new ArrayList<>(imageUrls);
                        MyApp.browsePhotos(getActivity(),urls,position);
                    }
                })
                .into(content_tv);
        return rootView;
    }

}
