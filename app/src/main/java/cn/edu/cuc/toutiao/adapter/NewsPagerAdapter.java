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
    private ArrayList<String> languages;
    public NewsPagerAdapter(FragmentManager fm , ArrayList<String> languages) {
        super(fm);
        this.languages = languages;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsListFragment.newInstance(languages.get(position));
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
