package com.floatwindow.permission.service;

import android.app.Activity;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import java.io.IOException;

public class WallpaperUtils {

    public static void setLiveWallpaper(Context context,
                                        Activity activity,
                                        int requestCode) {
        try {
            Intent intent = new Intent();
            int sdkInt = Build.VERSION.SDK_INT;
            if (sdkInt > 15) {
                intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        new ComponentName(context.getPackageName(),
                                LiveWallpaperService.class.getName()));
            }
            else {
                intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static boolean wallpaperIsUsed(Context ctx) {
        WallpaperInfo localWallpaperInfo = WallpaperManager.getInstance(ctx).getWallpaperInfo();
        return localWallpaperInfo != null
                && localWallpaperInfo.getPackageName().equals(ctx.getPackageName())
                && localWallpaperInfo.getServiceName().equals(LiveWallpaperService.class.getCanonicalName());
    }

    public static Bitmap getDefaultWallpaper(Context context) {
        if (isLivingWallpaper(context)) {
        }
        return ((BitmapDrawable) WallpaperManager.getInstance(context).getDrawable()).getBitmap();
    }

    public static boolean isLivingWallpaper(Context context) {
        return WallpaperManager.getInstance(context).getWallpaperInfo() != null;
    }

    public static boolean isMyLivingWallpaper(Context context) {
        WallpaperInfo wallpaperInfo = WallpaperManager.getInstance(context).getWallpaperInfo();
        if (wallpaperInfo != null) {
            if (context.getPackageName().equalsIgnoreCase(wallpaperInfo.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void clearWallpaper(Context context) {
        try {
            WallpaperManager.getInstance(context).clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
