package com.yj.cosmetics.ui.fragment.StoreFrag;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseFragment;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.ShopListEntity;
import com.yj.cosmetics.model.StoreListAddressEntity;
import com.yj.cosmetics.model.StoreListEntity;
import com.yj.cosmetics.ui.activity.SearchActivity;
import com.yj.cosmetics.ui.activity.locAddress.locAddressActivity;
import com.yj.cosmetics.ui.activity.storeDetail.StoreDetailActivity;
import com.yj.cosmetics.ui.adapter.StoreAdapter;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.ProgressLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/7/24 0024.
 */

public class StoreFrag extends BaseFragment implements StoreAdapter.ViewInterfaces {

	private static final String TAG = "StoreFrag";
	Unbinder unbinder;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	@BindView(R.id.frag_store_locinfo)
	LinearLayout rlStoreLocInfo;
	@BindView(R.id.frag_store_rl_search)
	RelativeLayout rlSearch;
	@BindView(R.id.frag_store_v_head)
	View vHead;
	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	LinearLayoutManager layoutManager;
	@BindView(R.id.frag_store_tv_info)
	TextView tvAddress;


	private int pageNum = 0;
	private StoreAdapter mAdapter;
	private List<StoreListEntity.DataBean.ProductClassifyListBean> mList = new ArrayList<>();
	private List<ShopListEntity.DataBean.ShopArrayBean> shopArray = new ArrayList<>();

	private String latitude, longitude, orderby;
//	private MyReceiver myReceiver;
	private String addressAreaDetail;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = createView(inflater.inflate(R.layout.fragment_store, container, false));
		unbinder = ButterKnife.bind(this, view);
		return view;
	}


	@Override
	protected void initData() {
		layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		orderby = "juli asc,nums desc";
//		for (int i = 0; i < 20; i++) {
//			mList.add("数据为" + i);
//		}
		tvAddress.setSelected(true);
//		myReceiver = new MyReceiver();
//		IntentFilter intentFilters = new IntentFilter();
//		intentFilters.addAction("cn.yj.robust.getAddInfo");
//		getActivity().registerReceiver(myReceiver, intentFilters);

		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
//		mRecyclerView.setLoadingMoreEnabled(false);
		mAdapter = new StoreAdapter(getActivity(),shopArray);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setViewInterfaces(this);
		mAdapter.setOnItemClickListener(new StoreAdapter.ShopListClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
				Intent intent = new Intent(getContext(), StoreDetailActivity.class);
				intent.putExtra("shopId", shopArray.get(postion-1).getShopId());
				startActivity(intent);
			}
		});

		transTitle();
		if (mUtils.getUid() == null) {

		}
//		refreshStoreData();
		refresh();
	}

	private void refreshStoreData() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				refresh();
			}
		}, 500);
	}
/*
	class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {

				if (intent.getStringExtra("refreshData") != null) {
					if (mUtils.getUid() == null) {

					}

					refreshStoreData();
					return;
				}

				if (intent.getStringExtra("latitude") != null && intent.getStringExtra("longitude") != null) {
					latitude = intent.getStringExtra("latitude");
					longitude = intent.getStringExtra("longitude");
					Log.i(TAG, "onReceive: longitude>>>>>" + latitude + " longitude: " + longitude);
					mRecyclerView.refresh();
				} else {
					String addressId = intent.getStringExtra("addressId");
					Log.e(TAG, "onReceive: " + addressId);
					doAsyncGetAddressList(intent.getStringExtra("addressId"));
				}
			}
		}
	}
*/


	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
//			refresh();
			if (mUtils.isLogin()) {
				if (mUtils.getAddInfo() != null)
					tvAddress.setText(mUtils.getAddInfo());
			}
		} else {
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}


	@TargetApi(21)
	private void transTitle() {
		if (Build.VERSION.SDK_INT >= 21) {
			vHead.setVisibility(View.VISIBLE);
		}
	}

	private void refresh() {
		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				pageNum = 1;
				new Handler().postDelayed(new Runnable() {
					public void run() {
						doAsyncGetData();
					}
				}, 500);            //refresh data here
			}

			@Override
			public void onLoadMore() {

				pageNum++;
				new Handler().postDelayed(new Runnable() {
					public void run() {
						mRecyclerView.setPullRefreshEnabled(false);
						loadMoreData();
					}
				}, 500);

			}
		});
		mRecyclerView.refresh();
	}


	@OnClick({R.id.frag_store_locinfo, R.id.frag_store_rl_search, /*R.id.frag_store_fab*/})
	public void onViewClicked(View view) {
		Intent intent;
		switch (view.getId()) {
			case R.id.frag_store_locinfo:
				if (mUtils.isLogin()) {
					intent = new Intent(getContext(), locAddressActivity.class);
					startActivityForResult(intent, 110);
				} else {
					IntentUtils.IntentToLogin(getContext());
				}
				break;
			case R.id.frag_store_rl_search:
				intent = new Intent(getContext(), SearchActivity.class);
				intent.putExtra("storeList","1");
				startActivity(intent);
				break;
//			case R.id.frag_store_fab:
//				mRecyclerView.smoothScrollToPosition(0);
//				break;

		}
	}

	@Override
	public void onClicks(int viewId) {

		switch (viewId) {
			case 1:
				orderby = "juli asc,nums desc";
				mRecyclerView.refresh();
				break;
			case 2:
				orderby = "nums desc";
				mRecyclerView.refresh();
				break;
			case 3:
				orderby = "juli asc";
				mRecyclerView.refresh();
				break;
		}
	}

