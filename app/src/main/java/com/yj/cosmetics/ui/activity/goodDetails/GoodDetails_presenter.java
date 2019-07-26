package com.yj.cosmetics.ui.activity.goodDetails;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.GoodsEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/7/17 0017.s
 */

public class GoodDetails_presenter implements GoodDetails_contract.Presenter {


	private GoodDetails_contract.View mView = null;
	private GoodsEntity.GoodsData data;
	private List<String> mTitleList;
	private String newDate;

	public GoodDetails_presenter(GoodsDetailActivitys view) {
		this.mView = view;
		mTitleList = new ArrayList<>();
	}

	@Override
	public void subscribe() {

	}

	@Override
	public void unsubscribe() {

	}


	@Override
	public void doAsyncGetDetial(UserUtils mUtils, String productId, String sproductId) {

		Map<String, String> map = new HashMap<>();
		if (mUtils.isLogin() && !TextUtils.isEmpty(mUtils.getUid())) {
			map.put("userId", mUtils.getUid());
		}
		if (productId != null) {
			map.put("productId", productId);
		}
		if (sproductId != null) {
			map.put("sproductId", sproductId);
		}

		LogUtils.i("商品详情传的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/productDetails")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<GoodsEntity>() {

			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				mView.isAsynGetDetailBefore();
			}

			@Override
			public GoodsEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("productDetails - json的值" + json);
				return new Gson().fromJson(json, GoodsEntity.class);
			}

			@Override
			public void onResponse(GoodsEntity response) {

				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					data = null;
					data = response.getData();
					newDate = response.getData().getTime();
					mTitleList.add("商品");
					mTitleList.add("评论");
					if (data != null && !TextUtils.isEmpty(data.getSpiderUrl())) {
						mTitleList.add("溯源");
					}
					mTitleList.add("详情");
					mView.setData();

//					mTitleAdapter.notifyDataSetChanged();
//					mAdapter.setData(data);
//					doAsyncGetJudge();

				} else {
					mView.dismissDialog();
//					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障 " + response.getMsg());
				}


			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				mView.dismissDialog();

				LogUtils.i("网络请求失败" + e);

				if (call.isCanceled()) {
					call.cancel();
				} else {
//					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障,无法获取商品详情信息");
				}





			}
		});
	}
}
