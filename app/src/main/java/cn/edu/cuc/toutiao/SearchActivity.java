package cn.edu.cuc.toutiao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import cn.edu.cuc.toutiao.greendao.SearchRecord;
import cn.edu.cuc.toutiao.greendao.SearchRecordDao;

public class SearchActivity extends AppCompatActivity {
    private FloatingSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = (FloatingSearchView) findViewById(R.id.floating_search_view);

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
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                String searchText = searchSuggestion.getBody();
                searchView.setSearchText(searchText);
                this.onSearchAction(searchText);
            }

            @Override
            public void onSearchAction(String currentQuery) {
                MyApp.getDaoSession().insertOrReplace(new SearchRecord(currentQuery));
            }
        });

        //searchView获取焦点
        searchView.setSearchFocused(true);
        searchView.setSearchText("");
    }
}
