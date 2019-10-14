package com.yj.cosmetics.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.rollviewpager.OnItemClickListener;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.GoodsCommentEntity;
import com.yj.cosmetics.model.GoodsEntitys;
import com.yj.cosmetics.ui.activity.BigImageActivity;
import com.yj.cosmetics.ui.activity.JudgeActivity;
import com.yj.cosmetics.ui.activity.goodDetail.GoodsDetailActivity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.WindowDisplayManager;
import com.yj.cosmetics.widget.CustomBanner.CustomRollPagerView;
import com.yj.cosmetics.widget.CustomCountDownTimerViewWhite;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class GoodsDetailContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private GoodsDetailActivity mContext;
	SpendDetialClickListener mItemClickListener;
	public GoodsBannerAdapter mBannerAdapter;
	GoodsEntitys.DataBeanX data;
	GoodsCommentEntity.GoodsCommentData mJudge;
	private String style;
	private ShowDetialInterface showDetialInterface;
	private showDialogTicket showDialogTicket;
	private int halfScreenHeight;
	private int size;
	private static WebView mWebView;

	public GoodsDetailContentAdapter(GoodsDetailActivity mContext, GoodsEntitys.DataBeanX data, GoodsCommentEntity.GoodsCommentData mJudge) {
		this.mContext = mContext;
		this.data = data;
		this.mJudge = mJudge;
		this.halfScreenHeight = mContext.getWindowManager().getDefaultDisplay().getHeight() / 2;
	}

	public void setOnItemClickListener(SpendDetialClickListener listener) {
		this.mItemClickListener = listener;
	}

	public void setShowDetialInterface(ShowDetialInterface showDetialInterface) {
		this.showDetialInterface = showDetialInterface;
	}

	public void setShowDialogInterface(showDialogTicket showDialogInterface) {
		this.showDialogTicket = showDialogInterface;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		BannerViewHolder bannerViewHolder;
		JudgeViewHolder judgeViewHolder;
//		WebViewHolder webViewHolder;
		SuYuanViewHolder suYuanViewHolder;
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		if (viewType == 0) {
			bannerViewHolder = new BannerViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_goods_detial_banner, parent, false));
			return bannerViewHolder;
		} else if (viewType == 1) {
			judgeViewHolder = new JudgeViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_goods_detial_judge, parent, false));
			return judgeViewHolder;
		} else {
			LogUtils.i("我在返回ViewType");
			WebView mWebView = new WebView(mContext);
			mWebView.setLayoutParams(lp);
			mWebView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
			return new WebViewHolder(mWebView);
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		} else if (position == 1) {
			return 1;
		} else {
			return 2;
		}
	}

	@SuppressLint("LongLogTag")
	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof BannerViewHolder) {
			((BannerViewHolder) holder).mBanner.setLayoutParams(WindowDisplayManager.getEqualRelParams(mContext, ((BannerViewHolder) holder).mBanner));
			if (data != null) {
				((BannerViewHolder) holder).mBanner.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(int position) {
					}
				});
				((BannerViewHolder) holder).mBanner.setHintView(null);
				((BannerViewHolder) holder).mBanner.setAnimationDurtion(3000);
//				LogUtils.i("轮播图长度======" + data.getJsodetimgs().size());
				mBannerAdapter = new GoodsBannerAdapter(((BannerViewHolder) holder).mBanner, data.getData().getJsodetimgs(), mContext);
				((BannerViewHolder) holder).mBanner.setAdapter(mBannerAdapter);
				((BannerViewHolder) holder).mBanner.setScrollChangeListener(mContext);


				if (data.getData().getJsodetimgs() != null && data.getData().getJsodetimgs().size() > 1) {
					((BannerViewHolder) holder).tvAll.setText("/" + data.getData().getJsodetimgs().size());
					((BannerViewHolder) holder).llTag.setVisibility(View.VISIBLE);
				} else {
					((BannerViewHolder) holder).llTag.setVisibility(View.GONE);
				}
