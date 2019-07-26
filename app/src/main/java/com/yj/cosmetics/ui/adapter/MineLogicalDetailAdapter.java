package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.LogicalDetailEntity;
import com.yj.cosmetics.widget.MaterialRippleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class MineLogicalDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<LogicalDetailEntity.LogicalDetialData.LogicalDetialItem> mList = new ArrayList<>();
    LogicalDetialClickListener mItemClickListener;


    public MineLogicalDetailAdapter(Context mContext, List<LogicalDetailEntity.LogicalDetialData.LogicalDetialItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setOnItemClickListener(LogicalDetialClickListener listener) {
        this.mItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogicalDetialViewHolder logicalDetialViewHolder;
        logicalDetialViewHolder = new LogicalDetialViewHolder(MaterialRippleView.create(LayoutInflater
                .from(mContext).inflate(R.layout.item_mine_logical_detail, parent, false)), mItemClickListener);
        return logicalDetialViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LogicalDetialViewHolder) {
            if(position == 0){
                ((LogicalDetialViewHolder) holder).ivState.setImageResource(R.mipmap.round_check_fill_w);
//                ((LogicalDetialViewHolder) holder).tvState.setTextColor(mContext.getResources().getColor(R.color.CE8_3C_3C));
                ((LogicalDetialViewHolder) holder).tvContent.setTextColor(mContext.getResources().getColor(R.color.CE8_3C_3C));
            }else{
                ((LogicalDetialViewHolder) holder).ivState.setImageResource(R.mipmap.round_right_fill_h);
                ((LogicalDetialViewHolder) holder).tvContent.setTextColor(mContext.getResources().getColor(R.color.C85_85_85));
//                ((LogicalDetialViewHolder) holder).tvState.setTextColor(mContext.getResources().getColor(R.color.C85_85_85));
            }

            ((LogicalDetialViewHolder) holder).tvContent.setText(mList.get(position).getContext());
            String[] date = mList.get(position).getTime().split(" ")[0].split("-");
            String[] hours = mList.get(position).getTime().split(" ")[1].split(":");
            ((LogicalDetialViewHolder) holder).tvTime.setText(date[1]+"/"+date[2]);
            ((LogicalDetialViewHolder) holder).tvHour.setText(hours[0]+":"+hours[1]);
        }

    }

    @Override
    public int getItemCount() {
        if(mList != null && mList.size()>0) {
            return mList.size();
        }
        return 0;
    }

    class LogicalDetialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.logical_detial_iv_state)
        ImageView ivState;
        @BindView(R.id.logical_detial_tv_detial)
        TextView tvContent;
        @BindView(R.id.logical_detial_tv_time)
        TextView tvTime;
        @BindView(R.id.logical_detial_tv_hour)
        TextView tvHour;
        /*@BindView(R.id.logical_detial_tv_state)
        TextView tvState;*/
        private LogicalDetialClickListener mListener;

        public LogicalDetialViewHolder(View itemView, LogicalDetialClickListener listener) {
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

    public interface LogicalDetialClickListener {
        public void onItemClick(View view, int postion);
    }
}