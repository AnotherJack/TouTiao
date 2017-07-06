package cn.edu.cuc.toutiao;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import cn.edu.cuc.toutiao.adapter.PhotoPagerAdapter;
import cn.edu.cuc.toutiao.widget.HackyViewPager;

public class PhotoActivity extends AppCompatActivity {
    private HackyViewPager viewPager;
    private TextView index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        viewPager = (HackyViewPager) findViewById(R.id.viewPager);
        index = (TextView) findViewById(R.id.index);

        final ArrayList<String> urls = getIntent().getStringArrayListExtra("urls");
        int initialIndex = getIntent().getIntExtra("initialIndex",0);

        PhotoPagerAdapter photoPagerAdapter = new PhotoPagerAdapter(this,urls);
        viewPager.setAdapter(photoPagerAdapter);
        viewPager.setCurrentItem(initialIndex);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index.setText(position+1+"/"+urls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        index.setText(initialIndex+1+"/"+urls.size());


    }
}
