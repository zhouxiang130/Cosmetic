package com.yj.cosmetics.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.JudgeGoodsDataEntity;
import com.yj.cosmetics.ui.activity.PostJudgeGoodsActivity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.yj.cosmetics.widget.StarBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.engine.GlideEngine;

/**
 * Created by Suo on 2017/4/17.
 */

public class PostJudgeGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private PostJudgeGoodsActivity mContext;
	CouponClickListener mItemClickListener;
	List<JudgeGoodsDataEntity.DataBean> entity;
	private Animation animation;
	private int i;
	private boolean close = true;
	private boolean picker = true;

	public PostJudgeGoodsAdapter(PostJudgeGoodsActivity mContext, List<JudgeGoodsDataEntity.DataBean> entity) {
		this.mContext = mContext;
		this.entity = entity;
		this.animation = AnimationUtils.loadAnimation(mContext, R.anim.judge_img_scale);
	}

	public void setOnItemClickListener(CouponClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		JudgeGoodsViewHolder judgeGoodsViewHolder;
		judgeGoodsViewHolder = new JudgeGoodsViewHolder(LayoutInflater
				.from(mContext).inflate(R.layout.item_post_judge_goods, parent, false), mItemClickListener);
		return judgeGoodsViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof JudgeGoodsViewHolder) {
			((JudgeGoodsViewHolder) holder).rbStar.setIntegerMark(true);
			((JudgeGoodsViewHolder) holder).rbStar.setStarMark(5);
			entity.get(position).setStars("5");
			((JudgeGoodsViewHolder) holder).rbStar.setOnStarChangeListener(new StarBar.OnStarChangeListener() {
				@Override
				public void onStarChange(float mark) {
					entity.get(position).setStars((mark + "").charAt(0) + "");
				}
			});
			((JudgeGoodsViewHolder) holder).tvTitle.setText(entity.get(position).getPname());
			((JudgeGoodsViewHolder) holder).tvStyle.setText(entity.get(position).getPsku());
			((JudgeGoodsViewHolder) holder).tvNum.setText("X" + entity.get(position).getNums());
			((JudgeGoodsViewHolder) holder).tvPrice.setText(entity.get(position).getMoney());
			Glide.with(mContext)
					.load(URLBuilder.getUrl(entity.get(position).getPimg()))
					.error(R.mipmap.default_goods)
					.centerCrop()
					.into(((JudgeGoodsViewHolder) holder).ivJudge);

			if (entity.size() == 1) {
				((JudgeGoodsViewHolder) holder).vLine.setVisibility(View.GONE);
			}
			((JudgeGoodsViewHolder) holder).etComment.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

				}

				@Override
				public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
					LogUtils.i("我onTextChange了" + charSequence);
					entity.get(position).setComment(charSequence + "");
					((JudgeGoodsViewHolder) holder).tvTextNum.setText(((JudgeGoodsViewHolder) holder).etComment.getText().length() + "");
				}

				@Override
				public void afterTextChanged(Editable editable) {
				}
			});


			((JudgeGoodsViewHolder) holder).ivOffer1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (entity.get(position).getmSelected().size() < 1) {
//                        takePicture(position,(JudgeGoodsViewHolder) holder);
						mContext.takePosition = position;
						mContext.setHolder((JudgeGoodsViewHolder) holder);
						mContext.isWritePermissionAllowed();
					}
				}
			});
			((JudgeGoodsViewHolder) holder).ivOffer2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (entity.get(position).getmSelected().size() < 2) {
						takePicture(position, (JudgeGoodsViewHolder) holder);
					}
				}
			});
			((JudgeGoodsViewHolder) holder).ivOffer3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (entity.get(position).getmSelected().size() < 3) {
						takePicture(position, (JudgeGoodsViewHolder) holder);
					}
				}
			});
			((JudgeGoodsViewHolder) holder).ivClose1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					LogUtils.i("我点击close1了 startAnimation");
					if (close) {
						close = false;
						((JudgeGoodsViewHolder) holder).rl1.startAnimation(animation);
						i = 0;
						setAnimationListener(position, ((JudgeGoodsViewHolder) holder));
					}
				}
			});
			((JudgeGoodsViewHolder) holder).ivClose2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (close) {
						close = false;
						((JudgeGoodsViewHolder) holder).rl2.startAnimation(animation);
						i = 1;
						setAnimationListener(position, ((JudgeGoodsViewHolder) holder));
					}
				}
			});
			((JudgeGoodsViewHolder) holder).ivClose3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (close) {
						close = false;
						((JudgeGoodsViewHolder) holder).rl3.startAnimation(animation);
						i = 2;
						setAnimationListener(position, ((JudgeGoodsViewHolder) holder));
					}
				}
			});


		}
	}

	@Override
	public int getItemCount() {
		return entity.size();
	}

	public class JudgeGoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.judge_show_offer1)
		RoundedImageView ivOffer1;
		@BindView(R.id.judge_show_offer2)
		RoundedImageView ivOffer2;
		@BindView(R.id.judge_show_offer3)
		RoundedImageView ivOffer3;
		@BindView(R.id.judge_show_offer1close)
		ImageView ivClose1;
		@BindView(R.id.judge_show_offer2close)
		ImageView ivClose2;
		@BindView(R.id.judge_show_offer3close)
		ImageView ivClose3;
		@BindView(R.id.judge_offer1_rl1)
		RelativeLayout rl1;
		@BindView(R.id.judge_offer2_rl2)
		RelativeLayout rl2;
		@BindView(R.id.judge_offer3_rl3)
		RelativeLayout rl3;
		@BindView(R.id.judge_show_rating)
		StarBar rbStar;
		@BindView(R.id.judge_show_content)
		EditText etComment;
		@BindView(R.id.item_post_judge_iv1)
		ImageView ivJudge;
		@BindView(R.id.judge_goods_line)
		View vLine;
		@BindView(R.id.post_judge_tv_text_num)
		TextView tvTextNum;
		@BindView(R.id.item_settle_price)
		TextView tvPrice;
		@BindView(R.id.item_settle_title)
		TextView tvTitle;
		@BindView(R.id.item_settle_num)
		TextView tvNum;
		@BindView(R.id.item_settle_style)
		TextView tvStyle;
		private CouponClickListener mListener;

		public JudgeGoodsViewHolder(View itemView, CouponClickListener listener) {
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

		public String getComment() {
			return etComment.getText().toString();
		}

	}

	public interface CouponClickListener {
		void onItemClick(View view, int postion);
	}


	public void takePicture(int position, JudgeGoodsViewHolder holder) {
		if (picker && close) {
			picker = false;
			mContext.setPosition(position);
			mContext.setHolder(holder);
			Picker.from(mContext).count(3 - entity.get(position).getmSelected().size()).enableCamera(false).setEngine(new GlideEngine()).forResult(200);

		}
	}

	private void setAnimationListener(final int position, final JudgeGoodsViewHolder holder) {
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				switch (i) {
					case 0:
						if (entity.get(position).getmSelected().size() >= 2) {
							LogUtils.i("我在size>=2了");
							entity.get(position).getmSelected().remove(0);
							glideImg(position, holder);
						} else {
							entity.get(position).getmSelected().remove(0);
							holder.ivOffer1.setImageResource(R.drawable.shape_corner_aa_stoke0_5_ed_radius2);
							holder.ivClose1.setVisibility(View.GONE);
							holder.rl2.setVisibility(View.GONE);
						}
						break;
					case 1:
						entity.get(position).getmSelected().remove(1);
						glideImg(position, holder);
						break;
					case 2:
						entity.get(position).getmSelected().remove(2);
						glideImg(position, holder);
						break;
				}
				close = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
	}

	public void glideImg(int position, JudgeGoodsViewHolder holder) {
		LogUtils.i("glideImg: " + entity.get(position).getmSelected().get(0));

		for (int i = 0; i < entity.get(position).getmSelected().size(); i++) {
			switch (i) {
				case 0:
					Glide.with(mContext.getApplicationContext()).load(entity.get(position).getmSelected().get(0)).into(holder.ivOffer1);
					holder.ivClose1.setVisibility(View.VISIBLE);
					if (entity.get(position).getmSelected().size() <= 1) {
						holder.rl2.setVisibility(View.VISIBLE);
						holder.ivOffer2.setImageResource(R.drawable.shape_corner_aa_stoke0_5_ed_radius2);
						holder.rl3.setVisibility(View.GONE);
						holder.ivClose2.setVisibility(View.GONE);
						holder.ivClose3.setVisibility(View.GONE);
					}

					break;
				case 1:
					holder.rl2.setVisibility(View.VISIBLE);
					holder.ivClose2.setVisibility(View.VISIBLE);
					LogUtils.i("我显示ivoClose2了");
					Glide.with(mContext.getApplicationContext()).load(entity.get(position).getmSelected().get(1)).into(holder.ivOffer2);
					holder.rl3.setVisibility(View.VISIBLE);
					holder.ivOffer3.setImageResource(R.drawable.shape_corner_aa_stoke0_5_ed_radius2);
					if (entity.get(position).getmSelected().size() <= 2) {
						holder.ivClose3.setVisibility(View.GONE);
					}
					break;
				case 2:
					holder.ivClose3.setVisibility(View.VISIBLE);
					Glide.with(mContext.getApplicationContext()).load(entity.get(position).getmSelected().get(2)).into(holder.ivOffer3);
					break;
			}
		}
	}

	public void setPicker(boolean picker) {
		this.picker = picker;
	}
}