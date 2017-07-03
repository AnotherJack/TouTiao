package cn.edu.cuc.toutiao.fragments;


import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cuc.toutiao.EditorActivity;
import cn.edu.cuc.toutiao.PhotoActivity;
import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.SearchActivity;
import cn.edu.cuc.toutiao.adapters.NewsPagerAdapter;
import cn.edu.cuc.toutiao.application.MyApp;
import cn.edu.cuc.toutiao.beans.NewsTag;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements Toolbar.OnMenuItemClickListener {
    private View mainContent;
    private Toolbar toolbar;
    private TabLayout newsTabLayout;
    private ViewPager viewPager;
    private ArrayList<NewsTag> newsTags = new ArrayList<>();
    private NewsPagerAdapter newsPagerAdapter;
    private ArrayList<String> photoUrls = new ArrayList<>();
    private PopupWindow popupWindow;

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

        photoUrls.add("http://www.bing.com/az/hprichbg/rb/EternalFlame_EN-CA10974314579_1920x1080.jpg");
        photoUrls.add("http://www.bing.com/az/hprichbg/rb/SunwaptaFalls_PT-BR9240176817_1920x1080.jpg");
        photoUrls.add("http://www.bing.com/az/hprichbg/rb/OsmaniaHospital_EN-IN8052196074_1920x1080.jpg");
        photoUrls.add("http://www.bing.com/az/hprichbg/rb/TourdefranceD_EN-AU8654672839_1920x1080.jpg");
        photoUrls.add("http://www.bing.com/az/hprichbg/rb/LakePukaki_ROW12938827408_1920x1080.jpg");
        photoUrls.add("http://www.bing.com/az/hprichbg/rb/RanwuLake_EN-CA11972106071_1920x1080.jpg");
        photoUrls.add("http://blog.pic.xiaokui.io/5bc23947c22f5ea4bf0cf4fecb85c19b");

        mainContent = rootView.findViewById(R.id.main_content);
        toolbar = rootView.findViewById(R.id.toolbar);
        viewPager = rootView.findViewById(R.id.viewPager);
        newsTabLayout = rootView.findViewById(R.id.newsTabLayout);

        toolbar.inflateMenu(R.menu.menu_home_toolbar);
        toolbar.setOnMenuItemClickListener(this);

        newsPagerAdapter = new NewsPagerAdapter(getActivity().getSupportFragmentManager(),newsTags);
        viewPager.setAdapter(newsPagerAdapter);

        newsTabLayout.setupWithViewPager(viewPager);
        newsTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        return rootView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
//                showPopup();
                MyApp.browsePhotos(getActivity(),photoUrls,3);
                break;
            case R.id.edit:
                Intent editIntent = new Intent(getActivity(), EditorActivity.class);
                startActivity(editIntent);
                break;
            case R.id.search:
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(searchIntent);
                break;
        }
        return true;
    }

    private void showPopup(){
        if(popupWindow==null){
            //init popupWindow
            popupWindow = new PopupWindow(getActivity());
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_popup,null);
            ImageView close = popupView.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
            popupWindow.setContentView(popupView);
            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            popupWindow.setOutsideTouchable(false);
            popupWindow.setFocusable(true);
            popupWindow.setAnimationStyle(R.style.popup_anim_style);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    Toast.makeText(getActivity(),"dismiss",Toast.LENGTH_SHORT).show();
                    newsTags.add(new NewsTag("6","新增"));
                    newsTags.remove(1);
                    newsPagerAdapter.notifyDataSetChanged();

                }
            });
        }

        popupWindow.showAtLocation(mainContent, Gravity.CENTER,0,0);
    }
}
