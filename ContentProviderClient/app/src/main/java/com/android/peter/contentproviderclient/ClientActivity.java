package com.android.peter.contentproviderclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ClientActivity extends AppCompatActivity {
    private final static String TAG = "Demo." + "ClientActivity";

    /**
     * student table
     * @id primary key
     * @name student's name. e.g:peter.
     * @gender student's gender. e.g: 0 male; 1 female.
     * @number student's number. e.g: 201804081702.
     * @score student's score. more than 0 and less than 100. e.g:90.
     * */
    private final static String AUTHORITY = "com.android.peter.provider";
    private final static Uri STUDENT_URI = Uri.parse("content://" + AUTHORITY + "/student");

    private final static int CONTENT_PROVIDER_CHANGED = 20180412;

    private Context mContext;
    private Handler mHandler;
    private ContentObserver mContentObserver;

    private class ObserverHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG,"handleMessage msg = " + msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        mContext = this;

        mHandler = new ObserverHandler();

        mContentObserver = new ContentObserver(mHandler) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Log.d(TAG,"onChange selfChange = " + selfChange + " , uri = " + uri.toString());
                mHandler.obtainMessage(CONTENT_PROVIDER_CHANGED).sendToTarget();
            }
        };

        mContext.getContentResolver().registerContentObserver(STUDENT_URI,true,mContentObserver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"------------insert---------");
                insertValue();
                queryValue();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"------------update---------");
                updateValue();
                queryValue();
            }
        },500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"------------delete---------");
                deleteValue();
                queryValue();
            }
        },1000);
        getTypeValue();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mContentObserver != null) {
            mContext.getContentResolver().unregisterContentObserver(mContentObserver);
        }
    }

    private void insertValue() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",0);
        contentValues.put("name","peter");
        contentValues.put("gender",0);
        contentValues.put("number","201804081705");
        contentValues.put("score","100");

        mContext.getContentResolver().insert(STUDENT_URI,contentValues);
    }

    private void queryValue() {
        Cursor cursor = getContentResolver().query(STUDENT_URI, new String[]{"id", "name","gender","number","score"},null,null,null);
        while (cursor.moveToNext()) {
            Student student = new Student();
            student.id = cursor.getInt(cursor.getColumnIndex("id"));
            student.name = cursor.getString(cursor.getColumnIndex("name"));
            student.gender = cursor.getInt(cursor.getColumnIndex("gender"));
            student.number = cursor.getString(cursor.getColumnIndex("number"));
            student.score = cursor.getInt(cursor.getColumnIndex("score"));
            Log.d(TAG,"student = " + student.toString());
        }
    }

    private void updateValue() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name","update");
        contentValues.put("gender",1);
        contentValues.put("number","201804111048");
        contentValues.put("score","90");

        getContentResolver().update(STUDENT_URI,contentValues,"name = ?",new String[] {"peter"});
    }

    private void deleteValue() {
        getContentResolver().delete(STUDENT_URI,"name = ?",new String[]{"update"});
    }

    private void getTypeValue() {
        Log.d(TAG,"getTypeValue type = " + getContentResolver().getType(STUDENT_URI));
    }
}
