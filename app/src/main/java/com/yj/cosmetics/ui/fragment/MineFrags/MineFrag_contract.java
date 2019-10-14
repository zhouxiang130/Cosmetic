package com.yj.cosmetics.ui.fragment.MineFrags;

import com.yj.cosmetics.base.BasePresenter;
import com.yj.cosmetics.base.BaseView;
import com.yj.cosmetics.model.MineEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.util.UserUtils;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public interface MineFrag_contract {

	interface View extends BaseView {

		void isNoLogin();

		void setLoginName(boolean isLoginName);

		void isLoginHeadImg(boolean isLoginHeadImg);

		void setDatas(MineEntity.MineData data);

		void setTel(boolean b, String replace);

		void setMineNewNum(boolean b, NormalEntity response);

		void showToast(String s);

		void setCallPhone(String serviceTel);
	}

	interface Presenter extends BasePresenter {

		void setMineHeadInfo(UserUtils mUtils);
	}
}
