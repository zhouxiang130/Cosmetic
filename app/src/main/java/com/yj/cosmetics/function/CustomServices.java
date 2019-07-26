package com.yj.cosmetics.function;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.sobot.chat.SobotApi;
import com.sobot.chat.api.enumtype.SobotChatTitleDisplayMode;
import com.sobot.chat.api.model.ConsultingContent;
import com.sobot.chat.api.model.Information;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.util.SharedPreferencesUtil;
import com.yj.cosmetics.util.UserUtils;

/**
 * Created by Administrator on 2018/5/18 0018.
 */

public class CustomServices {

	private static final String TAG = "CustomServices";
	private Context context = null;
	private Information userInfo;
	public UserUtils mUtils;

	public CustomServices(Context context) {
		this.context = context;
		userInfo = new Information();
		mUtils = UserUtils.getInstance(context);
	}

	public void doCustomServices(SharedPreferencesUtil preferencesUtil) {
		userInfo.setUseRobotVoice(false);//这个属性默认都是false。想使用需要付费。付费才可以设置为true。
		userInfo.setUid(mUtils.getUid());
		userInfo.setTel(mUtils.getTel());
//		userInfo.setRealname(mUtils.getUserName());
		Log.i(TAG, "doCustomServices: " + mUtils.getTel() + "------" + mUtils.getUserName() + "---" + URLBuilder.getUrl(mUtils.getAvatar()));
		userInfo.setUname(mUtils.getUserName());
		if (mUtils.getAvatar()!=null){
			userInfo.setFace(URLBuilder.getUrl(mUtils.getAvatar()));//头像
		}

		//咨询信息设置
		//@TODO  SP 存储需要咨询商品的
		if (preferencesUtil.getValue("product_name", "product_name") != null
				&& !preferencesUtil.getValue("product_name", "product_name").equals("")) {
			//咨询信息设置
			ConsultingContent consult = new ConsultingContent();
			consult.setSobotGoodsTitle(preferencesUtil.getValue("product_name", "product_name"));//定义商品标题
			consult.setSobotGoodsDescribe(preferencesUtil.getValue("Product_abstract", "Product_abstract"));//商品描述
			consult.setSobotGoodsImgUrl(URLBuilder.getUrl(preferencesUtil.getValue("product_listimg", "product_listimg")));
			consult.setSobotGoodsFromUrl(URLBuilder.getUrl(preferencesUtil.getValue("product_url", "product_url")));
//		if (data.getProductUrl() != null) {
//		}
			userInfo.setConsultingContent(consult);
		}
//		userInfo.setEmail(DemoSPUtil.getStringData(SobotDemoActivity.this, "sobot_email", ""));//Email
//		userInfo.setUname(DemoSPUtil.getStringData(SobotDemoActivity.this, "person_uName", ""));//用户姓名
//		userInfo.setRemark(DemoSPUtil.getStringData(SobotDemoActivity.this, "sobot_reMark", ""));//备注信息
//		userInfo.setQq(DemoSPUtil.getStringData(SobotDemoActivity.this, "sobot_qq", ""));
//		userInfo.setVisitTitle(DemoSPUtil.getStringData(SobotDemoActivity.this, "sobot_visitTitle", ""));
//		userInfo.setVisitUrl(DemoSPUtil.getStringData(SobotDemoActivity.this, "sobot_visitUrl", ""));

		String appkey = "1add5fe6182c48fbb2c9d5c41211bfe6";
		if (!TextUtils.isEmpty(appkey)) {
			userInfo.setAppkey(appkey);
			//设置标题显示模式
			SobotApi.setChatTitleDisplayMode(context.getApplicationContext(), SobotChatTitleDisplayMode.values()[0], "");
			//设置是否开启消息提醒
			SobotApi.setNotificationFlag(context.getApplicationContext(), true, R.mipmap.logo, R.mipmap.logo);
			SobotApi.hideHistoryMsg(context.getApplicationContext(), 0);
			SobotApi.setEvaluationCompletedExit(context.getApplicationContext(), false);
			SobotApi.startSobotChat(context, userInfo);
		} else {
		}
	}
}
