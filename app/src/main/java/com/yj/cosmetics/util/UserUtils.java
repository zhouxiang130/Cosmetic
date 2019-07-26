package com.yj.cosmetics.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by Suo on 2017/4/12.
 */

//创建此工具类实例会创建sp
public class UserUtils {
	private static UserUtils mUserUtils;
	private Context context = null;
	private SharedPreferences sp;

	public UserUtils(Context context) {
		this.context = context;
		sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
	}

	public static UserUtils getInstance(Context context) {
		if (mUserUtils == null) {
			synchronized (UserUtils.class) {
				mUserUtils = new UserUtils(context);
			}
		}
		return mUserUtils;
	}

	/*
	* 保存用户信息
	* */
	public void logOut() {
		sp.edit().putString("userId", null).apply();
		sp.edit().putString("userTel", null).apply();
		sp.edit().putString("userName", null).apply();
		sp.edit().putString("wxopenId", null).apply();
		sp.edit().putString("qqopenId", null).apply();
		sp.edit().putString("alipay", null).apply();
		sp.edit().putString("avatar", null).apply();
		sp.edit().putString("alipayName", null).apply();
		sp.edit().putString("inviteCode", null).apply();
		sp.edit().putString("serviceTel", null).apply();
		new SharedPreferencesUtil(context).setValue("userType", "");
	}

	/*
	* 判断用户是否登录
	* */
	public boolean isLogin() {
		if (!TextUtils.isEmpty(sp.getString("userTel", null))) {
			return true;
		}
		return false;
	}


/*
* save用户信息
* */

	public void saveTel(String tel) {
		if (tel == null) {
			return;
		}
		sp.edit().putString("userTel", tel).apply();
	}


	public void saveValue(String key, Object value) {
		sp.edit().putString(key, String.valueOf(value));
		sp.edit().commit();
	}

	public String getValue(String key, String defaultValue) {
		return sp.getString(key, defaultValue);
	}


	public void saveUserName(String name) {
		if (name == null) {
			return;
		}
		sp.edit().putString("userName", name).apply();
	}

	public void saveAvatar(String avatar) {
		if (avatar == null) {
			return;
		}
		sp.edit().putString("avatar", avatar).apply();
	}

	public void saveUid(String uid) {
		if (uid == null) {
			return;
		}
		sp.edit().putString("userId", uid).apply();
	}

	public void saveWXOpenId(String wxopenId) {
		if (wxopenId == null) {
			return;
		}
		sp.edit().putString("wxopenId", wxopenId).apply();
	}

	public void saveQQOpenId(String qqopenId) {
		if (qqopenId == null) {
			return;
		}
		sp.edit().putString("qqopenId", qqopenId).apply();
	}

	public void saveInviteCode(String inviteCode) {
		if (inviteCode == null) {
			return;
		}
		sp.edit().putString("inviteCode", inviteCode).apply();
	}

	public void saveAlipay(String alipay) {
		if (alipay == null) {
			return;
		}
		sp.edit().putString("alipay", alipay).apply();
	}

	public void saveAlipayName(String alipayName) {
		if (alipayName == null) {
			return;
		}
		sp.edit().putString("alipayName", alipayName).apply();
	}

	public void saveSearchHistory(String searchHistory) {
		if (searchHistory == null) {
			return;
		}
		sp.edit().putString("searchHistory", searchHistory).apply();
	}

	public void saveServiceTel(String serviceTel) {
		if (serviceTel == null) {
			return;
		}
		sp.edit().putString("serviceTel", serviceTel).apply();
	}

	public void saveVersion(String version) {
		if (version == null) {
			return;
		}
		sp.edit().putString("version", version).apply();
	}

	public void saveLink(String link) {
		if (link == null) {
			return;
		}
		sp.edit().putString("link", link).apply();
	}

	public void saveIsFirstDirect(boolean isFirstDirect) {
		sp.edit().putBoolean("isFirstDirect", isFirstDirect).apply();
	}

