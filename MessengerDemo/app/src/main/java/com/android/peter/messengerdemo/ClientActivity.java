package com.android.peter.messengerdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "LogTAG.ClientActivity";

    public final static int MSG_FROM_CLIENT = 0;
    public final static int MSG_FROM_SERVICE = 1;

    private Messenger mClientMessenger;
    private Messenger mServiceMessenger;
    private Button mBindService;
    private Button mSend;

    private ServiceConnection mSC = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"onServiceConnected name = " + name.toString());
            mServiceMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG,"onServiceDisconnected name = " + name.toString());
            mServiceMessenger = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        mClientMessenger = new Messenger(new MessengerHandler());
        mBindService = findViewById(R.id.btn_bind_service);
        mBindService.setOnClickListener(this);
        mSend = findViewById(R.id.btn_client_send);
        mSend.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSC != null) {
            unbindService(mSC);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind_service:
                Intent intent = new Intent(this,MessengerService.class);
                bindService(intent,mSC, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_client_send:
                Message clientMsg = Message.obtain();
                clientMsg.what = MSG_FROM_CLIENT;
                clientMsg.replyTo = mClientMessenger;
                Bundle data = new Bundle();
                data.putString("msg","Hi, I am peter ! What's your name ?");
                clientMsg.setData(data);
                if(mServiceMessenger != null) {
                    try {
                        mServiceMessenger.send(clientMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                //do nothing
        }
    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_FROM_SERVICE:
                    Log.i(TAG,"Message from service = " + msg.getData().getString("msg"));
                    break;
                default:
                    //do nothing
            }
        }
    }
}
