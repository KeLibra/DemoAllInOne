package com.android.peter.unittestdemo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by  peter on 2018/7/17.
 */

public class CalculatorTest1 {
    private Calculator mCalculator;

    @Before
    public void setUp() throws Exception {
        System.out.println("setUp 1");
        mCalculator = new Calculator();
    }

    @Test
    public void sum() throws Exception {
        System.out.println("sum");
        int result = mCalculator.sum(1,2);
        assertEquals(3,result);
    }

    @Test
    public void subtract() throws Exception {
        System.out.println("subtract");
        int result = mCalculator.subtract(2,1);
        assertEquals(1,result);
    }

    @Test
    public void divide() throws Exception {
        System.out.println("divide");
        int result = mCalculator.divide(2,1);
        assertEquals(2,result);
    }

    @Test
    public void multiply() throws Exception {
        System.out.println("multiply");
        int result = mCalculator.multiply(1,2);
        assertEquals(2,result);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("tearDown 1");
    }
}
