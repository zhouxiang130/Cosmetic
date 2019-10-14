package com.yj.cosmetics.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.ShopListEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/6/6.
 */

public class StoreGoodListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private int pos = 0;
	private Activity mContext;
	List<ShopListEntity.DataBean.ShopArrayBean.ProductlistBean> mList;
	private boolean isShow = true;//是否显示编辑/完成
	private int position;
	ProfitDetialClickListener mItemClickListener;

	public StoreGoodListAdapter(Activity context, List<ShopListEntity.DataBean.ShopArrayBean.ProductlistBean> data, int pos) {
		this.mContext = context;
		this.mList = data;
		this.pos = pos;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		CartItemViewHolder cartItemViewHolder = new CartItemViewHolder(
				LayoutInflater.from(mContext).inflate(R.layout.item_store_list_img, viewGroup, false), mItemClickListener);
		return cartItemViewHolder;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((CartItemViewHolder) holder).hotlll.getLayoutParams();
		DisplayMetrics dm = new DisplayMetrics();
		//获取屏幕信息
		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		layoutParams.width = (int) (screenWidth * 0.26); // 宽度设置为屏幕的0.65
		layoutParams.height = (int) (screenWidth * 0.26); // 宽度设置为屏幕的0.65
		((CartItemViewHolder) holder).hotlll.setLayoutParams(layoutParams); //使设置好的布局参数应用到控件</pre>


		Glide.with(mContext)
				.load(URLBuilder.getUrl(mList.get(position).getProduct_listImg()))
				.asBitmap()
				.centerCrop()
				.error(R.mipmap.default_goods)
				.into(((CartItemViewHolder) holder).iv);

	}

	public void setOnItemClickListener(ProfitDetialClickListener listener) {
		this.mItemClickListener = listener;
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}


	class CartItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.store_list_iv1)
		ImageView iv;

		@BindView(R.id.home_hot_ll1)
		RelativeLayout hotlll;

		private ProfitDetialClickListener mListener;

		public CartItemViewHolder(View itemView, ProfitDetialClickListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = listener;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onItemClick(v, getPosition(), pos);
			}
		}
	}


	public interface ProfitDetialClickListener {
		void onItemClick(View view, int pos, int postion);
	}


	public int getPosition() {
		return position;
	}


}