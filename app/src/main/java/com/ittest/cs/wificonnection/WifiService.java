package com.ittest.cs.wificonnection;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by 雷神 on 2017/3/6.
 */
public class WifiService extends Service {

    private WifiReceiver mWifiReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification1 = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("标题")
                .setContentTitle("wifi检测工具")
                .setContentText("记得打卡")
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();

        startForeground(1000, notification1);
        startWifiRrceiver();
    }

    private void startWifiRrceiver() {
        //        创建广播接收者实例
        mWifiReceiver = new WifiReceiver();
//        创建意图过滤器对象
        IntentFilter filter = new IntentFilter();
//        添加事件类型
        filter.addAction("android.net.wifi.RSSI_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
//        动态注册广播接收者
        registerReceiver(mWifiReceiver, filter);
    }

    @Override
    public void onDestroy() {
//        当服务销毁的时候取消注册
        unregisterReceiver(mWifiReceiver);
        super.onDestroy();
    }
}