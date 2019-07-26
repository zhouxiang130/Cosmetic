package com.yj.cosmetics.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sobot.chat.SobotApi;
import com.sobot.chat.api.enumtype.SobotChatTitleDisplayMode;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.utils.ZhiChiConstant;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseFragment;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.ui.activity.MineAboutActivity;
import com.yj.cosmetics.ui.activity.MineAccountActivity;
import com.yj.cosmetics.ui.activity.MineAddressManageActivity;
import com.yj.cosmetics.ui.activity.MineCollectionActivity;
import com.yj.cosmetics.ui.activity.MineInviteActivity;
import com.yj.cosmetics.ui.activity.MineOrderActivity;
import com.yj.cosmetics.ui.activity.MinePersonalDataActivity;
import com.yj.cosmetics.ui.activity.MineSettingActivity;
import com.yj.cosmetics.ui.activity.mineScoring.MineScoring1Activity;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2017/11/18.
 *
 * @TODO 个人中心
 */

public class MineFrag extends BaseFragment {
	private static final String TAG = "MineFrag";
	@BindView(R.id.frag_mine_login_iv)
	RoundedImageView ivLogin;
	@BindView(R.id.mine_new_tv_login)
	TextView tvLogin;
	@BindView(R.id.mine_new_tv_tel)
	TextView tvTel;
	@BindView(R.id.frag_mine_account_money)
	TextView tvMoney;
	@BindView(R.id.frag_mine_tv_score)
	TextView tvScore;
	@BindView(R.id.frag_mine_tv_pay_num)
	TextView tvPayNum;
	@BindView(R.id.frag_mine_tv_send_num)
	TextView tvSendNum;
	@BindView(R.id.frag_mine_tv_get_num)
	TextView tvGetNum;
	@BindView(R.id.frag_mine_tv_judge_num)
	TextView tvJudgeNum;
	@BindView(R.id.frag_mine_tv_info)
	TextView tvInfo;
	@BindView(R.id.frag_mine_tv_coustom_service_msg_num)
	TextView tvCoustomNum;

