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
import com.yj.cosmetics.model.InfoCenterEntity;
import com.yj.cosmetics.ui.adapter.InfoCenterAdapter;
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
 * Created by Suo on 2018/3/15.
 */

public class InfoCenterActivity extends BaseActivity {

	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;

	InfoCenterAdapter mAdapter;

	private List<InfoCenterEntity.InfoCenterData> mList;

	private int pageNum = 1;

	@Override
	protected int getContentView() {
		return R.layout.activity_info_center;
	}

	@Override
	protected void initView() {

		setTitleText("消息中心");
		mList = new ArrayList<>();
		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(layoutManager);
		mAdapter = new InfoCenterAdapter(this, mList);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
		mAdapter.setOnItemClickListener(new InfoCenterAdapter.SpendDetialClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
				Intent intent = new Intent(InfoCenterActivity.this, InfoDetailActivity.class);
				intent.putExtra("mid", mList.get(postion - 1).getMid());
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				pageNum = 1;
				new Handler().postDelayed(new Runnable() {
					public void run() {
						doRefreshData();
					}
				}, 500);
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

	@Override
	protected void initData() {

	}


	private void doRefreshData() {
		mProgressLayout.showContent();
		Map<String, String> map = new HashMap<>();
		map.put("pageNum", pageNum + "");
		map.put("userId", mUtils.getUid());
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/searchMessage")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<InfoCenterEntity>() {
			@Override
			public InfoCenterEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, InfoCenterEntity.class);
			}

			@Override
			public void onResponse(InfoCenterEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData().size() != 0) {
						mList.clear();
						mList.addAll(response.getData());
						mAdapter.notifyDataSetChanged();
						mProgressLayout.showContent();
					} else if (response.getData().size() == 0) {
						mProgressLayout.showNoInfo(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
							}
						});
					}

				} else {
					ToastUtils.showToast(InfoCenterActivity.this, "网络故障 " + response.getMsg());
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
				mRecyclerView.refreshComplete();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				mRecyclerView.refreshComplete();
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

			}
		});
	}

	private void loadMoreData() {
		Map<String, String> map = new HashMap<>();
		map.put("pageNum", pageNum + "");
		map.put("userId", mUtils.getUid());
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/searchMessage")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<InfoCenterEntity>() {
			@Override
			public InfoCenterEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, InfoCenterEntity.class);
			}

			@Override
			public void onResponse(InfoCenterEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData().size() != 0) {
						mList.addAll(response.getData());
						mAdapter.notifyDataSetChanged();
						mRecyclerView.loadMoreComplete();
					} else if (response.getData().size() == 0) {
						mRecyclerView.setNoMore(true);
						pageNum--;
					}
					mProgressLayout.showContent();
				} else {
					ToastUtils.showToast(InfoCenterActivity.this, "网络异常 :)" + response.getMsg());
					pageNum--;
					mRecyclerView.loadMoreComplete();
				}
				mRecyclerView.refreshComplete();
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
					ToastUtils.showToast(InfoCenterActivity.this, "网络故障,请稍后再试");
					pageNum--;
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		OkHttpUtils.getInstance().cancelTag(this);
		super.onDestroy();
	}
}
