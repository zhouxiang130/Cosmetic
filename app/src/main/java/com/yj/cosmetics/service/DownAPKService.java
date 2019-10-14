package com.yj.cosmetics.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.yj.cosmetics.R;
import com.yj.cosmetics.util.FileUtils;
import com.yj.cosmetics.util.InstallUtil;
import com.yj.cosmetics.util.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;


/**
 * @author LEI-LEI
 * @version 1.0
 *          2016-7-6 下午4:54:16
 * @Title:DownAPKService.java
 * @Description:专用下载APK文件Service工具类,通知栏显示进度,下载完成震动提示,并自动打开安装界面(配合xUtils快速开发框架) 需要添加权限：
 * <uses-permission android:name="android.permission.INTERNET" />
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.VIBRATE" />
 * <p>
 * 需要在<application></application>标签下注册服务
 * <p>
 * 可以在142行代码：builder.setSmallIcon(R.drawable.ic_launcher);中修改自己应用的图标
 */

public class DownAPKService extends Service {

	private static final String TAG = "DownAPKService";
	String appName, updateType, target, url;
	File downloadfile;
	private boolean change = false;
	private int percent = 0;

	//通知栏
	private Notification notification = null;
	private NotificationManager notificationManager = null;
	Notification.Builder builder1;
	private int downPercent;
	private File destDir;
	private String appLen;
	public String CHANNEL_ID = "CHANNEL_ID";
	private NotificationCompat.Builder mBuilder = null;
	private Notification build;


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@SuppressLint("NewApi")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    /*if(intent == null){
	        stopSelf();
        }
        url=intent.getStringExtra("url");*/
		try {
//            url = UserUtils.getInstance(getApplicationContext()).getLink();
			if (intent == null) {
				stopSelf();
			}
			url = intent.getStringExtra("url");
			updateType = intent.getStringExtra("updateType");
			appLen = intent.getStringExtra("applen");//@ 后台文件大小
			appName = url.substring(url.lastIndexOf("/") + 1);
//			appName ="wendi_new.apk";
			LogUtils.i("url的值" + url);
			LogUtils.i("appName的值" + appName);
		} catch (Exception e) {
			LogUtils.i("我进入到intent Exception了");
			stopSelf();
		}

