package com.yj.cosmetics.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.CouponListEntity;
import com.yj.cosmetics.ui.activity.HomeGoodsListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class MainCouponTicketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	private static final String TAG = "MineCouponDetailAdapter";
	private final List<CouponListEntity.DataBean.UserPossessionBean.CouponsBean> coupons;
	private Activity mContext;
	CouponDetialClickListener mItemClickListener;
	public int VISIBLE = 0x00000000;
	public static final int GONE = 0x00000008;

	public MainCouponTicketAdapter(Activity mContext, List<CouponListEntity.DataBean.UserPossessionBean.CouponsBean> coupons) {
		this.mContext = mContext;
		this.coupons = coupons;

	}

	public void setOnItemClickListener(CouponDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		CouponViewHolder CouponViewHolder = new CouponViewHolder(
				LayoutInflater.from(mContext).inflate(R.layout.item_main_coupon_ticket, parent, false), mItemClickListener);
		return CouponViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof CouponViewHolder) {

			((CouponViewHolder) holder).TextTicketClass.setText(coupons.get(position).getCouponTypeMsg());
			((CouponViewHolder) holder).TextTicketPriceStandard.setText(coupons.get(position).getCouponRequire());
			((CouponViewHolder) holder).TextTicketClassContent.setText(coupons.get(position).getCouponName());
			((CouponViewHolder) holder).TextTicketPrice.setText(coupons.get(position).getFaceValue());
			((CouponViewHolder) holder).TextTicketClassTime.setText(coupons.get(position).getDate());
			if (coupons.get(position).getCouponType() == 1) {//"折扣券"

				((CouponViewHolder) holder).TextTicketPriceSymbol.setVisibility(View.GONE);
				((CouponViewHolder) holder).TextTicketRebate.setVisibility(View.VISIBLE);
				((CouponViewHolder) holder).ivBackground.setBackgroundResource(R.mipmap.youhuiquan_bg2);
				((CouponViewHolder) holder).TextTicketRebate.setText("折");
				((CouponViewHolder) holder).TextTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_four);

			} else if (coupons.get(position).getCouponType() == 2) {//"代金券"
				((CouponViewHolder) holder).TextTicketPriceSymbol.setVisibility(View.VISIBLE);
				((CouponViewHolder) holder).TextTicketRebate.setVisibility(View.GONE);
				((CouponViewHolder) holder).ivBackground.setBackgroundResource(R.mipmap.youhuiquan_bg1);
				((CouponViewHolder) holder).TextTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_five);

			} else if (coupons.get(position).getCouponType() == 3) {//"兑换券"
				((CouponViewHolder) holder).TextTicketPriceSymbol.setVisibility(View.VISIBLE);
				((CouponViewHolder) holder).TextTicketRebate.setVisibility(View.GONE);
				((CouponViewHolder) holder).ivBackground.setBackgroundResource(R.mipmap.youhuiquan_bg);
				((CouponViewHolder) holder).TextTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_three);
			}


			((CouponViewHolder) holder).ivDeadLine.setVisibility(View.GONE);
			((CouponViewHolder) holder).mineCouponTextReceive.setVisibility(View.VISIBLE);
			((CouponViewHolder) holder).mineCouponTextReceive.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
//					Toast.makeText(mContext, "立即领取", Toast.LENGTH_SHORT).show();
					Intent intent1 = new Intent();
					intent1.setAction("cn.yj.robust.getCoupon_");
					intent1.putExtra("productIds", "hello world");
					mContext.sendBroadcast(intent1);

					Intent intent = new Intent(mContext, HomeGoodsListActivity.class);
					if (coupons.get(position).getProductIds() != null) {
						intent.putExtra("productIds", coupons.get(position).getProductIds());
					}
					mContext.startActivity(intent);

				}
			});
//			((CouponViewHolder) holder).mineCouponRl.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//
//				}
//			});
		}
	}

	@Override
	public int getItemCount() {
		return coupons.size();
	}


	class CouponViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.mine_sign_text_ticket_price_symbol)
		TextView TextTicketPriceSymbol;
		@BindView(R.id.mine_sign_text_ticket_price)
		TextView TextTicketPrice;
		@BindView(R.id.mine_sign_text_ticket_rebate)
		TextView TextTicketRebate;
		@BindView(R.id.mine_sign_text_ticket_price_standard)
		TextView TextTicketPriceStandard;
		@BindView(R.id.mine_sign_text_ticket_class)
		TextView TextTicketClass;
		@BindView(R.id.mine_sign_text_ticket_class_content)
		TextView TextTicketClassContent;
		@BindView(R.id.mine_sign_text_ticket_class_time)
		TextView TextTicketClassTime;
		@BindView(R.id.mine_coupon_text_receive)
		TextView mineCouponTextReceive;
		@BindView(R.id.mine_coupon_rl_)
		RelativeLayout mineCouponRl;

		@BindView(R.id.mine_coupon_iv_background)
		ImageView ivBackground;
		@BindView(R.id.mine_coupon_iv_ticket_deadline)
		ImageView ivDeadLine;


		private CouponDetialClickListener mListener;

		public CouponViewHolder(View itemView, CouponDetialClickListener listener) {
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

	public interface CouponDetialClickListener {
		void onItemClick(View view, int postion);
	}
}