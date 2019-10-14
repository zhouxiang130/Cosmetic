package com.yj.cosmetics.ui.activity.storeDetail;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.NormalEntitys;
import com.yj.cosmetics.model.ShopDetailEntity;
import com.yj.cosmetics.ui.activity.SearchActivity;
import com.yj.cosmetics.ui.fragment.MineStoreFrag.MineStoreFrag;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.StatusBarUtil;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.AppBarCollapsingStateHelper;
import com.yj.cosmetics.widget.Dialog.StoreInfoDialogs;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
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
 * Created by Administrator on 2018/7/25 0025.
 *
 *  店铺详情界面 3.0界面
 */

public class StoreDetailActivity extends BaseActivity {
	@BindView(R.id.tv_store_detail_collect)
	TextView tvCollect;
	@BindView(R.id.img_shop_icon)
	RoundedImageView shopIcon;
	@BindView(R.id.shop_detail_name)
	TextView tvShopName;
	@BindView(R.id.shop_dispatch_tv_publicity)
	TextView tvPublicity;
	@BindView(R.id.tv_store_depict)
	TextView tvDepict;
	@BindView(R.id.fl_frag)
	FrameLayout flFrag;
	@BindView(R.id.appbar)
	AppBarLayout appBarLayout;
	@BindView(R.id.fab_home_random)
	FloatingActionButton floatButtom;
	public ShopDetailEntity datas;
	private String shopId;

	@Override
	protected int getContentView() {
		return R.layout.activity_store_detail;
	}


	@Override
	protected void initView() {
		setTitleColor(getResources().getColor(R.color.white));
		setTitleBackground(getResources().getColor(R.color.transparent));
		shopId = getIntent().getStringExtra("shopId");
		getSupportFragmentManager().beginTransaction().add(R.id.fl_frag, MineStoreFrag.instant(shopId)).commit();
		doAsyncShopInfo();
		floatButtom.setVisibility(View.VISIBLE);
		floatButtom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MineStoreFrag.instant(shopId).setmRecyclerView();
			}
		});
	}

	@Override
	protected void initData() {
	}


	@OnClick({R.id.frag_store_info_iv, R.id.frag_store_rl_search, R.id.tv_store_detail_collect, R.id.shop_rl_info})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.frag_store_info_iv:
				finish();
				break;
			case R.id.frag_store_rl_search:
				Intent intent = new Intent(StoreDetailActivity.this, SearchActivity.class);
				intent.putExtra("shopId", shopId);
				startActivity(intent);
				break;
			case R.id.tv_store_detail_collect:
				if (mUtils.isLogin()) {
					LogUtils.i("我进入网络操作了");
					tvCollect.setClickable(false);
					doAsyncCollect();
				} else {
					IntentUtils.IntentToLogin(StoreDetailActivity.this);
				}

				break;
			case R.id.shop_rl_info:
				if (datas != null) {
					showDialogs(datas, 1);

				}
				break;
		}
	}

	private void doAsyncShopInfo() {
		Map<String, String> map = new HashMap<>();
		map.put("shopId", shopId);
		if (mUtils.getUid() != null && !mUtils.getUid().isEmpty())
			map.put("userId", mUtils.getUid());
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/storeDetail").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<ShopDetailEntity>() {


			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
//				tvInfo.setVisibility(View.GONE);
				if (call.isCanceled()) {
					call.cancel();
				} else {

				}
			}

			@Override
			public ShopDetailEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncShopInfo json的值" + json);
				return new Gson().fromJson(json, ShopDetailEntity.class);
			}

			@Override
			public void onResponse(ShopDetailEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					setData(response);
				} else {
				}
			}
		});
	}


	public void doAsyncCollect() {
		Map<String, String> map = new HashMap<>();
		map.put("shopId", shopId);
		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的网络数据" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/saveDeleteShopCollection")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<NormalEntitys>() {
			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
			}

			@Override
			public NormalEntitys parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, NormalEntitys.class);
			}

			@Override
			public void onResponse(NormalEntitys response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null) {
						if (response.getData().getMsg().equals("1")) {
							ToastUtils.showToast(StoreDetailActivity.this, "收藏成功");
							tvCollect.setText("已收藏");
						} else {
							ToastUtils.showToast(StoreDetailActivity.this, "取消收藏");
							tvCollect.setText(" 收藏");
						}
					}
				} else {
					ToastUtils.showToast(StoreDetailActivity.this, "网络故障 " + response.getMsg());
				}
				tvCollect.setClickable(true);
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络请求失败 " + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(StoreDetailActivity.this, "网络故障,请稍后再试 ");
				}

				tvCollect.setClickable(true);
			}
		});
	}


	private void setData(ShopDetailEntity data) {
		this.datas = data;
		if (data.getData().getShopLogo() != null) {
			Glide.with(StoreDetailActivity.this)
					.load(URLBuilder.getUrl(data.getData().getShopLogo()))
					.asBitmap()
					.centerCrop()
					.placeholder(R.mipmap.default_goods)
					.error(R.mipmap.default_goods)
					.into(shopIcon);
		}
		tvShopName.setText(data.getData().getShopName());
//		tvServices.setText("起送：" + data.getData().getServiceStartime());
		tvPublicity.setText("商品： " + data.getData().getShopProCount());
//		MineStoreFrags.instant(shopId).setData(data);

		if (data.getData().getShopCollectionType() != null && !data.getData().getShopCollectionType().equals("")) {
			if (data.getData().getShopCollectionType().equals("yes")) {
				tvCollect.setText("已收藏");
			} else {
				tvCollect.setText(" 收藏");
			}
		}

		if (data.getData().getShopNotice() != null) {
			tvDepict.setVisibility(View.VISIBLE);
			tvDepict.setText(data.getData().getShopNotice());
		} else {
			tvDepict.setVisibility(View.GONE);

		}
		/*if (data.getData().getData().getReceipt() .equals("1")) {
			if (data.getData().getData().getDeliveryDistanceType().equals("1")) {
				tvStoteStates.setVisibility(View.GONE);
			} else {
				tvStoteStates.setVisibility(View.VISIBLE);
				tvStoteStates.setText("超出配送范围");
			}

		} else {
			tvStoteStates.setVisibility(View.VISIBLE);
		}*/
	}

	@Override
	protected void onDestroy() {
		OkHttpUtils.getInstance().cancelTag(this);
		super.onDestroy();
	}

	StoreInfoDialogs TicketDialog;

	public void showDialogs(ShopDetailEntity datas, int i) {

		if (TicketDialog == null) {
			TicketDialog = new StoreInfoDialogs(StoreDetailActivity.this);
		}

		TicketDialog.setCustomDialog(datas, i);

		if (!TicketDialog.isShowing()) {
			TicketDialog.show();
		}
	}
}
