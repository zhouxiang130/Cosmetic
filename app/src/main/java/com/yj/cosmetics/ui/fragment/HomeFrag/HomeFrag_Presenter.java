package com.yj.cosmetics.ui.fragment.HomeFrag;

import com.yj.cosmetics.ui.activity.mineCoupon.MineCoupon_Contract;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public class HomeFrag_Presenter implements MineCoupon_Contract.Presenter {

	private HomeFrag_Contract.View mView = null;

	public HomeFrag_Presenter(HomeFrag_Contract.View view) {
		this.mView = view;
	}

	@Override
	public void subscribe() {

	}

	@Override
	public void unsubscribe() {

	}


//	private void doAsyncGetData() {
//		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/homePage.act")
//				.tag(getActivity()).build().execute(new Utils.MyResultCallback<HomeEntity>() {
//			@Override
//			public HomeEntity parseNetworkResponse(Response response) throws Exception {
//				String json = response.body().string().trim();
//				LogUtils.i("doAsyncGetData -- json的值" + json);
//				return new Gson().fromJson(json, HomeEntity.class);
//			}
//
//			@Override
//			public void onResponse(HomeEntity response) {
//				if (response != null && response.getCode().equals(response.HTTP_OK)) {
//					//返回值为200 说明请求成功
//					if (response.getData() != null) {
//						data = response.getData();
//						dealWithTimer();
//						HomeEntity.HomeData.HomeClassify homeClassify = new HomeEntity.HomeData.HomeClassify();
//						homeClassify.setClassify_name("全部");
//						data.getProductClassifyList().add(homeClassify);
//						mAdapter.setData(data);
//						doAsyncGetList();
//						mProgressLayout.showContent();
//					} else {
//						mProgressLayout.showNone(new View.OnClickListener() {
//							@Override
//							public void onClick(View view) {
//							}
//						});
//					}
//				} else {
//					LogUtils.i("我挂了" + response.getMsg());
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
//					mRecyclerView.refreshComplete();
//					tempY = 0;
//				}
//			}
//
//			@Override
//			public void onError(Call call, Exception e) {
//				super.onError(call, e);
//				mRecyclerView.refreshComplete();
//				LogUtils.i("我故障了" + e);
//				if (call.isCanceled()) {
//					call.cancel();
//				} else {
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
//				}
//				tempY = 0;
//			}
//		});
//	}
}
