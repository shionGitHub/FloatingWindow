package com.floatwindow.permission.rom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.util.Objects;

public class FloatManager {

    private static volatile FloatManager instance;

    public static FloatManager getInstance() {
        if (instance == null) {
            synchronized (FloatManager.class) {
                if (instance == null) {
                    instance = new FloatManager();
                }
            }
        }
        return instance;
    }

    //检测权限
    public boolean checkPermission(Context context) {
        if (Build.VERSION.SDK_INT < 23) {//6.0一下的版本
            if (RomUtil.isXiaomiRom()) {
                return XiaomiUtil.checkFloatWindowPermission(context);
            }
            else if (RomUtil.isMeizuRom()) {
                return MeizuUtil.checkFloatWindowPermission(context);
            }
            else if (RomUtil.isHuaweiRom()) {
                return HuaweiUtil.checkFloatWindowPermission(context);
            }
            else if (RomUtil.is360Rom()) {
                return QikuUtil.checkFloatWindowPermission(context);
            }
            else if (RomUtil.isOppoRom()) {
                return OppoUtil.checkFloatWindowPermission(context);
            }
            else if (RomUtil.isVivoRom()) {
                return VivoUtil.checkFloatWindowPermission(context);
            }
            else {
                return false;//......
            }
        }
        else {
            if (RomUtil.isVivoRom()) {//vivo
                return VivoUtil.checkFloatWindowPermission(context);
            }
            if (RomUtil.isMeizuRom()) {//meizu
                return MeizuUtil.checkFloatWindowPermission(context);
            }
            return commonROMPermissionCheck23(context);
        }

    }

    @TargetApi(23)
    private boolean commonROMPermissionCheck23(Context context) {
        return Settings.canDrawOverlays(context);
    }


    //申请权限
    public void applyPermission(Context context) {
        if (RomUtil.isXiaomiRom()) {
            XiaomiUtil.applyFloatWindowPermission(context);
        }
        else if (RomUtil.isMeizuRom()) {
            MeizuUtil.applyFloatWindowPermission(context);
        }
        else if (RomUtil.isHuaweiRom()) {
            HuaweiUtil.applyFloatWindowPermission(context);
        }
        else if (RomUtil.is360Rom()) {
            QikuUtil.applyFloatWindowPermission(context);
        }
        else if (RomUtil.isOppoRom()) {
            OppoUtil.applyFloatWindowPermission(context);
        }
        else if (RomUtil.isVivoRom()) {
            VivoUtil.applyFloatWindowPermission(context);
        }
        else {
            //......
        }


    }


    @TargetApi(Build.VERSION_CODES.M)
    private void commonRomPermApplyInternal23(Context context) {
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

}
