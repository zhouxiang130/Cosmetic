package com.yj.cosmetics.ui.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.tencent.mm.opensdk.utils.Log;
import com.yj.cosmetics.MyApplication;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.Constant;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.base.Variables;
import com.yj.cosmetics.model.AlipayEntity;
import com.yj.cosmetics.model.MineOrderEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.model.PayResult;
import com.yj.cosmetics.model.ShareEntity;
import com.yj.cosmetics.model.WXPayEntity;
import com.yj.cosmetics.ui.activity.MineLogicalDetialActivity;
import com.yj.cosmetics.ui.activity.MineOrderActivity;
import com.yj.cosmetics.ui.activity.MineOrderDetailActivity;
import com.yj.cosmetics.ui.activity.PayResultActivity;
import com.yj.cosmetics.ui.activity.PostJudgeGoodsActivity;
import com.yj.cosmetics.ui.activity.SettlementGoodsActivity;
import com.yj.cosmetics.ui.activity.storeDetail.StoreDetailActivity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.yj.cosmetics.widget.Dialog.MineOrderPayDialog;
import com.yj.cosmetics.widget.Dialog.QuickeOrderDialog;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.yj.cosmetics.widget.WrapContentGridView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.onekeyshare.OnekeyShare;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2017/4/17.
 */

public class MineOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private final int flag;
	private String TAG = "MineOrderListAdapter";
	private MineOrderActivity mContext;
	List<MineOrderEntity.DataBean.UserOrderMapsBean> mList = new ArrayList<>();
	MineOrderAllClickListener mItemClickListener;
	CustomNormalContentDialog deleteDialog;
	UserUtils mUtils;
	private IWXAPI api;
	private static final int SDK_PAY_FLAG = 1001;

	private String[] payMode = new String[]{
			/*"账户余额",*/ "支付宝支付", "微信支付"
	};

	private Integer[] payIcon = new Integer[]{
//			R.mipmap.yuezhifu,
			R.mipmap.zhifubaozhifu,
			R.mipmap.weixinzhifu,
	};


	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case SDK_PAY_FLAG:
					PayResult payResult = new PayResult((Map<String, String>) msg.obj);
					//同步获取结果
					String resultInfo = payResult.getResult();
					LogUtils.i("我进入支付了==" + resultInfo);
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, "9000")) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								Intent intent = new Intent(mContext, PayResultActivity.class);
								mContext.startActivity(intent);
								mContext.doFragRefresh();
							}
						}, 400);
						Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
					}
					break;
			}
		}
	};


	private Handler mHandlers = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//            super.handleMessage(msg);
			switch (msg.what) {
				case Finish:
					LogUtils.i("我在handler这");
					ToastUtils.showToast(mContext, "分享成功了");
//                    disMissDialog();

					break;
				case Cancel:
					ToastUtils.showToast(mContext, "取消分享");
					break;
				case Failed:
					if (!msg.getData().isEmpty()) {
						String error = msg.getData().getString("error");
						LogUtils.i("error" + error);
						ToastUtils.showToast(mContext, "分享失败");
					}
					break;
			}
		}
	};

	private ShareEntity.ShareData shareData;


	public MineOrderListAdapter(MineOrderActivity mContext, List<MineOrderEntity.DataBean.UserOrderMapsBean> mList, UserUtils mUtils, int flag) {
		this.mContext = mContext;
		this.mList = mList;
		this.flag = flag;
		this.mUtils = mUtils;
		deleteDialog = new CustomNormalContentDialog(mContext);
		api = WXAPIFactory.createWXAPI(mContext, Constant.APP_ID);
	}

	public void setOnItemClickListener(MineOrderAllClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		OrderGoodsViewHolder orderGoodsViewHolder;
		orderGoodsViewHolder = new OrderGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mine_order_frag, parent, false), mItemClickListener);
		return orderGoodsViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof OrderGoodsViewHolder) {

			MineOrderListGoodsAdapter gridViewAdapter = new MineOrderListGoodsAdapter(mContext, mList.get(position).getUserOrderdetailMaps());
			((OrderGoodsViewHolder) holder).gridView.setAdapter(gridViewAdapter);
			((OrderGoodsViewHolder) holder).gridView.setFocusable(false);
			((OrderGoodsViewHolder) holder).gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
					Intent intent = new Intent(mContext, MineOrderDetailActivity.class);
					intent.putExtra("oid", mList.get(position).getOrderId());
					intent.putExtra("flag", flag + "");
					if (mList.get(position).getOrderState().equals("1") || mList.get(position).getOrderState().equals("3") ||
							mList.get(position).getOrderState().equals("4") || mList.get(position).getOrderState().equals("5")) {
						mContext.startActivityForResult(intent, Variables.REFRESH_ORDER_LIST);
					} else {
						mContext.startActivity(intent);
					}
				}
			});
