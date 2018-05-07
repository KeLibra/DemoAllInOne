package com.android.peter.handlerdemo;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private final static int MSG_REFRESH_UI = 20180503;

    private TextView mTextView;

    private Handler mHandler = new MyHandler(this);
//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            /* 获取数据，更新UI */
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"onCreate");

        mTextView = findViewById(R.id.tv_handler_refresh_UI);

                /* 子线程更新UI */
        new Thread(new Runnable() {
            @Override
            public void run() {
                /* 更新UI */
//                SystemClock.sleep(2000);
                mTextView.setText("I'm from child thread !");
                Log.d(TAG,"current thread = " + Thread.currentThread().getName());
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        Log.d(TAG,"current thread = " + Thread.currentThread().getName());
        /* 子线程更新UI */
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                *//* 更新UI *//*
//                SystemClock.sleep(2000);
                mTextView.setText("I'm from child thread !");
                Log.d(TAG,"current thread = " + Thread.currentThread().getName());
            }
        }).start();*/

        /* post方法更新UI */
        new Thread(new Runnable() {
            @Override
            public void run() {
                /* 耗时操作 */
                SystemClock.sleep(5000);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        /* 更新UI */
                        mTextView.setText("I'm from post method !");
                    }
                });
            }
        }).start();

        /* sendMessage方法更新UI */
        new Thread(new Runnable() {
            @Override
            public void run() {
                /* 耗时操作 */
                SystemClock.sleep(10000);

                //从全局池中返回一个message实例，避免多次创建message（如new Message）
                Message msg = Message.obtain();
                msg.obj = "I'm from sendMessage method !";
                msg.what = MSG_REFRESH_UI;

                /* 发送UI更新消息 */
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<Activity> mActivityReference;

        MyHandler(Activity activity) {
            this.mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REFRESH_UI:
                    /* 更新UI */
                    MainActivity mainActivity = (MainActivity) mActivityReference.get(); //获取弱引用队列中的activity
                    String data = (String) msg.obj;
                    mainActivity.mTextView.setText(data);
                    break;
                default:
                    //do nothing
            }
        }
    }
}
