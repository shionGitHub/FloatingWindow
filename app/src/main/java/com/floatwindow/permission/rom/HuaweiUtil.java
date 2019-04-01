package com.floatwindow.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Objects;

public class HuaweiUtil {

    private static final String TAG = HuaweiUtil.class.getSimpleName();

    /**
     * 检测 Huawei 悬浮窗权限
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
            Log.e(TAG, "Below API 19 cannot invoke!");
        }
        return false;
    }


    public static void applyFloatWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            //ComponentName comp = new ComponentName("com.huawei.systemmanager",
            //"com.huawei.permissionmanager.ui.SingleAppActivity");//华为权限管理，跳转到指定app的权限管理位置需要华为接口权限，未解决
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");//悬浮窗管理页面
            intent.setComponent(comp);
            if (RomUtil.getEmuiVersion() == 3.1) {
                //emui 3.1 的适配
            }
            else {
                //emui 3.0 的适配
                comp = new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity");//悬浮窗管理页面
                intent.setComponent(comp);
            }
            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
                return;
            }

            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            ComponentName componentName = new ComponentName("com.huawei.systemmanager",
                    "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理，跳转到本app的权限管理页面,这个需要华为接口权限，未解决
            //ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");//悬浮窗管理页面
            intent.setComponent(componentName);
            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
                return;
            }

            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            ComponentName componentName1 = new ComponentName("com.huawei.systemmanager",
                    "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理，跳转到本app的权限管理页面,这个需要华为接口权限，未解决
            //ComponentName comp = new ComponentName("com.huawei.systemmanager","com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");//悬浮窗管理页面
            intent.setComponent(componentName1);
            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
                return;
            }

            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName componentName2 = new ComponentName("com.Android.settings", "com.android.settings.permission.TabItem");//权限管理页面 android4.4
            //ComponentName comp = new ComponentName("com.android.settings","com.android.settings.permission.single_app_activity");//此处可跳转到指定app对应的权限管理页面，但是需要相关权限，未解决
            intent.setComponent(componentName2);
            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
                return;
            }

            context.startActivity(intent);
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
