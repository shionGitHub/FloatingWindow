package com.floatwindow.permission.rom.fw;

import android.content.Context;
import android.content.Intent;

public class Meizu extends Rom {


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
