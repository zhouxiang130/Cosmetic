package com.yj.cosmetics.ui.activity.mineSignIn;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.model.SignInEntity;
import com.yj.cosmetics.ui.activity.mineAccount.MineAccount2Activity;
import com.yj.cosmetics.ui.activity.mineCoupon.MineCouponActivity;
import com.yj.cosmetics.ui.activity.mineScoring.MineScoring1Activity;
import com.yj.cosmetics.ui.adapter.MineSignInAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomDialog;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.yj.cosmetics.widget.MyLayoutManager;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/5 0005.
 *
 * 签到界面 2.0新增加的页面
 */

public class MineSignInActivity extends BaseActivity implements MineSign_contract.View {


	private static final String TAG = "MineSignInActivity";
	@BindView(R.id.mine_sign_ll_rule)
	LinearLayout mineSignLlRule;
	@BindView(R.id.title_ll_iv)
	ImageView ivTitleIcon;

	@BindView(R.id.mine_sign_text_dayNum)
	TextView mineSignTextDayNum;

	@BindView(R.id.mine_sign_cb_day1)
	CheckBox mineSignCbDay1;
	@BindView(R.id.mine_sign_text_day1)
	TextView mineSignTextDay1;
	@BindView(R.id.mine_sign_text_sign)
	TextView mineSignTextSign;

	@BindView(R.id.mine_sign_cb_day2)
	CheckBox mineSignCbDay2;
	@BindView(R.id.mine_sign_text_day2)
	TextView mineSignTextDay2;

	@BindView(R.id.mine_sign_cb_day3)
	CheckBox mineSignCbDay3;
	@BindView(R.id.mine_sign_text_day3)
	TextView mineSignTextDay3;

	@BindView(R.id.mine_sign_cb_day4)
	CheckBox mineSignCbDay4;
	@BindView(R.id.mine_sign_text_day4)
	TextView mineSignTextDay4;

	@BindView(R.id.mine_sign_cb_day5)
	CheckBox mineSignCbDay5;
	@BindView(R.id.mine_sign_text_day5)
	TextView mineSignTextDay5;

	@BindView(R.id.mine_sign_cb_day6)
	CheckBox mineSignCbDay6;
	@BindView(R.id.mine_sign_text_day6)
	TextView mineSignTextDay6;

	@BindView(R.id.mine_sign_cb_day7)
	CheckBox mineSignCbDay7;
	@BindView(R.id.mine_sign_text_day7)
	TextView mineSignTextDay7;

	@BindView(R.id.mRecyclerView)
	RecyclerView mRecyclerView;

	private CheckBox[] checkBoxesa = null;

	private TextView[] TextViews = null;

	private MineSign_contract.Presenter presenter = new MineSign_Presenter(this);
	private MineSignInAdapter mAdapter;
	private List<SignInEntity.DataBean.SignListBean> mData;

