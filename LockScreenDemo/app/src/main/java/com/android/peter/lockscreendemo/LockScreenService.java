package com.android.peter.lockscreendemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class LockScreenService extends Service {
    private final static String TAG = LockScreenService.class.getSimpleName();

    private Context mContext;
    private IntentFilter mIntentFilter = new IntentFilter();
    private LockScreenReceiver mReceiver = new LockScreenReceiver();
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mIntentFilter.addAction(Intent.ACTION_USER_PRESENT);
        mContext.registerReceiver(mReceiver,mIntentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }

    private static class LockScreenReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"onReceive action = " + intent.getAction());
            if(Intent.ACTION_SCREEN_OFF.equalsIgnoreCase(intent.getAction())) {
                Intent LockScreenIntent = new Intent(context, LockScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                context.startActivity(LockScreenIntent);
            }
        }
    }
}
