package com.yj.cosmetics.ui.activity.splash;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.view.View;

import com.google.gson.Gson;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.UpdateEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.UpdateDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/23 0023.
 */

public class Splash_Presenter implements Splash_Contract.Presenter {


	private final Splash_Contract.View splash_view;
	UpdateEntity.UpdateData data;
	private String newVersion;

	public Splash_Presenter(Splash_Contract.View view) {
		this.splash_view = view;
	}

	@Override
	public void subscribe() {

	}

	@Override
	public void doAsyncTaskUpdate(final UserUtils mUtils) {
		OkHttpUtils.post()
				.url(URLBuilder.URLBaseHeader + "/phone/user/appVersion")
				.tag(this)
				.build()
				.execute(new Utils.MyResultCallback<UpdateEntity>() {
					@Override
					public void onError(Call call, Exception e) {
						super.onError(call, e);
					}

					@Override
					public UpdateEntity parseNetworkResponse(Response response) throws Exception {
						String json = response.body().string();
						LogUtils.i("json的值" + json);
						return new Gson().fromJson(json, UpdateEntity.class);
					}

					@Override
					public void onResponse(UpdateEntity response) {
						if (response != null) {
							if (response.getCode().equals(response.HTTP_OK)) {
								data = response.getData();
								newVersion = data.getAppVersion();
								splash_view.setUpdateData(data, newVersion);
								mUtils.saveVersion(newVersion);
							} else {
							}
						}
					}
				});
	}


	/*
	* 判断网络状态
	* */
	public boolean isWifi(Context context) {
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


	@Override
	public void initDialog(UpdateDialog updateDialog) {
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
			updateDialog.getUpdate().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
//					//更新
					splash_view.isUpdate();


				}
			});
			updateDialog
					.getPass()
					.setOnClickListener(new View.OnClickListener() {
						                    @Override
						                    public void onClick(View view) {
							                    //pass了
							                    splash_view.dismissDialog();
							                    splash_view.changePage();
						                    }
					                    }
					);
			updateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					if (splash_view.alertDialog()) return;
					splash_view.changePage();
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
//					//立即更新 进入更新界面
					splash_view.isForceUpdate();
				}
			});
		}
		updateDialog.show();
	}



	@Override
	public void unsubscribe() {

	}
}
