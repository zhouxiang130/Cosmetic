package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.SettlementsCartEntity;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class SettlementCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context mContext;
	List<SettlementsCartEntity.DataBean.ProArrayBean> mList;
	SpendDetialClickListener mItemClickListener;

	public SettlementCartAdapter(Context mContext, List<SettlementsCartEntity.DataBean.ProArrayBean> mList) {
		this.mContext = mContext;
		this.mList = mList;
	}

	public void setOnItemClickListener(SpendDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		CostViewHolder costViewHolder;
		costViewHolder = new CostViewHolder(LayoutInflater
				.from(mContext).inflate(R.layout.item_settlement_goods_, parent, false), mItemClickListener);
		return costViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

		if (holder instanceof CostViewHolder) {

			((CostViewHolder) holder).tvShopName.setText(mList.get(position).getShopName());
			Glide.with(mContext)
					.load(URLBuilder.getUrl( mList.get(position).getShopImg()))
					.asBitmap()
					.centerCrop()
					.error(R.mipmap.default_goods)
					.into(((CostViewHolder) holder).ivShopIcon);

			LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
			((CostViewHolder) holder).gridView.setLayoutManager(layoutManager);
			SettlementCartAdapters adapters = new SettlementCartAdapters(mContext, mList.get(position).getShopProArray());
			((CostViewHolder) holder).gridView.setAdapter(adapters);
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	class CostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.refound_detial_tv_normal_states)
		TextView tvShopName;
		@BindView(R.id.frag_mine_login_iv)
		RoundedImageView ivShopIcon;

		@BindView(R.id.gridView)
		RecyclerView gridView;


		private SpendDetialClickListener mListener;

		public CostViewHolder(View itemView, SpendDetialClickListener listener) {
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

	public interface SpendDetialClickListener {
		public void onItemClick(View view, int postion);
	}
}