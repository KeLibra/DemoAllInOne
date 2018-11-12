package com.android.peter.producerconsumerdemo;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by peter on 2018/11/12.
 */

public class BlockingQueueUtil {
    private static final String TAG = BlockingQueueUtil.class.getSimpleName();

    private int mCount = 0;
    private boolean mIsRunning = true;
    private ExecutorService mService = Executors.newCachedThreadPool();
    private BlockingQueue<Integer> mQueue = new LinkedBlockingQueue<>(10);

    public void main() {
        mService.execute(new Producer(mQueue));
        mService.execute(new Consumer(mQueue));
    }

    public void shutdown() {
        if(mService!=null && !mService.isShutdown()) {
            mIsRunning = false;
            mService.shutdown();
        }
    }

    public class Producer implements Runnable {
        private BlockingQueue<Integer> queue;
        public Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (mIsRunning) {
                Log.d(TAG,"Producer mCount = " + mCount);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    queue.put(mCount);
                    mCount++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class Consumer implements Runnable {
        private BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (mIsRunning) {
                Log.d(TAG,"Consumer mCount = " + mCount);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