//			((OrderGoodsViewHolder) holder).tvOrderNum.setText("订单号 :  " + mList.get(position).getOrderNum());
			((OrderGoodsViewHolder) holder).tvNum.setText("共" + mList.get(position).getProNumber() + "件商品");
			((OrderGoodsViewHolder) holder).tvState.setText(mList.get(position).getOrderStateName());

			if (mList.get(position).getIsAccelerate() != null && mList.get(position).getIsAccelerate().equals("1")) {
				((OrderGoodsViewHolder) holder).tvOrder.setVisibility(View.VISIBLE);
			} else {
				((OrderGoodsViewHolder) holder).tvOrder.setVisibility(View.GONE);
			}


			((OrderGoodsViewHolder) holder).tvOrder.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					doAsyncGetShare(mList.get(position).getOrderId(), mList.get(position).getShopId());
				}
			});


			//获取自定义的类实例
			Glide.with(mContext)
					.load(URLBuilder.getUrl(mList.get(position).getShopImg()))
					.error(R.mipmap.default_goods)
					.centerCrop()
					.into(((OrderGoodsViewHolder) holder).shopIv);

			((OrderGoodsViewHolder) holder).shopName.setText(mList.get(position).getShopName());

			if (mList.get(position).getShopId() != null) {
				((OrderGoodsViewHolder) holder).ivMore.setVisibility(View.VISIBLE);
				((OrderGoodsViewHolder) holder).shopDetail.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(mContext, StoreDetailActivity.class);
						intent.putExtra("shopId", mList.get(position).getShopId());
						mContext.startActivity(intent);
					}
				});
			} else {
				((OrderGoodsViewHolder) holder).ivMore.setVisibility(View.GONE);
			}


			switch (mList.get(position).getOrderState()) {
				case "0":
					//待付款
					if (!TextUtils.isEmpty(mList.get(position).getProductState()) && mList.get(position).getProductState().equals("2")) {
						//处于下架状态 不让付款 跳进详情显示已下架
						((OrderGoodsViewHolder) holder).tvState.setText("已下架");
						((OrderGoodsViewHolder) holder).llBottom.setVisibility(View.VISIBLE);
						((OrderGoodsViewHolder) holder).tvBottomRight.setVisibility(View.GONE);
						((OrderGoodsViewHolder) holder).tvBottomLeft.setText("删除订单");
						((OrderGoodsViewHolder) holder).tvBottomLeft.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								showDeleteDialog(mList.get(position).getOrderId(), "1", position, "删除");
							}
						});

					} else if (!TextUtils.isEmpty(mList.get(position).getProductState()) && mList.get(position).getProductState().equals("3")) {
						//处于售罄状态 不让付款 跳进详情显示已售罄
						((OrderGoodsViewHolder) holder).tvState.setText("已售罄");
						((OrderGoodsViewHolder) holder).llBottom.setVisibility(View.VISIBLE);
						((OrderGoodsViewHolder) holder).tvBottomRight.setVisibility(View.GONE);
						((OrderGoodsViewHolder) holder).tvBottomLeft.setText("删除订单");
						((OrderGoodsViewHolder) holder).tvBottomLeft.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								showDeleteDialog(mList.get(position).getOrderId(), "1", position, "删除");
							}
						});
					} else {
						//正常状态 正常显示
						((OrderGoodsViewHolder) holder).tvBottomRight.setText("立即支付");
						((OrderGoodsViewHolder) holder).tvBottomLeft.setText("取消订单");
						((OrderGoodsViewHolder) holder).llBottom.setVisibility(View.VISIBLE);
						((OrderGoodsViewHolder) holder).tvBottomLeft.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								showDeleteDialog(mList.get(position).getOrderId(), "1", position, "取消");
							}
						});
						((OrderGoodsViewHolder) holder).tvBottomRight.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								//@TODO -----------------------
								showPayDialog(mList, position, payMode, payIcon);

