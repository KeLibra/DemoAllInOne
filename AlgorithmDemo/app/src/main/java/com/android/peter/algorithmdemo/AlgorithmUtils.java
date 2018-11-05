package com.android.peter.algorithmdemo;

import android.util.Log;

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

    public static int partition(int[] array, int low, int high) {
        int key = array[low];
        while (low< high) {
            while (low< high && key<=array[high]) {
                high--;
            }

            array[low] = array[high];

            while (low< high && key>=array[low]) {
                low++;
            }

            array[high] = array[low];
        }

        array[high] = key;

        return high;
    }
}
