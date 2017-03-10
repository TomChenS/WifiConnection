package com.ittest.cs.wificonnection.Utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by 雷神 on 2017/3/6.
 */
public class WifiUtil {
    private static String sWifiName;

    public static String getWifiName(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if ("<unknown ssid>".equals(wifiInfo.getSSID())) {
            sWifiName = "未连接";
        } else {
            sWifiName = wifiInfo.getSSID();
        }
        return sWifiName;
    }
}
