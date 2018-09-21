package com.android.peter.common.base;

import android.app.Application;

import com.android.peter.common.utils.Utils;

/**
 * desc：组件开发中我们的application是放在debug包下的，进行集成合并时是需要移除掉的，
 * 所以组件module中不能使用debug包下的application的context，
 * 因此组件中必须通过Utils.getContext()方法来获取全局 Context
 */

public class BaseApplication extends Application {
    private static BaseApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Utils.init(this);
    }

    public static BaseApplication getApplication(){
        return mApplication;
    }
}
