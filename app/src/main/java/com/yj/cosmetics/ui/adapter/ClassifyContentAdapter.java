package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.ClassifyContentEntity;
import com.yj.cosmetics.ui.activity.HomeGoodsListActivity;
import com.yj.cosmetics.ui.activity.NormalWebViewActivity;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class ClassifyContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "ClassifyContentAdapter";
	private Context mContext;
	private ClassifyContentEntity.ClassifyContentData data;
	SpendDetialClickListener mItemClickListener;

	public ClassifyContentAdapter(Context mContext, ClassifyContentEntity.ClassifyContentData data) {
		this.mContext = mContext;
		this.data = data;
	}

	public void setOnItemClickListener(SpendDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ContentBannerViewHolder contentBannerHolder;
		ContentViewHolder contentViewHolder;
		if (viewType == 0) {
			contentBannerHolder = new ContentBannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_classify_content, parent, false));
			return contentBannerHolder;
		} else {
			contentViewHolder = new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_classify_content_goods, parent, false), mItemClickListener);
			return contentViewHolder;
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
		if (manager instanceof GridLayoutManager) {
			final GridLayoutManager gridManager = ((GridLayoutManager) manager);
			gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
				@Override
				public int getSpanSize(int position) {
					Log.i(TAG, "getSpanSize: " + position);
					if (position == 0) {
						return 3;
					} else {
						return 1;
					}
				}
			});
		}
	}


	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof ContentBannerViewHolder) {
			if (data.getBanner() != null) {
				Glide.with(mContext)
						.load(URLBuilder.getUrl(data.getBanner().getBanner_img()))
						.asBitmap()
						.centerCrop()
						.error(R.mipmap.default_banner_empty)
						.into(((ContentBannerViewHolder) holder).ivTop);
			}
			((ContentBannerViewHolder) holder).ivTop.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (data.getBanner() != null) {
						if (!data.getBanner().getProduct_id().equals("")) {
							if (!TextUtils.isEmpty(data.getBanner().getProduct_id())) {
								IntentUtils.IntentToGoodsDetial(mContext, data.getBanner().getProduct_id());
							} else if (!TextUtils.isEmpty(data.getBanner().getBanner_url())) {
								Intent intent = new Intent(mContext, NormalWebViewActivity.class);
								intent.putExtra("url", data.getBanner().getBanner_url());
								intent.putExtra("title", "no");
								mContext.startActivity(intent);
							} else {
								return;
							}
						}
					}
				}
			});
		} else if (holder instanceof ContentViewHolder) {
			Log.i(TAG, "getProductClassifys: " + data.getProductClassifys().size() + " ----position :>>>>>>>" + position);
			if (data.getProductClassifys() != null && data.getProductClassifys().size() > 0) {
//				if (position < data.getProductClassifys().size()) {
				((ContentViewHolder) holder).tvTitle.setText(data.getProductClassifys().get(position - 1).getClassify_name());
				//获取自定义的类实例
				Glide.with(mContext)
						.load(URLBuilder.getUrl(data.getProductClassifys().get(position - 1).getClassify_img()))
						.asBitmap()
						.error(R.mipmap.default_goods)
						.centerCrop()
						.into(((ContentViewHolder) holder).ivGoods);

//				}
				((ContentViewHolder) holder).llAll.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(mContext, HomeGoodsListActivity.class);
						intent.putExtra("classifyId", data.getProductClassifys().get(position - 1).getClassify_id());
						mContext.startActivity(intent);
					}
				});
			}else {

			}
		}
	}

	@Override
	public int getItemCount() {
		if (data != null) {
			return data.getProductClassifys().size() + 1;
		}
		return 0;
	}


	public void setData(ClassifyContentEntity.ClassifyContentData data) {
		this.data = data;
		notifyDataSetChanged();
	}

	class ContentBannerViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.classify_content_iv_top)
		RoundedImageView ivTop;

		public ContentBannerViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);


		}

	}

	class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.classify_content_goods_iv)
		ImageView ivGoods;
		@BindView(R.id.classify_content_goods_tv)
		TextView tvTitle;
		@BindView(R.id.classify_content_goods_ll)
		LinearLayout llAll;


		private SpendDetialClickListener mListener;

		public ContentViewHolder(View itemView, SpendDetialClickListener listener) {
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