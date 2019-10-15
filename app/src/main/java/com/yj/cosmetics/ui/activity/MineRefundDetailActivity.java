package com.yj.cosmetics.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineRefundDetailEntity;
import com.yj.cosmetics.ui.adapter.MineRefundListGoodsDetailAdapter;
import com.yj.cosmetics.util.AuthorUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.yj.cosmetics.widget.WrapContentGridView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/15 0015.
 *
 * @退款详情 2.0添加页面
 */

public class MineRefundDetailActivity extends BaseActivity {
	@BindView(R.id.refound_detial_tv_state)
	TextView refoundDetialTvState;
	@BindView(R.id.refound_detial_tv_state_time)
	TextView refoundDetialTvStateTime;
	@BindView(R.id.refound_detial_tv_normal_price)
	TextView refoundDetialTvNormalPrice;
	@BindView(R.id.refound_detial_tv_normal_price_)
	TextView refoundDetialTvNormalPrice_;
	@BindView(R.id.order_detial_rl_price_)
	RelativeLayout rlprice_;
	@BindView(R.id.refund_detail_tv_reason_detail_)
	TextView tvReason_;

	@BindView(R.id.frag_mine_shop_iv)
	RoundedImageView shopIv;
	@BindView(R.id.frag_mine_shop_detail)
	RelativeLayout shopDetail;
	@BindView(R.id.frag_mine_shop_name)
	TextView shopName;
	@BindView(R.id.image_store_more)
	ImageView ivMore;

//	@BindView(R.id.item_order_goods_iv)
//	ImageView itemOrderGoodsIv;
//	@BindView(R.id.item_order_goods_tv_price)
//	TextView itemOrderGoodsTvPrice;
//	@BindView(R.id.item_order_goods_tv_title)
//	TextView itemOrderGoodsTvTitle;
//	@BindView(R.id.item_order_goods_tv_number)
//	TextView itemOrderGoodsTvNumber;
//	@BindView(R.id.item_order_goods_tv_style)
//	TextView itemOrderGoodsTvStyle;

	@BindView(R.id.refund_detail_tv_reason_detail)
	TextView refundDetailTvReasonDetail;
	@BindView(R.id.refund_detail_tv_price_)
	TextView refundDetailTvPrice_;
	@BindView(R.id.refund_detail_tv_time_)
	TextView refundDetailTvTime_;
	@BindView(R.id.refund_detail_tv_number_)
	TextView refundDetailTvNumber;
	@BindView(R.id.gridView)
	WrapContentGridView gridView;

	private String returnId;
	CustomNormalContentDialog mDialog;

