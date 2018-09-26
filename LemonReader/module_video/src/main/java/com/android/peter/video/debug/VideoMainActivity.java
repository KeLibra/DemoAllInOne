package com.android.peter.video.debug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.peter.video.R;
import com.android.peter.video.VideoFragment;

public class VideoMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_video_main,new VideoFragment())
                .commit();
    }
}
