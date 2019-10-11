package com.yj.cosmetics.ui.activity.goodDetail;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.utils.ZhiChiConstant;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.broadcastreceiver.MyReceiver;
import com.yj.cosmetics.function.CustomServices;
import com.yj.cosmetics.model.GoodsCommentEntity;
import com.yj.cosmetics.model.GoodsEntity;
import com.yj.cosmetics.model.GoodsEntitys;
import com.yj.cosmetics.model.GoodsSaleEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.model.SaleStyleEntity;
import com.yj.cosmetics.model.ShareEntity;
import com.yj.cosmetics.model.ShopCartEntity;
import com.yj.cosmetics.model.StyleDataEntity;
import com.yj.cosmetics.ui.MainActivity;
import com.yj.cosmetics.ui.activity.SettlementGoodsActivity;
import com.yj.cosmetics.ui.activity.storeDetail.StoreDetailActivity;
import com.yj.cosmetics.ui.adapter.GoodsDetailContentAdapter;
import com.yj.cosmetics.ui.adapter.GoodsDetialTitleAdapter;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.CustomBanner.CustomRollPagerView;
import com.yj.cosmetics.widget.CustomViewGroup.CustomSizeDialogViewGroup;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.yj.cosmetics.widget.Dialog.CustomShareDialog;
import com.yj.cosmetics.widget.Dialog.CustomSizeDialog;
import com.yj.cosmetics.widget.Dialog.GoodDetailTicketDialogs;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import cn.onekeyshare.OnekeyShare;

/**
 * Created by Suo on 2018/3/16.
 *
 * @TODO 商品详情界面
 */

public class GoodsDetailActivity extends BaseActivity implements CustomRollPagerView.ScrollChangeListener, GoodsDetailContentAdapter.ShowDetialInterface,
		CustomSizeDialogViewGroup.OnGroupItemClickListener, GoodsDetailContentAdapter.showDialogTicket
//		,GoodDetail_contract.View
{

	private static final String TAG = "GoodsDetailActivity";
	@BindView(R.id.recyclerView_title)
	RecyclerView recyclerViewTitle;
	@BindView(R.id.recyclerView_content)
	RecyclerView mRecyclerView;
	@BindView(R.id.goods_detial_like)
	ImageView ivLike;
	@BindView(R.id.goods_detial_rl_like)
	RelativeLayout rlLike;
	@BindView(R.id.goods_detial_cartNum)
	TextView tvCartNum;
	@BindView(R.id.goods_detial_buy)
	Button btnBuy;
	@BindView(R.id.goods_detial_add)
	Button btnAdd;
	@BindView(R.id.ll_all_buttom)
	LinearLayout llAllButtom;
	@BindView(R.id.rl_toolbar_all)
	RelativeLayout rlTitleAll;
	@BindView(R.id.goods_detial_store)
	RelativeLayout rlStore;
	@BindView(R.id.goods_detial_scrollview)
	NestedScrollView mScrollView;
	@BindView(R.id.frag_mine_tv_send_num)
	TextView tvMessageNum;
	@BindView(R.id.frag_home_v_head)
	View vHead;
	@BindView(R.id.home_vline)
	View vLine;
	@BindView(R.id.ll_all_buttom_float_layer)
	View vFloatLayer;
	@BindView(R.id.im_return)
	ImageView imReturn;
	@BindView(R.id.im_share)
	ImageView imShare;

	final int Finish = 0x11;
	final int Cancel = 0x88;
	final int Failed = 0x99;
	private int mHeight;


	//计算过后的商品高度和评论高度
	public int goodsHeight;
	public int judgeHeight;
	public int BannerHeight;

	GoodsDetialTitleAdapter mTitleAdapter;
	GoodsDetailContentAdapter mAdapter;
	private CustomProgressDialog loadingDialog;
	CustomShareDialog shareDialog;

	CustomSizeDialog dialog;
	GoodDetailTicketDialogs TicketDialog;
	private MyReceiver receiver;//广播

	LinearLayoutManager layoutManager;

	private List<String> mTitleList;
	private GoodsEntitys.DataBeanX data;
	private ShareEntity.ShareData shareData;
	private GoodsCommentEntity.GoodsCommentData mJudge;
	private List<GoodsSaleEntity.GoodsSaleData> mSale;
	public List<SaleStyleEntity> mStyle = new ArrayList<>();
	private Information userInfo;

	private boolean isClick = false;
	private String productId, sproductId;

	//	private String newDate;
	private MyThread myThread;

	public static final int VISIBLE = 0x00000000;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					break;
			}
			super.handleMessage(msg);
		}
	};


	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//            super.handleMessage(msg);
			switch (msg.what) {
				case Finish:
					LogUtils.i("我在handler这");
					ToastUtils.showToast(GoodsDetailActivity.this, "分享成功了");
//                    disMissDialog();

					break;
				case Cancel:
					ToastUtils.showToast(GoodsDetailActivity.this, "取消分享");
					break;
				case Failed:
					if (!msg.getData().isEmpty()) {
						String error = msg.getData().getString("error");
						LogUtils.i("error" + error);
						ToastUtils.showToast(GoodsDetailActivity.this, "分享失败");
					}
					break;
			}
		}
	};
