package com.yj.cosmetics.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yj.cosmetics.MyApplication;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.model.UserInfoEntity;
import com.yj.cosmetics.ui.MainActivity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.MatchUtils;
import com.yj.cosmetics.util.SecurityUtil.MD5Utils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2018/3/15.
 */

public class UserLoginActivity extends BaseActivity {

	private static final String TAG = "UserLoginActivity";
	@BindView(R.id.user_login_tv_password)
	TextView tvPass;
	@BindView(R.id.user_login_tv_username)
	TextView tvAccount;
	@BindView(R.id.user_login_etCount)
	EditText etAccount;
	@BindView(R.id.user_login_etPass)
	EditText etPass;
	@BindView(R.id.user_login_show_password)
	ImageView ivPassword;

	@BindView(R.id.title_ll_iv)
	ImageView title_ll_iv;

	//	@BindView(R.id.title_view)
//	View vLine;
	@BindView(R.id.user_login_btnLogin)
	Button btnLogin;
	@BindView(R.id.scrollView)
	ScrollView mScrollView;
	@BindView(R.id.user_login_ll_bottom)
	LinearLayout llBottom;
	@BindView(R.id.user_login_ll_top)
	LinearLayout llTop;

	private String openId;
	private String name;
	private String icon;

	final String FLAG_BUNDLE = "Flag_Bundle";
	final int TAG_SMS = 0x11;
	final int NET_ERROR = 0x88;

	CustomProgressDialog mDialog;
	private String jump;
	private PlatformDb platDB;

	private boolean isScroll = false, canSee = true;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//            super.handleMessage(msg);
			switch (msg.what) {
				case TAG_SMS:
				    /*if(!msg.getData().isEmpty()){
				        String error = msg.getData().getString("error");
                        ToastUtils.custom(error);
                    }*/
					break;
				case NET_ERROR:
					ToastUtils.showToast(UserLoginActivity.this, "网络故障,请检查网络后再试");
					break;
				default:
					break;
			}
		}
	};


	@Override
	protected int getContentView() {
		return R.layout.activity_user_logins;
	}

	@Override
	protected void initView() {
		setTitleText("用户登录");
		initEtView();
		hideTitle();
		String tel = getIntent().getStringExtra("tel");
		String pass = getIntent().getStringExtra("pass");
		jump = getIntent().getStringExtra("jump");
		if (!TextUtils.isEmpty(tel)) {
			etAccount.setText(tel);
		}
		if (!TextUtils.isEmpty(pass)) {
			etPass.setText(pass);
		}
	}

	@Override
	protected void initData() {
		mDialog = new CustomProgressDialog(this);
		etAccount.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
			    /*LogUtils.e("是否活动=="+isSoftShowing());
			    if(isSoftShowing()){
                    return false;
                }*/
				LogUtils.e("底部的高度是" + llBottom.getHeight() + "=========整体高度是====" + mScrollView.getHeight());
				scrollVertical(mScrollView.getHeight() - llBottom.getHeight());
				return false;
			}
		});

		etPass.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
	            /*LogUtils.e("是否活动=="+isSoftShowing());
                if(isSoftShowing()){
                    return false;
                }*/
				LogUtils.e("底部的高度是" + llBottom.getHeight() + "=========整体高度是====" + mScrollView.getHeight());
				scrollVertical(mScrollView.getHeight() - llBottom.getHeight());
				return false;
			}
		});
	}

	@Override
	protected void showShadow1() {
//		vLine.setVisibility(View.GONE);
	}

	@OnClick({R.id.user_login_forgot, R.id.user_login_regist, R.id.user_login_btnLogin, R.id.qq_login, R.id.wechat_login, R.id.user_login_show_password, R.id.title_ll_iv})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.user_login_forgot:
				Intent intentForgot = new Intent(this, UserForgotActivity.class);
				startActivity(intentForgot);
				break;

			case R.id.user_login_regist:
				Intent intentRegist = new Intent(this, UserRegistActivity.class);
				if (!TextUtils.isEmpty(jump)) {
					intentRegist.putExtra("jump", jump);
				}
				startActivity(intentRegist);
				break;
			case R.id.user_login_btnLogin:
				if (TextUtils.isEmpty(etAccount.getText())) {
					ToastUtils.showToast(UserLoginActivity.this, "请输入手机号");
					return;
				}
				if (TextUtils.isEmpty(etPass.getText())) {
					ToastUtils.showToast(UserLoginActivity.this, "请输入密码");
					return;
				}
				if (etPass.getText().toString().length() < 6) {
					ToastUtils.showToast(UserLoginActivity.this, "密码不小于6位");
					return;
				}
				String tel = etAccount.getText().toString().trim();
				String password = etPass.getText().toString().trim();
				String MD5Password = MD5Utils.MD5(password);

				inValidate(tel, MD5Password);
				break;
			case R.id.qq_login:
				// QQ登录
