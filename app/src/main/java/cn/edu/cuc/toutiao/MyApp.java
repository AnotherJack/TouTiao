package cn.edu.cuc.toutiao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

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
        setUpDatabase();
    }

    private void setUpDatabase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "db-name", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }
}
