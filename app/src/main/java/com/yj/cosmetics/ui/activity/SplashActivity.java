package com.yj.cosmetics.ui.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.UpdateEntity;
import com.yj.cosmetics.service.DownAPKService;
import com.yj.cosmetics.util.GetInfoUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.PermissionUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.util.VersionUtils;
import com.yj.cosmetics.widget.Dialog.CustomNormalDialog;
import com.yj.cosmetics.widget.Dialog.UpdateDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

import static com.yj.cosmetics.util.PermissionUtils.REQUEST_CODE_WRITE_EXTRONAL_STORAGE;

/**
 * Created by Administrator on 2018/5/23 0023.
 */

public class SplashActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
	@BindView(R.id.ll_splash)
	RelativeLayout llContent;
	@BindView(R.id.iv_splash_img)
	ImageView ivSplash;
	boolean isToRedirectToGuide = true;
	private String oldVersion;
	private String newVersion;
	UpdateEntity.UpdateData data;
	UpdateDialog updateDialog;
	AlertDialog alertDialog;
	private CustomNormalDialog infoDialog;
	/**
	 * 需要进行检测的权限数组
	 */
	protected String[] needPermissions = {
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.CAMERA,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.CALL_PHONE
	};

	private static final int PERMISSON_REQUESTCODE = 0;

	/**
	 * 判断是否需要检测，防止不停的弹框
	 */
	private boolean isNeedCheck = true;

	@Override
	protected int getContentView() {
		return R.layout.activity_splash;
	}

	@Override
	protected void initView() {
		mUtils.saveRefreshStore("true");
		// 避免从桌面启动程序后，会重新实例化入口类的activity 实现当程序进入后台后 再次进入程序无需重新运行.
		if (!this.isTaskRoot()) {
			Intent intent = getIntent();
			if (intent != null) {
				String action = intent.getAction();
				if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
					finish();
					return;
				}
			}
		}
		oldVersion = GetInfoUtils.getAPPVersion(this);
		updateDialog = new UpdateDialog(this);
	}

	@Override
	protected void initData() {
		doAsyncTaskUpdate();
		AlphaAnimation anim = new AlphaAnimation(0.3f, 1.0f);
		anim.setDuration(1500);
		llContent.startAnimation(anim);
		anim.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (!TextUtils.isEmpty(newVersion)) {
					/**
					 * 版本号对比
					 *
					 * @return error : 返回-2 既传入版本号格式有误
					 * oldVersion > newVersion  return -1
					 * oldVersion = newVersion  return 0
					 * oldVersion < newVersion  return 1
					 */

					LogUtils.e("onAnimationEnd: newVersion>>>> " +
							"" + newVersion + " oldVersion>>> " + oldVersion + " " +
							" >>>>>> " + VersionUtils.compareVersions(newVersion, oldVersion));

					if (VersionUtils.compareVersions(oldVersion, newVersion) == 1) {
						//版本有更新
						if (data.getAppForce().equals("2")) {
							//非强制更新
							if (isWorked()) {
								//正在更新不做操作 直接跳转
								changePage();
							} else {
								//先显示更新框，在提示打开文件读写权限
								if (isNeedCheck) {
									checkPermissions(needPermissions);
								}
//								showUpdateDialog();
//								isWritePermissionAllowed();
							}
						} else {
							//强制更新或更新没有运行 先显示更新框，在提示打开文件读写权限
//							isWritePermissionAllowed();
							if (isNeedCheck) {
								checkPermissions(needPermissions);
							}
//							showUpdateDialog();
						}
					} else {
						///版本没有更新,x
						if (isNeedCheck) {
							checkPermissions(needPermissions);
						}
					}
				} else {
					if (isNeedCheck) {
						checkPermissions(needPermissions);
					}
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
	}

	/**
	 * @since 2.5.0
	 */
	private void checkPermissions(String... permissions) {
		List<String> needRequestPermissonList = findDeniedPermissions(permissions);
		if (null != needRequestPermissonList && needRequestPermissonList.size() > 0) {
			ActivityCompat.requestPermissions(this, needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]), PERMISSON_REQUESTCODE);
		}
		if (needRequestPermissonList.size() == PERMISSON_REQUESTCODE) {
			//版本有更新
			if (!TextUtils.isEmpty(newVersion)) {
				if (VersionUtils.compareVersions(oldVersion, newVersion) == 1) {
					/**
					 * 如果后台返回的apk大小值为0，说明后台没有apk安装包。此时不去弹出更新提示框，直接跳转到首页
					 */
					if (!data.getAppLen().equals("0")) {
						showUpdateDialog();
					} else {
						changePage();
					}
				} else {
					changePage();
				}
			} else {
				changePage();
			}
		}
