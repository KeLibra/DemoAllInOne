package com.android.peter.genericsdemo;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peter on 2018/8/20.
 */

// 泛型类
public class GenericsClass<K,V> {
    private final static String TAG = "peter.log.GenericsClass";

    private Map<K,V> mMap = new HashMap<>();
    private List<GenericsInterface<K,V>> mCallBackList = new ArrayList<>();

    // 泛型方法
    public void add(K key,V value) {
        Log.d(TAG,"add key = " + key + " , value = " + value);
        mMap.put(key,value);
        for(GenericsInterface<K,V> callback: mCallBackList) {
            callback.dataChanged(key,value);
        }
    }

    // 泛型方法
    public V get(K key) {
        Log.d(TAG,"get key = " + key + " , value = " + mMap.get(key));
        return mMap.get(key);
    }

    // 泛型方法
    public void addCallback(GenericsInterface<K,V> callback) {
        mCallBackList.add(callback);
    }

    // 泛型方法
    public void removeCallback(GenericsInterface<K,V> callback) {
        if(mCallBackList.contains(callback)) {
            mCallBackList.remove(callback);
        }
    }

    // 泛型接口
    interface GenericsInterface<K,V> {
        // 泛型方法
        void dataChanged(K key,V value);
    }
}