//								if (!TextUtils.isEmpty(mList.get(position).getOrderpayStyle())) {
//									if (mList.get(position).getOrderpayStyle().equals("2")) {
//										//2 微信支付
//										doPayWeChat(mList.get(position).getOrderId(), "2", ((OrderGoodsViewHolder) holder).tvBottomRight);
//									} else {
//										//支付宝支付
//										payWithAlipay(mList.get(position).getOrderId(), "1", ((OrderGoodsViewHolder) holder).tvBottomRight);
//									}
//								} else {
//									ToastUtils.showToast(mContext, "无法获取支付方式");
//								}
							}
						});
					}
					((OrderGoodsViewHolder) holder).tvTotal.setText(mList.get(position).getOrderPayamount());
					break;
				case "1":
					//待发货
					((OrderGoodsViewHolder) holder).llBottom.setVisibility(View.GONE);
					((OrderGoodsViewHolder) holder).tvTotal.setText(mList.get(position).getOrderPayamount());
					break;
				case "2":
					//待收货
					((OrderGoodsViewHolder) holder).tvBottomRight.setText("确认收货");
					((OrderGoodsViewHolder) holder).tvBottomLeft.setText("查看物流");
					((OrderGoodsViewHolder) holder).llBottom.setVisibility(View.VISIBLE);
					((OrderGoodsViewHolder) holder).tvBottomLeft.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							checkLogistic(mList.get(position).getOrderId(), "1");
						}
					});
					((OrderGoodsViewHolder) holder).tvBottomRight.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							showConfirmGetDialog(mList.get(position).getOrderId(), position);
						}
					});
					((OrderGoodsViewHolder) holder).tvTotal.setText(mList.get(position).getOrderPayamount());
					break;
				case "3":
					//待评价
					((OrderGoodsViewHolder) holder).llBottom.setVisibility(View.VISIBLE);
					((OrderGoodsViewHolder) holder).tvBottomRight.setText("立即评价");
					((OrderGoodsViewHolder) holder).tvBottomLeft.setText("查看物流");
					((OrderGoodsViewHolder) holder).tvBottomLeft.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							checkLogistic(mList.get(position).getOrderId(), "1");
						}
					});
					((OrderGoodsViewHolder) holder).tvBottomRight.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							doJudgeGoods(mList.get(position).getOrderId());
						}
					});
					((OrderGoodsViewHolder) holder).tvTotal.setText(mList.get(position).getOrderPayamount());
					break;
				case "4":
					//已完成
					((OrderGoodsViewHolder) holder).tvState.setText("已完成");
					((OrderGoodsViewHolder) holder).llBottom.setVisibility(View.VISIBLE);
					((OrderGoodsViewHolder) holder).tvBottomRight.setVisibility(View.GONE);
					((OrderGoodsViewHolder) holder).tvBottomLeft.setText("删除订单");
					((OrderGoodsViewHolder) holder).tvBottomLeft.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							showDeleteDialog(mList.get(position).getOrderId(), "1", position, "删除");
						}
					});
					break;
				case "5":
					//已失效
					((OrderGoodsViewHolder) holder).tvBottomRight.setVisibility(View.GONE);
					((OrderGoodsViewHolder) holder).tvBottomLeft.setText("取消订单");
					((OrderGoodsViewHolder) holder).llBottom.setVisibility(View.VISIBLE);
					((OrderGoodsViewHolder) holder).tvBottomLeft.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							showDeleteDialog(mList.get(position).getOrderId(), "1", position, "取消");
						}
					});
					((OrderGoodsViewHolder) holder).tvTotal.setText(mList.get(position).getOrderPayamount());
					break;
			}
		}

	}


	private MineOrderPayDialog mineOrderPayDialog;
	private int checkedPosition;
	private TextView btnFinish;

	private void showPayDialog(final List<MineOrderEntity.DataBean.UserOrderMapsBean> mList,
	                           final int position, String[] payMode, Integer[] payIcon) {

		if (mineOrderPayDialog == null) {
			mineOrderPayDialog = new MineOrderPayDialog(mContext);
		}
		mineOrderPayDialog.setCustomDialog(payMode, payIcon, mList.get(position).getOrderPayamount());
		if (!mineOrderPayDialog.isShowing()) {
			mineOrderPayDialog.show();
		}
		btnFinish = mineOrderPayDialog.getBtnFinish();
		btnFinish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				checkedPosition = mineOrderPayDialog.getCheckedPosition();
				switch (checkedPosition) {
					case 0://1支付宝支付
						payWithAlipay(mList.get(position).getOrderId(), "1", btnFinish);
						break;
					case 1://2 微信支付
						doPayWeChat(mList.get(position).getOrderId(), "0", btnFinish);
						break;
				}
				mineOrderPayDialog.dismiss();
			}
		});
		mineOrderPayDialog.setCheckPosition(checkedPosition);
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	class OrderGoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.gridView)
		WrapContentGridView gridView;
		@BindView(R.id.frag_mine_shop_iv)
		RoundedImageView shopIv;
		@BindView(R.id.frag_mine_shop_detail)
		RelativeLayout shopDetail;
		@BindView(R.id.frag_mine_shop_name)
		TextView shopName;
		@BindView(R.id.image_store_more)
		ImageView ivMore;

		//@BindView(R.id.pay_header_head)
		//TextView tvOrderNum;

		@BindView(R.id.pay_header_state)
		TextView tvState;
		@BindView(R.id.order_bottom_check)
		TextView tvOrder;
		@BindView(R.id.pay_bottom_total)
		TextView tvTotal;
		@BindView(R.id.pay_bottom_num)
		TextView tvNum;
		@BindView(R.id.pay_bottom_pay)
		TextView tvBottomRight;
		@BindView(R.id.pay_bottom_check)
		TextView tvBottomLeft;
		@BindView(R.id.pay_bottom_ll)
		LinearLayout llBottom;

		private MineOrderAllClickListener mListener;


		public OrderGoodsViewHolder(View itemView, MineOrderAllClickListener listener) {
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


	public interface MineOrderAllClickListener {
		void onItemClick(View view, int postion);
	}


	public void doAsyncGetShare(String orderId, String shopId) {
		Map<String, String> map = new HashMap<>();
		map.put("shopId", shopId);
		map.put("userId", mUtils.getUid());
		map.put("orderId", orderId);
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/share")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<ShareEntity>() {

			@Override
			public ShareEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("parseNetworkResponse json的值" + json);
				return new Gson().fromJson(json, ShareEntity.class);
			}

			@Override
			public void inProgress(float progress) {
				super.inProgress(progress);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(mContext);
					if (!mContext.isFinishing()) {
						mDialog.show();
					}
				} else {
					if (!mContext.isFinishing()) {
						mDialog.show();
					}
				}
			}


			@Override
			public void onResponse(ShareEntity response) {
				disMissDialogs();
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null) {
						shareData = response.getData();
						QuickOrder();

					}
				} else {
					ToastUtils.showToast(mContext, "无法获取分享信息" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				disMissDialogs();
				LogUtils.i("网络请求失败 " + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(mContext, "获取分享信息失败，请检查网络稍后再试");
				}

			}
		});
	}


	QuickeOrderDialog QuickeOrderDialog;

	//@TODO ---------
	private void QuickOrder() {
		if (QuickeOrderDialog == null) {
			QuickeOrderDialog = new QuickeOrderDialog(mContext);
		}
		if (!QuickeOrderDialog.isShowing()) {
			QuickeOrderDialog.show();
		}


		QuickeOrderDialog.getTvContent().setText(URLBuilder.URLBaseHeader + shareData.getUrl());
		QuickeOrderDialog.getTvCopyUrl().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
				cm.setText(QuickeOrderDialog.getTvContent().getText());
				ToastUtils.showToast(mContext, "复制成功");
			}
		});

		QuickeOrderDialog.getTvFriend().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				showShare(Wechat.NAME);
			}
		});
		QuickeOrderDialog.getTvWeChat().setOnClickListener(new View.OnClickListener() {//微信好友
			@Override
			public void onClick(View v) {
//				showShare(WechatMoments.NAME);
			}
		});
	}

	final int Finish = 0x11;
	final int Cancel = 0x88;
	final int Failed = 0x99;


	private void showShare(String platform) {
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize();
		if (shareData != null && shareData.getUrl() != null) {
			LogUtils.i(shareData.getImage() + "....img的值");
			LogUtils.i(shareData.getUrl() + ".....link的值");
			LogUtils.i(shareData.getTitle() + ",,,title的值");
			LogUtils.i("我在data部位空里" + platform);
			if (shareData.getTitle() != null) {
				if (platform.equals(SinaWeibo.NAME)) {
					oks.setText(shareData.getTitle() + URLBuilder.URLBaseHeader + shareData.getUrl());
				} else {
					oks.setText(shareData.getTitle());
					oks.setUrl(URLBuilder.URLBaseHeader + shareData.getUrl());
					oks.setTitle(shareData.getTitle());
					// titleUrl是标题的网络链接，QQ和QQ空间等使用
					oks.setTitleUrl(URLBuilder.URLBaseHeader + shareData.getUrl());
					// text是分享文本，所有平台都需要这个字段
				}
			}
			// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//			oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
			if (!TextUtils.isEmpty(shareData.getImage())) {
				LogUtils.i("我进入到图片这了" + shareData.getImage());
				if (shareData.getImage().contains(",")) {
					//获取第一个,的位置,我们需要获取从开始到,之前的所有字符
					String imgUrl = shareData.getImage().substring(0, shareData.getImage().indexOf(","));
					LogUtils.i("获取,的值......" + shareData.getImage().indexOf(","));
					LogUtils.i("多张图片分割后取路径......" + imgUrl);
					oks.setImageUrl(URLBuilder.getUrl(imgUrl));
				} else {
					LogUtils.i("我进入到图片else了" + shareData.getImage());
					oks.setImageUrl(URLBuilder.getUrl(shareData.getImage()));
				}
			}
			// comment是我对这条分享的评论，仅在人人网和QQ空间使用
			oks.setComment("请输入您的评论");
			// site是分享此内容的网站名称，仅在QQ空间使用
			oks.setSite(mContext.getString(R.string.app_name));
			// siteUrl是分享此内容的网站地址，仅在QQ空间使用
			oks.setSiteUrl("http://www.szffxz.com/weChat/homePageTwo/toIndex");
			LogUtils.i("分享信息设置结束了");
		} else {
			ToastUtils.showToast(mContext, "无法获取分享信息,请检查网络");
		}
		if (platform != null) {
			LogUtils.i("我在设置分享平台");
			oks.setPlatform(platform);
		}

		oks.setCallback(new PlatformActionListener() {
			@Override
			public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                doShareConfirm(data.getLink());
				Message msg = mHandlers.obtainMessage();
				msg.what = Finish;
				mHandlers.sendMessage(msg);
				LogUtils.i("我完成分享了");
			}

			@Override
			public void onError(Platform platform, int i, Throwable throwable) {
//                cancel = true;
				Bundle bundle = new Bundle();
				bundle.putString("error", throwable + "");
				Message msg = mHandlers.obtainMessage();
				msg.what = Failed;
				msg.setData(bundle);
				mHandlers.sendMessage(msg);
				LogUtils.i("我分享失败了" + throwable);
			}

			@Override
			public void onCancel(Platform platform, int i) {
//                cancel = true;
				Message msg = mHandlers.obtainMessage();
				msg.what = Cancel;
				mHandlers.sendMessage(msg);
				LogUtils.i("我分享退出了");
			}
		});
