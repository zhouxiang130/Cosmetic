package com.yj.cosmetics.ui.activity.mineAccount;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.base.BasePresenter;
import com.yj.cosmetics.base.BaseView;
import com.yj.cosmetics.model.AccountEntity;
import com.yj.cosmetics.model.AccountProfitEntity;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.widget.ProgressLayout;

import java.util.List;

/**
 * Created by Administrator on 2018/6/5 0005.
 */

public interface MineAccount_contract {

	interface View extends BaseView {

		void initDialog();

		void showToast(String o);

		void setDatas(AccountEntity.AccountData data);

		void dismissDialog();

		void notifyDataSetChanged();


		void pageNumReduce();
	}

	interface Presenter extends BasePresenter {


		void doAsyncGetData(UserUtils mUtils);

		void doRefreshData(int pageNum, ProgressLayout mProgressLayout, XRecyclerView mRecyclerView, List<AccountProfitEntity.AccountProfitData> mList);

		void doRequestData(int pageNum, ProgressLayout mProgressLayout, XRecyclerView mRecyclerView, List<AccountProfitEntity.AccountProfitData> mList);
	}
}
