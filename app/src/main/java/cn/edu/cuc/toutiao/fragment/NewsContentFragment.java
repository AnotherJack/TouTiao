package cn.edu.cuc.toutiao.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;
import com.zzhoujay.richtext.callback.OnImageClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cuc.toutiao.NewsContentActivity;
import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.adapter.RecRvQuickAdapter;
import cn.edu.cuc.toutiao.application.MyApp;
import cn.edu.cuc.toutiao.bean.Recommendation;

public class NewsContentFragment extends Fragment {
    private String title;
    private String content;
    private String[] imgs;
    private Recommendation recommendation;
    private RecRvQuickAdapter recRvQuickAdapter;


    public NewsContentFragment() {
        // Required empty public constructor
    }

    public static NewsContentFragment newInstance(String title, String content,String imgs,Recommendation recommendation) {
        NewsContentFragment fragment = new NewsContentFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("content", content);
        args.putString("imgs",imgs);
        args.putSerializable("recommendation",recommendation);
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
            recommendation = (Recommendation) getArguments().getSerializable("recommendation");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_content, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recRvQuickAdapter = new RecRvQuickAdapter(R.layout.rec_item,recommendation.getNews());
        recRvQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Recommendation.NewsItem newsItem = (Recommendation.NewsItem) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
                intent.putExtra("newsId",newsItem.getId());
                intent.putExtra("type",newsItem.getType());
                startActivity(intent);
            }
        });


        View detailView = inflater.inflate(R.layout.news_detail,container,false);
        TextView title_tv = detailView.findViewById(R.id.title);
        TextView content_tv = detailView.findViewById(R.id.content);
        title_tv.setText(title);
        for(int i=0;i<imgs.length;i++){
            content = content.replaceAll("\\["+i+"\\]","<img src='"+imgs[i]+"'/>");
        }
        RichText.from(content.replaceAll("(\n)+","<br/><br/>"))
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

        recRvQuickAdapter.addHeaderView(detailView);
        recyclerView.setAdapter(recRvQuickAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        return rootView;
    }

}
