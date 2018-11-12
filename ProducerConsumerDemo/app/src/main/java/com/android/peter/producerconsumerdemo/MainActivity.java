package com.android.peter.producerconsumerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    BlockingQueueUtil mBlockQueue = new BlockingQueueUtil();
    WaitNotifyUtil mWaitNotify = new WaitNotifyUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        mBlockQueue.main();
        mWaitNotify.main();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBlockQueue != null) {
            mBlockQueue.shutdown();
        }

        if(mWaitNotify != null) {
            mWaitNotify.shutdown();
        }
    }
}
