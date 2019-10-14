package com.yj.cosmetics.ui.activity.mineScoring;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineEntity;
import com.yj.cosmetics.ui.activity.MineScoringAccordActivity;
import com.yj.cosmetics.ui.activity.MineScoringPostActivity;
import com.yj.cosmetics.ui.activity.NormalWebViewActivity;
import com.yj.cosmetics.ui.adapter.MineOrderTabAdapter;
import com.yj.cosmetics.ui.fragment.MineScoringAccord.MineScoringAccordFrag;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2018/3/12.
 *
 * 我的积分 2.0版本修改页面
 */

public class MineScoring1Activity extends BaseActivity {

	@BindView(R.id.mine_scoring_tv_score)
	TextView tvScore;
	@BindView(R.id.title_layout)
	LinearLayout lyTitle;
	@BindView(R.id.title_rl_next)
	RelativeLayout reLayout;
	@BindView(R.id.title_ll_iv)
	ImageView ivTitleIcon;

	@BindView(R.id.mine_scoring_detial_tablayout)
	TabLayout tabLayout;
	@BindView(R.id.viewpager)
	ViewPager mViewpager;

	@BindView(R.id.title_tv_next)
	TextView tvRightTitle;

	private String score, upintegral, money;
	private List<String> mTitle = new ArrayList<String>();
	private List<Fragment> mFragment = new ArrayList<Fragment>();

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_scoring1;
	}

	@Override
	protected void initView() {
		setTitleText("我的积分");
		ivTitleIcon.setImageResource(R.drawable.ic_keyboard_arrow_left_white_24dp);
		setTitleColor(getResources().getColor(R.color.white));
		lyTitle.setBackgroundColor(getResources().getColor(R.color.CE8_3C_3C));
		reLayout.setVisibility(View.VISIBLE);
		tvRightTitle.setText("积分规则");
		tvRightTitle.setTextColor(getResources().getColor(R.color.white));
		transTitle();
		mTitle.add("全部积分");
		mTitle.add("积分收入");
		mTitle.add("积分支出");
		for (int i = 0; i < mTitle.size(); i++) {
			LogUtils.i("我添加了" + i);
			mFragment.add(MineScoringAccordFrag.instant(i + 1));
		}

		MineOrderTabAdapter adapter = new MineOrderTabAdapter(getSupportFragmentManager(), mTitle, mFragment);
		mViewpager.setAdapter(adapter);
//		为TabLayout设置ViewPager
		tabLayout.setupWithViewPager(mViewpager);
//		使用ViewPager的适配器
//		忘了这句干啥的了. 如果使用过程中有问题.应该就是这句导致的.
		tabLayout.setTabsFromPagerAdapter(adapter);
	}


	@Override
	protected void initData() {

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
	protected void onResume() {
		super.onResume();
		doAsyncGetData();
	}

	@OnClick({R.id.mine_scoring_tv_accord, /*R.id.mine_scoring_rl_detial,*/ R.id.mine_scoring_tv_confirm, /*R.id.mine_scoring_tv_rule,*/ R.id.title_rl_next})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.mine_scoring_tv_accord:
				Intent intent = new Intent(this, MineScoringAccordActivity.class);
				startActivity(intent);
				break;
//			case R.id.mine_scoring_rl_detial:
//				Intent intentDetial = new Intent(this, MineScoringDetialActivity.class);
//				startActivity(intentDetial);
//				break;
			case R.id.mine_scoring_tv_confirm:
				if (TextUtils.isEmpty(money)) {
					ToastUtils.showToast(this, "无法获取额度信息，请检查网络稍后再试");
					return;
				}
//				if (Float.parseFloat(score) < 500) {
//					ToastUtils.showToast(this, "积分少于500，无法提升额度");
//					return;
//				}

				Intent intentPost = new Intent(this, MineScoringPostActivity.class);
				intentPost.putExtra("money", money);
				intentPost.putExtra("upintegral", upintegral);
				startActivity(intentPost);
				break;
//			case R.id.mine_scoring_tv_rule:
//				Intent intentRule = new Intent(this, NormalWebViewActivity.class);
//				intentRule.putExtra("title", "积分规则");
//				intentRule.putExtra("url", URLBuilder.URLBaseHeader + URLBuilder.ScoreRule);
//				startActivity(intentRule);
//				break;
			case R.id.title_rl_next:
				Intent intentRule = new Intent(this, NormalWebViewActivity.class);
				intentRule.putExtra("title", "积分规则");
				intentRule.putExtra("url", URLBuilder.URLBaseHeader + URLBuilder.ScoreRule);
				startActivity(intentRule);

//				setRuleDialog();
				break;
		}
	}


	private void setRuleDialog() {

		View pview = LayoutInflater.from(this).inflate(R.layout.dialog_integral_info, null);
		LinearLayout llCancel = (LinearLayout) pview.findViewById(R.id.integral_ll_cancel);
		TextView tvContent = (TextView) pview.findViewById(R.id.integral_tv_content);
		TextView tvYes = (TextView) pview.findViewById(R.id.integral_tv_yes);
//		tvContent.setText(R.string.integral_content);
		final CustomDialog builder = new CustomDialog(this, R.style.my_dialog).create(pview, false, 0.85f, 0.65f, 1.4f);

		builder.show();
		llCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				builder.dismiss();
			}
		});

		tvYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				builder.dismiss();

			}
		});
	}


	private void doAsyncGetData() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/zoneOrder").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<MineEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}

			@Override
			public MineEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, MineEntity.class);
			}

			@Override
			public void onResponse(MineEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					setData(response.getData());
				} else {
				}
			}
		});
	}

	private void setData(MineEntity.MineData data) {
		score = data.getUserScore();
		money = data.getUserMoney();
		upintegral = data.getUpintegral();
		tvScore.setText(data.getUserScore());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
