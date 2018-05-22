package com.example.peter.sqlitedatabasedemo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by peter on 2018/5/15.
 */

/**
 * student table
 * @id primary key
 * @name student's name. e.g:peter.
 * @gender student's gender. e.g: 0 male; 1 female.
 * @number student's number. e.g: 201804081702.
 * @score student's score. more than 0 and less than 100. e.g:90.
 * */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "student";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_PHONE = "phone";
    public static final String[] TABLE_COLUMNS = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_GENDER,
            COLUMN_NUMBER,
            COLUMN_SCORE
    };

    public static final String[] TABLE_COLUMNS_v2 = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_GENDER,
            COLUMN_NUMBER,
            COLUMN_SCORE,
            COLUMN_PHONE
    };

    public DBHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.i(TAG,"onCreate");
        String sql = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT,"
                + COLUMN_GENDER + " INTEGER, "
                + COLUMN_NUMBER + " TEXT, "
                + COLUMN_SCORE + " INTEGER)";


        //事务开始
        database.beginTransaction();
        try {
            //执行操作语句
            database.execSQL(sql);
            //事务成功
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //事务结束
            database.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.i(TAG,"onUpgrade oldVersion = " +  oldVersion + " , newVersion =" + newVersion);

        switch (oldVersion) {
            case 1:
                upgradeFromVersion1(database);
                break;
            default:
                Log.i(TAG,"Do not have this case !");
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //重写onDowngrade的时候不要调用父类onDowngrade方法
        //super.onDowngrade(database, oldVersion, newVersion);
        Log.i(TAG,"onDowngrade oldVersion = " +  oldVersion + " , newVersion =" + newVersion);
        switch (oldVersion) {
            case 2:
                downgradeFromVersion2(database);
                break;
            default:
                Log.i(TAG,"Do not have this case !");
        }
    }

    private void upgradeFromVersion1(SQLiteDatabase database) {
        Log.i(TAG,"upgradeFromVersion1");
        //do something
        database.execSQL("alter table " + TABLE_NAME + " add column phone text;");
    }

    private void downgradeFromVersion2(SQLiteDatabase database) {
        Log.i(TAG,"downgradeFromVersion2");
        //do something
        database.execSQL("drop table if exists " + TABLE_NAME);
        database.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT,"
                + COLUMN_GENDER + " INTEGER, "
                + COLUMN_NUMBER + " TEXT, "
                + COLUMN_SCORE + " INTEGER)");
    }
}
