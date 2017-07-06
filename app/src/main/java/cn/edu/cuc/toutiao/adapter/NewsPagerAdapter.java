package cn.edu.cuc.toutiao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;

import cn.edu.cuc.toutiao.bean.NewsTag;
import cn.edu.cuc.toutiao.fragment.NewsListFragment;

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

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
