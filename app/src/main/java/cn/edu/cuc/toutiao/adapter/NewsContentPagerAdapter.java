package cn.edu.cuc.toutiao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import cn.edu.cuc.toutiao.bean.NewsDetail;
import cn.edu.cuc.toutiao.bean.Recommendation;
import cn.edu.cuc.toutiao.fragment.NewsContentFragment;
import cn.edu.cuc.toutiao.fragment.NewsListFragment;

/**
 * Created by jack on 2017/8/6.
 */

public class NewsContentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> languages;
    private NewsDetail newsDetail;
    private Recommendation recommendation;
    public NewsContentPagerAdapter(FragmentManager fm , ArrayList<String> languages, NewsDetail newsDetail, Recommendation recommendation) {
        super(fm);
        this.languages = languages;
        this.newsDetail = newsDetail;
        this.recommendation = recommendation;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return NewsContentFragment.newInstance(newsDetail.getChineseTitle().trim(),newsDetail.getChineseContent(),newsDetail.getImgs(),recommendation);
            case 1:
                return NewsContentFragment.newInstance(newsDetail.getTitle().trim(),newsDetail.getContent(),newsDetail.getImgs(),recommendation);
            default:
                return NewsContentFragment.newInstance(newsDetail.getChineseTitle().trim(),newsDetail.getChineseContent(),newsDetail.getImgs(),recommendation);
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