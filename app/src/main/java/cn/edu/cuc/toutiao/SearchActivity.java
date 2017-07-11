package cn.edu.cuc.toutiao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.bumptech.glide.Glide;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import cn.edu.cuc.toutiao.application.MyApp;
import cn.edu.cuc.toutiao.greendao.SearchRecord;
import cn.edu.cuc.toutiao.greendao.SearchRecordDao;

public class SearchActivity extends AppCompatActivity {
    private FloatingSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        initSearchView();

        //searchView获取焦点
        searchView.setSearchFocused(true);
    }


    //初始化searchView
    private void initSearchView(){
        searchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
                Glide.with(SearchActivity.this).load(R.drawable.ic_history_grey_24dp).into(leftIcon);
            }
        });

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                List<SearchRecord> newSuggestions;
                QueryBuilder<SearchRecord> qb = MyApp.getDaoSession()
                        .getSearchRecordDao()
                        .queryBuilder()
                        .where(SearchRecordDao.Properties.Text.like("%"+newQuery+"%"))
                        .orderDesc(SearchRecordDao.Properties.Id);
                newSuggestions = qb.list();
                searchView.swapSuggestions(newSuggestions);
            }
        });

        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                List<SearchRecord> suggestions;
                QueryBuilder<SearchRecord> qb = MyApp.getDaoSession()
                        .getSearchRecordDao()
                        .queryBuilder()
                        .where(SearchRecordDao.Properties.Text.like("%"+searchView.getQuery()+"%"))
                        .orderDesc(SearchRecordDao.Properties.Id);
                suggestions = qb.list();
                searchView.swapSuggestions(suggestions);
            }

            @Override
            public void onFocusCleared() {

            }
        });

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                String searchText = searchSuggestion.getBody();
                searchView.setSearchText(searchText);
                searchView.setSearchFocused(false);
                this.onSearchAction(searchText);
            }

            @Override
            public void onSearchAction(String currentQuery) {
                MyApp.getDaoSession().insertOrReplace(new SearchRecord(currentQuery));
            }
        });

        searchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                SearchActivity.this.finish();
            }
        });
    }
}
