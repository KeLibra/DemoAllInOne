package com.android.peter.aidlservicedemo.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudentListControllerImpl implements StudentListController {
    private final static String TAG = "StudentListControllerImpl";

    public final static String TABLE_NAME = "student_list";
    public final static String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_SCORE = "score";
    public static final String[] TABLE_COLUMNS = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_GENDER,
            COLUMN_SCORE
    };

    @SuppressLint("StaticFieldLeak")
    private static volatile StudentListControllerImpl sStudentListTableImpl;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private StudentListControllerImpl(Context applicationContext) {
        mContext = applicationContext;
        mDatabase = new DBHelper(mContext).getWritableDatabase();
    }

    public static StudentListControllerImpl getInstance(Context context) {
        if(sStudentListTableImpl == null) {
            synchronized (StudentListControllerImpl.class) {
                if(sStudentListTableImpl == null) {
                    sStudentListTableImpl = new StudentListControllerImpl(context.getApplicationContext());
                }
            }
        }

        return sStudentListTableImpl;
    }

    @Override
    public void createTable(SQLiteDatabase database) {
        String sql = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT,"
                + COLUMN_GENDER + " INTEGER CHECK(COLUMN_GENDER >= 0 && COLUMN_GENDER <= 1), "
                + COLUMN_SCORE + " INTEGER CHECK(COLUMN_SCORE >= 0 && COLUMN_SCORE <= 100))";

        database.execSQL(sql);
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
    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy, String limit) {
        return mDatabase.query(TABLE_NAME,columns,selection,selectionArgs,groupBy,having,orderBy,limit);
    }
}
