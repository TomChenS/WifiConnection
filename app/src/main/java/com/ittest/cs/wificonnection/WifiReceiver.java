package com.ittest.cs.wificonnection;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.ittest.cs.wificonnection.Utils.SpUtil;

/**
 * Created by 雷神 on 2017/3/6.
 */
public class WifiReceiver extends BroadcastReceiver {
    private static int NOTIFY_ID = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
            //信号强度改变
        } else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {//wifi连接上与否

            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
//                System.out.println("wifi网络连接断开");
//                if (SpUtil.getBoolean(context,"检测")&& WifiUtil.getWifiName(context).equals(SpUtil.getString(context,"wifi"))){
//                    sendcation(context,"".equals(SpUtil.getString(context,"内容"))?"记得打卡":SpUtil.getString(context,"内容"));
//
//                }

                if (SpUtil.getBoolean(context,"检测")){
                    sendNotification(context,"".equals(SpUtil.getString(context,"内容"))?"记得打卡":SpUtil.getString(context,"内容"));

                }


            } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {

                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //获取当前wifi名称
//                System.out.println("连接到网络 " + wifiInfo.getSSID());

            }

        } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {//wifi打开与否
            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
            if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
                // System.out.println("系统关闭wifi");
            } else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                // System.out.println("系统开启wifi");
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(Context context, String message){
        //【1】获取Notification 管理器的参考
        NotificationManager notifyMgr= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //【2】设置通知。PendingIntent表示延后触发，是在用户下来状态栏并点击通知时触发，触发时PendingIntent发送intent，默认打开MainActivity。
        Intent intent = new Intent(context,MainActivity.class);
//        Intent intent = new Intent(Intent.ACTION_VIEW);       此为打开浏览器特定页面的方法
//        intent.setData(Uri.parse("http://www.google.com"));
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(message)
                .setContentTitle("wifi检测工具")
                .setContentText(message)
                .setContentIntent(pi)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL; //点击后删除，如果是FLAG_NO_CLEAR则不删除，FLAG_ONGOING_EVENT用于某事正在进行，例如电话，具体查看参考。
        //【3】发送通知到通知管理器。第一个参数是这个通知的唯一标识，通过这个id可以在以后cancel通知，更新通知（发送一个具有相同id的新通知）。这个id在应用中应该是唯一的。
        notifyMgr.notify(NOTIFY_ID, notification);


    }
}
