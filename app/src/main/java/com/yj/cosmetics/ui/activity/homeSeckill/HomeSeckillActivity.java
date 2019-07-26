package com.yj.cosmetics.ui.activity.homeSeckill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.ui.adapter.MineOrderTabAdapter;
import com.yj.cosmetics.ui.fragment.HomeSeckill.HomeSeckillFrag;
import com.yj.cosmetics.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Suo on 2018/3/30.
 *
 * @TODO 好物秒杀(2.0版面修改页面)
 */

public class HomeSeckillActivity extends BaseActivity implements HomeSeckill_contract.View {
	private static final String TAG = "HomeSeckillActivity";
	@BindView(R.id.home_seckill_tablayout)
	TabLayout tabLayout;
	@BindView(R.id.viewpager)
	ViewPager mViewpager;
	@BindView(R.id.title_view)
	View vLine;
	@BindView(R.id.seckill_head_banner_img)
	ImageView ivBanner;


	private List<String> mTitle = new ArrayList<String>();
	private List<Fragment> mFragment = new ArrayList<Fragment>();

	private HomeSeckill_contract.Presenter seckillPresenter = new HomeSeckill_Presenter(this);

	@Override
	protected int getContentView() {
		return R.layout.activity_home_seckill_;
	}

	@Override
	protected void initView() {
		setTitleText("好物秒杀");
		vLine.setVisibility(View.GONE);
		mTitle.add("正在疯抢");
		mTitle.add("即将开始");

		registerBannerReceiver();
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
//		HomeSeckillFrag.instant().sendMessage(new HomeSeckillFrag.ICallBack() {
//			@Override
//			public void get_message_from_Fragment(String string) {
//
//				Log.i(TAG, "get_message_from_Fragment: "+ string);
//
//				Glide.with(HomeSeckillActivity.this)
//						.load(URLBuilder.URLBaseHeader + string)
//						.asBitmap()
//						.centerCrop()
//						.error(R.mipmap.default_goods)
//						.into(ivBanner);
//			}
//		});
	}

	private myreceiver recevier = null;

	private void registerBannerReceiver() {
		recevier = new myreceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("COM.EXAMPLE.BANNER.IMAGEVIEW.URL");
		registerReceiver(recevier, intentFilter);

	}


	public class myreceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String banner_image_url = intent.getStringExtra("BANNER_IMAGE_URL");
			Glide.with(HomeSeckillActivity.this)
					.load(URLBuilder.getUrl(banner_image_url))
					.asBitmap()
					.centerCrop()
					.error(R.mipmap.default_goods)
					.into(ivBanner);

		}
	}


	@Override
	protected void initData() {

	}

	@Override
	protected void showShadow1() {
	}
}
