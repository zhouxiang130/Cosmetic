package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.GoodsEntity;
import com.yj.cosmetics.model.GoodsEntitys;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2017/4/17.
 */

public class TicketDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "SizeDialogAdapter";
	private String uid = null;
	private GoodsEntity.GoodsData.ProDetailCouponBean proDetailCoupon;
	private Context mContext;
	private int conditionLimitnum;

	public TicketDialogAdapter(Context mContext, GoodsEntitys.DataBeanX proDetailCoupon, String uid) {
		this.mContext = mContext;
//		this.proDetailCoupon = proDetailCoupon.getProDetailCoupon();
		this.uid = uid;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ItemViewHolder itemViewHolder;
		itemViewHolder = new ItemViewHolder(LayoutInflater
				.from(mContext).inflate(R.layout.item_dialog_ticket, parent, false));
		return itemViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof ItemViewHolder) {

			((ItemViewHolder) holder).tvTicketTime.setText(proDetailCoupon.getCoupons().get(position).getDate());
			((ItemViewHolder) holder).tvTicketContent.setText(proDetailCoupon.getCoupons().get(position).getCouponName());
			((ItemViewHolder) holder).tvTicketClass.setText(proDetailCoupon.getCoupons().get(position).getCouponTypeMsg());
			((ItemViewHolder) holder).tvTicketStandard.setText(proDetailCoupon.getCoupons().get(position).getCouponRequire());
			((ItemViewHolder) holder).tvTicketPrice.setText(proDetailCoupon.getCoupons().get(position).getFaceValue() + "");
			switch (proDetailCoupon.getCoupons().get(position).getCouponType()) {
				case 1:

					((ItemViewHolder) holder).ivBackground.setBackgroundResource(R.mipmap.zhuanzengyouhuiquan_1);
					((ItemViewHolder) holder).tvTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_four);
					break;
				case 2:
					((ItemViewHolder) holder).ivBackground.setBackgroundResource(R.mipmap.zhuanzengyouhuiquan_3);
					((ItemViewHolder) holder).tvTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_three);
					break;
				case 3:
					((ItemViewHolder) holder).ivBackground.setBackgroundResource(R.mipmap.zhuanzengyouhuiquan_2);
					((ItemViewHolder) holder).tvTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_five);
					break;
			}
			if (proDetailCoupon.getCoupons().get(position).getCouponType() == 1) {//1折扣券 2代金券3：兑换券 4：积分券5： 金额券
				((ItemViewHolder) holder).tvPrice.setVisibility(View.GONE);
				((ItemViewHolder) holder).tvTicketRebate.setText("折");
				((ItemViewHolder) holder).tvTicketRebate.setVisibility(View.VISIBLE);
			} else {
				((ItemViewHolder) holder).tvPrice.setVisibility(View.VISIBLE);
				((ItemViewHolder) holder).tvTicketRebate.setVisibility(View.GONE);
			}
			conditionLimitnum = proDetailCoupon.getCoupons().get(position).getConditionLimitnum();
			if (conditionLimitnum == 0) {
				((ItemViewHolder) holder).tvReceive.setText("已领取");
				((ItemViewHolder) holder).tvReceive.setTextColor(mContext.getResources().getColor(R.color.C99_99_99));
				((ItemViewHolder) holder).tvReceive.setBackgroundResource(R.drawable.shape_corner_e8_stroke0_5_radius17);
				((ItemViewHolder) holder).tvReceive.setEnabled(false);
				return;
			}

			((ItemViewHolder) holder).tvReceive.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					conditionLimitnum = proDetailCoupon.getCoupons().get(position).getConditionLimitnum();
					Log.i(TAG, "onClick: " + conditionLimitnum);
					if (conditionLimitnum == 0) {

						((ItemViewHolder) holder).tvReceive.setText("已领取");
						((ItemViewHolder) holder).tvReceive.setTextColor(mContext.getResources().getColor(R.color.C99_99_99));
						((ItemViewHolder) holder).tvReceive.setBackgroundResource(R.drawable.shape_corner_e8_stroke0_5_radius17);
						((ItemViewHolder) holder).tvReceive.setEnabled(false);
						Toast.makeText(mContext, "您领取已达上限", Toast.LENGTH_SHORT).show();
						return;

					} else {
						conditionLimitnum--;
						doAsyncGetCoupon(position, proDetailCoupon);
					}
				}
			});
		}
	}

	private void doAsyncGetCoupon(final int position, final GoodsEntity.GoodsData.ProDetailCouponBean mList) {
		final Map<String, String> map = new HashMap<>();
		map.put("userId", uid);
		map.put("type", "id");
		map.put("couponId", mList.getCoupons().get(position).getCouponId() + "");
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
					mList.getCoupons().get(position).setConditionLimitnum(conditionLimitnum);
					Toast.makeText(mContext, "领取成功", Toast.LENGTH_SHORT).show();
				} else {

				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return proDetailCoupon.getCoupons().size();
	}

	class ItemViewHolder extends RecyclerView.ViewHolder {

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


		public ItemViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

}