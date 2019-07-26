package com.yj.cosmetics.ui.activity.goodDetail;

import com.yj.cosmetics.ui.activity.goodDetail.GoodDetail_contract.Presenter;

/**
 * Created by Administrator on 2018/7/17 0017.
 */

public class GoodDetail_presenter implements Presenter {


	private GoodDetail_contract.View mView = null;

	public GoodDetail_presenter(GoodDetail_contract.View view) {
		this.mView = view;
	}

	@Override
	public void subscribe() {

	}

	@Override
	public void unsubscribe() {

	}


	@Override
	public void doAsyncGetDetial() {

	}
}
