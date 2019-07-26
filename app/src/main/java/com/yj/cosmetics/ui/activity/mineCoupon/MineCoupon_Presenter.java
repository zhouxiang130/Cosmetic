package com.yj.cosmetics.ui.activity.mineCoupon;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class MineCoupon_Presenter implements MineCoupon_Contract.Presenter {

	private MineCoupon_Contract.View mView = null;

	public MineCoupon_Presenter(MineCoupon_Contract.View view) {
		this.mView = view;

	}

	@Override
	public void subscribe() {

	}

	@Override
	public void unsubscribe() {

	}
}
