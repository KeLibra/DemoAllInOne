package com.android.peter.contentproviderdemo;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    /**
     * student table
     * @id primary key
     * @name student's name. e.g:peter.
     * @gender student's gender. e.g: 0 male; 1 female.
     * @number student's number. e.g: 201804081702.
     * @score student's score. more than 0 and less than 100. e.g:90.
     * */
    private final static String AUTHORITY = "com.android.peter.provider";
    private final static Uri STUDENT_URI = Uri.parse("content://" + AUTHORITY + "/student");

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        insertValue();
        queryValue();

        Uri uri = Uri.parse("content://com.android.peter.provider/student");
        Uri withAppendedIdUri = ContentUris.withAppendedId(uri, 1);
        Log.d(TAG," withAppendedId ~ uri = " + withAppendedIdUri.toString());
        long parseId = ContentUris.parseId(withAppendedIdUri);
        Log.d(TAG," parseId ~ uri = " + parseId);

        Uri.Builder ub = new Uri.Builder();
        ub.authority("com.android.peter.provider")
        .appendPath("student");
        Log.d(TAG,"ub = " + ub.toString());
        Uri.Builder appendIdUri = ContentUris.appendId(ub,1);
        Log.d(TAG,"appendIdUri = " + appendIdUri.toString());

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void insertValue() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",1);
        contentValues.put("name","lemon");
        contentValues.put("gender",1);
        contentValues.put("number","201804091601");
        contentValues.put("score","100");

        mContext.getContentResolver().insert(STUDENT_URI,contentValues);
    }

    private void queryValue() {
        Cursor cursor = getContentResolver().query(STUDENT_URI, new String[]{"id", "name","gender","number","score"},null,null,null);
        try{
            while (cursor != null && cursor.moveToNext()) {
                Student student = new Student();
                student.id = cursor.getInt(cursor.getColumnIndex("id"));
                student.name = cursor.getString(cursor.getColumnIndex("name"));
                student.gender = cursor.getInt(cursor.getColumnIndex("gender"));
                student.number = cursor.getString(cursor.getColumnIndex("number"));
                student.score = cursor.getInt(cursor.getColumnIndex("score"));
                Log.d(TAG,"student = " + student.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
    }
}
