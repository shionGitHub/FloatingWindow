package com.floatwindow.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Objects;

public class VivoUtil {

    private static final String TAG = VivoUtil.class.getSimpleName();


    public static boolean checkFloatWindowPermission(Context context) {
        try {
            int status = getFloatPermissionStatus(context);
            return status == 0;
        } catch (Exception e) {
            if (Build.VERSION.SDK_INT >= 23)
                return checkOp23(context);
            else if (Build.VERSION.SDK_INT >= 19)
                return checkOp19(context, 24); //OP_SYSTEM_ALERT_WINDOW = 24;
            return true;
        }
    }

    /**
     * 获取vivo悬浮窗权限状态.方法1
     *
     * @return 1或其他是没有打开;0是打开.该状态的定义和{@link AppOpsManager#MODE_ALLOWED},MODE_IGNORED等值差不多,自行查阅源码
     */
    private static int getFloatPermissionStatus(Context context) {
        String packageName = context.getPackageName();
        Uri uri = Uri.parse("content://com.iqoo.secure.provider.secureprovider/allowfloatwindowapp");
        String selection = "pkgname = ?";
        String[] selectionArgs = new String[]{packageName};
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        if (cursor == null)
            return getFloatPermissionStatus2(context);
        //String[] columns = cursor.getColumnNames(); //pkgname, _id, hasshowed, setbyuser, currentlmode
        if (cursor.moveToFirst()) {
            int currentmode = cursor.getInt(cursor.getColumnIndex("currentlmode"));
            cursor.close();
            return currentmode;
        }
        else {
            cursor.close();
            return getFloatPermissionStatus2(context);
        }
    }

    /**
     * 获取vivo悬浮窗权限状态.方法2
     *
     * @return 1或其他是没有打开;0是打开.该状态的定义和{@link AppOpsManager#MODE_ALLOWED},MODE_IGNORED等值差不多,自行查阅源码
     */
    private static int getFloatPermissionStatus2(Context context) {
        String packageName = context.getPackageName();
        Uri uri = Uri.parse("content://com.vivo.permissionmanager.provider.permission/float_window_apps");
        String selection = "pkgname = ?";
        String[] selectionArgs = new String[]{packageName};
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        if (cursor == null)
            return 1;
        //String[] columns = cursor.getColumnNames(); //hasshowed, setbyuser, pkguid, pkgname, _id, currentmode
        if (cursor.moveToFirst()) {
            int currentmode = cursor.getInt(cursor.getColumnIndex("currentmode"));
            cursor.close();
            return currentmode;
        }
        else {
            cursor.close();
            return 1;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkOp23(Context context) {
        return Settings.canDrawOverlays(context);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean checkOp19(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class clazz = AppOpsManager.class;
                Method method = clazz.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                return AppOpsManager.MODE_ALLOWED == (int) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        else {
            Log.e(TAG, "Below API 19 cannot invoke!");
        }
        return false;
    }


    public static void applyFloatWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName("com.iqoo.secure", "com.iqoo.secure.MainActivity");
            //intent.setClassName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.FloatWindowManager");
            //intent.setClassName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.SoftwareManagerActivity");
            //intent.putExtra("packagename", getPackageName());
            context.startActivity(intent);
        }
        else {
            {
                Intent intent = new Intent();
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("secure.intent.action.softPermissionDetail");
                intent.setClassName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity");
                intent.putExtra("packagename", context.getPackageName());
                if (isIntentAvailable(intent, context)) {
                    context.startActivity(intent);
                }

                intent = new Intent();
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("permission.intent.action.softPermissionDetail");
                intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
                intent.putExtra("packagename", context.getPackageName());
                if (isIntentAvailable(intent, context)) {
                    context.startActivity(intent);
                }

                commonRomPermApplyInternal23(context);

            }


        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void commonRomPermApplyInternal23(Context context) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String m = Build.MANUFACTURER.toLowerCase();
        if ((Objects.equals(m, "huawei"))
                && (Build.VERSION.SDK_INT >= 26)) {
        }
        else {
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        }
        context.startActivity(intent);
    }


    private static boolean isIntentAvailable(Intent intent, Context context) {
        if (intent == null) {
            return false;
        }
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

}