	private CustomProgressDialog mDialog;
	private int days;
	private String signRule;
	private int position;

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_sign;
	}


	@Override
	protected void initView() {
		setTitleText("签到领福利");
		ivTitleIcon.setImageResource(R.drawable.ic_keyboard_arrow_left_white_24dp);
		setTitleColor(getResources().getColor(R.color.white));
		setTitleBackground(getResources().getColor(R.color.transparent));
		transTitle();
		mData = new ArrayList<>();
		checkBoxesa = new CheckBox[]{
				mineSignCbDay1,
				mineSignCbDay2,
				mineSignCbDay3,
				mineSignCbDay4,
				mineSignCbDay5,
				mineSignCbDay6,
				mineSignCbDay7,

		};
		TextViews = new TextView[]{
				mineSignTextDay1,
				mineSignTextDay2,
				mineSignTextDay3,
				mineSignTextDay4,
				mineSignTextDay5,
				mineSignTextDay6,
				mineSignTextDay7,

		};
		MyLayoutManager myLayoutManager = new MyLayoutManager(this);
		myLayoutManager.setScrollEnabled(false);
		myLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(myLayoutManager);
		mAdapter = new MineSignInAdapter(this, mData);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(new MineSignInAdapter.ProfitDetialClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
				if (mData.get(postion).getIsSign().equals("1")) {
					Intent intent = null;
					switch (mData.get(postion).getType()) {//1折扣券 2代金券3：兑换券 4：积分券5： 金额券
						case "1":
						case "2":
						case "3":
							intent = new Intent(MineSignInActivity.this, MineCouponActivity.class);
							break;
						case "4":
							intent = new Intent(MineSignInActivity.this, MineScoring1Activity.class);
							break;
						case "5":
							intent = new Intent(MineSignInActivity.this, MineAccount2Activity.class);
							break;
					}
					startActivity(intent);
				} else {
					return;
				}
			}
		});
	}

	@Override
	protected void initData() {
		doAsyncGetSignIn(mUtils.getUid());
	}

	@Override
	protected void onResume() {
		super.onResume();


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


	@OnClick({R.id.mine_sign_ll_rule, R.id.mine_sign_text_sign, /*R.id.mine_sign_ll_ticket_class_go*/})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.mine_sign_ll_rule:
				setRuleDialog();
				break;
			case R.id.mine_sign_text_sign:
				if (days != 0) {
					position++;
				}
				days++;
				setCheckTexts();
				mineSignTextDayNum.setText(days + "");
				mineSignTextSign.setText("已签到");
				mAdapter.setData(position);
				mineSignTextSign.setClickable(false);
				doAsyncGetSignIns(mUtils.getUid());
				break;
		}
	}

	public void setPosition(int position) {
		this.position = position;
	}


	public void doAsyncGetSignIn(String uid) {
		final Map<String, String> map = new HashMap<>();
		map.put("userId", uid);
//		map.put("userId", "18");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/searchUserSign").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<SignInEntity>() {


			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {

				}
				dismissDialog();
			}

			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(MineSignInActivity.this);
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
			public SignInEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetSignIn json的值" + json);
				return new Gson().fromJson(json, SignInEntity.class);
			}

			@Override
			public void onResponse(SignInEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
//					days = response.getData().getSignDay().getDays();
					Log.i(TAG, "onResponse: " + response.getMsg());
					Log.e(TAG, "onResponse: " +response.getData().getSignList().size());
					mData.addAll(response.getData().getSignList());
					mAdapter.notifyDataSetChanged();
					setData(response.getData());
				} else {

				}
				dismissDialog();
			}
		});
	}


	private void setData(SignInEntity.DataBean data) {

		days = Integer.parseInt(data.getSignDay().getDays());
		signRule = data.getSignDay().getSignRule();
		setCheckTexts();

		mineSignTextDayNum.setText(data.getSignDay().getDays());
		if (data.getSignDay().getIsSign().equals("1")) {
			mineSignTextSign.setText("已签到");
			mineSignTextSign.setClickable(false);
			preferencesUtil.setValue("isTag", "true");
		} else {
			mineSignTextSign.setText("立即签到");
			mineSignTextSign.setClickable(true);
			preferencesUtil.setValue("isTag", "false");
		}
	}


	private void setCheckTexts() {
		for (int i = 0; i < days; i++) {
			checkBoxesa[i].setChecked(true);
		}
		for (int i = 0; i < days; i++) {
			TextViews[i].setTextColor(getResources().getColor(R.color.CFF_BD_30));
		}
	}


	public void doAsyncGetSignIns(String uid) {
		final Map<String, String> map = new HashMap<>();
		map.put("userId", uid);
//		map.put("userId", "18");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/signUser").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {

				}
				dismissDialog();
			}

			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(MineSignInActivity.this);
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
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetSignIns--- json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					Log.i(TAG, "onResponse: " + response.getMsg());
					Toast.makeText(MineSignInActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MineSignInActivity.this, "签到失败", Toast.LENGTH_SHORT).show();
				}
				dismissDialog();
			}
		});
	}


	@Override
	public void setSignInData(SignInEntity.DataBean data) {
//		this.mData = data;
//		mAdapter.setData(data);
	}


	private void setRuleDialog() {

		if (signRule != null && !signRule.equals("")) {
			View pview = LayoutInflater.from(this).inflate(R.layout.dialog_integral_info, null);
			LinearLayout llCancel = (LinearLayout) pview.findViewById(R.id.integral_ll_cancel);
			TextView tvContent = (TextView) pview.findViewById(R.id.integral_tv_content);
			TextView tvYes = (TextView) pview.findViewById(R.id.integral_tv_yes);
			tvContent.setText(signRule);
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
	}

	private void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}
}