//				((CollectionViewHolder) holder).tvOp.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//				((BannerViewHolder) holder).tvTicketContent.setText(data.getData().getProDetailCoupon().getCouponName());
//				((BannerViewHolder) holder).tvIntegralContent.setText("购买可得" + data.getConsumerBackscore() + "积分");
//				int CouponsSize = data.getData().getProDetailCoupon().getCoupons().size();
//				if (CouponsSize == 0) {
//					((BannerViewHolder) holder).rlTicket.setVisibility(View.GONE);
//				} else {
//					((BannerViewHolder) holder).rlTicket.setVisibility(View.VISIBLE);
//				}
				((BannerViewHolder) holder).rlTicket.setOnClickListener(new View.OnClickListener() {//优惠券
					@Override
					public void onClick(View view) {
//						showDialogTicket.showDialogs(1, data.getProDetailCoupon());
						showDialogTicket.showDialogs(1, data);
					}
				});
				((BannerViewHolder) holder).rlIntegral.setOnClickListener(new View.OnClickListener() {//积分
					@Override
					public void onClick(View view) {
//						showDialogTicket.showDialogs(2, data.getProDetailCoupon());
						showDialogTicket.showDialogs(2, data);
					}
				});
				((BannerViewHolder) holder).rlShow.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						showDetialInterface.showDetial(position);
					}
				});
				if (!TextUtils.isEmpty(style)) {
					((BannerViewHolder) holder).tvStyle.setText(style);
				}
//				if (data.getProductTimelimit().equals("2")) {
//					//秒杀
//					((BannerViewHolder) holder).rlSeckill.setVisibility(View.VISIBLE);
//					((BannerViewHolder) holder).llNormal.setVisibility(View.GONE);
//					((BannerViewHolder) holder).tvPrice.setText(data.getProductCurrent());
//					((BannerViewHolder) holder).tvOp.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				((BannerViewHolder) holder).tvOp.setText("￥" + data.getData().getProductOrginal());
				((BannerViewHolder) holder).tvIntegral.setText("销量:" + data.getData().getProductSales());
//					((BannerViewHolder) holder).tvSoldNum.setVisibility(View.GONE);
//					((BannerViewHolder) holder).tvSoldNum.setText("已售" + data.getDetailNum() + "件");
//					if (data.getIfStartTimeLimit().equals("yes")) {
//						//尚未开始
//						((BannerViewHolder) holder).llNo.setVisibility(View.VISIBLE);
//						((BannerViewHolder) holder).llStart.setVisibility(View.GONE);
//						((BannerViewHolder) holder).tvDate.setText(data.getStartTimeLimitMd());
//						((BannerViewHolder) holder).tvTime.setText(data.getStartTimeLimitHm() + "开抢");
//					} else {
//						//已经开始
//						((BannerViewHolder) holder).llStart.setVisibility(View.VISIBLE);
//						((BannerViewHolder) holder).llNo.setVisibility(View.GONE);
//						((BannerViewHolder) holder).tvPrecent.setText("已抢" + data.getSold() + "%");
//						LogUtils.i("转换后的值========" + Math.round(Float.parseFloat(data.getSold())));
//						((BannerViewHolder) holder).pb.setSecondaryProgress(Math.round(Float.parseFloat(data.getSold())));
//						LogUtils.i("小时=====" + data.getHours() + "======分钟===" + data.getMin() + "=====秒====" + data.getSec());
//						if (data.getHours() < 0 || data.getMin() < 0 || data.getSec() < 0) {
//						} else {
//							((BannerViewHolder) holder).timer.setTime(data.getHours(), data.getMin(), data.getSec());
//							((BannerViewHolder) holder).timer.start();
//						}
//					}
//				} else {
//					((BannerViewHolder) holder).rlSeckill.setVisibility(View.GONE);
//					((BannerViewHolder) holder).llNormal.setVisibility(View.VISIBLE);
				((BannerViewHolder) holder).tvOpN.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				((BannerViewHolder) holder).tvOpN.setText("￥" + data.getData().getProductOrginal());
				((BannerViewHolder) holder).tvPrice1.setText(data.getData().getProductCurrent());
//					((BannerViewHolder) holder).tvIntegral.setText("积分:" + data.getConsumerBackscore());
//				}
//				if (data.getProductHot().equals("2")) {
//					((BannerViewHolder) holder).tvHot.setVisibility(View.VISIBLE);
//					((BannerViewHolder) holder).tvTitle.setText("         " + data.getProductName());
//				} else {
//					((BannerViewHolder) holder).tvHot.setVisibility(View.GONE);
				((BannerViewHolder) holder).tvTitle.setText(data.getData().getProductName());
