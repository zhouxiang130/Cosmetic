package com.yj.cosmetics.ui.activity.couponReceive;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class CouponReceive_presenter implements CouponReceive_contract.Presenter {

	private CouponReceive_contract.View mView = null;

	public CouponReceive_presenter(CouponReceive_contract.View view) {
		this.mView = view;
	}

	@Override
	public void subscribe() {

	}

	@Override
	public void unsubscribe() {

	}
}
