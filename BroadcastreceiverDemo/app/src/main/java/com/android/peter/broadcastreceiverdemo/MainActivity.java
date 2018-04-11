package com.android.peter.broadcastreceiverdemo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "BRDemo";

    private Context mContext;
    private Button mNormalBtn;
    private Button mOrderedBtn;
    private Button mStickyBtn;
    private Button mLocalBtn;
    private IntentFilter mIntentFilter = new IntentFilter();
    private MyBroadcastReceiver mBroadcastReceiver = new MyBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        mNormalBtn = findViewById(R.id.btn_normal_broadcast);
        mNormalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.android.peter.STATIC_BROADCASTRECEIVER");
                intent.addCategory("android.intent.category.DEFAULT" );

                sendBroadcast(intent);
            }
        });
        mOrderedBtn = findViewById(R.id.btn_ordered_broadcast);
        mOrderedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mStickyBtn = findViewById(R.id.btn_sticky_broadcast);
        mStickyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mLocalBtn = findViewById(R.id.btn_local_broadcast);
        mLocalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mIntentFilter.addAction("com.android.peter.STATIC_BROADCASTRECEIVER");
        mIntentFilter.addCategory("android.intent.category.DEFAULT");
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mBroadcastReceiver,mIntentFilter);

        ArrayMap<String,String> arrayMap = new ArrayMap<>();
        arrayMap.put("a","1");
        arrayMap.put("b","2");
        arrayMap.put("c","3");
        arrayMap.put("d","4");

        Log.d(TAG, "Contains ? " + arrayMap.get("a"));
        Log.d(TAG, "Contains ? " + arrayMap.get("e"));
        Log.d(TAG, "Contains ? " + (arrayMap.containsValue("4") ? "Y" : "N"));
        Log.d(TAG, "Contains ? " + (arrayMap.containsValue("0") ? "Y" : "N"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