//	private WebView webView;


	@Override
	protected int getContentView() {
		return R.layout.activity_goods_detail;
	}

	@Override
	protected void initView() {
		userInfo = new Information();
		mTitleList = new ArrayList<>();
		productId = getIntent().getStringExtra("productId");
		sproductId = getIntent().getStringExtra("sproductId");
		regReceiver();
		final LinearLayoutManager titlelayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

		recyclerViewTitle.setLayoutManager(titlelayoutManager);
		mTitleAdapter = new GoodsDetialTitleAdapter(this, mTitleList);
		recyclerViewTitle.setAdapter(mTitleAdapter);
		recyclerViewTitle.setVisibility(View.GONE);
//		vHead.setBackgroundResource(Color.argb(0, 255, 255, 255));
		layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		layoutManager.setSmoothScrollbarEnabled(true);
		layoutManager.setAutoMeasureEnabled(true);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setNestedScrollingEnabled(false);
		mAdapter = new GoodsDetailContentAdapter(this, data, mJudge);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setShowDetialInterface(this);
		mAdapter.setShowDialogInterface(this);
//		webView = mAdapter.getWebView();

		//获取标题栏高度
		ViewTreeObserver viewTreeObserver = rlTitleAll.getViewTreeObserver();
		viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				rlTitleAll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				mHeight = Math.round(getResources().getDimension(R.dimen.dis208) - rlTitleAll.getHeight());//这里取的高度应该为图片的高度-标题栏
			}
		});

		mTitleAdapter.setOnItemClickListener(new GoodsDetialTitleAdapter.SpendDetialClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
//				if (postion == 2) {
//					if (data != null && !TextUtils.isEmpty(data.getSpiderUrl())) {
//						Intent intent = new Intent(GoodsDetailActivity.this, NormalWebViewActivity.class);
//						intent.putExtra("url", URLBuilder.URLBaseHeader + data.getSpiderUrl());
//						intent.putExtra("title", "溯源");
//						startActivity(intent);
//						return;
//					}
//				}
				isClick = true;
				mTitleAdapter.mPosition = postion;
				mTitleAdapter.notifyDataSetChanged();
				switch (postion) {
					case 0:
						mRecyclerView.smoothScrollToPosition(0);
//						mScrollView.smoothScrollTo(0, 0);
						mScrollView.scrollTo(0, 0);
						break;
					case 1:
						layoutManager.scrollToPositionWithOffset(2, 0);
//                        mScrollView.smoothScrollTo(0, judgeHeight + goodsHeight);
//						mScrollView.smoothScrollTo(0, goodsHeight);
						mScrollView.scrollTo(0, (goodsHeight / 2));
						break;
					case 2:
						mRecyclerView.smoothScrollToPosition(1);
//						mScrollView.smoothScrollTo(0, judgeHeight + goodsHeight);
						mScrollView.scrollTo(0, judgeHeight + goodsHeight);
						break;
					case 3:
//						mScrollView.smoothScrollTo(0, judgeHeight + goodsHeight);
						mScrollView.scrollTo(0, judgeHeight + goodsHeight);
						break;
				}
				recyclerViewTitle.postDelayed(new Runnable() {
					@Override
					public void run() {
						isClick = false;
					}
				}, 600);
			}
		});


