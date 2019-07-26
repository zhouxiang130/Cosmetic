package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.InfoCenterEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class InfoCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context mContext;
	//    private List<SpendDetialEntity.SpendDetialData> mList = new ArrayList<>();
	List<InfoCenterEntity.InfoCenterData> mList;
	SpendDetialClickListener mItemClickListener;

	public InfoCenterAdapter(Context mContext, List<InfoCenterEntity.InfoCenterData> mList) {
		this.mContext = mContext;
		this.mList = mList;
	}

	public void setOnItemClickListener(SpendDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		InfoViewHolder infoViewHolder;
		infoViewHolder = new InfoViewHolder(LayoutInflater
				.from(mContext).inflate(R.layout.item_info_center, parent, false), mItemClickListener);
		return infoViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof InfoViewHolder) {
			((InfoViewHolder) holder).tvTime.setText(mList.get(position).getTime());
			((InfoViewHolder) holder).tvTitle.setText(mList.get(position).getTitle());
			((InfoViewHolder) holder).tvContent.setText(mList.get(position).getContent());
			if (mList.get(position).getIsRead() == 1) {
				((InfoViewHolder) holder).ivMessage_.setVisibility(View.VISIBLE);
			} else {
				((InfoViewHolder) holder).ivMessage_.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.info_center_tv_time)
		TextView tvTime;
		@BindView(R.id.info_center_tv_content)
		TextView tvContent;
		@BindView(R.id.info_center_tv_title)
		TextView tvTitle;
		@BindView(R.id.info_center_tv_check)
		TextView tvCheck;
		@BindView(R.id.img_message_)
		ImageView ivMessage_;
		private SpendDetialClickListener mListener;

		public InfoViewHolder(View itemView, SpendDetialClickListener listener) {
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
		 void onItemClick(View view, int postion);
	}
}