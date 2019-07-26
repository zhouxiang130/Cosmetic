package com.yj.cosmetics.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/15 0015.
 */

public class AuthorUtils {


	private static final int PERMISSON_REQUESTCODE = 0;
	private static Context context = null;


	public AuthorUtils(Context context) {
		this.context = context;
	}

	/**
	 * @since 2.5.0
	 */
	public static boolean checkPermissions(String... permissions) {
		List<String> needRequestPermissonList = findDeniedPermissions(permissions);
		if (null != needRequestPermissonList && needRequestPermissonList.size() > 0) {
			ActivityCompat.requestPermissions((Activity) context, needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]), PERMISSON_REQUESTCODE);
		}
		if (needRequestPermissonList.size() == PERMISSON_REQUESTCODE) {
			//版本有更新
			return true;
		} else {
			return false;
		}
	}


	/**
	 * 获取权限集中需要申请权限的列表
	 *
	 * @param permissions
	 * @return
	 * @since 2.5.0
	 */
	public static List<String> findDeniedPermissions(String[] permissions) {
		List<String> needRequestPermissonList = new ArrayList<>();
		for (String perm : permissions) {
			if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED
					|| ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, perm)) {
				needRequestPermissonList.add(perm);
			}
		}
		return needRequestPermissonList;
	}


	/**
	 * 检测是否说有的权限都已经授权
	 *
	 * @param grantResults
	 * @return
	 * @since 2.5.0
	 */
	public static boolean verifyPermissions(int[] grantResults) {
		for (int result : grantResults) {
			if (result != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		}
		return true;
	}


}