//	CustomProgressDialog mDialog;


	private void doAsyncGetData() {

		Map<String, String> map = new HashMap<>();
//		map.put("orderBy", orderby);
		map.put("pageNum", pageNum + "");
//		if (!TextUtils.isEmpty(longitude + "")) {
//			map.put("shopLongitude", longitude + "");
//		}
//		if (!TextUtils.isEmpty(latitude + "")) {
//			map.put("shopLatitude", latitude + "");
//		}

		LogUtils.i("shopList 传输的值" + URLBuilder.format(map));

		String session = mUtils.getSession();
		Log.i(TAG, "session:Id " + session);
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/shopList")
				.addParams("data", URLBuilder.format(map))
				.tag(getActivity()).build().execute(new Utils.MyResultCallback<ShopListEntity>() {

			@Override
			public ShopListEntity parseNetworkResponse(Response response) throws Exception {
//				Headers headers = response.headers();
//				Log.i(TAG, "headers.toString(): " + headers.toString());
//				List<String> cookies = headers.values("Set-Cookie");
//				String session = cookies.get(0);
//				Log.d(TAG, "onResponse-size: " + cookies);
//				String s = session.substring(0, session.indexOf(";"));
//				Log.i(TAG, "shopList>>>>>>>>>>>>session is  :" + s);
				String json = response.body().string().trim();
				return new Gson().fromJson(json, ShopListEntity.class);
			}

			@Override
			public void onResponse(ShopListEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null && response.getData().getShopArray().size() != 0) {
						shopArray.clear();
						shopArray.addAll(response.getData().getShopArray());
//						mAdapte
// r.notifyDataSetChanged();
//						if (mUtils.getUid() == null) {
						tvAddress.setText(response.getData().getCity());
//						}
						doAsyncGetList();
						mProgressLayout.showContent();
					} else {
						mProgressLayout.showNone(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
							}
						});
					}
				} else {
					LogUtils.i("我挂了" + response.getMsg());
					mProgressLayout.showNetError(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (mList != null && !mList.isEmpty()) {
								mList.clear();
								mAdapter.notifyDataSetChanged();
							}
							mRecyclerView.refresh();
						}
					});
					mRecyclerView.refreshComplete();
				}
				mRecyclerView.refreshComplete();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				Log.i(TAG, "onError: " + e);
				mRecyclerView.refreshComplete();
				LogUtils.i("doAsyncGetData ----我故障了--" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					mProgressLayout.showNetError(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (mList != null && !mList.isEmpty()) {
								mList.clear();
								mAdapter.notifyDataSetChanged();
							}
							mRecyclerView.refresh();
						}
					});
				}
			}
		});
	}


//	private void disMissDialog() {
//		if (mDialog != null) {
//			mDialog.dismiss();
//			mDialog = null;
//		}
//	}


