package com.floatwindow.permission.rom;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.floatwindow.permission.rom.fw.Gionee;
import com.floatwindow.permission.rom.fw.HuaWei;
import com.floatwindow.permission.rom.fw.Lenovo;
import com.floatwindow.permission.rom.fw.Letv;
import com.floatwindow.permission.rom.fw.Meizu;
import com.floatwindow.permission.rom.fw.Oppo;
import com.floatwindow.permission.rom.fw.Qiku;
import com.floatwindow.permission.rom.fw.Rom;
import com.floatwindow.permission.rom.fw.Samsung;
import com.floatwindow.permission.rom.fw.Smartisan;
import com.floatwindow.permission.rom.fw.XiaoMi;

//开启自启动管理类
public class AutoStartManager {

    private static final AutoStartManager instance = new AutoStartManager();

    public static AutoStartManager getInstance() {
        return instance;
    }


    public void openAutoStartSetting(Context context) {
        if (context == null) {
            return;
        }
        Rom rom = null;
        if (Rom.isGionee()) {
            rom = new Gionee();

        }
        else if (Rom.isHuaWei()) {
            rom = new HuaWei();
        }
        else if (Rom.isLenvor()) {
            rom = new Lenovo();
        }
        else if (Rom.isLetv()) {
            rom = new Letv();
        }
        else if (Rom.isMeiZu()) {
            rom = new Meizu();
        }
        else if (Rom.isOppo()) {
            rom = new Oppo();
        }
        else if (Rom.is360()) {
            rom = new Qiku();
        }
        else if (Rom.isSamsung()) {
            rom = new Samsung();
        }
        else if (Rom.isSmartisan()) {
            rom = new Smartisan();
        }
        else if (Rom.isVivo()) {
            rom = new XiaoMi();
        }
        else if (Rom.isXiaolajiao()) {
            rom = new XiaoMi();
        }
        else if (Rom.isXiaoMi()) {
            rom = new XiaoMi();
        }
        else if (Rom.isZTE()) {
            rom = new XiaoMi();
        }

        if (rom == null) {
            rom = new Rom();
        }
        rom.openAutoStartSetting(context);

    }


}
