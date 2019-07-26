package com.yj.cosmetics.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.model.HelpSugEntity;
import com.yj.cosmetics.ui.activity.mineHelpSug.MineHelpSugActivity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.engine.GlideEngine;

/**
 * Created by Suo on 2017/4/17.
 */

public class MineHelpSugAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "MineHelpSugAdapter";
	private List<HelpSugEntity.JudgeGoodsItem> entity = null;
	private MineHelpSugActivity mContext;
	CouponClickListener mItemClickListener;
	private Animation animation;
	private int i;
	private boolean close = true;
	private boolean picker = true;

	public MineHelpSugAdapter(MineHelpSugActivity mContext, List<HelpSugEntity.JudgeGoodsItem> entity) {
		this.mContext = mContext;
		this.entity = entity;
		this.animation = AnimationUtils.loadAnimation(mContext, R.anim.judge_img_scale);
	}

	public void setOnItemClickListener(CouponClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		JudgeGoodsViewHolder judgeGoodsViewHolder = null;
		ImageViewHolder mImageViewHolder;
		if (viewType == 0) {
			judgeGoodsViewHolder = new JudgeGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_post_judge_help_sug, parent, false), mItemClickListener);
			return judgeGoodsViewHolder;
		} else {
			//底部View
			mImageViewHolder = new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_post_judge_goods_buttom, parent, false));
			return mImageViewHolder;
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		} else if (position == 1) {
			return 1;
		}
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof JudgeGoodsViewHolder) {

			((JudgeGoodsViewHolder) holder).cartCb1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					((JudgeGoodsViewHolder) holder).cartCb1.setChecked(true);
					((JudgeGoodsViewHolder) holder).cartCb2.setChecked(false);
					((JudgeGoodsViewHolder) holder).cartCb3.setChecked(false);
					entity.get(position).setJudgeType(1);

				}
			});
			((JudgeGoodsViewHolder) holder).cartCb2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					((JudgeGoodsViewHolder) holder).cartCb1.setChecked(false);
					((JudgeGoodsViewHolder) holder).cartCb2.setChecked(true);
					((JudgeGoodsViewHolder) holder).cartCb3.setChecked(false);
					entity.get(position).setJudgeType(2);
				}
			});
			((JudgeGoodsViewHolder) holder).cartCb3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					((JudgeGoodsViewHolder) holder).cartCb1.setChecked(false);
					((JudgeGoodsViewHolder) holder).cartCb2.setChecked(false);
					((JudgeGoodsViewHolder) holder).cartCb3.setChecked(true);
					entity.get(position).setJudgeType(3);
				}
			});


			((JudgeGoodsViewHolder) holder).etComment.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				}


				@Override
				public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
					LogUtils.i("我onTextChange了" + charSequence);
