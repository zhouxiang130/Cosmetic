package com.yj.cosmetics.ui.activity.splash;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yj.cosmetics.MyApplication;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.UpdateEntity;
import com.yj.cosmetics.service.DownAPKService;
import com.yj.cosmetics.ui.MainActivity;
import com.yj.cosmetics.ui.activity.AppGuideActivity;
import com.yj.cosmetics.ui.activity.UpdateActivity;
import com.yj.cosmetics.util.GetInfoUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.PermissionUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.widget.Dialog.CustomNormalDialog;
import com.yj.cosmetics.widget.Dialog.UpdateDialog;

import java.util.ArrayList;

import butterknife.BindView;

import static com.yj.cosmetics.util.PermissionUtils.REQUEST_CODE_WRITE_EXTRONAL_STORAGE;

/**
 * Created by Suo on 2016/12/28.
 *
 * @TODO 启动页面
 */

public class SplashActivity extends BaseActivity implements Splash_Contract.View {
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
	private Splash_Contract.Presenter splashPresenter = new Splash_Presenter(this);

	@Override
	protected int getContentView() {
		return R.layout.activity_splash;
	}

	@Override
	protected void initView() {
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
		updateDialog = new UpdateDialog(this);
	}


	@Override
	public void setUpdateData(UpdateEntity.UpdateData data, String newVersion) {
		this.data = data;
		this.newVersion = newVersion;
	}


	@Override
	protected void initData() {
		splashPresenter.subscribe();
		oldVersion = GetInfoUtils.getAPPVersion(this);
		//Presenter 业务逻辑层 处理联网获取更新版本的信息
		splashPresenter.doAsyncTaskUpdate(mUtils);

		AlphaAnimation anim = new AlphaAnimation(0.3f, 1.0f);
		anim.setDuration(2000);
		llContent.startAnimation(anim);
		anim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (!TextUtils.isEmpty(newVersion)) {
					if (!newVersion.equals(oldVersion)) {
						//版本有更新
						if (data.getAppForce().equals("2")) {
							//非强制更新
							if (isWorked()) {
								//正在更新不做操作 直接跳转
								changePage();
							} else {
								isWritePermissionAllowed();
							}
						} else {
							//强制更新或  更新没有运行
							isWritePermissionAllowed();
						}
					} else {
						//版本没有更新
						changePage();
					}
				} else {
					changePage();
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
	}


	@Override
	public void changePage() {
		isToRedirectToGuide = mUtils.getIsFirstDirect();
		mUtils.saveIsFirstDirect(false);
		if (isToRedirectToGuide) {
			Intent intent = new Intent();
			intent.setClass(SplashActivity.this, AppGuideActivity.class);
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}


	/**
	 * 强制更新
	 */
	@Override
	public void isForceUpdate() {
		//立即更新 进入更新界面
		if (splashPresenter.isWifi(SplashActivity.this)) {

			ivSplash.postDelayed(new Runnable() {
				@Override
				public void run() {
					dismissDialog();
					startUpdateActivity();
				}
			}, 400);

		} else {
			dismissDialog();
			alertDialog = new AlertDialog.Builder(SplashActivity.this)
					.setMessage("确认升级?\n未检测到wifi网络,将使用流量升级")
					.setPositiveButton("确认", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {


							ivSplash.postDelayed(new Runnable() {
								@Override
								public void run() {
									dismissAlert();
									startUpdateActivity();
								}
							}, 400);
						}
					})
					.setNegativeButton("取消并退出", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dismissAlert();
							MyApplication.exit();
						}
					}).create();


			alertDialog.show();
			alertDialog.setCancelable(false);
			alertDialog.setCanceledOnTouchOutside(false);
		}
	}

	private void startUpdateActivity() {
		Intent intent = new Intent(SplashActivity.this, UpdateActivity.class);
		intent.putExtra("content", data.getAppDesc());
		intent.putExtra("url", URLBuilder.URLBaseHeader + data.getAppLink());
		startActivity(intent);
		MyApplication.exit();
	}


	@Override
	public void isUpdate() {
		//更新
		if (splashPresenter.isWifi(SplashActivity.this)) {
			LogUtils.i("我进行后台下载了");
			//判断服务是否开启 如果已经开启 不做操作 没有开启则开启服务进行下载
			ToastUtils.showToast(SplashActivity.this, "正在进行后台下载");
			Intent intent = new Intent(SplashActivity.this, DownAPKService.class);
			mUtils.saveLink(URLBuilder.URLBaseHeader + data.getAppLink());
			intent.putExtra("url", URLBuilder.URLBaseHeader + data.getAppLink());
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
							mUtils.saveLink(URLBuilder.URLBaseHeader + data.getAppLink());
							intent.putExtra("url", URLBuilder.URLBaseHeader + data.getAppLink());
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
			   /* PermissionUtils.getAppDetailSettingIntent(SplashActivity.this);
			    ToastUtils.showToast(this, "您已关闭读写功能,相关功能将不可用.");*/
			}
		}
	}

	private void showUpdateDialog() {
		if (updateDialog == null) {
			updateDialog = new UpdateDialog(this);
		}

		splashPresenter.initDialog(updateDialog);
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
//                    PermissionUtils.getAppDetailSettingIntent(SplashActivity.this);
					showDialog("为保证您的正常使用,请允许相应权限");
				}
				break;
		}
	}


	@Override
	public boolean alertDialog() {
		if (alertDialog != null && alertDialog.isShowing()) {
			return true;
		}
		return false;
	}

	//判断服务是否开启
	private boolean isWorked() {
		ActivityManager myManager = (ActivityManager) SplashActivity.this.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
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
		infoDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.i("我confirm了");
				PermissionUtils.getAppDetailSettingIntent(SplashActivity.this);
				infoDialog.dismiss();
			}
		});
		infoDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.i("我cancel了");
				PermissionUtils.getAppDetailSettingIntent(SplashActivity.this);
				infoDialog.dismiss();
			}
		});
	}

	@Override
	public void dismissDialog() {
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