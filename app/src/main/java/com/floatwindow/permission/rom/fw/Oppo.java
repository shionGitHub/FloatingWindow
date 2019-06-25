package com.floatwindow.permission.rom.fw;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Oppo extends Rom {


    @Override
    public void openAutoStartSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setComponent(new ComponentName(
                "com.coloros.safecenter",
                "com.coloros.safecenter.startupapp.StartupAppListActivity"));
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent.setComponent(new ComponentName(
                "com.coloros.safecenter",
                "com.coloros.privacypermissionsentry.PermissionTopActivity"));
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent.setComponent(new ComponentName(
                "com.oppo.safe",
                "com.oppo.safe.permission.startup.StartupAppListActivity"));
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }
        intent.setClassName(
                "com.color.safecenter",
                "com.color.safecenter.permission.startup.StartupAppListActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        }

    }

    @Override
    protected void applyFloatingWindowPermission_4_4(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //com.coloros.safecenter/.sysfloatwindow.FloatWindowListActivity
        //com.color.safecenter/com.color.safecenter.permission.floatwindow.FloatWindowListActivity
        ComponentName comp = new ComponentName("com.coloros.safecenter",
                "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");//悬浮窗管理页面
        intent.setComponent(comp);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }
        intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        comp = new ComponentName("com.color.safecenter",
                "com.color.safecenter.permission.floatwindow.FloatWindowListActivity");
        intent.setComponent(comp);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        comp = new ComponentName("com.color.safecenter",
                "com.color.safecenter.permission.PermissionManagerActivity");
        intent.setComponent(comp);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        }

    }


}