	/**
	 * 需要进行检测的权限数组
	 */
	private static final int PERMISSON_REQUESTCODE = 0;
	private boolean isNeedCheck = true;
	protected String[] needPermissions = {
			Manifest.permission.CALL_PHONE
	};

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_refund_detail;
	}

	@Override
	protected void initView() {
		setTitleText("退款详情");
		returnId = getIntent().getStringExtra("returnId");
	}

	@Override
	protected void initData() {
		doRefreshData();
	}


	@OnClick({ R.id.refund_detail_rl_rexian})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.refund_detail_rl_rexian:
				if (TextUtils.isEmpty(mUtils.getServiceTel()) && TextUtils.isEmpty(mUtils.getServiceTel())) {
					Toast.makeText(this, "暂无客服联系方式", Toast.LENGTH_SHORT).show();
					return;
				}
				showCallDialog();
				break;
		}
	}


	public void showCallDialog() {
		if (mDialog == null) {
			mDialog = new CustomNormalContentDialog(this);
		}
		if (TextUtils.isEmpty(mUtils.getServiceTel()) && TextUtils.isEmpty(mUtils.getServiceTel())) {
			ToastUtils.showToast(this, "无法获取联系电话");
			return;
		}
		if (!mDialog.isShowing()) {
			mDialog.show();
		}
		mDialog.getTvTitle().setText("客服热线");
		if (!TextUtils.isEmpty(mUtils.getServiceTel())) {
			mDialog.getTvContent().setText("拨打" + mUtils.getServiceTel() + "热线，联系官方客服");
		} else if (!TextUtils.isEmpty(mUtils.getServiceTel())) {
			mDialog.getTvContent().setText("拨打" + mUtils.getServiceTel() + "热线，联系官方客服");
		}
		mDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//拨打电话

				if (new AuthorUtils(MineRefundDetailActivity.this).checkPermissions(needPermissions)) {
					setActionCall();
				}
			}
		});
		mDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismissDialog(mDialog);
			}
		});
	}


	private void setActionCall() {
		Intent callIntent = new Intent();
		callIntent.setAction(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + mUtils.getServiceTel()));
		startActivity(callIntent);
		dismissDialog(mDialog);
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



	public void dismissDialog(CustomNormalContentDialog mDialog) {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	private void doRefreshData() {
//		mProgressLayout.showContent();
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("returnId", returnId);
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/returnDetail.act")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<MineRefundDetailEntity>() {

			@Override

			public MineRefundDetailEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("returnDetail json的值" + json);
				return new Gson().fromJson(json, MineRefundDetailEntity.class);
			}

			@Override
			public void onResponse(MineRefundDetailEntity response) {
//                dismissDialog();
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功

					setDatas(response);
//					mList.clear();
//					if (response.getData() != null) {
//						if (response.getData().getItem().size() != 0) {
//							mList.addAll(response.getData().getList());
//							mAdapter.notifyDataSetChanged();
//							mProgressLayout.showContent();
//						} else if (response.getData().getItem().size() == 0) {

//							mProgressLayout.showNone(new View.OnClickListener() {
//								@Override
//								public void onClick(View view) {
//								}
//							});
//						}
//					}

//				} else {
//					ToastUtils.showToast(MineRefundDetailActivity.this, "请求失败 :)" + response.getMsg());
//					mProgressLayout.showNetError(new View.OnClickListener() {
//						@Override
//						public void onClick(View view) {
//							if (mList != null && !mList.isEmpty()) {
//								mList.clear();
//								mAdapter.notifyDataSetChanged();
//							}
//							mRecyclerView.refresh();
//						}
//					});

				}
//				mRecyclerView.refreshComplete();

			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
//					mProgressLayout.showNetError(new View.OnClickListener() {
//						@Override
//						public void onClick(View view) {
//							if (mList != null && !mList.isEmpty()) {
//								mList.clear();
//								mAdapter.notifyDataSetChanged();
//							}
//							mRecyclerView.refresh();
//						}
//					});
				}
//				mRecyclerView.refreshComplete();
				LogUtils.i("故障" + e);
			}
		});
	}

	public void setDatas(final MineRefundDetailEntity datas) {
		switch (datas.getData().getIsReturn()) {//1待处理 2退款成功 3退款驳回
			case 1:
				refoundDetialTvState.setText("待处理");
				break;
			case 2:
				refoundDetialTvState.setText("退款成功");
				break;
			case 3:
				refoundDetialTvState.setText("退款驳回");
				break;
		}
		if (datas.getData().getShopId() != null) {
			ivMore.setVisibility(View.VISIBLE);
			shopDetail.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(MineRefundDetailActivity.this, StoreDetailActivity.class);
					intent.putExtra("shopId", datas.getData().getShopId());
					startActivity(intent);
				}
			});
		} else {
			ivMore.setVisibility(View.GONE);
		}
		Glide.with(MineRefundDetailActivity.this)
				.load(URLBuilder.getUrl(datas.getData().getShopImg()))
				.error(R.mipmap.default_goods)
				.centerCrop()
				.into(shopIv);
		shopName.setText(datas.getData().getShopName());
		if (!datas.getData().getHandlingTime().equals("")) {
			refoundDetialTvStateTime.setText(datas.getData().getHandlingTime());
		} else {
			refoundDetialTvStateTime.setText("退款正在进行时，请稍后查看");
		}
		if (datas.getData().getBackReason() != null && !datas.getData().getBackReason().equals("")) {
			rlprice_.setVisibility(View.VISIBLE);
			tvReason_.setText(datas.getData().getBackReason());
		} else {
			rlprice_.setVisibility(View.GONE);
		}
		refundDetailTvTime_.setText(datas.getData().getTime());
		refundDetailTvReasonDetail.setText(datas.getData().getReturnReason());
		refundDetailTvPrice_.setText("￥" + datas.getData().getAccountMoney());//退款余额
		refoundDetialTvNormalPrice_.setText("￥" + datas.getData().getReturnBack());
		refoundDetialTvNormalPrice.setText("￥" + datas.getData().getReturnMoney());
		refundDetailTvNumber.setText(datas.getData().getReturnNum());
		MineRefundListGoodsDetailAdapter gridViewAdapter = new MineRefundListGoodsDetailAdapter(this, datas.getData().getItem());
		gridView.setAdapter(gridViewAdapter);
		gridView.setFocusable(false);
	}
}
