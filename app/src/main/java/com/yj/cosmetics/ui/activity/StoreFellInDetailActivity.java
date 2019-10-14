package com.yj.cosmetics.ui.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.R;
import com.yj.cosmetics.model.FellInEntity;
import com.yj.cosmetics.model.ShareEntity;
import com.yj.cosmetics.ui.adapter.FellInAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.yj.cosmetics.widget.Dialog.QuickeOrderDialog;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.onekeyshare.OnekeyShare;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import okhttp3.Call;
import okhttp3.Response;

import static java.lang.Float.parseFloat;

/**
 * Created by Administrator on 2019/6/21 0021.
 */

public class StoreFellInDetailActivity extends BaseActivity {

	@BindView(R.id.title_ll_back)
	LinearLayout llBack;
	@BindView(R.id.tv_store_name)
	TextView tvStoreName;
	@BindView(R.id.tv_store_con)
	TextView tvStoreCon;
	@BindView(R.id.frag_store_iv)
	RoundedImageView ivStore;
	@BindView(R.id.tv_mine_free_sayest)
	TextView tvMineFreeSayest;
	@BindView(R.id.tv_account_pic)
	TextView tvAccountPic;
	@BindView(R.id.tv_mine_store_num)
	TextView tvMineStoreNum;
	@BindView(R.id.tv_total_store)
	TextView tvTotalStore;
	@BindView(R.id.view_line)
	View Line;
	@BindView(R.id.xrecyclerView)
	RecyclerView xrecyclerView;
	FellInAdapter adapter = null;
	private String orderDescId, shopId, shopName;
	List<FellInEntity.DataBean.ArrBean> ListData = null;
	private LinearLayoutManager layoutManager;

	@Override
	protected int getContentView() {
		return R.layout.activity_store_fill_in_detail;
	}

	@Override
	protected void initView() {
		hideTitle();
		shopName = getIntent().getStringExtra("shopName");
		llBack.setVisibility(View.VISIBLE);
		if (!TextUtils.isEmpty(shopName) && shopName != null) {
			setTitleText(shopName);
		}
	}

	@Override
	protected void initData() {
		ListData = new ArrayList<>();
		shopId = getIntent().getStringExtra("shopId");
		orderDescId = getIntent().getStringExtra("orderDescId");
		reqAsynStoreDeail(mUtils.getUid(), orderDescId, shopId);
		layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		xrecyclerView.setLayoutManager(layoutManager);
		adapter = new FellInAdapter(this, ListData);
		xrecyclerView.setAdapter(adapter);
	}

