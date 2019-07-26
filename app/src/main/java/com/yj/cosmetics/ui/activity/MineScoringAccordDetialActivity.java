package com.yj.cosmetics.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.AccordDetailEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2017/7/31.
 */

public class MineScoringAccordDetialActivity extends BaseActivity {
	private static final String TAG = "MineScoringAccordDetialActivity";
	@BindView(R.id.scoring_accord_detial_tv_state)
	TextView tvState;
	@BindView(R.id.scoring_accord_detial_tv_money)
	TextView tvMoney;
	@BindView(R.id.scoring_accord_detial_tv_score)
	TextView tvScore;
	@BindView(R.id.scoring_accord_detial_tv_time)
	TextView tvTime;
	@BindView(R.id.scoring_accord_detial_tv1)
	TextView tvState1;
	@BindView(R.id.scoring_accord_detial_tv2)
	TextView tvState2;
	@BindView(R.id.scoring_accord_detial_tv3)
	TextView tvState3;
	@BindView(R.id.scoring_accord_detial_tv4)
	TextView tvState4;
	@BindView(R.id.scoring_accord_detial_iv2)
	ImageView ivState2;
	@BindView(R.id.scoring_accord_detial_iv3)
	ImageView ivState3;
	@BindView(R.id.scoring_accord_detial_v2)
	View v2;


	private String applyId;

	@Override
	protected int getContentView() {
		return R.layout.activity_scoring_accord_detail;
	}

	@Override
	protected void initView() {
		setTitleText("申请详情");
	}

	@Override
	protected void initData() {
		applyId = getIntent().getStringExtra("applyId");
		doAsyncGetData();
	}

	private void doAsyncGetData() {
		Map<String, String> map = new HashMap<>();
		map.put("id", applyId);
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/usersScoreApplyDetail")
				.tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<AccordDetailEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				Log.i(TAG, "onError: "+ e);
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}

			@Override
			public AccordDetailEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("MineScoringAccordDetialActivity ---json的值" + json);
				return new Gson().fromJson(json, AccordDetailEntity.class);
			}

			@Override
			public void onResponse(AccordDetailEntity response) {

				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					setData(response.getData());
				} else {
					ToastUtils.showToast(MineScoringAccordDetialActivity.this, "故障" + response.getMsg());
				}
			}
		});
	}

	private void setData(AccordDetailEntity.AccordDetialData data) {
		tvMoney.setText(data.getConsume().getApplyMoney());
		tvTime.setText(data.getConsume().getInsertTime());
		tvScore.setText(data.getConsume().getApplyScore());

		switch (data.getConsume().getApplyState()) {
			case "1":
				tvState.setText("等待审核中");
				ivState2.setImageResource(R.mipmap.tongguo_jfx);
				tvState2.setTextColor(getResources().getColor(R.color.CE8_3C_3C));
				tvState1.setTextColor(getResources().getColor(R.color.C52_52_52));
				v2.setBackgroundColor(getResources().getColor(R.color.CE8_3C_3C));
				break;
			case "2":
				tvState.setText("审核通过");
				ivState2.setImageResource(R.mipmap.tongguo_jfx);
				ivState3.setImageResource(R.mipmap.tongguo_jfx);
				tvState2.setTextColor(getResources().getColor(R.color.C52_52_52));
				tvState1.setTextColor(getResources().getColor(R.color.C52_52_52));
				tvState3.setTextColor(getResources().getColor(R.color.CE8_3C_3C));
				v2.setBackgroundColor(getResources().getColor(R.color.CE8_3C_3C));
				break;
			case "3":
				tvState.setText("审核失败");
				ivState2.setImageResource(R.mipmap.tongguo_jfx);
				ivState3.setImageResource(R.mipmap.request_denied);
				tvState2.setTextColor(getResources().getColor(R.color.C52_52_52));
				tvState1.setTextColor(getResources().getColor(R.color.C52_52_52));
				tvState3.setTextColor(getResources().getColor(R.color.CE8_3C_3C));
				tvState4.setText("您的申请失败了");
				tvState3.setText("审核失败");
				v2.setBackgroundColor(getResources().getColor(R.color.CE8_3C_3C));
				break;
		}
	}

	@Override
	protected void onDestroy() {
		OkHttpUtils.getInstance().cancelTag(this);
		super.onDestroy();
	}

}
