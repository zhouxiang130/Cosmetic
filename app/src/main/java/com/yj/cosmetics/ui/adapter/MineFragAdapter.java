package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineEntity;
import com.yj.cosmetics.ui.activity.MineAboutActivity;
import com.yj.cosmetics.ui.activity.MineAddressManageActivity;
import com.yj.cosmetics.ui.activity.MineCollectionActivity;
import com.yj.cosmetics.ui.activity.MineInviteActivity;
import com.yj.cosmetics.ui.activity.MineOrderActivity;
import com.yj.cosmetics.ui.activity.MinePersonalDataActivity;
import com.yj.cosmetics.ui.activity.MineSettingActivity;
import com.yj.cosmetics.ui.activity.mineAccount.MineAccount2Activity;
import com.yj.cosmetics.ui.activity.MineCouponActivity;
import com.yj.cosmetics.ui.activity.MineRefundListActivity;
import com.yj.cosmetics.ui.activity.MineScoring1Activity;
import com.yj.cosmetics.ui.activity.mineSignIn.MineSignInActivity;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/10 0010.
 */

public class MineFragAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

	private UserUtils mUtils = null;
	private Context mContext = null;
	private MineEntity.MineData data;
	private String newData;
	private String noReadNum = null;

	public MineFragAdapter(Context context, UserUtils mUtils) {
		this.mContext = context;
		this.mUtils = mUtils;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		HeadViewHolder viewHolder;
		ContentViewHolder contentViewHolder;
		if (viewType == 0) {
			viewHolder = new HeadViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_mine_frag_info, parent, false));
			return viewHolder;
		} else {
			contentViewHolder = new ContentViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_mine_frag_infos, parent, false));
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
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		if (viewHolder instanceof HeadViewHolder) {

			if (!TextUtils.isEmpty(mUtils.getUserName())) {
				((HeadViewHolder) viewHolder).mineNewTvLogin.setText(mUtils.getUserName());
				if (!TextUtils.isEmpty(mUtils.getTel())) {
					if (mUtils.getTel().length() > 8) {
						((HeadViewHolder) viewHolder).mineNewTvTel.setText(mUtils.getTel().replace(mUtils.getTel().substring(4, 8), "****"));
					} else {
						((HeadViewHolder) viewHolder).mineNewTvTel.setText(mUtils.getTel());
					}
				}
				((HeadViewHolder) viewHolder).mineNewTvTel.setVisibility(View.VISIBLE);
			} else {
				((HeadViewHolder) viewHolder).mineNewTvLogin.setText("点击登录");
				((HeadViewHolder) viewHolder).mineNewTvTel.setVisibility(View.GONE);
			}

			if (!TextUtils.isEmpty(mUtils.getAvatar())) {

				LogUtils.i("avatar的值" + mUtils.getAvatar());
				Glide.with(mContext.getApplicationContext())
						.load(URLBuilder.getUrl(mUtils.getAvatar()))
						.asBitmap()
						.fitCenter()
						.error(R.mipmap.default_avatar)
						.into(((HeadViewHolder) viewHolder).fragMineLoginIv);

			} else {
				Glide.with(mContext.getApplicationContext())
						.load(R.mipmap.default_avatar)
						.asBitmap()
						.fitCenter()
						.error(R.mipmap.default_avatar)
						.into(((HeadViewHolder) viewHolder).fragMineLoginIv);
			}

			if (mUtils.isLogin()) {
				if (data != null) {
					if (!TextUtils.isEmpty(data.getServiceTel())) {
						mUtils.saveServiceTel(data.getServiceTel());
					}

					((HeadViewHolder) viewHolder).fragMineAccountMoney.setText(data.getUserMoney());
					((HeadViewHolder) viewHolder).fragMineTvScore.setText(data.getUserScore());

					if (!TextUtils.isEmpty(data.getOrderpay())) {

						if (Integer.parseInt(data.getOrderpay()) > 99) {
							((HeadViewHolder) viewHolder).tvPayNum.setText("99+");
						} else {
							((HeadViewHolder) viewHolder).tvPayNum.setText(data.getOrderpay());
						}

						((HeadViewHolder) viewHolder).tvPayNum.setVisibility(View.VISIBLE);
					} else {
						((HeadViewHolder) viewHolder).tvPayNum.setVisibility(View.GONE);
					}

					if (!TextUtils.isEmpty(data.getOrdersend())) {

						if (Integer.parseInt(data.getOrdersend()) > 99) {
							((HeadViewHolder) viewHolder).tvSendNum.setText("99+");
						} else {
							((HeadViewHolder) viewHolder).tvSendNum.setText(data.getOrdersend());
						}
						((HeadViewHolder) viewHolder).tvSendNum.setVisibility(View.VISIBLE);

					} else {
						((HeadViewHolder) viewHolder).tvSendNum.setVisibility(View.GONE);
					}

					if (!TextUtils.isEmpty(data.getOrderget())) {


						if (Integer.parseInt(data.getOrderget()) > 99) {
							((HeadViewHolder) viewHolder).tvGetNum.setText("99+");
						} else {
							((HeadViewHolder) viewHolder).tvGetNum.setText(data.getOrderget());
						}
						((HeadViewHolder) viewHolder).tvGetNum.setVisibility(View.VISIBLE);
					} else {
						((HeadViewHolder) viewHolder).tvGetNum.setVisibility(View.GONE);
					}


					if (!TextUtils.isEmpty(data.getOrderjudge())) {
						if (Integer.parseInt(data.getOrderjudge()) > 99) {
							((HeadViewHolder) viewHolder).tvJudgeNum.setText("99+");
						} else {
							((HeadViewHolder) viewHolder).tvJudgeNum.setText(data.getOrderjudge());
						}
						((HeadViewHolder) viewHolder).tvJudgeNum.setVisibility(View.VISIBLE);
					} else {
						((HeadViewHolder) viewHolder).tvJudgeNum.setVisibility(View.GONE);
					}

				}

				if (newData != null) {
					((HeadViewHolder) viewHolder).tvInfo.setVisibility(View.VISIBLE);
					((HeadViewHolder) viewHolder).tvInfo.setText(newData);
				}else {
					((HeadViewHolder) viewHolder).tvInfo.setVisibility(View.GONE);
				}


			} else {
				((HeadViewHolder) viewHolder).tvPayNum.setVisibility(View.GONE);
				((HeadViewHolder) viewHolder).tvSendNum.setVisibility(View.GONE);
				((HeadViewHolder) viewHolder).tvGetNum.setVisibility(View.GONE);
				((HeadViewHolder) viewHolder).tvJudgeNum.setVisibility(View.GONE);
				((HeadViewHolder) viewHolder).tvInfo.setVisibility(View.GONE);
				((HeadViewHolder) viewHolder).fragMineAccountMoney.setText("0.00");
				((HeadViewHolder) viewHolder).fragMineTvScore.setText("0");
			}

			((HeadViewHolder) viewHolder).fragMineInfo.setOnClickListener(this);
			((HeadViewHolder) viewHolder).fragMineLogin.setOnClickListener(this);
			((HeadViewHolder) viewHolder).fragMineAccount.setOnClickListener(this);
			((HeadViewHolder) viewHolder).fragMineScoing.setOnClickListener(this);
			((HeadViewHolder) viewHolder).llLoginAll.setOnClickListener(this);
			((HeadViewHolder) viewHolder).rlOrderAll.setOnClickListener(this);


			((HeadViewHolder) viewHolder).rlOrderPay.setOnClickListener(this);
			((HeadViewHolder) viewHolder).rlOrderSend.setOnClickListener(this);
			((HeadViewHolder) viewHolder).rlOrderGet.setOnClickListener(this);
			((HeadViewHolder) viewHolder).rlOrderJudge.setOnClickListener(this);
			((HeadViewHolder) viewHolder).rlOrderDrawBack.setOnClickListener(this);


		} else if (viewHolder instanceof ContentViewHolder) {

			if (mUtils.isLogin()) {
				if (noReadNum != null) {
					if (!noReadNum.equals("")) {
						((ContentViewHolder) viewHolder).tvMessageNum.setVisibility(View.VISIBLE);
						((ContentViewHolder) viewHolder).tvMessageNum.setText(noReadNum);
					} else {
						((ContentViewHolder) viewHolder).tvMessageNum.setVisibility(View.GONE);
					}
				}
			}

			((ContentViewHolder) viewHolder).mineInvite.setOnClickListener(this);
			((ContentViewHolder) viewHolder).mineCollection.setOnClickListener(this);
			((ContentViewHolder) viewHolder).mineAddress.setOnClickListener(this);
			((ContentViewHolder) viewHolder).mineSignIn.setOnClickListener(this);
			((ContentViewHolder) viewHolder).mineTicket.setOnClickListener(this);
			((ContentViewHolder) viewHolder).mineService.setOnClickListener(this);
			((ContentViewHolder) viewHolder).mineContact.setOnClickListener(this);
			((ContentViewHolder) viewHolder).mineSetting.setOnClickListener(this);
			((ContentViewHolder) viewHolder).mineAbout.setOnClickListener(this);

		}

	}

	@Override
	public int getItemCount() {
		return 2;
	}

	public void setData(MineEntity.MineData data) {
		this.data = data;
		notifyDataSetChanged();
	}

	public void setNewData(String newData) {
		this.newData = newData;
		notifyDataSetChanged();
	}

	@OnClick({R.id.frag_mine_login_, R.id.frag_mine_account, R.id.frag_mine_scoring,
			R.id.frag_mine_order_all, R.id.frag_mine_login_ll, R.id.frag_mine_info,

			R.id.frag_mine_order_pay, R.id.frag_mine_order_send, R.id.frag_mine_order_get,
			R.id.frag_mine_order_judge, R.id.frag_mine_order_drawback,
			R.id.frag_mine_invite, R.id.frag_mine_collection, R.id.frag_mine_address,
			R.id.frag_mine_signIn, R.id.frag_mine_ticket,
			R.id.frag_mine_coustom_service, R.id.frag_mine_contact,
			R.id.frag_mine_rl_setting, R.id.frag_mine_rl_about})
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.frag_mine_login_:

				if (mUtils.isLogin()) {
					Intent intentPersonal = new Intent(mContext, MinePersonalDataActivity.class);
					mContext.startActivity(intentPersonal);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}
				break;

			case R.id.frag_mine_account:

				if (mUtils.isLogin()) {
					Intent intentPersonal = new Intent(mContext, MineAccount2Activity.class);
					mContext.startActivity(intentPersonal);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}
				break;

			case R.id.frag_mine_scoring:
				if (mUtils.isLogin()) {
					Intent intentPersonal = new Intent(mContext, MineScoring1Activity.class);
					mContext.startActivity(intentPersonal);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}

				break;

			case R.id.frag_mine_order_all:

				if (mUtils.isLogin()) {
					Intent intentPersonal = new Intent(mContext, MineOrderActivity.class);
					mContext.startActivity(intentPersonal);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}

				break;
			case R.id.frag_mine_login_ll:

				if (mUtils.isLogin()) {
					Intent intentPersonal = new Intent(mContext, MinePersonalDataActivity.class);
					mContext.startActivity(intentPersonal);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}

				break;

			case R.id.frag_mine_info:
				if (mUtils.isLogin()) {
					IntentUtils.IntentToInfoCenter(mContext);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}
				break;

			case R.id.frag_mine_order_pay:
				startMineOrder(1);
				break;
			case R.id.frag_mine_order_send:
				startMineOrder(2);

				break;
			case R.id.frag_mine_order_get:

				startMineOrder(3);
				break;
			case R.id.frag_mine_order_judge:

				startMineOrder(4);
				break;
			case R.id.frag_mine_order_drawback:
				if (mUtils.isLogin()) {
					Intent intentJudge = new Intent(mContext, MineRefundListActivity.class);
					mContext.startActivity(intentJudge);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}
				break;

			case R.id.frag_mine_invite:
				if (mUtils.isLogin()) {
					Intent intentInvite = new Intent(mContext, MineInviteActivity.class);
					mContext.startActivity(intentInvite);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}
				break;
			case R.id.frag_mine_collection:
				if (mUtils.isLogin()) {
					Intent intentCollection = new Intent(mContext, MineCollectionActivity.class);
					mContext.startActivity(intentCollection);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}
				break;
			case R.id.frag_mine_address:
				if (mUtils.isLogin()) {
					Intent intentAddress = new Intent(mContext, MineAddressManageActivity.class);
					mContext.startActivity(intentAddress);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}
				break;
			case R.id.frag_mine_signIn://签到中心
				if (mUtils.isLogin()) {
					Intent intentSetting = new Intent(mContext, MineSignInActivity.class);
					mContext.startActivity(intentSetting);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}
				break;
			case R.id.frag_mine_ticket://我的优惠券
				if (mUtils.isLogin()) {
					Intent intentCoupon = new Intent(mContext, MineCouponActivity.class);
					mContext.startActivity(intentCoupon);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}
				break;
			case R.id.frag_mine_coustom_service:

				if (mUtils.isLogin()) {
					checkInterface.onChecks(1);
//					mineFragView.doCustomServices();
				} else {
					IntentUtils.IntentToLogin(mContext);
				}

				break;
			case R.id.frag_mine_contact:
				if (mUtils.isLogin()) {

				checkInterface.onChecks(2);
//					mineFragView.doCustomServices();
				} else {
					IntentUtils.IntentToLogin(mContext);
				}

