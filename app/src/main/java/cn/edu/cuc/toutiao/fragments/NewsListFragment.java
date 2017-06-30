package cn.edu.cuc.toutiao.fragments;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

import cn.edu.cuc.toutiao.adapters.NewsListAdapter;
import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.adapters.NewsRvAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends LazyFragment {
    private String type;
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView recyclerView;
    private ArrayList<String> newsList = new ArrayList<>();
    private NewsRvAdapter newsRvAdapter;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private boolean canLoadmore = false;
    private boolean loading = false;
    private View footer_loadmore;

    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment newInstance(String type) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString("type");
        }
        footer_loadmore = LayoutInflater.from(getActivity()).inflate(R.layout.footer_loadmore, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);
//        TextView tv = (TextView) rootView.findViewById(R.id.type);
//        tv.setText(type);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeLayout);
        swipeLayout.setColorSchemeResources(R.color.colorAccent);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(newsRvAdapter = new NewsRvAdapter(newsList));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //获取最后一个完全显示的ItemPosition
                int lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if(lastVisibleItem >= (totalItemCount-3) && !loading && !swipeLayout.isRefreshing() && canLoadmore){
                    //TODO 加载更多
                    setLoading(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            newsList.add("1");
                            newsList.add("2");
                            newsList.add("3");
                            newsList.add("4");
                            newsList.add("5");
                            newsList.add("6");
                            newsList.add("7");
                            newsList.add("8");
                            newsList.add("9");
                            newsList.add("10");
                            newsList.add("11");
                            newsRvAdapter.notifyDataSetChanged();
                            setLoading(false);
                        }
                    }, 2000);
                }
            }
        });
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newsList.clear();
                        newsList.add("1");
                        newsList.add("2");
                        newsList.add("3");
                        newsList.add("4");
                        newsList.add("5");
                        newsList.add("6");
                        newsList.add("7");
                        newsList.add("8");
                        newsList.add("9");
                        newsList.add("10");
                        newsList.add("11");
                        newsRvAdapter.notifyDataSetChanged();
                        swipeLayout.setRefreshing(false);
                        canLoadmore = true;
                    }
                }, 2000);
            }
        };
        swipeLayout.setOnRefreshListener(refreshListener);

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

    //切换加载更多状态
    private void setLoading(boolean bl) {
        this.loading = bl;
        if (bl) {
            newsRvAdapter.addFooterView(footer_loadmore);
        } else {
            newsRvAdapter.removeFooterView(footer_loadmore);
        }
    }
}
