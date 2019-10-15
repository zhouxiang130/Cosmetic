package com.yj.cosmetics.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

/**
 * Created by Suo on 2017/4/13.
 */

public class PermissionUtils {
    final public static int REQUEST_CODE_WRITE_EXTRONAL_STORAGE = 999;
    final public static int CAMERA_ = 998;
    final public static int REQUEST_CODE_READ_PHONE_STATE = 888;
    final public static int READ_EXTERNAL_STORAGE = 989;
    final public static int CALL_PHONE = 988;


    /*
    * 获取权限状态,判断是否有该权限
    * */
        public static boolean isPermissionAllowed(Context context,String permissionName){
            if (!(PermissionChecker.checkSelfPermission(context, permissionName) == PermissionChecker.PERMISSION_GRANTED)) {
                //进入到这里代表没有权限.
                return false;
            } else {
                //这里代表有权限
                LogUtils.i("有权限");
                return true;
            }
        }
    /*判断是否禁用权限提示框*/
    public static boolean isRemindAllowed(Activity activity,String permissioName,int requestCode){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissioName)) {
            //已经禁止提示了
            LogUtils.i("我进入到禁止里了");
            return false;
        } else {
            //没有禁止,提示请求权限
            return true;
            /*显示请求权限的提示框*/
//            ActivityCompat.requestPermissions(activity, new String[]{permissionName},requestCode);
        }
    }

    /*跳转到修改应用权限界面*/
    public static  void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            LogUtils.i("我进入intent了");
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
//        startActivity(localIntent);
//        finish();
    }
}
