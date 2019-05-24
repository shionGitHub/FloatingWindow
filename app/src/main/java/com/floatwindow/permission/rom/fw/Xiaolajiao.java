package com.floatwindow.permission.rom.fw;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Xiaolajiao extends Rom {

    @Override
    public void openAutoStartSetting(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.zhuoyi.security.lite",
                    "com.freeme.sc.light.la.LA_AutoRunManager"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyFloatingWindowPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.zhuoyi.security.lite",
                    "com.freeme.sc.light.Light_SnowActivity"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
