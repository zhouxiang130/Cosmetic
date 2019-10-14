package com.yj.cosmetics.ui.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.model.UpdateEntity;
import com.yj.cosmetics.service.DownAPKService;
import com.yj.cosmetics.ui.MainActivity;
import com.yj.cosmetics.ui.activity.mineHelpSug.MineHelpSugActivity;
import com.yj.cosmetics.util.CacheUtils;
import com.yj.cosmetics.util.GetInfoUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.PermissionUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.util.VersionUtils;
import com.yj.cosmetics.widget.Dialog.CustomNormalDialog;
import com.yj.cosmetics.widget.Dialog.UpdateDialog;
import com.yj.cosmetics.widget.SwitchView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

import static com.yj.cosmetics.util.PermissionUtils.REQUEST_CODE_WRITE_EXTRONAL_STORAGE;

/**
 * Created by Suo on 2018/3/10.
 */

public class MineSettingActivity extends BaseActivity {
	private static final String TAG = "MineSettingActivity";
	@BindView(R.id.mine_setting_version)
	TextView tvVersion;
	@BindView(R.id.mine_setting_cache)
	TextView tvCache;
	@BindView(R.id.mine_setting_logout)
	TextView tvLogOut;
	@BindView(R.id.iv_update_red)
	ImageView ivRed;
	@BindView(R.id.settlement_cart_switch_btn)
	SwitchView switchBtn;
	UpdateDialog updateDialog;
	AlertDialog alertDialog;
	private CustomNormalDialog infoDialog;
	private UpdateEntity.UpdateData data;
	private String newVersion;
	private String oldVersion;

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_setting;
	}

	@Override
	protected void initView() {
		tvCache.setText(CacheUtils.getTotalCacheSize(this));
		setTitleText("设置");
		updateDialog = new UpdateDialog(this);
		newVersion = mUtils.getVersion();
		oldVersion = GetInfoUtils.getAPPVersion(this);
		tvVersion.setText("v" + oldVersion);
		if (mUtils.isLogin()) {
			tvLogOut.setTextColor(getResources().getColor(R.color.CE8_3C_3C));
		} else {
			tvLogOut.setTextColor(getResources().getColor(R.color.C64_64_64));
		}
		if (!TextUtils.isEmpty(mUtils.getVersion())) {
			//不为空,比较
			if (VersionUtils.compareVersions(oldVersion, newVersion) == 1) {
				//版本不同

				if (!isWorked()) {
					ivRed.setVisibility(View.VISIBLE);
					tvVersion.setText("发现新版本");
				} else {
					ivRed.setVisibility(View.GONE);
					tvVersion.setText("正在下载..");
				}
			}
		}

	}

	@Override
	protected void initData() {

	}


	@OnClick({/*R.id.settlement_cart_switch_btn,*/ R.id.mine_setting_update, R.id.mine_setting_clean, R.id.mine_setting_logout, R.id.mine_setting_help})
	public void onClick(View view) {
		switch (view.getId()) {
//			case R.id.settlement_cart_switch_btn:
//				isCheckBox();
//				break;
			case R.id.mine_setting_update:
				if (isWorked()) {
					//service已经开启
					LogUtils.i("我进入到isWork中了");
					ToastUtils.showToast(MineSettingActivity.this, "正在下载更新");
				} else {
					LogUtils.i("我在没有work中");
					doAsyncTaskUpdate();
				}
				break;
			case R.id.mine_setting_clean:
				//清理缓存
				CacheUtils.clearAllCache(MineSettingActivity.this);
				tvCache.setText("您的手机非常干净");
				ToastUtils.showToast(MineSettingActivity.this, "缓存已清理完毕");
				break;
			case R.id.mine_setting_logout:
				if (mUtils.isLogin()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					final AlertDialog dialog = builder.create();
					dialog.setMessage("确认退出吗?");
					dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							//确认按钮
							mUtils.logOut();
							Intent intent = new Intent();
							intent.setAction("CN.YJ.ROBUST.REFRESHDATA");
							sendBroadcast(intent);
//							preferencesUtil.setValue("userType", "");
							delectJpushAlias();
							tvLogOut.postDelayed(new Runnable() {
								@Override
								public void run() {
									Intent intent = new Intent(MineSettingActivity.this, MainActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									finish();
								}
							}, 400);
						}
					});
					dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							if (dialog != null) {
								if (dialog.isShowing()) {
									dialog.dismiss();
								}
							}
						}
					});
					dialog.show();
				}
				break;
			case R.id.mine_setting_help:
				Intent intentSetting = new Intent(this, MineHelpSugActivity.class);
				startActivity(intentSetting);
				break;
		}
	}

	public void delectJpushAlias() {
		preferencesUtil.setBooleanValue("is_register_jpush_alias", true);
		JPushInterface.deleteAlias(getApplicationContext(), 0);
		JPushInterface.setAliasAndTags(this, null, null, new TagAliasCallback() {
			@Override
			public void gotResult(int code, String alias, Set<String> tags) {
				Log.d("alias", "set tag result is" + code + "alias--" + alias);
			}
		});
	}


	//点击查询版本并升级.
	private void doAsyncTaskUpdate() {
		OkHttpUtils.post()
				.url(URLBuilder.URLBaseHeader + "/phone/user/appVersion")
				.tag(this)
				.build()
				.execute(new Utils.MyResultCallback<UpdateEntity>() {
					@Override
					public void onError(Call call, Exception e) {
						super.onError(call, e);
						LogUtils.i("我网络故障了" + e);
						if (call.isCanceled()) {
							call.cancel();
						} else {
							ToastUtils.showToast(MineSettingActivity.this, "网络故障");
						}
					}

					@Override
					public UpdateEntity parseNetworkResponse(Response response) throws Exception {
						String json = response.body().string().trim();
						LogUtils.i("json的值" + json);
						NormalEntity normalEntity = new Gson().fromJson(json, NormalEntity.class);
						if (normalEntity.getData().equals("") && !normalEntity.getCode().equals("200")) {
							return new UpdateEntity(normalEntity.getCode(), normalEntity.getMsg());
						} else {
							return new Gson().fromJson(json, UpdateEntity.class);
						}
					}

					@Override
					public void onResponse(UpdateEntity response) {
						if (response != null && response.getCode().equals(response.HTTP_OK)) {
							data = response.getData();
							newVersion = data.getAppVersion();
							mUtils.saveVersion(newVersion);

							/**
							 * 版本号对比
							 *
							 * @return error : 返回-2 既传入版本号格式有误
							 * oldVersion > newVersion  return -1
							 * oldVersion = newVersion  return 0
							 * oldVersion < newVersion  return 1
							 */

							Log.e(TAG, "onAnimationEnd: newVersion>>>> " +
									"" + newVersion + " oldVersion>>> " + oldVersion + " " +
									" >>>>>> " + VersionUtils.compareVersions(newVersion, oldVersion));

							if (VersionUtils.compareVersions(oldVersion, newVersion) == 1) {
								//版本有更新
								isWritePermissionAllowed();

							} else {
								//版本没有更新
								ToastUtils.showToast(MineSettingActivity.this, "当前已是最新版本");
								ivRed.setVisibility(View.GONE);
							}


//							if (!newVersion.equals(oldVersion)) {
//								//版本有更新
//								isWritePermissionAllowed();
//							} else {
//								//版本没有更新
//								ToastUtils.showToast(MineSettingActivity.this, "当前已是最新版本");
//								ivRed.setVisibility(View.GONE);
//							}
						} else {

							if (!TextUtils.isEmpty(response.getMsg()) && response.getMsg().equals("无数据")) {
								ToastUtils.showToast(MineSettingActivity.this, "当前已是最新版本");
								ivRed.setVisibility(View.GONE);
							} else {
								ToastUtils.showToast(MineSettingActivity.this, "网络故障,请稍后再试");
							}
						}
					}
				});
	}

	private void showUpdateDialog() {
		if (updateDialog == null) {
			updateDialog = new UpdateDialog(this);
		}
		if (!updateDialog.isShowing()) {
			updateDialog.show();
		}
		updateDialog.getVersion().setText("v" + data.getAppVersion());
		updateDialog.getDesc().setText(data.getAppDesc().replace("\\n", "\n"));
		if (data.getAppForce().equals("2")) {
			//不是强制更新
			updateDialog.setIsCancelable(true);
			updateDialog.getUpdate().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//更新
					if (isWifi(MineSettingActivity.this)) {
						LogUtils.i("我进行后台下载了");
						ivRed.setVisibility(View.GONE);
						//判断服务是否开启 如果已经开启 不做操作 没有开启则开启服务进行下载

						ToastUtils.showToast(MineSettingActivity.this, "正在进行后台下载");
						Intent intent = new Intent(MineSettingActivity.this, DownAPKService.class);
						mUtils.saveLink(URLBuilder.URLBaseHeader + URLBuilder.getURLs(data.getAppLink()));
						String urLs = URLBuilder.getURLs(data.getAppLink());
						intent.putExtra("url", URLBuilder.getUrl(urLs));
						intent.putExtra("applen", data.getAppLen());
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startService(intent);
						dismissDialog();
					} else {
						LogUtils.i("我进入到非WIFi中");
						//非wifi链接
						dismissDialog();
						alertDialog = new AlertDialog.Builder(MineSettingActivity.this)
								.setMessage("确认升级?\n未检测到wifi网络,将使用流量升级")
								.setPositiveButton("确认", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										ivRed.setVisibility(View.GONE);
										Intent intent = new Intent(MineSettingActivity.this, DownAPKService.class);
										mUtils.saveLink(URLBuilder.getUrl(URLBuilder.getURLs(data.getAppLink())));
										String urLs = URLBuilder.getURLs(data.getAppLink());
										intent.putExtra("applen", data.getAppLen());
										intent.putExtra("url", URLBuilder.getUrl(urLs));
										startService(intent);
										dismissAlert();
									}
								})
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dismissAlert();
									}
								}).create();
						alertDialog.show();
					}
				}
			});
			updateDialog.getPass().setOnClickListener(new View.OnClickListener() {
				                                          @Override
				                                          public void onClick(View view) {
					                                          //pass了
					                                          dismissDialog();
				                                          }
			                                          }
			);
		} else {
			//强制更新
			updateDialog.getBottom().setVisibility(View.GONE);
			updateDialog.getForce().setVisibility(View.VISIBLE);
			updateDialog.setIsCancelable(false);
			updateDialog.getForce().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//立即更新 进入更新界面
					if (isWifi(MineSettingActivity.this)) {


						LogUtils.i("我进行后台下载了");
						//判断服务是否开启 如果已经开启 不做操作 没有开启则开启服务进行下载
						ToastUtils.showToast(MineSettingActivity.this, "正在进行后台下载");
						Intent intent = new Intent(MineSettingActivity.this, DownAPKService.class);

						Log.e(TAG, "getAppLink: " + URLBuilder.getUrl(URLBuilder.getURLs(data.getAppLink())));
						mUtils.saveLink(URLBuilder.getUrl(URLBuilder.getURLs(data.getAppLink())));
						String urLs = URLBuilder.getURLs(data.getAppLink());
						intent.putExtra("url", URLBuilder.getUrl(urLs));
						intent.putExtra("applen", data.getAppLen());
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						startService(intent);
						dismissDialog();

					} else {

						LogUtils.i("我进入到非WIFi中");
						//非wifi链接
						dismissDialog();
						alertDialog = new AlertDialog.Builder(MineSettingActivity.this)
								.setMessage("确认升级?\n未检测到wifi网络,将使用流量升级")
								.setPositiveButton("确认", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Intent intent = new Intent(MineSettingActivity.this, DownAPKService.class);

										Log.e(TAG, "getAppLink: " + URLBuilder.getUrl(URLBuilder.getURLs(data.getAppLink())));
										mUtils.saveLink(URLBuilder.getUrl(URLBuilder.getURLs(data.getAppLink())));
										String urLs = URLBuilder.getURLs(data.getAppLink());
										intent.putExtra("url", URLBuilder.getUrl(urLs));
										intent.putExtra("applen", data.getAppLen());
										startService(intent);
										dismissAlert();
									}
								})
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dismissAlert();

									}
								}).create();
						alertDialog.show();
						alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								dismissAlert();
							}
						});
					}
				}
			});
		}
		updateDialog.show();
	}

	private void isWritePermissionAllowed() {
		if (PermissionUtils.isPermissionAllowed(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			//为true 表示有权限
			if (data != null && !TextUtils.isEmpty(data.getAppVersion())) {
				showUpdateDialog();
			}
		} else {
			//没有相关权限
			if (PermissionUtils.isRemindAllowed(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_WRITE_EXTRONAL_STORAGE)) {
				//是否能弹窗提示
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTRONAL_STORAGE);
			} else {
				//弹窗提示已被关闭
				showDialog("为保证您的正常使用,请允许相应权限");
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case REQUEST_CODE_WRITE_EXTRONAL_STORAGE:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					//用户同意授权
					LogUtils.i("我进入到同意授权了");
					if (data != null && !TextUtils.isEmpty(data.getAppVersion())) {
						showUpdateDialog();
					}
				} else {
					LogUtils.i("我在不同意授权");
					showDialog("为保证您的正常使用,请允许相应权限");
				}
				break;
		}
	}

	//判断服务是否开启
	private boolean isWorked() {
		ActivityManager myManager = (ActivityManager) MineSettingActivity.this
				.getApplicationContext().getSystemService(
						Context.ACTIVITY_SERVICE);
		ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
				.getRunningServices(200);
		if (runningService.size() <= 0) {
			return false;
		}
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString()
					.equals("com.yj.robust.service.DownAPKService")) {
				LogUtils.i("我return true了");
				return true;
			}
		}
		return false;
	}

	/*
   * 判断网络状态
   * */
	public static boolean isWifi(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (cm == null)
				return false;
			return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
		} catch (Exception e) {
			ToastUtils.showToast(context, "未检测到网络状态");
		}
		return false;

	}

	private void showDialog(String text) {
		if (infoDialog == null) {
			infoDialog = new CustomNormalDialog(this);
		}
		if (!infoDialog.isShowing()) {
			infoDialog.show();
		}
		infoDialog.getTvTitle().setText(text);
		infoDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.i("我confirm了");
				PermissionUtils.getAppDetailSettingIntent(MineSettingActivity.this);
				infoDialog.dismiss();
			}
		});
		infoDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.i("我cancel了");
				PermissionUtils.getAppDetailSettingIntent(MineSettingActivity.this);
				infoDialog.dismiss();
			}
		});
	}


	private void dismissDialog() {
		if (updateDialog != null) {
			updateDialog.dismiss();
			updateDialog = null;
		}
	}

	private void dismissAlert() {
		if (alertDialog != null) {
			alertDialog.dismiss();
			alertDialog = null;
		}
	}

	private void dismissInfo() {
		if (infoDialog != null) {
			infoDialog.dismiss();
			infoDialog = null;
		}
	}

	@Override
	protected void onDestroy() {
		dismissDialog();
		dismissAlert();
		dismissInfo();
		OkHttpUtils.getInstance().cancelTag(this);
		super.onDestroy();
	}

	public void isCheckBox() {
		if (switchBtn.isOpened()) {
			Toast.makeText(this, "OPEN", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "CLOSE", Toast.LENGTH_SHORT).show();
		}
	}
}
