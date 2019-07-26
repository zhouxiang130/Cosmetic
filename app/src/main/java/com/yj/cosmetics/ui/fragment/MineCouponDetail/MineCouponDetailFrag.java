package com.yj.cosmetics.ui.fragment.MineCouponDetail;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.LazyLoadFragment;
import com.yj.cosmetics.model.CouponListDataEntity;
import com.yj.cosmetics.ui.activity.couponReceive.CouponReceiveActivity;
import com.yj.cosmetics.ui.adapter.MineCouponDetailAdapter;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.widget.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Suo on 2018/3/24.
 */

public class MineCouponDetailFrag extends LazyLoadFragment implements MineCouponDetail_contract.View {

	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;

	@BindView(R.id.mine_coupon_num)
	TextView mineCouponNum;
	@BindView(R.id.mine_ll_coupon)
	LinearLayout mineLlCoupon;

	MineCouponDetailAdapter mAdapter;
	List<CouponListDataEntity.DataBean.ListBean> mList;
	private int flag;
	private String state = null;

	private MineCouponDetail_contract.Presenter couponPresenter = new MineCouponDetail_presenter(this);

	public static MineCouponDetailFrag instant(int flag) {
		MineCouponDetailFrag fragment = new MineCouponDetailFrag();
		fragment.flag = flag;
		return fragment;
	}

	@Override
	protected int setContentView() {
		return R.layout.fragment_mine_coupon_list;
	}

	@Override
	protected void initView() {
		mList = new ArrayList<>();
//      mDialog = new CustomProgressDialog(getActivity());
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
		mAdapter = new MineCouponDetailAdapter(getActivity(), mList, flag, mUtils.getUid());
		mRecyclerView.setAdapter(mAdapter);

		switch (flag) {
			case 0:
				state = "";
//				mineLlCoupon.setVisibility(View.VISIBLE);
				break;
			case 1:
				state = "3";
//				mineLlCoupon.setVisibility(View.GONE);
				break;
			case 2:
				state = "4";
//				mineLlCoupon.setVisibility(View.GONE);
				break;
		}

		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						couponPresenter.doRefreshData(mProgressLayout, mUtils, mRecyclerView, mList, state);
					}
				}, 500);
			}

			@Override
			public void onLoadMore() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						couponPresenter.doRequestData(mProgressLayout, mUtils, mRecyclerView, mList, state);
						mRecyclerView.setPullRefreshEnabled(false);
					}
				}, 500);
			}
		});
		mRecyclerView.refresh();
	}

	@Override
	protected void initData() {
	}

	public void doRefresh() {
		if (mRecyclerView != null) {
			mRecyclerView.refresh();
		}
	}


	@Override
	protected void lazyLoad() {
	}

	@OnClick(R.id.mine_ll_coupon)
	public void onViewClicked() {
		Intent intent = new Intent(getActivity(), CouponReceiveActivity.class);
		startActivityForResult(intent, 100);
	}


	@Override
	public void adapterNotifyDataChanged() {
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void showToast(String s) {
		ToastUtils.showToast(getActivity(), s);
	}

	@Override
	public void initDatas(int UserPossession) {

		Log.i(TAG, "initDatas: " + UserPossession);

		if (UserPossession != 0) {
			if (flag == 0) {
				mineLlCoupon.setVisibility(View.VISIBLE);
				mineCouponNum.setText("您有 " + UserPossession + " 张优惠券待领取");
			} else {
				mineLlCoupon.setVisibility(View.GONE);
			}
		} else {
			mineLlCoupon.setVisibility(View.GONE);
		}
	}
}