		//@ 判断文件是否存在,此处还要判断文件夹是否创建，apk包大小和服务器包是否相等
		if (initAPKDir()) {
			if (Integer.parseInt(appLen) == getApkSize()) {//比对服务器文件大小和本地文件大小
				//文件存在（文件为完整apk安装包）
				new Handler().postDelayed(
						new Runnable() {
							@Override
							public void run() {
								LogUtils.i("我开始从本地升级了");
								getApplicationContext().startActivity(InstallUtil.startInstallAct(getApplicationContext(), downloadfile));
								stopSelf();
							}
						}, 1000);
			} else {
				FileUtils.deleteFile(downloadfile);
				downloadfile = new File(destDir, appName);
				setNotificationManager();
			}
		} else {
			setNotificationManager();
		}
		return super.onStartCommand(intent, flags, startId);
	}


	/**
	 * 获取本地文件apk大小
	 *
	 * @return
	 */
	public long getApkSize() {
		try {
			long fileSize = FileUtils.getFileSize(downloadfile);
			String fileSizes = FileUtils.FormetFileSize(fileSize);
			Log.i(TAG, "onClick: " + " fileSizes : " + fileSizes + " fileSize: " + fileSize);
			return fileSize;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}


	/**
	 * 开启通知栏，显示下载进度，执行下载
	 */

	@RequiresApi(api = 26)
	private void setNotificationManager() {
		LogUtils.i("我开始升级了");
		//文件不存在,此处需要处理强制更新和不强制更新的问题
		/**
		 * @ 1. 强制更新 顶部通知条取消掉，改为 本页面下载进度条
		 *   @ 2.  非强制更新    显示顶部通知条
		 */
		createNotificationChannel(false);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
			mBuilder.setContentTitle("菲梵仙子");
			mBuilder.setContentText("正在下载");
			mBuilder.setSmallIcon(R.mipmap.logo);
			mBuilder.setPriority(NotificationCompat.PRIORITY_LOW);
			mBuilder.setCategory(Notification.CATEGORY_PROGRESS);
//					.setProgress(PROGRESS_MAX, progress, false);
			mBuilder.setVibrate(new long[]{0});
			mBuilder.setOnlyAlertOnce(true);//每次只会提醒一次声音
			notificationManager.notify(4, mBuilder.build());
		} else {
			builder1 = new Notification.Builder(this);
			builder1.setSmallIcon(R.mipmap.logo); //设置图标
			builder1.setTicker("菲梵仙子开始下载");
			builder1.setContentTitle("菲梵仙子"); //设置标题
			builder1.setContentText("正在下载"); //消息内容
			builder1.setWhen(System.currentTimeMillis()); //发送时间
			//builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
			builder1.setAutoCancel(true);//打开程序后图标消失
			builder1.setOnlyAlertOnce(true);//每次只会提醒一次声音

//            设置下载过程中，点击通知栏，回到主界面
//            updateIntent = new Intent(this, MainActivity.class);
//            pendingIntent =PendingIntent.getActivity(this, 0, updateIntent, 0);
//            builder1.setContentIntent(pendingIntent);

			notification = builder1.build();
			// 通过通知管理器发送通知֪
			notificationManager.notify(124, notification);

		}

		if (url != null && target != null) {
			downLoad(url);
		}
	}


	@TargetApi(Build.VERSION_CODES.O)
	private void createNotificationChannel(boolean isVibrate) {
		// Create the NotificationChannel, but only on API 26+ because
		// the NotificationChannel class is new and not in the support library
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = getString(R.string.app_name);
			String description = getString(R.string.app_name);
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
			if (isVibrate) {
				// 设置通知出现时的震动（如果 android 设备支持的话）
				channel.enableVibration(true);
				channel.setVibrationPattern(new long[]{1000, 500, 2000});
			} else {
				// 设置通知出现时不震动
				channel.enableVibration(false);
				channel.setVibrationPattern(new long[]{0});

			}

			channel.setDescription(description);
			// Register the channel with the system; you can't change the importance
			// or other notification behaviors after this
			notificationManager.createNotificationChannel(channel);
		}
	}

	private boolean initAPKDir() {
		/**
		 * 创建路径的时候一定要用[/],不能使用[\],但是创建文件夹加文件的时候可以使用[\].
		 * [/]符号是Linux系统路径分隔符,而[\]是windows系统路径分隔符 Android内核是Linux.
		 */
//		if (isHasSdcard()){// 判断是否插入SD卡
//			target = Environment.getExternalStorageDirectory().getAbsolutePath() + "";// 保存到SD卡路径下
//		} else {
		target = getApplicationContext().getExternalFilesDir("downLoad").getPath();// 保存到app的包名路径下
//		target = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
//		target = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wendi/download/";// 保存到app的包名路径下
//		}
		destDir = new File(target);
		if (!destDir.exists()) {// 判断文件夹是否存在
			destDir.mkdirs();
		}
		downloadfile = new File(destDir, appName);
		Log.i(TAG, "initAPKDir:>>>>>>>>>>>>>> " + downloadfile.getPath());
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


	private void downLoad(String url) {

		OkHttpUtils.get().
				url(url)
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
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
									mBuilder.setContentText("正在下载:" + downPercent + "%");
									mBuilder.setVibrate(new long[]{0});
								} else {
									builder1.setContentText("正在下载:" + downPercent + "%"); //消息内容
								}
								if (downPercent % 5 == 0) {
									//如果没有更新界面 进行更新
									if (percent != downPercent) {
										change = false;
										percent = downPercent;
									}
									if (!change) {
										LogUtils.i("我在更新通知栏" + percent);
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
											mBuilder.setProgress(100, downPercent, false);
											mBuilder.setVibrate(new long[]{0});
										} else {
											builder1.setProgress(100, downPercent, false);
										}
								/*if (downPercent >= 100) {
								    builder1.setContentText("下载完成");
			                        builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
			                    }*/
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
											build = mBuilder.build();
											notificationManager.notify(4, build);
											mBuilder.setVibrate(new long[]{0});
										} else {
											notification = builder1.build();
											notificationManager.notify(124, notification); //通过通知管理器发送通知
										}
										change = true;
									}
								}
							}

							@Override
							public void onError(Call call, Exception e) {

								if (call.isCanceled()) {
									LogUtils.i("我在Error中 Cancel ....." + e);
									call.cancel();
									if (notificationManager != null) {
										LogUtils.i("我进入到退出通知栏了");
										notificationManager.cancel(124);
									}
									if (build != null) {
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
											notificationManager.cancel(4);
											mBuilder.setVibrate(new long[]{0});

										}
									}
									// 结束service
								} else {
									LogUtils.i("我stopSelf了" + e);
									stopSelf();
								}
							}

							@Override
							public void onResponse(File file) {

//						if (appName.endsWith(".temp")) {
//							appName = appName.replace(".temp", ".apk");
								LogUtils.i("response中 AppName的值>>>>>" + appName);
//						}

								File newFile = new File(destDir, appName);
								file.renameTo(newFile);
								//@下载完成之后，通过Intent 开启安装界面；
								//提升读写权限  防止解析apk失败
								String command = "chmod " + "777" + " " + newFile;
								Runtime runtime = Runtime.getRuntime();
								try {
									runtime.exec(command);
								} catch (IOException e) {
									e.printStackTrace();
								}

								Intent intent = InstallUtil.startInstallAct(getApplicationContext(), downloadfile);
								PendingIntent mPendingIntent = PendingIntent.getActivity(DownAPKService.this, 0, intent, 0);
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

									mBuilder.setContentText("下载完成,请点击安装");
									mBuilder.setContentIntent(mPendingIntent);
									build = mBuilder.build();
									notificationManager.notify(4, build);
									if (notificationManager != null) {
										notificationManager.cancel(4);
										mBuilder.setVibrate(new long[]{0});
									}
								} else {
									builder1.setContentText("下载完成,请点击安装");
									builder1.setContentIntent(mPendingIntent);
									notification = builder1.build();
									notificationManager.notify(124, notification);
									if (notificationManager != null) {
										notificationManager.cancel(124);
									}
								}


								// 震动提示
								Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
								vibrator.vibrate(1000L);// 参数是震动时间(long类型)
								stopSelf();
								startActivity(intent);// 下载完成之后自动弹出安装界面
							}
						});
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


	/**
	 * @param x     当前值
	 * @param total 总值
	 *              [url=home.php?mod=space&uid=7300]@return[/url] 当前百分比
	 * @Description:返回百分之值
	 */
	private String getPercent(int x, int total) {
		String result = "";// 接受百分比的值
		double x_double = x * 1.0;
		double tempresult = x_double / total;
		// 百分比格式，后面不足2位的用0补齐 ##.00%
		DecimalFormat df1 = new DecimalFormat("0.00%");
		result = df1.format(tempresult);
		return result;
	}

	/**
	 * @return
	 * @Description:获取当前应用的名称
	 */
	private String getApplicationName() {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = getApplicationContext().getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
		return applicationName;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		LogUtils.i("我在onUnbind中");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		LogUtils.i("我在onDestory了");
		super.onDestroy();
		OkHttpUtils.getInstance().cancelTag(this);
		if (notificationManager != null) {
			LogUtils.i("我进入到退出通知栏了");
			notificationManager.cancel(124);
		}
	}
}
