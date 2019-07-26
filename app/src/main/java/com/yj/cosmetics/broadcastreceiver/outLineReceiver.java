package com.yj.cosmetics.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yj.cosmetics.util.SharedPreferencesUtil;

/**
 * @TODO 定义客服在断开连接广播
 */
public class outLineReceiver extends BroadcastReceiver {


	private String TAG = "outLineReceiver";
	private static SharedPreferencesUtil preferencesUtil = null;

	public static void outLineReceiver(Context context) {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO: This method is called when the BroadcastReceiver is receiving
		// an Intent broadcast.
//		throw new UnsupportedOperationException("Not yet implemented");
		String info = intent.getStringExtra("info");
		Log.i(TAG, "onReceive:------------- " + info);
		preferencesUtil = new SharedPreferencesUtil(context);
		if (info.equals("outline_")) {//评价完毕或者断开连接之后 ，清除商品信息 离线模式
			preferencesUtil.setValue("product_name", "");
			preferencesUtil.setValue("Product_abstract", "");
			preferencesUtil.setValue("product_listimg", "");
			preferencesUtil.setValue("product_url", "");
			preferencesUtil.setBooleanValue("is_open_chat_message_info", false);
		} else if (info.equals("is_notification_skip")) {//是否从通知栏打开消息对话页面
			preferencesUtil.setBooleanValue("is_notification_skip", false);
		} else if (info.equals("online_")) {//在线模式
			preferencesUtil.setBooleanValue("is_open_chat_message_info", true);
		}
	}
}
