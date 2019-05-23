package com.floatwindow.permission.rom.fw;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;


//华为手机悬浮窗
public class HuaWei extends Rom {


    @Override
    protected void applyFloatingWindowPermission_6_0(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//华为8.0以上的处理
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        else {
            super.applyFloatingWindowPermission_6_0(context);
        }
    }


    @Override
    protected void applyFloatingWindowPermission_4_4(Context context) {
        {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //"com.huawei.permissionmanager.ui.SingleAppActivity");//华为权限管理，跳转到指定app的权限管理位置需要华为接口权限
            ComponentName comp = new ComponentName(
                    "com.huawei.systemmanager",
                    "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");//悬浮窗管理页面
            intent.setComponent(comp);
            if (getEMUIVersion() == 3.1) {
                //emui 3.1 的适配
            }
            else {
                //emui 3.0 的适配
                comp = new ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.notificationmanager.ui.NotificationManagmentActivity");//悬浮窗管理页面
                intent.setComponent(comp);
            }
            if (isIntentAvailable(intent, context)) {//存在这个Intent
                context.startActivity(intent);
                return;
            }

            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //华为权限管理，跳转到本app的权限管理页面,这个需要华为接口权限，未解决
            ComponentName componentName = new ComponentName(
                    "com.huawei.systemmanager",
                    "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(componentName);
            if (isIntentAvailable(intent, context)) {//存在的话
                context.startActivity(intent);
                return;
            }

            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName componentName1 = new ComponentName(
                    "com.huawei.systemmanager",
                    "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(componentName1);
            if (isIntentAvailable(intent, context)) {//存在的话
                context.startActivity(intent);
                return;
            }

            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName componentName2 = new ComponentName(
                    "com.android.settings",
                    "com.android.settings.permission.TabItem");
            intent.setComponent(componentName2);
            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
            }

        }
    }


    // 获取华为EMUI系统版本号
    private double getEMUIVersion() {
        try {
            String emuiVersion = getSystemProperty("ro.build.version.emui");
            String version = emuiVersion.substring(emuiVersion.indexOf("_") + 1);
            return Double.parseDouble(version);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 4.0;
    }


}
