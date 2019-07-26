package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.SettlementGoodsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class TicketDetailDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "SizeDialogAdapter";
	private List<SettlementGoodsEntity.SettlementGoodsData.CouponsBean> proDetailCoupon;
	private Context mContext;
	private int checkedPosition = -1;
	private Map<Integer, Boolean> map = new HashMap<>();
	private boolean onBind;

	public TicketDetailDialogAdapter(Context mContext, List<SettlementGoodsEntity.SettlementGoodsData.CouponsBean> proDetailCoupon) {
		this.mContext = mContext;
		this.proDetailCoupon = proDetailCoupon;

	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ItemViewHolder itemViewHolder;
		itemViewHolder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ticketdetail_dialog_ticket, parent, false));
		return itemViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof ItemViewHolder) {
			((ItemViewHolder) holder).tvDesc.setText(proDetailCoupon.get(position).getCouponName());


			if (checkedPosition == position) {
				map.put(position, true);
			}

			((ItemViewHolder) holder).refundRl.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					boolean isChecked = ((ItemViewHolder) holder).refundCb.isChecked();

					if (checkedPosition != position) {
						if (!isChecked == true) {
							map.clear();
							map.put(position, true);
							checkedPosition = position;
						} else {
							map.remove(position);
							if (map.size() == 0) {
								checkedPosition = -1; //-1 代表一个都未选择
							}
						}
					} else {
						((ItemViewHolder) holder).refundCb.setChecked(false);
						map.remove(position);
						if (map.size() == 0) {
							checkedPosition = -1; //-1 代表一个都未选择
						}
					}
					if (!onBind) {
						notifyDataSetChanged();
					}
				}
			});

			onBind = true;
			if (map != null && map.containsKey(position)) {
				((ItemViewHolder) holder).refundCb.setChecked(true);
			} else {
				((ItemViewHolder) holder).refundCb.setChecked(false);
			}
			onBind = false;
		}
	}

	//得到当前选中的位置
	public int getCheckedPosition() {
		return checkedPosition;
	}

	public void setCheckPosition(int checkedPosition) {
		this.checkedPosition = checkedPosition;
	}


	@Override
	public int getItemCount() {
		return proDetailCoupon.size();
	}

	class ItemViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.refund_dialog_rl_)
		RelativeLayout refundRl;
		@BindView(R.id.dialog_refund_tv_desc)
		TextView tvDesc;
		@BindView(R.id.dialog_refund_cb_)
		CheckBox refundCb;


		public ItemViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

}