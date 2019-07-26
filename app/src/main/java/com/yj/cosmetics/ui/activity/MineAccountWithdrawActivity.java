package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Constant;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.AccountEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.model.SearchExplainEntity;
import com.yj.cosmetics.util.AESUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.RSAUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomPostDialog;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Float.parseFloat;

/**
 * Created by Suo on 2018/3/15.
 */

public class MineAccountWithdrawActivity extends BaseActivity {

	private static final String TAG = "MineAccountWithdrawActivity";
	@BindView(R.id.mine_account_withdraw_account)
	TextView tvAccount;
	@BindView(R.id.mine_account_withdraw_name)
	TextView tvName;
	@BindView(R.id.mine_account_withdraw_etmoney)
	EditText etMoney;
	@BindView(R.id.mine_account_withdraw_rest)
	TextView tvRest;
	@BindView(R.id.mine_account_withdraw_tv_money)
	TextView tvMoney;
	@BindView(R.id.text_account_explain)
	TextView tvExplain;
	@BindView(R.id.withdraw_code)
	EditText etCode;
	@BindView(R.id.mine_account_withdraw_tvSend)
	TextView tvSend;
	@BindView(R.id.withdraw_btn_confirm)
	Button btnConfirm;
	@BindView(R.id.scrollView)
	ScrollView mScrollView;

	CustomProgressDialog mDialog;
	private CustomPostDialog postDialog;

	private String balance;
	private String alipayId;

	private boolean isScroll = false;

	boolean isSend = false;
	boolean isNetError = false;

