package cn.edu.cuc.toutiao;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import cn.edu.cuc.toutiao.adapter.NewsContentPagerAdapter;
import cn.edu.cuc.toutiao.bean.NewsDetail;
import cn.edu.cuc.toutiao.bean.Recommendation;
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
    private NewsDetail newsDetail;
    private Recommendation recommendation;
    private View loadingView;

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
        loadingView = findViewById(R.id.loading_view);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getNews();
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
                newsDetail = response.body();
                getRec();
            }

            @Override
            public void onFailure(Call<NewsDetail> call, Throwable t) {
                Toast.makeText(NewsContentActivity.this,"出错了",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRec(){
        Call<Recommendation> call = apiService.getRecByNews(newsId,gid,uid,6);
        call.enqueue(new Callback<Recommendation>() {
            @Override
            public void onResponse(Call<Recommendation> call, Response<Recommendation> response) {
                recommendation = response.body();
                loadingView.setVisibility(View.INVISIBLE);

                viewPager.setAdapter(new NewsContentPagerAdapter(getSupportFragmentManager(),languages,newsDetail,recommendation));
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setCustomView(R.layout.lang_item_cn);
                tabLayout.getTabAt(1).setCustomView(R.layout.lang_item_origin);
            }

            @Override
            public void onFailure(Call<Recommendation> call, Throwable t) {
                Toast.makeText(NewsContentActivity.this,"出错了",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
