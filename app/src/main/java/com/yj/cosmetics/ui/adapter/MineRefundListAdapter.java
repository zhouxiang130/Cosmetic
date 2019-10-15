package com.yj.cosmetics.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineRefundListEntity;
import com.yj.cosmetics.ui.activity.MineRefundDetailActivity;
import com.yj.cosmetics.ui.activity.MineRefundListActivity;
import com.yj.cosmetics.ui.activity.StoreDetailActivity;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.yj.cosmetics.widget.WrapContentGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class MineRefundListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private MineRefundListActivity mContext;
	List<MineRefundListEntity.DataBean.ListBean> mList = new ArrayList<>();
	MineOrderAllClickListener mItemClickListener;

	UserUtils mUtils;


	public MineRefundListAdapter(MineRefundListActivity mContext, List<MineRefundListEntity.DataBean.ListBean> mList, UserUtils mUtils) {
		this.mContext = mContext;
		this.mList = mList;
		this.mUtils = mUtils;
	}

	public void setOnItemClickListener(MineOrderAllClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		OrderGoodsViewHolder orderGoodsViewHolder;
		orderGoodsViewHolder = new OrderGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mine_refund_list, parent, false), mItemClickListener);
		return orderGoodsViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof OrderGoodsViewHolder) {

			MineRefundListGoodsAdapter gridViewAdapter = new MineRefundListGoodsAdapter(mContext, mList.get(position).getItem());
			((OrderGoodsViewHolder) holder).gridView.setAdapter(gridViewAdapter);
			((OrderGoodsViewHolder) holder).gridView.setFocusable(false);
			((OrderGoodsViewHolder) holder).gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//					Intent intent = new Intent(mContext, MineOrderDetailActivity.class);
//					intent.putExtra("oid", mList.get(position).getOrderId());
//					if (mList.get(position).getOrderState().equals("1") || mList.get(position).getOrderState().equals("3") ||
//							mList.get(position).getOrderState().equals("4") || mList.get(position).getOrderState().equals("5")) {
//						mContext.startActivityForResult(intent, Variables.REFRESH_ORDER_LIST);
//					} else {
//						mContext.startActivity(intent);
//					}
				}
			});
//			((OrderGoodsViewHolder) holder).tvOrderNum.setText("订单号 :  " + mList.get(position).getOrderNum());
//			((OrderGoodsViewHolder) holder).tvState.setText(mList.get(position).getOrderStateName());
			Glide.with(mContext)
					.load(URLBuilder.getUrl( mList.get(position).getShopImg()))
					.error(R.mipmap.default_goods)
					.centerCrop()
					.into(((OrderGoodsViewHolder) holder).shopIv);
			((OrderGoodsViewHolder) holder).shopName.setText(mList.get(position).getShopName());
			((OrderGoodsViewHolder) holder).tvStates.setVisibility(View.INVISIBLE);
			if (mList.get(position).getShopId() != null) {
				((OrderGoodsViewHolder) holder).ivMore.setVisibility(View.VISIBLE);
				((OrderGoodsViewHolder) holder).shopDetail.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(mContext, StoreDetailActivity.class);
						intent.putExtra("shopId", mList.get(position).getShopId());
						mContext.startActivity(intent);
					}
				});
			} else {
				((OrderGoodsViewHolder) holder).ivMore.setVisibility(View.GONE);
			}
			//returnState 1待处理 2退款成功 3退款驳回
			//returnType 退款类型（1：仅退款 2：退货并退款）
			if (mList.get(position).getReturnType() == 1) {
				((OrderGoodsViewHolder) holder).tvType.setText("仅退款");
				((OrderGoodsViewHolder) holder).ivIcon.setImageResource(R.mipmap.jintuikuan);
			} else {
				((OrderGoodsViewHolder) holder).tvType.setText("退货并退款");
				((OrderGoodsViewHolder) holder).ivIcon.setImageResource(R.mipmap.tuikauntuihuo);
			}
			switch (mList.get(position).getReturnState()) {
				case 1:
					((OrderGoodsViewHolder) holder).tvState.setText("待处理");
					break;
				case 2:
					((OrderGoodsViewHolder) holder).tvState.setText("退款成功");
					break;
				case 3:
					((OrderGoodsViewHolder) holder).tvState.setText("退款驳回");
					break;
			}
			//待评价
			((OrderGoodsViewHolder) holder).llBottom.setVisibility(View.VISIBLE);
			((OrderGoodsViewHolder) holder).tvBottomLeft.setText("查看详情");
			((OrderGoodsViewHolder) holder).tvBottomLeft.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					checkLogistic(mList.get(position).getReturnId() + "");
				}
			});
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	class OrderGoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.gridView)
		WrapContentGridView gridView;
		@BindView(R.id.pay_header_state)
		TextView tvStates;
		@BindView(R.id.pay_bottom_num)
		TextView tvType;//仅退款
		@BindView(R.id.pay_bottom_num_)
		TextView tvState;//退款成功
		@BindView(R.id.refund_img_icon)
		ImageView ivIcon;
		@BindView(R.id.frag_mine_shop_iv)
		RoundedImageView shopIv;
		@BindView(R.id.frag_mine_shop_detail)
		RelativeLayout shopDetail;
		@BindView(R.id.frag_mine_shop_name)
		TextView shopName;
		@BindView(R.id.image_store_more)
		ImageView ivMore;


		@BindView(R.id.pay_bottom_check)
		TextView tvBottomLeft;
		@BindView(R.id.pay_bottom_ll)
		LinearLayout llBottom;
		private MineOrderAllClickListener mListener;

		public OrderGoodsViewHolder(View itemView, MineOrderAllClickListener listener) {
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


	public interface MineOrderAllClickListener {
		void onItemClick(View view, int postion);
	}


	private void checkLogistic(String returnId) {
//		Intent intent = new Intent(mContext, MineLogicalDetialActivity.class);
		Intent intent = new Intent(mContext, MineRefundDetailActivity.class);
		intent.putExtra("returnId", returnId);
		mContext.startActivity(intent);
	}


	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		super.onDetachedFromRecyclerView(recyclerView);
	}
}