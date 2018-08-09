package com.android.peter.aidlservicedemo.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.peter.aidlservicedemo.bean.Student;

import java.util.ArrayList;
import java.util.List;

import static com.android.peter.aidlservicedemo.database.StudentTable.COLUMN_ID;
import static com.android.peter.aidlservicedemo.database.StudentTable.TABLE_COLUMNS;
import static com.android.peter.aidlservicedemo.database.StudentTable.TABLE_NAME;

public class StudentListDaoImpl implements StudentListDao {
    private final static String TAG = "StudentListDaoImpl";

    @SuppressLint("StaticFieldLeak")
    private static volatile StudentListDaoImpl sStudentListTableImpl;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private StudentListDaoImpl(Context applicationContext) {
        mContext = applicationContext;
        mDatabase = DBHelper.getInstance(applicationContext).getWritableDatabase();
    }

    public static StudentListDaoImpl getInstance(Context context) {
        if(sStudentListTableImpl == null) {
            synchronized (StudentListDaoImpl.class) {
                if(sStudentListTableImpl == null) {
                    sStudentListTableImpl = new StudentListDaoImpl(context.getApplicationContext());
                }
            }
        }

        return sStudentListTableImpl;
    }

    @Override
    public long insert(ContentValues contentValues) {
        return mDatabase.insertWithOnConflict(
                TABLE_NAME,
                null,
                contentValues,
                SQLiteDatabase.CONFLICT_IGNORE);
    }

    @Override
    public long insert(String nullColumnHack, ContentValues contentValues, int conflictAlgorithm) {
        return mDatabase.insertWithOnConflict(
                TABLE_NAME,
                nullColumnHack,
                contentValues,
                conflictAlgorithm);
    }

    @Override
    public int delete(String whereClause, String[] whereArgs) {
        return mDatabase.delete(TABLE_NAME,whereClause,whereArgs);
    }

    @Override
    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return mDatabase.update(TABLE_NAME,values,whereClause,whereArgs);
    }

    @Override
    public List<Student> query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy, String limit) {
        Cursor cursor = mDatabase.query(TABLE_NAME,columns,selection,selectionArgs,groupBy,having,orderBy,limit);
        List<Student> studentList = new ArrayList<>();
        try {
            if(cursor != null && cursor.moveToFirst()) {
                do{
                    Student student = new Student(cursor);
                    studentList.add(student);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }

        return studentList;
    }

    @Override
    public List<Student> getAllStudent() {
        Cursor cursor = mDatabase.query(TABLE_NAME,TABLE_COLUMNS,
                null,null,null,
                null,COLUMN_ID + " ASC");
        List<Student> studentList = new ArrayList<>();
        try {
            if(cursor != null && cursor.moveToFirst()) {
                do {
                    Student student = new Student(cursor);
                    studentList.add(student);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }

        return studentList;
    }
}
