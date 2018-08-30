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
        getSupportFragmentManager().beginTransaction()
                .add(new CustomFragment(),"CustomFragment")
                .commit();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
