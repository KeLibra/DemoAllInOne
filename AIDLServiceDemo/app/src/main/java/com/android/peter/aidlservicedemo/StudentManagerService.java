package com.android.peter.aidlservicedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.peter.aidlservicedemo.database.StudentListDaoImpl;
import com.android.peter.aidlservicedemo.bean.Student;

import java.util.List;

public class StudentManagerService extends Service {
    private final static String TAG = "peter.StudentManagerService";
    private final static int FOREGROUND_SERVICE_NOTIFICATION_ID = 20180731;
    private final static String PERMISSION_STUDENT_MANAGER_SERVICE = "com.android.peter.aidlservicedemo.PERMISSION_STUDENT_MANAGER_SERVICE";

    private Context mContext;
    private NotificationManager mNM;
    private RemoteCallbackList<IOnDataChangeListener> mListenerList = new RemoteCallbackList<>();
    private IBinder mService = new IStudentManager.Stub() {
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int checkPermission = checkCallingOrSelfPermission(PERMISSION_STUDENT_MANAGER_SERVICE);
            if( checkPermission != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG,"Do not have permission !");
                return false;
            }

            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if(packages != null && packages.length > 0) {
                packageName = packages[0];
            }

            if (packageName != null && !packageName.startsWith("com.android.peter")) {
                Log.i(TAG,"Package name must be contains \"com.android.peter\" !");
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Student> getStudentList() throws RemoteException {
            Log.d(TAG,"getStudentList");
            return StudentListDaoImpl.getInstance(mContext).getAllStudent();
        }

        @Override
        public long addStudent(ContentValues contentValues) throws RemoteException {
            Log.d(TAG,"addStudent contentValues = " + contentValues);
            long id = StudentListDaoImpl.getInstance(mContext).insert(contentValues);
            // the row ID of the newly inserted row, or -1 if an error occurred
            if(id != -1) {
                notifyDataChanged();
            }

            return id;
        }

        @Override
        public int deletedStudent(String whereClause, String[] whereArgs) throws RemoteException {
            Log.d(TAG,"deletedStudent whereClause = " + whereClause + " , whereArgs = " + whereArgs);
            int num = StudentListDaoImpl.getInstance(mContext).delete(whereClause,whereArgs);
            // the number of rows affected if a whereClause is passed in, 0 otherwise.
            if(num > 0) {
                notifyDataChanged();
            }

            return num;
        }

        @Override
        public int updateStudent(ContentValues contentValues, String whereClause, String[] whereArgs) throws RemoteException {
            Log.d(TAG,"deletedStudent contentValues = " + contentValues + " , whereClause = " + whereClause + " , whereArgs = " + whereArgs);
            int num = StudentListDaoImpl.getInstance(mContext).update(contentValues,whereClause,whereArgs);
            // the number of rows affected
            if(num > 0) {
                notifyDataChanged();
            }

            return num;
        }

        @Override
        public List<Student> queryStudent(String[] columns, String selection,
                                   String[] selectionArgs, String groupBy, String having,
                                   String orderBy, String limit) throws RemoteException {
            Log.d(TAG,"queryStudent columns = " + columns + " , selection = " + selection + " , selectionArgs = " + selectionArgs
                    + " , groupBy = " + groupBy + " , having = " + having + " , orderBy = " + orderBy + " , limit = " + limit);
            return StudentListDaoImpl.getInstance(mContext).query(columns,selection,selectionArgs,groupBy,having,orderBy,limit);
        }

        @Override
        public void registerDataChangeListener(IOnDataChangeListener listener) throws RemoteException {
            mListenerList.register(listener);
        }

        @Override
        public void unregisterDataChangeListener(IOnDataChangeListener listener) throws RemoteException {
            mListenerList.unregister(listener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        sendForegroundService();
    }

    private void sendForegroundService() {
        NotificationChannel channel = new NotificationChannel("service","foreground_service", NotificationManager.IMPORTANCE_DEFAULT);
        mNM.createNotificationChannel(channel);
        Notification.Builder nb = new Notification.Builder(mContext,"service")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("AIDL service")
                .setContentText("AIDL service is running ! ")
                .setOngoing(true);

        startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID,nb.build());
    }

    private void notifyDataChanged() {
        mListenerList.beginBroadcast();
        int N = mListenerList.getRegisteredCallbackCount();
        for(int i = 0 ; i < N ; i++) {
            try {
                mListenerList.getBroadcastItem(i).onDataChange();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mListenerList.finishBroadcast();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return mService;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
