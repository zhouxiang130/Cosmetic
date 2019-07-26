package com.yj.cosmetics.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.util.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/9/10 0010.
 */

public class UpdateManager {


	private String url = null;
	private Context context = null;
	private String target, appName;
	File downloadfile;
	private ProgressBar mProgress;
	private AlertDialog downloadDialog;
	private TextView mpercent;
	private int downPercent;

	public UpdateManager(Context context, String url) {
		this.context = context;
		this.url = url;
		appName = url.substring(url.lastIndexOf("/") + 1);
	}


	public void task() {
		//判断文件是否存在
		if (initAPKDir()) {
			//文件存在
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					LogUtils.i("我开始从本地升级了");
					Intent install = new Intent(Intent.ACTION_VIEW);
					install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					install.addCategory(Intent.CATEGORY_DEFAULT);
					install.setDataAndType(Uri.fromFile(downloadfile), "application/vnd.android.package-archive");
					context.startActivity(install);
				}
			}, 1000);
		} else {
			LogUtils.i("我开始升级了");
			if (appName != null) {
				if (appName.endsWith(".apk")) {
					appName = appName.replace(".apk", ".temp");
					LogUtils.i("appName的值" + appName);
				}
			} else {

			}

			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.force_update_progress, null);
			mProgress = (ProgressBar) v.findViewById(R.id.progress);
			mpercent = (TextView) v.findViewById(R.id.text_percent);
			downloadDialog = new AlertDialog.Builder(context)
					.setTitle("版本更新")
					.setView(v)
					.setCancelable(false)
					.show();
			if (url != null && target != null) {
				downLoad(url);
			}

		}

	}

	private void downLoad(String url) {
		LogUtils.i("我开始下载APP了");
		OkHttpUtils.get().url(url)
				.tag(this)
				.build()
				.connTimeOut(30000)
				.readTimeOut(30000)
				.writeTimeOut(30000)
				.execute(new FileCallBack(target, appName) {
					@Override
					public void inProgress(float v, long l) {
						//Math.round(v*100)
						downPercent = Math.round(v * 100);
						mpercent.setText("正在下载:" + downPercent + "%"); //消息内容
						mProgress.setProgress(downPercent);

					}


					@Override
					public void onError(Call call, Exception e) {

						if (call.isCanceled()) {
							LogUtils.i("我在Error中 Cancel ....." + e);
							call.cancel();

						} else {
							LogUtils.i("我stopSelf了" + e);
						}
					}

					@Override
					public void onResponse(File file) {

						LogUtils.i("我在onResponse了" + file);
						if (appName.endsWith(".temp")) {
							appName = appName.replace(".temp", ".apk");
							LogUtils.i("response中 AppName的值" + appName);
						}

						File newFile = new File(target + appName);
						file.renameTo(newFile);
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.addCategory(Intent.CATEGORY_DEFAULT);
						intent.setDataAndType(Uri.fromFile(newFile), "application/vnd.android.package-archive");
						//在BroadcastReceiver和Service中startActivity要加上此设置
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mpercent.setText("下载完成.");
						cancelDownload();
//						builder1.setContentIntent(mPendingIntent);
//						notification = builder1.build();
//						notificationManager.notify(124, notification);
						// 震动提示
						Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
						vibrator.vibrate(1000L);// 参数是震动时间(long类型)
						context.startActivity(intent);// 下载完成之后自动弹出安装界面

					}
				});
	}


	private boolean initAPKDir() {
		/**
		 * 创建路径的时候一定要用[/],不能使用[\],但是创建文件夹加文件的时候可以使用[\].
		 * [/]符号是Linux系统路径分隔符,而[\]是windows系统路径分隔符 Android内核是Linux.
		 */

		if (isHasSdcard())// 判断是否插入SD卡
		{
			target = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wendi/download/";// 保存到SD卡路径下
		} else {
			target = context.getFilesDir().getAbsolutePath() + "/wendi/download/";// 保存到app的包名路径下
		}
		File destDir = new File(target);
		if (!destDir.exists()) {// 判断文件夹是否存在
			destDir.mkdirs();
		}
		downloadfile = new File(target + appName);
		if (!downloadfile.exists()) {
			//文件不存在,创建
			LogUtils.i("我在文件不存在中");
			return false;
		} else {
			//文件存在 返回true
			LogUtils.i("我在文件存在中");
			return true;
		}
	}


	private void cancelDownload() {
		if (downloadDialog != null)
			downloadDialog.dismiss();
	}

	/**
	 * @Description:判断是否插入SD卡
	 */
	private boolean isHasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

}
