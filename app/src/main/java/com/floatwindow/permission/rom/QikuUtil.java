package com.floatwindow.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Objects;

public class QikuUtil {

    private static final String TAG = QikuUtil.class.getSimpleName();

    /**
     * 检测 360 悬浮窗权限
     */
    public static boolean checkFloatWindowPermission(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24); //OP_SYSTEM_ALERT_WINDOW = 24;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean checkOp(Context context, int op) {
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
            Log.e("", "Below API 19 cannot invoke!");
        }
        return false;
    }


    public static void applyFloatWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            intent.setClassName("com.android.settings", "com.android.settings.Settings$OverlaySettingsActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
            }
            else {
                intent.setClassName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
                if (isIntentAvailable(intent, context)) {
                    context.startActivity(intent);
                }
                else {
                    context.startActivity(intent);
                }
            }
        }
        else {
            commonRomPermApplyInternal23(context);
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
