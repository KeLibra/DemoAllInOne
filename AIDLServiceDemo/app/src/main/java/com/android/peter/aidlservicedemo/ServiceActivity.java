package com.android.peter.aidlservicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        startMyService();
    }

    private void startMyService() {
        startService(new Intent(this,StudentManagerService.class));
    }
}
