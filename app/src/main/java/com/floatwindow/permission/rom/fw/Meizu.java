package com.floatwindow.permission.rom.fw;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class Meizu extends Rom {

    @Override
    public void openAutoStartSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //5,7,8
        intent.setClassName(
                "com.meizu.safe",
                "com.meizu.safe.permission.SmartBGActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }
        //6
        intent.setClassName(
                "com.meizu.safe",
                "com.meizu.safe.permission.AutoRunActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", context.getPackageName());
        }
        else {
            intent.setClassName(
                    "com.meizu.safe",
                    "com.meizu.safe.permission.PermissionMainActivity");
        }
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        }

    }

    @Override
    public void applyFloatingWindowPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.putExtra("packageName", context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        }
        else {
            super.applyFloatingWindowPermission_6_0(context);
        }
    }


}
