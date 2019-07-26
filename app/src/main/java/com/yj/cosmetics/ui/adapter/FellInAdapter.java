package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.R;
import com.yj.cosmetics.model.FellInEntity;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/21 0021.
 */

public class FellInAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	private List<FellInEntity.DataBean.ArrBean> mData = null;
	private Context mContext = null;
	private String rowNum = null;
	private int positions;


	public FellInAdapter(Context mContext, List<FellInEntity.DataBean.ArrBean> mData) {
		this.mContext = mContext;
		this.mData = mData;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		PartakeViewHolder partakeViewHolder;
		ItemMoreViewHolder itemMoreViewHolder;

		if (viewType == 0) {
			partakeViewHolder = new PartakeViewHolder(
					LayoutInflater.from(mContext).inflate(R.layout.item_partake_list_item, parent, false));
			return partakeViewHolder;
		} else {

			itemMoreViewHolder = new ItemMoreViewHolder(
					LayoutInflater.from(mContext).inflate(R.layout.item_partake_list_item_more, parent, false));

			return itemMoreViewHolder;
		}
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof PartakeViewHolder) {
			positions = position > 20 ? position - 1 : position;
			Glide.with(mContext)
					.load(URLBuilder.getUrl(mData.get(positions).getUserHeadImg()))
					.asBitmap()
					.centerCrop()
					.error(R.mipmap.default_avatar)
					.into(((PartakeViewHolder) holder).shopImg);
			((PartakeViewHolder) holder).tvName.setText(mData.get(positions).getUserName());
			((PartakeViewHolder) holder).tvInit.setText(mData.get(positions).getInsertTime());
			if (position == 0) {
				((PartakeViewHolder) holder).ovCrown.setVisibility(View.VISIBLE);
				((PartakeViewHolder) holder).iv_round.setBackgroundResource(R.drawable.shape_round360_e95);
			} else {
				((PartakeViewHolder) holder).ovCrown.setVisibility(View.INVISIBLE);
				((PartakeViewHolder) holder).iv_round.setBackgroundResource(R.drawable.shape_round360_e83);
			}

			if (rowNum != null) {
				if (mData.get(positions).getRowNum().equals(rowNum)) {
					((PartakeViewHolder) holder).iv_round.setBackgroundResource(R.drawable.shape_round360_e95);
				}
			}
//			if (position > 20) {
//			} else {
////				positions = position;
//				position = position + 1;
//			}
			position = position > 20 ? position : position + 1;
			if (position > 0 && position < 10) {
				((PartakeViewHolder) holder).tvRowNum.setText("0" + (position));
			} else {
				((PartakeViewHolder) holder).tvRowNum.setText(String.valueOf(position));
			}


/*			((PartakeViewHolder) holder).tvDetail.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					IntentUtils.IntentToStoreDetial(mContext,data.get(position).getSellerId());
				}
			});*/
		}

	}

	@Override
	public int getItemCount() {
		if (mData.size() >= 20) {
			return mData.size() + 1;
		} else {
			return mData.size();
		}
	}


	@Override
	public int getItemViewType(int position) {
		Log.i("TAG position ", "getItemViewType: " + mData.size() + ">>>>position: " + position);
		if (position == 20) {
			return 1;
		} else {
			return 0;
		}
	}

	public void setData(List<FellInEntity.DataBean.ArrBean> data, String rowNum) {
		this.mData = data;
		this.rowNum = rowNum;
		notifyDataSetChanged();
	}


	class PartakeViewHolder extends RecyclerView.ViewHolder {


		@BindView(R.id.item_partake_shop_img)
		RoundedImageView shopImg;

		@BindView(R.id.iv_crown)
		ImageView ovCrown;
		@BindView(R.id.iv_round)
		ImageView iv_round;

		@BindView(R.id.tv_partake_shop_name)
		TextView tvName;

		@BindView(R.id.tv_partake_shop_loc)
		TextView tvInit;

		@BindView(R.id.tv_row_number)
		TextView tvRowNum;


		public PartakeViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	class ItemMoreViewHolder extends RecyclerView.ViewHolder {
		public ItemMoreViewHolder(View itemView) {
			super(itemView);
		}
	}
}
