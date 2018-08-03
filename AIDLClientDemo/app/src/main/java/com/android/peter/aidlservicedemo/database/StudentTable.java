package com.android.peter.aidlservicedemo.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by  peter on 2018/8/3.
 */

public class StudentTable {
    public final static String TABLE_NAME = "student_list";
    public final static String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_SCORE = "score";
    public static final String[] TABLE_COLUMNS = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_GENDER,
            COLUMN_AGE,
            COLUMN_SCORE
    };

    public static void createTable(SQLiteDatabase database) {
        String sql = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_GENDER + " INTEGER CHECK( " + COLUMN_GENDER + " >= 0 AND " + COLUMN_GENDER + " <= 1), "
                + COLUMN_AGE + " INTEGER CHECK(" + COLUMN_AGE + " > 0), "
                + COLUMN_SCORE + " INTEGER CHECK(" + COLUMN_SCORE + " >= 0 AND " + COLUMN_SCORE + " <= 100))";

        database.execSQL(sql);
    }
}
