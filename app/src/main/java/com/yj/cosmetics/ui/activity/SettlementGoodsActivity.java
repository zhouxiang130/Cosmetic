package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Constant;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.base.Variables;
import com.yj.cosmetics.model.AlipayEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.model.PayResult;
import com.yj.cosmetics.model.SettlementGoodsEntity;
import com.yj.cosmetics.model.WXPayEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.yj.cosmetics.widget.Dialog.SettlementGoodsTicketDialog;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.yj.cosmetics.widget.SwitchView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Suo on 2018/3/22.
 *
 * @TODO 立即支付
 */

public class SettlementGoodsActivity extends BaseActivity {

	public static final String TAG = "SettlementGoodsActivity";

	@BindView(R.id.settlement_goods_address)
	RelativeLayout rlAddress;
	@BindView(R.id.settlement_goods_new_address)
	RelativeLayout rlNewAddress;

	@BindView(R.id.settlement_goods_tel)
	TextView tvTel;
	@BindView(R.id.settlement_goods_name)
	TextView tvName;
	@BindView(R.id.settlement_goods_content)
	TextView tvAddress;
	@BindView(R.id.goods_item_ticket_content)
	TextView tvTickContent;
	@BindView(R.id.goods_detial_ticket)
	RelativeLayout rlTicket;


	@BindView(R.id.settlement_goods_iv_goods)
	ImageView ivGoods;
	@BindView(R.id.settlement_goods_tv_price)
	TextView tvSinglePrice;
	@BindView(R.id.settlement_goods_tv_title)
	TextView tvTitle;
	@BindView(R.id.settlement_goods_tv_num)
	TextView tvNumber;
	@BindView(R.id.settlement_goods_tv_style)
	TextView tvStyle;
	@BindView(R.id.settlement_goods_tv_fee)
	TextView tvFee;
	@BindView(R.id.settlement_goods_tv_rest_all)
	TextView tvRestAll;
	@BindView(R.id.settlement_goods_tv_rest)
	TextView tvRest;
	/*@BindView(R.id.settlement_goods_switch)
	SwitchView cbSwitch;*/
	@BindView(R.id.settlement_goods_switch)
	SwitchView cbSwitch;
	@BindView(R.id.settlement_goods_tv_goods_price)
	TextView tvGoodsPrice;
	@BindView(R.id.settlement_goods_tv_fee_price)
	TextView tvFeePrice;
	@BindView(R.id.settlement_goods_ll_limit)
	LinearLayout llLimit;
	@BindView(R.id.settlement_goods_tv_limit)
	TextView tvLimit;
	@BindView(R.id.settlement_goods_cb2)
	CheckBox cbAlipay;
	@BindView(R.id.settlement_goods_cb1)
	CheckBox cbWechat;
	@BindView(R.id.settlement_goods_tvTotal)
	TextView tvTotal;
	@BindView(R.id.settlement_goods_rl_rest)
	RelativeLayout rlRest;
	@BindView(R.id.settlement_goods_solve)
	TextView tvPay;

	@BindView(R.id.frag_mine_login_iv)
	RoundedImageView storeIcon;
	@BindView(R.id.refound_detial_tv_normal_states)
	TextView tvStoreName;


	private CustomProgressDialog mDialog;
	SettlementGoodsTicketDialog TicketDialog;
	private SettlementGoodsEntity.SettlementGoodsData data;
	private int checkedPosition = -1, userCouponId = -1;
	private String proId, proNumber, pro, addressId, tickPic, sproductId, shopId;
	private float total = 0, rest = 0;//总价
	private float tickPic_;
	private IWXAPI api;


