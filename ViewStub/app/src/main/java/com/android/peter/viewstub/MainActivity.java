package com.android.peter.viewstub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private ViewStub mViewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewStub = findViewById(R.id.view_stub);
        mViewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub viewStub, View view) {
                Log.d(TAG,"onInflate");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mViewStub.setVisibility(View.VISIBLE);
        View layoutView = mViewStub.inflate();
        TextView tv = layoutView.findViewById(R.id.tv_textview);
        tv.setText("aaaaa");
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
