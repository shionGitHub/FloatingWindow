package com.floatwindow.permission.rom.fw;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;

//公共的手机型号
public class Rom {

    protected String TAG = getClass().getSimpleName();

    //检测4.4以后的悬浮窗的权限(适用于大部分机型)
    public boolean checkFloatingWindowPermission(Context context) {
        if (context == null) {
            return false;
        }
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.M) {//6.0以后的检测方法
            return Settings.canDrawOverlays(context);
        }
        else if (sdkInt >= Build.VERSION_CODES.KITKAT) {//4.4以后检测方法
            //checkOp该方法已经被@Hide,这里采用反射获取
            AppOpsManager ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Class<AppOpsManager> c = AppOpsManager.class;
            try {
                Method method = c.getDeclaredMethod("checkOp",
                        int.class, int.class, String.class);
                int op = 24;
                int uid = Binder.getCallingUid();
                String packageName = context.getPackageName();
                int mode = (int) method.invoke(ops, op, uid, packageName);
                return mode == AppOpsManager.MODE_ALLOWED;
            } catch (Exception e) {
                Log.e(TAG, "打印异常的信息：----\n" + Log.getStackTraceString(e));
                return false;
            }
        }
        else {//4.4以前的手机没什么限制
            return true;
        }

    }

    //申请悬浮窗的权限
    public void applyFloatingWindowPermission(Context context) {
        if (context == null) {
            return;
        }
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.M) {//6.0
            applyFloatingWindowPermission_6_0(context);
        }
        else if (sdkInt >= Build.VERSION_CODES.KITKAT) {//4.4
            applyFloatingWindowPermission_4_4(context);
        }
        //4.4以前的手机无需处理了

    }

    //专门处理6.0以上的权限的申请
    @TargetApi(Build.VERSION_CODES.M)
    protected void applyFloatingWindowPermission_6_0(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    //专门处理4.4~6.0的权限的申请 ,一般手机都要单独适配的
    protected void applyFloatingWindowPermission_4_4(Context context) {
        try {
            Intent intent = new Intent(
                    "android.settings.action.MANAGE_OVERLAY_PERMISSION",
                    Uri.parse("package:" + context.getPackageName())
            );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch (Exception e) {
            Log.e(TAG, "4.4权限跳转失败！");
        }


    }

    //判断当前的Intent是否存在
    protected boolean isIntentAvailable(Intent i, Context context) {
        if (i == null || context == null) {
            return false;
        }

        PackageManager pm = context.getPackageManager();
        //List<ResolveInfo> list = pm.queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY);
        //if (list.size() == 0) {
        //    return false;
        //}

        ActivityInfo activityInfo = i.resolveActivityInfo(pm, i.getFlags());
        if (activityInfo == null
                || !activityInfo.exported) {
            return false;
        }
        String permission = activityInfo.permission;
        if (!TextUtils.isEmpty(permission)) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    //获取系统属性 系统的版本信息
    protected String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.e(TAG, "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }


    //添加开启自启动的方法 ---默认打开设置页面
    public void openAutoStartSetting(Context context) {
        Intent i = new Intent(Settings.ACTION_SETTINGS);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static boolean isHuaWei() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("huawei");
    }

    //检测是不是小米手机
    public static boolean isXiaoMi() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("xiaomi");
    }

    //检测是不是魅族手机
    public static boolean isMeiZu() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("meizu");
    }

    //检测是不是360手机
    public static boolean is360() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return (manufacturer.contains("qiku"))
                || (manufacturer.contains("360"));
    }

    //检测是不是oppo手机
    public static boolean isOppo() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("oppo");
    }

    //检测是不是vivo手机
    public static boolean isVivo() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("vivo");
    }

    //检测是否是小辣椒
    public static boolean isXiaolajiao() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("xiaolajiao");
    }

    //检测是三星手机
    public static boolean isSamsung() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("samsung");
    }

    //检测是金立手机
    public static boolean isGionee() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("gionee");
    }

    //检测是联想手机
    public static boolean isLenvor() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("gionee");
    }

    //检测是联想手机
    public static boolean isLetv() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("letv");
    }

    //检测是锤子手机
    public static boolean isSmartisan() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("smartisan");
    }

    //检测是中兴手机
    public static boolean isZTE() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("zte");
    }


}