	public void savePayOrder(String payOrder) {
		sp.edit().putString("payOrder", payOrder).apply();
	}

	public void savePayType(String payType) {
		sp.edit().putString("payType", payType).apply();
	}

	/**
	 * 保存短暂信息
	 *
	 * @return
	 */

	public void saveAddselect(String addInfo) {
		sp.edit().putString("addInfo", addInfo).apply();
	}

	public void saveRefreshStore(String RefreshStore) {
		sp.edit().putString("RefreshStore", RefreshStore).apply();
	}


	public String getRefreshStore() {
		String RefreshStore = sp.getString("RefreshStore", null);
		if (TextUtils.isEmpty(RefreshStore)) {
			return null;
		}
		return RefreshStore;
	}


	public String getAddInfo() {
		String addInfo = sp.getString("addInfo", null);
		if (TextUtils.isEmpty(addInfo)) {
			return null;
		}
		return addInfo;
	}


	/*
* get用户信息
* */
	public String getTel() {
		String tel = sp.getString("userTel", null);
		if (TextUtils.isEmpty(tel)) {
			return null;
		}
		return tel;
	}

	public String getUserName() {
		String name = sp.getString("userName", null);
		if (TextUtils.isEmpty(name)) {
			return null;
		}
		return name;
	}

	public String getAvatar() {
		String avatar = sp.getString("avatar", null);
		if (TextUtils.isEmpty(avatar)) {
			return null;
		}
		return avatar;
	}

	public String getUid() {
		String uid = sp.getString("userId", null);
		if (TextUtils.isEmpty(uid)) {
			return null;
		}
		return uid;
	}

	public String getWXOpenId() {
		String wxopenId = sp.getString("wxopenId", null);
		if (TextUtils.isEmpty(wxopenId)) {
			return null;
		}
		return wxopenId;
	}

	public String getQQOpenId() {
		String qqopenId = sp.getString("qqopenId", null);
		if (TextUtils.isEmpty(qqopenId)) {
			return null;
		}
		return qqopenId;
	}

	public String getInviteCode() {
		String inviteCode = sp.getString("inviteCode", null);
		if (TextUtils.isEmpty(inviteCode)) {
			return null;
		}
		return inviteCode;
	}

	public String getAlipay() {
		String alipay = sp.getString("alipay", null);
		if (TextUtils.isEmpty(alipay)) {
			return null;
		}
		return alipay;
	}

	public String getAlipayName() {
		String alipayName = sp.getString("alipayName", null);
		if (TextUtils.isEmpty(alipayName)) {
			return null;
		}
		return alipayName;
	}

	public String getSearchHistory() {
		String searchHistory = sp.getString("searchHistory", null);
		if (TextUtils.isEmpty(searchHistory)) {
			return null;
		}
		return searchHistory;
	}

	public String getServiceTel() {
		String serviceTel = sp.getString("serviceTel", null);
		if (TextUtils.isEmpty(serviceTel)) {
			return null;
		}
		return serviceTel;
	}

	public String getVersion() {
		String version = sp.getString("version", null);
		if (TextUtils.isEmpty(version)) {
			return null;
		}
		return version;
	}

	public String getPayOrder() {
		String payOrder = sp.getString("payOrder", null);
		if (TextUtils.isEmpty(payOrder)) {
			return null;
		}
		return payOrder;
	}

	public String getPayType() {
		String payType = sp.getString("payType", null);
		if (TextUtils.isEmpty(payType)) {
			return null;
		}
		return payType;
	}

	public boolean getIsFirstDirect() {
		boolean isFirstDirect = sp.getBoolean("isFirstDirect", true);
		return isFirstDirect;
	}

	public void saveSession(String session) {
		if (session == null) {
			return;
		}
		sp.edit().putString("session", session).apply();
	}

	public String getSession() {
		String session = sp.getString("session", null);
		if (TextUtils.isEmpty(session)) {
			return null;
		}
		return session;
	}
}
