package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.R;
import com.yj.cosmetics.model.AccountListEntity;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/6 0006.
 */

public class MyAccListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	private int falg;
	private List<AccountListEntity.DataBean.ListBean> data = null;
	private Context mContext = null;
	onItemClickListener mItemClickListener;


	public void setOnItemClickListener(onItemClickListener listener) {
		this.mItemClickListener = listener;
	}

	public MyAccListAdapter(Context mContext, List<AccountListEntity.DataBean.ListBean> data, int falg) {
		this.falg = falg;
		this.mContext = mContext;
		this.data = data;
	}


	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		PartakeViewHolder partakeViewHolder = new PartakeViewHolder(
				LayoutInflater.from(mContext).inflate(R.layout.item_myacc_list_item, parent, false), mItemClickListener);
		return partakeViewHolder;
	}


	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

		if (holder instanceof PartakeViewHolder) {

			Glide.with(mContext)
					.load(URLBuilder.getUrl(data.get(position).getHeadimg()))
					.asBitmap()
					.centerCrop()
					.error(R.mipmap.default_avatar)
					.into(((PartakeViewHolder) holder).ivStore);
			((PartakeViewHolder) holder).tvDateTime.setText(data.get(position).getInsertTime());
			((PartakeViewHolder) holder).shopName.setText(data.get(position).getShopName());
			if (falg == 0) {
				((PartakeViewHolder) holder).tvStoreLocs.setVisibility(View.GONE);
				((PartakeViewHolder) holder).tvStoreLoc.setVisibility(View.VISIBLE);
				((PartakeViewHolder) holder).tvStoreLoc.setText(data.get(position).getPayMoney());
				((PartakeViewHolder) holder).ivSuperScript.setBackgroundResource(R.mipmap.icon_superscripts);
				((PartakeViewHolder) holder).tvConsume.setVisibility(View.VISIBLE);
				if (data.get(position).getRowNum() > 9) {
					((PartakeViewHolder) holder).tvConsume.setText(String.valueOf(data.get(position).getRowNum()));
				} else {
					((PartakeViewHolder) holder).tvConsume.setText("0" + data.get(position).getRowNum());
				}
			} else {
				((PartakeViewHolder) holder).tvStoreLoc.setVisibility(View.INVISIBLE);
				((PartakeViewHolder) holder).tvStoreLocs.setVisibility(View.VISIBLE);
				((PartakeViewHolder) holder).tvStoreLocs.setText(data.get(position).getPayMoney());

				((PartakeViewHolder) holder).ivSuperScript.setBackgroundResource(R.mipmap.icon_superscript);
				((PartakeViewHolder) holder).tvConsume.setVisibility(View.GONE);
			}
//			((PartakeViewHolder) holder).pic.setText(data.get(position).getPayMoney());
//
//
//			((PartakeViewHolder) holder).rlGotoDetail.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
////					IntentUtils.IntentToStoreDetial(mContext, data.get(position).getSellers_id());
//				}
//			});
		}
	}

	@Override
	public int getItemCount() {
		if (data != null && data.size() > 0) {
			return data.size();
		} else {
			return 9;
		}
	}

	public void setData(List<AccountListEntity.DataBean.ListBean> data) {
		this.data = data;
		notifyDataSetChanged();
	}


	class PartakeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.item_partake_shop_img)
		RoundedImageView ivStore;
//		@BindView(R.id.tv_partake_shop_detail)
//		TextView pic;//价格
		@BindView(R.id.tv_partake_shop_name)
		TextView shopName; //名字
		@BindView(R.id.tv_partake_shop_loc)
		TextView tvStoreLoc;
		@BindView(R.id.tv_partake_shop_locs)
		TextView tvStoreLocs;
		@BindView(R.id.iv_top_suprescript)
		ImageView ivSuperScript;//日期名称 //消费 免单
		@BindView(R.id.tv_consume)
		TextView tvConsume;//已免单  /已消费
		@BindView(R.id.tv_date_time)
		TextView tvDateTime;//日期名称 //消费 免单
		@BindView(R.id.rl_to_store_detail)
		RelativeLayout rlGotoDetail;
		private onItemClickListener mListener;

		public PartakeViewHolder(View itemView, onItemClickListener mItemClickListener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = mItemClickListener;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onItemClick(v, getPosition());
			}
		}
	}

	public interface onItemClickListener {
		void onItemClick(View view, int postion);
	}
}