//				}
//				if (TextUtils.isEmpty(data.getSystemValue())) {
//					((BannerViewHolder) holder).tvFee.setVisibility(View.GONE);
//					((BannerViewHolder) holder).tvSystemValue.setVisibility(View.GONE);
//				} else {
//					if (data.getSystemValue().equals("0") || data.getSystemValue().equals("免运费") || data.getSystemValue().equals("包邮")) {
////                        ((BannerViewHolder) holder).tvFee.setText("运费： 免运费");
//						((BannerViewHolder) holder).tvFee.setText("免运费");
//						((BannerViewHolder) holder).tvSystemValue.setVisibility(View.GONE);
//					} else {
//						((BannerViewHolder) holder).tvSystemValue.setText("满" + data.getSystemValue() + "元免运费(2kg以内)");
////                        ((BannerViewHolder) holder).tvFee.setText("运费：" + "满" + data.getSystemValue() + "免运费");
//						((BannerViewHolder) holder).tvFee.setText("满" + data.getSystemValue() + "免运费");
//					}
//				}
//				((BannerViewHolder) holder).tvReturn.setText("返现:" + data.getConsumerBackmoney());
//				((BannerViewHolder) holder).tvMoonNum.setText("月销:" + data.getData().getProductMaxpurchase());
			}
			mContext.BannerHeight = ((BannerViewHolder) holder).mBanner.getHeight();
			mContext.goodsHeight = ((BannerViewHolder) holder).llTop.getHeight();
			LogUtils.i("goodsHeight的值======" + mContext.goodsHeight
					+ " BannerHeight的值: " + mContext.BannerHeight
					+ " halfScreenHeight: " + halfScreenHeight
					+ " ((BannerViewHolder) holder).llTop.getHeight(): " + ((BannerViewHolder) holder).llTop.getHeight()
			);
		} else if (holder instanceof JudgeViewHolder) {
			if (mJudge != null) {
				size = mJudge.getCommArray().size();
				if (mJudge.getCommArray() != null && size > 0) {
//                    ((CouponReceiveHolder) holder).llYes.setVisibility(View.VISIBLE);
//                    ((CouponReceiveHolder) holder).rlNo.setVisibility(View.GONE);
					LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
					((JudgeViewHolder) holder).mRecyclerView.setLayoutManager(layoutManager);
					GoodsDetialJudgeAdapter judgeAdapter = new GoodsDetialJudgeAdapter(mContext, mJudge.getCommArray());
					((JudgeViewHolder) holder).mRecyclerView.setAdapter(judgeAdapter);

					judgeAdapter.setOnItemClickListener(new GoodsDetialJudgeAdapter.SpendDetialClickListener() {
						@Override
						public void onItemClick(View view, int postion, int i) {
							Intent intent = new Intent(mContext, BigImageActivity.class);
							intent.putExtra("postion", postion + "");
							intent.putExtra("postions", i + "");
							intent.putExtra("bigImg_list", (Serializable) mJudge.getCommArray().get(postion).getCommentImg());
							mContext.startActivity(intent);
						}
					});
					((JudgeViewHolder) holder).tvNumber.setText("评论（" + mJudge.getCommentCount() + "）");
//                    int height = ((CouponReceiveHolder) holder).mRecyclerView.getHeight();

					((JudgeViewHolder) holder).tvMore.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (data != null) {
								Intent intent = new Intent(mContext, JudgeActivity.class);
								intent.putExtra("title", "商品评价");
								intent.putExtra("proId", data.getData().getProductId());
//								intent.putExtra("sproductId",  data.getData().get());
								mContext.startActivity(intent);
							} else {
								return;
							}

						}
					});
				} else {
					((JudgeViewHolder) holder).tvMore.setVisibility(View.GONE);
					((JudgeViewHolder) holder).rlNo.setVisibility(View.VISIBLE);
					((JudgeViewHolder) holder).llYes.setVisibility(View.GONE);
				}
			}
			webViewInit(((JudgeViewHolder) holder).WebViews);
			if (data != null && !TextUtils.isEmpty(data.getData().getProductUrl())) {
//				String productUrl = data.getData().getProductUrl();
				((JudgeViewHolder) holder).WebViews.loadUrl(URLBuilder.URLBaseHeader + data.getData().getProductUrl());
			}

			mContext.judgeHeight = ((JudgeViewHolder) holder).llYes.getHeight() * size * size;
		} else if (holder instanceof WebViewHolder) {

//			webViewInit(((WebViewHolder) holder).mWebView);

			mWebView = (WebView) holder.itemView;
			//"http://www.51xianchang.com//project/189/detail.act"
			if (data != null) {
//                LogUtils.i("jsp的值=====" + data.getDetailJsp());
			}
/*			if (data != null && !TextUtils.isEmpty(data.getData().getProductUrl())) {
				mWebView.loadUrl(URLBuilder.URLBaseHeader + data.getData().getProductUrl());
//				((WebViewHolder) holder).mWebView.loadUrl("http://www.wendiapp.com/phone/homePage/detailJsp.act?productId=852");
			}*/

		} else if (holder instanceof SuYuanViewHolder) {

		}
	}

	public WebView getWebView() {
		return mWebView;
	}


	@Override
	public int getItemCount() {
		return 3;
	}

	public void setData(GoodsEntitys.DataBeanX data) {
		this.data = data;
		notifyDataSetChanged();
	}

	public void setJudge(GoodsCommentEntity.GoodsCommentData mJudge) {
		this.mJudge = mJudge;
		notifyDataSetChanged();
	}

	public void setStyle(String style) {
		this.style = style;
		notifyDataSetChanged();
	}


	class BannerViewHolder extends RecyclerView.ViewHolder implements CustomCountDownTimerViewWhite.CountTimeInterface {

		@BindView(R.id.goods_detial_banner)
		CustomRollPagerView mBanner;

		@BindView(R.id.goods_detial_banner_tvnow)
		TextView tvNow;
		@BindView(R.id.goods_detial_banner_tvall)
		TextView tvAll;
		@BindView(R.id.goods_detial_banner_ll)
		LinearLayout llTag;
		@BindView(R.id.goods_detial_show)
		RelativeLayout rlShow;
		@BindView(R.id.goods_item_style)
		TextView tvStyle;
		@BindView(R.id.goods_detial_banner_tv_hot)
		TextView tvHot;
		@BindView(R.id.goods_banner_tvTitle)
		TextView tvTitle;
		@BindView(R.id.goods_detial_banner_fee)
		TextView tvFee;
		@BindView(R.id.goods_detial_banner_sold_moon)
		TextView tvMoonNum;
		@BindView(R.id.goods_detial_banner_return)
		TextView tvReturn;

		@BindView(R.id.goods_detial_seckill_rl)
		RelativeLayout rlSeckill;
		@BindView(R.id.goods_detial_banner_sec_price)
		TextView tvPrice;
		@BindView(R.id.goods_detial_banner_tv_op)
		TextView tvOp;
		@BindView(R.id.goods_detial_banner_tv_number)
		TextView tvSoldNum;
		@BindView(R.id.goods_detial_banner_tv_precent)
		TextView tvPrecent;
		@BindView(R.id.goods_detial_banner_pb)
		ProgressBar pb;
		@BindView(R.id.item_goods_detial_timer)
		CustomCountDownTimerViewWhite timer;
		@BindView(R.id.item_goods_detial_tv_end)
		TextView tvEnd;

		@BindView(R.id.goods_detial_ticket)
		RelativeLayout rlTicket;//优惠券
		@BindView(R.id.goods_item_ticket_textTitle)
		TextView tvTicketTitle;//优惠券标题
		@BindView(R.id.goods_item_ticket_content)
		TextView tvTicketContent;//内容

		@BindView(R.id.goods_detial_integral)
		RelativeLayout rlIntegral;//积分
		@BindView(R.id.goods_item_integral_textTitle)
		TextView tvIntegralTitle;//积分标题
		@BindView(R.id.goods_item_integral_content)
		TextView tvIntegralContent;//内容


		@BindView(R.id.goods_banner_ll1)
		LinearLayout llNormal;
		@BindView(R.id.goods_detial_banner_tv_price1_normal)
		TextView tvPrice1;
		@BindView(R.id.goods_detial_banner_tv_price2_normal)
		TextView tvPrice2;
		@BindView(R.id.goods_detial_banner_tv_op_normal)
		TextView tvOpN;
		@BindView(R.id.goods_detial_tv_systemvalue)
		TextView tvSystemValue;

		@BindView(R.id.goods_detial_banner_ll_yes)
		LinearLayout llNo;
		@BindView(R.id.goods_detial_banner_ll_no)
		LinearLayout llStart;
		@BindView(R.id.goods_detial_banner_tv_date)
		TextView tvDate;
		@BindView(R.id.goods_detial_banner_tv_time)
		TextView tvTime;

		@BindView(R.id.goods_detial_tv_integral)
		TextView tvIntegral;

		@BindView(R.id.goods_detial_ll_top)
		LinearLayout llTop;


		public BannerViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			timer.setCountTimeInterface(this);
		}

		@Override
		public void countTimeDown() {
			LogUtils.i("我进入countTimeDown了");
			timer.setVisibility(View.GONE);
			tvEnd.setText("已结束");
			mContext.getBtnAdd().setVisibility(View.GONE);
			mContext.getBtnBuy().setBackgroundResource(R.drawable.shape_corner_six);
			mContext.getBtnBuy().setEnabled(false);
			mContext.getBtnBuy().setText("活动结束");
		}
	}

	class JudgeViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.recyclerView)
		RecyclerView mRecyclerView;
		@BindView(R.id.goods_detial_judge_tvmore)
		TextView tvMore;
		@BindView(R.id.goods_detial_judge_num)
		TextView tvNumber;
		@BindView(R.id.goods_detial_judge_rl_no)
		RelativeLayout rlNo;
		@BindView(R.id.goods_detial_judge_ll_yes)
		LinearLayout llYes;

		@BindView(R.id.webViews)
		com.yj.cosmetics.widget.mWebView WebViews;

		public JudgeViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	class WebViewHolder extends RecyclerView.ViewHolder {

//		@BindView(R.id.webView)
//		WebView mWebView;

		public WebViewHolder(View itemView) {
			super(itemView);
			if (itemView instanceof WebView) {
				webViewInit(itemView);
			}
			ButterKnife.bind(this, itemView);
		}

	}

	class SuYuanViewHolder extends RecyclerView.ViewHolder {

		public SuYuanViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	public interface SpendDetialClickListener {
		void onItemClick(View view, int postion);
	}

	public interface ShowDetialInterface {
		void showDetial(int position);
	}

	public interface showDialogTicket {
		void showDialogs(int i, GoodsEntitys.DataBeanX proDetailCoupon);
	}


	private void webViewInit(View view) {
		WebView mWebView = (WebView) view;
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(false);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//先清除webView的缓存，在加载数据，防止后台更改数据了，没有及时刷新数据
		mWebView.clearCache(true);

		mWebView.getSettings().setJavaScriptEnabled(true);
//		mWebView.getSettings().setBlockNetworkImage(false); // 解决图片不显示
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.setWebChromeClient(new MyWebChromeClient());
//		mWebView.getSettings().setDomStorageEnabled(true);
//        mWebView.requestFocus();
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//			mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//		}
		mWebView.setFocusable(false);
	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			LogUtils.i("overrideUrl " + url);
//			return super.shouldOverrideUrlLoading(view, url);

			// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
			view.loadUrl(url);
			return true;

		}

		@Override
		public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
			super.onReceivedError(view, request, error);
//			view.loadUrl("file:///android_asset/error.html");
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			LogUtils.i("pageStarted " + url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

//		@Override
//		public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//			return new WebResourceResponse("text/html", "UTF-8", new InputStream() {
//				@Override
//				public int read() throws IOException {
//					return -1;
//				}
//			});
//			if (ADFilterTool.hasNotAd(url)) {
//				return super.shouldInterceptRequest(view, url);
//			} else {
//				return new WebResourceResponse(null, null, null);
//			}
//		}
//		@Override
//		public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//			return new WebResourceResponse("text/html", "UTF-8", new InputStream() {
//				@Override
//				public int read() throws IOException {
//					return -1;
//				}
//			});
//		}

		@Override
		public void onPageFinished(WebView view, String url) {

//            LogUtils.i("我加载结束了========"+url+"==========view的高度"+view.getHeight());
//            mContext.detialHeight = view.getContentHeight();
//            LogUtils.i("height的值===="+view.getHeight());
//            LogUtils.i("detialHeight的值======"+mContext.detialHeight);
			super.onPageFinished(view, url);
		}
	}

	private class MyWebChromeClient extends WebChromeClient {
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
		}
	}

	@SuppressLint("LongLogTag")
	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		super.onDetachedFromRecyclerView(recyclerView);
	}
}