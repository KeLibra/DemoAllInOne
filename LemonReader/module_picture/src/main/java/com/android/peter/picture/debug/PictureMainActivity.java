package com.android.peter.picture.debug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.peter.picture.PictureFragment;
import com.android.peter.picture.R;

public class PictureMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_picture_main,new PictureFragment())
                .commit();
    }
}
