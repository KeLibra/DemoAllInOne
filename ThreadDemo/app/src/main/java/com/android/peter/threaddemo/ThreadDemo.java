package com.android.peter.threaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ThreadDemo extends AppCompatActivity {
    private final static String TAG = ThreadDemo.class.getSimpleName();

    private Object mLock = new Object();
    private Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            synchronized (mLock) {
                Log.i(TAG,"state 1 = " + mThread.getState());
                try {
                    mLock.wait(10 * 1000);
                    Log.i(TAG,"state 2 = " + mThread.getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"state 3 = " + mThread.getState());
        mThread.start();
        Log.i(TAG,"state = 4 " + mThread.getState());

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        synchronized(mLock) {
            Log.i(TAG,"state = 5 " + mThread.getState());
            mLock.notify();
            Log.i(TAG,"state = 6 " + mThread.getState());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"state = 7 " + mThread.getState());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