	public void reqAsynStoreDeail(String userId, String orderDescIds, String shopId) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("orderDescId", orderDescIds);
		map.put("shopId", shopId);
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.sellerDt)
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<FellInEntity>() {

			@Override
			public FellInEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				FellInEntity homeEntity = new Gson().fromJson(json, FellInEntity.class);
				return homeEntity;
			}

			@Override
			public void onResponse(FellInEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null) {
						showContent(response.getData());
					} else {
						setNone();
					}
				} else {
				}
			}

			@SuppressLint("LongLogTag")
			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					showNetError();
				}
			}
		});
	}

	public void showContent(FellInEntity.DataBean data) {
//		this.data = data;
		setStoreInfo(data);
	}


	public void setNone() {

	}

	public void showNetError() {

	}

	private void setStoreInfo(FellInEntity.DataBean data) {
		Glide.with(StoreFellInDetailActivity.this)
				.load(URLBuilder.getUrl(data.getShopHeadImg()))
				.asBitmap()
				.centerCrop()
				.error(R.mipmap.default_avatar)
				.into(ivStore);

//		if (data.getRowNum().equals("1")) {
////			tvMineSucc.setText(data.getSuccessMoney());//免单成功金额
//			tvMineStoreNum.setText(data.getPoolFunds());//免单收益
//			tvMineFreeSayest.setText(data.getPayMoney());//消费积分
//		} else {
		tvMineFreeSayest.setText(data.getPayMoney());//消费积分
//			tvAccountPic.setText("我的消费积分");//消费积分
		tvMineStoreNum.setText(data.getRowNum());//我的排名
//			tvTotalSucc.setText("我的排名");//消费积分
//		}

		ListData.addAll(data.getArr());
		adapter.setData(ListData, data.getRowNum());
		tvStoreName.setText(data.getShopName());
		tvStoreCon.setText(data.getShopAddress());


	}


	final int Finish = 0x11;
	final int Cancel = 0x88;
	final int Failed = 0x99;

	private Handler mHandlers = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//            super.handleMessage(msg);
			switch (msg.what) {
				case Finish:
					LogUtils.i("我在handler这");
					ToastUtils.showToast(StoreFellInDetailActivity.this, "分享成功了");
//                    disMissDialog();

					break;
				case Cancel:
					ToastUtils.showToast(StoreFellInDetailActivity.this, "取消分享");
					break;
				case Failed:
					if (!msg.getData().isEmpty()) {
						String error = msg.getData().getString("error");
						LogUtils.i("error" + error);
						ToastUtils.showToast(StoreFellInDetailActivity.this, "分享失败");
					}
					break;
			}
		}
	};

	@OnClick({R.id.iv_speed_up})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_speed_up:
				doAsyncGetShare(orderDescId, shopId);
				break;
		}
	}

	CustomProgressDialog mDialog;
	private ShareEntity.ShareData shareData;

	public void doAsyncGetShare(String orderId, String shopId) {
		Map<String, String> map = new HashMap<>();
		map.put("shopId", shopId);
		map.put("userId", mUtils.getUid());
		map.put("orderId", orderId);
		LogUtils.i("传输的网络数据" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/share")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<ShareEntity>() {

			@Override
			public ShareEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.e("json的值" + json);
				return new Gson().fromJson(json, ShareEntity.class);
			}


			@Override
			public void inProgress(float progress) {
				super.inProgress(progress);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(StoreFellInDetailActivity.this);
					if (!StoreFellInDetailActivity.this.isFinishing()) {
						mDialog.show();
					}
				} else {
					if (!StoreFellInDetailActivity.this.isFinishing()) {
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
					ToastUtils.showToast(StoreFellInDetailActivity.this, "无法获取分享信息 :)" + response.getMsg());
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
					ToastUtils.showToast(StoreFellInDetailActivity.this, "获取分享信息失败，请检查网络稍后再试");
				}

			}
		});
	}

	private void disMissDialogs() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}
	QuickeOrderDialog QuickeOrderDialog;
	private void QuickOrder() {
		if (QuickeOrderDialog == null) {
			QuickeOrderDialog = new QuickeOrderDialog(StoreFellInDetailActivity.this);
		}
		if (!QuickeOrderDialog.isShowing()) {
			QuickeOrderDialog.show();
		}


		QuickeOrderDialog.getTvContent().setText(URLBuilder.URLBaseHeader + shareData.getUrl());
		QuickeOrderDialog.getTvCopyUrl().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ClipboardManager cm = (ClipboardManager) StoreFellInDetailActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
				cm.setText(QuickeOrderDialog.getTvContent().getText());
				ToastUtils.showToast(StoreFellInDetailActivity.this, "复制成功");
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


	private void showShare(String platform) {
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize();
		if (shareData != null && shareData.getUrl() != null) {
			if (shareData.getTitle() != null) {
				oks.setText(shareData.getTitle());
				oks.setUrl(URLBuilder.URLBaseHeader + shareData.getUrl());
				oks.setTitle(shareData.getTitle());
				// titleUrl是标题的网络链接，QQ和QQ空间等使用
				oks.setTitleUrl(URLBuilder.URLBaseHeader + shareData.getUrl());
				// text是分享文本，所有平台都需要这个字段
			}
			// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//			oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
			if (!TextUtils.isEmpty(shareData.getImage())) {
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
			oks.setSite(this.getString(R.string.app_name));
			// siteUrl是分享此内容的网站地址，仅在QQ空间使用
			oks.setSiteUrl("http://www.szffxz.com/weChat/homePageTwo/toIndex");
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
		oks.show(this);
	}

}
