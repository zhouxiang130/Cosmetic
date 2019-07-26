package com.yj.cosmetics.ui.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/9 0009.
 * 商品评价大图显示
 */

public class BigImageActivity extends BaseActivity {

	private static final String TAG = " BigImageActivity ";
	@BindView(R.id.show_big_img_viewpager)
	ViewPager bigImgViewPager;

	@BindView(R.id.show_big_img_indicator)
	LinearLayout bigImgIndicator;
	private String postions;

	private List<ImageView> points;
	private ArrayList<String> bigImageList;//数据源

	@Override
	protected int getContentView() {
		return R.layout.activity_big_image;
	}

	@Override
	protected void initView() {
		points = new ArrayList<>();//点的集合
		postions = getIntent().getStringExtra("postions");
		//接收list
		bigImageList = (ArrayList<String>) getIntent().getSerializableExtra("bigImg_list");
		if (bigImageList.size() > 1) {
			addLunBoPoints();//添加轮播图的点
		}
		MyPagerAdapter adapter = new MyPagerAdapter();
		bigImgViewPager.setAdapter(adapter);
		bigImgViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				for (int i = 0; i < points.size(); i++) {
					if (i == position) {
						points.get(position).setEnabled(true);
					} else {
						points.get(i).setEnabled(false);
					}
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		bigImgViewPager.setCurrentItem(Integer.parseInt(postions));
	}


	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return bigImageList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(BigImageActivity.this, R.layout.item_big_imge, null);
			ImageView image = (ImageView) view.findViewById(R.id.image);
			final ProgressBar progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);

			Glide.with(BigImageActivity.this)  //配置上下文
					.load(URLBuilder.getUrl(bigImageList.get(position)))//设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
					.error(R.mipmap.default_goods)           //设置错误图片
//                    .placeholder(R.mipmap.default_image)     //设置占位图片
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存
					.into(new GlideDrawableImageViewTarget(image) {
						@Override
						public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
							super.onResourceReady(resource, animation);
							progress_bar.setVisibility(View.INVISIBLE);
						}
					});
//                    .into(view);

			container.addView(view);
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}


	/**
	 * 添加轮播图的点
	 */
	private void addLunBoPoints() {
		bigImgIndicator.removeAllViews();
		points.clear();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.leftMargin = 15;//设置点的间距
		params.bottomMargin = 20;//设置点距离底部的间距

		for (int i = 0; i < bigImageList.size(); i++) {
			ImageView imageView = new ImageView(BigImageActivity.this);
//            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//            layoutParams.width = 10;
//            layoutParams.height = 10;
//            imageView.setLayoutParams(layoutParams);
			imageView.setImageResource(R.drawable.lunbo_points_big_image);
			if (i == 0) {
				imageView.setEnabled(true);
			} else {
				imageView.setEnabled(false);
			}
			bigImgIndicator.addView(imageView, params);
			points.add(imageView);//添加到点的集合
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	protected void initData() {

	}
}
