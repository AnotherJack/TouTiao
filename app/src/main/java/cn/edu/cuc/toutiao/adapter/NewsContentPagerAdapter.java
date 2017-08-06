package cn.edu.cuc.toutiao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import cn.edu.cuc.toutiao.bean.NewsDetail;
import cn.edu.cuc.toutiao.fragment.NewsContentFragment;
import cn.edu.cuc.toutiao.fragment.NewsListFragment;

/**
 * Created by jack on 2017/8/6.
 */

public class NewsContentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> languages;
    private NewsDetail newsDetail;
    public NewsContentPagerAdapter(FragmentManager fm , ArrayList<String> languages, NewsDetail newsDetail) {
        super(fm);
        this.languages = languages;
        this.newsDetail = newsDetail;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return NewsContentFragment.newInstance(newsDetail.getChineseTitle(),newsDetail.getChineseContent(),newsDetail.getImgs());
            case 1:
                return NewsContentFragment.newInstance(newsDetail.getTitle(),newsDetail.getContent(),newsDetail.getImgs());
            default:
                return NewsContentFragment.newInstance(newsDetail.getChineseTitle(),newsDetail.getChineseContent(),newsDetail.getImgs());
        }
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return languages.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}