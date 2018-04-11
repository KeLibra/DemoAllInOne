package com.android.peter.activitydemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "Activity";

    private final static Boolean DEBUG_ACTIVITY_PERIOD = true;
    private final static Boolean DEBUG_ACTIVITY_MENU = false;
    private final static Boolean DEBUG_ACTIVITY_KEY = false;
    private final static Boolean DEBUG_ACTIVITY_WINDOW = false;

    private Context mContext;
    private TextView mTextView;

    //Activity生命周期
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onCreate");

        if(savedInstanceState != null) {
            if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onCreate " + savedInstanceState.getString("msg","null"));
        }

        mTextView = findViewById(R.id.tv_hello_world);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SECOND_ACTIVITY");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//                startActivity(intent);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onDestroy");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onActivityResult requestCode = "
                + requestCode + " , resultCode = " + resultCode + ", data = " + (data != null ? data.getStringExtra("msg") : "null"));
    }

    //保存和恢复临时状态
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onSaveInstanceState");
        outState.putString("msg","Android");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onRestoreInstanceState");
        if(savedInstanceState != null) {
            if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onRestoreInstanceState " + savedInstanceState.getString("msg","null"));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(DEBUG_ACTIVITY_PERIOD) Log.d(TAG,"onConfigurationChanged");
    }

    //Window
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(DEBUG_ACTIVITY_WINDOW) Log.d(TAG,"onAttachedToWindow");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(DEBUG_ACTIVITY_WINDOW) Log.d(TAG,"onAttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(DEBUG_ACTIVITY_WINDOW) Log.d(TAG,"onDetachedFromWindow");
    }

    //按键key
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(DEBUG_ACTIVITY_KEY) Log.d(TAG,"onKeyDown");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(DEBUG_ACTIVITY_KEY) Log.d(TAG,"onKeyUp");
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(DEBUG_ACTIVITY_KEY) Log.d(TAG,"onBackPressed");
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if(DEBUG_ACTIVITY_KEY) Log.d(TAG,"onKeyLongPress");
        return super.onKeyLongPress(keyCode, event);
    }

    //菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(DEBUG_ACTIVITY_MENU) Log.d(TAG,"onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(DEBUG_ACTIVITY_MENU) Log.d(TAG, "onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(DEBUG_ACTIVITY_MENU) Log.d(TAG,"onOptionsItemSelected");
        switch(item.getItemId()) {
            case R.id.option1:
                return true;
            case R.id.option2:
                return true;
            case R.id.option3:
                return true;
            default:
                //do nothing
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        if(DEBUG_ACTIVITY_MENU) Log.d(TAG, "onOptionsMenuClosed");
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(DEBUG_ACTIVITY_MENU) Log.d(TAG, "onMenuOpened");
        return super.onMenuOpened(featureId, menu);
    }


}
