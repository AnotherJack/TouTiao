package cn.edu.cuc.toutiao.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.adapters.NewsPagerAdapter;
import cn.edu.cuc.toutiao.beans.NewsTag;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private TabLayout newsTabLayout;
    private ViewPager viewPager;
    private ArrayList<NewsTag> newsTags = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        newsTags.add(new NewsTag("1","综合"));
        newsTags.add(new NewsTag("2","国内"));
        newsTags.add(new NewsTag("3","国外"));
        newsTags.add(new NewsTag("4","宇宙"));
        newsTags.add(new NewsTag("5","世界"));
        newsTags.add(new NewsTag("5","世界"));
        newsTags.add(new NewsTag("5","世界"));
        newsTags.add(new NewsTag("5","世界"));

        newsTabLayout = getActivity().findViewById(R.id.newsTabLayout);
        viewPager = rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new NewsPagerAdapter(getActivity().getSupportFragmentManager(),newsTags));
        newsTabLayout.setupWithViewPager(viewPager);
        newsTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        return rootView;
    }

}
