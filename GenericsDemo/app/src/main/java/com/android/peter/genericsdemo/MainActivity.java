package com.android.peter.genericsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "peter.log.MainActivity";
    private GenericsClass<String,String> mGenericsClass = new GenericsClass<>();
    private GenericsClass.GenericsInterface<String,String> mGenericsInterface = new GenericsClass.GenericsInterface<String, String>() {
        @Override
        public void dataChanged(String key, String value) {
            Log.d(TAG," dataChanged key = " + key + " , value = " + value);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGenericsClass.addCallback(mGenericsInterface);
        mGenericsClass.add("peter","https://www.jianshu.com/u/2d3edce151d3");
        mGenericsClass.get("peter");
        GenericsTest.main();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGenericsClass.removeCallback(mGenericsInterface);
    }
}
