package com.yj.cosmetics.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.cosmetics.MyApplication;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Constant;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.model.UserInfoEntity;
import com.yj.cosmetics.util.AESUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.MatchUtils;
import com.yj.cosmetics.util.RSAUtils;
import com.yj.cosmetics.util.MD5Utils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Suo on 2018/3/15.
 */

public class UserRegistActivity extends BaseActivity {
	private static final String TAG = "UserRegistActivity";
	@BindView(R.id.user_regist_tv_tel)
	TextView tvTel;
	@BindView(R.id.user_regist_et_tel)
	EditText etTel;
	@BindView(R.id.user_regist_tv_verify)
	TextView tvVerify;
	@BindView(R.id.user_regist_et_verify)
	EditText etVerify;
	@BindView(R.id.user_regist_tv_pwd)
	TextView tvPwd;
	@BindView(R.id.user_regist_et_pwd)
	EditText etPwd;
	@BindView(R.id.user_regist_tv_invite)
	TextView tvInvite;
	@BindView(R.id.user_regist_et_invite)
	EditText etInvite;
	@BindView(R.id.user_regist_tvsend)
	TextView tvSend;
	@BindView(R.id.user_regist_btn_confirm)
	Button btnConfirm;

//	@BindView(R.id.title_view)
//	View vLine;

	//	@BindView(R.id.title_layout)
//	LinearLayout llTop;
	@BindView(R.id.user_regist_cbx)
	CheckBox cbCheck;
	@BindView(R.id.scrollView)
	ScrollView mScrollView;

	boolean isSend = false;
	boolean isNetError = false;

	final int TAG_SMS = 0x11;
	private SMSThread mThread;
	private int countTime = 60;
	private boolean isScroll = false;

	CustomProgressDialog mDialog;

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

	@Override
	protected int getContentView() {
		return R.layout.activity_user_regists;
	}

	@Override
	protected void initView() {
		setTitleText("用户注册");
		hideTitle();
	}

