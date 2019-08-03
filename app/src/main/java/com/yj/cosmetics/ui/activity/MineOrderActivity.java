package com.yj.cosmetics.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.RelativeLayout;

import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Variables;
import com.yj.cosmetics.ui.adapter.MineOrderTabAdapter;
import com.yj.cosmetics.ui.fragment.MineOrderFrag;
import com.yj.cosmetics.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Suo on 2017/4/28.
 */

public class MineOrderActivity extends BaseActivity {
	@BindView(R.id.mine_order_tablayout)
	TabLayout tabLayout;
	@BindView(R.id.viewpager)
	ViewPager mViewpager;
	@BindView(R.id.title_view)
	View vLine;
	@BindView(R.id.mine_order_title)
	RelativeLayout rlTitle;
	@BindView(R.id.mine_order_vline)
	View vLine2;
	OrderListReceiver orderListReceiver;
	private List<String> mTitle = new ArrayList<String>();
	private List<Fragment> mFragment = new ArrayList<Fragment>();
	private DynamicReceiver dynamicReceiver;

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_order;
	}

	@Override
	protected void initView() {
		setTitleText("我的订单");
		vLine.setVisibility(View.GONE);
		mTitle.add("全部");
		mTitle.add("待付款");
		mTitle.add("待发货");
		mTitle.add("待收货");
		mTitle.add("待评价");
		//实例化IntentFilter对象
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.yj.robust.receiver_one");
		dynamicReceiver = new DynamicReceiver();
		//注册广播接收
		registerReceiver(dynamicReceiver, filter);
		for (int i = 0; i < mTitle.size(); i++) {
			mFragment.add(MineOrderFrag.instant(i - 1));
		}
		MineOrderTabAdapter adapter = new MineOrderTabAdapter(getSupportFragmentManager(), mTitle, mFragment);
		mViewpager.setAdapter(adapter);
		//为TabLayout设置ViewPager
		tabLayout.setupWithViewPager(mViewpager);
		//使用ViewPager的适配器
		//忘了这句干啥的了. 如果使用过程中有问题.应该就是这句导致的.
		tabLayout.setTabsFromPagerAdapter(adapter);
	}

	//通过继承 BroadcastReceiver建立动态广播接收器
	class DynamicReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//接收到广播
			if (intent.getStringExtra("flag") != null) {
				int CurrentItems = Integer.parseInt(intent.getStringExtra("flag"));
				mViewpager.setCurrentItem(CurrentItems);
				doFragRefresh();
				return;
			}
			mViewpager.setCurrentItem(mTitle.size() - 1);
		}
	}

	@Override
	protected void initData() {
		showShadow();
		mViewpager.setCurrentItem(getIntent().getIntExtra("page", 0));
	}

	private void showShadow() {
		if (Build.VERSION.SDK_INT >= 21) {
			rlTitle.setElevation(getResources().getDimension(R.dimen.dis2));
			rlTitle.setOutlineProvider(ViewOutlineProvider.BOUNDS);
			vLine2.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		LogUtils.i("resultCode" + resultCode);
		if (requestCode == Variables.REFRESH_ORDER_LIST && resultCode == Variables.REFRESH_ORDER_LIST) {
			LogUtils.i("我进入resultCode相等了");
			if (data != null && !TextUtils.isEmpty(data.getStringExtra("refresh"))) {
				LogUtils.i("我进入refresh了");
				doFragRefresh();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(dynamicReceiver);
	}

	public void doFragRefresh() {
		MineOrderFrag frag = (MineOrderFrag) mFragment.get(mViewpager.getCurrentItem());
		if (frag != null) {
			frag.doRefresh();
		}
	}

	public void registBroadcastReceiver() {
		//new出上边定义好的BroadcastReceiver
		orderListReceiver = new OrderListReceiver();
//实例化过滤器并设置要过滤的广播
		LogUtils.i("isInital的值是xxxxx" + orderListReceiver.isInitialStickyBroadcast());
		IntentFilter intentFilter = new IntentFilter("com.example.orderlistrefresh");
//注册广播
		registerReceiver(orderListReceiver, intentFilter);
//        mContext.registerReceiver(orderListReceiver,intentFilter,
//                "android.permission.RECEIVE_SMS", null);
		LogUtils.i("isInital的值" + orderListReceiver.isInitialStickyBroadcast());
	}

	public class OrderListReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//在这里可以写相应的逻辑来实现一些功能
			//可以从Intent中获取数据、还可以调用BroadcastReceiver的getResultData()获取数据
//            ToastUtils.showToast(context,"我收到广播了");
			LogUtils.i("我收到广播了");
			if (intent != null && !TextUtils.isEmpty(intent.getStringExtra("state")) && intent.getStringExtra("state").equals("success")) {
				LogUtils.i("我刷新了");
				doFragRefresh();
			}
			unregisterReceiver(this);
		}
	}

}
