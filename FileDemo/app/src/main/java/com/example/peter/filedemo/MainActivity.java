package com.example.peter.filedemo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    private final static int MSG_UPDATE = 0;
    private final static int MSG_DONE = 1;
    private Context mContext;
    private Button mByteData;
    private Button mByteDataWithBuffer;
    private Button mCharacterDataWithBuffer;
    private ProgressBar mLoading;
    private TextView mContent;
    private String mData;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_UPDATE:
                    mContent.setText(getString(R.string.data_content,mData));
                    break;
                case MSG_DONE:
                    mLoading.setVisibility(View.GONE);
                    mByteDataWithBuffer.setEnabled(true);
                    mContent.setText(getString(R.string.data_content,"Done"));
                    break;
            }
        }
    };
    private WorkingThread mWorkingThread = new WorkingThread(mHandler);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"onCreate");
        mContext = this;

        mByteData = findViewById(R.id.btn_byte_data);
        mByteDataWithBuffer = findViewById(R.id.btn_byte_data_with_buffer);
        mCharacterDataWithBuffer = findViewById(R.id.btn_character_data_with_buffer);
        mContent = findViewById(R.id.tv_content);
        mLoading = findViewById(R.id.pb_loading);

        mByteData.setOnClickListener(this);
        mByteDataWithBuffer.setOnClickListener(this);
        mCharacterDataWithBuffer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(mWorkingThread != null && mWorkingThread.isAlive()) {
            mWorkingThread.interrupt();
        }
        switch (view.getId()) {
            case R.id.btn_byte_data:
                mWorkingThread = new WorkingThread(mHandler,WorkingThread.WORK_TYPE_BYTE_DATA);
                mWorkingThread.start();
                break;
            case R.id.btn_byte_data_with_buffer:
                mByteDataWithBuffer.setEnabled(false);
                mLoading.setVisibility(View.VISIBLE);
                mWorkingThread = new WorkingThread(mHandler,WorkingThread.WORK_TYPE_BYTE_DATA_WITH_BUFFER);
                mWorkingThread.start();
                break;
            case R.id.btn_character_data_with_buffer:
                mWorkingThread = new WorkingThread(mHandler,WorkingThread.WORK_TYPE_CHARACTER_DATA_WITH_BUFFER);
                mWorkingThread.start();
                break;
            default:
                //do nothing
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG,"onAttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG,"onDetachedFromWindow");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG,"onWindowFocusChanged");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null) {
            if(Log.isLoggable(TAG,Log.DEBUG)) {
                Log.d(TAG, "Memory size = " + activityManager.getMemoryClass() + " Large memory size = " + activityManager.getLargeMemoryClass());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        if(mWorkingThread != null && mWorkingThread.isAlive()) {
            Log.d(TAG,"mWorkingThread.interrupt");
            mWorkingThread.interrupt();
        }
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

    private void writeCharacterDataWithBuffer() {
        String content = FileUtils.getContentFromAsset(this,"test_file_for_getContentFromAsset.txt");

        final String PATH_DATA_DATA_FILES = mContext.getFilesDir().getPath();
        final String FILE_NAME = PATH_DATA_DATA_FILES + "/" + "character_stream.txt";

        FileUtils.writeCharacterDataWithBuffer(FILE_NAME, content);
    }

    private String readCharacterDataWithBuffer() {
        final String PATH_DATA_DATA_FILES = mContext.getFilesDir().getPath();
        final String FILE_NAME = PATH_DATA_DATA_FILES + "/" + "character_stream.txt";

        return FileUtils.readCharacterDataWithBuffer(FILE_NAME);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if(Log.isLoggable(TAG,Log.DEBUG)) {
            Log.d(TAG,"level = " + level);
        }
    }

    private class WorkingThread extends Thread {
        private final static int WORK_TYPE_BYTE_DATA = 0;
        private final static int WORK_TYPE_BYTE_DATA_WITH_BUFFER = 1;
        private final static int WORK_TYPE_CHARACTER_DATA_WITH_BUFFER = 2;

        private int type;
        private Handler handler;

        public WorkingThread(Handler handler) {
            this.handler = handler;
            type = WORK_TYPE_BYTE_DATA;
        }
        public WorkingThread(Handler handler,int type) {
            this.handler = handler;
            this.type = type;
        }

        @Override
        public void run() {
            super.run();
            long beginTime = System.currentTimeMillis();
            Log.d(TAG,"WorkingThread type = " + type);
            Message msg = Message.obtain();

            switch (type) {
                case WORK_TYPE_BYTE_DATA:
                    //test for FileInputStream and FileOutputStream
                    writeByteData();
                    mData = readByteData();
                    copyFile();

                    msg.what = MSG_UPDATE;
                    handler.sendMessage(msg);
                    break;
                case WORK_TYPE_BYTE_DATA_WITH_BUFFER:
                    //test for BufferedInputStream and BufferedOutputStream
                    writeByteDataWithBuffer();
                    readByteDataWithBuffer();
                    copyWithBufferedStream();
                    msg.what = MSG_DONE;
                    handler.sendMessage(msg);
                    break;
                case WORK_TYPE_CHARACTER_DATA_WITH_BUFFER:
                    //test for BufferedReader and BufferedWriter
                    writeCharacterDataWithBuffer();
                    mData = readCharacterDataWithBuffer();

                    msg.what = MSG_UPDATE;
                    handler.sendMessage(msg);
                    break;
                default:
                    //do nothing
            }

            if(Log.isLoggable(TAG,Log.DEBUG)) {
                Log.d(TAG, "cost time = " + (System.currentTimeMillis() - beginTime) + "");
            }
        }
    }
}