//                Platform qq = ShareSDK.getPlatform(this, QQ.NAME);
//                authorize(qq);

				qqlogin();
				break;
			case R.id.wechat_login:
				wechatLogin();
				break;
			case R.id.user_login_show_password:
				String passWord_text = etPass.getText().toString().trim();
				if (canSee == false) {
					//如果是不能看到密码的情况下，
					etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
					etPass.setSelection(passWord_text.length());
					canSee = true;
					ivPassword.setImageResource(R.mipmap.yanjing);
				} else {
					//如果是能看到密码的状态下
					etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
					etPass.setSelection(passWord_text.length());
					canSee = false;
					ivPassword.setImageResource(R.mipmap.biyan);
				}
				break;
			case R.id.title_ll_iv:
				finish();
				break;
		}
	}

	private void inValidate(String tel, String password) {
		if (!MatchUtils.isValidPhoneNumber(tel)) {
			ToastUtils.showToast(this, "请输入正确的手机号码");
		} else {
			//手机号格式正确
			btnLogin.setEnabled(false);
			doAsyncLogin(tel, password);
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
					LogUtils.e("弹出后的高度是" + to);
					mScrollView.scrollTo(0, to);
					isScroll = false;
				}
			}, 500);
		}
	}


	private void doAsyncLogin(String tel, final String password) {
		LogUtils.e(" 我进入网络操作了");
		Map<String, String> map = new HashMap<>();
		map.put("userPhone", tel);
		map.put("password", password);
		map.put("loginType", "4");
		LogUtils.e("params的值" + URLBuilder.format(map));

		try {
			OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.Login).tag(this)
//					.addParams(Key.data, AESUtils.encryptData(Constant.KEY, URLBuilder.format(map)))
//					.addParams("key", RSAUtils.encryptByPublicKey(Constant.KEY))
					.addParams(Key.data, URLBuilder.format(map))
					.build().execute(new Utils.MyResultCallback<UserInfoEntity>() {

				@Override
				public void inProgress(float progress) {
					super.inProgress(progress);
					if (mDialog == null) {
						mDialog = new CustomProgressDialog(UserLoginActivity.this);
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
				public UserInfoEntity parseNetworkResponse(Response response) throws Exception {
					String json = response.body().string().trim();
					LogUtils.e("doAsyncLogin --- json的值" + json);
					NormalEntity normalEntity = new Gson().fromJson(json, NormalEntity.class);
					if (normalEntity.getData().equals("") && !normalEntity.getCode().equals("200")) {
						return new UserInfoEntity(normalEntity.getCode(), normalEntity.getMsg());
					} else {
						return new Gson().fromJson(json, UserInfoEntity.class);
					}
				}

				@Override
				public void onResponse(UserInfoEntity response) {
					LogUtils.e("我在response中");
					if (isFinishing()) {
						return;
					}
					disMissDialog();

					if (response.getCode().equals(response.HTTP_OK)) {
						ToastUtils.showToast(UserLoginActivity.this, "登陆成功");
						saveInfo(response);
						btnLogin.postDelayed(new Runnable() {
							@Override
							public void run() {
								if (!TextUtils.isEmpty(jump)) {
									Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
									startActivity(intent);
								}
								finish();
							}
						}, 400);

					} else {
						ToastUtils.showToast(UserLoginActivity.this, response.getMsg() + "):" + response.getCode());
						btnLogin.setEnabled(true);
					}
				}

				@Override
				public void onError(Call call, Exception e) {
					btnLogin.setEnabled(true);
					if (call.isCanceled()) {
						call.cancel();
					} else {
						ToastUtils.showToast(UserLoginActivity.this, "网络故障,请稍后再试");
					}

					LogUtils.e("网络故障" + e);
					disMissDialog();
					super.onError(call, e);

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "doAsyncLogin: " + e.toString());
		}
	}

	private void saveInfo(UserInfoEntity info) {
		mUtils.saveTel(info.getData().getUserPhone());
		LogUtils.e("uid的值" + info.getData().getUserId());
		mUtils.saveUid(info.getData().getUserId());
		LogUtils.e("uid的userutils" + mUtils.getUid());
		mUtils.saveUserName(info.getData().getUserName());
		LogUtils.e("HeadImd--" + info.getData().getHeadImg());
		mUtils.saveAvatar(info.getData().getHeadImg());
		LogUtils.e("headImg ======" + mUtils.getAvatar());
		mUtils.saveInviteCode(info.getData().getAgentNumber());
		mUtils.saveQQOpenId(info.getData().getQqOpenId());
		mUtils.saveWXOpenId(info.getData().getWechatId());
		mUtils.saveAlipay(info.getData().getAlipayAccount());
		mUtils.saveAlipayName(info.getData().getAlipayName());

		Intent intent = new Intent();
		intent.setAction("CN.YJ.ROBUST.REFRESHDATA");
		sendBroadcast(intent);

	}

	private void saveInfoThird(UserInfoEntity info) {

		if (TextUtils.isEmpty(info.getData().getPhone())) {
			mUtils.saveTel(info.getData().getUserPhone());
		} else {
			mUtils.saveTel(info.getData().getPhone());
		}

		if (TextUtils.isEmpty(info.getData().getUserId())) {
			mUtils.saveUid(info.getData().getId());
		} else {
			mUtils.saveUid(info.getData().getUserId());
		}

		if (TextUtils.isEmpty(info.getData().getName())) {
			mUtils.saveUserName(info.getData().getUserName());
		} else {
			mUtils.saveUserName(info.getData().getName());
		}
		if (TextUtils.isEmpty(info.getData().getHeadImg())) {
			mUtils.saveAvatar(info.getData().getImg());
		} else {
			mUtils.saveAvatar(info.getData().getHeadImg());
		}
		mUtils.saveInviteCode(info.getData().getAgentNumber());
		mUtils.saveQQOpenId(info.getData().getQqOpenId());
		mUtils.saveWXOpenId(info.getData().getWechatId());
		mUtils.saveAlipay(info.getData().getAlipayAccount());
		mUtils.saveAlipayName(info.getData().getAlipayName());

		Intent intent = new Intent();
		intent.setAction("CN.YJ.ROBUST.REFRESHDATA");
		sendBroadcast(intent);

	}

	/**
	 * 执行授权,获取用户信息
	 *
	 * @param plat
	 */
	private void authorize(Platform plat) {
		if (plat == null) {
			return;
		}

		// 使用SSO授权。有客户端的都会优先启用客户端授权，没客户端的则任然使用网页版进行授权。
		plat.SSOSetting(false);
		plat.setPlatformActionListener(new PlatformActionListener() {
			@Override
			public void onComplete(Platform arg0, int arg1, HashMap<String, Object> map) {
				String headImageUrl = null;//头像
				String token;//token
				String gender;//年龄
				String userId;
				String name = null;//用户名

				// 用户资源都保存到res
				// 通过打印res数据看看有哪些数据是你想要的
				if (arg1 == Platform.ACTION_USER_INFOR) {
					platDB = arg0.getDb(); // 获取数平台数据DB
					if (arg0.getName().equals(Wechat.NAME)) {

						// 通过DB获取各种数据
						token = platDB.getToken();
						userId = platDB.getUserId();
						name = platDB.getUserName();
						gender = platDB.getUserGender();
						headImageUrl = platDB.getUserIcon();
						if ("m".equals(gender)) {
							gender = "1";
						} else {
							gender = "2";
						}

					} else if (arg0.getName().equals(SinaWeibo.NAME)) {
						// 微博登录
					} else if (arg0.getName().equals(QQ.NAME)) {
						// QQ登录
						token = platDB.getToken();
						userId = platDB.getUserId();
						name = map.get("nickname").toString(); // 名字
						gender = map.get("gender").toString(); // 年龄
						headImageUrl = map.get("figureurl_qq_2").toString(); // 头像figureurl_qq_2 中等图，figureurl_qq_1缩略图
						String city = map.get("city").toString(); // 城市
						String province = map.get("province").toString(); // 省份
						getUserInfo(name, headImageUrl);

					}
				}
			}


			@Override
			public void onError(Platform platform, int i, Throwable throwable) {
				Toast.makeText(UserLoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCancel(Platform platform, int i) {

			}
		});
//		plat.authorize();
		// 参数null表示获取当前授权用户资料
		plat.showUser(null);
	}


	private void getUserInfo(final String name, final String imgUrl) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "run: " + name + "imgUrl" + imgUrl);
			}
		});
	}


	private void qqlogin() {
		Platform qq = ShareSDK.getPlatform(QQ.NAME);
		qq.SSOSetting(false);
		// 此处必须删除授权的资料，不删除会出现第三方平台退出依然能够登录
		if (qq.isAuthValid()) { //如果授权就删除授权资料
			qq.removeAccount(true);
		}
		//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
		qq.setPlatformActionListener(new PlatformActionListener() {
			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				LogUtils.e("11我onError了" + arg2);
				arg2.printStackTrace();
				Bundle bundle = new Bundle();
				bundle.putString("error", arg2 + "");
				Message msg = mHandler.obtainMessage();
				msg.setData(bundle);
				msg.what = TAG_SMS;
				mHandler.sendMessage(msg);
			}

			@Override
			public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
				// TODO Auto-generated method stub
				Message msg = mHandler.obtainMessage();
				msg.what = TAG_SMS;
				mHandler.sendMessage(msg);
				//输出所有授权信息
				arg0.getDb().exportData();
				PlatformDb platDB = arg0.getDb();//获取数平台数据DB
				//通过DB获取各种数据
				String token = platDB.getToken();
				String gender = platDB.getUserGender();
				icon = platDB.getUserIcon();
				String id = platDB.getUserId();
				name = platDB.getUserName();
				openId = arg0.getDb().getUserId();
				LogUtils.e(arg0.getDb().getUserId() + "userid........................");
				LogUtils.e("对象值" + platDB);
				LogUtils.e("token" + token + ".....gender" + gender + ".....icon" + icon + ".....id" + id + ".....name" + name);


				LogUtils.e(".icon的值" + icon + "..........");
				if (openId != null) {
					LogUtils.e("." + openId + "..........");
//                    doRequestOpenId(openId,"QQ",name,icon);
					doRequestOpenId("QQ");
				}
//                IntentUtils.startAty(UserLoginActivity.this,MainActivity.class);
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				LogUtils.e("我onCancell ");
				// TODO Auto-generated method stub
				Message msg = mHandler.obtainMessage();
				msg.what = TAG_SMS;
				mHandler.sendMessage(msg);

			}
		});
		if (qq.isClientValid()) {
			//判断是否存在授权凭条的客户端，true是有客户端，false是无
		}
		//authorize与showUser单独调用一个即可
		//qq.authorize();
		qq.showUser(null);//授权并获取用户信息
		LogUtils.e("userid........................" + qq.getDb().getUserId());
		//移除授权
		//weibo.removeAccount(true);
	}

	private void wechatLogin() {
		if (!MyApplication.mWxApi.isWXAppInstalled()) {
			ToastUtils.showToast(UserLoginActivity.this, "您还未安装微信客户端");
			return;
		}
		Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
		wechat.SSOSetting(false);
		if (wechat.isAuthValid()) { //如果授权就删除授权资料
			wechat.removeAccount(true);
		}
		//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
		wechat.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				LogUtils.e("222我onError了" + arg2);
				arg2.printStackTrace();
				Bundle bundle = new Bundle();
				bundle.putString("error", arg2 + "");
				Message msg = mHandler.obtainMessage();
				msg.setData(bundle);
				msg.what = TAG_SMS;
				mHandler.sendMessage(msg);
			}

			@Override
			public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
				Message msg = mHandler.obtainMessage();
				msg.what = TAG_SMS;
				mHandler.sendMessage(msg);
				// TODO Auto-generated method stub
				//输出所有授权信息
				arg0.getDb().exportData();
				PlatformDb platDB = arg0.getDb();//获取数平台数据DB
				//通过DB获取各种数据
				String token = platDB.getToken();
				String gender = platDB.getUserGender();
				icon = platDB.getUserIcon();
				String id = platDB.getUserId();
				name = platDB.getUserName();
				openId = arg2.get("unionid").toString();
				LogUtils.e("全部返回值" + arg2);
				Log.e(TAG, "openId." + openId + "..........");
				LogUtils.e("token" + token + ".....gender" + gender + ".....icon" + icon + ".....id" + id + ".....name" + name);
				LogUtils.e(".icon的值" + icon + "..........");
				LogUtils.e("weixin  UnionId>>>>> " + openId);
				if (openId != null) {
//                    doRequestOpenId(openId,"weChat",name,icon);
					doRequestOpenId("weChat");
				}
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				// TODO Auto-generated method stub
				Message msg = mHandler.obtainMessage();
				msg.what = TAG_SMS;
				mHandler.sendMessage(msg);
			}
		});
		//authorize与showUser单独调用一个即可
