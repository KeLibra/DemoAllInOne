package com.android.peter.reflectdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "peter.log.ReflectDemo";

    private Button mReboot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReboot = findViewById(R.id.btn_shutdown);
        mReboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReflectClass.shutDown();
//                ReflectClass.shutdownOrReboot(true,true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG," zenmode = " + ReflectClass.getZenMode());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
