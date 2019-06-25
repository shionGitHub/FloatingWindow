package com.floatwindow.permission;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;

public class AutoReceiver extends BroadcastReceiver {
    private static final String TAG = AutoReceiver.class.getSimpleName();

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
            case "android.intent.action.BOOT_COMPLETED":
                Log.e(TAG, "------: " + action + " -------: " + "设备开机后");
                break;
            case "android.intent.action.USER_PRESENT":
                Log.e(TAG, "------: " + action + " -------: " + "用户登录时，屏幕锁打开之后");
                break;
            //case "android.net.conn.CONNECTIVITY_CHANGE":
            case ConnectivityManager.CONNECTIVITY_ACTION:
                Log.e(TAG, "------: " + action + " -------: " + "网络连接更改时");
                break;
            case "android.intent.action.ACTION_POWER_CONNECTED":
                Log.e(TAG, "------: " + action + " -------: " + "插入电源时");
                break;
            case "android.intent.action.ACTION_POWER_DISCONNECTED":
                Log.e(TAG, "------: " + action + " -------: " + "拨出电源时");
                break;
            case "android.intent.action.MEDIA_MOUNTED":
                Log.e(TAG, "------: " + action + " -------: " + "安装媒体时");
                break;
            case "android.intent.action.BATTERY_CHANGED":
                Log.e(TAG, "------: " + action + " -------: " + "电池电量变化时");
                break;
            case "android.intent.action.NEW_OUTGOING_CALL":
                Log.e(TAG, "------: " + action + " -------: " + "新外拨电话时");
                break;
            case "android.intent.action.PHONE_STATE":
                Log.e(TAG, "------: " + action + " -------: " + "通话状态更改时");
                break;
            case "android.intent.action.LOCALE_CHANGED":
                Log.e(TAG, "------: " + action + " -------: " + "语言更改时");
                break;
            case "android.intent.action.DATE_CHANGED":
                Log.e(TAG, "------: " + action + " -------: " + "日期更改时");
                break;
            case "android.intent.action.MEDIA_SCANNER_STARTED":
                Log.e(TAG, "------: " + action + " -------: " + "开始媒体扫描时");
                break;
            case "android.intent.action.MEDIA_SCANNER_FINISHED":
                Log.e(TAG, "------: " + action + " -------: " + "媒体扫描完成时");
                break;
            case "android.intent.action.TIMEZONE_CHANGED":
                Log.e(TAG, "------: " + action + " -------: " + "时区更改时");
                break;
            case "android.intent.action.TIME_SET":
                Log.e(TAG, "------: " + action + " -------: " + "时间更改时");
                //if (!Account.isChild) {//父母端拉取最新时间设置
                //    LocalBroadcastManager manager = LocalBroadcastManager.getInstance(Factory.app());
                //    manager.sendBroadcast(new Intent(Common.Constant.ACTION_CONTROL_DATA_PARENT));
                //}
                break;
            case "android.intent.action.SCREEN_ON":
                Log.e(TAG, "------: " + action + " -------: " + "打开屏幕时");
                break;
            case "android.intent.action.SCREEN_OFF":
                Log.e(TAG, "------: " + action + " -------: " + "关闭屏幕时");
                break;
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
