package com.yj.cosmetics.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.CouponListDataEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.MatchUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2017/4/17.
 */

public class MineCouponDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private String uid = null;
	private Activity mContext;
	List<CouponListDataEntity.DataBean.ListBean> mList;
	CouponDetialClickListener mItemClickListener;
	private int flag;
	public int VISIBLE = 0x00000000;
	public static final int GONE = 0x00000008;
	private CustomDialog builder;

	public MineCouponDetailAdapter(Activity mContext, List<CouponListDataEntity.DataBean.ListBean> mList, int flag, String uid) {
		this.mContext = mContext;
		this.mList = mList;
		this.flag = flag;
		this.uid = uid;
		LogUtils.e("onBindViewHolder: " + flag);
	}

	public void setOnItemClickListener(CouponDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		CouponViewHolder CouponViewHolder = new CouponViewHolder(
				LayoutInflater.from(mContext).inflate(R.layout.item_mine_coupon_detail, parent, false), mItemClickListener);
		return CouponViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof CouponViewHolder) {

			((CouponViewHolder) holder).TextTicketClassTime.setText(mList.get(position).getDate());
			((CouponViewHolder) holder).TextTicketClassContent.setText(mList.get(position).getCouponName());
			((CouponViewHolder) holder).TextTicketClass.setText(mList.get(position).getCouponTypeMsg());
			((CouponViewHolder) holder).TextTicketPriceStandard.setText(mList.get(position).getCouponRequire());
			((CouponViewHolder) holder).TextTicketPrice.setText(mList.get(position).getFaceValue() + "");

			if (mList.get(position).getCouponType() == 1) {//1折扣券 2代金券3：兑换券 4：积分券5： 金额券
				((CouponViewHolder) holder).TextTicketPriceSymbol.setVisibility(View.GONE);
				((CouponViewHolder) holder).TextTicketRebate.setText("折");
				((CouponViewHolder) holder).TextTicketRebate.setVisibility(View.VISIBLE);
			} else {
				((CouponViewHolder) holder).TextTicketPriceSymbol.setVisibility(View.VISIBLE);
				((CouponViewHolder) holder).TextTicketRebate.setVisibility(View.GONE);
			}

			if (flag == 0) {//优惠券
				((CouponViewHolder) holder).ivDeadLine.setVisibility(View.GONE);
				((CouponViewHolder) holder).mineCouponTextReceive.setVisibility(View.VISIBLE);
				switch (mList.get(position).getCouponType()) {
					case 1:
						((CouponViewHolder) holder).ivBackground.setBackgroundResource(R.mipmap.youhuiquan_bg2);
						((CouponViewHolder) holder).TextTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_four);
						break;
					case 2:
						((CouponViewHolder) holder).ivBackground.setBackgroundResource(R.mipmap.youhuiquan_bg1);
						((CouponViewHolder) holder).TextTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_three);
						break;
					case 3:
						((CouponViewHolder) holder).ivBackground.setBackgroundResource(R.mipmap.youhuiquan_bg);
						((CouponViewHolder) holder).TextTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_five);
						break;
				}


				int visibility = ((CouponViewHolder) holder).mineCouponLlAll.getVisibility();
				if (visibility != 0x00000008) {
					((CouponViewHolder) holder).mineCouponLlAll.setVisibility(View.GONE);
					((CouponViewHolder) holder).ivIndicate.setBackgroundResource(R.mipmap.youhuiquan_zhankai);
				}

				((CouponViewHolder) holder).mineCouponTextReceive.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						IntentUtils.IntentToGoodsList(mContext, mList.get(position).getClassifyId());
					}
				});


				if (mList.get(position).getCouponShare() == 1) {
					((CouponViewHolder) holder).tvShare.setVisibility(View.VISIBLE);
				} else {
					((CouponViewHolder) holder).tvShare.setVisibility(View.GONE);
				}

				if (!mList.get(position).getConditionDesc().equals("")) {
					((CouponViewHolder) holder).tvTitle.setVisibility(View.VISIBLE);
				} else {
					((CouponViewHolder) holder).tvTitle.setVisibility(View.GONE);
				}
				((CouponViewHolder) holder).tvCode.setText("核销码: " + mList.get(position).getClosureNum());


				((CouponViewHolder) holder).mineCouponRl.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int visibility = ((CouponViewHolder) holder).mineCouponLlAll.getVisibility();
						if (visibility == 0x00000008) {

							if (mList.get(position).getCouponShare() == 1) {
								((CouponViewHolder) holder).mineCouponLlShare.setVisibility(View.VISIBLE);
							} else {
								((CouponViewHolder) holder).mineCouponLlShare.setVisibility(View.GONE);
							}
							if (!mList.get(position).getConditionDesc().equals("")) {
								((CouponViewHolder) holder).VLine1.setVisibility(View.VISIBLE);
								((CouponViewHolder) holder).mineCouponLlContent.setVisibility(View.VISIBLE);
								((CouponViewHolder) holder).tvContent.setText(mList.get(position).getConditionDesc());
							} else {
								((CouponViewHolder) holder).VLine1.setVisibility(View.GONE);
								((CouponViewHolder) holder).mineCouponLlContent.setVisibility(View.GONE);
							}

							((CouponViewHolder) holder).mineCouponLlAll.setVisibility(View.VISIBLE);
							((CouponViewHolder) holder).ivIndicate.setBackgroundResource(R.mipmap.youhuiquan_jiantou);


						} else {
							((CouponViewHolder) holder).mineCouponLlAll.setVisibility(View.GONE);
							((CouponViewHolder) holder).ivIndicate.setBackgroundResource(R.mipmap.youhuiquan_zhankai);


						}
					}
				});

				if (mList.get(position).getCouponShare() == 1) {
					((CouponViewHolder) holder).mineCouponLlShare.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							setRuleDialog(position);
						}
					});
				}


			} else if (flag == 1) {//已使用

				((CouponViewHolder) holder).ivBackground.setBackgroundResource(R.mipmap.youhuiquan_bg3);
				((CouponViewHolder) holder).ivDeadLine.setVisibility(View.VISIBLE);
				((CouponViewHolder) holder).mineCouponTextReceive.setVisibility(View.GONE);

			} else if (flag == 2) {//已失效
				((CouponViewHolder) holder).ivDeadLine.setVisibility(View.VISIBLE);
				((CouponViewHolder) holder).ivDeadLine.setBackgroundResource(R.mipmap.youhuiquan_yishixiao);
				((CouponViewHolder) holder).mineCouponTextReceive.setVisibility(View.GONE);
				((CouponViewHolder) holder).ivBackground.setBackgroundResource(R.mipmap.youhuiquan_bg3);
			}
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}


	private void setRuleDialog(final int position) {

		View pview = LayoutInflater.from(mContext).inflate(R.layout.dialog_coupon_ticket, null);
		LinearLayout llCancel = (LinearLayout) pview.findViewById(R.id.integral_ll_cancel);
		TextView tvTitle = (TextView) pview.findViewById(R.id.integral_tv_title);
		TextView tvYes = (TextView) pview.findViewById(R.id.integral_tv_yes);

		TextView tvPriceSymbol = (TextView) pview.findViewById(R.id.mine_sign_text_ticket_price_symbol);
		TextView tvPrice = (TextView) pview.findViewById(R.id.mine_sign_text_ticket_price);
		TextView tvRebate = (TextView) pview.findViewById(R.id.mine_sign_text_ticket_rebate);
		TextView tvPriceStandard = (TextView) pview.findViewById(R.id.mine_sign_text_ticket_price_standard);
		TextView tvClass = (TextView) pview.findViewById(R.id.mine_sign_text_ticket_class);
		TextView tvClassContent = (TextView) pview.findViewById(R.id.mine_sign_text_ticket_class_content);

		TextView tvClassTime = (TextView) pview.findViewById(R.id.mine_sign_text_ticket_class_time);
		ImageView ivBackground = (ImageView) pview.findViewById(R.id.mine_coupon_iv_background);
		final EditText etPhone = (EditText) pview.findViewById(R.id.mine_coupon_et_phone);
		tvTitle.setText("分享优惠券");


		tvClassTime.setText(mList.get(position).getDate());
		tvClassContent.setText(mList.get(position).getCouponName());
		tvClass.setText(mList.get(position).getCouponTypeMsg());
		tvPriceStandard.setText(mList.get(position).getCouponRequire());
		tvPrice.setText(mList.get(position).getFaceValue() + "");

		switch (mList.get(position).getCouponType()) {//1折扣券 2代金券3：兑换券 4：积分券5： 金额券
			case 1:
				tvPriceSymbol.setVisibility(View.GONE);
				tvRebate.setText("折");
				tvRebate.setVisibility(View.VISIBLE);
				ivBackground.setBackgroundResource(R.mipmap.zhuanzengyouhuiquan_1);
				tvClass.setBackgroundResource(R.drawable.shape_corner_oval_four);
				break;
			case 2:
				ivBackground.setBackgroundResource(R.mipmap.zhuanzengyouhuiquan_3);
				tvClass.setBackgroundResource(R.drawable.shape_corner_oval_three);
				break;
			case 3:
				ivBackground.setBackgroundResource(R.mipmap.zhuanzengyouhuiquan_2);
				tvClass.setBackgroundResource(R.drawable.shape_corner_oval_five);
				break;
		}


		builder = new CustomDialog(mContext, R.style.my_dialog).create(pview, false, 0.85f, 0.45f, 1.7f);
		builder.show();
		llCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				builder.dismiss();
			}
		});

		tvYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String et_phone_num = etPhone.getText().toString().trim();
				if (!et_phone_num.equals("")) {
					inValidate(et_phone_num, mList.get(position).getUserCouponId(), position);
//					if (et_phone_num.length() != 0) {
//					} else {
//						Toast.makeText(mContext, "手机号码格式不对", Toast.LENGTH_SHORT).show();
//					}
				} else {
					Toast.makeText(mContext, "手机号码不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void inValidate(String tel, int couponId, int position) {
		if (!MatchUtils.isValidPhoneNumber(tel)) {
			ToastUtils.showToast(mContext, "请输入正确的手机号码");
		} else {
			getAsynShareCoupon(tel, couponId, position);
		}
	}

	public void getAsynShareCoupon(String userPhone, int userCouponId, final int position) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", uid);
		map.put("userPhone", userPhone);
		map.put("userCouponId", userCouponId + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));

		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/shareUserCoupon")
				.addParams("data", URLBuilder.format(map))
				.tag(mContext).build().execute(new Utils.MyResultCallback<NormalEntity>() {
			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetList -- json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					builder.dismiss();
					ToastUtils.showToast(mContext, "分享成功");
					mList.remove(position);
					notifyDataSetChanged();
				} else {
					builder.dismiss();
					LogUtils.i("我挂了" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				builder.dismiss();
				LogUtils.i("我故障了" + e);
				if (call.isCanceled()) {
					call.cancel();
					ToastUtils.showToast(mContext, "分享失败");
				} else {
				}
			}
		});
	}


	class CouponViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.mine_sign_text_ticket_price_symbol)
		TextView TextTicketPriceSymbol;
		@BindView(R.id.mine_sign_text_ticket_price)
		TextView TextTicketPrice;
		@BindView(R.id.mine_sign_text_ticket_rebate)
		TextView TextTicketRebate;
		@BindView(R.id.mine_sign_text_ticket_price_standard)
		TextView TextTicketPriceStandard;
		@BindView(R.id.mine_sign_text_ticket_class)
		TextView TextTicketClass;
		@BindView(R.id.mine_sign_text_ticket_class_content)
		TextView TextTicketClassContent;


		@BindView(R.id.mine_coupon_text_share)
		TextView tvShare;
		@BindView(R.id.mine_coupon_text_content_title)
		TextView tvTitle;
		@BindView(R.id.mine_coupon_text_content_titles)
		TextView tvTitles;


		@BindView(R.id.mine_sign_text_ticket_class_time)
		TextView TextTicketClassTime;
		@BindView(R.id.mine_coupon_text_receive)
		TextView mineCouponTextReceive;
		@BindView(R.id.mine_coupon_rl_)
		RelativeLayout mineCouponRl;
		@BindView(R.id.mine_coupon_fl)
		FrameLayout mineCouponFl;


		@BindView(R.id.mine_coupon_ll_all)
		LinearLayout mineCouponLlAll;
		@BindView(R.id.mine_coupon_ll_share)
		LinearLayout mineCouponLlShare;

		@BindView(R.id.view_line1)
		View VLine1;
		@BindView(R.id.mine_coupon_ll_content)
		LinearLayout mineCouponLlContent;//详情
		@BindView(R.id.mine_coupon_tv_content)
		TextView tvContent;//详情内容

		@BindView(R.id.view_line2)
		View VLine2;
		@BindView(R.id.mine_coupon_ll_code)
		LinearLayout mineCouponLlCo;//核销码
		@BindView(R.id.mine_coupon_tv_code)
		TextView tvCode;//核销码内容


		@BindView(R.id.mine_coupon_iv_indicate)
		ImageView ivIndicate;
		@BindView(R.id.mine_coupon_iv_background)
		ImageView ivBackground;
		@BindView(R.id.mine_coupon_iv_ticket_deadline)
		ImageView ivDeadLine;


		private CouponDetialClickListener mListener;

		public CouponViewHolder(View itemView, CouponDetialClickListener listener) {
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

	public interface CouponDetialClickListener {
		void onItemClick(View view, int postion);
	}
}