package com.yj.cosmetics.util;

import android.content.Context;
import android.widget.Toast;

import com.yj.cosmetics.widget.CustomToast;


/**
 * Created by Suo on 2017/4/11.
 */

public class ToastUtils {
	private static Context context;
	private static CustomToast customToast;
	private static Toast mToast = null;

	private static ToastUtils toastUtils = null;

	private ToastUtils() {

	}

	public static ToastUtils instant() {
		if (toastUtils == null) {
			toastUtils = new ToastUtils();
		}
		return toastUtils;
	}


	public static void init(Context context) {
		ToastUtils.context = context;
//		customToast = new CustomToast(context);
	}

	public static void custom(CharSequence text,int i) {
		customToast = new CustomToast(context,i);
		customToast.showMsg(text);
		customToast.show();
	}

	@Deprecated
	public static void show(int resId) {
		Toast.makeText(context, context.getResources().getText(resId), Toast.LENGTH_SHORT).show();
	}

	@Deprecated
	public static void show(CharSequence text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, CharSequence text) {
		if (context != null) {
			if (mToast == null) {
				mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}

	public static void showLong(Context context, CharSequence text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}
}
