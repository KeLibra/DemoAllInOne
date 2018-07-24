package com.android.peter.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "ServiceDemoLog";

    private Context mContext;
    private Button mStart;
    private Button mStop;
    private Button mBind;
    private Button mUnbind;
    private Button mStopSelf;
    private Button mShowToast;
    private Intent mIntent;

    // To invoke the bound service, first make sure that this value
    // is not null.
    private LocalService mBoundService;
    private LocalService.LocalBinder mBinder;

    //flag for service bind state
    private boolean mIsBind;

    private ServiceConnection mSC = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "onServiceConnected ~ componentName = " + componentName + " ,iBinder = " + iBinder);
            mIsBind = true;
            mBinder = (LocalService.LocalBinder) iBinder;
            mBoundService = mBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected ~ componentName = " + componentName);
            mIsBind = false;
            mBinder = null;
            mBoundService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mIntent = new Intent();
        mIntent.setComponent(new ComponentName(mContext,LocalService.class));

        mBind = findViewById(R.id.btn_bind);
        mUnbind = findViewById(R.id.btn_unbind);
        mStart = findViewById(R.id.btn_start);
        mStop = findViewById(R.id.btn_stop);
        mStopSelf = findViewById(R.id.btn_stop_self);
        mShowToast = findViewById(R.id.btn_show_toast);

        mBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindService(mIntent,mSC,BIND_AUTO_CREATE);
            }
        });

        mUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIsBind) {
                    unbindService(mSC);
                }
            }
        });

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(mIntent);
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(mIntent);
            }
        });

        mStopSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBoundService != null) {
                    mBoundService.stopSelf();
                }
            }
        });

        mShowToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBoundService != null) {
                    mBoundService.showToast("peter");
                }
                if(mBinder != null) {
                    mBinder.add(2,3);
                    mBinder.startDownload();

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mSC);
    }

    private void test() {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
    }
}
