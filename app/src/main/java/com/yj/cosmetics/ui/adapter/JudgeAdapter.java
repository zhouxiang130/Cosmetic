package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.GoodsCommentEntity;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.yj.cosmetics.widget.StarBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class JudgeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "JudgeAdapter";
	private Context mContext;
	List<GoodsCommentEntity.GoodsCommentData.GoodsCommentList> mList;
	SpendDetialClickListener mItemClickListener;

	public JudgeAdapter(Context mContext, List<GoodsCommentEntity.GoodsCommentData.GoodsCommentList> mList) {
		this.mContext = mContext;
		this.mList = mList;
	}

	public void setOnItemClickListener(SpendDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		JudgeViewHolder judgeViewHolder;
		judgeViewHolder = new JudgeViewHolder(LayoutInflater
				.from(mContext).inflate(R.layout.item_judge, parent, false), mItemClickListener);
		return judgeViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof JudgeViewHolder) {
		    /*防止Bug 吧mark也写上了*/
			((JudgeViewHolder) holder).mStar.setStartTouchable(false);
			((JudgeViewHolder) holder).mStar.setStarCount(5);
			((JudgeViewHolder) holder).mStar.setStarMark(5 - Integer.parseInt(mList.get(position).getScore()));
			((JudgeViewHolder) holder).mStar.setIntegerMark(true);

			((JudgeViewHolder) holder).tvName.setText(mList.get(position).getUserName());
			((JudgeViewHolder) holder).tvContent.setText(mList.get(position).getCommentContent());
			((JudgeViewHolder) holder).tvDate.setText(mList.get(position).getInserttime());
			((JudgeViewHolder) holder).tvStyle.setText(mList.get(position).getSkuPropertiesName());
			if (!TextUtils.isEmpty(mList.get(position).getUserHeadimg())) {
				Glide.with(mContext)
						.load(URLBuilder.getUrl(mList.get(position).getUserHeadimg()))
						.asBitmap()
						.error(R.mipmap.default_avatar)
						.centerCrop()
						.into(((JudgeViewHolder) holder).ivHeader);
			}


			if (mList.get(position).getCommentImg() != null && mList.get(position).getCommentImg().size() > 0) {
				((JudgeViewHolder) holder).llJudge.setVisibility(View.VISIBLE);
				((JudgeViewHolder) holder).iv1.setVisibility(View.VISIBLE);
				((JudgeViewHolder) holder).iv2.setVisibility(View.GONE);
				((JudgeViewHolder) holder).iv3.setVisibility(View.GONE);
				Glide.with(mContext)
						.load(URLBuilder.getUrl( mList.get(position).getCommentImg().get(0)))
						.asBitmap()
						.error(R.mipmap.default_goods)
						.centerCrop()
						.into(((JudgeViewHolder) holder).iv1);
				if (mList.get(position).getCommentImg().size() > 1) {
					((JudgeViewHolder) holder).iv2.setVisibility(View.VISIBLE);
					Glide.with(mContext)
							.load(URLBuilder.getUrl( mList.get(position).getCommentImg().get(1)))
							.asBitmap()
							.error(R.mipmap.default_goods)
							.centerCrop()
							.into(((JudgeViewHolder) holder).iv2);
					if (mList.get(position).getCommentImg().size() > 2) {
						((JudgeViewHolder) holder).iv3.setVisibility(View.VISIBLE);
						Glide.with(mContext)
								.load(URLBuilder.getUrl( mList.get(position).getCommentImg().get(2)))
								.asBitmap()
								.error(R.mipmap.default_goods)
								.centerCrop()
								.into(((JudgeViewHolder) holder).iv3);
					}
				}

			} else {
				((JudgeViewHolder) holder).llJudge.setVisibility(View.GONE);
			}
//            if (mList.get(position).getCommentImg() != null && mList.get(position).getCommentImg().size() > 0) {
//                ((CouponReceiveHolder) holder).scrollGridView.setVisibility(View.VISIBLE);
//                Log.i(TAG, "onBindViewHolder: " + position + " ====  " + mList.get(position).getCommentImg().size());
//                judgeImgParamsAdapter judgeImgParamsAdapter = new judgeImgParamsAdapter(mContext, mList.get(position).getCommentImg());
//                ((CouponReceiveHolder) holder).scrollGridView.setAdapter(judgeImgParamsAdapter);
//            } else {
//                ((CouponReceiveHolder) holder).scrollGridView.setVisibility(View.GONE);
//            }
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	class JudgeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.goods_judgeiv_rb)
		StarBar mStar;
		@BindView(R.id.goods_judge_iv)
		RoundedImageView ivHeader;
		@BindView(R.id.goods_judge_name)
		TextView tvName;
		@BindView(R.id.goods_judge_date)
		TextView tvDate;
		@BindView(R.id.goods_judge_content)
		TextView tvContent;
		@BindView(R.id.goods_judge_style)
		TextView tvStyle;

		@BindView(R.id.goods_judge_ll)
		LinearLayout llJudge;
		@BindView(R.id.goods_judge_iv1)
		ImageView iv1;
		@BindView(R.id.goods_judge_iv2)
		ImageView iv2;
		@BindView(R.id.goods_judge_iv3)
		ImageView iv3;
		//@TODO---------------------------
//        @BindView(R.id.view_charge)
//        NoScrollGridView scrollGridView;

		private SpendDetialClickListener mListener;

		public JudgeViewHolder(View itemView, SpendDetialClickListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = listener;
			iv1.setOnClickListener(this);
			iv2.setOnClickListener(this);
			iv3.setOnClickListener(this);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                }
//            });

		}

		@Override
		public void onClick(View v) {
			if (mListener != null) {
				switch (v.getId()) {
					case R.id.goods_judge_iv1:
						mListener.onItemClick(v, getPosition(), 0);
						break;
					case R.id.goods_judge_iv2:
						mListener.onItemClick(v, getPosition(), 1);
						break;
					case R.id.goods_judge_iv3:
						mListener.onItemClick(v, getPosition(), 2);
						break;
				}
//                scrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Log.i(TAG, "onItemClick--------------: " + position);
//                        mListener.onItemClick(view, getPosition(), position);
//                    }
//                });
			}
		}
	}

	public interface SpendDetialClickListener {
		void onItemClick(View view, int postion, int i);
	}
}