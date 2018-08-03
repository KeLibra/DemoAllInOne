package com.android.peter.unittestdemo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by  peter on 2018/7/17.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ CalculatorTest.class, CalculatorTest1.class })//被测试类
public class SuiteTest {

}
