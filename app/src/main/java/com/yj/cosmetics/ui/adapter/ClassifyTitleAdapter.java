package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.ClassifyEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class ClassifyTitleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    List<ClassifyEntity.ClassifyData.ClassifyItem> mList;
    SpendDetialClickListener mItemClickListener;
    public int mPosition = 0;

    public ClassifyTitleAdapter(Context mContext, List<ClassifyEntity.ClassifyData.ClassifyItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setOnItemClickListener(SpendDetialClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TitleViewHolder titleViewHolder;
        titleViewHolder = new TitleViewHolder(LayoutInflater
                .from(mContext).inflate(R.layout.item_classify_title, parent, false), mItemClickListener);
        return titleViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TitleViewHolder){
            if(mPosition == position){
                ((TitleViewHolder) holder).tvTitle.setTextColor(mContext.getResources().getColor(R.color.CE8_3C_3C));
                ((TitleViewHolder) holder).llTitle.setBackgroundColor(mContext.getResources().getColor(R.color.CF6_F8_F9));
            }else{
                ((TitleViewHolder) holder).tvTitle.setTextColor(mContext.getResources().getColor(R.color.C10_10_10));
                ((TitleViewHolder) holder).llTitle.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            ((TitleViewHolder) holder).tvTitle.setText(mList.get(position).getClassify_name());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.classify_title_ll)
        LinearLayout llTitle;
        @BindView(R.id.classify_title_tv)
        TextView tvTitle;
        private SpendDetialClickListener mListener;

        public TitleViewHolder(View itemView, SpendDetialClickListener listener) {
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
         void onItemClick(View view, int postion);
    }
}