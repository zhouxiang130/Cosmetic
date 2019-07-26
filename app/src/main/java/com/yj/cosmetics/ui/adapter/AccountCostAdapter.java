package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.AccountCostEntity;
import com.yj.cosmetics.widget.MaterialRippleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class AccountCostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context mContext;
	List<AccountCostEntity.AccountCostData> mList;
	SpendDetialClickListener mItemClickListener;
	private int flag;

	public AccountCostAdapter(Context mContext, List<AccountCostEntity.AccountCostData> mList, int flag) {
		this.mContext = mContext;
		this.mList = mList;
		this.flag = flag;
	}

	public void setOnItemClickListener(SpendDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		CostViewHolder costViewHolder;
		costViewHolder = new CostViewHolder(MaterialRippleView.create(LayoutInflater
				.from(mContext).inflate(R.layout.item_mine_account_cost, parent, false)), mItemClickListener);
		return costViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof CostViewHolder) {
			if (position == 0) {
				((CostViewHolder) holder).view1.setVisibility(View.VISIBLE);
			} else {
				((CostViewHolder) holder).view1.setVisibility(View.GONE);
			}
			if (flag == 1) {
				//收益提现
				((CostViewHolder) holder).tvName.setText("收益提现");
				((CostViewHolder) holder).tvTime.setText(mList.get(position).getTime());
				((CostViewHolder) holder).tvPrice.setText(mList.get(position).getCashmoney() + "元");
				switch (mList.get(position).getCashState()) {
					//1：待审核2：提现成功3：提现失败
					case "1":
						((CostViewHolder) holder).tvState.setText("待审核");
						break;
					case "2":
						((CostViewHolder) holder).tvState.setText("提现成功");
						break;
					case "3":
						((CostViewHolder) holder).tvState.setText("提现失败");
						break;
				}

			} else {
				//余额抵扣
				((CostViewHolder) holder).tvName.setText(mList.get(position).getProductName());
				((CostViewHolder) holder).tvTime.setText(mList.get(position).getInsertTime());
				((CostViewHolder) holder).tvPrice.setText(mList.get(position).getConsumeMoney() + "元");
				switch (mList.get(position).getConsumeState()) {
					//1：待支付2：已支付3：取消支付
					case "1":
						((CostViewHolder) holder).tvState.setText("待支付");
						break;
					case "2":
						((CostViewHolder) holder).tvState.setText("已支付");
						break;
					case "3":
						((CostViewHolder) holder).tvState.setText("取消支付");
						break;

				}
			}
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	class CostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.item_account_cost_v1)
		View view1;
		@BindView(R.id.item_account_cost_state)
		TextView tvState;
		@BindView(R.id.item_account_cost_price)
		TextView tvPrice;
		@BindView(R.id.item_account_cost_time)
		TextView tvTime;
		@BindView(R.id.item_account_cost_name)
		TextView tvName;

		private SpendDetialClickListener mListener;

		public CostViewHolder(View itemView, SpendDetialClickListener listener) {
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
		public void onItemClick(View view, int postion);
	}
}