package com.yj.cosmetics.ui.fragment.MineCouponDetail;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.base.BasePresenter;
import com.yj.cosmetics.base.BaseView;
import com.yj.cosmetics.model.CouponListDataEntity;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.widget.ProgressLayout;

import java.util.List;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public interface MineCouponDetail_contract {


	interface View extends BaseView {
		void adapterNotifyDataChanged();

		void showToast(String s);

		void initDatas(int UserPossession);
	}

	interface Presenter extends BasePresenter {
		void doRefreshData(ProgressLayout mProgressLayout, UserUtils mUtils, XRecyclerView mRecyclerView, List<CouponListDataEntity.DataBean.ListBean> mList, String flag);

		void doRequestData(ProgressLayout mProgressLayout, UserUtils mUtils, XRecyclerView mRecyclerView, List<CouponListDataEntity.DataBean.ListBean> mList, String flag);
	}

}