//		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//			@Override
//			public void onScrolled(RecyclerView recyclerView, int scrollX, int scrollY) {
//				super.onScrolled(recyclerView, scrollX, scrollY);
//
//
//				Log.e(TAG, "onScrolled: " + scrollY);
//				if (!isClick) {
//					if (scrollY < goodsHeight) {
//						mTitleAdapter.mPosition = 0;
//						mTitleAdapter.notifyDataSetChanged();
//					} else if (scrollY > goodsHeight && scrollY < goodsHeight + judgeHeight) {
//						mTitleAdapter.mPosition = 1;
//						mTitleAdapter.notifyDataSetChanged();
//					} else if (scrollY > goodsHeight + judgeHeight) {
//						if (data != null && !TextUtils.isEmpty(data.getSpiderUrl())) {
//							mTitleAdapter.mPosition = 3;
//						} else {
//							mTitleAdapter.mPosition = 2;
//						}
//						mTitleAdapter.notifyDataSetChanged();
//					}
//				}
//
//				if (scrollY <= 0) {
//
//					Log.i(TAG, "onScrollChange1111: " + "<<<<<<<<<<<<<<<= 0");
//					//顶部图处于最顶部，标题栏透明
//					setTopColor(Color.argb(0, 255, 255, 255));
//					rlTitleAll.setBackgroundColor(Color.argb(0, 255, 255, 255));
//					vLine.setVisibility(View.GONE);
//					recyclerViewTitle.setVisibility(View.GONE);
//
//				} else if (scrollY > 0 && scrollY < mHeight) {
//					Log.i(TAG, "onScrollChange1111: " + "> 0 < mHeight ");
//					//滑动过程中，渐变
//					float scale = (float) scrollY / mHeight;//算出滑动距离比例
//					float alpha = (255 * scale);//得到透明度
//					setTopColor(Color.argb((int) alpha, 0, 0, 0));
//					rlTitleAll.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
//					vLine.setVisibility(View.GONE);
//					recyclerViewTitle.setVisibility(View.VISIBLE);
//				} else {
//					Log.i(TAG, "onScrollChange1111: " + "---------------------------");
//					//过顶部图区域，标题栏定色
//					rlTitleAll.setBackgroundColor(Color.argb(255, 255, 255, 255));
//					setTopColor(Color.argb(255, 0, 0, 0));
//					vLine.setVisibility(View.VISIBLE);
//				}
//
//			}
//		});


		mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
			@Override
			public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
				if (!isClick) {
					if (scrollY < goodsHeight) {
						mTitleAdapter.mPosition = 0;
						mTitleAdapter.notifyDataSetChanged();
					} else if (scrollY > goodsHeight && scrollY < goodsHeight + judgeHeight) {
						mTitleAdapter.mPosition = 1;
						mTitleAdapter.notifyDataSetChanged();
					} else if (scrollY > goodsHeight + judgeHeight) {
						mTitleAdapter.mPosition = 2;
						mTitleAdapter.notifyDataSetChanged();
					}
				}
				if (scrollY <= 0) {
					//顶部图处于最顶部，标题栏透明
					setTopColor(Color.argb(0, 255, 255, 255));
					rlTitleAll.setBackgroundColor(Color.argb(0, 160, 80, 243));
					imReturn.getBackground().setAlpha(255);
					imShare.getBackground().setAlpha(255);
					vLine.setVisibility(View.GONE);
					recyclerViewTitle.setVisibility(View.GONE);
				} else if (scrollY > 0 && scrollY < mHeight) {
					//滑动过程中，渐变
					float scale = (float) scrollY / mHeight;//算出滑动距离比例
					float alpha = (255 * scale);//得到透明度
					setTopColor(Color.argb((int) alpha, 0, 0, 0));
					rlTitleAll.setBackgroundColor(Color.argb((int) alpha, 160, 80, 243));
					vLine.setVisibility(View.GONE);
					recyclerViewTitle.setVisibility(View.VISIBLE);
					imReturn.getBackground().setAlpha(255 - (int) alpha);
					imShare.getBackground().setAlpha(255 - (int) alpha);
//					int strokeWidth = 5; // 3dp 边框宽度
//					int roundRadius = 15; // 8dp 圆角半径
//					int strokeColor = Color.parseColor("#2E3135");//边框颜色int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色
//					GradientDrawable gd = new GradientDrawable();//创建drawable
//					gd.setColor(fillColor);
//					gd.setCornerRadius(roundRadius);
//					gd.setStroke(strokeWidth, strokeColor);
//					int colors[] = { 0xff255779 , 0xff3e7492, 0xffa6c0cd };//分别为开始颜色，中间夜色，结束颜
//					GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
				} else {
					//过顶部图区域，标题栏定色
					rlTitleAll.setBackgroundColor(Color.argb(255, 160, 80, 243));
					setTopColor(Color.argb(255, 0, 0, 0));
					imReturn.getBackground().setAlpha(0);
					imShare.getBackground().setAlpha(0);
					vLine.setVisibility(View.VISIBLE);
				}

			}
		});