	final int TAG_SMS = 0x11;
	private SMSThread mThread;
	private int countTime = 60;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//            super.handleMessage(msg);
			switch (msg.what) {
				case TAG_SMS:
					if (isNetError) {
						isSend = false;
						tvSend.setText("获取");
					} else {
						if (countTime == 60) {
							tvSend.setText(String.valueOf(countTime) + "s");
							countTime--;
						} else if (countTime < 60 & countTime > 0) {
							tvSend.setText(String.valueOf(countTime) + "s");
							countTime--;
						} else if (countTime == 0) {
							//计时器已走完 验证码可以继续发送
							isSend = false;
							tvSend.setEnabled(true);
							tvSend.setText("获取");
						}
					}
					break;
			}
		}
	};
	private String upintegral;

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_account_withdraw;
	}

	@Override
	protected void initView() {
		setTitleText("提现");
		upintegral = getIntent().getStringExtra("upintegral");
		if (upintegral != null && !TextUtils.isEmpty(upintegral)) {
			tvMoney.setText("最低提现额度 " + upintegral);
//			etMoney.setHint(new SpannableString(upintegral));
		}
		mThread = new SMSThread();
		if (!mThread.isInterrupted()) {
			isSend = false;
		}
	}

	@Override
	protected void initData() {
		doAsyncGetSearchExplain();
		mDialog = new CustomProgressDialog(this);
		etMoney.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				scrollVertical(mScrollView.getHeight());
				return false;
			}
		});
		etCode.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				scrollVertical(mScrollView.getHeight());
				return false;
			}
		});
	}


	@Override
	protected void onResume() {
		if (mUtils.isLogin() && !TextUtils.isEmpty(mUtils.getAlipay()) && !TextUtils.isEmpty(mUtils.getAlipayName())) {
			if (mUtils.getAlipay().length() >= 6) {
				tvAccount.setText(mUtils.getAlipay().replace(mUtils.getAlipay().substring(countAlilpay(), countAlilpay() + 4), "****"));
			} else {
				tvAccount.setText(mUtils.getAlipay());
			}
			tvName.setText("*" + mUtils.getAlipayName().substring(1, mUtils.getAlipayName().length()));
			tvName.setVisibility(View.VISIBLE);
		} else {
			tvAccount.setText("未绑定支付宝");
			tvName.setVisibility(View.GONE);
		}
		doAsyncGetData();
		super.onResume();
	}

	private int countAlilpay() {
		if (mUtils.getAlipay().length() % 2 == 0) {
			//长度为偶
			return (mUtils.getAlipay().length() - 4) / 2 + 1;
		} else {
			//长度为奇数
			return (mUtils.getAlipay().length() - 3) / 2 + 1;
		}
	}

	@OnClick({R.id.mine_account_withdraw_rl_change, R.id.mine_account_withdraw_tvSend, R.id.withdraw_btn_confirm, R.id.mine_account_withdraw_tv_all})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.mine_account_withdraw_rl_change:
				Intent intent = new Intent(this, MinePersonalConfirmTelActivity.class);
				startActivity(intent);
				break;
			case R.id.mine_account_withdraw_tvSend:
				if (TextUtils.isEmpty(mUtils.getAlipay())) {
					ToastUtils.showToast(this, "请绑定支付宝账号");
					return;
				}
				if (balance != null) {
					String money = etMoney.getText().toString().trim();
					if (TextUtils.isEmpty(money)) {
						//money输入为空 此时不发送验证码
						ToastUtils.showToast(getApplicationContext(), "请输入要提现的金额");
						return;
					}
					if (money.contains(".") && money.substring(money.indexOf(".")).length() > 3) {
						//小数位大于2
						ToastUtils.showToast(getApplicationContext(), "取现小数不得大于两位数,请重新输入");
						return;
					}
					float have = parseFloat(balance);
					float want = parseFloat(etMoney.getText().toString());
					if (have < want) {
						ToastUtils.showToast(getApplicationContext(), "余额不足,请重新输入");
						return;
					}
					Float.parseFloat(upintegral);
					Log.i(TAG, "onClick: " + (Float.parseFloat(upintegral) - 1));
					if (want <= (Float.parseFloat(upintegral) - 1)) {
						ToastUtils.showToast(getApplicationContext(), "提现金额不能小于" + upintegral + ",请努力赚钱吧!");
						return;
					}
					if (isSend) {
						return;
					}
					doAsyncSendMS();
				} else {
					ToastUtils.showToast(getApplicationContext(), "网络故障,请退出重试");
				}

				break;
			case R.id.withdraw_btn_confirm:
				if (TextUtils.isEmpty(mUtils.getAlipay())) {
					ToastUtils.showToast(this, "请绑定支付宝账号");
					return;
				}
				if (TextUtils.isEmpty(etMoney.getText().toString().trim())) {
					ToastUtils.showToast(this, "请输入提现金额");
					return;
				}
				if (TextUtils.isEmpty(etCode.getText().toString().trim())) {
					ToastUtils.showToast(this, "请输入验证码");
					return;
				}
				if (TextUtils.isEmpty(alipayId)) {
					ToastUtils.showToast(this, "无法获取支付宝id,请检查网络稍后再试");
					return;
				}
				if (balance != null) {
					String money = etMoney.getText().toString().trim();
					if (TextUtils.isEmpty(money)) {
						//money输入为空 此时不发送验证码
						ToastUtils.showToast(getApplicationContext(), "请输入要提现的金额");
						return;
					}
					if (money.contains(".") && money.substring(money.indexOf(".")).length() > 3) {
						//小数位大于2
						ToastUtils.showToast(getApplicationContext(), "取现小数不得大于两位数,请重新输入");
						return;
					}
					float have = parseFloat(balance);
					float want = parseFloat(etMoney.getText().toString());
					if (have < want) {
						ToastUtils.showToast(getApplicationContext(), "余额不足,请重新输入");
						return;
					}
					if (want <= (Float.parseFloat(upintegral) - 1)) {
						ToastUtils.showToast(getApplicationContext(), "提现金额不能小于" + upintegral + ",请努力赚钱吧!");
						return;
					}
					doAsyncPost();
				} else {
					ToastUtils.showToast(getApplicationContext(), "网络故障,请退出重试");
				}
				break;
			case R.id.mine_account_withdraw_tv_all:
				if (!TextUtils.isEmpty(balance)) {
					float bal = Float.parseFloat(balance);
					if (bal > 0) {
						etMoney.setText(balance);
					}
				}
				break;
		}
	}


	private void doAsyncGetSearchExplain() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("type", "1");//@TODO 提现 2.额度提升
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/searchExplain").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<SearchExplainEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}

			@Override
			public SearchExplainEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetSearchExplain --- json的值" + json);
				return new Gson().fromJson(json, SearchExplainEntity.class);
			}

			@Override
			public void onResponse(SearchExplainEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					tvExplain.setText(response.getData().getIntro());
				} else {
					tvExplain.setVisibility(View.GONE);
				}
			}
		});
	}


	private void doAsyncGetData() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/backmoney").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<AccountEntity>() {
			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(MineAccountWithdrawActivity.this);
					if (!isFinishing()) {
						mDialog.show();
					}
				} else {
					if (!isFinishing()) {
						mDialog.show();
					}
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(MineAccountWithdrawActivity.this, "网络故障,请稍后再试");
				}
				dismissDialog();

			}

			@Override
			public AccountEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, AccountEntity.class);
			}

			@Override
			public void onResponse(AccountEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					setData(response.getData());
				} else {
					ToastUtils.showToast(MineAccountWithdrawActivity.this, "故障" + response.getMsg());
				}
				dismissDialog();
			}
		});
	}

	private void doAsyncSendMS() {
		Map<String, String> map = new HashMap<>();
		map.put(Key.tel, mUtils.getTel());
		LogUtils.i("传输的值" + URLBuilder.format(map));
		try {
			OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.SendMsg).tag(this)
					//				.addParams(Key.data, URLBuilder.format(map))
					.addParams(Key.data, AESUtils.encryptData(Constant.KEY, URLBuilder.format(map)))
					.addParams("key", RSAUtils.encryptByPublicKey(Constant.KEY))
					.build().execute
					(new Utils.MyResultCallback<NormalEntity>() {

						@Override
						public void onBefore(Request request) {
							LogUtils.i("我onBefore了");
							super.onBefore(request);
							isSend = true;
							isNetError = false;

							countTime = 60;
							if (!mThread.isAlive()) {
								if (!mThread.isInterrupted()) {
									mThread.start();
								}
							}
						}

						@Override
						public void onError(Call call, Exception e) {
							super.onError(call, e);
							if (call.isCanceled()) {
								call.cancel();
							} else {
								ToastUtils.showToast(MineAccountWithdrawActivity.this, "网络故障,请稍后再试");
							}
							LogUtils.i("我进入onError了" + e);

							isSend = false;
							isNetError = true;
							//                        mThread=null
							//发送验证码按钮
							tvSend.setText("获取");
						}

						//进行json解析.
						@Override
						public NormalEntity parseNetworkResponse(Response response) throws Exception {
							String json = response.body().string().trim();
							LogUtils.i("json的值" + json);
							return new Gson().fromJson(json, NormalEntity.class);
						}

						//对返回结果进行判断.
						@Override
						public void onResponse(NormalEntity response) {
							LogUtils.i("我onResponse了");
							if (isFinishing()) {
								return;
							}
							if (response.getCode().equals(response.HTTP_OK)) {
								ToastUtils.showToast(MineAccountWithdrawActivity.this, "验证码已发送");
								isNetError = false;
							} else {
								ToastUtils.showToast(MineAccountWithdrawActivity.this, response.getMsg() + "):" + response.getCode());
								isNetError = true;
								isSend = false;
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doAsyncPost() {
		Map<String, String> map = new HashMap<>();
		map.put("alipayId", alipayId);
		map.put("cashmoney", etMoney.getText().toString().trim());
		map.put("code", etCode.getText().toString().trim());
		LogUtils.i("changeName传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/addBackMoney")
				.addParams(Key.data, URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void inProgress(float progress) {
				super.inProgress(progress);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(MineAccountWithdrawActivity.this);
					if (!isFinishing()) {
						mDialog.show();
					}
				} else {
					if (!isFinishing()) {
						mDialog.show();
					}
				}
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(final NormalEntity response) {
				if (isFinishing()) {
					return;
				}
				dismissDialog();
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (postDialog == null) {
						postDialog = new CustomPostDialog(MineAccountWithdrawActivity.this);
					}
					if (!postDialog.isShowing()) {
						postDialog.show();
					}
					btnConfirm.postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent(MineAccountWithdrawActivity.this, MineAccountCostDetialActivity.class);
							intent.putExtra("cashmoneyId", response.getMsg().toString());
							intent.putExtra("flag", "1");
							startActivity(intent);
							finish();
						}
					}, 600);
				} else {
					ToastUtils.showToast(MineAccountWithdrawActivity.this, "请求失败 :)" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(MineAccountWithdrawActivity.this, "网络故障,请稍后再试");
				}
				dismissDialog();
				LogUtils.i("网络请求失败" + e);

			}
		});
	}

	private void setData(AccountEntity.AccountData data) {
		tvRest.setText("可提现余额￥" + data.getUserMoney());
		balance = data.getUserMoney();
		alipayId = data.getAlipayId();
	}

	//使用SMSThread来更新界面.隐藏和显示发送验证码
	private class SMSThread extends Thread {
		@Override
		public void run() {
//            super.run();
			while (!Thread.currentThread().isInterrupted()) {
				if (isSend) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Message msg = mHandler.obtainMessage();
					msg.what = TAG_SMS;
					msg.sendToTarget();
				}
			}
		}
	}

	/**
	 * 使滚动条滚动至指定位置（垂直滚动）
	 *
	 * @param to 滚动到的位置
	 */
	protected void scrollVertical(final int to) {
		if (!isScroll) {
			isScroll = true;
			mScrollView.postDelayed(new Runnable() {
				@Override
				public void run() {
					LogUtils.i("弹出后的高度是" + to);
					mScrollView.scrollTo(0, to);
					isScroll = false;
				}
			}, 500);
		}
	}

	private void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

	private void dismissDialog2() {
		if (postDialog != null) {
			postDialog.dismiss();
			postDialog = null;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isSend = false;
		mThread.interrupt();
		dismissDialog();
		dismissDialog2();
		OkHttpUtils.getInstance().cancelTag(this);
	}

}
