package com.yj.cosmetics.ui.activity.sotreList;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.ShopListEntity;
import com.yj.cosmetics.model.StoreListEntity;
import com.yj.cosmetics.ui.activity.storeDetail.StoreDetailActivity;
import com.yj.cosmetics.ui.adapter.SearchStoreListAdapter;
import com.yj.cosmetics.ui.adapter.StoreAdapter;
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
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/8/8 0008.
 */

public class StoreListActivity extends BaseActivity {


	private static final String TAG = "StoreListActivity";
	@BindView(R.id.store_list_tv_default)
	TextView storeListTvDefault;
	@BindView(R.id.store_list_ll_default)
	LinearLayout storeListLlDefault;
	@BindView(R.id.store_list_tv_lately)
	TextView storeListTvLately;
	@BindView(R.id.store_list_ll_lately)
	LinearLayout storeListLlLately;
	@BindView(R.id.store_list_tv_highest)
	TextView storeListTvHighest;
	@BindView(R.id.store_list_ll_highest)
	LinearLayout storeListLlHighest;
	@BindView(R.id.title_view)
	View vLine;

	@BindView(R.id.recyclerView)
	XRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;

	private String name;
	private LinearLayoutManager layoutManager;


	private int pageNum = 0;

	private List<ShopListEntity.DataBean.ShopArrayBean> shopArray = new ArrayList<>();
	private List<StoreListEntity.DataBean.ProductClassifyListBean> mList = new ArrayList<>();
//	private SearchStoreListAdapter mAdapter;

	private StoreAdapter mAdapter;

	@Override
	protected int getContentView() {
		return R.layout.activity_store_list;
	}

	@Override
	protected void initView() {
		setTitleText("店铺列表");
		vLine.setVisibility(View.GONE);
		storeListTvDefault.setSelected(true);
		name = getIntent().getStringExtra("name");
		layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
//		mRecyclerView.setLoadingMoreEnabled(false);
//		mAdapter = new SearchStoreListAdapter(this, shopArray);
		mAdapter = new StoreAdapter(StoreListActivity.this, shopArray);
		mRecyclerView.setAdapter(mAdapter);
//		mAdapter.setOnItemClickListener(new SearchStoreListAdapter.SpendDetialClickListener() {
//			@Override
//			public void onItemClick(View view, int postion) {
//				Intent intent = new Intent(StoreListActivity.this, StoreDetailActivity.class);
//				intent.putExtra("shopId", shopArray.get(postion - 1).getShopId());
//				startActivity(intent);
//			}
//		});
		mAdapter.setOnItemClickListener(new StoreAdapter.ShopListClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
				Intent intent = new Intent(StoreListActivity.this, StoreDetailActivity.class);
				intent.putExtra("shopId", shopArray.get(postion - 1).getShopId());
				startActivity(intent);
			}
		});

	}


	@Override
	protected void initData() {
//		if (mUtils.isLogin()) {
		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				pageNum = 1;
				new Handler().postDelayed(new Runnable() {
					public void run() {
						doRefreshData();

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
//		}

	}

	private void doRefreshData() {
		mProgressLayout.showContent();

		Map<String, String> map = new HashMap<>();
//		map.put("orderBy", orderby);
		map.put("shopName", name);
		map.put("pageNum", String.valueOf(pageNum));


		LogUtils.i("shopList 传输的值" + URLBuilder.format(map));

		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/shopList")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<ShopListEntity>() {
			@Override
			public ShopListEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("shopList json的值。" + json);
				return new Gson().fromJson(json, ShopListEntity.class);
			}

			@Override
			public void onResponse(ShopListEntity response) {

				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null) {
						shopArray.clear();
						shopArray.addAll(response.getData().getShopArray());
						mAdapter.notifyDataSetChanged();
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

							mRecyclerView.refresh();
						}
					});
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		OkHttpUtils.getInstance().cancelTag(this);
		super.onDestroy();
	}

	private void loadMoreData() {

		Map<String, String> map = new HashMap<>();
//		map.put("orderBy", orderby);
		map.put("shopName", name);
		map.put("pageNum", String.valueOf(pageNum));


		LogUtils.i("shopList 传输的值" + URLBuilder.format(map));

		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/shopList")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<ShopListEntity>() {
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
					ToastUtils.showToast(StoreListActivity.this, "网络异常 :)" + response.getMsg());
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
					ToastUtils.showToast(StoreListActivity.this, "网络故障,请稍后再试");
					pageNum--;
				}
			}
		});

	}


	@OnClick({R.id.store_list_ll_default, R.id.store_list_ll_lately, R.id.store_list_ll_highest})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.store_list_ll_default:
//				orderby = "nums desc";
				storeListTvDefault.setSelected(true);
				storeListTvLately.setSelected(false);
				storeListTvHighest.setSelected(false);
				mRecyclerView.refresh();
				break;
			case R.id.store_list_ll_lately:
//				orderby = "juli asc";
				storeListTvDefault.setSelected(false);
				storeListTvLately.setSelected(true);
				storeListTvHighest.setSelected(false);
				mRecyclerView.refresh();
				break;
			case R.id.store_list_ll_highest:
//				orderby = "nums desc";
				storeListTvDefault.setSelected(false);
				storeListTvLately.setSelected(false);
				storeListTvHighest.setSelected(true);
				mRecyclerView.refresh();
				break;
		}
	}
}
