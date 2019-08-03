package com.yj.cosmetics.ui.activity;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
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

	@BindView(R.id.tablayout)
	TabLayout tabLayout;
	@BindView(R.id.tv_mine_store_num)
	TextView tvStoreNum;
	@BindView(R.id.tv_mine_free_sayest)
	TextView tvMoney;
	@BindView(R.id.tv_account_pic)
	TextView tvAccountPic;
	@BindView(R.id.tv_total_store)
	TextView tvTotalStore;
	@BindView(R.id.viewpager)
	NoScrollViewPager mViewpager;
	private List<String> mTitle = new ArrayList<>();
	private List<Fragment> mFragment = new ArrayList<>();

	@Override
	protected int getContentView() {
		return R.layout.activity_account_list;
	}

	@Override
	protected void initView() {
		setTitleText("账单记录");
	}

	@Override
	protected void initData() {
		mTitle.add("我的消费");
		mTitle.add("我的免单");
		for (int i = 0; i < mTitle.size(); i++) {
			mFragment.add(MyAccountListFrags.instant(i));
		}
		MineOrderTabAdapter adapter = new MineOrderTabAdapter(MyFreeOrderActivity.this.getSupportFragmentManager(), mTitle, mFragment);
		mViewpager.setAdapter(adapter);
		//为TabLayout设置ViewPager
		tabLayout.setupWithViewPager(mViewpager);
		tabLayout.setTabsFromPagerAdapter(adapter);
	}


	@Override
	public void process(String str, String s,int type) {
		tvMoney.setText(str);
		tvStoreNum.setText(s);
		if (type==0) {
			tvAccountPic.setText("累计消费（元）");
			tvTotalStore.setText("累计店铺（个）");
		}else if (type==1){
			tvAccountPic.setText("累计免单（元）");
			tvTotalStore.setText("已免单（个）");
		}
	}
}
