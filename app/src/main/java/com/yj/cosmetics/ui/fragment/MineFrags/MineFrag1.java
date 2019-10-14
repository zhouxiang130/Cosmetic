package com.yj.cosmetics.ui.fragment.MineFrags;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseFragment;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.ui.activity.MineAboutActivity;
import com.yj.cosmetics.ui.activity.MineAddressManageActivity;
import com.yj.cosmetics.ui.activity.MineCollectionActivity;
import com.yj.cosmetics.ui.activity.MineOrderActivity;
import com.yj.cosmetics.ui.activity.MinePersonalDataActivity;
import com.yj.cosmetics.ui.activity.MineSettingActivity;
import com.yj.cosmetics.ui.activity.MyFreeOrderActivity;
import com.yj.cosmetics.ui.activity.NormalWebViewActivity;
import com.yj.cosmetics.ui.activity.mineAccount.MineAccount2Activity;
import com.yj.cosmetics.ui.activity.mineRefundList.MineRefundListActivity;
import com.yj.cosmetics.ui.activity.mineScoring.MineScoring1Activity;
import com.yj.cosmetics.util.AuthorUtils;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2017/11/18.
 *
 * @TODO 个人中心 2.0版本升级页面
 */

public class MineFrag1 extends BaseFragment implements MineFrag_contract.View {
	@BindView(R.id.frag_mine_tv_info)
	TextView fragMineTvInfo;
	@BindView(R.id.frag_mine_login_iv)
	RoundedImageView fragMineLoginIv;
	@BindView(R.id.frag_mine_login_)
	RelativeLayout fragMineLogin;
	@BindView(R.id.mine_new_tv_login)
	TextView mineNewTvLogin;
	@BindView(R.id.mine_new_tv_tel)
	TextView mineNewTvTel;
	@BindView(R.id.frag_mine_account_money)
	TextView fragMineAccountMoney;
	@BindView(R.id.frag_mine_tv_score)
	TextView fragMineTvScore;
	@BindView(R.id.frag_mine_tv_pay_num)
	TextView fragMineTvPayNum;
	@BindView(R.id.frag_mine_tv_send_num)
	TextView fragMineTvSendNum;
	@BindView(R.id.frag_mine_tv_coustom_service_msg_num)
	TextView tvCoustomNum;
	@BindView(R.id.frag_mine_tv_get_num)
	TextView fragMineTvGetNum;
	@BindView(R.id.frag_mine_tv_judge_num)
	TextView fragMineTvJudgeNum;
	Unbinder unbinder;
	CustomNormalContentDialog mDialog;
	private MineFrag_contract.Presenter mineFragView = new MineFrag_presenter(this);

	/**
	 * 需要进行检测的权限数组
	 */
	private static final int PERMISSON_REQUESTCODE = 0;
	private boolean isNeedCheck = true;
	protected String[] needPermissions = {
			Manifest.permission.CALL_PHONE
	};

