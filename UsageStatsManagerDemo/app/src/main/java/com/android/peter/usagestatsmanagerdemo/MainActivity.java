package com.android.peter.usagestatsmanagerdemo;

import android.app.usage.ConfigurationStats;
import android.app.usage.EventStats;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "peter.log.MainActivity";

    private Context mContext;
    private UsageStatsManager mUsageStatsManager;
    private long mCurrentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        AppUsageUtil.checkUsageStateAccessPermission(mContext);
        mUsageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentTime = System.currentTimeMillis();
        if(mUsageStatsManager != null) {
            queryUsageStats();
            queryConfigurations();
            queryEventStats();
            AppUsageUtil.getTopActivityPackageName(mContext);
        }

    }

    private void queryUsageStats() {
        List<UsageStats> usageStatsList = mUsageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, mCurrentTime - 60 * 1000, mCurrentTime);
        for(UsageStats usageStats: usageStatsList) {
            Log.d(TAG,"usageStats PackageName = " + usageStats.getPackageName() + " , FirstTimeStamp = "
                    + usageStats.getFirstTimeStamp() + " , LastTimeStamp = " + usageStats.getLastTimeStamp()
                    + ", LastTimeUsed = " + usageStats.getLastTimeUsed()
                    + " , TotalTimeInForeground = " + usageStats.getTotalTimeInForeground());
        }
    }

    private void queryConfigurations() {
        List<ConfigurationStats> configurationStatsList = mUsageStatsManager.queryConfigurations(
                UsageStatsManager.INTERVAL_DAILY,mCurrentTime - 60 * 1000, mCurrentTime);
        for (ConfigurationStats configurationStats:configurationStatsList) {
            Log.d(TAG,"configurationStats Configuration = " + configurationStats.getConfiguration() + " , ActivationCount = " + configurationStats.getActivationCount()
                    + " , FirstTimeStamp = " + configurationStats.getFirstTimeStamp() + " , LastTimeStamp = " + configurationStats.getLastTimeStamp()
                    + " , LastTimeActive = " + configurationStats.getLastTimeActive() + " , TotalTimeActive = " + configurationStats.getTotalTimeActive());
        }
    }

    private void queryEventStats() {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            List<EventStats> eventStatsList = mUsageStatsManager.queryEventStats(
                    UsageStatsManager.INTERVAL_DAILY,mCurrentTime - 60 * 1000,mCurrentTime);
            for(EventStats eventStats:eventStatsList) {
                Log.d(TAG,"eventStats EventType" + eventStats.getEventType() + " , Count = " + eventStats.getCount()
                        + " , FirstTime = " + eventStats.getFirstTimeStamp() + " , LastTime = " + eventStats.getLastTimeStamp()
                        + " , LastEventTime = " + eventStats.getLastEventTime() + " , TotalTime = " + eventStats.getTotalTime());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