/*		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
			}
		});*/
		transTitle();
	}

	@TargetApi(21)
	private void setTopColor(int color) {
		if (Build.VERSION.SDK_INT >= 21) {
			Log.e(TAG, "setTopColor: " + color);
			vHead.setBackgroundColor(color);
		}
	}


	@TargetApi(21)
	private void transTitle() {
		if (Build.VERSION.SDK_INT >= 21) {
			View decorView = getWindow().getDecorView();
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			decorView.setSystemUiVisibility(option);
			getWindow().setStatusBarColor(Color.TRANSPARENT);
		}
	}


	@Override
	public void showDialogs(int type, GoodsEntitys.DataBeanX proDetailCoupon) {
		if (TicketDialog == null) {
			SoftReference aSoftRef = new SoftReference(GoodsDetailActivity.this);
			GoodsDetailActivity anotherRef = (GoodsDetailActivity) aSoftRef.get();
			TicketDialog = new GoodDetailTicketDialogs(anotherRef);
		}
		TicketDialog.setCustomDialog(type, proDetailCoupon, mUtils.getUid());

		if (!TicketDialog.isShowing()) {
			TicketDialog.show();
		}
		TicketDialog.getCommitButtom().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TicketDialog.dismiss();
			}
		});
	}

	@Override
	protected void initData() {
		loadingDialog = new CustomProgressDialog(this);
//		goodDetailPresenter.doAsyncGetDetial();
		doAsyncGetDetial();
	}

	@Override
	protected void onResume() {
		rlLike.setClickable(true);
		if (mUtils.isLogin() && !TextUtils.isEmpty(mUtils.getUid())) {
			doAsyncGetCart();
		}
		super.onResume();
	}

	@OnClick({R.id.goods_detial_buy,
			R.id.goods_detial_rl_like,
			R.id.goods_detial_rlcart,
			R.id.goods_detial_add,
			R.id.goods_detial_rl_return,
			R.id.goods_detial_rl_share,
			R.id.goods_detial_custom_service,
			R.id.goods_detial_store})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.goods_detial_buy:
				if (btnBuy.getText().toString().equals("抢购提醒")) {
					if (mUtils.isLogin()) {
						setAsynrRemind();
						btnBuy.setEnabled(false);
					} else {
						IntentUtils.IntentToLogin(GoodsDetailActivity.this);
					}
					return;
				}
				showDetial(2);
				break;
			case R.id.goods_detial_add:
				showDetial(1);
				break;
			case R.id.goods_detial_rl_like:
				if (mUtils.isLogin()) {
					LogUtils.i("我进入网络操作了");
					rlLike.setClickable(false);
					doAsyncCollect();
				} else {
					IntentUtils.IntentToLogin(GoodsDetailActivity.this);
				}
				break;
			case R.id.goods_detial_rlcart:
				if (mUtils.isLogin()) {
					Intent intentCart = new Intent(this, MainActivity.class);
					intentCart.putExtra("page", "3");
					startActivity(intentCart);
					cancelDialog();
				} else {
					IntentUtils.IntentToLogin(GoodsDetailActivity.this);
				}
				break;
			case R.id.goods_detial_rl_return:
				onBackPressed();
				break;
			case R.id.goods_detial_rl_share:
				doAsyncGetShare();
				break;
			//联系客服
			case R.id.goods_detial_custom_service:
				//判断用户是否登录 ，未登录 先去登录
				if (mUtils.isLogin()) {
					int visibility = tvMessageNum.getVisibility();
					if (visibility == VISIBLE) {
						tvMessageNum.setVisibility(View.GONE);
					}
					doCustomServices();
				} else {
					IntentUtils.IntentToLogin(this);
				}
				break;
			case R.id.goods_detial_store:
				Intent intent = new Intent(GoodsDetailActivity.this, StoreDetailActivity.class);
				intent.putExtra("shopId", data.getData().getShopId());
				startActivity(intent);

				break;
		}
	}

	/**
	 * 设置抢购提示
	 */
	private void setAsynrRemind() {

		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("productId", productId);

		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/rushToBuyReminding")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("setAsynrRemind --json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					ToastUtils.showToast(GoodsDetailActivity.this, "已开启抢购提醒");
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障 " + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络请求失败 获取轮播图错误" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障,请稍后再试 ");
				}
			}
		});
	}

	private void doCustomServices() {
		//用户信息设置
		//设置用户自定义字段
		userInfo.setUseRobotVoice(false);//这个属性默认都是false。想使用需要付费。付费才可以设置为true。
		userInfo.setUid(mUtils.getUid());
		userInfo.setTel(mUtils.getTel());
		userInfo.setRealname(mUtils.getUserName());
		Log.i(TAG, "doCustomServices: " + mUtils.getTel() + "------" + mUtils.getUserName() + "---" + URLBuilder.getUrl(mUtils.getAvatar()));
		userInfo.setUname(mUtils.getUserName());
		userInfo.setFace(URLBuilder.getUrl(mUtils.getAvatar()));//头像

		if (data != null) {
			//咨询信息设置
			//@TODO  SP 存储需要咨询商品的
			preferencesUtil.setValue("product_name", data.getData().getProductName());
			preferencesUtil.setValue("Product_abstract", data.getData().getProductAbstract());
			preferencesUtil.setValue("product_listimg", data.getData().getProductListimg());
			preferencesUtil.setValue("product_url", data.getData().getProductUrl());
			CustomServices customServices = new CustomServices(this);
			customServices.doCustomServices(preferencesUtil);
		}
	}


	//设置广播获取新收到的信息和未读消息数
	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int noReadNum = intent.getIntExtra("noReadCount", 0);
			String content = intent.getStringExtra("content");
			//未读消息数
			if (noReadNum != 0) {
				tvMessageNum.setVisibility(View.VISIBLE);
				tvMessageNum.setText(noReadNum + "");
			}
			//新消息内容
			com.sobot.chat.utils.LogUtils.i("新消息内容:" + content);
		}
	}

	private void regReceiver() {
		//注册广播获取新收到的信息和未读消息数
		if (receiver == null) {
			receiver = new MyReceiver();
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(ZhiChiConstant.sobot_unreadCountBrocast);
		registerReceiver(receiver, filter);
	}


	public Button getBtnBuy() {
		return btnBuy;
	}

	public Button getBtnAdd() {
		return btnAdd;
	}


	@Override
	public void showDetial(int position) {
		//显示规格dialog
		if (mSale == null) {
			return;
		}
		if (data == null) {
			return;
		}
		if (dialog == null) {
			dialog = new CustomSizeDialog(GoodsDetailActivity.this, GoodsDetailActivity.this, data, mSale);
		}
		if (!dialog.isShowing()) {
			dialog.show();
		}
//		if (!TextUtils.isEmpty(data.getIfStartTimeLimit())) {
//			if (data.getIfStartTimeLimit().equals("yes")) {
//				//尚未开始
//				dialog.getBtnBuy().setVisibility(View.GONE);
//				dialog.getBtnCart().setBackgroundResource(R.drawable.shape_corner_size);
//			} else {
//				//已经开始
//				dialog.getBtnBuy().setVisibility(View.VISIBLE);
//			}
//		}
		dialog.getBtnCart().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mUtils.isLogin()) {
					if (checkForGoods()) {
						StringBuffer stringBuffer = new StringBuffer();
						for (int i = 0; i < mStyle.size(); i++) {
							stringBuffer.append(mStyle.get(i).getTitleId() + ":" + mStyle.get(i).getStyleId() + ";");
						}
						LogUtils.i("最终的stringBuffer值" + stringBuffer);
						dialog.getGoodsCount();
						doAddCart(stringBuffer.toString(), dialog.getGoodsCount().toString());
					} else {
						ToastUtils.showToast(GoodsDetailActivity.this, "请选择商品属性");
					}
				} else {
					IntentUtils.IntentToLogin(GoodsDetailActivity.this);
				}
			}
		});
		dialog.getBtnBuy().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mUtils.isLogin()) {
					if (checkForGoods()) {
						StringBuffer stringBuffer = new StringBuffer();
						for (int i = 0; i < mStyle.size(); i++) {
							stringBuffer.append(mStyle.get(i).getTitleId() + ":" + mStyle.get(i).getStyleId() + ";");
						}
						LogUtils.i("最终的stringBuffer值" + stringBuffer);
						Intent intent = new Intent(GoodsDetailActivity.this, SettlementGoodsActivity.class);
						intent.putExtra("proId", productId);
						intent.putExtra("sproductId", sproductId);
						intent.putExtra("pro", stringBuffer.toString());
						intent.putExtra("shopId", data.getData().getProductId());
						intent.putExtra("proNumber", dialog.getGoodsCount());
						startActivity(intent);
						dialog.cancel();
					} else {
						ToastUtils.showToast(GoodsDetailActivity.this, "请选择商品属性");
					}
				} else {
					IntentUtils.IntentToLogin(GoodsDetailActivity.this);
				}

			}
		});
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialogInterface) {
				LogUtils.i("我进入cancelListener了");
				if (checkForGoods()) {
					StringBuffer stringBuffer = new StringBuffer();
					for (int i = 0; i < mStyle.size(); i++) {
						stringBuffer.append(mStyle.get(i).getTitle() + ":" + mStyle.get(i).getStyle() + " ");
					}
					mAdapter.setStyle(stringBuffer.toString());
				}
			}
		});
	}

	@Override
	public void onGroupItemClick(int item, String chooseText, int position) {
		mStyle.get(position).setTitle(mSale.get(position).getPropesName());
		mStyle.get(position).setTitleId(mSale.get(position).getPropesId());
		mStyle.get(position).setStyle(mSale.get(position).getJsonArray().get(item).getProvalue());
		mStyle.get(position).setStyleId(mSale.get(position).getJsonArray().get(item).getProvalueId());
	   /* for(int i = 0;i<mStyle.size();i++){
	        LogUtils.i("mStyle的值"+mStyle.get(i).getTitleId());
        }*/
		if (checkForGoods()) {
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < mStyle.size(); i++) {
				stringBuffer.append(mStyle.get(i).getTitleId() + ":" + mStyle.get(i).getStyleId() + ";");
			}
			LogUtils.i("最终的stringBuffer值" + stringBuffer);
			doGetStyleData(stringBuffer.toString());
		}
	}

	private void doAsyncGetDetial() {
		Map<String, String> map = new HashMap<>();
		if (mUtils.isLogin() && !TextUtils.isEmpty(mUtils.getUid())) {
			map.put("userId", mUtils.getUid());
		}
		if (productId != null) {
			map.put("productId", productId);
		}
		if (sproductId != null) {
			map.put("sproductId", sproductId);
		}
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/productDetails")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<GoodsEntitys>() {

			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (loadingDialog == null) {
					loadingDialog = new CustomProgressDialog(GoodsDetailActivity.this);
					if (!isFinishing()) {
						loadingDialog.show();
					}
				} else {
					if (!isFinishing()) {
						loadingDialog.show();
					}
				}
			}

			@Override
			public GoodsEntitys parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("productDetails - json的值" + json);
				return new Gson().fromJson(json, GoodsEntitys.class);
			}

			@Override
			public void onResponse(GoodsEntitys response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					data = null;
					data = response.getData();
//					newDate = response.getData().getTime();
					mTitleList.add("商品");
					mTitleList.add("评论");
//					if (data != null && !TextUtils.isEmpty(data.getSpiderUrl())) {
//						mTitleList.add("溯源");
//					}
					mTitleList.add("详情");
					setData();
					mTitleAdapter.notifyDataSetChanged();

					mAdapter.setData(data);
					doAsyncGetJudge();
				} else {
					dismissDialog3();
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障 " + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				dismissDialog3();
				LogUtils.i("网络请求失败" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障,无法获取商品详情信息 ");
				}

			}
		});
	}

	private void setData() {
		if (data.getData().getShopId() != null && !data.getData().getShopId().equals("")) {
			rlStore.setVisibility(View.VISIBLE);
		} else {
			rlStore.setVisibility(View.GONE);
		}
//		if (data.getReceipt() != null && !data.getReceipt().equals("")) {
//			if (data.getReceipt().equals("1")) {
//				vFloatLayer.setVisibility(View.GONE);
//				btnBuy.setEnabled(true);
//				btnAdd.setEnabled(true);
//			} else {
//				vFloatLayer.setVisibility(View.VISIBLE);
//				btnBuy.setEnabled(false);
//				btnAdd.setEnabled(false);
//			}
//		}


//		if (!TextUtils.isEmpty(data.getEndTimeStyle())) {
//
//			if (data.getEndTimeStyle().equals("yes")) {//
//				btnAdd.setVisibility(View.GONE);
//				btnBuy.setBackgroundResource(R.drawable.shape_corner_six);
//				btnBuy.setEnabled(false);
//				btnBuy.setText("活动结束");
//
//			} else {
//
//				if (!TextUtils.isEmpty(data.getIfStartTimeLimit())) {
//					if (data.getIfStartTimeLimit().equals("yes")) {
//						//尚未开始
////									btnBuy.setEnabled(false);
//						btnBuy.setText("抢购提醒");
//					} else {
//						//已经开始
//						if (data.getReceipt() != null && !data.getReceipt().equals("")) {
//							if (data.getReceipt().equals("1")) {
//								btnBuy.setEnabled(true);
//							} else {
//								btnBuy.setEnabled(false);
//							}
//						}
//
//						btnBuy.setText("立即购买");
//						dealWithTimer();
//					}
//				}
//			}
//		}
		if (!TextUtils.isEmpty(data.getData().getWhetcollection())) {
			if (data.getData().getWhetcollection().equals("no")) {
				ivLike.setImageResource(R.mipmap.like_x);
			} else {
				ivLike.setImageResource(R.mipmap.like_fill_x);
			}
		}
//		if (!TextUtils.isEmpty(data.getCartNumber())) {
//			if (data.getCartNumber().equals("0")) {
//				tvCartNum.setVisibility(View.GONE);
//			} else {
//				tvCartNum.setVisibility(View.VISIBLE);
//				tvCartNum.setText(data.getCartNumber());
//			}
//		} else {
//			tvCartNum.setVisibility(View.GONE);
//		}
	}

	private void doAsyncGetJudge() {
		Map<String, String> map = new HashMap<>();
		map.put("productId", productId);

		if (!TextUtils.isEmpty(sproductId)) {
			map.put("sproductId", sproductId);
		}
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/commentTwo")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<GoodsCommentEntity>() {
			@Override
			public GoodsCommentEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i(" commentTwo - json的值" + json);
				return new Gson().fromJson(json, GoodsCommentEntity.class);
			}

			@Override
			public void onResponse(GoodsCommentEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					mJudge = null;
					mJudge = response.getData();
					mAdapter.setJudge(mJudge);
					doAsyncGetInfo();
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障 " + response.getMsg());
				}
				dismissDialog3();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络故障" + e);
				dismissDialog3();
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障,无法获取评论信息 ");
				}
			}
		});
	}

	private void doAsyncGetInfo() {
		Map<String, String> map = new HashMap<>();
		if (productId != null) {
			map.put("proId", productId);
		}

		//@TODO 额外增加一个字段：sproductId 3.0
		if (sproductId != null) {
			map.put("sproductId", sproductId);
		}

		LogUtils.i("doAsyncGetInfo" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/choShopInfo")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<GoodsSaleEntity>() {
			@Override
			public GoodsSaleEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("choShopInfo - json的值" + json);
				return new Gson().fromJson(json, GoodsSaleEntity.class);
			}

			@Override
			public void onResponse(GoodsSaleEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (mSale == null) {
						mSale = new ArrayList<>();
					}
					mSale.clear();
					mSale = response.getData();
					for (int i = 0; i < mSale.size(); i++) {
						SaleStyleEntity saleStyleEntity = new SaleStyleEntity();
						mStyle.add(saleStyleEntity);
					}
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障 " + response.getMsg());
				}
				dismissDialog3();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络请求失败" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障,无法获取商品属性信息 ");
				}
				dismissDialog3();
			}
		});
	}

	public void doAsyncCollect() {
		Map<String, String> map = new HashMap<>();
		map.put("proId", productId);
		map.put("userId", mUtils.getUid());
		if (sproductId != null) {
			map.put("sproductId", sproductId);
		}
		LogUtils.i("传输的网络数据" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/saveDeleteCollection")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {
			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (loadingDialog == null) {
					loadingDialog = new CustomProgressDialog(GoodsDetailActivity.this);
					if (!isFinishing()) {
						loadingDialog.show();
					}
				} else {
					if (!isFinishing()) {
						loadingDialog.show();
					}
				}
			}

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
					if (response.getData() != null) {
						if (response.getData().equals("1")) {
							ToastUtils.showToast(GoodsDetailActivity.this, "收藏成功");
							ivLike.setImageResource(R.mipmap.like_fill_x);
						} else {
							ToastUtils.showToast(GoodsDetailActivity.this, "取消收藏");
							ivLike.setImageResource(R.mipmap.like_x);
						}
					}
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障 " + response.getMsg());
				}
				rlLike.setClickable(true);
				dismissDialog3();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络请求失败 " + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障,请稍后再试 ");
				}
				rlLike.setClickable(true);
				dismissDialog3();
			}
		});
	}

	private void doGetStyleData(String pro) {
		Map<String, String> map = new HashMap<>();
		map.put("proId", productId);
		map.put("pro", pro);
		//@TODO 额外增加一个字段：sproductId 3.0
		if (sproductId != null) {
			map.put("sproductId", sproductId);
		}
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/proSkuImg")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<StyleDataEntity>() {

			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (loadingDialog == null) {
					loadingDialog = new CustomProgressDialog(GoodsDetailActivity.this);
					if (!isFinishing()) {
						loadingDialog.show();
					}
				} else {
					if (!isFinishing()) {
						loadingDialog.show();
					}
				}
			}

			@Override
			public StyleDataEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, StyleDataEntity.class);
			}

			@Override
			public void onResponse(StyleDataEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
//                    ToastUtils.showToast(GoodsDetailActivity.this, "添加成功");
					if (dialog == null) {
						return;
					}
					dialog.resetView(response.getData().getImg(), response.getData().getPrice(), response.getData().getNum());
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障 " + response.getMsg());
				}
				dismissDialog3();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络请求失败 " + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障,无法获取商品属性信息 ");
				}

				dismissDialog3();
			}
		});
	}

	private void doAddCart(String pro, String number) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("proId", productId);
		map.put("pro", pro);

		//@TODO 额外增加一个字段：shopId
		if (data.getData().getProductId() != null) {
			map.put("shopId", data.getData().getProductId());
		}

		map.put("proNumber", number);
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/addShopCar")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {
			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (loadingDialog == null) {
					loadingDialog = new CustomProgressDialog(GoodsDetailActivity.this);
					if (!isFinishing()) {
						loadingDialog.show();
					}
				} else {
					if (!isFinishing()) {
						loadingDialog.show();
					}
				}
			}

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
					ToastUtils.showToast(GoodsDetailActivity.this, "添加成功");
					doAsyncGetCart();
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障 " + response.getMsg());
				}
				dismissDialog3();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				dismissDialog3();
				LogUtils.i("网络请求失败 获取轮播图错误" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障,请稍后再试 ");
				}

			}
		});
	}

	private void doAsyncGetCart() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		LogUtils.i("购物车传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/cartNumber")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<ShopCartEntity>() {
			@Override
			public ShopCartEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetCart --json的值" + json);
				return new Gson().fromJson(json, ShopCartEntity.class);
			}

			@Override
			public void onResponse(ShopCartEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData().getCartNumber().equals("0")) {
						tvCartNum.setVisibility(View.GONE);
					} else {
						tvCartNum.setVisibility(View.VISIBLE);
						tvCartNum.setText(response.getData().getCartNumber());
					}
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障 " + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络请求失败 " + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "网络故障,无法获取购物车信息 ");
				}

			}
		});
	}


	private boolean checkForGoods() {
		for (int a = 0; a < mStyle.size(); a++) {
			if (mStyle.get(a).getTitleId() == null || mStyle.get(a).getStyleId() == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
//        LogUtils.i("我onPageScrolled了"+arg0+"...float的值"+arg1+".....arg2的值"+arg2);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
//        LogUtils.i("我onPageScrollStateChanged了"+arg0);
	}

	@Override
	public void onPageSelected(int arg0) {
//        LogUtils.i("最终的值" + arg0 % mAdapter.mBannerAdapter.getRealCount());
		int position = arg0 % mAdapter.mBannerAdapter.getRealCount();
		if (layoutManager != null) {
			TextView tvNow = (TextView) layoutManager.findViewByPosition(0).findViewById(R.id.goods_detial_banner_tvnow);
//            LogUtils.i("tvNow+=====" + tvNow);
			if (tvNow != null) {
				tvNow.setText((position + 1) + "");
			}
		}
	}


	public void doAsyncGetShare() {
		Map<String, String> map = new HashMap<>();
		map.put("codeOrId", productId);
		map.put("type", "1");
		LogUtils.i("传输的网络数据" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/shareUrl")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<ShareEntity>() {

			@Override
			public ShareEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("parseNetworkResponse json的值" + json);
				return new Gson().fromJson(json, ShareEntity.class);
			}

			@Override
			public void onResponse(ShareEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null) {
						shareData = response.getData();
						if (shareDialog == null) {
							shareDialog = new CustomShareDialog(GoodsDetailActivity.this);
							setItemClickListener();
						}
						if (!shareDialog.isShowing()) {
							shareDialog.show();
						}
					}
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "无法获取分享信息 :)" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络请求失败 " + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(GoodsDetailActivity.this, "获取分享信息失败，请检查网络稍后再试 ");
				}

			}
		});
	}

	private void setItemClickListener() {
		if (shareDialog != null) {
			shareDialog.setOnGridItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
					switch (i) {
						case 0:
							showShare(QQ.NAME);
							break;
						case 1:
							showShare(Wechat.NAME);
							break;
						case 2:
							showShare(SinaWeibo.NAME);
							break;
						case 3:
							showShare(WechatMoments.NAME);
							break;
					}
					dismissDialog2();
				}
			});
		}
	}

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
			oks.setSite(getString(R.string.app_name));
			// siteUrl是分享此内容的网站地址，仅在QQ空间使用
			oks.setSiteUrl("http://www.51xianchang.com");
			LogUtils.i("分享信息设置结束了");
		} else {
			ToastUtils.showToast(this, "无法获取分享信息,请检查网络");
		}
		if (platform != null) {
			LogUtils.i("我在设置分享平台");
			oks.setPlatform(platform);
		}

		oks.setCallback(new PlatformActionListener() {
			@Override
			public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                doShareConfirm(data.getLink());
				Message msg = mHandler.obtainMessage();
				msg.what = Finish;
				mHandler.sendMessage(msg);
				LogUtils.i("我完成分享了");
			}

			@Override
			public void onError(Platform platform, int i, Throwable throwable) {
//                cancel = true;
				Bundle bundle = new Bundle();
				bundle.putString("error", throwable + "");
				Message msg = mHandler.obtainMessage();
				msg.what = Failed;
				msg.setData(bundle);
				mHandler.sendMessage(msg);
				LogUtils.i("我分享失败了" + throwable);
			}

			@Override
			public void onCancel(Platform platform, int i) {
//                cancel = true;
				Message msg = mHandler.obtainMessage();
				msg.what = Cancel;
				mHandler.sendMessage(msg);
				LogUtils.i("我分享退出了");
			}
		});
