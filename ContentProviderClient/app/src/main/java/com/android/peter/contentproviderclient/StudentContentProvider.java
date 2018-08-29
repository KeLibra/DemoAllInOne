package com.android.peter.contentproviderclient;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by peter on 2018/4/8.
 */

public class StudentContentProvider extends ContentProvider {
    private final static String TAG = "Demo." + "StudentProvider";

    private final static String AUTHORITY = "com.android.peter.provider";
    private final static int STUDENT_URI_CODE = 0;

    private Context mContext;
    private static SQLiteDatabase mDataBase;
    private final static UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY,"student",STUDENT_URI_CODE);
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDataBase = new DBOpenHelper(mContext).getWritableDatabase();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int uriType = sUriMatcher.match(uri);
        Cursor cursor;
        switch (uriType) {
            case STUDENT_URI_CODE:
                cursor = mDataBase.query(DBOpenHelper.DATABASE_STUDENT_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder,null);
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int uriType = sUriMatcher.match(uri);

        switch (uriType) {
            case STUDENT_URI_CODE:
                return ContentResolver.CURSOR_DIR_BASE_TYPE;
            default:
                //do nothing
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        long row;

        switch (uriType) {
            case STUDENT_URI_CODE:
                row = mDataBase.insert(DBOpenHelper.DATABASE_STUDENT_TABLE_NAME,null, values);
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }

        Log.d(TAG,"insert row = " + row);
        if(row > -1) {
            Log.d(TAG,"insert notifyChange");
            mContext.getContentResolver().notifyChange(uri,null);
            return ContentUris.withAppendedId(uri, row);
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        int rowDelete;

        switch (uriType) {
            case STUDENT_URI_CODE:
                rowDelete = mDataBase.delete(DBOpenHelper.DATABASE_STUDENT_TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }

        Log.d(TAG,"delete row = " + rowDelete);
        if(rowDelete > 0) {
            Log.d(TAG,"delete notifyChange");
            mContext.getContentResolver().notifyChange(uri,null);
        }

        return rowDelete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        int rowUpdate;
        switch (uriType) {
            case STUDENT_URI_CODE:
                rowUpdate = mDataBase.update(DBOpenHelper.DATABASE_STUDENT_TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }

        Log.d(TAG,"update row = " + rowUpdate);
        if(rowUpdate > 0) {
            Log.d(TAG,"update notifyChange");
            mContext.getContentResolver().notifyChange(uri,null);
        }

        return rowUpdate;
    }

    public static SQLiteDatabase getConnectionDB() {
        return mDataBase;
    }
}
