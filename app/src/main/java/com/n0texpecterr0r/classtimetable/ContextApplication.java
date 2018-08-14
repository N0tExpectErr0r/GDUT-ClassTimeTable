package com.n0texpecterr0r.classtimetable;

import android.app.Application;
import android.content.Context;
import com.n0texpecterr0r.classtimetable.db.DaoMaster;
import com.n0texpecterr0r.classtimetable.db.DaoMaster.DevOpenHelper;
import com.n0texpecterr0r.classtimetable.db.DaoSession;

/**
 * @author Created by Nullptr
 * @date 2018/8/13 11:20
 * @describe 全局Context Application
 */
public class ContextApplication extends Application {
    private final static String DB_NAME = "ClassTimeTable_db";
    private static Context sContext;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    /**
     * 获得DaoMaster
     * @return DaoMaster
     */
    public static DaoMaster getDaoMaster(){
        if (mDaoMaster == null){
            DevOpenHelper helper = new DevOpenHelper(sContext,DB_NAME,null);
            mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    /**
     * 获取DaoSession
     * @return DaoSession
     */
    public static DaoSession getDaoSession(){
        if(mDaoSession == null){
            if(mDaoMaster == null){
                mDaoMaster = getDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }
}