//		else {
//			changePage();
//		}
	}

	/**
	 * 获取权限集中需要申请权限的列表
	 *
	 * @param permissions
	 * @return
	 * @since 2.5.0
	 */
	private List<String> findDeniedPermissions(String[] permissions) {
		List<String> needRequestPermissonList = new ArrayList<>();
		for (String perm : permissions) {
			if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
				needRequestPermissonList.add(perm);
			}
		}
		return needRequestPermissonList;
	}


	private void changePage() {
		isToRedirectToGuide = mUtils.getIsFirstDirect();
		mUtils.saveIsFirstDirect(false);

		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		startActivity(intent);
		finish();

		/*if (isToRedirectToGuide) {
			Intent intent = new Intent();
			intent.setClass(SplashActivity.this, AppGuideActivity.class);
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}*/
	}


	private void doAsyncTaskUpdate() {
		OkHttpUtils.post()
				.url(URLBuilder.URLBaseHeader + "/phone/user/appVersion")
				.tag(this)
				.build()
				.execute(new Utils.MyResultCallback<UpdateEntity>() {
					@Override
					public void onError(Call call, Exception e) {
						super.onError(call, e);

//						String json = JsonUtils.getJson("datasss.txt", SplashActivity.this);
//						LogUtils.i("initData: " + json);
//						data = new Gson().fromJson(json, UpdateEntity.class).getData();
//						newVersion = data.getAppVersion();
					}

					@Override
					public UpdateEntity parseNetworkResponse(Response response) throws Exception {
						String json = response.body().string();
						LogUtils.i("doAsyncTaskUpdate --- json的值" + json);
						return new Gson().fromJson(json, UpdateEntity.class);
					}

					@Override
					public void onResponse(UpdateEntity response) {
						if (response != null) {
							if (response.getCode().equals(response.HTTP_OK)) {
								data = response.getData();
								newVersion = data.getAppVersion();
								mUtils.saveVersion(newVersion);
							} else {

							}
						}
					}
				});
	}


	/***
	 * 弹出提示框，显示版本更新的提示框
	 */
	private void showUpdateDialog() {
		if (updateDialog == null) {
			updateDialog = new UpdateDialog(this);
		}
		if (!updateDialog.isShowing()) {
			updateDialog.show();
		}
		LogUtils.i("updateDialog的值" + updateDialog);
		LogUtils.i("version的值" + data.getAppVersion());
		updateDialog.getVersion().setText("v" + data.getAppVersion());
		updateDialog.getDesc().setText(data.getAppDesc().replace("\\n", "\n"));
		if (data.getAppForce().equals("2")) {
			//不是强制更新
			updateDialog.setIsCancelable(true);
			updateDialog.getUpdate().setOnClickListener(
					new View.OnClickListener() {//立即下载
						@Override
						public void onClick(View view) {
							//@TODO立即下载要先去判断权限是否开启读写权限，如果没有开启读写权限不去下载apk包，
							isWritePermissionAllowed();
//					isStartServices();

						}
					});
			updateDialog.getPass().setOnClickListener(new View.OnClickListener() {//稍后再说
				                                          @Override
				                                          public void onClick(View view) {
					                                          //pass了
					                                          dismissDialog();
					                                          changePage();
				                                          }
			                                          }
			);
			updateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					if (alertDialog()) return;
					changePage();
				}
			});
		} else {
			//强制更新
			updateDialog.getBottom().setVisibility(View.GONE);
			updateDialog.getForce().setVisibility(View.VISIBLE);
			updateDialog.setIsCancelable(false);
			updateDialog.getForce().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//立即更新 进入更新界面
//					isStartServices();
					isWritePermissionAllowed();
				}
			});
		}
		updateDialog.show();
	}

	private void isStartServices() {
		//更新
		if (isWifi(SplashActivity.this)) {
			LogUtils.i("我进行后台下载了");
			//判断服务是否开启 如果已经开启 不做操作 没有开启则开启服务进行下载
			ToastUtils.showToast(SplashActivity.this, "正在进行后台下载");
			Intent intent = new Intent(SplashActivity.this, DownAPKService.class);
			LogUtils.e("getAppLink: " + URLBuilder.URLBaseHeader + URLBuilder.getURLs(data.getAppLink()));
			mUtils.saveLink(URLBuilder.URLBaseHeader + URLBuilder.getURLs(data.getAppLink()));
			String urLs = URLBuilder.getURLs(data.getAppLink());
			intent.putExtra("url", URLBuilder.getUrl(urLs));
			intent.putExtra("applen", data.getAppLen());
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startService(intent);
			dismissDialog();
			changePage();
		} else {
			LogUtils.i("我进入到非WIFi中");
			//非wifi链接
			dismissDialog();
			alertDialog = new AlertDialog.Builder(SplashActivity.this)
					.setMessage("确认升级?\n未检测到wifi网络,将使用流量升级")
					.setPositiveButton("确认", new DialogInterface.OnClickListener() {


						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(SplashActivity.this, DownAPKService.class);
							mUtils.saveLink(URLBuilder.URLBaseHeader + URLBuilder.getURLs(data.getAppLink()));
							LogUtils.e("getAppLink: " + URLBuilder.URLBaseHeader + URLBuilder.getURLs(data.getAppLink()));
							String urLs = URLBuilder.getURLs(data.getAppLink());
							intent.putExtra("url", URLBuilder.getUrl(urLs));
							intent.putExtra("applen", data.getAppLen());
							startService(intent);
							dismissAlert();
							changePage();
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dismissAlert();
							changePage();
						}
					}).create();
			alertDialog.show();
			alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					changePage();
				}
			});
		}
	}

	private boolean alertDialog() {
		if (alertDialog != null && alertDialog.isShowing()) {
			return true;
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


	//判断文件读写权限
	private void isWritePermissionAllowed() {
		if (PermissionUtils.isPermissionAllowed(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			//为true 表示有权限
			if (data != null && !TextUtils.isEmpty(data.getAppVersion())) {
				isStartServices();
			}
		} else {

//			Manifest.permission.ACCESS_FINE_LOCATION,
//					Manifest.permission.ACCESS_FINE_LOCATION,
			//没有相关权限
			if (PermissionUtils.isRemindAllowed(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_WRITE_EXTRONAL_STORAGE)) {
				//是否能弹窗提示
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTRONAL_STORAGE);
			} else {
				//弹窗提示已被关闭
				showDialog("为保证您的正常使用,请允许相应权限");
//				showUpdateDialog();
               /* PermissionUtils.getAppDetailSettingIntent(SplashActivity.this);
                ToastUtils.showToast(this, "您已关闭读写功能,相关功能将不可用.");*/
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case REQUEST_CODE_WRITE_EXTRONAL_STORAGE:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					//用户同意授权。开启服务后台下载apk安装包
					LogUtils.i("我进入到同意授权了");
					if (data != null && !TextUtils.isEmpty(data.getAppVersion())) {
						isStartServices();
					}
				} else {
					LogUtils.i("我在不同意授权");
					//是否能弹窗提示
					showDialog("为保证您的正常使用,请允许相应权限");
				}
				break;
			case PERMISSON_REQUESTCODE:
				if (!verifyPermissions(grantResults)) {
//					showMissingPermissionDialog();
					isNeedCheck = false;
				}

				if (!TextUtils.isEmpty(newVersion)) {
					if (VersionUtils.compareVersions(oldVersion, newVersion) == 1) {
						showUpdateDialog();
					} else {
						changePage();
					}
				} else {
					changePage();
				}
				LogUtils.e("onRequestPermissionsResult: " + isNeedCheck);
				break;
		}
	}

	/**
	 * 检测是否说有的权限都已经授权
	 *
	 * @param grantResults
	 * @return
	 * @since 2.5.0
	 */
	private boolean verifyPermissions(int[] grantResults) {
		for (int result : grantResults) {
			if (result != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		}
		return true;
	}

	//判断服务是否开启
	private boolean isWorked() {
		ActivityManager myManager = (ActivityManager) SplashActivity.this
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

	private void showDialog(String text) {
		if (infoDialog == null) {
			infoDialog = new CustomNormalDialog(this);
		}
		if (!infoDialog.isShowing()) {
			infoDialog.show();
		}
		infoDialog.getTvTitle().setText(text);
		infoDialog.getTvConfirm().setOnClickListener(

				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						LogUtils.i("我confirm了");
//			PermissionUtils.getAppDetailSettingIntent(SplashActivity.this);
//			isWritePermissionAllowed();
						ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTRONAL_STORAGE);
						infoDialog.dismiss();
					}
				}
//				view -> {
//				}
		);

		infoDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			                                            @Override
			                                            public void onClick(View view) {
				                                            LogUtils.i("我cancel了");
				                                            infoDialog.dismiss();

			                                            }
		                                            }


//				view -> {
//			PermissionUtils.getAppDetailSettingIntent(SplashActivity.this);
//			showDialog("为保证您的正常使用,请允许相应权限");}
//				}
		);
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
		super.onDestroy();
	}
}
