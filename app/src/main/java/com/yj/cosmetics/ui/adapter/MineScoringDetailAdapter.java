package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.model.ScoringDetailEntity;
import com.yj.cosmetics.widget.MaterialRippleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class MineScoringDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context mContext;
	List<ScoringDetailEntity.ScoringDetialData> mList;
	ProfitDetialClickListener mItemClickListener;
	private int flag;

	public MineScoringDetailAdapter(Context mContext, List<ScoringDetailEntity.ScoringDetialData> mList, int flag) {
		this.mContext = mContext;
		this.mList = mList;
		this.flag = flag;
	}

	public void setOnItemClickListener(ProfitDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ProfitViewHolder profitViewHolder;
		profitViewHolder = new ProfitViewHolder(MaterialRippleView.create(LayoutInflater
				.from(mContext).inflate(R.layout.item_mine_scoring_detail, parent, false)), mItemClickListener);
		return profitViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof ProfitViewHolder) {
			if (position == 0) {
				((ProfitViewHolder) holder).v1.setVisibility(View.VISIBLE);
			} else {
				((ProfitViewHolder) holder).v1.setVisibility(View.GONE);
			}

			((ProfitViewHolder) holder).tvName.setText(mList.get(position).getName());

			if (mList.get(position).getUserName() != null && !"".equals(mList.get(position).getUserName())) {
				((ProfitViewHolder) holder).tvAgent.setVisibility(View.VISIBLE);
				((ProfitViewHolder) holder).tvAgent.setText(mList.get(position).getUserName());
			} else {
				((ProfitViewHolder) holder).tvAgent.setVisibility(View.GONE);
			}

			if (flag == 1) {
				//全部积分
				String state = mList.get(position).getState();
				if (state.equals("1")) {
					((ProfitViewHolder) holder).tvMoney.setText("+" + mList.get(position).getScore());
				} else {
					((ProfitViewHolder) holder).tvMoney.setText("-" + mList.get(position).getScore());
				}
			} else if (flag == 2) {
				//积分收益
				if (mList.get(position).getScore().startsWith("-")) {
					((ProfitViewHolder) holder).tvMoney.setText(mList.get(position).getScore());
				} else {
					((ProfitViewHolder) holder).tvMoney.setText("+" + mList.get(position).getScore());
				}

			} else if (flag == 3) {
				//积分支出
				((ProfitViewHolder) holder).tvMoney.setText("-" + mList.get(position).getScore());
			}

			switch (mList.get(position).getAddAndSub()) {
				case "1":
					if (mList.get(position).getType() != null) {

						switch (mList.get(position).getType()) {//1=消费返利 2=推荐返现 3=系统更改 4=签到赠送；
							case "1":
								((ProfitViewHolder) holder).tvContent.setText("[消费返利]");
								break;
							case "2":
								((ProfitViewHolder) holder).tvContent.setText("[推荐返现]");
								break;
							case "3":
								((ProfitViewHolder) holder).tvContent.setText("[系统更改]");
								break;
							case "4":
								((ProfitViewHolder) holder).tvContent.setText("[签到赠送]");
								break;
						}
						break;
					}

				case "2":
					if (mList.get(position).getType() != null) {
						switch (mList.get(position).getType()) {// 1=待审核 2=审核通过 3=审核驳回 4=签到赠送
							case "1":
								((ProfitViewHolder) holder).tvContent.setText("[待审核]");
								break;
							case "2":
								((ProfitViewHolder) holder).tvContent.setText("[审核通过]");
								break;
							case "3":
								((ProfitViewHolder) holder).tvContent.setText("[审核驳回]");
								break;
							case "4":
								((ProfitViewHolder) holder).tvContent.setText("[签到赠送]");
								break;
						}
						break;
					}
			}

			((ProfitViewHolder) holder).tvTime.setText(mList.get(position).getTime());
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	class ProfitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.item_account_profit_v1)
		View v1;
		@BindView(R.id.item_scoring_detial_name)
		TextView tvName;
		@BindView(R.id.item_scoring_detial_money)
		TextView tvMoney;
		@BindView(R.id.item_scoring_detial_time)
		TextView tvTime;
		@BindView(R.id.item_scoring_detial_agent)
		TextView tvAgent;
		@BindView(R.id.item_scoring_detial_content)
		TextView tvContent;


		private ProfitDetialClickListener mListener;

		public ProfitViewHolder(View itemView, ProfitDetialClickListener listener) {
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

	public interface ProfitDetialClickListener {
		public void onItemClick(View view, int postion);
	}
}