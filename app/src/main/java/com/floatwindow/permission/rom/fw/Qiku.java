package com.floatwindow.permission.rom.fw;

import android.content.Context;
import android.content.Intent;

public class Qiku extends Rom {


    @Override
    public void openAutoStartSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.yulong.android.coolsafe",
                "com.yulong.android.coolsafe.ui.activity.autorun.AutoRunListActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent.setClassName("com.android.settings",
                "com.android.settings.Settings$OverlaySettingsActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        }

    }

    @Override
    protected void applyFloatingWindowPermission_4_4(Context context) {
        Intent intent = new Intent();
        intent.setClassName(
                "com.android.settings",
                "com.android.settings.Settings$OverlaySettingsActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        }
        else {
            intent.setClassName(
                    "com.qihoo360.mobilesafe",
                    "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
            }
        }

    }

}
