package cn.edu.cuc.toutiao.fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import cn.edu.cuc.toutiao.adapters.NewsListAdapter;
import cn.edu.cuc.toutiao.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends LazyFragment {
    private String type;
    private SwipeRefreshLayout swipeLayout;
    private ListView listView;
    private ArrayList<String> newsList = new ArrayList<>();
    private NewsListAdapter newsListAdapter;
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
        listView = (ListView) rootView.findViewById(R.id.listView);
        newsListAdapter = new NewsListAdapter(getActivity(), newsList);
        listView.setAdapter(newsListAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    // 判断是否滚动到底部
//                    if (view.getLastVisiblePosition() >= view.getCount() - 4 && !loading && !swipeLayout.isRefreshing() && canLoadmore) {
//                        //TODO 加载更多功能的代码
//                        setLoading(true);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                newsList.add("1");
//                                newsList.add("2");
//                                newsList.add("3");
//                                newsList.add("4");
//                                newsList.add("5");
//                                newsList.add("6");
//                                newsList.add("7");
//                                newsList.add("8");
//                                newsList.add("9");
//                                newsList.add("10");
//                                newsList.add("11");
//                                newsListAdapter.notifyDataSetChanged();
//                                setLoading(false);
//                            }
//                        }, 2000);
//                    }
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (view.getLastVisiblePosition() >= view.getCount() - 4 && !loading && !swipeLayout.isRefreshing() && canLoadmore) {
                    //TODO 加载更多功能的代码
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
                            newsListAdapter.notifyDataSetChanged();
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
                        newsListAdapter.notifyDataSetChanged();
                        swipeLayout.setRefreshing(false);
                        canLoadmore = true;
                    }
                }, 2000);
            }
        };
        swipeLayout.setOnRefreshListener(refreshListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),i+"",Toast.LENGTH_SHORT).show();
            }
        });

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

    private void setLoading(boolean bl) {
        this.loading = bl;
        if (bl) {
            listView.addFooterView(footer_loadmore);
        } else {
            listView.removeFooterView(footer_loadmore);
        }
    }
}
