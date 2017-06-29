package cn.edu.cuc.toutiao;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
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
    private boolean canExit = false;

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
                CheckBox icon = bottomItem.findViewById(R.id.icon);
                TextView text = bottomItem.findViewById(R.id.text);
                icon.setChecked(true);
                text.setTextColor(Color.parseColor("#ee1111"));
                int position = tab.getPosition();
                setContentFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View bottomItem = tab.getCustomView();
                CheckBox icon = bottomItem.findViewById(R.id.icon);
                TextView text = bottomItem.findViewById(R.id.text);
                icon.setChecked(false);
                text.setTextColor(Color.parseColor("#555555"));

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                Toast.makeText(MainActivity.this,"reselected",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //设置显示哪个fragment
    private void setContentFragment(int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
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

    //重写返回键事件
    @Override
    public void onBackPressed() {
        if(canExit){
            finish();
        }else {
            canExit = true;
            Toast.makeText(MainActivity.this,"再按一次就放你走",Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    canExit = false;
                }
            },2000);
        }
    }
}
