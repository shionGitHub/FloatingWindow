package com.floatwindow.permission.rom.fw;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class ZTE extends Rom {

    @Override
    public void openAutoStartSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setComponent(new ComponentName(
                "com.zte.heartyservice",
                "com.zte.heartyservice.speedup.BackgroundAutorunAppActivity"));
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }
        intent.setComponent(new ComponentName(
                "com.zte.smartpower",
                "com.zte.smartpower.SelfStartActivity"));
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent.setComponent(new ComponentName(
                "com.zte.heartyservice",
                "com.zte.heartyservice.autorun.AppAutoRunManager"));
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        }

    }

}
