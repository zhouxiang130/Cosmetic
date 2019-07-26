package com.yj.cosmetics.util;

import android.text.TextUtils;
import android.util.Log;

import com.yj.cosmetics.MyApplication;
import com.yj.cosmetics.R;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/8/31 0031.
 */

public class ADFilterTool {


	private static final String TAG = "ADFilterTool";
	/**
	 * 正则表达式
	 */
	private static String PATTERN = "";

	static {
		initPattern();
	}

	/**
	 * 初始化pattern
	 */
	private static void initPattern() {
		PATTERN = getPatternStr();
	}

	/**
	 * 判断url的域名是否合法
	 * <p>
	 * 域名是否合法：自己项目中使用的域名，为合法域名；其它域名皆为不合法域名，进行屏蔽
	 *
	 * @param url
	 * @return
	 */
	public static boolean hasNotAd(String url) {
		if (TextUtils.isEmpty(url)) {
			return false;
		}
		if (TextUtils.isEmpty(PATTERN)) {
			initPattern();
		}
		if (Pattern.matches(PATTERN, url)) {
			return true;
		}
		return false;
	}

	/**
	 * 拼接正则表达式
	 *
	 * @return
	 */
	private static String getPatternStr() {
		String[] adUrls = MyApplication.getApplication().getResources().getStringArray(R.array.legal_domain);
		if (null != adUrls && adUrls.length > 0) {
			StringBuffer sb = new StringBuffer("^(http)://.*(");
			for (String a : adUrls) {
				if (null != a && a.length() > 0) {
					sb.append(a).append("|");
				}
			}
			Log.i(TAG, "getPatternStr: "+sb.substring(0, sb.length() - 1) + ").*" );
			return sb.substring(0, sb.length() - 1) + ").*";
		}
		return null;
	}

}
