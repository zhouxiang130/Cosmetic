package com.yj.cosmetics.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.yj.cosmetics.MyApplication;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.ui.fragment.CartFrag;
import com.yj.cosmetics.ui.fragment.ClassifyFrag;
import com.yj.cosmetics.ui.fragment.HomeFrag;
import com.yj.cosmetics.ui.fragment.MineFrags.MineFrag1;
import com.yj.cosmetics.ui.fragment.StoreFrag;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.PermissionUtils;
import com.yj.cosmetics.util.ToastUtils;

import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.yj.cosmetics.util.PermissionUtils.READ_EXTERNAL_STORAGE;
import static com.yj.cosmetics.util.PermissionUtils.REQUEST_CODE_READ_PHONE_STATE;
import static com.yj.cosmetics.util.PermissionUtils.REQUEST_CODE_WRITE_EXTRONAL_STORAGE;


public class MainActivity extends BaseActivity {
	@BindView(R.id.main_tv_homepage)
	TextView tvHomepage;
	@BindView(R.id.main_tv_classify)
	TextView tvClassify;
	@BindView(R.id.main_tv_cart)
	TextView tvCart;
	@BindView(R.id.main_tv_mine)
	TextView tvMine;
	@BindView(R.id.main_tv_store)
	TextView tvStore;

	private TextView[] mTextView;
	private FragmentManager mManager;
	private Fragment[] mFragments;
	private Fragment mHomepage, mClassify, mStore, mCart, mMine1 /*mMine,*/;
	public Fragment mCurrent;
	public static int mPage = 0;
	private boolean isExit = false;
	private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒


	@Override
	protected int getContentView() {
		return R.layout.activity_main;
	}

	@Override
	protected void initView() {
		mTextView = new TextView[]{tvHomepage, tvClassify, tvStore, tvCart, tvMine};
		mHomepage = new HomeFrag();
		mClassify = new ClassifyFrag();
		mCart = new CartFrag();
//		mCart = new CartFrags();
//		mMine = new MineFrag();
		mStore = new StoreFrag();
		mMine1 = new MineFrag1();
//		mMine1 = new MineFrags();
		tvHomepage.setSelected(true);
		LogUtils.i("initView了");
		initFragment();
		transTitle();

	}

	@TargetApi(21)
	private void transTitle() {
		if (Build.VERSION.SDK_INT >= 21) {
			View decorView = getWindow().getDecorView();
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			decorView.setSystemUiVisibility(option);
			getWindow().setStatusBarColor(Color.TRANSPARENT);
		}
	}

	@Override
	protected void initData() {
		LogUtils.i("initData了");
		//判断权限
	}

	private void initFragment() {
		mFragments = new Fragment[]{mHomepage, mClassify, mStore, mCart, mMine1};
		mManager = getSupportFragmentManager();
		List<Fragment> fragments = mManager.getFragments();
		if (fragments != null) {
			for (Fragment fragment : fragments) {
				mManager.beginTransaction().remove(fragment).commit();
			}
		}
		mManager.beginTransaction()
				.add(R.id.main_fm_container, mHomepage)
				.addToBackStack(null)
				.add(R.id.main_fm_container, mClassify)
				.addToBackStack(null)
				.add(R.id.main_fm_container, mStore)
				.addToBackStack(null)
				.add(R.id.main_fm_container, mCart)
				.addToBackStack(null)
				.add(R.id.main_fm_container, mMine1)
				.addToBackStack(null)
				.show(mHomepage)
				.commit();
		mManager.beginTransaction().hide(mClassify).hide(mStore
		).hide(mCart).hide(mMine1).commitAllowingStateLoss();
		mCurrent = mHomepage;
		if (mPage != 0) {
			selected(mPage);
		}
		LogUtils.i("initFragment了");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		LogUtils.i("我onNewIntent了=======" + intent.getStringExtra("page"));
		if (!TextUtils.isEmpty(intent.getStringExtra("page"))) {
			String page = intent.getStringExtra("page");
			selected(Integer.parseInt(page));
		}
	}

	@OnClick({R.id.main_tv_homepage, R.id.main_tv_classify, R.id.main_tv_store, R.id.main_tv_cart, R.id.main_tv_mine})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.main_tv_homepage:
				if (mCurrent == mHomepage) {
					return;
				} else {
					mCurrent = mHomepage;
					selected(0);
				}
				break;
			case R.id.main_tv_classify:
				if (mCurrent == mClassify) {
					return;
				} else {
					mCurrent = mClassify;
					selected(1);
				}
				break;
			case R.id.main_tv_store:

				if (mCurrent == mStore) {
					return;
				} else {
					mCurrent = mStore;
					selected(2);
				}

				break;
			case R.id.main_tv_cart:
				if (mCurrent == mCart) {
					return;
				} else {
					mCurrent = mCart;
					selected(3);
				}
				break;
			case R.id.main_tv_mine:
				if (mCurrent == mMine1) {
					return;
				} else {
					mCurrent = mMine1;
					selected(4);
				}
				break;
		}
	}

	public void selected(int position) {
		for (int i = 0; i < mTextView.length; i++) {
			if (position == i) {
				mCurrent = mFragments[i];
				mManager.beginTransaction().show(mFragments[i]).commitAllowingStateLoss();
				mFragments[i].onResume();
			} else {
				mTextView[i].setSelected(false);
				mManager.beginTransaction().hide(mFragments[i]).commitAllowingStateLoss();
			}
		}
		mTextView[position].setSelected(true);
	}

	private void exitBy2Click() {
		Timer tExit;
		if (!isExit) {
			isExit = true;
//            ToastUtils.custom("再按一次退出程序!");
			ToastUtils.showToast(this, "再按一次退出菲梵仙子!");
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false;
				}
			}, 2000);
		} else {
			preferencesUtil.setBooleanValue("is_app_close", false);
			MyApplication.exit();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
		}
		return false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//防止应用崩溃后无法加载界面 将此方法注掉
