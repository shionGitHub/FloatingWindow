package com.floatwindow.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.floatwindow.permission.rom.AutoStartManager;
import com.floatwindow.permission.rom.FloatWindowManager;
import com.floatwindow.permission.service.WallpaperUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_PRESENT");
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");


        registerReceiver(receiver, filter);

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("android.intent.action.PACKAGE_RESTARTED");
        filter1.addAction("android.intent.action.PACKAGE_ADDED");
        filter1.addAction("android.intent.action.PACKAGE_REPLACED");
        filter1.addAction("android.intent.action.PACKAGE_REMOVED");
        filter1.addDataScheme("package");
        registerReceiver(appChangeReceiver, filter1);

    }

    AutoReceiver receiver = new AutoReceiver();
    AppChangeReceiver appChangeReceiver = new AppChangeReceiver();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(appChangeReceiver);
    }

    //开启自启动
    public void autoStart(View view) {
        AutoStartManager.getInstance().openAutoStartSetting(this);
    }

    //开启悬浮窗
    public void floatwindow(View view) {
        if (!FloatWindowManager.getInstance().checkPermission(this)) {
            FloatWindowManager.getInstance().applyPermission(this);
        }

    }

    //设置动态壁纸
    public void setWallpaper(View view) {
        Log.e("1234", "--------------isMyLivingWallpaper: " + WallpaperUtils.isMyLivingWallpaper(this));
        WallpaperUtils.setLiveWallpaper(this, this, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "壁纸设置成功!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "壁纸设置失败!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "请求壁纸失败!", Toast.LENGTH_SHORT).show();
        }

    }
}
