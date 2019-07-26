package com.yj.cosmetics.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.CouponReceiveEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.ui.activity.HomeGoodsListActivity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2017/4/17.
 */

public class CouponReceiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "JudgeAdapter";
	private String uid = null;
	private Activity mContext;
	List<CouponReceiveEntity.DataBean.CouponsBean> mList;
	SpendDetialClickListener mItemClickListener;

	private final static int HEAD_COUNT = 1;
	private final static int FOOT_COUNT = 1;

	private final static int TYPE_HEAD = 0;
	private final static int TYPE_CONTENT = 1;
	private final static int TYPE_FOOTER = 2;
	private int conditionLimitnum;
	private String phone = null;

	public CouponReceiveAdapter(Activity mContext, List<CouponReceiveEntity.DataBean.CouponsBean> mList, String uid) {
		this.mContext = mContext;
		this.mList = mList;
		this.uid = uid;
	}

	public void setOnItemClickListener(SpendDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		CouponReceiveHolder itemHolder;
		CouponReceiveFooterHolder couponReceiveFooterHolder;
		if (viewType == 0) {
			itemHolder = new CouponReceiveHolder(LayoutInflater.from(mContext).inflate(R.layout.item_coupon_receive, parent, false), mItemClickListener);
			return itemHolder;
		} else {
			couponReceiveFooterHolder = new CouponReceiveFooterHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_foot_coupon_item, parent, false));
			return couponReceiveFooterHolder;
		}
	}

	@Override
	public int getItemViewType(int position) {

		int contentSize = getContentSize();

		Log.i(TAG, "getItemViewType: " + contentSize + " ----- position : " + position);
		if (HEAD_COUNT != 0 && position == 0) { // 头部
			return 0;
		} else if (FOOT_COUNT != 0 && position == contentSize - 1) { // 尾部
			return 1;
		} else {
			return 0;
		}
	}

	public int getContentSize() {
		return mList.size() + HEAD_COUNT;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof CouponReceiveHolder) {
			Log.i(TAG, "onBindViewHolder: " + position);
			if (position <= mList.size() - 1) {
				((CouponReceiveHolder) holder).tvTicketTime.setText(mList.get(position).getDate());
				((CouponReceiveHolder) holder).tvTicketContent.setText(mList.get(position).getCouponName());
				((CouponReceiveHolder) holder).tvTicketClass.setText(mList.get(position).getCouponTypeMsg());
				((CouponReceiveHolder) holder).tvTicketStandard.setText(mList.get(position).getCouponRequire());
				((CouponReceiveHolder) holder).tvTicketPrice.setText(mList.get(position).getFaceValue() + "");

				if (mList.get(position).getCouponType() == 1) {//1折扣券 2代金券3：兑换券 4：积分券5： 金额券
					((CouponReceiveHolder) holder).tvPrice.setVisibility(View.GONE);
					((CouponReceiveHolder) holder).tvTicketRebate.setText("折");
					((CouponReceiveHolder) holder).tvTicketRebate.setVisibility(View.VISIBLE);
				} else {
					((CouponReceiveHolder) holder).tvPrice.setVisibility(View.VISIBLE);
					((CouponReceiveHolder) holder).tvTicketRebate.setVisibility(View.GONE);
				}

				switch (mList.get(position).getCouponType()) {
					case 1:
						((CouponReceiveHolder) holder).ivBackground.setBackgroundResource(R.mipmap.youhuiquan_bg2);
						((CouponReceiveHolder) holder).tvTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_four);
						break;
					case 2:
						((CouponReceiveHolder) holder).ivBackground.setBackgroundResource(R.mipmap.youhuiquan_bg1);
						((CouponReceiveHolder) holder).tvTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_three);
						break;
					case 3:
						((CouponReceiveHolder) holder).ivBackground.setBackgroundResource(R.mipmap.youhuiquan_bg);
						((CouponReceiveHolder) holder).tvTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_five);
						break;
				}

				conditionLimitnum = mList.get(position).getConditionLimitnum();
				Log.i(TAG, "onBindViewHolder: " + conditionLimitnum + " position : " + position);
