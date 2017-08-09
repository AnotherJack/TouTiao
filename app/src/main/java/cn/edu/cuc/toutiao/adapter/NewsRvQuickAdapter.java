package cn.edu.cuc.toutiao.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.edu.cuc.toutiao.R;
import cn.edu.cuc.toutiao.bean.Recommendation;

/**
 * Created by jack on 2017/8/5.
 */

public class NewsRvQuickAdapter extends BaseMultiItemQuickAdapter<Recommendation.NewsItem,BaseViewHolder> {
    private RequestOptions requestOptions;
    private RequestListener requestListener;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewsRvQuickAdapter(List data) {
        super(data);
        addItemType(Recommendation.NewsItem.TYPE_0, R.layout.news_item0);
        addItemType(Recommendation.NewsItem.TYPE_1, R.layout.news_item1);
        addItemType(Recommendation.NewsItem.TYPE_2, R.layout.news_item2);
        addItemType(Recommendation.NewsItem.TYPE_3, R.layout.news_item3);

        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .centerCrop();

        requestListener = new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                Log.d("Glide-------Fail",(String) model);
                return true;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        };
    }

    @Override
    protected void convert(BaseViewHolder helper, Recommendation.NewsItem item) {
        helper.setText(R.id.title, item.getChineseTitle().trim())
                .setText(R.id.country,item.getCountry());
        String[] imgs = item.getImgs().trim().split(" ");
        switch (helper.getItemViewType()) {
            case Recommendation.NewsItem.TYPE_0:

                break;
            case Recommendation.NewsItem.TYPE_1:
                Glide.with(mContext).load(imgs[0]).apply(requestOptions).listener(requestListener).into((ImageView) helper.getView(R.id.img));
                break;
            case Recommendation.NewsItem.TYPE_2:
                Glide.with(mContext).load(imgs[0]).apply(requestOptions).listener(requestListener).into((ImageView) helper.getView(R.id.img1));
                Glide.with(mContext).load(imgs[1]).apply(requestOptions).listener(requestListener).into((ImageView) helper.getView(R.id.img2));
                break;
            case Recommendation.NewsItem.TYPE_3:
                Glide.with(mContext).load(imgs[0]).apply(requestOptions).listener(requestListener).into((ImageView) helper.getView(R.id.img1));
                Glide.with(mContext).load(imgs[1]).apply(requestOptions).listener(requestListener).into((ImageView) helper.getView(R.id.img2));
                Glide.with(mContext).load(imgs[2]).apply(requestOptions).listener(requestListener).into((ImageView) helper.getView(R.id.img3));
                break;
        }
    }
}
