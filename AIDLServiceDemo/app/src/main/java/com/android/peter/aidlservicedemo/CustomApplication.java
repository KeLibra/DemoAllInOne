package com.android.peter.aidlservicedemo;

import android.app.Application;

import com.android.peter.aidlservicedemo.database.DBHelper;

/**
 * Created by  peter_lenovo on 2018/8/9.
 */

public class CustomApplication extends Application {
    private DBHelper mDBHelper;
    @Override
    public void onCreate() {
        super.onCreate();

        mDBHelper = DBHelper.getInstance(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mDBHelper.close();
    }
}
