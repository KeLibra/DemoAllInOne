package com.android.peter.notificationdemo;

import android.app.NotificationManager;
import android.app.RemoteInput;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.android.peter.notificationdemo.Notificaitons.*;

/**
 *
 * @author peter
 * @date 2018/6/28
 */

public class NotificationService extends Service {
    private final static String TAG = "NotificationService";

    public final static String ACTION_SEND_PROGRESS_NOTIFICATION = "com.android.peter.notificationdemo.ACTION_SEND_PROGRESS_NOTIFICATION";

    private Context mContext;
    private NotificationManager mNM;
    private boolean mIsLoved;
    private boolean mIsPlaying = true;

    private List<NotificationContentWrapper> mContent = new ArrayList<>();
    private int mCurrentPosition = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        initNotificationContent();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null && intent.getAction() != null) {
            Log.i(TAG,"onStartCommand action = " + intent.getAction());
            switch (intent.getAction()) {
                case ACTION_SIMPLE:
                    break;
                case ACTION_ACTION:
                    break;
                case ACTION_REMOTE_INPUT:
                    break;
                case ACTION_BIG_PICTURE_STYLE:
                    break;
                case ACTION_BIG_TEXT_STYLE:
                    break;
                case ACTION_INBOX_STYLE:
                    break;
                case ACTION_MEDIA_STYLE:
                    dealWithActionMediaStyle(intent);
                    break;
                case ACTION_MESSAGING_STYLE:
                    break;
                case ACTION_YES:
                    mNM.cancel(NOTIFICATION_ACTION);
                    break;
                case ACTION_NO:
                    mNM.cancel(NOTIFICATION_ACTION);
                    break;
                case ACTION_DELETE:
                    break;
                case ACTION_REPLY:
                    dealWithActionReplay(intent);
                    break;
                case ACTION_PROGRESS:
                    break;
                case ACTION_SEND_PROGRESS_NOTIFICATION:
                    dealWithActionSendProgressNotification();
                    break;
                case ACTION_CUSTOM_HEADS_UP_VIEW:
                    dealWithActionCustomHeadsUpView(intent);
                    break;
                case ACTION_CUSTOM_VIEW:
                    break;
                case ACTION_CUSTOM_VIEW_OPTIONS_LOVE:
                    Notificaitons.getInstance().sendCustomViewNotification(this,mNM,mContent.get(mCurrentPosition),!mIsLoved,mIsPlaying);
                    mIsLoved = !mIsLoved;
                    break;
                case ACTION_CUSTOM_VIEW_OPTIONS_PRE:
                    --mCurrentPosition;
                    Notificaitons.getInstance().sendCustomViewNotification(this,mNM,getNotificationContent(),mIsLoved,mIsPlaying);
                    break;
                case ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE:
                    Notificaitons.getInstance().sendCustomViewNotification(this,mNM,mContent.get(mCurrentPosition),mIsLoved,!mIsPlaying);
                    mIsPlaying = !mIsPlaying;
                    break;
                case ACTION_CUSTOM_VIEW_OPTIONS_NEXT:
                    ++mCurrentPosition;
                    Notificaitons.getInstance().sendCustomViewNotification(this,mNM,getNotificationContent(),mIsLoved,mIsPlaying);
                    break;
                case ACTION_CUSTOM_VIEW_OPTIONS_LYRICS:
                    break;
                case ACTION_CUSTOM_VIEW_OPTIONS_CANCEL:
                    mNM.cancel(NOTIFICATION_CUSTOM);
                    break;
                default:
                    //do nothing
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initNotificationContent() {
        mContent.clear();
        mContent.add(new NotificationContentWrapper(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.custom_view_picture_pre),"远走高飞","金志文"));
        mContent.add(new NotificationContentWrapper(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.custom_view_picture_current),"最美的期待","周笔畅 - 最美的期待"));
        mContent.add(new NotificationContentWrapper(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.custom_view_picture_next),"你打不过我吧","跟风超人"));
    }

    private void dealWithActionMediaStyle(Intent intent) {
        String option = intent.getStringExtra(EXTRA_OPTIONS);
        Log.i(TAG,"media option = " + option);
        if(option == null) {
            return;
        }
        switch (option) {
            case MEDIA_STYLE_ACTION_PAUSE:
                Notificaitons.getInstance().sendMediaStyleNotification(this,mNM,false);
                break;
            case MEDIA_STYLE_ACTION_PLAY:
                Notificaitons.getInstance().sendMediaStyleNotification(this,mNM,true);
                break;
            case MEDIA_STYLE_ACTION_NEXT:
                break;
            case MEDIA_STYLE_ACTION_DELETE:
                mNM.cancel(NOTIFICATION_MEDIA_STYLE);
                break;
            default:
                //do nothing
        }
    }

    private void dealWithActionReplay(Intent intent) {
        Bundle result = RemoteInput.getResultsFromIntent(intent);
        if(result != null) {
            String content = result.getString(REMOTE_INPUT_RESULT_KEY);
            Log.i(TAG,"content = " + content);
            mNM.cancel(NOTIFICATION_REMOTE_INPUT);
        }
    }

    private void dealWithActionSendProgressNotification() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0 ; i<=100 ; i++) {
                    Notificaitons.getInstance().sendProgressViewNotification(mContext,mNM,i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void dealWithActionCustomHeadsUpView(Intent intent) {
        String headsUpOption = intent.getStringExtra(EXTRA_OPTIONS);
        Log.i(TAG,"heads up option = " + headsUpOption);
        if(headsUpOption == null) {
            return;
        }
        switch (headsUpOption) {
            case ACTION_ANSWER:
                mNM.cancel(NOTIFICATION_CUSTOM_HEADS_UP);
                break;
            case ACTION_REJECT:
                mNM.cancel(NOTIFICATION_CUSTOM_HEADS_UP);
                break;
            default:
                //do nothing
        }
    }

    private NotificationContentWrapper getNotificationContent() {
        switch (mCurrentPosition) {
            case -1:
                mCurrentPosition = 2;
                break;
            case 3:
                mCurrentPosition = 0;
                break;
            default:
                // do nothing
        }

        return mContent.get(mCurrentPosition);
    }
}
