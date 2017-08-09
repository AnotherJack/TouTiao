package cn.edu.cuc.toutiao.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.bean.Recommendation;

/**
 * Created by zhengj on 2017/8/9.
 */

public class RecRvQuickAdapter extends BaseQuickAdapter<Recommendation.NewsItem,BaseViewHolder> {

    public RecRvQuickAdapter(@LayoutRes int layoutResId, @Nullable List<Recommendation.NewsItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Recommendation.NewsItem item) {
        helper.setText(R.id.title,item.getChineseTitle().trim());
    }
}
