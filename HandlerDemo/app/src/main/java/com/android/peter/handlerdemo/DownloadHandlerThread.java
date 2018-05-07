package com.android.peter.handlerdemo;

import android.os.HandlerThread;

/**
 * Created by peter on 2018/5/4.
 */

public class DownloadHandlerThread extends HandlerThread {
    public DownloadHandlerThread(String name) {
        super(name);
    }

    public DownloadHandlerThread(String name, int priority) {
        super(name, priority);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
    }

    @Override
    public boolean quit() {
        return super.quit();
    }

    @Override
    public boolean quitSafely() {
        return super.quitSafely();
    }
}
