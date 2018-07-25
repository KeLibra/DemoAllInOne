package com.android.peter.messengerdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {
    private final static String TAG = "LogTAG.MessengerService";

    public final static int MSG_FROM_CLIENT = 0;
    public final static int MSG_FROM_SERVICE = 1;

    private Messenger mMessenger = new Messenger(new MessengerHandler());

    public MessengerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    Log.i(TAG,"Message from client = " + msg.getData().getString("msg"));
                    Message serviceMsg = Message.obtain();
                    serviceMsg.what = MSG_FROM_SERVICE;
                    Bundle data = new Bundle();
                    data.putString("msg","Hi, peter ! I am lemon .");
                    serviceMsg.setData(data);
                    if(msg.replyTo != null){
                        try {
                            msg.replyTo.send(serviceMsg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                default:
                    //do nothing
            }
        }
    }
}
