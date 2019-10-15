package com.yj.cosmetics.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.LazyLoadFragment;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineCollectEntity;
import com.yj.cosmetics.ui.activity.MainActivity;
import com.yj.cosmetics.ui.adapter.MineCollectionAdapter;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.CustomXrecyclerView.SwipeHorXRecyclerView;
import com.yj.cosmetics.widget.ProgressLayout;
import com.yj.cosmetics.widget.SwipeItemLayout.SwipeItemLayout;
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

public class MineCollectFrag extends LazyLoadFragment {

	@BindView(R.id.xrecyclerView)
	SwipeHorXRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;

	MineCollectionAdapter mAdapter;
	List<MineCollectEntity.DataBean.ListBean> mList;
	private int flag;
	private int pageNum = 1;
//    CustomProgressDialog mDialog;


	public static MineCollectFrag instant(int flag) {
		MineCollectFrag fragment = new MineCollectFrag();
		fragment.flag = flag;
		return fragment;
	}

	@Override
	protected int setContentView() {
		return R.layout.fragment_mine_collect;
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
		mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
		mAdapter = new MineCollectionAdapter(getContext(), mList, mUtils, flag);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(new MineCollectionAdapter.SpendDetialClickListener() {
			@Override
			public void onItemClick(View view, int postion, int flags) {
				if (flags == 1) {
					IntentUtils.IntentToStoreDetial(getContext(), mList.get(postion - 1).getShopId());
				} else {
					if (mList.get(postion - 1).getSproductId() != null) {
						IntentUtils.IntentToGoodsDetial(getContext(), mList.get(postion - 1).getProductId(),mList.get(postion - 1).getSproductId() );
					}else {
						IntentUtils.IntentToGoodsDetial(getContext(), mList.get(postion - 1).getProductId());
					}
				}
			}
		});
//        mRecyclerView.refresh();
//        MyApplication.isReserve = false;
	}

	@Override
	protected void initData() {
	}

	//@TODO　　
	@Override
	protected void lazyLoad() {

		mRecyclerView.setLoadingListener(new SwipeHorXRecyclerView.LoadingListener() {
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

		map.put("type", flag + "");
		map.put("userId", mUtils.getUid());
		map.put("pageNum", pageNum + "");


		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/productCollections")
				.tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<MineCollectEntity>() {

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
			}

			@Override
			public MineCollectEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("userCollection json的值" + json);
				return new Gson().fromJson(json, MineCollectEntity.class);
			}

			@Override
			public void onResponse(MineCollectEntity response) {

				if (response != null && response.HTTP_OK.equals(response.getCode())) {

					if (response.getData().getList() != null) {
						if (response.getData().getList().size() != 0) {
							mList.clear();
							mList.addAll(response.getData().getList());
							mAdapter.notifyDataSetChanged();
							mProgressLayout.showContent();
						} else if (response.getData().getList().size() == 0) {
							mProgressLayout.showNoCollection(new View.OnClickListener() {
								@Override
								public void onClick(View view) {
									Intent intent = new Intent(getContext(), MainActivity.class);
									intent.putExtra("page", "0");
									startActivity(intent);
								}
							});
						}

					} else {
						mProgressLayout.showNoCollection(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								Intent intent = new Intent(getContext(), MainActivity.class);
								if (flag == 1) {
									intent.putExtra("page", "2");
								} else {
									intent.putExtra("page", "0");
								}
								getContext().startActivity(intent);
							}
						});
					}

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
			}
		});
	}

	private void loadMoreData() {
		Map<String, String> map = new HashMap<>();
		map.put("type", flag + "");
		map.put("userId", mUtils.getUid());

		map.put("pageNum", pageNum + "");

		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/productCollections")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<MineCollectEntity>() {
			@Override
			public MineCollectEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("userCollection json的值" + json);
				return new Gson().fromJson(json, MineCollectEntity.class);
			}

			@Override
			public void onResponse(MineCollectEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData().getList().size() != 0) {
						mList.addAll(response.getData().getList());
						mAdapter.notifyDataSetChanged();
						mRecyclerView.loadMoreComplete();
					} else if (response.getData().getList().size() == 0) {
						mRecyclerView.setNoMore(true);
						pageNum--;
					}

				} else {
					ToastUtils.showToast(getContext(), "网络异常 :)" + response.getMsg());
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
					ToastUtils.showToast(getContext(), "网络故障,请稍后再试");
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