// 启动分享GUI
		LogUtils.i("我要启动分享的UI了");
		oks.show(mContext);
	}


	private void checkLogistic(String order, String type) {
		Intent intent = new Intent(mContext, MineLogicalDetialActivity.class);
		intent.putExtra("order", order);
		mContext.startActivity(intent);
	}

	private void showDeleteDialog(final String oid, final String type, final int position, String delete) {
		if (deleteDialog == null) {
			deleteDialog = new CustomNormalContentDialog(mContext);
		}
		if (!deleteDialog.isShowing()) {
			deleteDialog.show();
		}
		deleteDialog.getTvTitle().setText("确认" + delete + "该订单吗?");
		deleteDialog.getTvContent().setText("确定" + delete + "订单，取消后可在商品页重新下单");
		deleteDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				doDeleteOrder(oid, type, position);
				deleteDialog.dismiss();
			}
		});
	}

	private void showConfirmGetDialog(final String oid, final int position) {
		if (deleteDialog == null) {
			deleteDialog = new CustomNormalContentDialog(mContext);
		}
		if (!deleteDialog.isShowing()) {
			deleteDialog.show();
		}
		deleteDialog.getTvTitle().setText("确认收货吗?");
		deleteDialog.getTvContent().setText("请确保您已签收");
		deleteDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				doConfirmGetGoods(oid, position);
				deleteDialog.dismiss();
			}
		});
	}


	private void doDeleteOrder(String oid, String type, final int position) {
		Map<String, String> map = new HashMap<>();
		map.put("orderNum", oid);
		map.put("type", type);
		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/cancleOrder")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {
			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					ToastUtils.showToast(mContext, "删除订单成功");
					mList.remove(position);
					notifyDataSetChanged();
				} else {
					ToastUtils.showToast(mContext, response.getCode() + ":)" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(mContext, "网络故障,请稍后再试");
				}
			}
		});
	}


	private void doConfirmGetGoods(String oid, final int position) {
		Map<String, String> map = new HashMap<>();
		map.put("oid", oid);
		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/confrimOrders")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {
			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					ToastUtils.showToast(mContext, "确认收货成功");
					mList.get(position).setOrderState("5");
					notifyDataSetChanged();
					//发送广播
					Intent intent = new Intent();
					intent.setAction("com.yj.robust.receiver_one");
					mContext.sendBroadcast(intent);
				} else {
					ToastUtils.showToast(mContext, response.getCode() + " :)" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(mContext, "网络故障,请稍后再试");
				}
			}
		});
	}

	private void doJudgeGoods(String oid) {
		//商品评价
		Intent intent = new Intent(mContext, PostJudgeGoodsActivity.class);
		intent.putExtra("oid", oid);
		mContext.startActivityForResult(intent, Variables.REFRESH_ORDER_LIST);
	}

	private void doPayWeChat(final String oid, final String type, final TextView tvPay) {
		tvPay.setEnabled(false);
		Map<String, String> map = new HashMap<>();
		map.put("oid", oid);
		map.put("pay", type);
		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/alipayPay")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<WXPayEntity>() {

			@Override
			public void inProgress(float progress) {
				super.inProgress(progress);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(mContext);
					if (!mContext.isFinishing()) {
						mDialog.show();
					}
				} else {
					if (!mContext.isFinishing()) {
						mDialog.show();
					}
				}
			}

			@Override
			public WXPayEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.e("json的值" + json);
				return new Gson().fromJson(json, WXPayEntity.class);
			}

			@Override
			public void onResponse(WXPayEntity response) {
				disMissDialogs();
				if (response != null && response.getCode().equals("200")) {
					if (response.getData() != null && !TextUtils.isEmpty(response.getData().getAppPayJson().getAppid())) {
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
						mUtils.savePayOrder(oid);
						mUtils.savePayType("goods");
						MyApplication.orderDetial = false;
						MyApplication.orderlistReceiver = true;
						mContext.registBroadcastReceiver();
					} else {
						//请求成功 但是没返回微信数据 很有可能是余额的问题
						ToastUtils.showToast(mContext, "无法获取支付信息");
					}
				} else {
					ToastUtils.showToast(mContext, response.getCode() + ":)" + response.getMsg());
				}
				tvPay.setEnabled(true);
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				disMissDialogs();
				tvPay.setEnabled(true);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(mContext, "网络故障,请稍后再试");
				}
			}
		});
	}

	CustomProgressDialog mDialog;


	private void payWithAlipay(final String oid, final String type, final TextView tvPay) {
		tvPay.setEnabled(false);
		Map<String, String> map = new HashMap<>();
		map.put("oid", oid);
		map.put("pay", type);
		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/alipayPay")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<AlipayEntity>() {

			@Override
			public void inProgress(float progress) {
				super.inProgress(progress);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(mContext);
					if (!mContext.isFinishing()) {
						mDialog.show();
					}
				} else {
					if (!mContext.isFinishing()) {
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
					if (response.getData() != null && response.getData().getOrderString() != null /*&& !TextUtils.isEmpty(response.getData().getAppPayJson().getAPPID())*/) {
						mUtils.savePayOrder(oid);
						mUtils.savePayType("goods");
						alipay(response.getData().getOrderString());
					} else {
						if (response.getData() != null && response.getData().getCode().equals(response.HTTP_OK)) {
							ToastUtils.showToast(mContext, "支付成功");
							Intent intent = new Intent(mContext, PayResultActivity.class);
							mUtils.savePayOrder(oid);
							mUtils.savePayType("goods");
							mContext.startActivity(intent);
							mContext.doFragRefresh();
						} else {
							ToastUtils.showToast(mContext, "支付失败");
						}
					}
				} else {
					ToastUtils.showToast(mContext, response.getCode() + " :)" + response.getMsg());
				}
				tvPay.setEnabled(true);
				disMissDialogs();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				disMissDialogs();
				tvPay.setEnabled(true);
				LogUtils.i("网络请求失败 获取轮播图错误" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(mContext, "网络故障,请稍后再试 ");
				}

			}
		});
	}

	private void alipay(final String OrderString) {

		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				PayTask alipay = new PayTask(mContext);
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
				PayTask alipay = new PayTask(mContext);
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
		if (deleteDialog != null) {
			deleteDialog.dismiss();
			deleteDialog = null;
		}
	}


	private void disMissDialogs() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}


	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		dismissDialog();
		disMissDialogs();
		super.onDetachedFromRecyclerView(recyclerView);
	}
}