//				if (conditionLimitnum == 0) {
//					((CouponReceiveHolder) holder).tvReceive.setText("已领取");
//					((CouponReceiveHolder) holder).tvReceive.setTextColor(mContext.getResources().getColor(R.color.C99_99_99));
//					((CouponReceiveHolder) holder).tvReceive.setBackgroundResource(R.drawable.shape_corner_e8_stroke0_5_radius17);
//					((CouponReceiveHolder) holder).tvReceive.setEnabled(false);
//					return;
//				}


				((CouponReceiveHolder) holder).tvReceive.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						conditionLimitnum = mList.get(position).getConditionLimitnum();
						Log.i(TAG, "onClick: " + conditionLimitnum);
						if (conditionLimitnum == 0) {
							((CouponReceiveHolder) holder).tvReceive.setText("已领取");
							((CouponReceiveHolder) holder).tvReceive.setTextColor(mContext.getResources().getColor(R.color.C99_99_99));
							((CouponReceiveHolder) holder).tvReceive.setBackgroundResource(R.drawable.shape_corner_e8_stroke0_5_radius17);
							((CouponReceiveHolder) holder).tvReceive.setEnabled(false);
							Toast.makeText(mContext, "您领取已达上限", Toast.LENGTH_SHORT).show();
							return;
						} else {
							doAsyncGetCoupon(position, mList);
							setRuleDialog(position, mList);
						}
					}
				});
			}
		} else if (holder instanceof CouponReceiveFooterHolder) {
			((CouponReceiveFooterHolder) holder).tvPhone.setText("欢迎致电客服电话: " + phone);
		}
	}


	private void setRuleDialog(final int position, final List<CouponReceiveEntity.DataBean.CouponsBean> mList) {
		View pview = LayoutInflater.from(mContext).inflate(R.layout.dialog_receive_coupon_ticket, null);
		LinearLayout llCancel = (LinearLayout) pview.findViewById(R.id.integral_ll_cancel);
		TextView tvTitle = (TextView) pview.findViewById(R.id.integral_tv_title);
		TextView tvPrice = (TextView) pview.findViewById(R.id.mine_sign_text_ticket_price_symbol);//符号
		TextView tvTicketPrice = (TextView) pview.findViewById(R.id.mine_sign_text_ticket_price);//价格 几折
		TextView tvTicketRebate = (TextView) pview.findViewById(R.id.mine_sign_text_ticket_rebate);//折
		TextView tvTicketStandard = (TextView) pview.findViewById(R.id.mine_sign_text_ticket_price_standard);//
		TextView tvYes = (TextView) pview.findViewById(R.id.integral_tv_yes);
		tvTitle.setText("恭喜你，已领取优惠券");
		tvTicketStandard.setText(mList.get(position).getCouponRequire());
		tvTicketPrice.setText(mList.get(position).getFaceValue() + "");

		if (mList.get(position).getCouponType() == 1) {//1折扣券 2代金券3：兑换券 4：积分券5： 金额券
			tvPrice.setVisibility(View.GONE);
			tvTicketRebate.setText("折");
			tvTicketRebate.setVisibility(View.VISIBLE);
		}

		final CustomDialog builder = new CustomDialog(mContext, R.style.my_dialog).create(pview, false, 0.85f, 0.32f, 2.3f);
		builder.show();
		llCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				builder.dismiss();
			}
		});

		tvYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, HomeGoodsListActivity.class);
				intent.putExtra("productIds", mList.get(position).getProductIds());
				mContext.startActivity(intent);
				builder.dismiss();
			}
		});
	}


	private void doAsyncGetCoupon(final int position, final List<CouponReceiveEntity.DataBean.CouponsBean> mList) {
		final Map<String, String> map = new HashMap<>();
		map.put("userId", uid);
		map.put("type", "id");
		map.put("couponId", mList.get(position).getCouponId() + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/receiveCoupon").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {

				}
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetCoupon json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					Log.i(TAG, "onResponse: " + response.getMsg());
					conditionLimitnum--;
					mList.get(position).setConditionLimitnum(conditionLimitnum);
					notifyDataSetChanged();
					Toast.makeText(mContext, "领取成功", Toast.LENGTH_SHORT).show();
				} else {

				}
			}
		});
	}


	@Override
	public int getItemCount() {
		return mList.size() + 1;
	}


	class CouponReceiveFooterHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.buttom_coupon_phone)
		TextView tvPhone;

		public CouponReceiveFooterHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}


	class CouponReceiveHolder extends RecyclerView.ViewHolder {


		@BindView(R.id.mine_sign_text_ticket_price_symbol)
		TextView tvPrice;
		@BindView(R.id.mine_sign_text_ticket_price)
		TextView tvTicketPrice;
		@BindView(R.id.mine_sign_text_ticket_rebate)
		TextView tvTicketRebate;
		@BindView(R.id.mine_sign_text_ticket_price_standard)
		TextView tvTicketStandard;
		@BindView(R.id.mine_sign_text_ticket_class)
		TextView tvTicketClass;
		@BindView(R.id.mine_sign_text_ticket_class_content)
		TextView tvTicketContent;
		@BindView(R.id.mine_sign_text_ticket_class_time)
		TextView tvTicketTime;
		@BindView(R.id.mine_coupon_text_receive)
		TextView tvReceive;
		@BindView(R.id.mine_coupon_iv_background)
		ImageView ivBackground;


		private SpendDetialClickListener mListener;

		public CouponReceiveHolder(View itemView, SpendDetialClickListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = listener;

		}
	}

	public interface SpendDetialClickListener {
		void onItemClick(View view, int postion, int i);
	}
}