package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.SettlementsCartEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class SettlementCartAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<SettlementsCartEntity.DataBean.ProArrayBean.ShopProArrayBean> mList;
    SpendDetialClickListener mItemClickListener;

    public SettlementCartAdapters(Context mContext, List<SettlementsCartEntity.DataBean.ProArrayBean.ShopProArrayBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    public void setOnItemClickListener(SpendDetialClickListener listener) {
        this.mItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CostViewHolder costViewHolder;
        costViewHolder = new CostViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_settlement_goods, parent, false), mItemClickListener);
        return costViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof CostViewHolder){
            ((CostViewHolder) holder).tvTitle.setText(mList.get(position).getProName());
            ((CostViewHolder) holder).tvStyle.setText(mList.get(position).getSkuPropertiesName());
            ((CostViewHolder) holder).tvPrice.setText(mList.get(position).getSkuPrice());
            ((CostViewHolder) holder).tvNum.setText("X"+mList.get(position).getNum());
            Glide.with(mContext)
                    .load(URLBuilder.getUrl(mList.get(position).getProImg()))
                    .asBitmap()
                    .centerCrop()
                    .error(R.mipmap.default_goods)
                    .into(((CostViewHolder) holder).iv);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class CostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.item_settle_iv1)
        ImageView iv;
        @BindView(R.id.item_settle_price)
        TextView tvPrice;
        @BindView(R.id.item_settle_title)
        TextView tvTitle;
        @BindView(R.id.item_settle_num)
        TextView tvNum;
        @BindView(R.id.item_settle_style)
        TextView tvStyle;


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