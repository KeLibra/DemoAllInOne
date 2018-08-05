package com.android.peter.aidlclientdemo;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.peter.aidlservicedemo.IOnDataChangeListener;
import com.android.peter.aidlservicedemo.IStudentManager;
import com.android.peter.aidlservicedemo.bean.Student;
import com.android.peter.aidlservicedemo.database.StudentTable;

import java.util.List;

public class ClientActivity extends AppCompatActivity {
    private final static String TAG = "peter.ClientActivity";

    private final static String REMOTE_SERVICE_PACKAGE_NAME = "com.android.peter.aidlservicedemo";
    private final static String REMOTE_SERVICE_CLASS_NAME = "com.android.peter.aidlservicedemo.StudentManagerService";

    private final static int MESSAGE_DATA_CHANGED = 20180804;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_DATA_CHANGED:
                    Log.i(TAG,"I have received a data changed message!");
                    break;
                default:
                    // do nothing
            }
        }
    };
    private IStudentManager mRemoteService;
    private IOnDataChangeListener mOnDataChangeLister = new IOnDataChangeListener.Stub() {
        @Override
        public void onDataChange() throws RemoteException {
            Log.i(TAG,"onDataChange");
            // running in Binder's thread pool,could be used to do long-time task,
            // and could not to access to UI thread directly
            mHandler.sendEmptyMessage(MESSAGE_DATA_CHANGED);
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.i(TAG,"Remote service is died !");
            if(mRemoteService == null) {
                return;
            }

            mRemoteService.asBinder().unlinkToDeath(mDeathRecipient,0);
            mRemoteService = null;

            // rebind remote service
            bindRemoteService();
        }
    };
    private ServiceConnection mSC = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"onServiceConnected name = " + name);
            mRemoteService = IStudentManager.Stub.asInterface(service);
            if(mRemoteService != null) {
                try {
                    mRemoteService.asBinder().linkToDeath(mDeathRecipient,0);
                    mRemoteService.registerDataChangeListener(mOnDataChangeLister);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i(TAG,"Connect error!");
            }

            // onServiceConnected is running in main thread,
            // can not do long-time task
            new Thread(new Runnable() {
                @Override
                public void run() {
                    insertStudent();
                    queryStudent();
                    updateStudent();
                    deleteStudent();
                }
            }).start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG,"onServiceDisconnected name = " + name);
            mRemoteService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        bindRemoteService();
    }

    // bind remote service
    private void bindRemoteService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(REMOTE_SERVICE_PACKAGE_NAME, REMOTE_SERVICE_CLASS_NAME));
        bindService(intent,mSC, Context.BIND_AUTO_CREATE);
    }

    // inset three students
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
            if(mRemoteService != null) {
                mRemoteService.addStudent(peter);
                mRemoteService.addStudent(lemon);
                mRemoteService.addStudent(baoyamei);

                List<Student> studentList = mRemoteService.getStudentList();
                Log.i(TAG,"studentList = " + studentList.toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // query the student who's age is 30
    private void queryStudent() {
        Log.i(TAG,"queryStudent");
        if(mRemoteService != null) {
            try{
                List<Student> queryList = mRemoteService.queryStudent(StudentTable.TABLE_COLUMNS,StudentTable.COLUMN_AGE + "=?",
                        new String[]{"30"},null,null,null,null);
                Log.i(TAG,"queryList = " + queryList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // update the student who's score is 90
    private void updateStudent() {
        Log.i(TAG,"updateStudent");
        if(mRemoteService != null) {
            ContentValues lemon = new ContentValues();
            lemon.put(StudentTable.COLUMN_SCORE,100);
            try {
                mRemoteService.updateStudent(lemon,StudentTable.COLUMN_NAME + "=?",new String[]{"baoyamei"});
                List<Student> studentList = mRemoteService.getStudentList();
                Log.i(TAG,"studentList = " + studentList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // delete the student who's name is baoyamei
    private void deleteStudent() {
        Log.i(TAG,"deleteStudent");
        if(mRemoteService != null) {
            try{
                mRemoteService.deletedStudent(StudentTable.COLUMN_SCORE + "=?",new String[]{"100"});
                List<Student> studentList = mRemoteService.getStudentList();
                Log.i(TAG,"studentList = " + studentList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mRemoteService != null && mRemoteService.asBinder().isBinderAlive()) {
            try {
                mRemoteService.unregisterDataChangeListener(mOnDataChangeLister);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if(mSC != null) {
            unbindService(mSC);
        }
    }
}
