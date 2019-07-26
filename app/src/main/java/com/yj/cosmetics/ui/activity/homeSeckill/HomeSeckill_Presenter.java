package com.yj.cosmetics.ui.activity.homeSeckill;

/**
 * Created by Administrator on 2018/6/12 0012.
 */

public class HomeSeckill_Presenter  implements HomeSeckill_contract.Presenter{

	private HomeSeckill_contract.View mView = null;

	public HomeSeckill_Presenter(HomeSeckill_contract.View view) {
		this.mView = view;
	}

	@Override
	public void subscribe() {

	}

	@Override
	public void unsubscribe() {

	}
}
