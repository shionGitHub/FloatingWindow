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

public class XiaomiUtil {

    private static final String TAG = "MiuiUtils";

    /**
     * 获取小米 rom 版本号，获取失败返回 -1
     *
     * @return miui rom version code, if fail , return -1
     */
    public static int getMiuiVersion() {
        String version = RomUtil.getSystemProperty("ro.miui.ui.version.name");
        if (version != null) {
            try {
                return Integer.parseInt(version.substring(1));
            } catch (Exception e) {
                Log.e(TAG, "get miui version code error, version : " + version);
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        return -1;
    }

    /**
     * 检测 miui 悬浮窗权限
     */
    public static boolean checkFloatWindowPermission(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24); //OP_SYSTEM_ALERT_WINDOW = 24;
        }
        else {
            return true;
        }
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
            Log.e(TAG, "Below API 19 cannot invoke!");
        }
        return false;
    }


    public static void applyFloatWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Intent intent = xiaomiRomPermApply22(context);
            context.startActivity(intent);
        }
        else {
            commonRomPermApplyInternal23(context);
        }
    }

    private static Intent xiaomiRomPermApply22(Context context) {
        int versionCode = getMiuiVersion();
        if (versionCode == 5) {
            return miPerm22V5(context);
        }
        else if (versionCode == 6) {
            return miPerm22V6(context);
        }
        else if (versionCode == 7) {
            return miPerm22V7(context);
        }
        else if (versionCode == 8) {
            return miPerm22V8(context);
        }
        else if (versionCode == 9) {
            return miPerm22V8(context);
        }
        else {
            Log.e("TAG", "this is a special MIUI rom version, its version code " + versionCode);
        }

        return new Intent();
    }

    private static Intent miPerm22V8(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionsEditorActivity");
        //intent.setPackage("com.miui.securitycenter");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (isIntentAvailable(intent, context)) {
            return intent;
        }

        intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setPackage("com.miui.securitycenter");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;

    }

    private static Intent miPerm22V7(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;

    }

    private static Intent miPerm22V6(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private static Intent miPerm22V5(Context context) {
        String packageName = context.getPackageName();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
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
