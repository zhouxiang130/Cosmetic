package com.yj.cosmetics.util;

import android.app.Application;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Suo on 2017/4/12.
 */

public class AppCrashUtils implements Thread.UncaughtExceptionHandler {
    private static AppCrashUtils crashUtils = new AppCrashUtils();
    private static Application mApplication;

    private AppCrashUtils() {
    }

    public static void init(Application application) {
        mApplication = application;
        Thread.setDefaultUncaughtExceptionHandler(crashUtils);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
//        try {
//            // 这是把错误信息写到本地的操作
//            writeLog(throwable);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 2 .获取 相关的信息
        /**
         * 获取当前的版本信息
         * 获取 出错手机硬件信息
         * 程序出错的信息
         */
        String version = GetInfoUtils.getAPPVersion(mApplication);
        String mobileInfo = GetInfoUtils.getMobileInfo();
        String errorInfo = getErrorInfo(throwable);
        //  3. 把这些相关的信息传递到服务器上面
        // TODO
        LogUtils.i("app 版本信息" + version + "手机信息:" + mobileInfo + "出错信息:" + errorInfo);
//        Process.killProcess(Process.myPid());
    }

    private String getErrorInfo(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        throwable.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }

//    private void writeLog(Throwable throwable) throws Exception {
//        FileUtils.WriteCrashLog(mApplication.getFileStreamPath(Constant.LOG_FILE), throwable);
//    }
}