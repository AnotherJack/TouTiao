package cn.edu.cuc.toutiao.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;

import cn.edu.cuc.toutiao.beans.NewsTag;
import cn.edu.cuc.toutiao.fragments.NewsListFragment;

/**
 * Created by zhengj on 2017/6/27.
 */

public class NewsPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<NewsTag> newsTags;
    public NewsPagerAdapter(FragmentManager fm , ArrayList<NewsTag> newsTags) {
        super(fm);
        this.newsTags = newsTags;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsListFragment.newInstance(newsTags.get(position).getType());
    }

    @Override
    public int getCount() {
        return newsTags.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return newsTags.get(position).getTitle();
    }
}
