package com.android.peter.genericsdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2018/8/21.
 */

public class GenericsTest {
    public static void main() {
        List<String> name = new ArrayList<String>();
        List<Integer> age = new ArrayList<Integer>();
        List<Number> number = new ArrayList<Number>();

        name.add("icon");
        age.add(18);
        number.add(314);

        getData(name);
        getData(age);
        getData(number);
    }

    public static void getData(List<?> data) {
        System.out.println("data :" + data.get(0));
    }
}