//        super.onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}


	@Override
	public void onAttachFragment(android.app.Fragment fragment) {
//        LogUtils.i("onAttachFragment了");
		super.onAttachFragment(fragment);
	}

	@Override
	public void onAttachedToWindow() {
//        LogUtils.i("onAttachTOWindow了");
		super.onAttachedToWindow();
	}

	@Override
	protected void onResumeFragments() {
//        LogUtils.i("onResumFragments了");
		super.onResumeFragments();
	}

	@Override
	protected void onStart() {
//        LogUtils.i("onStart了");
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (preferencesUtil.getValuesBoolean("is_register_jpush_alias")) {
			setJpushAlias();
		}
	}

	private void setJpushAlias() {

		if (mUtils.getUid() != null) {
//			JPushInterface.setAlias(this,0,mUtils.getUid());
			JPushInterface.setAliasAndTags(this, mUtils.getUid(), null, new TagAliasCallback() {
				@Override
				public void gotResult(int code, String alias, Set<String> tags) {
					Log.d("alias", "set tag result is" + code);
					String logs;
					switch (code) {
						case 0:
							//这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
							//UserUtils.saveTagAlias(getHoldingActivity(), true);
							logs = "Set tag and alias success极光推送别名设置成功";
							LogUtils.i(logs);
							preferencesUtil.setBooleanValue("is_register_jpush_alias", false);
							break;
						case 6002:
							//极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
							logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
							LogUtils.i(logs);
							break;
						default:
							logs = "极光推送设置失败，Failed with errorCode = " + code;
							LogUtils.i(logs);
							break;
					}
				}
			});
		}
	}


	private void isWritePermissionAllowed() {
		if (PermissionUtils.isPermissionAllowed(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			//为true 表示有权限
		} else {
			//没有相关权限
			if (PermissionUtils.isRemindAllowed(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_WRITE_EXTRONAL_STORAGE)) {
				//是否能弹窗提示
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTRONAL_STORAGE);
			} else {
				//弹窗提示已被关闭
//                showDialog("检测到您未开启读写功能,这将对您使用本应用带来极大的不便,我们强烈建议您开启此功能,点击确认手动更改");
                /*PermissionUtils.getAppDetailSettingIntent(MainActivity.this);
                ToastUtils.showToast(this,"您已关闭读写功能,相关功能将不可用.");*/
			}
		}
	}

	private void isReadPermissionAllowed() {
		if (PermissionUtils.isPermissionAllowed(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			//为true 表示有权限
		} else {
			//没有相关权限
			if (PermissionUtils.isRemindAllowed(this, Manifest.permission.READ_EXTERNAL_STORAGE, PermissionUtils.READ_EXTERNAL_STORAGE)) {
				//是否能弹窗提示
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionUtils.READ_EXTERNAL_STORAGE);
			} else {
				//弹窗提示已被关闭
//                showDialog("为保证您的正常使用,请允许相应权限");
               /* PermissionUtils.getAppDetailSettingIntent(MainActivity.this);
                ToastUtils.showToast(this,"您已关闭读写功能,相关功能将不可用.");*/
			}
		}
	}

	private void isPhoneStatePermissionAllowed() {
		if (PermissionUtils.isPermissionAllowed(this, Manifest.permission.READ_PHONE_STATE)) {
			//为true 表示有权限
		} else {
			//没有相关权限
			if (PermissionUtils.isRemindAllowed(this, Manifest.permission.READ_PHONE_STATE, REQUEST_CODE_READ_PHONE_STATE)) {
				//是否能弹窗提示
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_READ_PHONE_STATE);
			} else {
				//弹窗提示已被关闭
//                showDialog("为保证您的正常使用,请允许相应权限");
               /* PermissionUtils.getAppDetailSettingIntent(MainActivity.this);
                ToastUtils.showToast(this,"您已关闭读取手机硬件信息功能,相关功能将不可用.");*/
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
				} else {
					LogUtils.i("我在不同意授权");
//                    showDialog("检测到您未开启读写功能,这将对您使用本应用带来极大的不便,我们强烈建议您开启此功能,点击确认手动更改");
                   /* PermissionUtils.getAppDetailSettingIntent(MainActivity.this);
                    ToastUtils.showToast(this,"您已关闭读取手机硬件信息功能,相关功能将不可用.");*/
				}
				break;
			case READ_EXTERNAL_STORAGE:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					//用户同意授权
					LogUtils.i("我进入到同意授权了");
				} else {
					LogUtils.i("我在不同意授权");
//                    showDialog("检测到您未开启读写功能,这将对您使用本应用带来极大的不便,我们强烈建议您开启此功能,点击确认手动更改");
                   /* PermissionUtils.getAppDetailSettingIntent(MainActivity.this);
                    ToastUtils.showToast(this,"您已关闭读取手机硬件信息功能,相关功能将不可用.");*/
				}
				break;
			case REQUEST_CODE_READ_PHONE_STATE:
				if (PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
						== PermissionChecker.PERMISSION_GRANTED) {
					//用户同意授权
					LogUtils.i("我进入到同意授权了");
				} else {
					LogUtils.i("我进入到拒绝授权了");
					/*PermissionUtils.getAppDetailSettingIntent(MainActivity.this);
					ToastUtils.showToast(this,"您已关闭读取手机硬件信息功能,相关功能将不可用.");*/
//                    showDialog("检测到您未开启获取手机硬件信息功能,这将对您使用本应用带来极大的不便,我们强烈建议您开启此功能,点击确认手动更改");
				}
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
