package com.android.peter.aidlclientdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.peter.aidlservicedemo.IStudentManager;
import com.android.peter.aidlservicedemo.bean.Student;

import java.util.List;

public class ClientActivity extends AppCompatActivity {
    private final static String TAG = "peter.ClientActivity";

    private final static String SERVICE_PACKAGE_NAME = "com.android.peter.aidlservicedemo";
    private final static String SERVICE_CLASS_NAME = "com.android.peter.aidlservicedemo.StudentManagerService";
    private IStudentManager mService;
    private ServiceConnection mSC = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"onServiceConnected name = " + name);
            mService = IStudentManager.Stub.asInterface(service);

            try {
                mService.addStudent(new Student("peter",true,33,100));
                mService.addStudent(new Student("lemon",false,30,100));

                List<Student> studentList = mService.getStudentList();
                Log.d(TAG,"studentList = " + studentList.toString());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(SERVICE_PACKAGE_NAME,SERVICE_CLASS_NAME));
        bindService(intent,mSC, Context.BIND_AUTO_CREATE);
    }
}
