package com.example.peter.wifiandbluetoothdetection;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WifiAndBluetoothListActivity extends AppCompatActivity {
    private static final String TAG = "WifiAndBluetoothListActivity";

    private static final int PERMISSION_REQUEST_LOCATION = 0;

    private Context mContext;
    private TextView mWifiNum;
    private TextView mWifiList;
    private TextView mBluetoothNum;
    private TextView mBluetoothList;

    private List<ScanResult> mWifiRecorders = new ArrayList<>();
    private Map<BluetoothDevice, android.bluetooth.le.ScanResult> mBluetoothRecorders = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_and_bluetooth_list);

        mContext = this;

        mWifiNum = findViewById(R.id.tv_wifi_number);
        mWifiList = findViewById(R.id.tv_wifi_list);
        mBluetoothNum = findViewById(R.id.tv_bluetooth_number);
        mBluetoothList = findViewById(R.id.tv_bluetooth_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initPermission();
        getWifiList();
        getBluetoothList();
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

    //init permission
    private void initPermission() {
        if(Build.VERSION.SDK_INT>=23){
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(mContext,"Need this permission for scan wifi and bluetooth device", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (permissions[0] .equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        Toast.makeText(mContext,"Need this permission for scan wifi and bluetooth device", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                break;
            default:
                //do nothing
        }
    }

    private void getWifiList(){
        Log.d(TAG,"getWifiList");
        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(wifiManager == null) {
            return;
        }

        mWifiRecorders.clear();
        wifiManager.startScan();
        mWifiRecorders = wifiManager.getScanResults();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mWifiRecorders.size() == 0) {
                    Log.d(TAG,"null");
                    Toast.makeText(mContext, "No WiFi found!", Toast.LENGTH_LONG).show();
                }else {
                    StringBuffer wifiList = new StringBuffer();
                    wifiList.append("Wifi List:\n");
                    for(int i = 0; i< mWifiRecorders.size(); i++) {
                        Log.i(TAG, "getWifiList = " + mWifiRecorders.get(i).toString());
                        wifiList.append(mWifiRecorders.get(i).SSID).append("\n");
                    }
                    mWifiNum.setText("Wifi num: " + mWifiRecorders.size());
                    mWifiList.setText(wifiList.toString());
                }
            }
        },3000);
    }

    private void getBluetoothList() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        if(bluetoothManager == null) {
            return;
        }

        mBluetoothRecorders.clear();
        bluetoothManager.getAdapter().getBluetoothLeScanner().startScan(mScanCallback);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bluetoothManager.getAdapter().getBluetoothLeScanner().stopScan(mScanCallback);

                if (mBluetoothRecorders.size() == 0) {
                    Log.d(TAG,"null");
                    Toast.makeText(mContext, "No Bluetooth found!", Toast.LENGTH_LONG).show();
                    mBluetoothNum.setText("0");
                }else {
                    StringBuffer bluetoothList = new StringBuffer();
                    bluetoothList.append("Bluetooth List:\n");

                    for (BluetoothDevice bluetoothDevice : mBluetoothRecorders.keySet()) {
                        bluetoothList.append(bluetoothDevice.toString()).append("\n");
                    }
                    mBluetoothNum.setText("Bluetooth num: " + mBluetoothRecorders.size());
                    mBluetoothList.setText(bluetoothList.toString());
                }
            }
        },3000);
    }

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, android.bluetooth.le.ScanResult result) {
            super.onScanResult(callbackType, result);

            if(result.getDevice() != null && !mBluetoothRecorders.containsKey(result.getDevice())) {
                mBluetoothRecorders.put(result.getDevice(),result);
                Log.d(TAG,"onScanResult result = " + result.toString());
            }
        }

        @Override
        public void onBatchScanResults(List<android.bluetooth.le.ScanResult> results) {
            super.onBatchScanResults(results);
            Log.d(TAG,"onBatchScanResults results = " + results.toString());
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d(TAG,"onScanFailed errorCode = " + errorCode);
        }
    };
}