//		wechat.authorize();
		wechat.showUser(null);//授权并获取用户信息
		//移除授权
//		wechat.removeAccount(true);
	}

	private void doRequestOpenId(final String platform) {
		RequestCall params = null;
		if (platform.equals("QQ")) {
			Map<String, String> map = new HashMap<>();
			map.put("qqOpenid", openId);
			map.put("loginType", "4");
			params = OkHttpUtils.post()
					.url(URLBuilder.URLBaseHeader + URLBuilder.OpenIdVerify)
					.tag(this)
					.addParams(Key.data, URLBuilder.format(map))
					.build();
			LogUtils.e("传输的值" + URLBuilder.format(map));
		} else if (platform.equals("weChat")) {
			Map<String, String> map = new HashMap<>();
			map.put("wxOpenid", openId);
			map.put("loginType", "4");
			params = OkHttpUtils.post()
					.url(URLBuilder.URLBaseHeader + URLBuilder.OpenIdVerify)
					.tag(this)
					.addParams(Key.data, URLBuilder.format(map))
					.build();
			LogUtils.e("传输的值" + URLBuilder.format(map));
		}

		params.execute(new Utils.MyResultCallback<UserInfoEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(UserLoginActivity.this, "网络故障,请稍后再试");
				}
				LogUtils.e("网络故障" + e);
			}

			@Override
			public UserInfoEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.e("json的值" + json);
				NormalEntity normalEntity = new Gson().fromJson(json, NormalEntity.class);
				if (normalEntity.getData().equals("") && !normalEntity.getCode().equals("200")) {
					return new UserInfoEntity(normalEntity.getCode(), normalEntity.getMsg());
				} else {
					return new Gson().fromJson(json, UserInfoEntity.class);
				}
			}

			@Override
			public void onResponse(UserInfoEntity response) {
				if (isFinishing()) {
					return;
				}
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					saveInfoThird(response);
					ToastUtils.showToast(UserLoginActivity.this, "验证成功,正在登陆");
					btnLogin.postDelayed(new Runnable() {
						@Override
						public void run() {
							if (!TextUtils.isEmpty(jump)) {
								Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
								startActivity(intent);
							}
							finish();
						}
					}, 400);
				} else {
					ToastUtils.showToast(UserLoginActivity.this, "请绑定手机号");
					btnLogin.postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent(UserLoginActivity.this, ThirdLoginRegistActivity.class);
							intent.putExtra("openId", openId);
							intent.putExtra("platform", platform);
							intent.putExtra("name", name);
							intent.putExtra("icon", icon);
							if (!TextUtils.isEmpty(jump)) {
								intent.putExtra("jump", jump);
							}

							startActivity(intent);
						}
					}, 400);
				}
			}
		});
	}


	private void initEtView() {
		etPass.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				LogUtils.e("count的值。" + count);
				if (s.length() > 0) {
					LogUtils.e("我长度大于0了");
					if (tvPass.getVisibility() == View.VISIBLE) {
						tvPass.setVisibility(View.GONE);
					}
				} else {
					LogUtils.e("我显示了");
					tvPass.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				int index = etPass.getSelectionStart() - 1;
				if (index > 0) {
					if (com.yj.cosmetics.util.TextUtils.isEmojiCharacter(s.charAt(index))) {
						Editable edit = etPass.getText();
						edit.delete(s.length() - 2, s.length());
						ToastUtils.showToast(UserLoginActivity.this, "不支持输入表情符号");
					}
				}
			}
		});
		etAccount.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				LogUtils.e("count的值。" + count);
				if (s.length() > 0) {
					LogUtils.e("我长度大于0了");
					if (tvAccount.getVisibility() == View.VISIBLE) {
						tvAccount.setVisibility(View.GONE);
					}
				} else {
					LogUtils.e("我显示了");
					tvAccount.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
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

	private void disMissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		OkHttpUtils.getInstance().cancelTag(this);
		disMissDialog();
	}
}
