package com.android.peter.user.debug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.peter.user.R;
import com.android.peter.user.UserFragment;

public class UserMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_user_main,new UserFragment())
                .commit();
    }
}
