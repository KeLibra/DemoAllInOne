package com.android.peter.producerconsumerdemo;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by peter on 2018/11/12.
 */

public class WaitNotifyUtil {
    private static final String TAG = WaitNotifyUtil.class.getSimpleName();

    private Object mLock = new Object();
    private int mCount = 0;
    private ExecutorService mService = Executors.newCachedThreadPool();
    private boolean mIsRunning = true;

    public void main() {
        mService.execute(new Producer());
        mService.execute(new Consumer());
    }

    public void shutdown() {
        if(mService != null && !mService.isShutdown()) {
            mIsRunning = false;
            mService.shutdown();
        }
    }

    public class Producer implements Runnable {
        @Override
        public void run() {
            while (mIsRunning) {
                try {
                    Thread.sleep((long) (200 + Math.random()/200));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (mLock) {
                    if(mCount == 10) {
                        try {
                            mLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mCount++;
                        mLock.notifyAll();
                        Log.d(TAG,"Producer mCount = " + mCount);
                    }
                }
            }
        }
    }

    public class Consumer implements Runnable {
        @Override
        public void run() {
            while (mIsRunning) {
                try {
                    Thread.sleep((long) (500 + Math.random()/500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (mLock) {
                    if(mCount == 0) {
                        try {
                            mLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mCount--;
                        mLock.notifyAll();
                        Log.d(TAG,"Consumer mCount = " + mCount);
                    }
                }
            }
        }
    }
}
