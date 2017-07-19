package cn.edu.cuc.toutiao.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import cn.edu.cuc.toutiao.R;


/**
 * Created by jack on 2017/7/1.
 */

public class PhotoPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> urls;
    private SparseArray<View> cacheView;

    public PhotoPagerAdapter(Context context,ArrayList<String> urls) {
        this.context = context;
        this.urls = urls;
        cacheView = new SparseArray<>(urls.size());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = cacheView.get(position);

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_photo_view,container,false);
            PhotoView photoView = view.findViewById(R.id.photoView);
            Glide.with(context).load(urls.get(position)).into(photoView);
            photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    Activity activity = (Activity) context;
                    activity.finish();
                }
            });
            cacheView.put(position,view);
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
