package com.yj.cosmetics.base;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.cosmetics.MyApplication;
import com.yj.cosmetics.R;
import com.yj.cosmetics.util.KeyBoardUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.SharedPreferencesUtil;
import com.yj.cosmetics.util.UserUtils;

import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/11.
 */

public abstract class BaseActivity extends AppCompatActivity {
	TextView tv;
	TextView tvNext;
	LinearLayout back;
	View line;
	LinearLayout title;
	public SharedPreferencesUtil preferencesUtil;
	private ImageView llIv;

	protected abstract int getContentView();

	protected abstract void initView();

	public UserUtils mUtils;

	protected abstract void initData();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentView());
		ButterKnife.bind(this);
		mUtils = UserUtils.getInstance(this);
		preferencesUtil = new SharedPreferencesUtil(this);
		initTitleBar();
		initView();
		initData();
		pushAtyToStack();
	}

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
		LogUtils.i("outState的值..." + outState + "......outPersistenState的值" + outPersistentState);
//        super.onSaveInstanceState(outState, outPersistentState);
	}

	public void initTitleBar() {
		tv = (TextView) findViewById(R.id.title_tv);
		if (tv != null) {
			back = (LinearLayout) findViewById(R.id.title_ll_back);
			llIv = (ImageView) findViewById(R.id.title_ll_iv);
			line = findViewById(R.id.title_view);
			title = (LinearLayout) findViewById(R.id.title_layout);
			tvNext = (TextView) findViewById(R.id.title_tv_next);
			if (back != null) {
				back.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						onBackClick(back);
						KeyBoardUtils.hintKb(BaseActivity.this);
					}
				});
			}
			if (title != null) {
				showShadow1();
			}
		}
	}

	@TargetApi(21)
	public void hideTitle() {
		if (Build.VERSION.SDK_INT >= 21) {
			View decorView = getWindow().getDecorView();
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			decorView.setSystemUiVisibility(option);
			getWindow().setStatusBarColor(Color.TRANSPARENT);
		}
	}

	/**
	 * @param background
	 */
	public void setTitleBackground(int background) {
		title = (LinearLayout) findViewById(R.id.title_layout);
		if (title != null) {
			title.setBackgroundColor(background);
		}
	}

	public void setTitleText(String text) {
		if (tv != null) {
			tv.setText(text);
		}
	}

	public void setTitleColor(int color) {
		if (tv != null) {
			tv.setTextColor(color);
		}
	}

	public void setTitleLeftImg(Drawable background) {
		if (llIv != null) {
			llIv.setBackground(background);
		}
	}

	public void setNextText(String text) {
		if (tvNext != null) {
			tvNext.setText(text);
		}
	}

	@TargetApi(21)
	protected void showShadow1() {
		if (Build.VERSION.SDK_INT >= 21) {
			line.setVisibility(View.GONE);
			title.setElevation(getResources().getDimension(R.dimen.dis2));
			title.setOutlineProvider(ViewOutlineProvider.BOUNDS);
		}
	}

	@Override
	public void onBackPressed() {
		onBackClick(null);
		super.onBackPressed();
		LogUtils.i("我onbackPressed了");

	}

	protected void onBackClick(Object o) {
		finish();
	}

	private void pushAtyToStack() {
		MyApplication.push(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.pop(this);
	}
}

