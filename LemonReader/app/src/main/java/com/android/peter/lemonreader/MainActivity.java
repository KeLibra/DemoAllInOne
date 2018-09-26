package com.android.peter.lemonreader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.android.peter.news.NewsFragment;
import com.android.peter.picture.PictureFragment;
import com.android.peter.user.UserFragment;
import com.android.peter.video.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private NewsFragment mNewsFragment = new NewsFragment();
    private PictureFragment mPictureFragment = new PictureFragment();
    private VideoFragment mVideoFragment = new VideoFragment();
    private UserFragment mUserFragment = new UserFragment();
    private Fragment mCurrentFragment = new Fragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    switchFragment(mNewsFragment);
                    return true;
                case R.id.navigation_picture:
                    switchFragment(mPictureFragment);
                    return true;
                case R.id.navigation_video:
                    switchFragment(mVideoFragment);
                    return true;
                case R.id.navigation_user:
                    switchFragment(mUserFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchFragment(mNewsFragment);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction
                    .hide(mCurrentFragment)
                    .add(R.id.cl_main, targetFragment)
                    .commit();
        } else {
            transaction
                    .hide(mCurrentFragment)
                    .show(targetFragment)
                    .commit();
        }
        mCurrentFragment = targetFragment;
    }
}
