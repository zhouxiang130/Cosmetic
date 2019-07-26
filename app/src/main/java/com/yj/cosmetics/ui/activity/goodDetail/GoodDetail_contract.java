package com.yj.cosmetics.ui.activity.goodDetail;

import com.yj.cosmetics.base.BasePresenter;
import com.yj.cosmetics.base.BaseView;

/**
 * Created by Administrator on 2018/7/17 0017.
 */

public interface GoodDetail_contract {


	interface View extends BaseView {

	}

	interface Presenter extends BasePresenter {


		void doAsyncGetDetial();
	}
}
