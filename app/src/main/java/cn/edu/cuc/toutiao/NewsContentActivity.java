package cn.edu.cuc.toutiao;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import cn.edu.cuc.toutiao.adapter.NewsContentPagerAdapter;
import cn.edu.cuc.toutiao.bean.NewsDetail;
import cn.edu.cuc.toutiao.retrofit.ApiService;
import cn.edu.cuc.toutiao.retrofit.ServiceGenerator;
import cn.edu.cuc.toutiao.util.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsContentActivity extends AppCompatActivity {
    private String newsId;
    private String type;
    private String uid;
    private String gid;
    private ApiService apiService;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<String> languages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        languages.add("中文");
        languages.add("原文");
        newsId = getIntent().getStringExtra("newsId");
        type = getIntent().getStringExtra("type");
        initId();
        apiService = ServiceGenerator.createService(ApiService.class);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

    }

    private void initId(){
        SPUtils spUtils = SPUtils.getInstance();
        uid = spUtils.getString("uid", "");
        gid = spUtils.getString("gid", "");
    }

    private void getNews(){
        Call<NewsDetail> call = apiService.getNews(newsId,gid,uid);
        call.enqueue(new Callback<NewsDetail>() {
            @Override
            public void onResponse(Call<NewsDetail> call, Response<NewsDetail> response) {
                NewsDetail newsDetail = response.body();
                viewPager.setAdapter(new NewsContentPagerAdapter(getSupportFragmentManager(),languages,newsDetail));
                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onFailure(Call<NewsDetail> call, Throwable t) {

            }
        });
    }
}
