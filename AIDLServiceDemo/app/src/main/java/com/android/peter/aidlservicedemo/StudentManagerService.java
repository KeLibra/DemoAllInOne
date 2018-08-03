package com.android.peter.aidlservicedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.peter.aidlservicedemo.database.StudentListDaoImpl;
import com.android.peter.aidlservicedemo.bean.Student;

import java.util.List;

public class StudentManagerService extends Service {
    private final static String TAG = "peter.StudentManagerService";
    private final static int FOREGROUND_SERVICE_NOTIFICATION_ID = 20180731;

    private Context mContext;
    private NotificationManager mNM;
    private IBinder mService = new IStudentManager.Stub() {
        @Override
        public List<Student> getStudentList() throws RemoteException {
            Log.d(TAG,"getStudentList");
            return StudentListDaoImpl.getInstance(mContext).getAllStudent();
        }

        @Override
        public long addStudent(ContentValues contentValues) throws RemoteException {
            Log.d(TAG,"addStudent contentValues = " + contentValues);
            return StudentListDaoImpl.getInstance(mContext).insert(contentValues);
        }

        @Override
        public int deletedStudent(String whereClause, String[] whereArgs) throws RemoteException {
            Log.d(TAG,"deletedStudent whereClause = " + whereClause + " , whereArgs = " + whereArgs);
            return StudentListDaoImpl.getInstance(mContext).delete(whereClause,whereArgs);
        }

        @Override
        public int updateStudent(ContentValues contentValues, String whereClause, String[] whereArgs) throws RemoteException {
            Log.d(TAG,"deletedStudent contentValues = " + contentValues + " , whereClause = " + whereClause + " , whereArgs = " + whereArgs);
            return StudentListDaoImpl.getInstance(mContext).update(contentValues,whereClause,whereArgs);
        }

        @Override
        public List<Student> queryStudent(String[] columns, String selection,
                                   String[] selectionArgs, String groupBy, String having,
                                   String orderBy, String limit) throws RemoteException {
            Log.d(TAG,"queryStudent columns = " + columns + " , selection = " + selection + " , selectionArgs = " + selectionArgs + " , groupBy = " + groupBy
                + " , having = " + having + " , orderBy = " + orderBy + " , limit = " + limit);
            return StudentListDaoImpl.getInstance(mContext).query(columns,selection,selectionArgs,groupBy,having,orderBy,limit);
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
