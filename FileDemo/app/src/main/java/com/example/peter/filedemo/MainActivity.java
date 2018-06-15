package com.example.peter.filedemo;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Context mContext;
    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mContent = findViewById(R.id.tv_content);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        long beginTime = System.currentTimeMillis();

        String data;

        //test for FileInputStream and FileOutputStream
//        writeByteData();
//        data = readByteData();
//        mContent.setText(data);
//        copyFile();

        //test for BufferedInputStream and BufferedOutputStream
        writeByteDataWithBuffer();
        readByteDataWithBuffer();
        copyWithBufferedStream();

        //test for BufferedReader and BufferedWriter
//        writeCharacterData();
//        data = readCharacterData();
//        mContent.setText(data);

        Log.d(TAG, "cost time = " + (System.currentTimeMillis() - beginTime) + "");
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

    private void writeByteData() {
        String content = FileUtils.getContentFromAsset(this,"test_file_for_getContentFromAsset.txt");

        final String PATH_DATA_DATA_FILES = mContext.getFilesDir().getPath();
        final String FILE_NAME = PATH_DATA_DATA_FILES + "/" + "byte_stream.txt";

        FileUtils.writeByteData(FILE_NAME, content);
    }

    private String readByteData() {
        final String PATH_DATA_DATA_FILES = mContext.getFilesDir().getPath();
        final String FILE_NAME = PATH_DATA_DATA_FILES + "/" + "byte_stream.txt";

        return FileUtils.readByteData(FILE_NAME);
    }

    private void writeByteDataWithBuffer() {
        String content = FileUtils.getContentFromAsset(this,"mass_data_test.txt");

        final String PATH_DATA_DATA_FILES = mContext.getFilesDir().getPath();
        final String FILE_NAME = PATH_DATA_DATA_FILES + "/" + "byte_stream_with_buffer.txt";

        FileUtils.writeByteDataWithBuffer(FILE_NAME, content);
    }

    private String readByteDataWithBuffer() {
        final String PATH_DATA_DATA_FILES = mContext.getFilesDir().getPath();
        final String FILE_NAME = PATH_DATA_DATA_FILES + "/" + "byte_stream_with_buffer.txt";

        return FileUtils.readByteDataWithBuffer(FILE_NAME);
    }

    private void copyFile(){
        final String PATH_DATA_DATA_FILES = mContext.getFilesDir().getPath();
        final String FILE_FROM_NAME = PATH_DATA_DATA_FILES + "/" + "byte_stream.txt";
        final String FILE_TO_NAME = PATH_DATA_DATA_FILES + "/" + "byte_stream_copy.txt";

        FileUtils.copyFileByByte(FILE_FROM_NAME,FILE_TO_NAME);
    }

    private void copyWithBufferedStream() {
        final String PATH_DATA_DATA_FILES = mContext.getFilesDir().getPath();
        final String FILE_FROM_NAME = PATH_DATA_DATA_FILES + "/" + "byte_stream_with_buffer.txt";
        final String FILE_TO_NAME = PATH_DATA_DATA_FILES + "/" + "byte_stream_with_buffer_copy.txt";
        FileUtils.copyWithBufferedStream(new File(FILE_FROM_NAME), new File(FILE_TO_NAME));
    }

    private void writeCharacterData() {
        String content = FileUtils.getContentFromAsset(this,"test_file_for_getContentFromAsset.txt");

        final String PATH_DATA_DATA_FILES = mContext.getFilesDir().getPath();
        final String FILE_NAME = PATH_DATA_DATA_FILES + "/" + "character_stream.txt";

        FileUtils.writeCharacterData(FILE_NAME, content);
    }

    private String readCharacterData() {
        final String PATH_DATA_DATA_FILES = mContext.getFilesDir().getPath();
        final String FILE_NAME = PATH_DATA_DATA_FILES + "/" + "character_stream.txt";

        return FileUtils.readCharacterData(FILE_NAME);
    }
}
