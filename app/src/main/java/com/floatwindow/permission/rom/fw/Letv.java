package com.floatwindow.permission.rom.fw;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Letv extends Rom {

    @Override
    public void openAutoStartSetting(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(
                    "com.letv.android.letvsafe",
                    "com.letv.android.letvsafe.AutobootManageActivity"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
