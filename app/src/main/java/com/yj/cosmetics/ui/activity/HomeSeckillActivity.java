package com.yj.cosmetics.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.ui.adapter.MineOrderTabAdapter;
import com.yj.cosmetics.ui.fragment.HomeSeckillFrag;
import com.yj.cosmetics.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Suo on 2018/3/30.
 *
 *  好物秒杀
 */

public class HomeSeckillActivity extends BaseActivity {
	@BindView(R.id.home_seckill_tablayout)
	TabLayout tabLayout;
	@BindView(R.id.viewpager)
	ViewPager mViewpager;
	@BindView(R.id.title_view)
	View vLine;
	@BindView(R.id.home_seckill_iv_right)
	ImageView ivRight;
	@BindView(R.id.home_seckill_iv_left)
	ImageView ivLeft;
	private List<String> mTitle = new ArrayList<String>();
	private List<Fragment> mFragment = new ArrayList<Fragment>();

	@Override
	protected int getContentView() {
		return R.layout.activity_home_seckill;
	}

	@Override
	protected void initView() {
		setTitleText("好物秒杀");
		vLine.setVisibility(View.GONE);
		mTitle.add("正在疯抢");
		mTitle.add("即将开始");

		for (int i = 0; i < mTitle.size(); i++) {
			LogUtils.i("我添加了" + i);
			mFragment.add(HomeSeckillFrag.instant(i));
		}

		MineOrderTabAdapter adapter = new MineOrderTabAdapter(getSupportFragmentManager(), mTitle, mFragment);
		mViewpager.setAdapter(adapter);
		//为TabLayout设置ViewPager
		tabLayout.setupWithViewPager(mViewpager);
		//使用ViewPager的适配器
		//忘了这句干啥的了. 如果使用过程中有问题.应该就是这句导致的.
		tabLayout.setTabsFromPagerAdapter(adapter);
		mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				LogUtils.i("我进入scrolled了==" + position + "=======offSet====" + positionOffset + "=====pixels===" + positionOffsetPixels);
				if (position == 0) {
					if (positionOffset < 0.5) {
						ivLeft.setVisibility(View.VISIBLE);
						ivRight.setVisibility(View.GONE);
					}
				} else {
					if (positionOffset > 0.5 || positionOffset == 0) {
						ivLeft.setVisibility(View.GONE);
						ivRight.setVisibility(View.VISIBLE);
					}
				}
				if (positionOffset < 0.5 && positionOffset > 0) {
					if (position == 1) {
						ivLeft.setVisibility(View.VISIBLE);
						ivRight.setVisibility(View.GONE);
					}
				} else if (positionOffset > 0.5) {
					if (position == 0) {
						ivLeft.setVisibility(View.GONE);
						ivRight.setVisibility(View.VISIBLE);
					}
				} else if (positionOffset == 0) {
					LogUtils.i("我进入等于0 了");
					if (position == 0) {
						ivLeft.setVisibility(View.VISIBLE);
						ivRight.setVisibility(View.GONE);
					} else {
						ivLeft.setVisibility(View.GONE);
						ivRight.setVisibility(View.VISIBLE);
					}
				}
			}

			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
//                LogUtils.i("我进入stat=="+state);
			}
		});
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void showShadow1() {
	}
}
