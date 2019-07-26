package com.yj.cosmetics.ui.activity.splash;

import android.content.Context;

import com.yj.cosmetics.base.BasePresenter;
import com.yj.cosmetics.base.BaseView;
import com.yj.cosmetics.model.UpdateEntity;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.widget.Dialog.UpdateDialog;

/**
 * Created by Administrator on 2018/5/23 0023.
 */

public interface Splash_Contract {

	interface View extends BaseView {
		void setUpdateData(UpdateEntity.UpdateData data, String newVersion);

		void isUpdate();

		void isForceUpdate();

		void dismissDialog();

		void changePage();

		boolean alertDialog();
	}

	interface Presenter extends BasePresenter {

		void doAsyncTaskUpdate(UserUtils mUtils);

		boolean isWifi(Context context);

		void initDialog(UpdateDialog updateDialog);

	}

}
