package com.floatwindow.permission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.floatwindow.permission.rom.AutoStartManager;
import com.floatwindow.permission.rom.FloatWindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}
