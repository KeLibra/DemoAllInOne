package com.android.peter.jsoupdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by peter on 2018/9/18.
 */

public class JianShuDetailActivity extends AppCompatActivity {
    private final static String TAG = "peter.log." + JianShuDetailActivity.class.getSimpleName();

    private final static String JIAN_SHU_BASE_URL = "https://www.jianshu.com";

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String mUrl = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jian_shu_detail);
        Intent intent = getIntent();
        if(intent == null) {
            return;
        }

        mUrl = intent.getStringExtra("url");
        Log.d(TAG,"url = " + mUrl);
        mWebView = findViewById(R.id.wv_detail);
        mProgressBar = findViewById(R.id.cl_progress_bar);
        // allow load image in web view
        mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress == 100) {
                    mProgressBar.setProgress(100);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.GONE);
                        }
                    },300);
                } else {
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        mWebView.loadUrl(JIAN_SHU_BASE_URL + mUrl);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
