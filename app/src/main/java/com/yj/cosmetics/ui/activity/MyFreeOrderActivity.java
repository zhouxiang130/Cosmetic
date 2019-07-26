package com.yj.cosmetics.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.R;
import com.yj.cosmetics.ui.adapter.MineOrderTabAdapter;
import com.yj.cosmetics.ui.fragment.MyAccountListFrags;
import com.yj.cosmetics.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/6/15 0015.
 */

public class MyFreeOrderActivity extends BaseActivity implements MyAccountListFrags.FragmentInteraction {


	private static final String TAG = "MyFreeOrderAtivity";

	@BindView(R.id.tablayout)
	TabLayout tabLayout;

	@BindView(R.id.tv_mine_store_num)
	TextView tvStoreNum;

	@BindView(R.id.tv_mine_free_sayest)
	TextView tvMoney;

	private List<String> mTitle = new ArrayList<>();
	private List<Fragment> mFragment = new ArrayList<>();

	@BindView(R.id.viewpager)
	NoScrollViewPager mViewpager;


	@Override
	protected int getContentView() {
		return R.layout.activity_account_list;
	}

	@Override
	protected void initView() {
		setTitleText("我的免单");
	}

	@Override
	protected void initData() {
		mTitle.add("我的消费");
		mTitle.add("我的免单");
		for (int i = 0; i < mTitle.size(); i++) {
			mFragment.add(MyAccountListFrags.instant(i));
		}

		MineOrderTabAdapter adapter = new MineOrderTabAdapter
				(MyFreeOrderActivity.this.getSupportFragmentManager(), mTitle, mFragment);
		mViewpager.setAdapter(adapter);
		//为TabLayout设置ViewPager
		tabLayout.setupWithViewPager(mViewpager);
		tabLayout.setTabsFromPagerAdapter(adapter);
	}


	@Override
	public void process(String str, String s) {
		Log.i(TAG, "process: " + str + "" + s);
		tvMoney.setText(str);
		tvStoreNum.setText(s);
	}
}
