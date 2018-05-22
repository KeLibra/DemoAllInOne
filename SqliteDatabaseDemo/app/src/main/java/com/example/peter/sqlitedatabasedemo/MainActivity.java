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

        switch (mDatabase.getVersion()) {
            case 1:
                mDatabase.beginTransaction();
                try {
                    insertData();
                    queryData();
                    updateData();
                    queryData();
                    deleteData();
                    queryData();
                    mDatabase.setTransactionSuccessful();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    mDatabase.endTransaction();
                }
                break;
            case 2:
                insertDataV2();
                queryDataV2();
                break;
            default:
                //do nothing
        }
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

        contentValues = new ContentValues();
        contentValues.put("id",3);
        contentValues.put("name","lemon");
        contentValues.put("gender",1);
        contentValues.put("number","201804111048");
        contentValues.put("score","90");

        mDatabase.insertWithOnConflict(DBHelper.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
    }

    private void deleteData() {
        Log.i(TAG,"deleteData");
        int number = mDatabase.delete(DBHelper.TABLE_NAME,"id = ?",new String[]{"3"});
        Log.d(TAG,"deleteData num = " + number);
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

    private void insertDataV2() {
        Log.i(TAG,"insertDataV2");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_ID,2);
        contentValues.put(DBHelper.COLUMN_NAME,"lucky");
        contentValues.put(DBHelper.COLUMN_GENDER,1);
        contentValues.put(DBHelper.COLUMN_NUMBER,"201805161054");
        contentValues.put(DBHelper.COLUMN_SCORE,99);
        contentValues.put(DBHelper.COLUMN_PHONE,"13813812345");

        mDatabase.insertWithOnConflict(DBHelper.TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
    }

    private void queryDataV2() {
        Log.i(TAG,"queryDataV2");

        Cursor cursor;
        cursor = mDatabase.query(DBHelper.TABLE_NAME, null,"name = ?", new String[] {"lucky"},null,null,null);

        try{
            while (cursor != null && cursor.moveToNext()) {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
                student.setName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));
                student.setGender(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_GENDER)));
                student.setNumber(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NUMBER)));
                student.setScore(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_SCORE)));
                student.setPhone(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PHONE)));

                Log.i(TAG,"queryDataV2 student = " + student.toString());
            }
        } catch (SQLException e) {
            Log.e(TAG,"queryDataV2 exception", e);
        }
    }
}
