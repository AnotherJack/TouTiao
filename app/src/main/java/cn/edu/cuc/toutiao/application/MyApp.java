package cn.edu.cuc.toutiao.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;

import cn.edu.cuc.toutiao.PhotoActivity;
import cn.edu.cuc.toutiao.greendao.DaoMaster;
import cn.edu.cuc.toutiao.greendao.DaoSession;

/**
 * Created by zhengj on 2017/6/30.
 */

public class MyApp extends Application {
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        setUpDatabase();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }

    private void setUpDatabase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "db-name", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static void browsePhotos(Context context, ArrayList<String> urls, int initialIndex){
        Intent i = new Intent(context,PhotoActivity.class);
        i.putStringArrayListExtra("urls",urls);
        i.putExtra("initialIndex",initialIndex);
        context.startActivity(i);
    }
}
