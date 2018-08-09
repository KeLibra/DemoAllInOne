package com.android.peter.aidlservicedemo.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private final static String TAG = "DBHelper";
    private final static String DB_NAME = "Student.db";
    private final static int DB_VERSION = 1;

    @SuppressLint("StaticFieldLeak")
    private static volatile DBHelper sDBHelper;

    private DBHelper(Context context) {
        this(context,DB_NAME,null,DB_VERSION);
    }

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, factory, DB_VERSION, errorHandler);
    }

    public static DBHelper getInstance(Context context) {
        if(sDBHelper == null) {
            synchronized (DBHelper.class) {
                if(sDBHelper == null) {
                    sDBHelper = new DBHelper(context.getApplicationContext());
                }
            }
        }

        return sDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            StudentTable.createTable(db);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
