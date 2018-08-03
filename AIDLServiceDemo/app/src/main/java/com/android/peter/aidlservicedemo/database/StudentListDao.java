package com.android.peter.aidlservicedemo.database;

import android.content.ContentValues;

import com.android.peter.aidlservicedemo.bean.Student;

import java.util.List;

interface StudentListDao {
    List<Student> getAllStudent();
    long insert(ContentValues contentValues);
    long insert(String nullColumnHack, ContentValues contentValues, int conflictAlgorithm);
    int delete(String whereClause, String[] whereArgs);
    int update(ContentValues values, String whereClause, String[] whereArgs);
    List<Student> query(String[] columns, String selection,
                 String[] selectionArgs, String groupBy, String having,
                 String orderBy, String limit);
}
