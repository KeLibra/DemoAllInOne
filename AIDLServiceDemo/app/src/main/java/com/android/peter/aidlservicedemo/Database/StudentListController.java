package com.android.peter.aidlservicedemo.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

interface StudentListController {
    void createTable(SQLiteDatabase database);
    long insert(ContentValues contentValues);
    long insert(String nullColumnHack, ContentValues contentValues, int conflictAlgorithm);
    int delete(String whereClause, String[] whereArgs);
    int update(ContentValues values, String whereClause, String[] whereArgs);
    Cursor query(String[] columns, String selection,
                 String[] selectionArgs, String groupBy, String having,
                 String orderBy, String limit);
}