// 启动分享GUI
		LogUtils.i("我要启动分享的UI了");
		oks.show(this);
	}

	private void dismissDialog3() {
		if (loadingDialog != null) {
			loadingDialog.dismiss();
			loadingDialog = null;
		}
	}

	private void dismissDialog2() {
		if (shareDialog != null) {
			shareDialog.dismiss();
			shareDialog = null;
		}
	}

	public void cancelDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.cancel();
		}
	}

	class MyThread extends Thread {
		//用来停止线程
		boolean endThread;
		GoodsEntity.GoodsData mRecommendActivitiesList;

		public MyThread(GoodsEntity.GoodsData mRecommendActivitiesList) {
			this.mRecommendActivitiesList = mRecommendActivitiesList;
		}

		@Override
		public void run() {
			while (!endThread) {
				try {
//线程每秒钟执行一次
					Thread.sleep(1000);
//遍历商品列表
//拿到每件商品的时间差,转化为具体的多少天多少小时多少分多少秒
//并保存在商品time这个属性内
					long counttime = mRecommendActivitiesList.getSelfTime();
					long days = counttime / (1000 * 60 * 60 * 24);
					long hours = (counttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
					long hours_ = hours + days * 24;
					long minutes = (counttime - days * (1000 * 60 * 60 * 24)
							- hours * (1000 * 60 * 60)) / (1000 * 60);
					long second = (counttime - days * (1000 * 60 * 60 * 24)
							- hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
//并保存在商品time这个属性内
					String finaltime = days + "天" + hours + "时" + minutes + "分" + second + "秒";
					mRecommendActivitiesList.setFinalTime(finaltime);
					mRecommendActivitiesList.setHours((int) hours_);
					mRecommendActivitiesList.setMin((int) minutes);
					mRecommendActivitiesList.setSec((int) second);
//如果时间差大于1秒钟,将每件商品的时间差减去一秒钟,
// 并保存在每件商品的counttime属性内
					if (counttime > 1000) {
						mRecommendActivitiesList.setSelfTime(counttime - 1000);
					}
					Message message = new Message();
					message.what = 1;
//发送信息给handler
					handler.sendMessage(message);
				} catch (Exception e) {
				}
			}
		}
	}

	public void dealWithTimer() {
//		if (TextUtils.isEmpty(data.getProductTimeend())) {
//			return;
//		}
//		long counttime = getTime(newDate, data.getProductTimeend());
//		long days = counttime / (1000 * 60 * 60 * 24);
//		long hours = (counttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
//		long hours_ = hours + days * 24;
//		long minutes = (counttime - days * (1000 * 60 * 60 * 24)
//				- hours * (1000 * 60 * 60)) / (1000 * 60);
//		long second = (counttime - days * (1000 * 60 * 60 * 24)
//				- hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
//		//并保存在商品time这个属性内
//		String finaltime = days + "天" + hours + "时" + minutes + "分" + second + "秒";
////		Log.i(TAG, "dealWithTimer: " + finaltime);
//		data.setHours((int) hours_);
//		data.setMin((int) minutes);
//		data.setSec((int) second);
//		data.setFinalTime(finaltime);
//		data.setSelfTime(counttime);
//		myThread = new MyThread(data);
//		myThread.start();
	}

	public long getTime(String serverTime, String startTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long diff = 0;
		try {
			Date now = format.parse(serverTime);
			Date end = format.parse(startTime);
			diff = end.getTime() - now.getTime();
		} catch (Exception e) {

		}
		return diff;
	}


	@Override
	protected void onDestroy() {
		dismissDialog2();
		dismissDialog3();

		unregisterReceiver(receiver);
		if (myThread != null) {
			myThread.endThread = true;
			myThread = null;
		}
		OkHttpUtils.getInstance().cancelTag(this);
		super.onDestroy();
	}
}
