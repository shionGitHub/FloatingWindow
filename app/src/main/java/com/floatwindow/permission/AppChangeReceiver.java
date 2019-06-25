package com.floatwindow.permission;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;

public class AppChangeReceiver extends BroadcastReceiver {
    private static final String TAG = AppChangeReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        showActionContent(context, intent);
    }

    private void showActionContent(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action) || action == null) {
            action = "";
        }
        switch (action) {
            case "android.intent.action.PACKAGE_RESTARTED":
                String packageName = intent.getData().getSchemeSpecificPart();
                Log.e(TAG, "------: " + action + " -------: " + "重启应用时: " + packageName);
                break;
            case "android.intent.action.PACKAGE_ADDED":
                String packageName1 = intent.getData().getSchemeSpecificPart();
                Log.e(TAG, "------: " + action + " -------: " + "安装应用时: " + packageName1);

                break;
            case "android.intent.action.PACKAGE_REPLACED":
                String packageName2 = intent.getData().getSchemeSpecificPart();
                Log.e(TAG, "------: " + action + " -------: " + "更新应用时: " + packageName2);
                break;
            case "android.intent.action.PACKAGE_REMOVED":
                String packageName3 = intent.getData().getSchemeSpecificPart();
                Log.e(TAG, "------: " + action + " -------: " + "卸载应用时: " + packageName3);
                break;
            default:
                Log.e(TAG, "-------其他的Action---------:[" + action + "]");
                break;
        }

    }


}
