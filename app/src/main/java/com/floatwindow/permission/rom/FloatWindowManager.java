package com.floatwindow.permission.rom;

import android.content.Context;

import com.floatwindow.permission.rom.fw.HuaWei;
import com.floatwindow.permission.rom.fw.Meizu;
import com.floatwindow.permission.rom.fw.Oppo;
import com.floatwindow.permission.rom.fw.Qiku;
import com.floatwindow.permission.rom.fw.Rom;
import com.floatwindow.permission.rom.fw.Vivo;
import com.floatwindow.permission.rom.fw.XiaoMi;

//悬浮窗管理工具
public class FloatWindowManager {

    private static final FloatWindowManager instance = new FloatWindowManager();

    public static FloatWindowManager getInstance() {
        return instance;
    }

    //检测手机悬浮窗权限
    public boolean checkPermission(Context context) {
        Rom rom = new Rom();
        if (Rom.isHuaWei()) {
            rom = new HuaWei();
        }
        else if (Rom.isXiaoMi()) {
            rom = new XiaoMi();
        }
        else if (Rom.is360()) {
            rom = new Qiku();
        }
        else if (Rom.isMeiZu()) {
            rom = new Meizu();
        }
        else if (Rom.isOppo()) {
            rom = new Oppo();
        }
        else if (Rom.isVivo()) {
            rom = new Vivo();
        }
        return rom.checkFloatingWindowPermission(context);

    }

    //申请权限
    public void applyPermission(Context context) {
        Rom rom = new Rom();
        if (Rom.isHuaWei()) {
            rom = new HuaWei();
        }
        else if (Rom.isXiaoMi()) {
            rom = new XiaoMi();
        }
        else if (Rom.is360()) {
            rom = new Qiku();
        }
        else if (Rom.isMeiZu()) {
            rom = new Meizu();
        }
        else if (Rom.isOppo()) {
            rom = new Oppo();
        }
        else if (Rom.isVivo()) {
            rom = new Vivo();
        }
        rom.applyFloatingWindowPermission(context);

    }


}
