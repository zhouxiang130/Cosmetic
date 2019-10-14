package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.GoodsCommentEntity;
import com.yj.cosmetics.ui.adapter.JudgeAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.ProgressLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2018/3/17.
 */

public class JudgeActivity extends BaseActivity {
	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	@BindView(R.id.title_view)
	View vLine;
	@BindView(R.id.title_layout)
	LinearLayout llTop;
	JudgeAdapter mAdapter;
	List<GoodsCommentEntity.GoodsCommentData.GoodsCommentList> mList;
	private String proId, sproductId;
	private int page = 1;

	@Override
	protected int getContentView() {
		return R.layout.activity_judge;
	}

	@Override
	protected void initView() {
		setTitleText("查看评价");
		setTitleText(getIntent().getStringExtra("title"));
		proId = getIntent().getStringExtra("proId");
		sproductId = getIntent().getStringExtra("sproductId");
		mList = new ArrayList<>();
		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
		mAdapter = new JudgeAdapter(this, mList);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(new JudgeAdapter.SpendDetialClickListener() {
			@Override
			public void onItemClick(View view, int postion, int postions) {
				LogUtils.e("onItemClick: " + postions);
				Intent intent = new Intent(JudgeActivity.this, BigImageActivity.class);
				intent.putExtra("postion", postion + "");
				intent.putExtra("postions", postions + "");
				intent.putExtra("bigImg_list", (Serializable) mList.get(postion - 1).getCommentImg());
				startActivity(intent);
			}
		});
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

	@Override
	protected void showShadow1() {
//		vLine.setVisibility(View.GONE);
//		llTop.setBackgroundColor(getResources().getColor(R.color.CF7_F9_FA));
	}

	private void doRefresh() {
		mProgressLayout.showContent();
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("proId", proId);
		if (!TextUtils.isEmpty(sproductId)) {
			map.put("sproductId", sproductId);
		}
		map.put("pageNum", page + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/proCommentPageList")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<GoodsCommentEntity>() {
			@Override
			public GoodsCommentEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, GoodsCommentEntity.class);
			}

			@Override
			public void onResponse(GoodsCommentEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData().getCommArray().size() != 0) {
						mList.clear();
						mList.addAll(response.getData().getCommArray());
						mAdapter.notifyDataSetChanged();
						mProgressLayout.showContent();
					} else if (response.getData().getCommArray().size() == 0) {
						mProgressLayout.showNoComments(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
							}
						});
					}
				} else {
					ToastUtils.showToast(JudgeActivity.this, "网络故障 " + response.getMsg());
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
				ToastUtils.showToast(JudgeActivity.this, "网络故障,请稍后再试");
			}
		});
	}

	private void doAsyncLoadMore() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("proId", proId);
		map.put("pageNum", page + "");
		if (!TextUtils.isEmpty(sproductId)) {
			map.put("sproductId", sproductId);
		}
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/proCommentPageList")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<GoodsCommentEntity>() {
			@Override
			public GoodsCommentEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, GoodsCommentEntity.class);
			}

			@Override
			public void onResponse(GoodsCommentEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData().getCommArray().size() != 0) {
						mList.addAll(response.getData().getCommArray());
						mAdapter.notifyDataSetChanged();
						mRecyclerView.setPullRefreshEnabled(true);
						mRecyclerView.loadMoreComplete();
					} else if (response.getData().getCommArray().size() == 0) {
						mRecyclerView.setNoMore(true);
						mRecyclerView.setPullRefreshEnabled(true);
						page--;
					}
					mProgressLayout.showContent();
				} else {
					ToastUtils.showToast(JudgeActivity.this, "网络异常 :)" + response.getMsg());
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
					ToastUtils.showToast(JudgeActivity.this, "网络故障,请稍后再试");
					page--;
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