	private static final int SDK_PAY_FLAG = 1001;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case SDK_PAY_FLAG:
					PayResult payResult = new PayResult((Map<String, String>) msg.obj);
					//同步获取结果
					String resultInfo = payResult.getResult();
					LogUtils.i("我进入pay了" + resultInfo);
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, "9000")) {
						LogUtils.i("我进入支付成功了");
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								Intent intent = new Intent(SettlementGoodsActivity.this, PayResultActivity.class);
								startActivity(intent);
								finish();
							}
						}, 400);
						Toast.makeText(SettlementGoodsActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
					} else {
						LogUtils.i("我进入支付失败了");
						Toast.makeText(SettlementGoodsActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(SettlementGoodsActivity.this, MineOrderDetailActivity.class);
						intent.putExtra("oid", mUtils.getPayOrder());
						startActivity(intent);
						finish();
						tvPay.setEnabled(true);
					}
					break;
			}
		}
	};


	@Override
	protected int getContentView() {
		return R.layout.activity_goods_settlement;
	}


	@Override
	protected void initView() {
		setTitleText("填写订单");
		proId = getIntent().getStringExtra("proId");
		shopId = getIntent().getStringExtra("shopId");
		sproductId = getIntent().getStringExtra("sproductId");
		pro = getIntent().getStringExtra("pro");
		proNumber = getIntent().getStringExtra("proNumber");
		api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
		rlTicket.setEnabled(true);
	}

	@Override
	protected void initData() {
		doAsyncGetData();
	  /*  cbSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	        @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onMoneyChange();
            }
        });*/
		cbSwitch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onMoneyChange();
			}
		});
	}

	@OnClick({R.id.settlement_goods_cb_rl1, R.id.settlement_goods_cb_rl2, R.id.settlement_goods_solve, R.id.settlement_goods_new_address,
			R.id.settlement_goods_address, R.id.goods_detial_ticket})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.settlement_goods_cb_rl1:
				if (cbAlipay.isChecked()) {
					cbAlipay.setChecked(false);
					cbWechat.setChecked(true);
				}
				break;
			case R.id.settlement_goods_cb_rl2:
				if (cbWechat.isChecked()) {
					cbWechat.setChecked(false);
					cbAlipay.setChecked(true);
				}
				break;
			case R.id.settlement_goods_solve:
				tvPay.setEnabled(false);

				Intent intent = new Intent();
				intent.setAction("CN.YJ.ROBUST.REFRESHDATA");
				sendBroadcast(intent);

				if (cbAlipay.isChecked()) {
					payWithAlipay();
				} else {
					payWithWechat();
				}
				break;
			case R.id.settlement_goods_new_address:
				Intent addressIntent = new Intent(this, MineAddressManageActivity.class);
				addressIntent.putExtra("state", "new");
				addressIntent.putExtra("shopId", shopId);
				addressIntent.putExtra("productId", proId);
				startActivityForResult(addressIntent, Variables.NEW_ADDRESS);
				break;
			case R.id.settlement_goods_address:
				Intent chooseIntent = new Intent(this, MineAddressManageActivity.class);
				chooseIntent.putExtra("state", "choose");
				chooseIntent.putExtra("aid", addressId);
				chooseIntent.putExtra("shopId", shopId);
				chooseIntent.putExtra("productId", proId);
				startActivityForResult(chooseIntent, Variables.CHOOSE_ADDRESS);
				break;
			case R.id.goods_detial_ticket://@TODO 选择使用优惠券-----------------
				if (data != null) {
					if (data.getCoupons().size() != 0) {
						showDialogTicket(data);
					} else {
						Toast.makeText(this, "暂无优惠券", Toast.LENGTH_SHORT).show();
						rlTicket.setEnabled(false);
					}
				}
				break;
		}
	}

	private void showDialogTicket(final SettlementGoodsEntity.SettlementGoodsData data) {
		if (TicketDialog == null) {
			TicketDialog = new SettlementGoodsTicketDialog(SettlementGoodsActivity.this);
		}

		TicketDialog.setCustomDialog(data.getCoupons());
		if (!TicketDialog.isShowing()) {
			TicketDialog.show();
		}

		TicketDialog.getBtnFinish().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				checkedPosition = TicketDialog.getCheckedPosition();
				if (checkedPosition != -1) {
					userCouponId = data.getCoupons().get(checkedPosition).getUserCouponId();

					int couponType = data.getCoupons().get(checkedPosition).getCouponType();
					tvTickContent.setVisibility(View.VISIBLE);


					if (couponType == 1) {
						tickPic = data.getCoupons().get(checkedPosition).getFaceValueZk();
					} else {
						tickPic = data.getCoupons().get(checkedPosition).getFaceValue();
					}

					tickPic_ = Float.parseFloat(tickPic);//优惠券价格
					tvTickContent.setText(" 立省 " + tickPic + " 元 ");
					countTicketPic();

				} else {
					userCouponId = -1;
					tvTickContent.setVisibility(View.GONE);
					tickPic_ = 0;
					countTicketPic();

				}
				Log.i(TAG, "TicketDialog22222222 ---- checkedPosition  " + checkedPosition + " userCouponId :" + userCouponId);
				TicketDialog.dismiss();
			}
		});
		if (checkedPosition != -1) {
			TicketDialog.setCheckPosition(checkedPosition);
		}
	}

	private float tickAfterPic;//使用优惠券后的价格
	private boolean isTickAfterPics = false;

	// total = 0,  //  总价    //rest = 0;  余额  // tickPic_ 优惠券

	//tvTotal 最后的总价TextView
	//@TODO ---------------------------------------------------------------
	public void countTicketPic() {
//		rest - tickPic
		if (data != null) {
			total = Float.parseFloat(data.getOrderTotalMoney());
		}
		if (tickPic_ < total) {
			tickAfterPic = total - tickPic_;
			tvTotal.setText(new DecimalFormat("0.00").format(tickAfterPic) + "");
			isTickAfterPics = true;
		} else {
			tvTotal.setText("0.00");
			isTickAfterPics = false;
		}
		if (isA) {
//			tvRest.setText("-" + new DecimalFormat("0.00").format(tickAfterPic) + "元");
//			tvRestAll.setText("可用余额抵扣" + new DecimalFormat("0.00").format(rest - tickAfterPic) + "元");

			if (tickPic_ < total) {
				tickAfterPic = total - tickPic_;
				if (tickAfterPic < rest) {
					tvTotal.setText("0.00");
				} else {
					tvTotal.setText(new DecimalFormat("0.00").format(tickAfterPic - rest) + "");
				}
			} else {
				tvTotal.setText("0.00");
			}
		}
	}

	private String tvTotals;
	private boolean isA = false;


	//@TODO  在点击CheckBox的时候，显示不一样的使用金额------此处需要修改--
	private void onMoneyChange() {
		tvTotals = tvTotal.getText().toString().trim();
		total = Float.parseFloat(tvTotals);
		if (cbSwitch.isOpened()) {
			isA = true;
			if (isTickAfterPics) {
				if (total < rest) {
					tvTotal.setText("0.00");
				} else {
					tvTotal.setText(new DecimalFormat("0.00").format(total - rest) + "");
				}
			} else {
				if (total < rest) {
					tvTotal.setText("0.00");
				} else {
					tvTotal.setText(new DecimalFormat("0.00").format(total - rest) + "");
				}
			}
		} else {
			isA = false;
			if (isTickAfterPics) {//
				total = Float.parseFloat(data.getOrderTotalMoney());
				if (tickPic_ < total) {
					tickAfterPic = total - tickPic_;
					tvTotal.setText(new DecimalFormat("0.00").format(tickAfterPic) + "");
				} else {
					tvTotal.setText("0.00");
				}
			} else {
				total = Float.parseFloat(data.getOrderTotalMoney());
				if (tickPic_ >= total) {
//					tickAfterPic = total - tickPic_;
//					tvTotal.setText(new DecimalFormat("0.00").format(tickAfterPic) + "");
					tvTotal.setText("0.00");
				} else {
//					tvTotal.setText("0.00");
					tvTotal.setText(total + "");
				}
			}
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		LogUtils.i("我onActivityResult了");
		if (requestCode == Variables.NEW_ADDRESS || requestCode == Variables.CHOOSE_ADDRESS) {
			LogUtils.i("resquestCode的值" + requestCode + "...resultCode" + resultCode + "....data的值" + data + "....data的name" + data.getStringExtra("name"));
			if (data != null && !TextUtils.isEmpty(data.getStringExtra("name"))) {
				LogUtils.i("我进入展示布局了");
				rlAddress.setVisibility(View.VISIBLE);
				rlNewAddress.setVisibility(View.GONE);
				tvName.setText(data.getStringExtra("name"));
				tvTel.setText(data.getStringExtra("tel"));
				tvAddress.setText("收货地址：" + data.getStringExtra("area") + data.getStringExtra("detial"));
				addressId = data.getStringExtra("addressId");
			} else if (data != null && !TextUtils.isEmpty(data.getStringExtra("delete"))) {
				addressId = "";
				rlAddress.setVisibility(View.GONE);
				rlNewAddress.setVisibility(View.VISIBLE);
			} else {
				if (!TextUtils.isEmpty(addressId)) {
					rlAddress.setVisibility(View.VISIBLE);
					rlNewAddress.setVisibility(View.GONE);
				} else {
					rlAddress.setVisibility(View.GONE);
					rlNewAddress.setVisibility(View.VISIBLE);
				}
			}
			LogUtils.i("返回结果aid的值...." + addressId);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void doAsyncGetData() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("proId", proId);
		map.put("pro", pro);

		if (!TextUtils.isEmpty(sproductId)) {
			map.put("sproductId", sproductId);
		}
		map.put("proNumber", proNumber);
//        map.put("addressId","");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/payImmediately")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<SettlementGoodsEntity>() {

			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(SettlementGoodsActivity.this);
					if (!isFinishing()) {
						mDialog.show();
					}
				} else {
					if (!isFinishing()) {
						mDialog.show();
					}
				}
			}

			@Override
			public SettlementGoodsEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("payImmediately json的值" + json);
				return new Gson().fromJson(json, SettlementGoodsEntity.class);
			}

			@Override
			public void onResponse(SettlementGoodsEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					data = response.getData();
					setData(response.getData());
				} else {
					ToastUtils.showToast(SettlementGoodsActivity.this, "请求错误 :)" + response.getMsg());
				}
				dismissDialog();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				dismissDialog();
				LogUtils.i("网络请求失败 获取轮播图错误" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(SettlementGoodsActivity.this, "网络故障,请稍后再试 ");
				}
			}
		});
	}

	private void setData(SettlementGoodsEntity.SettlementGoodsData data) {
		if (!TextUtils.isEmpty(data.getAddressId())) {
			addressId = data.getAddressId();
			rlAddress.setVisibility(View.VISIBLE);
			rlNewAddress.setVisibility(View.GONE);
			tvAddress.setText("收货地址：" + data.getAreaDetail());
			tvTel.setText(data.getReceiverTel());
			tvName.setText(data.getReceiverName());
		} else {
			rlAddress.setVisibility(View.GONE);
			rlNewAddress.setVisibility(View.VISIBLE);
		}
		tvTitle.setText(data.getProName());
		tvStyle.setText(data.getSkuPropertiesName());
		tvNumber.setText("X" + data.getNum());
		tvSinglePrice.setText(data.getSkuPrice());
		tvGoodsPrice.setText("￥" + data.getProductTotal());
		tvTotal.setText(data.getOrderTotalMoney());

		if (!TextUtils.isEmpty(data.getUserMoney())) {
			tvRestAll.setText("可用余额抵扣" + data.getUserMoney() + "元");
			rest = Float.parseFloat(data.getUserMoney());
			if (rest <= 0) {
				rlRest.setVisibility(View.GONE);
			} else {
				rlRest.setVisibility(View.VISIBLE);
			}
		} else {
			rlRest.setVisibility(View.GONE);
		}

		total = Float.parseFloat(data.getOrderTotalMoney());
		if (total == 0) {
			cbSwitch.setEnabled(false);
		} else {
			cbSwitch.setEnabled(true);
		}

//		if (!TextUtils.isEmpty(data.getSystemValue()) || !data.getSystemValue().equals("0")) {
//			llLimit.setVisibility(View.VISIBLE);
//			tvLimit.setText("满" + data.getSystemValue() + "元免运费");
//		} else {
//			llLimit.setVisibility(View.GONE);
//		}
		if (!TextUtils.isEmpty(data.getPostage()) && !data.getPostage().equals("0")) {
			tvFee.setText("￥" + data.getPostage());
			tvFeePrice.setText("+￥" + data.getPostage());
		} else {
			tvFee.setText("免运费");
			tvFeePrice.setText("+￥0.00");
		}


//		RoundedImageView storeIcon;
//		TextView tvStoreName;

		Glide.with(getApplicationContext())
				.load(URLBuilder.getUrl(data.getShopImg()))
				.error(R.mipmap.default_goods)
				.centerCrop()
				.into(storeIcon);

		tvStoreName.setText(data.getShopName());


		Glide.with(getApplicationContext())
				.load(URLBuilder.getUrl(data.getProImg()))
				.error(R.mipmap.default_goods)
				.centerCrop()
				.into(ivGoods);
		onMoneyChange();
	}


	private void payWithWechat() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("proId", proId);
		map.put("pro", pro);
		map.put("orderPayChannel", "3");
		if (userCouponId != -1) {
			map.put("userCouponId", userCouponId + "");
		}
		if (!TextUtils.isEmpty(sproductId)) {
			map.put("sproductId", sproductId);
		}

		map.put("proNumber", proNumber);
		map.put("pay", "0");
		if (!TextUtils.isEmpty(addressId)) {
			map.put("addressId", addressId);
		} else {
			ToastUtils.showToast(this, "收货地址不能为空╮(╯▽╰)╭");
			tvPay.setEnabled(true);
			return;
		}
		if (rest != 0 && cbSwitch.isOpened()) {
			map.put("userMoneyType", "1");
		} else {
			map.put("userMoneyType", "2");
		}
		LogUtils.i("传输的值" + URLBuilder.format(map));
		try {
			OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/payImmediatelyAddOrder")
					.addParams("data", URLBuilder.format(map))
//					.addParams(Key.data, AESUtils.encryptData(Constant.KEY, URLBuilder.format(map)))
//					.addParams("key", RSAUtils.encryptByPublicKey(Constant.KEY))
					.tag(this).build().execute(new Utils.MyResultCallback<WXPayEntity>() {
				@Override
				public void onBefore(Request request) {
					super.onBefore(request);
					if (mDialog == null) {
						mDialog = new CustomProgressDialog(SettlementGoodsActivity.this);
						if (!isFinishing()) {
							mDialog.show();
						}
					} else {
						if (!isFinishing()) {
							mDialog.show();
						}
					}
				}

				@Override
				public WXPayEntity parseNetworkResponse(Response response) throws Exception {
					String json = response.body().string().trim();
					LogUtils.e("json的值" + json);
					NormalEntity normalEntity = new Gson().fromJson(json, NormalEntity.class);
					if (normalEntity.getData().equals("") && !normalEntity.getCode().equals("200")) {
						return new WXPayEntity(normalEntity.getCode(), normalEntity.getMsg());
					} else {
						return new Gson().fromJson(json, WXPayEntity.class);
					}
				}

				@Override
				public void onResponse(WXPayEntity response) {
					if (response != null && response.getCode().equals("200")) {
						if (response.getData() != null && response.getData().getAppPayJson() != null && !TextUtils.isEmpty(response.getData().getAppPayJson().getAppid())) {
							PayReq req = new PayReq();
							//req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
							req.appId = response.getData().getAppPayJson().getAppid();
							req.partnerId = response.getData().getAppPayJson().getPartnerid();
							req.prepayId = response.getData().getAppPayJson().getPrepayid();
							req.nonceStr = response.getData().getAppPayJson().getNoncestr();
							req.timeStamp = response.getData().getAppPayJson().getTimestamp();
							req.packageValue = "Sign=WXPay";
							req.sign = response.getData().getAppPayJson().getSign();
							req.extData = "app data"; // optional
							api.sendReq(req);
							mUtils.savePayOrder(response.getData().getOrderId());
							mUtils.savePayType("goods");
						} else {
							//请求成功 但是没返回微信数据 很有可能是余额的问题
							if (total - rest <= 0) {
								ToastUtils.showToast(SettlementGoodsActivity.this, "支付成功");
								Intent intent = new Intent(SettlementGoodsActivity.this, PayResultActivity.class);
								mUtils.savePayOrder(response.getData().getOrderId());
								mUtils.savePayType("goods");
								startActivity(intent);
								finish();
							} else {
								ToastUtils.showToast(SettlementGoodsActivity.this, "支付失败");
								Intent intent = new Intent(SettlementGoodsActivity.this, MineOrderDetailActivity.class);
								intent.putExtra("oid", response.getData().getOrderId());
								startActivity(intent);
								finish();
							}
						}
					} else {
						ToastUtils.showToast(SettlementGoodsActivity.this, "网络故障" + response.getMsg());
					}
					tvPay.setEnabled(true);
					dismissDialog();
				}

				@Override
				public void onError(Call call, Exception e) {
					super.onError(call, e);
					LogUtils.i("网络请求失败 获取轮播图错误" + e);
					if (call.isCanceled()) {
						call.cancel();
					} else {
						ToastUtils.showToast(SettlementGoodsActivity.this, "网络故障,请稍后再试 ");
					}

					tvPay.setEnabled(true);
					dismissDialog();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void payWithAlipay() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("proId", proId);
		map.put("pro", pro);
		map.put("orderPayChannel", "3");
		if (!TextUtils.isEmpty(sproductId)) {
			map.put("sproductId", sproductId);
		}
		if (userCouponId != -1) {
			map.put("userCouponId", userCouponId + "");
		}
		map.put("proNumber", proNumber);
		map.put("pay", "1");
		if (!TextUtils.isEmpty(addressId)) {
			map.put("addressId", addressId);
		} else {
			ToastUtils.custom(" 收货地址不能为空╮(╯▽╰)╭", 250);
//			ToastUtils.showToast(this, "收货地址不能为空╮(╯▽╰)╭");
			tvPay.setEnabled(true);
			return;
		}
		if (rest != 0 && cbSwitch.isOpened()) {
			map.put("userMoneyType", "1");
		} else {
			map.put("userMoneyType", "2");
		}
		try {
			OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/payImmediatelyAddOrder")
					.addParams("data", URLBuilder.format(map))
//					.addParams(Key.data, AESUtils.encryptData(Constant.KEY, URLBuilder.format(map)))
//					.addParams("key", RSAUtils.encryptByPublicKey(Constant.KEY))

					.tag(this).build().execute(new Utils.MyResultCallback<AlipayEntity>() {
				@Override
				public void onBefore(Request request) {
					super.onBefore(request);
					if (mDialog == null) {
						mDialog = new CustomProgressDialog(SettlementGoodsActivity.this);
						if (!isFinishing()) {
							mDialog.show();
						}
					} else {
						if (!isFinishing()) {
							mDialog.show();
						}
					}
				}

				@Override
				public AlipayEntity parseNetworkResponse(Response response) throws Exception {
					String json = response.body().string().trim();
					LogUtils.i("json的值" + json);
					NormalEntity normalEntity = new Gson().fromJson(json, NormalEntity.class);
					if (normalEntity.getData().equals("") && !normalEntity.getCode().equals("200")) {
						return new AlipayEntity(normalEntity.getCode(), normalEntity.getMsg());
					} else {
						return new Gson().fromJson(json, AlipayEntity.class);
					}
				}

				@Override
				public void onResponse(AlipayEntity response) {
					if (response != null && response.getCode().equals(response.HTTP_OK)) {
						if (response.getData() != null && response.getData().getOrderString() != null /*&& !TextUtils.isEmpty(response.getData().getAppPayJson().getAPPID())*/) {
							mUtils.savePayOrder(response.getData().getOrderId());
							mUtils.savePayType("goods");
							alipay(response.getData().getOrderString());
						} else {
							LogUtils.i("我进else了");
							if (total - rest <= 0) {
								ToastUtils.showToast(SettlementGoodsActivity.this, "支付成功");
								Intent intent = new Intent(SettlementGoodsActivity.this, PayResultActivity.class);
								mUtils.savePayOrder(response.getData().getOrderId());
								mUtils.savePayType("goods");
								startActivity(intent);
								finish();
							} else {
								ToastUtils.showToast(SettlementGoodsActivity.this, "支付失败");
								Intent intent = new Intent(SettlementGoodsActivity.this, MineOrderDetailActivity.class);
								intent.putExtra("oid", response.getData().getOrderId());
								startActivity(intent);
								finish();
							}
						}
					} else {
						ToastUtils.custom("请求错误 :)" + response.getMsg(), 250);
						//					ToastUtils.showToast(SettlementGoodsActivity.this, "故障:)" + response.getMsg());
					}
					tvPay.setEnabled(true);
					dismissDialog();
				}

				@Override
				public void onError(Call call, Exception e) {
					super.onError(call, e);
					LogUtils.i("网络请求失败" + e);
					if (call.isCanceled()) {
						call.cancel();
					} else {
						ToastUtils.custom(" 网络故障,请稍后再试 ", 250);
						//					ToastUtils.showToast(SettlementGoodsActivity.this, "网络故障,请稍后再试 ");
					}

					tvPay.setEnabled(true);
					dismissDialog();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void alipay(final String OrderString) {

		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				PayTask alipay = new PayTask(SettlementGoodsActivity.this);
				Map<String, String> result = alipay.payV2(OrderString, true);
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();

	/*	//秘钥验证的类型 true:RSA2 false:RSA
		boolean rsa2 = true;

		//构造支付订单参数列表
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(data.getAPPID(), rsa2, data);
		LogUtils.i("params的值" + params);
		//构造支付订单参数信息
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
		LogUtils.i("orderParam的值" + orderParam);
		//对支付参数信息进行签名
		LogUtils.i("RSA_PRIVATE_KEY的值" + data.getRSA_PRIVATE_KEY());
		String sign = OrderInfoUtil2_0.getSign(params, data.getRSA_PRIVATE_KEY(), rsa2);
		LogUtils.i("sing的值" + sign);
		//订单信息
		final String orderInfo = orderParam + "&" + sign;
		LogUtils.i("orderInfo的值" + orderInfo);
		//异步处理
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				//新建任务
				PayTask alipay = new PayTask(SettlementGoodsActivity.this);
				//获取支付结果
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();*/
	}


	private void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissDialog();
		OkHttpUtils.getInstance().cancelTag(this);
	}
}
