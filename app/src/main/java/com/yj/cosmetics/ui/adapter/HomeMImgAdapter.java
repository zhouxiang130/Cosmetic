package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.HomeEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/13 0013.
 */

public class HomeMImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	private List<HomeEntity.HomeData.HomeBanners> mDatas = null;
	private Context mContext = null;
	private ImgViewClickListener imgViewClickListener;

	public HomeMImgAdapter(Context context, List<HomeEntity.HomeData.HomeBanners> mDatas) {
		this.mContext = context;
		this.mDatas = mDatas;
	}


	public void setOnItemClickListener(ImgViewClickListener listener) {
		this.imgViewClickListener = listener;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

		ImagViewHolders viewHolders = new ImagViewHolders(LayoutInflater
				.from(mContext).inflate(R.layout.item_home_item_imgs, viewGroup, false), imgViewClickListener);

		return viewHolders;
	}


	@Override

	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		if (viewHolder instanceof ImagViewHolders) {

			Glide.with(mContext)
					.load(URLBuilder.getUrl(mDatas.get(position).getBannerImg()))
//					.crossFade(500)//动画默认的持续时间是 300毫秒
//					.asBitmap()
					.centerCrop()
					.error(R.mipmap.default_goods)
//					.skipMemoryCache(true)
//					.diskCacheStrategy(DiskCacheStrategy.RESULT)
					.into(((ImagViewHolders) viewHolder).imageView);

		}
	}


	@Override
	public int getItemCount() {
		if (mDatas.size() != 0) {
			return mDatas.size();
		} else {
			return 0;
		}
	}


	class ImagViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.iv_imageview_)
		ImageView imageView;
		private ImgViewClickListener ClickListener;

		public ImagViewHolders(View itemView, ImgViewClickListener ClickListener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.ClickListener = ClickListener;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			if (ClickListener != null) {
				ClickListener.onItemClick(getPosition());
			}
		}
	}

	public interface ImgViewClickListener {
		void onItemClick(int positon);
	}

}
