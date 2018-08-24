package com.android.peter.reflectdemo;

import android.content.Context;
import android.os.IBinder;

import java.lang.reflect.Method;

/**
 * Created by peter on 2018/8/22.
 */

public class ReflectClass {
    private final static String TAG = "ReflectClass";

    public static int getZenMode() {
        int zenMode = -1;
        try {
            Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
            Method mGetService = cServiceManager.getMethod("getService", String.class);
            Object oNotificationManagerService = mGetService.invoke(null, Context.NOTIFICATION_SERVICE);
            Class<?> cINotificationManagerStub = Class.forName("android.app.INotificationManager$Stub");
            Method mAsInterface = cINotificationManagerStub.getMethod("asInterface",IBinder.class);
            Object oINotificationManager = mAsInterface.invoke(null,oNotificationManagerService);
            Method mGetZenMode = cINotificationManagerStub.getMethod("getZenMode");
            zenMode = (int) mGetZenMode.invoke(oINotificationManager);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return zenMode;
    }

    public static void shutDown() {
        try {
            Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
            Method mGetService = cServiceManager.getMethod("getService",String.class);
            Object oPowerManagerService = mGetService.invoke(null,Context.POWER_SERVICE);
            Class<?> cIPowerManagerStub = Class.forName("android.os.IPowerManager$Stub");
            Method mShutdown = cIPowerManagerStub.getMethod("shutdown",boolean.class,String.class,boolean.class);
            Method mAsInterface = cIPowerManagerStub.getMethod("asInterface",IBinder.class);
            Object oIPowerManager = mAsInterface.invoke(null,oPowerManagerService);
            mShutdown.invoke(oIPowerManager,true,null,true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void shutdownOrReboot(final boolean shutdown, final boolean confirm) {
        try {
            Class<?> ServiceManager = Class.forName("android.os.ServiceManager");
            // 获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
            // 调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
            // 获得IPowerManager.Stub类
            Class<?> cStub = Class.forName("android.os.IPowerManager$Stub");
            // 获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            // 调用asInterface方法获取IPowerManager对象
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            if (shutdown) {
                // 获得shutdown()方法
                Method shutdownMethod = oIPowerManager.getClass().getMethod(
                        "shutdown", boolean.class, String.class, boolean.class);
                // 调用shutdown()方法
                shutdownMethod.invoke(oIPowerManager, confirm, null, false);
            } else {
                // 获得reboot()方法
                Method rebootMethod = oIPowerManager.getClass().getMethod("reboot",
                        boolean.class, String.class, boolean.class);
                // 调用reboot()方法
                rebootMethod.invoke(oIPowerManager, confirm, null, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
