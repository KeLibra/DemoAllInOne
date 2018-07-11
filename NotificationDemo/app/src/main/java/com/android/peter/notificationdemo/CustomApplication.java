package com.android.peter.notificationdemo;

import android.app.Application;

/**
 * Created by  peter on 2018/7/11.
 */

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationChannels.createAllNotificationChannels(this);
    }
}
