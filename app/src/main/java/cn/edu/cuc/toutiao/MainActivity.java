package cn.edu.cuc.toutiao;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.cuc.toutiao.fragments.FavFragment;
import cn.edu.cuc.toutiao.fragments.HomeFragment;
import cn.edu.cuc.toutiao.fragments.ProfileFragment;
import cn.edu.cuc.toutiao.fragments.VideoFragment;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private HomeFragment homeFragment;
    private VideoFragment videoFragment;
    private FavFragment favFragment;
    private ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        setContentFragment(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View bottomItem = tab.getCustomView();
                CheckBox icon = (CheckBox) bottomItem.findViewById(R.id.icon);
                TextView text = (TextView) bottomItem.findViewById(R.id.text);
                icon.setChecked(true);
                text.setTextColor(Color.parseColor("#ee1111"));
                int position = tab.getPosition();
                setContentFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View bottomItem = tab.getCustomView();
                CheckBox icon = (CheckBox) bottomItem.findViewById(R.id.icon);
                TextView text = (TextView) bottomItem.findViewById(R.id.text);
                icon.setChecked(false);
                text.setTextColor(Color.parseColor("#555555"));

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                Toast.makeText(MainActivity.this,"reselected",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setContentFragment(int position){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (position){
            case 0:
                if(homeFragment==null){
                    homeFragment = HomeFragment.newInstance();
                    transaction.add(R.id.contentFrame,homeFragment);
                }
                hideFragments(transaction);
                transaction.show(homeFragment);
                break;
            case 1:
                if(videoFragment==null){
                    videoFragment = VideoFragment.newInstance();
                    transaction.add(R.id.contentFrame,videoFragment);
                }
                hideFragments(transaction);
                transaction.show(videoFragment);
                break;
            case 2:
                if(favFragment==null){
                    favFragment = FavFragment.newInstance();
                    transaction.add(R.id.contentFrame,favFragment);
                }
                hideFragments(transaction);
                transaction.show(favFragment);
                break;
            case 3:
                if(profileFragment==null){
                    profileFragment = ProfileFragment.newInstance();
                    transaction.add(R.id.contentFrame,profileFragment);
                }
                hideFragments(transaction);
                transaction.show(profileFragment);
                break;
        }
        transaction.commit();
    }
    //hide所有的fragment
    private void hideFragments( FragmentTransaction transaction){
        if(homeFragment!=null){
            transaction.hide(homeFragment);
        }
        if(videoFragment!=null){
            transaction.hide(videoFragment);
        }
        if(favFragment!=null){
            transaction.hide(favFragment);
        }
        if(profileFragment!=null){
            transaction.hide(profileFragment);
        }
    }
}