	private String serviceTel;
	private String upintegral;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = createView(inflater.inflate(R.layout.fragment_mine1, container, false));
		unbinder = ButterKnife.bind(this, view);
		return view;
	}

	@Override
	protected void initData() {
		mineFragView.subscribe();
	}


	@Override
	public void onResume() {
		super.onResume();
		mineFragView.setMineHeadInfo(mUtils);
	}


	@OnClick({R.id.frag_mine_info, /*R.id.frag_mine_login_iv,*/ R.id.frag_mine_login_,/* R.id.frag_mine_head_img,*/ R.id.frag_mine_login_ll, R.id.frag_mine_account, R.id.frag_mine_scoring,
			R.id.frag_mine_order_all, R.id.frag_mine_order_pay, R.id.frag_mine_order_send, R.id.frag_mine_order_get, R.id.frag_mine_order_judge, R.id.frag_mine_order_drawback,
			R.id.frag_mine_md, R.id.frag_mine_sjrz, R.id.frag_mine_address, R.id.frag_mine_collection, /*R.id.frag_mine_coustom_service,*/ R.id.frag_mine_contact,
			R.id.frag_mine_rl_setting, R.id.frag_mine_rl_about})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.frag_mine_info:
				if (mUtils.isLogin()) {
					IntentUtils.IntentToInfoCenter(getActivity());
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_login_:

				if (mUtils.isLogin()) {
					Intent intentPersonal = new Intent(getActivity(), MinePersonalDataActivity.class);
					startActivity(intentPersonal);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
//			case R.id.frag_mine_head_img:
//
//				break;
			case R.id.frag_mine_login_ll:
				if (mUtils.isLogin()) {
					Intent intentPersonal = new Intent(getActivity(), MinePersonalDataActivity.class);
					startActivity(intentPersonal);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_account:
				if (mUtils.isLogin()) {
					Intent intentAccount = new Intent(getActivity(), MineAccount2Activity.class);
					intentAccount.putExtra("upintegral", upintegral);
					startActivity(intentAccount);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_scoring:
				if (mUtils.isLogin()) {
					Intent intentScoring = new Intent(getActivity(), MineScoring1Activity.class);
					startActivity(intentScoring);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_all:

				if (mUtils.isLogin()) {
					Intent intentAll = new Intent(getActivity(), MineOrderActivity.class);
					intentAll.putExtra("page", 0);
					startActivity(intentAll);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_pay:

				if (mUtils.isLogin()) {
					Intent intentPay = new Intent(getActivity(), MineOrderActivity.class);
					intentPay.putExtra("page", 1);
					startActivity(intentPay);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_send:
				if (mUtils.isLogin()) {
					Intent intentSend = new Intent(getActivity(), MineOrderActivity.class);
					intentSend.putExtra("page", 2);
					startActivity(intentSend);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_get:
				if (mUtils.isLogin()) {
					Intent intentGet = new Intent(getActivity(), MineOrderActivity.class);
					intentGet.putExtra("page", 3);
					startActivity(intentGet);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_judge:
				if (mUtils.isLogin()) {
					Intent intentJudge = new Intent(getActivity(), MineOrderActivity.class);
					intentJudge.putExtra("page", 4);
					startActivity(intentJudge);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_order_drawback://退款

				if (mUtils.isLogin()) {
					Intent intentJudge = new Intent(getActivity(), MineRefundListActivity.class);
					startActivity(intentJudge);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}

				break;

			case R.id.frag_mine_md://我的免单
				if (mUtils.isLogin()) {
//					Intent intentInvite = new Intent(getActivity(), MineInviteActivity.class);
//					startActivity(intentInvite);

					Intent intentFreeOrder = new Intent(getActivity(), MyFreeOrderActivity.class);
					startActivity(intentFreeOrder);

//					ToastUtils.showToast(getContext(), "我的免单");
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_sjrz://店铺入住
				if (mUtils.isLogin()) {
					Intent intentCompany = new Intent(getActivity(), NormalWebViewActivity.class);
					intentCompany.putExtra("url", URLBuilder.URLBaseHeader + URLBuilder.SjRz);
					intentCompany.putExtra("title", "商家入驻");
					startActivity(intentCompany);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}

				break;
			case R.id.frag_mine_address:
				if (mUtils.isLogin()) {
					Intent intentAddress = new Intent(getActivity(), MineAddressManageActivity.class);
					startActivity(intentAddress);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_collection://签到中心
				if (mUtils.isLogin()) {

					Intent intentSetting = new Intent(getActivity(), MineCollectionActivity.class);
					startActivity(intentSetting);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
		/*	case R.id.frag_mine_coustom_service:
				if (mUtils.isLogin()) {
					mineFragView.doCustomServices();
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;*/
			case R.id.frag_mine_contact:
				if (mUtils.isLogin()) {
					doAsyncGetNumber();
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_rl_setting:
				if (mUtils.isLogin()) {
					Intent intentSetting = new Intent(getActivity(), MineSettingActivity.class);
					startActivity(intentSetting);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
			case R.id.frag_mine_rl_about:
				if (mUtils.isLogin()) {
					Intent intentAbout = new Intent(getActivity(), MineAboutActivity.class);
					startActivity(intentAbout);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
		}
	}

	private void doAsyncGetNumber() {
		Map<String, String> map = new HashMap<>();
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/seachTel")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<String>() {

			@Override
			public String parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.e("json==" + json);
				return json;
			}

			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					serviceTel = jsonObject.getString("msg");
					setCallDialog();
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
			}
		});
	}

	private void setCallDialog() {
		if (mDialog == null) {
			mDialog = new CustomNormalContentDialog(getContext());
		}
		if (TextUtils.isEmpty(serviceTel)) {
			ToastUtils.showToast(getContext(), "无法获取联系电话，请检查网络稍后再试");
			return;
		}
		if (!mDialog.isShowing()) {
			mDialog.show();
		}
		mDialog.getTvTitle().setText("客服热线");
		if (!TextUtils.isEmpty(serviceTel)) {
			mDialog.getTvContent().setText("拨打" + serviceTel + "热线，联系官方客服");
		} else if (!TextUtils.isEmpty(mUtils.getServiceTel())) {
			mDialog.getTvContent().setText("拨打" + mUtils.getServiceTel() + "热线，联系官方客服");
		}
		mDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (new AuthorUtils(getContext()).checkPermissions(needPermissions)) {
					setActionCall(serviceTel);
				}
				dismissDialog();
			}
		});
		mDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismissDialog();
			}
		});
	}

	public void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	@Override
	public void isNoLogin() {
		fragMineTvPayNum.setVisibility(View.GONE);
		fragMineTvSendNum.setVisibility(View.GONE);
		fragMineTvGetNum.setVisibility(View.GONE);
		fragMineTvJudgeNum.setVisibility(View.GONE);
		fragMineTvInfo.setVisibility(View.GONE);
		fragMineAccountMoney.setText("0.00");
		fragMineTvScore.setText("0");

	}

	@Override
	public void setLoginName(boolean isLoginName) {
		if (isLoginName) {
			mineNewTvLogin.setText(mUtils.getUserName());
		} else {
			mineNewTvLogin.setText("点击登录");
			mineNewTvTel.setVisibility(View.GONE);
		}
	}

	@Override
	public void isLoginHeadImg(boolean isLoginHeadImg) {
		if (isLoginHeadImg) {
			Glide.with(getActivity().getApplicationContext())
					.load(URLBuilder.getUrl(mUtils.getAvatar()))
					.asBitmap()
					.fitCenter()
					.error(R.mipmap.default_avatar)
					.into(fragMineLoginIv);
		} else {
			Glide.with(getActivity().getApplicationContext())
					.load(R.mipmap.default_avatar)
					.asBitmap()
					.fitCenter()
					.error(R.mipmap.default_avatar)
					.into(fragMineLoginIv);

		}
	}


	@Override
	public void setCallPhone(String serviceTel) {
		this.serviceTel = serviceTel;
		if (new AuthorUtils(getActivity()).checkPermissions(needPermissions)) {
			//权限校验成功之后，开启打电话
			setActionCall(serviceTel);
		}

	}

	private void setActionCall(String serviceTel) {
		//拨打电话
		if (serviceTel != null) {
			Intent callIntent = new Intent();
			callIntent.setAction(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + serviceTel));
			startActivity(callIntent);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case PERMISSON_REQUESTCODE:
				if (!AuthorUtils.verifyPermissions(grantResults)) {
					isNeedCheck = false;
				} else {
					isNeedCheck = true;
				}
				if (isNeedCheck) {
					setActionCall(serviceTel);
				}
				break;
		}
	}

	@Override
	public void setDatas(MineEntity.MineData data) {
		preferencesUtil.setValue("userType", data.getUserType());
		if (!TextUtils.isEmpty(data.getUpintegral())) {
			upintegral = data.getUpintegral();
		}
		if (!TextUtils.isEmpty(data.getServiceTel())) {
			mUtils.saveServiceTel(data.getServiceTel());
		}
		if (data != null) {
			fragMineAccountMoney.setText(data.getUserMoney());
			fragMineTvScore.setText(data.getUserScore());
		}

		if (!TextUtils.isEmpty(data.getOrderpay())) {

			if (Integer.parseInt(data.getOrderpay()) > 99) {
				fragMineTvPayNum.setText("99+");
			} else {
				fragMineTvPayNum.setText(data.getOrderpay());
			}

			fragMineTvPayNum.setVisibility(View.VISIBLE);
		} else {
			fragMineTvPayNum.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(data.getOrdersend())) {

			if (Integer.parseInt(data.getOrdersend()) > 99) {
				fragMineTvSendNum.setText("99+");
			} else {
				fragMineTvSendNum.setText(data.getOrdersend());
			}
			fragMineTvSendNum.setVisibility(View.VISIBLE);

		} else {
			fragMineTvSendNum.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(data.getOrderget())) {


			if (Integer.parseInt(data.getOrderget()) > 99) {
				fragMineTvGetNum.setText("99+");
			} else {
				fragMineTvGetNum.setText(data.getOrderget());
			}
			fragMineTvGetNum.setVisibility(View.VISIBLE);
		} else {
			fragMineTvGetNum.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(data.getOrderjudge())) {
			if (Integer.parseInt(data.getOrderjudge()) > 99) {
				fragMineTvJudgeNum.setText("99+");
			} else {
				fragMineTvJudgeNum.setText(data.getOrderjudge());
			}
			fragMineTvJudgeNum.setVisibility(View.VISIBLE);
		} else {
			fragMineTvJudgeNum.setVisibility(View.GONE);
		}
	}

	@Override
	public void setTel(boolean b, String replace) {
		if (b) {
			mineNewTvTel.setVisibility(View.VISIBLE);
		}
		if (b) {
			mineNewTvTel.setText(replace);
		} else {
			mineNewTvTel.setText(mUtils.getTel());
		}
	}

	@Override
	public void setMineNewNum(boolean b, NormalEntity response) {
		if (b) {
			String text = response.getData().toString();
			fragMineTvInfo.setVisibility(View.VISIBLE);
			int i = (int) Float.parseFloat(text);
			if (i > 99) {
				fragMineTvInfo.setText("99");
			} else {
				fragMineTvInfo.setText(i + "");
			}
		} else {
			fragMineTvInfo.setVisibility(View.GONE);
		}
	}

	@Override
	public void showToast(String s) {
		ToastUtils.showToast(getActivity(), s);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}
}
