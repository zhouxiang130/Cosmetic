package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.AccountProfitEntity;
import com.yj.cosmetics.widget.MaterialRippleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class AccountProfitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<AccountProfitEntity.AccountProfitData> mList;
    ProfitDetialClickListener mItemClickListener;

    public AccountProfitAdapter(Context mContext, List<AccountProfitEntity.AccountProfitData> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setOnItemClickListener(ProfitDetialClickListener listener) {
        this.mItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProfitViewHolder profitViewHolder;
        profitViewHolder = new ProfitViewHolder(MaterialRippleView.create(LayoutInflater
                .from(mContext).inflate(R.layout.item_mine_account_profit, parent, false)), mItemClickListener);
        return profitViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ProfitViewHolder ){
            if(position == 0){
                ((ProfitViewHolder) holder).v1.setVisibility(View.VISIBLE);
            }else{
                ((ProfitViewHolder) holder).v1.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(mList.get(position).getUser_name())){
                ((ProfitViewHolder) holder).tvTitle.setText(mList.get(position).getUser_name());
            }else if(!TextUtils.isEmpty(mList.get(position).getProductName())){
                ((ProfitViewHolder) holder).tvTitle.setText(mList.get(position).getProductName());
            }else{
                ((ProfitViewHolder) holder).tvTitle.setText("系统发放");
            }
            ((ProfitViewHolder) holder).tvName.setText("["+mList.get(position).getBack_mode()+"]");
            ((ProfitViewHolder) holder).tvTime.setText(mList.get(position).getInsert_time());
            ((ProfitViewHolder) holder).tvMoney.setText(mList.get(position).getBackmoney());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ProfitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_profit_money)
        TextView tvMoney;
        @BindView(R.id.item_profit_title)
        TextView tvTitle;
        @BindView(R.id.item_profit_name)
        TextView tvName;
        @BindView(R.id.item_profit_time)
        TextView tvTime;
        @BindView(R.id.item_account_profit_v1)
        View v1;
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