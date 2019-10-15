package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.CouponReceiveEntity;
import com.yj.cosmetics.ui.adapter.CouponReceiveAdapter;
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
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/8 0008.
 *
 * @领券中心 2.0添加页面
 */

public class CouponReceiveActivity extends BaseActivity {
	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	CouponReceiveAdapter mAdapter;
	List<CouponReceiveEntity.DataBean.CouponsBean> mList;
	private int page;

	@Override
	protected int getContentView() {
		return R.layout.activity_coupon_receive;
	}

	@Override
	protected void initView() {
		setTitleText("领券中心");
		mList = new ArrayList<>();

		View headView = getLayoutInflater().inflate(R.layout.layout_header_coupon_item, null);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
		mAdapter = new CouponReceiveAdapter(this, mList, mUtils.getUid());
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.addHeaderView(headView);

		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				page = 1;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						doRefresh();
					}
				}, 500);
			}

			@Override
			public void onLoadMore() {
				page++;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mRecyclerView.setPullRefreshEnabled(false);
						doAsyncLoadMore();
					}
				}, 500);
			}
		});
		mRecyclerView.refresh();

	}

	@Override
	protected void initData() {
	}

	private void doRefresh() {
		mProgressLayout.showContent();
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("pageNum", page + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/couponGiveList")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<CouponReceiveEntity>() {
			@Override
			public CouponReceiveEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doRefreshCouponList ---json的值" + json);
				return new Gson().fromJson(json, CouponReceiveEntity.class);
			}

			@Override
			public void onResponse(CouponReceiveEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData().getCoupons().size() != 0) {
						mList.clear();
						mList.addAll(response.getData().getCoupons());
						mAdapter.setPhone(response.getData().getTel());
						mAdapter.notifyDataSetChanged();
						mProgressLayout.showContent();
					} else if (response.getData().getCoupons().size() == 0) {
						mProgressLayout.showNoCoupons(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
							}
						});
					}
				} else {
					ToastUtils.showToast(CouponReceiveActivity.this, "网络故障 " + response.getMsg());
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
				mRecyclerView.setPullRefreshEnabled(true);
				mRecyclerView.refreshComplete();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.e("onError: " + e);
				mRecyclerView.refreshComplete();
				mRecyclerView.setPullRefreshEnabled(true);
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
//                disMissDialog();
				ToastUtils.showToast(CouponReceiveActivity.this, "网络故障,请稍后再试");
			}
		});
	}


	private void doAsyncLoadMore() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("pageNum", page + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/couponGiveList.act")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<CouponReceiveEntity>() {
			@Override
			public CouponReceiveEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, CouponReceiveEntity.class);
			}

			@Override
			public void onResponse(CouponReceiveEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData().getCoupons().size() != 0) {
						mList.addAll(response.getData().getCoupons());
						mAdapter.notifyDataSetChanged();
						mRecyclerView.setPullRefreshEnabled(true);
						mRecyclerView.loadMoreComplete();
					} else if (response.getData().getCoupons().size() == 0) {
						mRecyclerView.setNoMore(true);
						mRecyclerView.setPullRefreshEnabled(true);
						page--;
					}
					mProgressLayout.showContent();
				} else {
					ToastUtils.showToast(CouponReceiveActivity.this, "网络异常 :)" + response.getMsg());
					page--;
					mRecyclerView.setPullRefreshEnabled(true);
					mRecyclerView.loadMoreComplete();
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				mRecyclerView.loadMoreComplete();
				mRecyclerView.setPullRefreshEnabled(true);
				if (call.isCanceled()) {
					LogUtils.i("我进入到加载更多cancel了");
					call.cancel();
				} else if (page != 1) {
					LogUtils.i("加载更多的Log");
					ToastUtils.showToast(CouponReceiveActivity.this, "网络故障,请稍后再试");
					page--;
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		OkHttpUtils.getInstance().cancelTag(this);
		//数据是使用Intent返回
		Intent intent = new Intent();
		//把返回数据存入Intent
//		intent.putExtra("result", "My name is linjiqin");
		//设置返回数据
		CouponReceiveActivity.this.setResult(RESULT_OK, intent);
		super.onDestroy();
	}
}
