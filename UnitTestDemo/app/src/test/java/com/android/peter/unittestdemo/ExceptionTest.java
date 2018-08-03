package com.android.peter.unittestdemo;

import org.junit.Test;

/**
 * Created by  peter on 2018/7/17.
 */

public class ExceptionTest {
    String s;
    @Test(expected = NullPointerException.class)
    public void testException() {
        s.length();
    }
}
