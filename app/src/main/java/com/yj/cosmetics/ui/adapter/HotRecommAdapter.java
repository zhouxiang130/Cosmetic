package com.yj.cosmetics.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.HomeEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class HotRecommAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "HotRecommAdapter";
	private Activity mContext;
	List<HomeEntity.HomeData.HomeHot> mList;
	ProfitDetialClickListener mItemClickListener;

	public HotRecommAdapter(Activity mContext, List<HomeEntity.HomeData.HomeHot> mList) {
		this.mContext = mContext;
		this.mList = mList;
	}

	public void setOnItemClickListener(ProfitDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		HotRecommViewHolder hotRecommViewHolder;
		hotRecommViewHolder = new HotRecommViewHolder(LayoutInflater
				.from(mContext).inflate(R.layout.item_home_seckill_item, parent, false), mItemClickListener);
		return hotRecommViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof HotRecommViewHolder) {
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((HotRecommViewHolder) holder).hotlll.getLayoutParams();
			DisplayMetrics dm = new DisplayMetrics();
			//获取屏幕信息
			mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int screenWidth = dm.widthPixels;
			layoutParams.width = (int) (screenWidth * 0.3); // 宽度设置为屏幕的0.65
			((HotRecommViewHolder) holder).hotlll.setLayoutParams(layoutParams); //使设置好的布局参数应用到控件</pre>


			((HotRecommViewHolder) holder).tvTitle.setText(mList.get(position).getProduct_name());
			((HotRecommViewHolder) holder).tvContent.setText(mList.get(position).getProduct_abstract());
			((HotRecommViewHolder) holder).tvPrice.setText("￥" + mList.get(position).getProduct_current());
			Glide.with(mContext)
					.load(URLBuilder.getUrl(mList.get(position).getProduct_listImg()))
					.asBitmap()
					.centerCrop()
					.error(R.mipmap.default_goods)
					.into(((HotRecommViewHolder) holder).iv);

		}
	}

	@Override
	public int getItemCount() {
		if (mList.size() != 0) {
			return mList.size();
		} else {
			return 5;
		}
	}

	class HotRecommViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.home_hot_tv_title1)
		TextView tvTitle;
		@BindView(R.id.home_hot_tv_content1)
		TextView tvContent;
		@BindView(R.id.home_hot_iv1)
		ImageView iv;
		@BindView(R.id.home_hot_tv_price1)
		TextView tvPrice;
		@BindView(R.id.home_hot_ll1)
		LinearLayout hotlll;

		private ProfitDetialClickListener mListener;

		public HotRecommViewHolder(View itemView, ProfitDetialClickListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = listener;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onItemClick(v, getPosition());
			}
		}
	}

	public interface ProfitDetialClickListener {
		void onItemClick(View view, int postion);
	}
}