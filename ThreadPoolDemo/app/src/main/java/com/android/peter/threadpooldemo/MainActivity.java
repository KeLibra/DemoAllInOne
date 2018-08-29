package com.android.peter.threadpooldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "peter.log.MainActivity";

    @BindView(R.id.btn_fixed_thread_pool)
    Button mFixedButton;
    @BindView(R.id.btn_cached_thread_pool)
    Button mCachedButton;
    @BindView(R.id.btn_scheduled_thread_pool)
    Button mScheduledButton;
    @BindView(R.id.btn_single_thread_executor)
    Button mSingleButton;
    @BindView(R.id.btn_custom_thread_pool)
    Button mCustomButton;

    @OnClick(R.id.btn_fixed_thread_pool)
    void executeFixedThreadPool() {
        WorkTask task;
        for (int i=0; i<10; i++) {
            task = new WorkTask("FixedThreadPool",i+1);
            mFixedThreadPool.execute(task);
        }
    }
    @OnClick(R.id.btn_cached_thread_pool)
    void executeCachedThreadPool() {
        WorkTask task;
        for (int i=0; i<10; i++) {
            task = new WorkTask("CacheThreadPool",i+1);
            mCacheThreadPool.execute(task);
        }
    }
    @OnClick(R.id.btn_scheduled_thread_pool)
    void executeScheduledThreadPool() {
        WorkTask task;
        for (int i=0; i<10; i++) {
            task = new WorkTask("ScheduledThreadPool",i+1);
            mScheduledThreadPool.execute(task);
        }
    }
    @OnClick(R.id.btn_single_thread_executor)
    void executeSingleThreadExecutor() {
        WorkTask task;
        for (int i=0; i<10; i++) {
            task = new WorkTask("SingleThreadExecutor",i+1);
            mSingleThreadExecutor.execute(task);
        }
    }
    @OnClick(R.id.btn_custom_thread_pool)
    void executeCustomThreadPool() {
        WorkTask task;
        for (int i=0; i<10; i++) {
            task = new WorkTask("CustomThreadPool",i+1);
            mCustomThreadPool.execute(task);
        }
    }

    private Unbinder mUnBinder;
    private ExecutorService mFixedThreadPool = Executors.newFixedThreadPool(5);
    private ExecutorService mCacheThreadPool = Executors.newCachedThreadPool();
    private ScheduledExecutorService mScheduledThreadPool = Executors.newScheduledThreadPool(5);
    private ExecutorService mSingleThreadExecutor = Executors.newSingleThreadExecutor();
    private ThreadPoolExecutor mCustomThreadPool = new ThreadPoolExecutor(5, 10,
            30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(3), new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            Log.i(TAG,"rejectedExecution tag = " + ((WorkTask)r).tag + " , id = " + ((WorkTask)r).id);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnBinder = ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
/*        WorkTask task = new WorkTask("ScheduledThreadPool",20180829);
        //延迟2000后执行runnable
        mScheduledThreadPool.schedule(task,2000, TimeUnit.MILLISECONDS);
        //延迟10后，每隔1000执行一次runnable
        mScheduledThreadPool.scheduleAtFixedRate(task,10,1000,TimeUnit.MILLISECONDS);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFixedThreadPool.shutdown();
        mCacheThreadPool.shutdown();
        mScheduledThreadPool.shutdown();
        mSingleThreadExecutor.shutdown();
        mCustomThreadPool.shutdown();
        if(mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    private class WorkTask implements Runnable {
        private String tag;
        private int id;

        public WorkTask(String tag, int id) {
            this.tag = tag;
            this.id = id;
        }

        @Override
        public void run() {
            Log.i(TAG,"ThreadPool + " + tag + " , Task " + id + " begin ");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            Log.i(TAG,"ThreadPool + " + tag + " , Task " + id + " end ");
        }
    }
}
