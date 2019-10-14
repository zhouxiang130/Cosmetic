package com.yj.cosmetics.ui.fragment.MineCouponDetail;

import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.CouponListDataEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.ProgressLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class MineCouponDetail_presenter implements MineCouponDetail_contract.Presenter {
	private MineCouponDetail_contract.View mView = null;
	private int pageNum = 1;

	public MineCouponDetail_presenter(MineCouponDetail_contract.View view) {
		this.mView = view;
	}

	@Override
	public void subscribe() {

	}

	@Override
	public void unsubscribe() {

	}

	@Override
	public void doRefreshData(final ProgressLayout mProgressLayout,
	                          UserUtils mUtils,
	                          final XRecyclerView mRecyclerView,
	                          final List<CouponListDataEntity.DataBean.ListBean> mList,
	                          String flag) {
		pageNum = 1;
		mProgressLayout.showContent();
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("pageNum", pageNum + "");
		map.put("cosureState", flag + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/userCouponPage").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<CouponListDataEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.e("onError: " + e);
				mRecyclerView.refreshComplete();
				if (call.isCanceled()) {
					call.cancel();
				} else {
					mProgressLayout.showNetError(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (mList != null && !mList.isEmpty()) {
								mList.clear();
								mView.adapterNotifyDataChanged();

							}
							mRecyclerView.refresh();
						}
					});
				}
			}

			@Override
			public CouponListDataEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("MineCouponDetail_presenter json的值" + json);
				return new Gson().fromJson(json, CouponListDataEntity.class);
			}

			@Override
			public void onResponse(CouponListDataEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					if (response.getData().getCountUserPossession() != 0) {
						mView.initDatas(response.getData().getCountUserPossession());
					}

					if (response.getData().getList() != null) {
						if (response.getData().getList().size() != 0) {
							mList.clear();
							mList.addAll(response.getData().getList());
							mView.adapterNotifyDataChanged();
							mProgressLayout.showContent();
						} else if (response.getData().getList().size() == 0) {
							mProgressLayout.showNone(new View.OnClickListener() {
								@Override
								public void onClick(View view) {
								}
							});
						}
					} else {
						mProgressLayout.showNone(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
							}
						});
					}
				} else {
//                    ToastUtils.showToast(getActivity(),"故障"+response.getMsg());
					mProgressLayout.showNetError(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (mList != null && !mList.isEmpty()) {
								mList.clear();
								mView.adapterNotifyDataChanged();
							}
							mRecyclerView.refresh();
						}
					});
				}
				mRecyclerView.refreshComplete();
			}
		});
	}

	@Override
	public void doRequestData(final ProgressLayout mProgressLayout,
	                          UserUtils mUtils,
	                          final XRecyclerView mRecyclerView,
	                          final List<CouponListDataEntity.DataBean.ListBean> mList,
	                          String flag) {
		pageNum++;
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("pageNum", pageNum + "");
		map.put("cosureState", flag + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/userCouponPage").tag(this)
				.addParams("data", URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<CouponListDataEntity>() {
			@Override
			public CouponListDataEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("MineCouponDetail_presenter json的值" + json);
				return new Gson().fromJson(json, CouponListDataEntity.class);
			}

			@Override
			public void onResponse(CouponListDataEntity info) {
				if (info != null && info.HTTP_OK.equals(info.getCode())) {
					if (info.getData().getList() != null) {
						if (info.getData().getList().size() != 0) {
							mList.addAll(info.getData().getList());
							mView.initDatas(info.getData().getCountUserPossession());
							mView.adapterNotifyDataChanged();
							mRecyclerView.setPullRefreshEnabled(true);
							mRecyclerView.loadMoreComplete();
						} else if (info.getData().getList().size() == 0) {
							mRecyclerView.setNoMore(true);
							mRecyclerView.setPullRefreshEnabled(true);
							pageNum--;
						}
						mProgressLayout.showContent();
					}
				} else {
					mView.showToast("网络异常");
					pageNum--;
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
				} else if (pageNum != 1) {
					LogUtils.i("加载更多的Log");
					mView.showToast("网络故障,请稍后再试");
					pageNum--;
				}
			}
		});
	}
}
