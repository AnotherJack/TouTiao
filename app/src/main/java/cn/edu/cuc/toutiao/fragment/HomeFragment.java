package cn.edu.cuc.toutiao.fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import cn.edu.cuc.toutiao.EditorActivity;
import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.SearchActivity;
import cn.edu.cuc.toutiao.adapter.NewsPagerAdapter;
import cn.edu.cuc.toutiao.application.MyApp;
import cn.edu.cuc.toutiao.bean.NewsTag;
import cn.edu.cuc.toutiao.retrofit.ApiService;
import cn.edu.cuc.toutiao.retrofit.ServiceGenerator;
import cn.edu.cuc.toutiao.util.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements Toolbar.OnMenuItemClickListener {
    private View mainContent;
    private Toolbar toolbar;
    private TabLayout newsTabLayout;
    private ViewPager viewPager;
    private ArrayList<String> types = new ArrayList<>();
    private ArrayList<String> languages = new ArrayList<>();
    private NewsPagerAdapter newsPagerAdapter;
    private PopupWindow popupWindow;
    private String uid;
    private String gid;
    private ApiService api;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initId();
        api = ServiceGenerator.createService(ApiService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        mainContent = rootView.findViewById(R.id.main_content);
        toolbar = rootView.findViewById(R.id.toolbar);
        viewPager = rootView.findViewById(R.id.viewPager);
        newsTabLayout = rootView.findViewById(R.id.newsTabLayout);

        toolbar.inflateMenu(R.menu.menu_home_toolbar);
        toolbar.setOnMenuItemClickListener(this);

//        newsPagerAdapter = new NewsPagerAdapter(getActivity().getSupportFragmentManager(),types);
        newsPagerAdapter = new NewsPagerAdapter(getActivity().getSupportFragmentManager(),languages);
        viewPager.setAdapter(newsPagerAdapter);

        newsTabLayout.setupWithViewPager(viewPager);
        newsTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //加载types
//        getTypes();
        getLanguages();

        return rootView;
    }

    private void initId(){
        SPUtils spUtils = SPUtils.getInstance();
        uid = spUtils.getString("uid", "");
        gid = spUtils.getString("gid", "");
    }

    private void getTypes(){
        Call<ArrayList<String>> call = api.getTypes(gid,uid);
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                types.addAll(response.body());
                newsPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(getActivity(),"出错了",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void getLanguages(){
        Call<ArrayList<String>> call = api.getLanguages(gid,uid);
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                languages.addAll(response.body());
                newsPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(getActivity(),"出错了",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.add:
////                showPopup();
//                MyApp.browsePhotos(getActivity(),photoUrls,3);
//                break;
//            case R.id.edit:
//                Intent editIntent = new Intent(getActivity(), EditorActivity.class);
//                startActivity(editIntent);
//                break;
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
                    newsPagerAdapter.notifyDataSetChanged();

                }
            });
        }

        popupWindow.showAtLocation(mainContent, Gravity.CENTER,0,0);
    }
}
