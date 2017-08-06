package cn.edu.cuc.toutiao.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.cuc.toutiao.R;

public class NewsContentFragment extends Fragment {
    private String title;
    private String[] contentItem;
    private String[] imgs;
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_IMG = 1;


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
            contentItem = getArguments().getString("content").split(" ");
            imgs = getArguments().getString("imgs").split(" ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_content, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        return rootView;
    }

}