//				setCallDialog();
//				mineFragView.showCallDialog(mDialog);

				break;
			case R.id.frag_mine_rl_setting:

				if (mUtils.isLogin()) {
					Intent intentSetting = new Intent(mContext, MineSettingActivity.class);
					mContext.startActivity(intentSetting);
				} else {
					IntentUtils.IntentToLogin(mContext);
				}
				break;
			case R.id.frag_mine_rl_about:
				Intent intentAbout = new Intent(mContext, MineAboutActivity.class);
				mContext.startActivity(intentAbout);
				break;


		}
	}

	private void startMineOrder(int i) {
		if (mUtils.isLogin()) {
			Intent intentPay = new Intent(mContext, MineOrderActivity.class);
			intentPay.putExtra("page", i);
			mContext.startActivity(intentPay);
		} else {
			IntentUtils.IntentToLogin(mContext);
		}
	}

	public void tvCoustomNum(String noReadNum) {
		this.noReadNum = noReadNum;
	}


	class ContentViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.frag_mine_invite)
		RelativeLayout mineInvite;
		@BindView(R.id.frag_mine_collection)
		RelativeLayout mineCollection;
		@BindView(R.id.frag_mine_address)
		RelativeLayout mineAddress;
		@BindView(R.id.frag_mine_tv_coustom_service_msg_num)
		TextView tvMessageNum;

		@BindView(R.id.frag_mine_signIn)
		RelativeLayout mineSignIn;
		@BindView(R.id.frag_mine_ticket)
		RelativeLayout mineTicket;
		@BindView(R.id.frag_mine_coustom_service)
		RelativeLayout mineService;
		@BindView(R.id.frag_mine_contact)
		RelativeLayout mineContact;

		@BindView(R.id.frag_mine_rl_setting)
		RelativeLayout mineSetting;
		@BindView(R.id.frag_mine_rl_about)
		RelativeLayout mineAbout;


		public ContentViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	private CheckInterfaces checkInterface;


	/**
	 * 复选框接口
	 */
	public interface CheckInterfaces {
		/**
		 * 组选框状态改变触发的事件
		 */
		void onChecks(int i);
	}

	/**
	 * @param checkInterface
	 */
	public void setCheckInterface(CheckInterfaces checkInterface) {
		this.checkInterface = checkInterface;
	}


	class HeadViewHolder extends RecyclerView.ViewHolder {


		@BindView(R.id.frag_mine_tv_info)
		TextView tvInfo;
		@BindView(R.id.frag_mine_login_iv)
		RoundedImageView fragMineLoginIv;
		@BindView(R.id.mine_new_tv_login)
		TextView mineNewTvLogin;
		@BindView(R.id.mine_new_tv_tel)
		TextView mineNewTvTel;
		@BindView(R.id.frag_mine_login_)
		RelativeLayout fragMineLogin;
		@BindView(R.id.frag_mine_info)
		RelativeLayout fragMineInfo;
		@BindView(R.id.frag_mine_login_ll)
		LinearLayout llLoginAll;
		@BindView(R.id.frag_mine_account)
		LinearLayout fragMineAccount;
		@BindView(R.id.frag_mine_account_money)
		TextView fragMineAccountMoney;
		@BindView(R.id.frag_mine_scoring)
		LinearLayout fragMineScoing;
		@BindView(R.id.frag_mine_tv_score)
		TextView fragMineTvScore;
		@BindView(R.id.frag_mine_tv_pay_num)
		TextView tvPayNum;
		@BindView(R.id.frag_mine_tv_send_num)
		TextView tvSendNum;
		@BindView(R.id.frag_mine_tv_get_num)
		TextView tvGetNum;
		@BindView(R.id.frag_mine_tv_judge_num)
		TextView tvJudgeNum;
		@BindView(R.id.frag_mine_tv_drawback_num)
		TextView tvDrawbackNum;
		@BindView(R.id.frag_mine_order_all)
		RelativeLayout rlOrderAll;
		@BindView(R.id.frag_mine_order_pay)
		RelativeLayout rlOrderPay;
		@BindView(R.id.frag_mine_order_send)
		RelativeLayout rlOrderSend;
		@BindView(R.id.frag_mine_order_get)
		RelativeLayout rlOrderGet;
		@BindView(R.id.frag_mine_order_judge)
		RelativeLayout rlOrderJudge;
		@BindView(R.id.frag_mine_order_drawback)
		RelativeLayout rlOrderDrawBack;


		public HeadViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
