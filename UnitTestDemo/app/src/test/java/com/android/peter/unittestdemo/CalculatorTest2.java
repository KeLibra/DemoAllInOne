package com.android.peter.unittestdemo;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by  peter on 2018/7/17.
 */

@RunWith(Parameterized.class)
public class CalculatorTest2 {
    private Calculator mCalculator;

    private int expected;
    private int a;

    public CalculatorTest2(int expected, int a, int b) {
        this.expected = expected;
        this.a = a;
        this.b = b;
    }

    private int b;

    @Parameterized.Parameters//创建并返回测试数据
    public static Collection params() {
        return Arrays.asList(new Integer[][] { { 3, 1, 2 }, { 5, 2, 3 } });
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("setUp");
        mCalculator = new Calculator();
    }

    @Test
    public void sum() throws Exception {
        System.out.println("sum a = " + a + " , b = " + b + " , expected = " + expected);
        int result = mCalculator.sum(a,b);
        assertEquals(expected,result);
    }

    @Ignore
    public void subtract() throws Exception {
        System.out.println("subtract");
        int result = mCalculator.subtract(2,1);
        assertEquals(1,result);
    }

    @Ignore
    public void divide() throws Exception {
        System.out.println("divide");
        int result = mCalculator.divide(2,1);
        assertEquals(2,result);
    }

    @Ignore
    public void multiply() throws Exception {
        System.out.println("multiply");
        int result = mCalculator.multiply(1,2);
        assertEquals(2,result);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("tearDown");
    }
}
