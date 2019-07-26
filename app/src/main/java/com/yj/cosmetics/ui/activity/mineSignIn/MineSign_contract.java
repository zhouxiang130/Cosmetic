package com.yj.cosmetics.ui.activity.mineSignIn;

import com.yj.cosmetics.base.BasePresenter;
import com.yj.cosmetics.base.BaseView;
import com.yj.cosmetics.model.SignInEntity;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public interface MineSign_contract {

	interface View extends BaseView {

		void setSignInData(SignInEntity.DataBean data);
	}

	interface Presenter extends BasePresenter {

		void doAsyncGetSignIn(String uid);
	}

}
