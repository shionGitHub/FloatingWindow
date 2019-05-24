package com.floatwindow.permission.rom.fw;

import android.content.Context;
import android.content.Intent;

public class Samsung extends Rom {

    //三星手机打开自启动的前一个页面，列出全部情形
    @Override
    public void openAutoStartSetting(Context context) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(
                "com.samsung.android.sm",
                "com.samsung.android.sm.ui.ram.RamActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent.setClassName(
                "com.samsung.android.sm",
                "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent.setClassName(
                "com.samsung.android.sm_cn",
                "com.samsung.android.sm.ui.ram.AutoRunActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent.setClassName(
                "com.samsung.android.sm_cn",
                "com.samsung.android.sm.ui.appmanagement.AppManagementActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent.setClassName(
                "com.samsung.android.sm_cn",
                "com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent.setClassName(
                "com.samsung.android.sm_cn",
                "com.samsung.android.sm.ui.scoreboard.CstyleScoreBoardActivity");
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent.setClassName(
                "com.samsung.android.sm",
                "com.samsung.android.sm.ui.dashboard.SmartManagerDashBoardActivity");
        context.startActivity(intent);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        }


    }


}
