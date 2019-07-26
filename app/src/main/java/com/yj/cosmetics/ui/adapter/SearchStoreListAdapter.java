package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.ShopListEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class SearchStoreListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	private Context mContext;
	List<ShopListEntity.DataBean.ShopArrayBean> mData;
	SpendDetialClickListener mItemClickListener;


	public SearchStoreListAdapter(Context mContext, List<ShopListEntity.DataBean.ShopArrayBean> mList) {
		this.mContext = mContext;
		this.mData = mList;
	}


	public void setOnItemClickListener(SpendDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		CollectionViewHolder collectionViewHolder = new CollectionViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_list_item, parent, false), mItemClickListener);
		return collectionViewHolder;
	}


	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof CollectionViewHolder) {
			setItemData((CollectionViewHolder) holder, position);
		}
	}

	public void setItemData(CollectionViewHolder viewHolder, int position) {


//		if (!mData.get(position).getReceipt().equals("") && mData.get(position).getReceipt() != null) {

//			if (mData.get(position).getReceipt().equals("1")) {
//				viewHolder.tvStoreStates.setVisibility(View.GONE);
//			} else {
//				viewHolder.tvStoreStates.setVisibility(View.VISIBLE);
//			}
//		}
		if (mData.get(position).getShopType() == 1) {
			viewHolder.tvTitle_.setVisibility(View.GONE);
		} else {
			viewHolder.tvTitle_.setVisibility(View.VISIBLE);
			viewHolder.tvTitle_.setText(mData.get(position).getShopTypeName());
		}

		viewHolder.tvTitle.setText(mData.get(position).getShopName());
		Glide.with(mContext)
				.load(URLBuilder.getUrl( mData.get(position).getShopLogo()))
				.asBitmap()
				.centerCrop()
				.error(R.mipmap.default_goods)
				.into(viewHolder.ivIcon);

		viewHolder.tvSales.setText("月售 " + mData.get(position).getDetailNumMonth() + " 单");
		viewHolder.tvExpenses.setText("起送价: ￥" + mData.get(position).getServiceStartime());
		viewHolder.tvDistance.setText("距离:" + mData.get(position).getJuli());

	}


	public void setData(List<ShopListEntity.DataBean.ShopArrayBean> shopArray) {
		this.mData = shopArray;
		notifyDataSetChanged();
	}


	@Override
	public int getItemCount() {
		return mData.size();
	}

	class CollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.store_list_tv_title)
		TextView tvTitle;
		@BindView(R.id.store_list_tv_sales)
		TextView tvSales;
		@BindView(R.id.store_list_tv_expenses)
		TextView tvExpenses;
		@BindView(R.id.store_list_tv_wuliu)
		TextView tvWulliu;
		@BindView(R.id.store_list_tv_distance)
		TextView tvDistance;
		@BindView(R.id.store_list_tv_title_)
		TextView tvTitle_;
		@BindView(R.id.item_tv_store_states)
		TextView tvStoreStates;

		@BindView(R.id.store_list_iv)
		ImageView ivIcon;

		private SpendDetialClickListener mListener;


		public CollectionViewHolder(View itemView, SpendDetialClickListener listener) {
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