package com.android.peter.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by peter on 2018/3/20.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    private final static String TAG = "BRDemo";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"onReceive = " + intent.getAction());
    }
}
