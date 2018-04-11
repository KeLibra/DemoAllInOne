package com.android.peter.servicedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by peter on 2018/3/27.
 */

public class LocalService extends Service {
    private final static String TAG = "ServiceDemoLog";

    private final static int NOTIFICATION = 20180327;
    private Context mContext;
    private NotificationManager mNM;

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final LocalBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
        mContext = this;

        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand  Received start id " + startId + ": " + intent);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind intent = " + intent);
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
        stopForeground(true);
    }

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        NotificationChannel notificationChannel = new NotificationChannel("foreground service","service",NotificationManager.IMPORTANCE_HIGH);

        mNM.createNotificationChannel(notificationChannel);

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
                new Intent(mContext, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(mContext,"foreground service")
                .setSmallIcon(R.drawable.ic_launcher_foreground)  // the status icon
                .setTicker("Foreground service")  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle("Foreground service")  // the label of the entry
                .setContentText("Service is running")  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        // Send the notification.
        startForeground(NOTIFICATION, notification);
    }

    public void showToast(String string) {
        Toast.makeText(mContext,"Hi! I am " + string, Toast.LENGTH_SHORT).show();
    }

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }
}
