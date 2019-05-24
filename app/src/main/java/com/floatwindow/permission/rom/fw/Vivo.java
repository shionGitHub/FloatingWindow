package com.floatwindow.permission.rom.fw;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

public class Vivo extends Rom {


    @Override
    public void openAutoStartSetting(Context context) {
        Intent intent = new Intent();
        if ((Build.MODEL.contains("X9")
                || Build.MODEL.contains("x9"))
                && Build.VERSION.SDK_INT < 25) {
            try {
                intent.setComponent(new ComponentName(
                        "com.iqoo.secure",
                        "com.iqoo.secure.safeguard.PurviewTabActivity"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            intent.setComponent(new ComponentName(
                    "com.iqoo.secure",
                    "com.iqoo.secure.MainActivity"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e2) {
            try {
                intent.setComponent(new ComponentName(
                        "com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.PurviewTabActivity"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }


    }

    @Override
    public boolean checkFloatingWindowPermission(Context context) {
        try {
            int status = getVivoFloatPermissionStatus(context);
            return status == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.checkFloatingWindowPermission(context);
    }


    /*
     * 获取vivo悬浮窗权限状态.方法
     * @return 1没有打开;0是打开
     */
    private int getVivoFloatPermissionStatus(Context context) {
        String packageName = context.getPackageName();
        Uri uri = Uri.parse("content://com.iqoo.secure.provider.secureprovider/allowfloatwindowapp");
        String selection = "pkgname = ?";
        String[] selectionArgs = new String[]{packageName};
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        if (cursor == null)
            return getVivoFloatPermissionStatus2(context);
        //String[] columns = cursor.getColumnNames(); //pkgname, _id, hasshowed, setbyuser, currentlmode
        if (cursor.moveToFirst()) {
            int currentmode = cursor.getInt(cursor.getColumnIndex("currentlmode"));
            cursor.close();
            return currentmode;
        }
        else {
            cursor.close();
            return getVivoFloatPermissionStatus2(context);
        }
    }

    // 获取vivo悬浮窗权限状态.方法2
    private int getVivoFloatPermissionStatus2(Context context) {
        String packageName = context.getPackageName();
        Uri uri = Uri.parse("content://com.vivo.permissionmanager.provider.permission/float_window_apps");
        String selection = "pkgname = ?";
        String[] selectionArgs = new String[]{packageName};
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        if (cursor == null)
            return 1;
        //String[] columns = cursor.getColumnNames(); //hasshowed, setbyuser, pkguid, pkgname, _id, currentmode
        if (cursor.moveToFirst()) {
            int mode = cursor.getInt(cursor.getColumnIndex("currentmode"));
            cursor.close();
            return mode;
        }
        else {
            cursor.close();
            return 1;
        }

    }


    @Override
    protected void applyFloatingWindowPermission_4_4(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.MainActivity");
        //intent.setClassName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.FloatWindowManager");
        //intent.setClassName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.SoftwareManagerActivity");
        //intent.putExtra("packagename", getPackageName());
        context.startActivity(intent);
    }

    @Override
    protected void applyFloatingWindowPermission_6_0(Context context) {

        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("secure.intent.action.softPermissionDetail");
        intent.setClassName(
                "com.iqoo.secure",
                "com.iqoo.secure.safeguard.SoftPermissionDetailActivity");
        intent.putExtra("packagename", context.getPackageName());
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        intent = new Intent();
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("permission.intent.action.softPermissionDetail");
        intent.setClassName(
                "com.vivo.permissionmanager",
                "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        intent.putExtra("packagename", context.getPackageName());
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }

        super.applyFloatingWindowPermission_6_0(context);

    }


}
