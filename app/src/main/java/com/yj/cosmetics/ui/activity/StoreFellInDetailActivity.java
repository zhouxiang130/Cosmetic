package com.yj.cosmetics.ui.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.R;
import com.yj.cosmetics.model.FellInEntity;
import com.yj.cosmetics.ui.adapter.FellInAdapter;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/6/21 0021.
 */

public class StoreFellInDetailActivity extends BaseActivity {


	private static final String TAG = "StoreFellInDetailActivity";
	@BindView(R.id.title_ll_back)
	LinearLayout llBack;
	@BindView(R.id.tv_store_name)
	TextView tvStoreName;
	@BindView(R.id.tv_store_con)
	TextView tvStoreCon;

	@BindView(R.id.frag_store_iv)
	RoundedImageView ivStore;

	@BindView(R.id.tv_mine_free_sayest)
	TextView tvMineFreeSayest;
	@BindView(R.id.tv_account_pic)
	TextView tvAccountPic;

	@BindView(R.id.tv_mine_store_num)
	TextView tvMineStoreNum;
	@BindView(R.id.tv_total_store)
	TextView tvTotalStore;


	@BindView(R.id.view_line)
	View Line;


	@BindView(R.id.xrecyclerView)
	RecyclerView xrecyclerView;

	FellInAdapter adapter = null;

	private String orderDescId, shopId, shopName;
	List<FellInEntity.DataBean.ArrBean> ListData = null;
	private LinearLayoutManager layoutManager;

	@Override
	protected int getContentView() {
		return R.layout.activity_store_fill_in_detail;
	}

	@Override
	protected void initView() {
		hideTitle();
		shopName = getIntent().getStringExtra("shopName");
		llBack.setVisibility(View.VISIBLE);
		if (!TextUtils.isEmpty(shopName) && shopName != null) {
			setTitleText(shopName);
		}
	}

	@Override
	protected void initData() {
		ListData = new ArrayList<>();
		shopId = getIntent().getStringExtra("shopId");
		orderDescId = getIntent().getStringExtra("orderDescId");

		reqAsynStoreDeail(mUtils.getUid(), orderDescId, shopId);

		layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		xrecyclerView.setLayoutManager(layoutManager);
		adapter = new FellInAdapter(this, ListData);
		xrecyclerView.setAdapter(adapter);
	}

	@SuppressLint("LongLogTag")
	public void reqAsynStoreDeail(String userId, String orderDescIds, String shopId) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("orderDescId", orderDescIds);
		map.put("shopId", shopId);

		Log.i(TAG, "reqAsynStoreDeail: " + URLBuilder.format(map));

		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.sellerDt)
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<FellInEntity>() {


			@SuppressLint("LongLogTag")
			@Override
			public FellInEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				Log.i(TAG, "doAsyncGetData -- json的值" + json);
				FellInEntity homeEntity = new Gson().fromJson(json, FellInEntity.class);
				return homeEntity;
			}

			@SuppressLint("LongLogTag")
			@Override
			public void onResponse(FellInEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null) {
						showContent(response.getData());
					} else {
						setNone();
					}
				} else {
					Log.i(TAG, "我挂了" + response.getMsg());
				}
			}

			@SuppressLint("LongLogTag")
			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				Log.i(TAG, "doAsyncGetData ----我故障了--" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					showNetError();
				}

			}
		});
	}

	public void showContent(FellInEntity.DataBean data) {
//		this.data = data;
		setStoreInfo(data);
	}


	public void setNone() {

	}

	public void showNetError() {

	}

	private void setStoreInfo(FellInEntity.DataBean data) {
		Glide.with(StoreFellInDetailActivity.this)
				.load(URLBuilder.getUrl(data.getShopHeadImg()))
				.asBitmap()
				.centerCrop()
				.error(R.mipmap.default_avatar)
				.into(ivStore);

//		if (data.getRowNum().equals("1")) {
////			tvMineSucc.setText(data.getSuccessMoney());//免单成功金额
//			tvMineStoreNum.setText(data.getPoolFunds());//免单收益
//			tvMineFreeSayest.setText(data.getPayMoney());//消费积分
//		} else {
		tvMineFreeSayest.setText(data.getPayMoney());//消费积分
//			tvAccountPic.setText("我的消费积分");//消费积分
		tvMineStoreNum.setText(data.getRowNum());//免单成功金额
//			tvTotalSucc.setText("我的排名");//消费积分
//		}

		ListData.addAll(data.getArr());
		adapter.setData(ListData, data.getRowNum());
		tvStoreName.setText(data.getShopName());
		tvStoreCon.setText(data.getShopAddress());


	}
}
