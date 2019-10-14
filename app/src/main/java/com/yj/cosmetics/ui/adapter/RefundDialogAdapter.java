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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class RefundDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "SizeDialogAdapter";
	private final List<String> refund_dialog_desc;
	private Context mContext;
	private Map<Integer, Boolean> map = new HashMap<>();
	private int checkedPosition = -1;
	private boolean onBind;

	public RefundDialogAdapter(Context mContext, List<String> refund_dialog_desc) {
		this.mContext = mContext;
		this.refund_dialog_desc = refund_dialog_desc;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ItemViewHolder itemViewHolder;
		itemViewHolder = new ItemViewHolder(LayoutInflater
				.from(mContext).inflate(R.layout.item_dialog_refund, parent, false));
		return itemViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof ItemViewHolder) {
			((ItemViewHolder) holder).tvDesc.setText(refund_dialog_desc.get(position));
//			((ItemViewHolder) holder).refundCb.setTag(new Integer(position));//设置tag 否则划回来时选中消失

			//此处添加是标记值
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
		return refund_dialog_desc.size();
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