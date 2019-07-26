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

public class MineScoringAccordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<ScoringDetailEntity.ScoringDetialData> mList;
    SpendDetialClickListener mItemClickListener;

    public MineScoringAccordAdapter(Context mContext, List<ScoringDetailEntity.ScoringDetialData> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setOnItemClickListener(SpendDetialClickListener listener) {
        this.mItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CostViewHolder costViewHolder;
        costViewHolder = new CostViewHolder(MaterialRippleView.create(LayoutInflater
                .from(mContext).inflate(R.layout.item_mine_scoring_accord, parent, false)), mItemClickListener);
        return costViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CostViewHolder){
            if (position == 0){
                ((CostViewHolder) holder).view1.setVisibility(View.VISIBLE);
            }else{
                ((CostViewHolder) holder).view1.setVisibility(View.GONE);
            }

            ((CostViewHolder) holder).tvTime.setText(mList.get(position).getTime());
            ((CostViewHolder) holder).tvName.setText("申请提现");
            switch (mList.get(position).getState()){
                //1：待审核2：审核通过3：审核驳回
                case "1":
                    ((CostViewHolder) holder).tvState.setText("待审核");
                    break;
                case "2":
                    ((CostViewHolder) holder).tvState.setText("审核通过");
                    break;
                case "3":
                    ((CostViewHolder) holder).tvState.setText("审核驳回");
                    break;
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
        @BindView(R.id.scoring_accord_name)
        TextView tvName;
        @BindView(R.id.scoring_accord_time)
        TextView tvTime;
        @BindView(R.id.scoring_accord_state)
        TextView tvState;

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