	public static final int VISIBLE = 0x00000000;
	private String contactTel;
	private Information userInfo;
	CustomNormalContentDialog mDialog;
	private String userType;
	private String upintegral;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = createView(inflater.inflate(R.layout.fragment_mine, container, false));
		return view;
	}

	@Override
	protected void initData() {
		userInfo = new Information();
		regReceiver();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!TextUtils.isEmpty(mUtils.getUserName())) {
			tvLogin.setText(mUtils.getUserName());
			if (!TextUtils.isEmpty(mUtils.getTel())) {
				if (mUtils.getTel().length() > 8) {
					tvTel.setText(mUtils.getTel().replace(mUtils.getTel().substring(4, 8), "****"));
				} else {
					tvTel.setText(mUtils.getTel());
				}
			}
			tvTel.setVisibility(View.VISIBLE);
		} else {
			tvLogin.setText("点击登录");
			tvTel.setVisibility(View.GONE);
		}


		Log.i(TAG, "onResume: " + " 头像信息： " + mUtils.getAvatar());
		if (!TextUtils.isEmpty(mUtils.getAvatar())) {
			LogUtils.i("avatar的值" + mUtils.getAvatar());
			Glide.with(getActivity().getApplicationContext())
					.load(URLBuilder.getUrl(mUtils.getAvatar()))
					.asBitmap()
					.fitCenter()
					.error(R.mipmap.default_avatar)
					.into(ivLogin);
		} else {
			Glide.with(getActivity().getApplicationContext())
					.load(R.mipmap.default_avatar)
					.asBitmap()
					.fitCenter()
					.error(R.mipmap.default_avatar)
					.into(ivLogin);
		}
		if (mUtils.isLogin()) {
			doAsyncGetData();
			doAsyncGetInfo();
		} else {
			tvPayNum.setVisibility(View.GONE);
			tvSendNum.setVisibility(View.GONE);
			tvGetNum.setVisibility(View.GONE);
			tvJudgeNum.setVisibility(View.GONE);
			tvInfo.setVisibility(View.GONE);
			tvMoney.setText("0.00");
			tvScore.setText("0");
		}
		   /* doAsyncGetLevel();
		    doAsyncGetStars();
        } else {
            ivLevel.setVisibility(View.GONE);
            tvNumber.setVisibility(View.GONE);
            MainActivity activity = (MainActivity) getActivity();
            activity.hideNumber();
            tvStarNum.setText("——");
            tvTimes.setText("——");
            tvHours.setText("——");
        }*/
	}

	@OnClick({R.id.frag_mine_iv_personal, R.id.frag_mine_rl_about, R.id.frag_mine_rl_setting, R.id.frag_mine_contact, R.id.frag_mine_coustom_service, R.id.frag_mine_address,
			R.id.frag_mine_account, R.id.frag_mine_scoring, R.id.frag_mine_order_all, R.id.frag_mine_order_pay, R.id.frag_mine_order_send,
			R.id.frag_mine_order_get, R.id.frag_mine_order_judge, R.id.frag_mine_info, R.id.frag_mine_invite, R.id.frag_mine_login_ll,
			R.id.frag_mine_collection, R.id.frag_mine_login_iv})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.frag_mine_iv_personal:
				if (mUtils.isLogin()) {
					Intent intentPersonal = new Intent(getActivity(), MinePersonalDataActivity.class);
					startActivity(intentPersonal);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_rl_about:
				Intent intentAbout = new Intent(getActivity(), MineAboutActivity.class);
				startActivity(intentAbout);
				break;
			case R.id.frag_mine_rl_setting:
				Intent intentSetting = new Intent(getActivity(), MineSettingActivity.class);
				startActivity(intentSetting);
				break;
			case R.id.frag_mine_contact:
				showCallDialog();
				break;
			case R.id.frag_mine_coustom_service:
				if (mUtils.isLogin()) {
					int visibility = tvCoustomNum.getVisibility();
					if (visibility == VISIBLE) {
						tvCoustomNum.setVisibility(View.GONE);
					}
					doCustomServices();
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_address:
				if (mUtils.isLogin()) {
					Intent intentAddress = new Intent(getActivity(), MineAddressManageActivity.class);
					startActivity(intentAddress);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_account:
				if (mUtils.isLogin()) {
					Intent intentAccount = new Intent(getActivity(), MineAccountActivity.class);
					intentAccount.putExtra("upintegral", upintegral);
					startActivity(intentAccount);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_scoring:
				if (mUtils.isLogin()) {
//					Intent intentScoring = new Intent(getActivity(), MineScoringActivity.class);
					Intent intentScoring = new Intent(getActivity(), MineScoring1Activity.class);
					startActivity(intentScoring);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_all:
				if (mUtils.isLogin()) {
					Intent intentAll = new Intent(getActivity(), MineOrderActivity.class);
					intentAll.putExtra("page", 0);
					startActivity(intentAll);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_pay:
				if (mUtils.isLogin()) {
					Intent intentPay = new Intent(getActivity(), MineOrderActivity.class);
					intentPay.putExtra("page", 1);
					startActivity(intentPay);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_send:
				if (mUtils.isLogin()) {
					Intent intentSend = new Intent(getActivity(), MineOrderActivity.class);
					intentSend.putExtra("page", 2);
					startActivity(intentSend);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_get:
				if (mUtils.isLogin()) {
					Intent intentGet = new Intent(getActivity(), MineOrderActivity.class);
					intentGet.putExtra("page", 3);
					startActivity(intentGet);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_judge:
				if (mUtils.isLogin()) {
					Intent intentJudge = new Intent(getActivity(), MineOrderActivity.class);
					intentJudge.putExtra("page", 4);
					startActivity(intentJudge);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_info:
				if (mUtils.isLogin()) {
					IntentUtils.IntentToInfoCenter(getActivity());
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_invite:
				if (mUtils.isLogin()) {
					Intent intentInvite = new Intent(getActivity(), MineInviteActivity.class);
					startActivity(intentInvite);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_login_ll:
				if (!mUtils.isLogin()) {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_login_iv:
				if (!mUtils.isLogin()) {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_collection:
				if (mUtils.isLogin()) {
					Intent intentCollection = new Intent(getActivity(), MineCollectionActivity.class);
					startActivity(intentCollection);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
		}
	}

	private void doCustomServices() {
		//用户信息设置
		//设置用户自定义字段
		userInfo.setUseRobotVoice(false);//这个属性默认都是false。想使用需要付费。付费才可以设置为true。
		userInfo.setUid(mUtils.getUid());
		userInfo.setTel(mUtils.getTel());
//		userInfo.setRealname(mUtils.getUserName());
		userInfo.setUname(mUtils.getUserName());
		userInfo.setFace(URLBuilder.URLBaseHeader + mUtils.getAvatar());//头像


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
			SobotApi.setChatTitleDisplayMode(getActivity().getApplicationContext(), SobotChatTitleDisplayMode.values()[0], "");
			//设置是否开启消息提醒
			SobotApi.setNotificationFlag(getActivity().getApplicationContext(), true, R.mipmap.logo, R.mipmap.logo);
			SobotApi.hideHistoryMsg(getActivity().getApplicationContext(), 0);
			SobotApi.setEvaluationCompletedExit(getActivity().getApplicationContext(), false);
			SobotApi.startSobotChat(getActivity(), userInfo);
		} else {
			Log.i(TAG, "doCustomServices: " + "app_key 不能为空");
		}
	}

	private MyReceiver receiver;//广播

	private void regReceiver() {
		//注册广播获取新收到的信息和未读消息数
		if (receiver == null) {
			receiver = new MyReceiver();
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(ZhiChiConstant.sobot_unreadCountBrocast);
		getActivity().registerReceiver(receiver, filter);
	}

	//设置广播获取新收到的信息和未读消息数
	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int noReadNum = intent.getIntExtra("noReadCount", 0);
			String content = intent.getStringExtra("content");
			//未读消息数
			if (noReadNum != 0) {
				tvCoustomNum.setVisibility(View.VISIBLE);
				tvCoustomNum.setText(noReadNum + "");
			}
			//新消息内容
			com.sobot.chat.utils.LogUtils.i("新消息内容:" + content);
		}
	}


	private void showCallDialog() {
		if (TextUtils.isEmpty(contactTel) && TextUtils.isEmpty(mUtils.getServiceTel())) {
			ToastUtils.showToast(getActivity(), "无法获取联系电话，请检查网络稍后再试");
			return;
		}
		if (mDialog == null) {
			mDialog = new CustomNormalContentDialog(getActivity());
		}
		if (!mDialog.isShowing()) {
			mDialog.show();
		}
		mDialog.getTvTitle().setText("客服热线");
		if (!TextUtils.isEmpty(contactTel)) {
			mDialog.getTvContent().setText("拨打" + contactTel + "热线，联系官方客服");
		} else if (!TextUtils.isEmpty(mUtils.getServiceTel())) {
			mDialog.getTvContent().setText("拨打" + mUtils.getServiceTel() + "热线，联系官方客服");
		}
		mDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//拨打电话
				Intent callIntent = new Intent();
				callIntent.setAction(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + contactTel));
				startActivity(callIntent);
				dismissDialog();
			}
		});
		mDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismissDialog();
			}
		});
	}


	private void doAsyncGetData() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/zoneOrder").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<MineEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}

			@Override
			public MineEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, MineEntity.class);
			}

			@Override
			public void onResponse(MineEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					setData(response.getData());
				} else {

				}
			}
		});
	}

	private void doAsyncGetInfo() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/countMsg").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				tvInfo.setVisibility(View.GONE);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					if (response.getData() != null && Float.parseFloat(response.getData().toString()) > 0) {
						tvInfo.setText(response.getData().toString());
					} else {
						tvInfo.setVisibility(View.GONE);
					}
				} else {
					tvInfo.setVisibility(View.GONE);
				}
			}
		});
	}

	private void setData(MineEntity.MineData data) {
		contactTel = data.getServiceTel();
		upintegral = data.getUpintegral();
		if (!TextUtils.isEmpty(data.getServiceTel())) {
			mUtils.saveServiceTel(data.getServiceTel());
		}
		Log.i(TAG, "userMoney-----------------------: " + data.getUserMoney());
		tvMoney.setText(data.getUserMoney());
		tvScore.setText(data.getUserScore());
		if (!TextUtils.isEmpty(data.getOrderpay())) {
			tvPayNum.setVisibility(View.VISIBLE);
			tvPayNum.setText(data.getOrderpay());
		} else {
			tvPayNum.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(data.getOrdersend())) {
			tvSendNum.setText(data.getOrdersend());
			tvSendNum.setVisibility(View.VISIBLE);
		} else {
			tvSendNum.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(data.getOrderget())) {
			tvGetNum.setText(data.getOrderget());
			tvGetNum.setVisibility(View.VISIBLE);
		} else {
			tvGetNum.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(data.getOrderjudge())) {
			tvJudgeNum.setText(data.getOrderjudge());
			tvJudgeNum.setVisibility(View.VISIBLE);
		} else {
			tvJudgeNum.setVisibility(View.GONE);
		}
	}

	private void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		dismissDialog();
		getActivity().unregisterReceiver(receiver);
	}
}