//					entity.get(position).setComment(charSequence + "");
					entity.get(position).setJudgeContent(charSequence + "");
					((JudgeGoodsViewHolder) holder).tvInputNum.setText(((JudgeGoodsViewHolder) holder).etComment.getText().length() + "");
				}

				@Override
				public void afterTextChanged(Editable editable) {
				}
			});

			((JudgeGoodsViewHolder) holder).ivOffer1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (entity.get(position).getmSelected().size() < 1) {
						takePicture(position, (JudgeGoodsViewHolder) holder);
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

			((JudgeGoodsViewHolder) holder).ivOffer4.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (entity.get(position).getmSelected().size() < 4) {
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
			((JudgeGoodsViewHolder) holder).ivClose4.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (close) {
						close = false;
						((JudgeGoodsViewHolder) holder).rl4.startAnimation(animation);
						i = 3;
						setAnimationListener(position, ((JudgeGoodsViewHolder) holder));
					}
				}
			});

		}
	}

	@Override
	public int getItemCount() {
		return 2;
	}

	class ImageViewHolder extends RecyclerView.ViewHolder {


		public ImageViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}


	public class JudgeGoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.settlement_cart_cb1)
		CheckBox cartCb1;
		@BindView(R.id.mine_setting_tv_content1)
		TextView tvCause1;
		@BindView(R.id.settlement_cart_cb2)
		CheckBox cartCb2;
		@BindView(R.id.mine_setting_tv_content2)
		TextView tvCause2;
		@BindView(R.id.settlement_cart_cb3)
		CheckBox cartCb3;
		@BindView(R.id.mine_setting_tv_content3)
		TextView tvCause3;
		@BindView(R.id.text_img_num_)
		TextView tvImgNum;
		@BindView(R.id.judge_show_offer1)
		RoundedImageView ivOffer1;
		@BindView(R.id.judge_show_offer2)
		RoundedImageView ivOffer2;
		@BindView(R.id.judge_show_offer3)
		RoundedImageView ivOffer3;
		@BindView(R.id.judge_show_offer4)
		RoundedImageView ivOffer4;
		@BindView(R.id.judge_show_offer1close)
		ImageView ivClose1;
		@BindView(R.id.judge_show_offer2close)
		ImageView ivClose2;
		@BindView(R.id.judge_show_offer3close)
		ImageView ivClose3;
		@BindView(R.id.judge_show_offer4close)
		ImageView ivClose4;
		@BindView(R.id.judge_offer1_rl1)
		RelativeLayout rl1;
		@BindView(R.id.judge_offer2_rl2)
		RelativeLayout rl2;
		@BindView(R.id.judge_offer3_rl3)
		RelativeLayout rl3;
		@BindView(R.id.judge_offer3_rl4)
		RelativeLayout rl4;
		@BindView(R.id.input_text_num)
		TextView tvInputNum;
		@BindView(R.id.judge_show_content)
		EditText etComment;


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
			Picker.from(mContext).count(4 - entity.get(position).getmSelected().size()).enableCamera(false).setEngine(new GlideEngine()).forResult
					(200);

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
						if (entity.get(position).getmSelected().size() >= 3) {
							LogUtils.i("我在size>=2了");
							entity.get(position).getmSelected().remove(0);
							glideImg(position, holder);
						} else {
							entity.get(position).getmSelected().remove(0);
							glideImg(position, holder);
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

					case 3:
						entity.get(position).getmSelected().remove(3);
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

		holder.tvImgNum.setText(entity.get(position).getmSelected().size() + "");
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
						holder.ivClose4.setVisibility(View.GONE);
					}
					break;
				case 1:
					holder.rl2.setVisibility(View.VISIBLE);
					holder.ivClose2.setVisibility(View.VISIBLE);
					LogUtils.i("我显示ivoClose2了");
					Glide.with(mContext.getApplicationContext()).load(entity.get(position).getmSelected().get(1)).into(holder.ivOffer2);
					if (entity.get(position).getmSelected().size() <= 2) {
						holder.rl3.setVisibility(View.VISIBLE);
						holder.ivOffer3.setImageResource(R.drawable.shape_corner_aa_stoke0_5_ed_radius2);
						holder.ivClose3.setVisibility(View.GONE);
						holder.rl4.setVisibility(View.GONE);
						holder.ivClose4.setVisibility(View.GONE);
					}
					break;
				case 2:
					holder.ivClose3.setVisibility(View.VISIBLE);
					holder.rl3.setVisibility(View.VISIBLE);
					Glide.with(mContext.getApplicationContext()).load(entity.get(position).getmSelected().get(2)).into(holder.ivOffer3);
					if (entity.get(position).getmSelected().size() <= 3) {
						holder.ivOffer4.setImageResource(R.drawable.shape_corner_aa_stoke0_5_ed_radius2);
						holder.rl4.setVisibility(View.VISIBLE);
						holder.ivClose4.setVisibility(View.GONE);
					}
					break;
				case 3:
					holder.ivClose4.setVisibility(View.VISIBLE);
					holder.rl4.setVisibility(View.VISIBLE);
					Glide.with(mContext.getApplicationContext()).load(entity.get(position).getmSelected().get(3)).into(holder.ivOffer4);
					break;
			}
		}
	}

	public void setPicker(boolean picker) {
		this.picker = picker;
	}
}