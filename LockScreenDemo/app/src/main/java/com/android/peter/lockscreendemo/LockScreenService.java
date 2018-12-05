package com.android.peter.lockscreendemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class LockScreenService extends Service {
    private final static String TAG = LockScreenService.class.getSimpleName();

    private final static int FOREGROUND_NOTIFICATION_ID = 20181205;

    private Context mContext;
    private NotificationManager mNM;
    private IntentFilter mIntentFilter = new IntentFilter();
    private LockScreenReceiver mReceiver = new LockScreenReceiver();
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        sendForegroundServiceNotification();
        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mIntentFilter.addAction(Intent.ACTION_USER_PRESENT);
        mIntentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        mContext.registerReceiver(mReceiver,mIntentFilter);
    }

    private void sendForegroundServiceNotification() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(mContext, LockScreenActivity.class));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent,0);

        Notification.Builder nb;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("foreground","foreground service", NotificationManager.IMPORTANCE_LOW);
            mNM.createNotificationChannel(channel);
            nb = new Notification.Builder(mContext,channel.getId());
        } else {
            nb = new Notification.Builder(mContext);
        }

        nb.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("LockScreen")
                .setContentText("LockScreen service is running!")
                .setContentIntent(pendingIntent);

        startForeground(FOREGROUND_NOTIFICATION_ID, nb.build());
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
            } else if(Intent.ACTION_USER_PRESENT.equalsIgnoreCase(intent.getAction())) {

            }
        }
    }
}
