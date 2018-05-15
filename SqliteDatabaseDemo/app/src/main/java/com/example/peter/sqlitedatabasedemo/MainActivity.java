package com.example.peter.sqlitedatabasedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = new DBHelper(this).getWritableDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        insertData();
        queryData();
        updateData();
        queryData();
        deleteData();
        queryData();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void insertData() {
        Log.i(TAG,"insertData");
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",0);
        contentValues.put("name","peter");
        contentValues.put("gender",0);
        contentValues.put("number","201804081705");
        contentValues.put("score","100");

        mDatabase.insertWithOnConflict(DBHelper.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);

        contentValues = new ContentValues();
        contentValues.put("id",1);
        contentValues.put("name","lemon");
        contentValues.put("gender",1);
        contentValues.put("number","201804111048");
        contentValues.put("score","90");

        mDatabase.insertWithOnConflict(DBHelper.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
    }

    private void deleteData() {
        Log.i(TAG,"deleteData");
        mDatabase.delete(DBHelper.TABLE_NAME,"name = ?",new String[]{"update"});
    }

    private void updateData() {
        Log.i(TAG,"updateData");
        ContentValues contentValues = new ContentValues();
        contentValues.put("name","update");
        contentValues.put("gender",1);
        contentValues.put("number","201804111048");
        contentValues.put("score","100");

        mDatabase.update(DBHelper.TABLE_NAME,contentValues,"name = ?",new String[]{"lemon"});
    }

    private void queryData() {
        Cursor cursor;

        cursor = mDatabase.query(DBHelper.TABLE_NAME,DBHelper.TABLE_COLUMNS,null,null,null,null,null);

        try{
            while (cursor != null && cursor.moveToNext()) {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
                student.setName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));
                student.setGender(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_GENDER)));
                student.setNumber(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NUMBER)));
                student.setScore(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_SCORE)));

                Log.i(TAG,"queryData student = " + student.toString());
            }
        } catch (SQLException e) {
            Log.e(TAG,"queryData exception", e);
        }
    }
}
