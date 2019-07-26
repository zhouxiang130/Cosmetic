package com.yj.cosmetics.ui.activity.mineSettingHelp;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.sobot.chat.SobotApi;
import com.sobot.chat.api.enumtype.SobotChatTitleDisplayMode;
import com.sobot.chat.api.model.Information;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Constant;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.ui.activity.mineHelpSug.MineHelpSugActivity;

import java.lang.ref.SoftReference;

import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/11 0011.
 *
 * @TODO 求助反馈界面 2.0版本添加页面
 */

public class MineSettingHelpActivity extends BaseActivity {

	private static final String TAG = "MineSettingHelpActivity";
	private Information userInfo;

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_setting_helps;
	}

	@Override
	protected void initView() {
		setTitleText("求助反馈");
		userInfo = new Information();

	}

	@Override
	protected void initData() {

	}


	@OnClick({R.id.mine_setting_helps_help, R.id.mine_setting_helps_suggestion})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.mine_setting_helps_help:

				doCustomServices();
				break;
			case R.id.mine_setting_helps_suggestion:
				Intent intent = new Intent(this, MineHelpSugActivity.class);
				startActivity(intent);

				break;
		}
	}

	public void doCustomServices() {
		//用户信息设置
		//设置用户自定义字段
		userInfo.setUseRobotVoice(false);//这个属性默认都是false。想使用需要付费。付费才可以设置为true。
		userInfo.setUid(mUtils.getUid());
		userInfo.setTel(mUtils.getTel());
		userInfo.setUname(mUtils.getUserName());
		if (mUtils.getAvatar()!=null){
			userInfo.setFace(URLBuilder.getUrl(mUtils.getAvatar()));//头像
		}

		SoftReference<String> appkeySR = new SoftReference<>(Constant.ZC_appkey);
		String appkey = appkeySR.get();
		if (!TextUtils.isEmpty(appkey)) {
			userInfo.setAppkey(appkey);
			//设置标题显示模式
			SobotApi.setChatTitleDisplayMode(getApplicationContext(), SobotChatTitleDisplayMode.values()[0], "");
			//设置是否开启消息提醒
			SobotApi.setNotificationFlag(getApplicationContext(), true, R.mipmap.logo, R.mipmap.logo);
			SobotApi.hideHistoryMsg(getApplicationContext(), 0);
			SobotApi.setEvaluationCompletedExit(getApplicationContext(), false);
			SobotApi.startSobotChat(this, userInfo);
		} else {
			Log.i(TAG, "doCustomServices: " + "app_key 不能为空");
		}
	}
}
