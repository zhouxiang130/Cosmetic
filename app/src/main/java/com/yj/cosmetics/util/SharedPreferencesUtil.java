package com.yj.cosmetics.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

	public static SharedPreferencesUtil instance;
	public SharedPreferences sp;
    public static final String Sys_Config = "sys_config";

	public static SharedPreferencesUtil getInstance(Context context) {
		if (instance == null) {
			instance = new SharedPreferencesUtil(context);
		}
		return instance;
	}

	public SharedPreferencesUtil(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences(Sys_Config, Context.MODE_PRIVATE);
		}
	}
	
	public void setBooleanValue(String key, boolean value) {
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void setValue(String key, Object value) {
		Editor editor = sp.edit();
		editor.putString(key, String.valueOf(value));
		editor.commit();
	}

	public String getValue(String key, String defaultValue) {
		return sp.getString(key, defaultValue);
	}

	public boolean checkExist(String userInfo, String allInfo) {
		if ((userInfo != null && userInfo.equals(""))
				|| (allInfo != null && allInfo.equals(""))) {
			return false;
		}
		return allInfo.indexOf(userInfo) >= 0;
	}

	public void setValues(String key, boolean value) {
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getValuesBoolean(String key) {
		return sp.getBoolean(key, true);
	}

}
