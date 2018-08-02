package com.android.peter.aidlservicedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.android.peter.aidlservicedemo.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentManagerService extends Service {
    private final static String TAG = "peter.StudentManagerService";
    private final static int FOREGROUND_SERVICE_NOTIFICATION_ID = 20180731;

    private Context mContext;
    private NotificationManager mNM;
    private List<Student> mStudentList = new ArrayList<>();
    private IBinder mService = new IStudentManager.Stub() {
        @Override
        public List<Student> getStudentList() throws RemoteException {

            return mStudentList;
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            mStudentList.add(student);
        }

        @Override
        public void deletedStudent(Student student) throws RemoteException {
            if(mStudentList.contains(student)) {
                mStudentList.remove(student);
            }
        }

        @Override
        public void updateStudent(Student student) throws RemoteException {

        }

        @Override
        public Student queryStudent(String name) throws RemoteException {
            return null;
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
