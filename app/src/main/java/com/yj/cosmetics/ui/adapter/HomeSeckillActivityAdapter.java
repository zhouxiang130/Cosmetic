package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.SeckillEntity;
import com.yj.cosmetics.ui.activity.GoodsDetailActivity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.widget.CustomCountDownTimerView;
import com.yj.cosmetics.widget.MaterialRippleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class HomeSeckillActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context mContext;
	//    private List<ProfitDetialEntity.ProfitDetialData> mList = new ArrayList<>();
	List<SeckillEntity.SeckillData.SeckillList> mList;
	ProfitDetialClickListener mItemClickListener;
	private int flag;

	public HomeSeckillActivityAdapter(Context mContext, List<SeckillEntity.SeckillData.SeckillList> mList, int flag) {
		this.mContext = mContext;
		this.mList = mList;
		this.flag = flag;
	}

	public void setOnItemClickListener(ProfitDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		SeckillItemViewHolder seckillItemViewHolder;
		SeckillHeadViewHolder seckillHeadViewHolder;
		SeckillCommingViewHolder seckillCommingViewHolder;
		if (viewType == 0) {
			seckillHeadViewHolder = new SeckillHeadViewHolder(MaterialRippleView.create(LayoutInflater
					.from(mContext).inflate(R.layout.item_home_activity_seckill_head, parent, false)));
			return seckillHeadViewHolder;
		} else if (viewType == 1) {
			seckillItemViewHolder = new SeckillItemViewHolder(MaterialRippleView.create(LayoutInflater
					.from(mContext).inflate(R.layout.item_home_activity_seckill_item, parent, false)), mItemClickListener);
			return seckillItemViewHolder;
		} else {
			seckillCommingViewHolder = new SeckillCommingViewHolder(MaterialRippleView.create(LayoutInflater
					.from(mContext).inflate(R.layout.item_home_seckill_comming, parent, false)), mItemClickListener);
			return seckillCommingViewHolder;
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		}
		if (flag == 0) {
			return 1;
		} else {
			return 2;
		}
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof SeckillItemViewHolder) {
			LogUtils.i("小时=====" + mList.get(position - 1).getHours() + "======分钟===" + mList.get(position - 1).getMin() + "=====秒====" + mList.get(position - 1).getSec());
			if (mList.get(position - 1).getHours() < 0 || mList.get(position - 1).getMin() < 0 || mList.get(position - 1).getSec() < 0) {

			} else {
				((SeckillItemViewHolder) holder).tvTimer.setTime(mList.get(position - 1).getHours(), mList.get(position - 1).getMin(), mList.get(position - 1).getSec());
				((SeckillItemViewHolder) holder).tvTimer.start();
			}
			((SeckillItemViewHolder) holder).tvOp.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			((SeckillItemViewHolder) holder).tvOp.setText("￥" + mList.get(position - 1).getProduct_orginal());
			((SeckillItemViewHolder) holder).tvTitle.setText(mList.get(position - 1).getProduct_name());
			((SeckillItemViewHolder) holder).tvState.setText("已抢" + mList.get(position - 1).getSold() + "%");
			((SeckillItemViewHolder) holder).tvStyle.setText(mList.get(position - 1).getProduct_abstract());
			((SeckillItemViewHolder) holder).tvPrice.setText("￥" + mList.get(position - 1).getProduct_current());
			((SeckillItemViewHolder) holder).tvConfirm.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, GoodsDetailActivity.class);
					intent.putExtra("productId", mList.get(position - 1).getProduct_id());
					mContext.startActivity(intent);
				}
			});
			LogUtils.i("转换后的值=====" + Math.round(Float.parseFloat(mList.get(position - 1).getSold())));
			((SeckillItemViewHolder) holder).pb.setSecondaryProgress(Math.round(Float.parseFloat(mList.get(position - 1).getSold())));
			Glide.with(mContext)
					.load(URLBuilder.getUrl(mList.get(position - 1).getProduct_listImg()))
					.asBitmap()
					.centerCrop()
					.error(R.mipmap.default_goods)
					.into(((SeckillItemViewHolder) holder).iv);

		} else if (holder instanceof SeckillCommingViewHolder) {
			((SeckillCommingViewHolder) holder).tvOp.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			((SeckillCommingViewHolder) holder).tvOp.setText("￥" + mList.get(position - 1).getProduct_orginal());
			((SeckillCommingViewHolder) holder).tvTitle.setText(mList.get(position - 1).getProduct_name());
			((SeckillCommingViewHolder) holder).tvStyle.setText(mList.get(position - 1).getProduct_abstract());
			((SeckillCommingViewHolder) holder).tvPrice.setText("￥" + mList.get(position - 1).getProduct_current());
			Glide.with(mContext)
					.load(URLBuilder.getUrl(mList.get(position - 1).getProduct_listImg()))
					.asBitmap()
					.centerCrop()
					.error(R.mipmap.default_goods)
					.into(((SeckillCommingViewHolder) holder).iv);
			((SeckillCommingViewHolder) holder).tvTime.setText(mList.get(position - 1).getProduct_timeStart());
		}
	}

	@Override
	public int getItemCount() {
		return mList.size() + 1;
	}

	class SeckillHeadViewHolder extends RecyclerView.ViewHolder {

		public SeckillHeadViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}


	class SeckillItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.item_seckill_timer)
		CustomCountDownTimerView tvTimer;
		@BindView(R.id.item_seckill_iv)
		ImageView iv;
		@BindView(R.id.item_seckill_tv_op)
		TextView tvOp;
		@BindView(R.id.item_seckill_tv_title)
		TextView tvTitle;
		@BindView(R.id.item_seckill_tv_style)
		TextView tvStyle;
		@BindView(R.id.item_seckill_tv_state)
		TextView tvState;
		@BindView(R.id.item_seckill_tv_price)
		TextView tvPrice;
		@BindView(R.id.item_seckill_tv_confirm)
		TextView tvConfirm;
		@BindView(R.id.item_seckill_pb)
		ProgressBar pb;

		private ProfitDetialClickListener mListener;

		public SeckillItemViewHolder(View itemView, ProfitDetialClickListener listener) {
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

	class SeckillCommingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.item_seckill_iv)
		ImageView iv;
		@BindView(R.id.item_seckill_tv_op)
		TextView tvOp;
		@BindView(R.id.item_seckill_tv_title)
		TextView tvTitle;
		@BindView(R.id.item_seckill_tv_style)
		TextView tvStyle;
		@BindView(R.id.item_seckill_tv_price)
		TextView tvPrice;
		@BindView(R.id.item_seckill_tv_confirm)
		TextView tvConfirm;
		@BindView(R.id.item_seckill_tv_time)
		TextView tvTime;
		private ProfitDetialClickListener mListener;

		public SeckillCommingViewHolder(View itemView, ProfitDetialClickListener listener) {
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
		public void onItemClick(View view, int postion);
	}
}