	@Override
	protected void initData() {
		cbCheck.setChecked(true);
		mThread = new SMSThread();
		if (!mThread.isInterrupted()) {
			isSend = false;
		}
		mDialog = new CustomProgressDialog(this);
		initEtView();
/*
        etTel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollVertical(mScrollView.getHeight());
                return false;
            }
        });*/
		etPwd.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				scrollVertical(mScrollView.getHeight());
				return false;
			}
		});
		etVerify.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				scrollVertical(mScrollView.getHeight());
				return false;
			}
		});
		etInvite.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				scrollVertical(mScrollView.getHeight());
				return false;
			}
		});
	}

	@Override
	protected void showShadow1() {
//		vLine.setVisibility(View.GONE);
//		llTop.setBackgroundColor(getResources().getColor(R.color.CF7_F9_FA));
	}

	@OnClick({R.id.user_regist_agreement, R.id.user_regist_tvsend, R.id.user_regist_btn_confirm, R.id.title_ll_iv})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.user_regist_agreement:
				Intent intentAgreement = new Intent(this, NormalWebViewActivity.class);
				intentAgreement.putExtra("title", "用户服务协议");
				intentAgreement.putExtra("url", URLBuilder.URLBaseHeader + URLBuilder.Agreement);
				startActivity(intentAgreement);
				break;
			case R.id.user_regist_tvsend:
				String tel = etTel.getText().toString().trim();
				if (TextUtils.isEmpty(tel)) {
					ToastUtils.showToast(this, "请输入手机号");
					return;
				}
				inValidate(tel);
				break;
			case R.id.user_regist_btn_confirm:
				if (TextUtils.isEmpty(etTel.getText())) {
					ToastUtils.showToast(UserRegistActivity.this, "请输入手机号");
					return;
				}
				if (TextUtils.isEmpty(etPwd.getText())) {
					ToastUtils.showToast(UserRegistActivity.this, "请输入密码");
					return;
				}
				if (TextUtils.isEmpty(etVerify.getText())) {
					ToastUtils.showToast(UserRegistActivity.this, "请输入验证码");
					return;
				}
				if (etPwd.getText().toString().length() < 6) {
					ToastUtils.showToast(UserRegistActivity.this, "密码不小于6位");
					return;
				}
				if (etInvite.getText().toString().equals(etPwd.getText().toString())) {
					ToastUtils.showToast(UserRegistActivity.this, "再次输入密码错误");
					return;
				}
				if (!cbCheck.isChecked()) {
					ToastUtils.showToast(UserRegistActivity.this, "请阅读并同意用户协议");
					return;
				}
				//点击进行注册
				if (!MatchUtils.isValidPhoneNumber(etTel.getText().toString().trim())) {
					ToastUtils.showToast(this, "手机号格式不正确");
					return;
				} else {
					btnConfirm.setEnabled(false);
					doAsyncRegister(etTel.getText().toString().trim(), etPwd.getText().toString().trim(),
							etVerify.getText().toString().trim());
				}
				break;
			case R.id.title_ll_iv:
				finish();
				break;
		}
	}

	private void inValidate(String tel) {
		if (!MatchUtils.isValidPhoneNumber(tel)) {
			ToastUtils.showToast(this, "请输入正确的手机号码");
		} else {
			//手机号格式正确
			if (isSend) {
				return;
			}
			doAsyncSendMS(tel);
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
					LogUtils.i("弹出后的高度是" + mScrollView.getHeight());
					mScrollView.scrollTo(0, to);
					isScroll = false;
				}
			}, 500);
		}
	}

	private void doAsyncSendMS(String tel) {
		Map<String, String> map = new HashMap<>();
		map.put(Key.tel, tel);
		map.put(Key.type, Key.register);
		LogUtils.i("传输的值" + URLBuilder.format(map));
		try {
			OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.SendMsg)
					.tag(this)
					//.addParams(Key.data, URLBuilder.format(map))
					//.addParams("tel","18338764293")
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
								ToastUtils.showToast(UserRegistActivity.this, "网络故障,请稍后再试");
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
								ToastUtils.showToast(UserRegistActivity.this, "验证码已发送");
								isNetError = false;
							} else {
								ToastUtils.showToast(UserRegistActivity.this, response.getMsg() + "):" + response.getCode());
								isNetError = true;
								isSend = false;

							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//做异步的注册 使用okhttp进行操作
	private void doAsyncRegister(final String userName, final String passWord, String vertifyCode) {
		Map<String, String> map = new HashMap<>();
		map.put("userPhone", userName);
		map.put("password", MD5Utils.MD5(passWord));
		map.put("msgCode", vertifyCode);
		LogUtils.i("doAsyncRegister 传输的值" + URLBuilder.format(map));
		try {
			OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.Regist).tag(this)
//					.addParams(Key.data, AESUtils.encryptData(Constant.KEY, URLBuilder.format(map)))
//					.addParams("key", RSAUtils.encryptByPublicKey(Constant.KEY))
					.addParams(Key.data, URLBuilder.format(map))
					.build()
					.execute(new Utils.MyResultCallback<UserInfoEntity>() {

						@Override
						public void inProgress(float progress) {
							super.inProgress(progress);
							if (mDialog == null) {
								mDialog = new CustomProgressDialog(UserRegistActivity.this);
								if (!isFinishing()) {
									mDialog.show();
								}
							} else {
								if (!isFinishing()) {
									mDialog.show();
								}
							}
						}

						//网络请求错误时进行的操作
						@Override
						public void onError(Call call, Exception e) {
							super.onError(call, e);
							dismissDialog();
							btnConfirm.setEnabled(true);
							if (call.isCanceled()) {
								call.cancel();
							} else {
								ToastUtils.showToast(UserRegistActivity.this, "网络故障,请稍后再试");
							}
							LogUtils.i("注册的异常" + e);
						}

						//对返回的数据进行json解析.
						public UserInfoEntity parseNetworkResponse(Response response) throws Exception {
							String json = response.body().string().trim();
							LogUtils.i("doAsyncRegister json的值" + json);
							NormalEntity normalEntity = new Gson().fromJson(json, NormalEntity.class);
							if (normalEntity.getData().equals("") && !normalEntity.getCode().equals("200")) {
								return new UserInfoEntity(normalEntity.getCode(), normalEntity.getMsg());
							} else {
								return new Gson().fromJson(json, UserInfoEntity.class);
							}
						}

						//请求成功后对返回的resoponseCode进行判断.
						@Override
						public void onResponse(UserInfoEntity response) {
							if (isFinishing()) {
								return;
							}
							dismissDialog();
							if (response.getCode().equals(response.HTTP_OK)) {
								ToastUtils.showToast(UserRegistActivity.this, "注册成功");
								btnConfirm.setEnabled(true);
								Intent intent = new Intent();
								intent.setAction("CN.YJ.ROBUST.REFRESHDATA");
								sendBroadcast(intent);

								saveInfo(response);
								btnConfirm.postDelayed(new Runnable() {
									@Override
									public void run() {
										if (!TextUtils.isEmpty(getIntent().getStringExtra("jump"))) {
											Intent intent = new Intent(UserRegistActivity.this, MainActivity.class);
											startActivity(intent);
										}
										if (MyApplication.atyStack != null) {
											for (int i = 0; i < MyApplication.atyStack.size(); i++) {
												if (MyApplication.atyStack.get(i) instanceof UserLoginActivity) {
													LogUtils.i("我进入到有UserLoginActivity了");
													MyApplication.atyStack.get(i).finish();
												}
											}
										}
										finish();
									}
								}, 400);

							} else {
								ToastUtils.showToast(UserRegistActivity.this, response.getMsg() + "):" + response.getCode());
								btnConfirm.setEnabled(true);
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.e("doAsyncRegister: " + e.toString());
		}
	}

	private void saveInfo(UserInfoEntity info) {
		mUtils.saveTel(info.getData().getUserPhone());
		LogUtils.i("uid的值" + info.getData().getUserId());
		mUtils.saveUid(info.getData().getUserId());
		LogUtils.i("uid的userutils" + mUtils.getUid());
		mUtils.saveUserName(info.getData().getUserName());
		mUtils.saveAvatar(info.getData().getHeadImg());
		mUtils.saveInviteCode(info.getData().getAgentNumber());
		mUtils.saveQQOpenId(info.getData().getQqOpenId());
		mUtils.saveWXOpenId(info.getData().getWechatId());
		mUtils.saveAlipay(info.getData().getAlipayAccount());
		mUtils.saveAlipayName(info.getData().getAlipayName());
	}

	private void initEtView() {
		etPwd.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					if (tvPwd.getVisibility() == View.VISIBLE) {
						tvPwd.setVisibility(View.GONE);
					}
				} else {
					tvPwd.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				int index = etPwd.getSelectionStart() - 1;
				if (index > 0) {
					if (com.yj.cosmetics.util.TextUtils.isEmojiCharacter(s.charAt(index))) {
						Editable edit = etPwd.getText();
						edit.delete(s.length() - 2, s.length());
						ToastUtils.showToast(UserRegistActivity.this, "不支持输入表情符号");
					}
				}
			}
		});
		etTel.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					if (tvTel.getVisibility() == View.VISIBLE) {
						tvTel.setVisibility(View.GONE);
					}
				} else {
					tvTel.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		etVerify.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					if (tvVerify.getVisibility() == View.VISIBLE) {
						tvVerify.setVisibility(View.GONE);
					}
				} else {
					tvVerify.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		etInvite.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					if (tvInvite.getVisibility() == View.VISIBLE) {
						tvInvite.setVisibility(View.GONE);
					}
				} else {
					tvInvite.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
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

	private boolean isSoftShowing() {
		//获取当前屏幕内容的高度
		int screenHeight = getWindow().getDecorView().getHeight();
		//获取View可见区域的bottom
		Rect rect = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

		return screenHeight - rect.bottom - getSoftButtonsBarHeight() != 0;
	}

	/**
	 * 底部虚拟按键栏的高度
	 *
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private int getSoftButtonsBarHeight() {
		DisplayMetrics metrics = new DisplayMetrics();
		//这个方法获取可能不是真实屏幕的高度
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int usableHeight = metrics.heightPixels;
		//获取当前屏幕的真实高度
		getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
		int realHeight = metrics.heightPixels;
		if (realHeight > usableHeight) {
			return realHeight - usableHeight;
		} else {
			return 0;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isSend = false;
		mThread.interrupt();
		dismissDialog();
		OkHttpUtils.getInstance().cancelTag(this);
	}

	private void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}
}
