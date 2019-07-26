package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.widget.CustomBanner.CustomLoopPagerAdapter;
import com.yj.cosmetics.widget.CustomBanner.CustomRollPagerView;

import java.util.List;

/**
 * Created by Suo on 2017/4/11.
 */

public class GoodsBannerAdapter extends CustomLoopPagerAdapter {

	private List<String> stringList;
	private Context context;


	public GoodsBannerAdapter(CustomRollPagerView viewPager, List<String> stringList, Context context, String tag) {
		super(viewPager);
		this.stringList = stringList;
		this.context = context;
	}

	@Override
	public View getView(ViewGroup container, final int position) {
		final int picNo = position + 1;
		final ImageView view = new ImageView(container.getContext());
		view.setScaleType(ImageView.ScaleType.CENTER_CROP);
		view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		if (stringList != null) {
			view.setScaleType(ImageView.ScaleType.CENTER_CROP);
			view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			LogUtils.i("我在Stringlist != null中");
			LogUtils.i("stirngList的url" + URLBuilder.URLBaseHeader + stringList.get(position));
			Glide.with(context)
					.load(URLBuilder.getUrl(stringList.get(position)))
					.centerCrop()
					.placeholder(R.mipmap.default_goods)
					.error(R.mipmap.default_goods)
					.into(new SimpleTarget<GlideDrawable>() {
						@Override
						public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
							view.setImageDrawable(glideDrawable);
						}

						@Override
						public void onLoadFailed(Exception e, Drawable errorDrawable) {
							super.onLoadFailed(e, errorDrawable);
							view.setImageResource(R.mipmap.default_goods);
						}

						@Override
						public void onLoadStarted(Drawable placeholder) {
							super.onLoadStarted(placeholder);
							view.setImageResource(R.mipmap.default_goods);
						}
					});
		}
		return view;
	}

	@Override
	public int getRealCount() {
		if (stringList != null) {
			return stringList.size();
		}
		return 0;
	}

}
