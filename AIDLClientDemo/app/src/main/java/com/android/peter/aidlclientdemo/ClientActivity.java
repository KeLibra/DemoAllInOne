package com.android.peter.aidlclientdemo;

import android.content.ComponentName;
import android.content.ContentValues;
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
import com.android.peter.aidlservicedemo.database.StudentTable;

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

            insertStudent();
            queryStudent();
            updateStudent();
            deleteStudent();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG,"onServiceDisconnected name = " + name);
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

    private void insertStudent() {
        Log.i(TAG,"insertStudent");
        ContentValues peter = new ContentValues();
        peter.put(StudentTable.COLUMN_NAME,"peter");
        peter.put(StudentTable.COLUMN_GENDER,0);
        peter.put(StudentTable.COLUMN_AGE,33);
        peter.put(StudentTable.COLUMN_SCORE,100);

        ContentValues lemon = new ContentValues();
        lemon.put(StudentTable.COLUMN_NAME,"lemon");
        lemon.put(StudentTable.COLUMN_GENDER,1);
        lemon.put(StudentTable.COLUMN_AGE,30);
        lemon.put(StudentTable.COLUMN_SCORE,100);

        ContentValues baoyamei = new ContentValues();
        baoyamei.put(StudentTable.COLUMN_NAME,"baoyamei");
        baoyamei.put(StudentTable.COLUMN_GENDER,1);
        baoyamei.put(StudentTable.COLUMN_AGE,30);
        baoyamei.put(StudentTable.COLUMN_SCORE,90);

        try {
            mService.addStudent(peter);
            mService.addStudent(lemon);
            mService.addStudent(baoyamei);

            List<Student> studentList = mService.getStudentList();
            Log.i(TAG,"studentList = " + studentList.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void queryStudent() {
        Log.i(TAG,"queryStudent");
        try{
            List<Student> queryList = mService.queryStudent(StudentTable.TABLE_COLUMNS,StudentTable.COLUMN_AGE + "=?",
                    new String[]{"30"},null,null,null,null);
            Log.i(TAG,"queryList = " + queryList.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateStudent() {
        Log.i(TAG,"updateStudent");
        ContentValues lemon = new ContentValues();
        lemon.put(StudentTable.COLUMN_SCORE,100);

        try {
            mService.updateStudent(lemon,StudentTable.COLUMN_SCORE + "=?",new String[]{"90"});
            List<Student> studentList = mService.getStudentList();
            Log.i(TAG,"studentList = " + studentList.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void deleteStudent() {
        Log.i(TAG,"deleteStudent");
        try{
            mService.deletedStudent(StudentTable.COLUMN_NAME + "=?",new String[]{"baoyamei"});
            List<Student> studentList = mService.getStudentList();
            Log.i(TAG,"studentList = " + studentList.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSC != null) {
            unbindService(mSC);
        }
    }
}
