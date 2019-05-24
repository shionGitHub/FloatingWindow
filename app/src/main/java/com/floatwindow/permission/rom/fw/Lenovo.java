package com.floatwindow.permission.rom.fw;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Lenovo extends Rom {

    @Override
    public void openAutoStartSetting(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.lenovo.security",
                    "com.lenovo.security.homepage.HomePageActivity"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
