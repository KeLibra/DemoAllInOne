package com.android.peter.fragmentdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "peter.log." + MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"onCreate");
        Log.i(TAG,"onCreate begin");
        super.onCreate(savedInstanceState);
        if(getSupportFragmentManager().findFragmentByTag("CustomFragment") == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(new CustomFragment(),"CustomFragment")
                    .commit();
        }
        Log.i(TAG,"onCreate end");
    }

    @Override
    protected void onRestart() {
        Log.e(TAG,"onRestart");
        Log.i(TAG,"onRestart begin");
        super.onRestart();
        Log.i(TAG,"onRestart end");
    }

    @Override
    protected void onStart() {
        Log.e(TAG,"onStart");
        Log.i(TAG,"onStart begin");
        super.onStart();
        Log.i(TAG,"onStart end");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e(TAG,"onRestoreInstanceState");
        Log.i(TAG,"onRestoreInstanceState begin");
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) {
            String msg = savedInstanceState.getString("msg");
            Log.d(TAG,"onRestoreInstanceState msg = " + msg);
           CustomFragment customFragment = (CustomFragment) getSupportFragmentManager().findFragmentByTag("CustomFragment");
           if(customFragment != null) {
               customFragment.setArguments(savedInstanceState);
           }
        }
        Log.i(TAG,"onRestoreInstanceState end");
    }

    @Override
    protected void onResume() {
        Log.e(TAG,"onResume");
        Log.i(TAG,"onResume begin");
        super.onResume();
        Log.i(TAG,"onResume end");
    }

    @Override
    protected void onPause() {
        Log.e(TAG,"onPause");
        Log.i(TAG,"onPause begin");
        super.onPause();
        Log.i(TAG,"onPause end");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e(TAG,"onSaveInstanceState");
        Log.i(TAG,"onSaveInstanceState begin");
        super.onSaveInstanceState(outState);
        outState.putString("msg","data");
        Log.i(TAG,"onSaveInstanceState end");
    }

    @Override
    protected void onStop() {
        Log.e(TAG,"onStop");
        Log.i(TAG,"onStop begin");
        super.onStop();
        Log.i(TAG,"onStop end");
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG,"onDestroy");
        Log.i(TAG,"onDestroy begin");
        super.onDestroy();
        Log.i(TAG,"onDestroy end");
    }
}
