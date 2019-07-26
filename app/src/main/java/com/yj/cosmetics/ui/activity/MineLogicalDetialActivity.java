package com.yj.cosmetics.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.LogicalDetailEntity;
import com.yj.cosmetics.ui.adapter.MineLogicalDetailAdapter;
import com.yj.cosmetics.util.AuthorUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.yj.cosmetics.widget.ProgressLayout;
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
 * Created by Suo on 2017/5/10.
 *
 * @TODO 物流信息界面
 */

public class MineLogicalDetialActivity extends BaseActivity {

	private static final String TAG = "MineLogicalDetialActivity";
	@BindView(R.id.recyclerView)
	RecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	@BindView(R.id.logical_detial_iv1)
	ImageView ivHeader;
	@BindView(R.id.logical_detial_state)
	TextView tvState;
	@BindView(R.id.logical_detial_order_num)
	TextView tvNum;
	@BindView(R.id.logical_detial_phone)
	TextView tvTel;
	//	@BindView(R.id.logical_detial_tv_name)
//	TextView tvShop;
	@BindView(R.id.logical_detial_tv_address)
	TextView tvAddress;
	@BindView(R.id.title_view)
	View vLine;
	@BindView(R.id.title_layout)
	LinearLayout llTop;
	@BindView(R.id.ll_no_wuliu_info)
	RelativeLayout llwuliuInfo;
	@BindView(R.id.tv_wuliu_info)
	TextView tvWuliuInfo;
	@BindView(R.id.ll_wuliu_all_info)
	LinearLayout allWuliuInfo;
	@BindView(R.id.rl_no_wuliu_info)
	RelativeLayout noWuliuInfo;


	private String order;

	/**
	 * 需要进行检测的权限数组
	 */
	private static final int PERMISSON_REQUESTCODE = 0;
	private boolean isNeedCheck = true;
	protected String[] needPermissions = {
			Manifest.permission.CALL_PHONE
	};

	List<LogicalDetailEntity.LogicalDetialData.LogicalDetialItem> mList;
	MineLogicalDetailAdapter mAdapter;
	private CustomNormalContentDialog mDialog;
	private String contactTel;

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_logical_detail;
	}

	@Override
	protected void initView() {
		setTitleText("物流详情");
		mList = new ArrayList<>();
		order = getIntent().getStringExtra("order");
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mAdapter = new MineLogicalDetailAdapter(this, mList);
		mRecyclerView.setAdapter(mAdapter);

	}

	@Override
	protected void initData() {
		doAsyncGetData();
	}

	@Override
	protected void showShadow1() {
		vLine.setVisibility(View.GONE);
//		llTop.setBackgroundColor(getResources().getColor(R.color.CF7_F9_FA));
	}

	@OnClick({R.id.logical_detial_phone})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.logical_detial_phone:
				contactTel = tvTel.getText().toString();
				showContactDialog(contactTel);
				break;
		}
	}

	private void showContactDialog(final String contactTel) {
		if (TextUtils.isEmpty(contactTel)) {
			ToastUtils.showToast(this, "网络故障，无法获取快递电话，请稍后再试");
			return;
		}
		if (mDialog == null) {
			mDialog = new CustomNormalContentDialog(this);
		}
		if (!mDialog.isShowing()) {
			mDialog.show();
		}
		mDialog.getTvTitle().setText("快递电话");
		mDialog.getTvContent().setText("拨打" + contactTel + "，联系快递客服");
		mDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//拨打电话

				if (new AuthorUtils(MineLogicalDetialActivity.this).checkPermissions(needPermissions)) {
					setActionCall();
				}
			}
		});

		mDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismissDialog();
			}
		});
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case PERMISSON_REQUESTCODE:
				if (!AuthorUtils.verifyPermissions(grantResults)) {
					isNeedCheck = false;
				} else {
					isNeedCheck = true;
				}
				if (isNeedCheck) {
					setActionCall();
				}
				break;
		}
	}




	private void setActionCall() {

		Intent callIntent = new Intent();
		callIntent.setAction(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + contactTel));
		startActivity(callIntent);
		dismissDialog();
	}

	private void doAsyncGetData() {
		mProgressLayout.showContent();
		Map<String, String> map = new HashMap<>();
		map.put("orderNum", order);
		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/express")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<LogicalDetailEntity>() {
			@Override
			public LogicalDetailEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetWuiuData----json的值" + json);
				return new Gson().fromJson(json, LogicalDetailEntity.class);
			}

			@Override
			public void onResponse(LogicalDetailEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					LogicalDetailEntity.LogicalDetialData data = response.getData();
					String code = response.getCode();
					Log.i(TAG, "物流信息: " + code);
					allWuliuInfo.setVisibility(View.VISIBLE);
					setData(response.getData());

				} else {
					noWuliuInfo.setVisibility(View.VISIBLE);
					allWuliuInfo.setVisibility(View.GONE);
					ToastUtils.showToast(MineLogicalDetialActivity.this, " :)" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					mProgressLayout.showNetError(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if(mList != null && !mList.isEmpty()){
								mList.clear();
								mAdapter.notifyDataSetChanged();
							}
							doAsyncGetData();
						}
					});
					ToastUtils.showToast(MineLogicalDetialActivity.this, "网络故障,请稍后再试");
				}
//                disMissDialog();
				LogUtils.i("故障" + e);
			}
		});
	}

	private void setData(LogicalDetailEntity.LogicalDetialData data) {


		tvNum.setText(data.getNu());
		tvTel.setText(data.getTel());
//		tvShop.setText(data.getOrderShipstyle());
		tvAddress.setText("[收货地址]" + data.getAddress());
		if (!TextUtils.isEmpty(data.getState())) {
			switch (data.getState()) {
				case "0":
					tvState.setText("在途");
					break;
				case "1":
					tvState.setText("已揽件");
					break;
				case "2":
					tvState.setText("疑难");
					break;
				case "3":
					tvState.setText("已签收");
					break;
				case "4":
					tvState.setText("已退签");
					break;
				case "5":
					tvState.setText("已派件");
					break;
				case "6":
					tvState.setText("已退回");
					break;
			}
		} else {
			tvState.setText("已出库");
		}
		String message = data.getMessage();
		if (!message.equals("ok")) {
			llwuliuInfo.setVisibility(View.VISIBLE);
//			tvWuliuInfo.setText("暂无物流配送信息,请稍后再查.");
			mRecyclerView.setVisibility(View.GONE);
		}


		Glide.with(getApplicationContext())
				.load(URLBuilder.getUrl( data.getImg()))
				.error(R.mipmap.default_goods)
				.centerCrop()
				.into(ivHeader);
		mList.clear();
		if (data.getData() != null) {
			mList.addAll(data.getData());
		}
		mAdapter.notifyDataSetChanged();
	}

	private void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

	@Override
	protected void onDestroy() {
		dismissDialog();
		OkHttpUtils.getInstance().cancelTag(this);
		super.onDestroy();
	}
}
