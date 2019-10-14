package com.yj.cosmetics;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yj.cosmetics.base.Constant;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.WindowDisplayManager;

import java.util.Stack;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

//import com.tencent.tinker.loader.app.ApplicationLike;
//import com.tinkerpatch.sdk.TinkerPatch;
//import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
//import com.yj.robust.tinker.FetchPatchHandler;


/**
 * Created by Suo on 2017/4/11.
 */

public class MyApplication extends MultiDexApplication {
	public static Stack<Activity> atyStack = new Stack<>();
	public static MyApplication mApplication;
	public static float ratio;
	public static boolean orderDetial = true;
	public static boolean orderListRefresh = false;
	public static boolean orderlistReceiver = false;
//	private ApplicationLike tinkerApplicationLike;
	public static IWXAPI mWxApi;

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		initialize();
	   /* if (!BuildConfig.DEBUG) {
	        AppCrashUtils.init(this);
        }*/
		ShareSDK.initSDK(this);
		ToastUtils.init(this);
		JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
		JPushInterface.init(getApplicationContext());
		mWxApi = WXAPIFactory.createWXAPI(this, null);
		// 将该app注册到微信
		mWxApi.registerApp(Constant.APP_ID);
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	private void initialize() {
		ratio = WindowDisplayManager.getRatio(this);
	}


	public static synchronized MyApplication getApplication() {
		return mApplication;
	}

	public static void push(Activity aty) {
		atyStack.push(aty);
	}

	public static void pop(Activity aty) {
		atyStack.remove(aty);
	}

	public static void exit() {
		emptyTheStackAndExit();
	}

	private static void emptyTheStackAndExit() {
		while (!atyStack.isEmpty()) {
			atyStack.pop().finish();
		}
	}

	/**
	 * 获取界面数量
	 *
	 * @return activity size
	 */
	public static int getActivitySize() {
		if (atyStack != null) {
			return atyStack.size();
		}
		return 0;
	}

	/**
	 * 获取指定类名的 Activity
	 *
	 * @param cls 指定的类
	 * @return Activity
	 */
	public static Activity getActivity(Class<?> cls) {
		if (atyStack == null) {
			return null;
		}
		for (Activity activity : atyStack) {
			if (activity.getClass().equals(cls)) {
				return activity;
			}
		}
		return null;
	}


	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		Glide.with(this).onTrimMemory(level);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Glide.with(this).onLowMemory();
	}
}
