package com.floatwindow.permission.rom;

import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//检测是那种品牌的手机
public class RomUtil {
    private static final String TAG = RomUtil.class.getSimpleName();

    /**
     * 获取 emui 版本号
     *
     * @return
     */
    public static double getEmuiVersion() {
        try {
            String emuiVersion = getSystemProperty("ro.build.version.emui");
            String version = emuiVersion.substring(emuiVersion.indexOf("_") + 1);
            return Double.parseDouble(version);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 4.0;
    }

    /**
     * 获取小米 rom 版本号，获取失败返回 -1
     *
     * @return miui rom version code, if fail , return -1
     */
    public static int getMiuiVersion() {
        String version = getSystemProperty("ro.miui.ui.version.name");
        if (version != null) {
            try {
                return Integer.parseInt(version.substring(1));
            } catch (Exception e) {
                Log.e(TAG, "get miui version code error, version : " + version);
            }
        }
        return -1;
    }

    public static String getSystemProperty(String propName) {
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

    public static boolean isHuaweiRom() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("huawei");
    }

    //检测是不是小米手机
    public static boolean isXiaomiRom() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("xiaomi");
    }

    //检测是不是魅族手机
    public static boolean isMeizuRom() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("meizu");
    }

    //检测是不是360手机
    public static boolean is360Rom() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return (manufacturer.contains("qiku"))
                || (manufacturer.contains("360"));
    }

    //检测是不是oppo手机
    public static boolean isOppoRom() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("oppo");
    }

    //检测是不是vivo手机
    public static boolean isVivoRom() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("vivo");
    }

    //检测是不是三星手机
    public static boolean isSamsungRom() {
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        return manufacturer.contains("samsung");
    }

}
