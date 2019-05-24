# 悬浮窗和自启动权限总结

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
# 公共的手机权限类Rom中的方法
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
