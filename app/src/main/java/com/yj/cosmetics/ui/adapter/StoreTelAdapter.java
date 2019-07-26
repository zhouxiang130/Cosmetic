package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.ShopDetailEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class StoreTelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private final ShopDetailEntity datas;
	private int flag = 0;
	private Context mContext;
	SpendDetialClickListener mItemClickListener;
	private String TAG = "MineCollectionAdapter";

	public StoreTelAdapter(Context mContext, ShopDetailEntity datas) {
		this.mContext = mContext;
		this.datas = datas;

	}

	public void setOnItemClickListener(SpendDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		CollectionViewHolder collectionViewHolder;
		collectionViewHolder = new CollectionViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_store_tel_list, parent, false), mItemClickListener);
		return collectionViewHolder;
	}


	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof CollectionViewHolder) {
			((CollectionViewHolder) holder).tvTel.setText(datas.getData().getShopTel().get(position));
		}
	}

	@Override
	public int getItemCount() {
		return datas.getData().getShopTel().size();
	}

	class CollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.text_tel_info)
		TextView tvTel;


		private SpendDetialClickListener mListener;

		public CollectionViewHolder(View itemView, SpendDetialClickListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = listener;
			//@TODO 布局文件 添加点击事件
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onItemClick(v, getPosition(), flag);
			}
		}
	}

	public interface SpendDetialClickListener {
		void onItemClick(View view, int postion, int flag);
	}


}