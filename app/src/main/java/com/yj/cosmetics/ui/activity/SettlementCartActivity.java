package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
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
import com.yj.cosmetics.model.SettlementsCartEntity;
import com.yj.cosmetics.model.WXPayEntity;
import com.yj.cosmetics.ui.adapter.SettlementCartAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.yj.cosmetics.widget.Dialog.SettlementCartTicketDialog;
import com.yj.cosmetics.widget.SwitchView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Suo on 2018/3/16.
 *
 *  提交订单页面
 */

public class SettlementCartActivity extends BaseActivity {
	@BindView(R.id.recyclerView)
	RecyclerView mRecyclerView;
	@BindView(R.id.goods_detial_ticket)
	RelativeLayout rlTicket;
	@BindView(R.id.settlement_cart_bottom)
	RelativeLayout rlBottom;
	@BindView(R.id.settlement_cart_solve)
	TextView tvSolve;
	@BindView(R.id.settlement_cart_tvTotal)
	TextView tvTotal;
	@BindView(R.id.settlement_cart_address)
	RelativeLayout rlAddress;
	@BindView(R.id.settlement_cart_tel)
	TextView tvTel;
	@BindView(R.id.goods_item_ticket_content)
	TextView tvTickContent;
	@BindView(R.id.settlement_cart_name)
	TextView tvName;
	@BindView(R.id.settlement_cart_content)
	TextView tvAddress;
	@BindView(R.id.settlement_cart_new_address)
	RelativeLayout rlNewAddress;
	@BindView(R.id.settlement_cart_fee)
	TextView tvFee;
	@BindView(R.id.settlement_cart_ll_limit)
	LinearLayout llLimit;
	@BindView(R.id.settlement_cart_tv_limit)
	TextView tvLimit;
	@BindView(R.id.settlement_cart_rl_rest)
	RelativeLayout rlRest;
	@BindView(R.id.settlement_cart_tv_rest_all)
	TextView tvRestAll;
	@BindView(R.id.settlement_cart_tv_rest)
	TextView tvRest;
	@BindView(R.id.settlement_cart_switch_btn)
	SwitchView switchBtn;
	@BindView(R.id.settlement_cart_tv_price)
	TextView tvPrice;
	@BindView(R.id.settlement_cart_tv_sendcost)
	TextView tvSendcost;
	@BindView(R.id.settlement_cart_cb2)
	CheckBox cbAlipay;
	@BindView(R.id.settlement_cart_cb1)
	CheckBox cbWechat;
	SettlementCartAdapter mAdapter;
	private String cartIdObj, addressId, tickPic;
	private List<SettlementsCartEntity.DataBean.ProArrayBean> mList;
	private SettlementsCartEntity.DataBean data;
	private float total = 0, rest = 0, coupon = 0;
	private CustomProgressDialog mDialog;
	SettlementCartTicketDialog TicketDialog;
	private int checkedPosition = -1, userCouponId = -1;
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
					LogUtils.i("Pay:" + resultInfo);
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, "9000")) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								Intent intent = new Intent(SettlementCartActivity.this, PayResultActivity.class);
								startActivity(intent);
								finish();
							}
						}, 400);
						Toast.makeText(SettlementCartActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(SettlementCartActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(SettlementCartActivity.this, MineOrderActivity.class);
						startActivity(intent);
						finish();
						tvSolve.setEnabled(true);
					}
					break;
			}
		}
	};
	private float tickPic_;


	@Override
	protected int getContentView() {
		return R.layout.activity_cart_settlement;
	}

	@Override
	protected void initView() {
		setTitleText("填写订单");
		mList = new ArrayList<>();
		cartIdObj = getIntent().getStringExtra("cartIdObj");
		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(layoutManager);
		mAdapter = new SettlementCartAdapter(this, mList);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setNestedScrollingEnabled(false);
		api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
	}

	@Override
	protected void initData() {
		doAsyncGetData();
	   /* switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	        @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onMoneyChange();
            }
        });*/
		switchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onMoneyChange();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		tvSolve.setEnabled(true);
		rlTicket.setEnabled(true);
	}

	@OnClick({R.id.settlement_cart_cb_rl1, R.id.settlement_cart_cb_rl2, R.id.settlement_cart_solve, R.id.settlement_cart_new_address, R.id.settlement_cart_address, R.id.goods_detial_ticket})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.settlement_cart_cb_rl1:
				if (cbAlipay.isChecked()) {
					cbAlipay.setChecked(false);
					cbWechat.setChecked(true);
				}
				break;
			case R.id.settlement_cart_cb_rl2:
				if (cbWechat.isChecked()) {
					cbWechat.setChecked(false);
					cbAlipay.setChecked(true);
				}
				break;
			case R.id.settlement_cart_solve:
				tvSolve.setEnabled(false);

				Intent intent = new Intent();
				intent.setAction("CN.YJ.ROBUST.REFRESHDATA");
				sendBroadcast(intent);

				if (cbAlipay.isChecked()) {
					payWithAlipay();
				} else {
					payWithWechat();
				}

				break;
			case R.id.settlement_cart_new_address:
				Intent addressIntent = new Intent(this, MineAddressManageActivity.class);
				addressIntent.putExtra("state", "new");
				addressIntent.putExtra("cartIdObj", cartIdObj);
				startActivityForResult(addressIntent, Variables.NEW_ADDRESS);
				break;
			case R.id.settlement_cart_address:
				Intent chooseIntent = new Intent(this, MineAddressManageActivity.class);
				chooseIntent.putExtra("state", "choose");
				chooseIntent.putExtra("cartIdObj", cartIdObj);
				chooseIntent.putExtra("aid", addressId);
				startActivityForResult(chooseIntent, Variables.CHOOSE_ADDRESS);
				break;
			case R.id.goods_detial_ticket:
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


	private void showDialogTicket(final SettlementsCartEntity.DataBean coupons) {
		if (TicketDialog == null) {
			TicketDialog = new SettlementCartTicketDialog(SettlementCartActivity.this);
		}
		TicketDialog.setCustomDialog(coupons);
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
					tvTickContent.setText(" 立省 " + tickPic + " 元 ");
					tickPic_ = Float.parseFloat(tickPic);//优惠券价格
					countTicketPic();

				} else {
					userCouponId = -1;
					tvTickContent.setVisibility(View.GONE);
					tickPic_ = 0;
					countTicketPic();
				}
				LogUtils.e("TicketDialog111111 ---- checkedPosition  " + checkedPosition + " userCouponId :" + userCouponId);
				TicketDialog.dismiss();
			}
		});
		if (checkedPosition != -1) {
			TicketDialog.setCheckPosition(checkedPosition);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Variables.NEW_ADDRESS || requestCode == Variables.CHOOSE_ADDRESS) {
			if (data != null && !TextUtils.isEmpty(data.getStringExtra("name"))) {
				rlAddress.setVisibility(View.VISIBLE);
				rlNewAddress.setVisibility(View.GONE);
				tvName.setText(data.getStringExtra("name"));
				tvTel.setText(data.getStringExtra("tel"));
				tvAddress.setText(data.getStringExtra("area") + data.getStringExtra("detial"));
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
		map.put("cartIdObj", cartIdObj);
		LogUtils.i("我传输的值" + URLBuilder.format(map));
//		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/cartsOrder.act")
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/newCartsOrder")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<SettlementsCartEntity>() {

			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(SettlementCartActivity.this);
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
			public SettlementsCartEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("newCartsOrder json的值" + json);
				return new Gson().fromJson(json, SettlementsCartEntity.class);
			}

			@Override
			public void onResponse(SettlementsCartEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					data = response.getData();
					setData(response.getData());
				} else {
					ToastUtils.showToast(SettlementCartActivity.this, "请求错误 :)" + response.getMsg());
				}
				dismissDialog();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络请求失败 获取轮播图错误" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(SettlementCartActivity.this, "网络故障,请稍后再试 ");
				}

				dismissDialog();
			}
		});
	}

	private void setData(SettlementsCartEntity.DataBean data) {
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
		tvPrice.setText("￥" + data.getProductTotal());
		tvTotal.setText(data.getOrderTotalMoney());

		total = Float.parseFloat(data.getOrderTotalMoney());
		if (total == 0) {
			switchBtn.setEnabled(false);
		} else {
			switchBtn.setEnabled(true);
		}

//		if (!TextUtils.isEmpty(data.getSystemValue()) || !data.getSystemValue().equals("0")) {
//			llLimit.setVisibility(View.VISIBLE);
//			tvLimit.setText("满" + data.getSystemValue() + "元免运费");
//		} else {
//			llLimit.setVisibility(View.GONE);
//		}

		if (!TextUtils.isEmpty(data.getPostage()) && !data.getPostage().equals("0")) {
			tvFee.setText("￥" + data.getPostage());
			tvSendcost.setText("+￥" + data.getPostage());
		} else {
			tvFee.setText("免运费");
			tvSendcost.setText("+￥0.00");
		}

		mList.clear();
		mList.addAll(data.getProArray());
		mAdapter.notifyDataSetChanged();
		onMoneyChange();
	}

	private float tickAfterPic;//使用优惠券后的价格
	private boolean isTickAfterPics = false;

	// total = 0,  //  总价    //rest = 0;  余额  // tickPic_ 优惠券

	//tvTotal 最后的总价TextView
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
		if (switchBtn.isOpened()) {
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

//	private void onMoneyChange() {
//		LogUtils.i("total的值====" + total);
//		LogUtils.i("rest的值-===" + rest);
//		LogUtils.i("计算的值-===" + new DecimalFormat("0.00").format(rest - total));
//
//		if (switchBtn.isOpened()) {
//			if (total < rest) {
//				tvRest.setText("-" + new DecimalFormat("0.00").format(total) + "元");
//				tvRestAll.setText("可用余额抵扣" + new DecimalFormat("0.00").format(rest - total) + "元");
//				tvTotal.setText("0.00");
//			} else {
//				tvRest.setText("-" + new DecimalFormat("0.00").format(rest) + "元");
//				tvRestAll.setText("可用余额抵扣0.00元");
//				tvTotal.setText(new DecimalFormat("0.00").format(total - rest) + "");
//			}
//		} else {
//			tvRest.setText("");
//			tvRestAll.setText("可用余额抵扣" + new DecimalFormat("0.00").format(rest) + "元");
//			tvTotal.setText(new DecimalFormat("0.00").format(total) + "");
//		}
//	}

	private void payWithAlipay() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("cartIds", cartIdObj);
		map.put("pay", "1");
		map.put("orderPayChannel", "3");
		if (userCouponId != -1) {
			map.put("userCouponId", userCouponId + "");
		}
		if (!TextUtils.isEmpty(addressId)) {
			map.put("addressId", addressId);
		} else {
			ToastUtils.custom(" 收货地址不能为空╮(╯▽╰)╭", 250);
//			ToastUtils.showToast(this, "收货地址不能为空╮(╯▽╰)╭");
			tvSolve.setEnabled(true);
			return;
		}
		if (rest != 0 && switchBtn.isOpened()) {
			map.put("userMoneyType", "1");
		} else {
			map.put("userMoneyType", "2");
		}
		LogUtils.i("传输的值" + URLBuilder.format(map));
		try {
			OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/insertCartOrder")
					.addParams("data", URLBuilder.format(map))
//					.addParams(Key.data, AESUtils.encryptData(Constant.KEY, URLBuilder.format(map)))
//					.addParams("key", RSAUtils.encryptByPublicKey(Constant.KEY))
					.tag(this).build().execute(new Utils.MyResultCallback<AlipayEntity>() {

				@Override
				public void onBefore(Request request) {
					super.onBefore(request);
					if (mDialog == null) {
						mDialog = new CustomProgressDialog(SettlementCartActivity.this);
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
					LogUtils.e("json的值" + json);
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
						//返回值为200 说明请求成功
						if (response.getData() != null && response.getData().getOrderString() != null /*&& !TextUtils.isEmpty(response.getData().getAppPayJson().getAPPID())*/) {
							mUtils.savePayOrder(response.getData().getOrderNum());
							mUtils.savePayType("cart");
							alipay(response.getData().getOrderString());
						} else {
							if (total - coupon - rest <= 0) {
								ToastUtils.showToast(SettlementCartActivity.this, "支付成功");
								Intent intent = new Intent(SettlementCartActivity.this, PayResultActivity.class);
								mUtils.savePayOrder(response.getData().getOrderNum());
								mUtils.savePayType("cart");
								startActivity(intent);
								finish();
							} else {
								ToastUtils.showToast(SettlementCartActivity.this, "支付失败");
								Intent intent = new Intent(SettlementCartActivity.this, MineOrderActivity.class);
								startActivity(intent);
								finish();
							}
						}
					} else {
						ToastUtils.custom(" 请求错误:)" + response.getMsg(), 250);
						//					ToastUtils.showToast(SettlementCartActivity.this, "请求错误 :)" + response.getMsg());
					}
					tvSolve.setEnabled(true);
					dismissDialog();
				}

				@Override
				public void onError(Call call, Exception e) {
					super.onError(call, e);
					LogUtils.e("网络请求失败" + e);
					if (call.isCanceled()) {
						call.cancel();
					} else {
						ToastUtils.custom(" 网络故障,请稍后再试 ", 250);
						//					ToastUtils.showToast(SettlementCartActivity.this, "网络故障,请稍后再试 ");
					}

					tvSolve.setEnabled(true);
					dismissDialog();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void payWithWechat() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("cartIds", cartIdObj);
		map.put("pay", "0");
		if (userCouponId != -1) {
			map.put("userCouponId", userCouponId + "");
		}

		if (!TextUtils.isEmpty(addressId)) {
			map.put("addressId", addressId);
		} else {
			ToastUtils.custom(" 收货地址不能为空╮(╯▽╰)╭", 250);
			tvSolve.setEnabled(true);
			return;
		}
		if (rest != 0 && switchBtn.isOpened()) {
			map.put("userMoneyType", "1");
		} else {
			map.put("userMoneyType", "2");
		}
		LogUtils.i("传输的值" + URLBuilder.format(map));
		try {
			OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/insertCartOrder")
					.addParams("data", URLBuilder.format(map))
//					.addParams(Key.data, AESUtils.encryptData(Constant.KEY, URLBuilder.format(map)))
//					.addParams("key", RSAUtils.encryptByPublicKey(Constant.KEY))
					.tag(this).build().execute(new Utils.MyResultCallback<WXPayEntity>() {

				@Override
				public void onBefore(Request request) {
					super.onBefore(request);
					if (mDialog == null) {
						mDialog = new CustomProgressDialog(SettlementCartActivity.this);
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
							mUtils.savePayOrder(response.getData().getOrderNum());
							mUtils.savePayType("cart");
						} else {
							//请求成功 但是没返回微信数据 很有可能是余额的问题
							if (total - coupon - rest <= 0) {
								ToastUtils.showToast(SettlementCartActivity.this, "支付成功");
								Intent intent = new Intent(SettlementCartActivity.this, PayResultActivity.class);
								mUtils.savePayOrder(response.getData().getOrderNum());
								mUtils.savePayType("cart");
								startActivity(intent);
								finish();
							} else {
								//失败 删除订单
								ToastUtils.showToast(SettlementCartActivity.this, "支付失败");
								Intent intent = new Intent(SettlementCartActivity.this, MineOrderActivity.class);
								startActivity(intent);
								finish();
							}
						}
					} else {
						ToastUtils.custom(" 请求错误:)" + response.getMsg(), 250);
						//					ToastUtils.showToast(SettlementCartActivity.this, "网络故障" + response.getMsg());
					}
					tvSolve.setEnabled(true);
					dismissDialog();
				}

				@Override
				public void onError(Call call, Exception e) {
					super.onError(call, e);
					LogUtils.i("网络请求失败" + e);
					if (call.isCanceled()) {
						call.cancel();
					} else {
						ToastUtils.showToast(SettlementCartActivity.this, "网络故障,请稍后再试 ");
					}
					tvSolve.setEnabled(true);
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
				PayTask alipay = new PayTask(SettlementCartActivity.this);
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


/*
		//秘钥验证的类型 true:RSA2 false:RSA
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
				PayTask alipay = new PayTask(SettlementCartActivity.this);
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
		dismissDialog();
		OkHttpUtils.getInstance().cancelTag(this);
		super.onDestroy();
	}
}
