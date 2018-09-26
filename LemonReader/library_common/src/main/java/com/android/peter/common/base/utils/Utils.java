package com.android.peter.common.base.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by peter on 2018/9/21.
 */

public class Utils {
    private volatile static Context mAppContext;

    private Utils() {
        throw new UnsupportedOperationException("You can't instantiate me!");
    }

    public static void init(Context context) {
        mAppContext = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (mAppContext != null) {
            return mAppContext;
        }

        throw new NullPointerException("You should init it first!");
    }

    /**
     * 判断App是否是Debug版本
     */
    public static boolean isDebug() {
        if (TextUtils.isEmpty(mAppContext.getPackageName())) {
            return false;
        }
        try {
            PackageManager pm = mAppContext.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(mAppContext.getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
