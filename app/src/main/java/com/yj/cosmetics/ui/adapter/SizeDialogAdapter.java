package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.GoodsSaleEntity;
import com.yj.cosmetics.widget.CustomViewGroup.CustomSizeDialogViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class SizeDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "SizeDialogAdapter";
	private Context mContext;
	private ArrayList<String> viewtexts = new ArrayList<>();
	private CustomSizeDialogViewGroup.OnGroupItemClickListener listener;
	private List<GoodsSaleEntity.GoodsSaleData> mSale;

	public SizeDialogAdapter(Context mContext, List<GoodsSaleEntity.GoodsSaleData> mSale, CustomSizeDialogViewGroup.OnGroupItemClickListener listener) {
		this.mContext = mContext;
		this.listener = listener;
		this.mSale = mSale;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ItemViewHolder itemViewHolder;
		itemViewHolder = new ItemViewHolder(LayoutInflater
				.from(mContext).inflate(R.layout.item_dialog_size, parent, false));
		return itemViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		if(holder instanceof ItemViewHolder){
			((ItemViewHolder) holder).tvTitle.setText(mSale.get(position).getPropesName());
			getNearlyLocation(((ItemViewHolder) holder).viewGroup,position);
		}
	}

	@Override
	public int getItemCount() {
		return mSale.size();
	}

	class ItemViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.dialog_view_group)
		CustomSizeDialogViewGroup viewGroup;
		@BindView(R.id.dialog_size_title)
		TextView tvTitle;

		public ItemViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
	private void getNearlyLocation(CustomSizeDialogViewGroup mViewGroup, int position){
		//@TODO ----- 修改bug处 数据size 为 10 说明数据体没问题
		Log.i(TAG, "getNearlyLocation: "+ mSale.get(position).getJsonArray().size());
		mViewGroup.addItemViews(mSale.get(position).getJsonArray(), CustomSizeDialogViewGroup.TEV_MODE,position);
		mViewGroup.setGroupClickListener(listener);
	}

}