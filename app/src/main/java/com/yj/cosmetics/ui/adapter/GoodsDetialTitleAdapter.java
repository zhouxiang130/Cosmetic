package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.widget.MaterialRippleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class GoodsDetialTitleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
//    private List<SpendDetialEntity.SpendDetialData> mList = new ArrayList<>();
    List<String> mList;
    SpendDetialClickListener mItemClickListener;

    public int mPosition = 0;

    public GoodsDetialTitleAdapter(Context mContext, /*List<SpendDetialEntity.SpendDetialData>*/List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setOnItemClickListener(SpendDetialClickListener listener) {
        this.mItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TitleViewHolder titleViewHolder;
        titleViewHolder = new TitleViewHolder(MaterialRippleView.create(LayoutInflater
                .from(mContext).inflate(R.layout.item_goods_detial_title, parent, false)), mItemClickListener);
        return titleViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TitleViewHolder){
            if(position == mPosition ){
                ((TitleViewHolder) holder).tvTitle.setTextSize(16);
                ((TitleViewHolder) holder).vBottom.setVisibility(View.VISIBLE);
            }else{
                ((TitleViewHolder) holder).tvTitle.setTextSize(14);
                ((TitleViewHolder) holder).vBottom.setVisibility(View.GONE);
            }
            ((TitleViewHolder) holder).tvTitle.setText(mList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_goods_detial_title_tv)
        TextView tvTitle;
        @BindView(R.id.item_goods_detial_title_v)
        View vBottom;
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
        public void onItemClick(View view, int postion);
    }
}