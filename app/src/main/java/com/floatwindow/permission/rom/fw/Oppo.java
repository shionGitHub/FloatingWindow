package com.floatwindow.permission.rom.fw;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Oppo extends Rom {


    @Override
    protected void applyFloatingWindowPermission_4_4(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName(
                "com.coloros.safecenter",
                "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");//悬浮窗管理页面
        intent.setComponent(comp);
        context.startActivity(intent);

    }


}
