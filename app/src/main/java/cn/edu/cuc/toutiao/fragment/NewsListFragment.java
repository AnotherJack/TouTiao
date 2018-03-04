package cn.edu.cuc.toutiao.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cuc.toutiao.NewsContentActivity;
import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.adapter.NewsRvQuickAdapter;
import cn.edu.cuc.toutiao.bean.Recommendation;
import cn.edu.cuc.toutiao.retrofit.ApiService;
import cn.edu.cuc.toutiao.retrofit.ServiceGenerator;
import cn.edu.cuc.toutiao.util.SPUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends LazyFragment {
    private String language;
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView recyclerView;
    private NewsRvQuickAdapter newsRvQuickAdapter;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private ApiService apiService;
    private String gid;
    private String uid;
    private Gson gson = new GsonBuilder().create();
    private View rootView;

    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment newInstance(String language) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString("language", language);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            language = getArguments().getString("language");
        }
        apiService = ServiceGenerator.createService(ApiService.class);
        initId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_news_list, container, false);
            swipeLayout = rootView.findViewById(R.id.swipeLayout);
            swipeLayout.setColorSchemeResources(R.color.colorAccent);
            recyclerView = rootView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            newsRvQuickAdapter = new NewsRvQuickAdapter(new ArrayList());
            newsRvQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    loadData();
                }
            },recyclerView);
            newsRvQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Recommendation.NewsItem newsItem = (Recommendation.NewsItem) adapter.getItem(position);
                    Intent intent = new Intent(getActivity(), NewsContentActivity.class);
                    intent.putExtra("newsId",newsItem.getId());
                    intent.putExtra("type",newsItem.getType());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(newsRvQuickAdapter);
            newsRvQuickAdapter.setEmptyView(R.layout.empty_view);


            refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //TODO  下拉刷新
                    swipeLayout.setRefreshing(true);
                    loadData();
                }
            };
            swipeLayout.setOnRefreshListener(refreshListener);
        }else {
            //如果rootView不为null，说明不是第一次调用，直接返回rootView，方法体中的代码是解决（Java.lang.IllegalStateException: The specified child already has a parent）异常的
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }

        return rootView;
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();

        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        });

    }
    private void initId(){
        SPUtils spUtils = SPUtils.getInstance();
        uid = spUtils.getString("uid", "");
        gid = spUtils.getString("gid", "");
    }

    private void loadData(){
        Call<Recommendation> call = apiService.getRec("",gid,uid,10,"",language);
        call.enqueue(new Callback<Recommendation>() {
            @Override
            public void onResponse(Call<Recommendation> call, Response<Recommendation> response) {
                Recommendation recommendation = response.body();
                List<Recommendation.NewsItem> newsList = recommendation.getNews();

                if(swipeLayout.isRefreshing()){
                    //如果是刷新
                    newsRvQuickAdapter.setNewData(newsList);
                    swipeLayout.setRefreshing(false);
                }else {
                    //否则就是上拉加载
                    newsRvQuickAdapter.getData().addAll(newsList);
                    if(newsList.isEmpty()){
                        newsRvQuickAdapter.loadMoreEnd();
                    }else {
                        newsRvQuickAdapter.loadMoreComplete();
                    }
                }
                newsRvQuickAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Recommendation> call, Throwable t) {
                Toast.makeText(getActivity(),"出错了",Toast.LENGTH_SHORT).show();

                if(swipeLayout.isRefreshing()){
                    //如果是刷新
                    swipeLayout.setRefreshing(false);
                }else {
                    //否则就是上拉加载
                    newsRvQuickAdapter.loadMoreFail();
                }
            }
        });
    }

}
