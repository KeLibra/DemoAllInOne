package com.android.peter.algorithmdemo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2018/11/5.
 */

public class AlgorithmUtils {
    private final static String TAG = AlgorithmUtils.class.getSimpleName();

    public static void bubbleSort(int[] array) {
        int temp;
        for(int i=0 ; i<array.length-1 ; i++) {
            for (int j=0; j<array.length-1-i ; j++) {
                if(array[j]>array[j+1]) {
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
            Log.d(TAG,"bubbleSort array = " + printResult(array));
        }
    }

    public static void selectionSort(int[] array) {
        int temp;
        for(int i=0 ; i<array.length -1; i++) {
            int k=i;
            for (int j=k+1 ; j<array.length ; j++) {
                if(array[k] > array[j]) {
                    k = j;
                }
            }
            if(k!=i) {
                temp = array[k];
                array[k] = array[i];
                array[i] = temp;
            }

            Log.d(TAG,"selectionSort array = " + printResult(array));
        }
    }

    // 固定的切分方式
    public static int partition(int[] array, int low, int high) {
        int key = array[low];

        while (low< high) {
            // 从后半部分向前扫描
            while (low< high && key<=array[high]) {
                high--;
            }

            array[low] = array[high];

            // 从前半部分向后扫描
            while (low< high && key>=array[low]) {
                low++;
            }

            array[high] = array[low];
        }

        array[high] = key;

        return high;
    }


    public static void fastSort(int[] array, int low, int high) {
        if(low>= high) {
            return;
        }

        int index = partition(array,low, high);
        fastSort(array,low,index-1);
        fastSort(array,index+1, high);
        Log.d(TAG,"fastSort array = " + printResult(array));
    }

    public static String printResult(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int integer : array) {
            sb.append(integer);
        }

        return sb.toString();
    }

    public static void toutiaoSort(int[] array) {
//        int[] array = {10, -2, 5, 8, -4, 2, -3, 7, 12, -88, -23, 35};

        int k=0;
        int temp;
        for (int i=0 ; i<array.length ; i++) {
            if(array[i] < 0) {
                if(i!=0) {
                    temp = array[i];
                    array[i] = array[k];
                    array[k] = temp;
                }
                k++;
            }
        }

        Log.d(TAG,"toutiaoSort array = " + printResult(array));
    }

    public static String huiwen(String string) {

        if(string == null) {
            return null;
        }
        if(string.length() < 2) {
            return string;
        }

        String result = "";
        String temp = "";

        for(int i = 0; i< string.length()-1; i++) {
            if(string.charAt(i) == (string.charAt(i+1))) {
                if(i == 0) {
                    result = string.substring(0,1);
                } else if(i+1 == string.length()) {
                    result = string.substring(string.length()-2, string.length()-1);
                } else {
                    for(int j = 1; j<i && i< string.length()-1-j; j++) {
                        if(!(string.charAt(i-j) == string.charAt(i+1+j))) {
                            temp = string.substring(i+1-j,i+1+j);
                            break;
                        }
                    }

                    if(result.length() < temp.length()) {
                        result = temp;
                    }
                }
            }
        }

        Log.d(TAG,"huiwen result = " + result);
        return result;
    }
}
