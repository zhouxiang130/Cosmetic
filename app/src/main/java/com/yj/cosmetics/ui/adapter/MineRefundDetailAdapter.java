package com.yj.cosmetics.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.model.RefundDetailEntity;
import com.yj.cosmetics.model.RefundListEntity;
import com.yj.cosmetics.ui.activity.MineRefundDetailTableActivity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.widget.Dialog.CustomRefundDialog;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.engine.GlideEngine;

/**
 * Created by Suo on 2017/4/17.
 */

public class MineRefundDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private String Money = null;
	private List<String> refund_dialog_desc = null;
	private List<RefundListEntity.DataBean> refundDialogDesc = null;
	private int tag = 0;  // 1 退款退data货;  2, 仅退款;
	private List<RefundDetailEntity.RefundDetailItem> entity = null;
	private MineRefundDetailTableActivity mContext;
	CouponClickListener mItemClickListener;
	private Animation animation;
	private int i;
	private boolean close = true;
	private boolean picker = true;
	CustomRefundDialog refundDialog;
	private int GoodStatusCheckedPosition, refundCausePosition;

	public MineRefundDetailAdapter(MineRefundDetailTableActivity mContext,
	                               List<RefundDetailEntity.RefundDetailItem> entity,
	                               int refund_detial_tag,
	                               List<String> refund_dialog_desc,
	                               List<RefundListEntity.DataBean> refundDialogDesc,
	                               String Money
	) {
		this.mContext = mContext;
		this.entity = entity;
		this.Money = Money;
		this.tag = refund_detial_tag;
		this.refund_dialog_desc = refund_dialog_desc;
		this.refundDialogDesc = refundDialogDesc;
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
			judgeGoodsViewHolder = new JudgeGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_post_refund_detail, parent, false), mItemClickListener);
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

			if (tag == 2) {//1 退款退货;  2, 仅退款;
				((JudgeGoodsViewHolder) holder).refundRlGoodStatus.setVisibility(View.GONE);
				((JudgeGoodsViewHolder) holder).refundViewLine.setVisibility(View.GONE);
			} else {
				((JudgeGoodsViewHolder) holder).refundRlGoodStatus.setVisibility(View.VISIBLE);
				((JudgeGoodsViewHolder) holder).refundViewLine.setVisibility(View.VISIBLE);
			}

			((JudgeGoodsViewHolder) holder).tvGoodStatus.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					showDialogs(1, refund_dialog_desc, refundDialogDesc, holder, position);
				}
			});
			((JudgeGoodsViewHolder) holder).tvCause.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					showDialogs(2, refund_dialog_desc, refundDialogDesc, holder, position);

				}
			});

			((JudgeGoodsViewHolder) holder).tvPrice.setHint(new SpannableString("退款金额: ￥" + Money));//这里输入自己想要的提示文字
			((JudgeGoodsViewHolder) holder).tvPicNum.setText("退款金额: ￥" + Money);
			((JudgeGoodsViewHolder) holder).tvPrice.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL); //输入类型为小数数字，允许十进制小数点提供分数值。

			((JudgeGoodsViewHolder) holder).tvPrice.addTextChangedListener(new TextWatcher() {


				@Override
				public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				}

				@Override
				public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
					String s = charSequence + "";
					LogUtils.e("onTextChanged: " + s);
					if (s != null && !s.equals("") && !TextUtils.isEmpty(s)) {
						boolean positiveInteger = isPositiveInteger(s);
						LogUtils.e("onTextChanged:  positiveInteger  " + positiveInteger);
						if (!positiveInteger) {
							Toast.makeText(mContext, "请输入的是数字", Toast.LENGTH_SHORT).show();
							return;
						}
						entity.get(position).setPrice(charSequence + "");
//						Pattern p =  Pattern.compile("/^[0-9]+([.]{1}[0-9]{1,2})?$/");
//						Matcher m = p.matcher(s);
//						if (m.matches()) {
//							Toast.makeText(mContext, "输入的是数字", Toast.LENGTH_SHORT).show();
//						}
//						 p  = Pattern.compile("[a-zA-Z]");
//						Matcher m = p.matcher(s);
//						if (m.matches()) {
//							Toast.makeText(mContext, "输入的是字母", Toast.LENGTH_SHORT).show();
//						}
//						 p = Pattern.compile("[\u4e00-\u9fa5]");
//						m = p.matcher(s);
//						if (m.matches()) {
//							Toast.makeText(mContext, "输入的是汉字", Toast.LENGTH_SHORT).show();
//						}
					}
//					if (Money.equals(s)) {
//						((JudgeGoodsViewHolder) holder).tvPrice.setTextColor(mContext.getResources().getColor(R.color.red));
//					} else {
//						((JudgeGoodsViewHolder) holder).tvPrice.setTextColor(mContext.getResources().getColor(R.color.C99_99_99));
//					}
				}

				@Override
				public void afterTextChanged(Editable editable) {
				}
			});

			((JudgeGoodsViewHolder) holder).tvReason.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				}

				@Override
				public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
					entity.get(position).setReason(charSequence + "");
				}

				@Override
				public void afterTextChanged(Editable editable) {
				}
			});

			((JudgeGoodsViewHolder) holder).tvPhone.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				}

				@Override
				public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
					entity.get(position).setPhone(charSequence + "");
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


	public static boolean isPositiveInteger(String orginal) {
		return isMatch("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])", orginal);
	}

	private static boolean isMatch(String regex, String orginal) {
		if (orginal == null || orginal.trim().equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher isNum = pattern.matcher(orginal);
		return isNum.matches();
	}

	private List<String > refundReason = null;
	private List<String > refundId = null;

	public void showDialogs(final int type, final List<String> refund_dialog_desc, final List<RefundListEntity.DataBean> refundDialogDesc, final RecyclerView.ViewHolder holder, final int position) {
		refundReason = new ArrayList<>();
		refundId = new ArrayList<>();
		for (int i = 0; i < refundDialogDesc.size(); i++) {
			refundReason.add(refundDialogDesc.get(i).getRefundReason());
			refundId.add(refundDialogDesc.get(i).getRefundId());
		}
		if (refundDialog == null) {
			refundDialog = new CustomRefundDialog(mContext);
		}

		if (type == 1) {
			refundDialog.setCustomDialog(refund_dialog_desc);
		} else {
			refundDialog.setCustomDialog(refundReason);
		}

		if (!refundDialog.isShowing()) {
			refundDialog.show();
		}

		refundDialog.getBtnFinish().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (type == 1) {
					GoodStatusCheckedPosition = refundDialog.getCheckedPosition();
					if (GoodStatusCheckedPosition != -1) {
						((JudgeGoodsViewHolder) holder).tvGoodStatus.setText(refund_dialog_desc.get(GoodStatusCheckedPosition));
						entity.get(position).setGoodStatus(GoodStatusCheckedPosition + 1);
					}
				} else {
					refundCausePosition = refundDialog.getCheckedPosition();
					if (refundCausePosition != -1) {
						((JudgeGoodsViewHolder) holder).tvCause.setText(refundReason.get(refundCausePosition));
						entity.get(position).setCause(refundId.get(refundCausePosition));
					}
				}
				refundDialog.dismiss();
			}
		});
		if (type == 1) {
			refundDialog.setCheckPosition(GoodStatusCheckedPosition);
		} else {
			refundDialog.setCheckPosition(refundCausePosition);
		}
	}

	public void setData(List<RefundListEntity.DataBean> data) {
		this.refundDialogDesc  =data;
	}


	class ImageViewHolder extends RecyclerView.ViewHolder {

		public ImageViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}


	public class JudgeGoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.refund_detail_good_status_line)
		View refundViewLine;
		@BindView(R.id.refund_detial_rl_good_status)
		RelativeLayout refundRlGoodStatus;
		@BindView(R.id.refund_tv_good_status)
		TextView tvGoodStatus;
		@BindView(R.id.refund_tv_cause)
		TextView tvCause;
		@BindView(R.id.refund_tv_price)
		EditText tvPrice;
		@BindView(R.id.refund_tv_price_num)
		TextView tvPicNum;
		@BindView(R.id.refund_et_explain)
		EditText tvReason;
		@BindView(R.id.refund_et_phone)
		EditText tvPhone;
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

//		holder.tvImgNum.setText(entity.get(position).getmSelected().size() + "");

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