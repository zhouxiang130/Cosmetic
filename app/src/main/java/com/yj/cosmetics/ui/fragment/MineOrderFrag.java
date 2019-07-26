package com.yj.cosmetics.ui.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.LazyLoadFragment;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineOrderEntity;
import com.yj.cosmetics.ui.activity.MineOrderActivity;
import com.yj.cosmetics.ui.adapter.MineOrderListAdapter;
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
 * Created by Suo on 2016/12/12.
 */

public class MineOrderFrag extends LazyLoadFragment {
	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;

	MineOrderListAdapter mAdapter;
	List<MineOrderEntity.DataBean.UserOrderMapsBean> mList;
	private int flag;
	private int pageNum = 1;
//    CustomProgressDialog mDialog;


	public static MineOrderFrag instant(int flag) {
		MineOrderFrag fragment = new MineOrderFrag();
		fragment.flag = flag;
		return fragment;
	}

	@Override
	protected int setContentView() {
		return R.layout.fragment_mine_order;
	}

	@Override
	protected void initView() {
		mList = new ArrayList<>();
//        mDialog = new CustomProgressDialog(getActivity());
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
		mAdapter = new MineOrderListAdapter((MineOrderActivity) getActivity(), mList, mUtils, flag);
		mRecyclerView.setAdapter(mAdapter);

//        mRecyclerView.refresh();
//        MyApplication.isReserve = false;
	}

	@Override
	protected void initData() {
	}

	//@TODO　　
	@Override
	protected void lazyLoad() {
		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				pageNum = 1;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						doRefreshData();
					}
				}, 500);
			}

			@Override
			public void onLoadMore() {
				pageNum++;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						loadMoreData();
						mRecyclerView.setPullRefreshEnabled(false);
					}
				}, 500);
			}
		});
		mRecyclerView.refresh();
	}

	private void doRefreshData() {
		mProgressLayout.showContent();
		Map<String, String> map = new HashMap<>();
		map.put("usersId", mUtils.getUid());
		map.put("pageNum", pageNum + "");



		if (flag >= 0) {
			map.put("orderState", flag + "");
		}

		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/userOrders")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<MineOrderEntity>() {

			@Override
			public MineOrderEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("userOrders --- json的值" + json);
				return new Gson().fromJson(json, MineOrderEntity.class);
			}

			@Override
			public void onResponse(MineOrderEntity response) {
//                dismissDialog();
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					mList.clear();
					if (response.getData() != null) {
						if (response.getData().getUserOrderMaps().size() != 0) {
							mList.addAll(response.getData().getUserOrderMaps());
							mAdapter.notifyDataSetChanged();
							mProgressLayout.showContent();
						} else if (response.getData().getUserOrderMaps().size() == 0) {
							mProgressLayout.showNone(new View.OnClickListener() {
								@Override
								public void onClick(View view) {
								}
							});
						}
					}

				} else {
					ToastUtils.showToast(getActivity(), "请求失败 :)" + response.getMsg());
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
				mRecyclerView.refreshComplete();
				LogUtils.i("故障" + e);
			}
		});
	}

	private void loadMoreData() {
		Map<String, String> map = new HashMap<>();
		map.put("usersId", mUtils.getUid());
		map.put("pageNum", pageNum + "");
		map.put("orderState", flag + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/userOrders")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<MineOrderEntity>() {

			@Override
			public MineOrderEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("userOrders json的值" + json);
				return new Gson().fromJson(json, MineOrderEntity.class);
			}

			@Override
			public void onResponse(MineOrderEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					if (response.getData() != null) {
						if (response.getData().getUserOrderMaps().size() != 0) {
							mList.addAll(response.getData().getUserOrderMaps());
							mAdapter.notifyDataSetChanged();
							mRecyclerView.loadMoreComplete();
						} else if (response.getData().getUserOrderMaps().size() == 0) {
							mRecyclerView.setNoMore(true);
							pageNum--;
						}
					}
					mProgressLayout.showContent();
				} else {
					ToastUtils.showToast(getActivity(), "异常 :)" + response.getMsg());
					pageNum--;
					mRecyclerView.loadMoreComplete();
				}
				mRecyclerView.setPullRefreshEnabled(true);
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
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

	public void doRefresh() {
		if (mRecyclerView != null) {
			mRecyclerView.refresh();
		}
	}

	/* private void dismissDialog(){
		 if(mDialog != null){
			 mDialog.dismiss();
			 mDialog = null;
		 }
	 }*/
	@Override
	public void onDestroy() {
		super.onDestroy();
//        dismissDialog();
//        OkHttpUtils.getInstance().cancelTag(this);
	}
}