//	private void doAsyncGetLoction(String address, double latitude, double longitude) {
//		Map<String, String> map = new HashMap<>();
//		map.put("address", address);
//		map.put("location", longitude + "," + latitude);
//		LogUtils.i("sessionSaveLonLatName 传输的值" + URLBuilder.format(map));
//		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/sessionSaveLonLatName.act").tag(this)
//				.addParams(Key.data, URLBuilder.format(map))
//				.build().execute(new Utils.MyResultCallback<NormalEntity>() {
//
//			@Override
//			public void onError(Call call, Exception e) {
//				super.onError(call, e);
//			}
//
//			@Override
//			public NormalEntity parseNetworkResponse(Response response) throws Exception {
//				String json = response.body().string().trim();
//				LogUtils.i("sessionSaveLonLatName json的值" + json);
//				return new Gson().fromJson(json, NormalEntity.class);
//			}
//
//			@Override
//			public void onResponse(NormalEntity response) {
//				if (response != null && response.HTTP_OK.equals(response.getCode())) {
//					Log.i(TAG, "onResponse: " + response.getMsg());
//					preferencesUtil.setBooleanValue("isGetCoupon", false);
//				} else {
//				}
//			}
//		});
//	}


	private void doAsyncGetList() {
		Map<String, String> map = new HashMap<>();
		map.put("pageNum", pageNum + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/productClassifyList")
				.addParams("data", URLBuilder.format(map))
				.tag(getActivity()).build().execute(new Utils.MyResultCallback<StoreListEntity>() {
			@Override
			public StoreListEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetList -- json的值" + json);
				return new Gson().fromJson(json, StoreListEntity.class);
			}

			@Override
			public void onResponse(StoreListEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null && response.getData().getProductClassifyList().size() > 0) {
						mList.clear();
						mList.addAll(response.getData().getProductClassifyList());
						mAdapter.notifyDataSetChanged();
					}
				} else {
					LogUtils.i("我挂了" + response.getMsg());
				}
				mRecyclerView.setPullRefreshEnabled(true);
				mRecyclerView.refreshComplete();

			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);

				mRecyclerView.refreshComplete();
				mRecyclerView.setPullRefreshEnabled(true);
				LogUtils.i("doAsyncGetList ---- 我故障了--" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}
		});
	}


	private void loadMoreData() {

		Map<String, String> map = new HashMap<>();
//		map.put("orderBy", orderby);
		map.put("pageNum", pageNum + "");
//		if (!TextUtils.isEmpty(longitude + "")) {
//			map.put("shopLongitude", longitude + "");
//		}
//		if (!TextUtils.isEmpty(latitude + "")) {
//			map.put("shopLatitude", latitude + "");
//		}

		LogUtils.i("shopList 传输的值" + URLBuilder.format(map));

		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/shopList")
				.addParams("data", URLBuilder.format(map))
				.tag(getActivity()).build().execute(new Utils.MyResultCallback<ShopListEntity>() {
			@Override
			public ShopListEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("shopList -- json的值" + json);
				return new Gson().fromJson(json, ShopListEntity.class);
			}

			@Override

			public void onResponse(ShopListEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null && response.getData().getShopArray().size() != 0) {

						shopArray.addAll(response.getData().getShopArray());
						mAdapter.notifyDataSetChanged();
						mRecyclerView.setPullRefreshEnabled(true);
						mRecyclerView.loadMoreComplete();
						mProgressLayout.showContent();
					} else if (response.getData().getShopArray().size() == 0) {
						mRecyclerView.setNoMore(true);
						pageNum--;
					}
				} else {
					ToastUtils.showToast(getActivity(), "网络异常 :)" + response.getMsg());
					pageNum--;
					mRecyclerView.loadMoreComplete();

				}
				mRecyclerView.setPullRefreshEnabled(true);
			}

			@Override
			public void onError(Call call, Exception e) {

				super.onError(call, e);

				mRecyclerView.refreshComplete();
				mRecyclerView.loadMoreComplete();
				mRecyclerView.setPullRefreshEnabled(true);
				if (call.isCanceled()) {
					LogUtils.i("我进入到加载更多cancel了");
					call.cancel();
				} else if (pageNum != 1) {
					LogUtils.i("加载更多的Log");
					ToastUtils.showToast(getActivity(), "网络故障,请稍后再试");
					pageNum--;
				}
			}
		});
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		Log.e(TAG, "onActivityResult: " +
//				"addressAreaDetail " + data.getExtras().getString("addressAreaDetail")
//				+ " latitude " + data.getExtras().getString("latitude") +
//				" longitude " + data.getExtras().getString("longitude"));

		if (data != null) {
			mUtils.saveAddselect(data.getExtras().getString("addressAreaDetail"));
			Intent intent = new Intent();
			intent.setAction("cn.yj.robust.getAddInfos");
			tvAddress.setText(data.getExtras().getString("addressAreaDetail"));
			if (data.getExtras().getString("latitude") != null && data.getExtras().getString("longitude") != null) {
				latitude = data.getExtras().getString("latitude");
				longitude = data.getExtras().getString("longitude");
				addressAreaDetail = data.getExtras().getString("addressAreaDetail");
				intent.putExtra("latitude", latitude);
				intent.putExtra("longitude", longitude);
				intent.putExtra("addressAreaDetail", addressAreaDetail);
				mRecyclerView.refresh();
			} else {
				intent.putExtra("addressId", data.getExtras().getString("addressId"));
//				doAsyncGetAddressList(data.getExtras().getString("addressId"));
			}
			getActivity().sendBroadcast(intent);
		}
	}

	private void doAsyncGetAddressList(String addressId) {
		Map<String, String> map = new HashMap<>();
		map.put("addressId", addressId);
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/yjDistance")
				.addParams("data", URLBuilder.format(map))
				.tag(getActivity()).build().execute(new Utils.MyResultCallback<StoreListAddressEntity>() {
			@Override
			public StoreListAddressEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("yjDistance -- json的值" + json);
				return new Gson().fromJson(json, StoreListAddressEntity.class);
			}

			@Override
			public void onResponse(StoreListAddressEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null) {
						String location = response.getData().getLocation();
						String[] split = location.split(",");
						longitude = split[0];
						latitude = split[1];
						mRecyclerView.refresh();
					}
				} else {
					LogUtils.i("我挂了" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("doAsyncGetList ---- 我故障了--" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}
		});
	}

	@Override
	public void onDestroy() {
//		OkHttpUtils.getInstance().cancelTag(this);
//		getActivity().unregisterReceiver(myReceiver);
		super.onDestroy();
	}
}
