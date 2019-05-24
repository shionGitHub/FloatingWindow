package com.floatwindow.permission.rom.fw;

import android.content.Context;
import android.content.Intent;

public class OnePlus extends Rom {

    @Override
    public void openAutoStartSetting(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(
                "com.oneplus.security",
                "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        }

    }


}
