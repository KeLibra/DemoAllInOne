package com.android.peter.unittestdemo;

import org.junit.Test;

/**
 * Created by  peter on 2018/7/17.
 */

public class TimeTest {
    @Test(timeout=1000)
    public void testTime() throws Exception {
        System.out.println("test timeout");
        Thread.currentThread().sleep(500);
    }
}
