package com.android.peter.algorithmdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private int[] array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        array = new int[]{5,2,8,4,9,1};
        AlgorithmUtils.bubbleSort(array);
        array = new int[]{5,2,8,4,9,1};
        AlgorithmUtils.selectionSort(array);
        array = new int[]{5,2,8,4,9,1};
        AlgorithmUtils.fastSort(array,0,array.length-1);
        array = new int[]{10, -2, 5, 8, -4, 2, -3, 7, 12, -88, -23, 35};
        AlgorithmUtils.toutiaoSort(array);
        String huiwen = "3214156654123321789";
        AlgorithmUtils.huiwen(huiwen);
    }
}
