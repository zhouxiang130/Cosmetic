package com.yj.cosmetics.ui.activity;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.RelativeLayout;

import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.ui.adapter.MineOrderTabAdapter;
import com.yj.cosmetics.ui.fragment.MineCollectFrag.MineCollectFrag;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Suo on 2018/3/15.
 */

public class MineCollectionActivity extends BaseActivity {


	@BindView(R.id.mine_collect_tablayout)
	TabLayout tabLayout;
	@BindView(R.id.viewpager)
	NoScrollViewPager mViewpager;
	@BindView(R.id.title_view)
	View vLine;
	@BindView(R.id.mine_order_title)
	RelativeLayout rlTitle;
	@BindView(R.id.mine_order_vline)
	View vLine2;


	private List<String> mTitle = new ArrayList<String>();
	private List<Fragment> mFragment = new ArrayList<Fragment>();

//	@BindView(R.id.xrecyclerView)
//	SwipeHorXRecyclerView mRecyclerView;
//	@BindView(R.id.progress_layout)
//	ProgressLayout mProgressLayout;


	@Override
	protected int getContentView() {
		return R.layout.activity_mine_collection;
	}

	@Override
	protected void initView() {
		setTitleText("我的收藏");
		vLine.setVisibility(View.GONE);

		mTitle.add("店铺");
		mTitle.add("商品");

		for (int i = 0; i < mTitle.size(); i++) {
		    /*if(i == 2){
		        i++;
            }*/
			LogUtils.i("我添加了" + i);
			mFragment.add(MineCollectFrag.instant(i+1));
		}
		MineOrderTabAdapter adapter = new MineOrderTabAdapter(getSupportFragmentManager(), mTitle, mFragment);
		mViewpager.setAdapter(adapter);
		//为TabLayout设置ViewPager
		mViewpager.setScroll(false);
		tabLayout.setupWithViewPager(mViewpager);
		//使用ViewPager的适配器
		//忘了这句干啥的了. 如果使用过程中有问题.应该就是这句导致的.
		tabLayout.setTabsFromPagerAdapter(adapter);


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
	protected void onResume() {
		super.onResume();

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
