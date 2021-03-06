package com.floatwindow.permission.rom.fw;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Gionee extends Rom {

    @Override
    public void openAutoStartSetting(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.gionee.softmanager",
                    "com.gionee.softmanager.MainActivity"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
