package com.ittest.cs.wificonnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ittest.cs.wificonnection.Utils.SpUtil;

/**
 * Created by 雷神 on 2017/3/29.
 */

public class BootedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //监听开机启动的广播，接收到开机广播后开启监听wifi的服务
        if (SpUtil.getBoolean(context, "检测")) {
            context.startService(new Intent(context, WifiService.class));
        }
    }
}
