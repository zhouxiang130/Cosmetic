package com.yj.cosmetics.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.OrderDetailEntity;
import com.yj.cosmetics.ui.activity.mineRefundClass.MineRefundClassActivity;
import com.yj.cosmetics.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/9/8.
 */

public class MineOrderListGoodsDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private String flag = null;
	private String orderId = null;
	private Context mContext;
	//    private List<OrderDetailEntity.OrderDetialData.OrderDetialList> mList = new ArrayList<>();
	List<OrderDetailEntity.OrderDetialData.OrderDetialItem> mList = new ArrayList<>();
	MineOrderAllInnerClickListener mItemClickListener;
	private String orderState = null;
	private String orderMoney/*, orderSendCosts, orderbalance*/;
	//	private String format;
	private String string;
	private String refundType = null;
	private String productState = null;

	public MineOrderListGoodsDetailAdapter(Context mContext, List<OrderDetailEntity.OrderDetialData.OrderDetialItem> mList, String orderId, String flag) {
		this.mContext = mContext;
		this.mList = mList;
		this.orderId = orderId;
		this.flag = flag;

	}

	public void setOnItemClickListener(MineOrderAllInnerClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		InnerGoodsViewHolder innerGoodsViewHolder;
		innerGoodsViewHolder = new InnerGoodsViewHolder(LayoutInflater
				.from(mContext).inflate(R.layout.item_mine_order_list_goods_detail, parent, false), mItemClickListener);
		return innerGoodsViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@SuppressLint("LongLogTag")
	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof InnerGoodsViewHolder) {
			((InnerGoodsViewHolder) holder).tvTitle.setText(mList.get(position).getProductName());
			((InnerGoodsViewHolder) holder).tvStyle.setText(mList.get(0).getSkuPropertiesName());
			((InnerGoodsViewHolder) holder).tvNum.setText("X" + mList.get(position).getDetailNum());
			((InnerGoodsViewHolder) holder).tvPrice.setText(mList.get(position).getMoney());

			if (productState.equals("2")) {
				((InnerGoodsViewHolder) holder).tvState.setVisibility(View.VISIBLE);
				((InnerGoodsViewHolder) holder).tvState.setText("已下架");

			} else if (productState.equals("3")) {
				((InnerGoodsViewHolder) holder).tvState.setVisibility(View.VISIBLE);
				((InnerGoodsViewHolder) holder).tvState.setText("已售罄");
			} else {
				((InnerGoodsViewHolder) holder).tvState.setVisibility(View.GONE);
			}


			Glide.with(mContext)
					.load(URLBuilder.getUrl(mList.get(position).getProductListimg()))
					.error(R.mipmap.default_goods)
					.centerCrop()
					.into(((InnerGoodsViewHolder) holder).ivGoods);

			if (orderState != null) {
				if (orderState.equals("2")) {
					if (refundType.equals("2")) {
						((InnerGoodsViewHolder) holder).tvDrawBack.setVisibility(View.VISIBLE);
					} else {
						((InnerGoodsViewHolder) holder).tvDrawBack.setVisibility(View.GONE);
					}
					//getOrderMoney 总价  getOrderSendCosts 运费  //getOrderBalance 余额
					// 待发货  应付价格 + 余额
					float orderMoney_ = Float.parseFloat(orderMoney);//应付价格
//					float orderbalance_ = Float.parseFloat(orderbalance);//余额
//					format = new DecimalFormat("0.00").format(orderMoney_ + orderbalance_);

//					boolean contains = format.contains("-");
//					if (contains) {
////						format = format.substring(0);
//						string = format.split("-")[1];
//					} else {
//						string = format + "";
//					}
					LogUtils.e("onBindViewHolder: " + string);
				} else if (orderState.equals("3")) {
					// 待收货  总价 + 余额 - 运费
					//	* @param orderMoney 总价
					//	 * @param orderSendCosts 余额
					//	 * @param orderbalance 运费
					if (refundType.equals("2")) {
						((InnerGoodsViewHolder) holder).tvDrawBack.setVisibility(View.GONE);
					} else {
						((InnerGoodsViewHolder) holder).tvDrawBack.setVisibility(View.GONE);
					}

					float orderMoney_ = Float.parseFloat(orderMoney);
//					float orderbalance_ = Float.parseFloat(orderbalance);
//					float orderSendCosts_ = Float.parseFloat(orderSendCosts);
//					format = new DecimalFormat("0.00").format(orderMoney_ + orderbalance_ - orderSendCosts_);

//					boolean contains = format.contains("-");
//					if (contains) {
//						string = format.split("-")[1];
//						format = format.substring(0);
//					} else {
//						string = format + "";
//					}
//					LogUtils.i("onBindViewHolder: " + string);
				} else {
					((InnerGoodsViewHolder) holder).tvDrawBack.setVisibility(View.GONE);
				}
			}

			//添加退款按钮
			((InnerGoodsViewHolder) holder).tvDrawBack.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					LogUtils.e("onClick: orderId ------- " + orderId + "   format>>>>>>  " /*+ format*/ + "flag : " + flag);
					Intent intent = new Intent(mContext, MineRefundClassActivity.class);
					intent.putExtra("mList", mList.get(position));
//						intent.putExtra("Money", format);
					intent.putExtra("orderId", orderId);
					intent.putExtra("flag", flag);
					mContext.startActivity(intent);

				}
			});
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public void setProductState(String productState) {
		this.productState = productState;
	}

	/**
	 * @param orderMoney     应付价格
	 * @param orderSendCosts 运费
	 *                       //	 * @param orderbalance   余额
	 */

	//getOrderMoney 总价  getOrderSendCosts 运费  //getOrderBalance 余额
	public void setOrderRefundPic(String orderMoney, String orderSendCosts, String orderbalance) {
		this.orderMoney = orderMoney;
//		this.orderSendCosts = orderSendCosts;
//		this.orderbalance = orderbalance;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	class InnerGoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.item_order_goods_iv)
		ImageView ivGoods;
		@BindView(R.id.item_order_goods_tv_title)
		TextView tvTitle;
		@BindView(R.id.item_order_goods_tv_style)
		TextView tvStyle;
		@BindView(R.id.item_order_goods_tv_state)
		TextView tvState;
		@BindView(R.id.item_order_goods_tv_price)
		TextView tvPrice;
		@BindView(R.id.item_order_goods_tv_number)
		TextView tvNum;
		@BindView(R.id.pay_bottom_drawback)
		TextView tvDrawBack;
		private MineOrderAllInnerClickListener mListener;

		public InnerGoodsViewHolder(View itemView, MineOrderAllInnerClickListener listener) {
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

	public interface MineOrderAllInnerClickListener {
		void onItemClick(View view, int postion);
	}
}
