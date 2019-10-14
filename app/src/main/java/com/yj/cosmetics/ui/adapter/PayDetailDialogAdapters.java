package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class PayDetailDialogAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private final String[] payMode;
	private final Integer[] payIcon;
	private Context mContext;
	private int checkedPosition = -1;
	private Map<Integer, Boolean> map = new HashMap<>();
	private boolean onBind;

	public PayDetailDialogAdapters(Context mContext, String[] payMode, Integer[] payIcon) {
		this.mContext = mContext;
		this.payMode = payMode;
		this.payIcon = payIcon;


	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ItemViewHolder itemViewHolder;
		itemViewHolder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_paymode_dialog, parent, false));
		return itemViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof ItemViewHolder) {
			((ItemViewHolder) holder).tvDesc.setText(payMode[position]);
			((ItemViewHolder) holder).payIcon.setBackgroundResource(payIcon[position]);

			if (checkedPosition == position) {
				map.put(position, true);
			}
			((ItemViewHolder) holder).refundRl.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					boolean isChecked = ((ItemViewHolder) holder).refundCb.isChecked();
					LogUtils.e("onBindViewHolder:---- " + isChecked + " checkedPosition : " + checkedPosition + " position : " + position);
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
						LogUtils.e("onClick: ------------------------------");
//						((ItemViewHolder) holder).refundCb.setChecked(false);
//						map.remove(position);
//						if (map.size() == 0) {
//							checkedPosition = -1; //-1 代表一个都未选择
//						}
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
		return payMode.length;//Coupon.size();
	}

	class ItemViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.refund_dialog_rl_)
		RelativeLayout refundRl;
		@BindView(R.id.dialog_refund_tv_desc)
		TextView tvDesc;
		@BindView(R.id.dialog_refund_cb_)
		CheckBox refundCb;
		@BindView(R.id.dialog_pay_iv_icon)
		ImageView payIcon;


		public ItemViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

}