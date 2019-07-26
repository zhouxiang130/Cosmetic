package com.yj.cosmetics.ui.fragment.HomeFrag;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseFragment;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.CouponListEntity;
import com.yj.cosmetics.model.HomeEntity;
import com.yj.cosmetics.model.HomeListEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.model.StoreListAddressEntity;
import com.yj.cosmetics.ui.activity.SearchActivity;
import com.yj.cosmetics.ui.adapter.HomeAdapter;
import com.yj.cosmetics.ui.adapter.MainCouponTicketAdapter;
import com.yj.cosmetics.util.FileUtils;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.SpacesItemDecoration;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomDialog;
import com.yj.cosmetics.widget.ProgressLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Suo on 2017/11/18.
 *
 * @TODO 首页展示页面
 */

public class HomeFrag extends BaseFragment implements HomeFrag_Contract.View {
	private static final String TAG = "HomeFrag";
	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	@BindView(R.id.frag_classify_rl_top)
	RelativeLayout rlTop;
	@BindView(R.id.frag_home_info_iv)
	ImageView ivInfo;
	@BindView(R.id.home_vline)
	View vLine;
	@BindView(R.id.frag_home_tv_info)
	TextView tvInfo;
	@BindView(R.id.frag_home_locinfo)
	LinearLayout homeLocinfo;

	//	@BindView(R.id.frag_homes_tv_info)
//	MarqueeTextView tvLoc;
	@BindView(R.id.frag_home_v_head)
	View vHead;
	//	@BindView(R.id.up_iv_icon)
//	ImageView ivIcon;
	@BindView(R.id.frag_home_fab)
	FloatingActionButton fab;


	private HomeAdapter mAdapter;
	MainCouponTicketAdapter mAdapters;
	LinearLayoutManager layoutManager;

	private HomeEntity.HomeData data;
	private List<HomeListEntity.HomeListData.HomeListItem> mList;
	private HomeFrag_Presenter presenter = new HomeFrag_Presenter(this);

	private int mHeight;
	private int tempY = 0;
	private float duration = 150.0f;//在0-150之间去改变头部的透明度

	private int pageNum = 1;
	private MyThread myThread;

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
	private GridLayoutManager gridLayoutManager;
	private CustomDialog builder;
	private MyReceiver myReceiver;
	private MyReceivers myReceivers;

	private String shopId;
	private DynamicReceiver dynamicReceiver;
	private String homejson1;
	private String homejson2;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = createView(inflater.inflate(R.layout.fragment_home_, container, false));
		return view;
	}


	@Override
	protected void initData() {
		mList = new ArrayList<>();
		myReceiver = new MyReceiver();
//		cacheData = new CacheData();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("cn.yj.robust.getCoupon_");
		getActivity().registerReceiver(myReceiver, intentFilter);


		myReceivers = new MyReceivers();
		IntentFilter intentFilters = new IntentFilter();
		intentFilters.addAction("cn.yj.robust.getAddInfos");
		getActivity().registerReceiver(myReceivers, intentFilters);


		dynamicReceiver = new DynamicReceiver();
		IntentFilter intentFilter1 = new IntentFilter();
		intentFilter1.addAction("CN.YJ.ROBUST.REFRESHDATA");
		getActivity().registerReceiver(dynamicReceiver, intentFilter1);


		layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
   /*
     *  创建一个移动动画效果
     *  入参的含义如下：
     *  fromXType：移动前的x轴坐标的类型
     *  fromXValue：移动前的x轴的坐标
     *  toXType：移动后的x轴的坐标的类型
     *  toXValue：移动后的x轴的坐标
     *  fromYType：移动前的y轴的坐标的类型
     *  fromYValue：移动前的y轴的坐标
     *  toYType：移动后的y轴的坐标的类型
     *  toYValue：移动后的y轴的坐标
     */

//		TranslateAnimation translateAnimation = new TranslateAnimation(
//				Animation.RELATIVE_TO_SELF,
//				0,
//				Animation.RELATIVE_TO_SELF,
//				0.5f,
//				Animation.RELATIVE_TO_SELF,
//				0,
//				Animation.RELATIVE_TO_SELF,
//				0.5f);
//
//		translateAnimation.setDuration(3000);
//		ivIcon.setAnimation(translateAnimation);
		//-------------------------------------
//		gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
		gridLayoutManager = new GridLayoutManager(getActivity(), 2);
		mRecyclerView.setLayoutManager(gridLayoutManager);
//		RecyclerSpace decor = new RecyclerSpace(10, getResources().getColor(R.color.Cf7_f7_f7), 0);
//		decor.setItemOffets(11);
//		mRecyclerView.addItemDecoration(decor);
		initRvContent();
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
		mAdapter = new HomeAdapter(getActivity(), data, mList, mUtils, layoutManager);
		if (!TextUtils.isEmpty(mUtils.getUid())) {
			refresh();
		} else {
			refresh();
//			setLocInfo();
		}

		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(new HomeAdapter.ShopListClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
				if (data == null) {
					return;
				}
				if (data.getTimeLimitList() != null && data.getTimeLimitList().size() > 0) {
					if (postion < 7) {
						return;
					}
					IntentUtils.IntentToGoodsDetial(getActivity(), mList.get(postion - 8).getProduct_id());
				} else {
					if (postion < 6) {
						return;
					}
					IntentUtils.IntentToGoodsDetial(getActivity(), mList.get(postion - 8).getProduct_id());
				}
			}
		});
//		refresh();
		//获取标题栏高度
//		ViewTreeObserver viewTreeObserver = rlTop.getViewTreeObserver();
//		viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//			@Override
//			public void onGlobalLayout() {
//				rlTop.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//				mHeight = Math.round(getResources().getDimension(R.dimen.dis208) - rlTop.getHeight());//这里取的高度应该为图片的高度-标题栏
//			}
//		});
//		mRecyclerView.scrollToPosition(0);

		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				Log.i(TAG, "onScrolled:>>>>> " + dx + "--------------------------" + dy);

	/*            //在这里做颜色渐变的操作
	            tempY += dy;
*//*
		        // 滚动的总距离相对0-150之间有一个百分比，头部的透明度也是从初始值变动到不透明，通过距离的百分比，得到透明度对应的值
                // 如果小于0那么透明度为初始值，如果大于150为不透明状态*//*

                LogUtils.i("tempY的值"+tempY);
                if (tempY <= 0) {
                    //顶部图处于最顶部，标题栏透明
                    setTopColor(Color.argb(0,255,255,255));
                    rlTop.setBackgroundColor(Color.argb(0, 255, 255, 255));
                    ivInfo.setImageResource(R.mipmap.new_home);
                    vLine.setVisibility(View.GONE);
                } else if (tempY > 0 && tempY < mHeight) {
                    //滑动过程中，渐变
                    float scale = (float) tempY / mHeight;//算出滑动距离比例
                    float alpha = (255 * scale);//得到透明度
                    setTopColor(Color.argb((int)alpha,0,0,0));
                    rlTop.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    vLine.setVisibility(View.GONE);
                } else {
                    //过顶部图区域，标题栏定色
                    rlTop.setBackgroundColor(Color.argb(255, 255, 255, 255));
                    ivInfo.setImageResource(R.mipmap.notification);
                    setTopColor(Color.argb(255,0,0,0));
                    vLine.setVisibility(View.VISIBLE);
                }*/
//				if (gridLayoutManager.findFirstVisibleItemPosition() > 1) {
//					//过顶部图区域，标题栏定色
//					rlTop.setBackgroundColor(Color.argb(255, 255, 255, 255));
//					ivInfo.setImageResource(R.mipmap.notification);
//					setTopColor(Color.argb(255, 0, 0, 0));
//					vLine.setVisibility(View.VISIBLE);
//				} else {
//					setTopColor(Color.argb(0, 255, 255, 255));
//					rlTop.setBackgroundColor(Color.argb(0, 255, 255, 255));
//					ivInfo.setImageResource(R.mipmap.new_home);
//					vLine.setVisibility(View.GONE);
//				}
			}
		});
		transTitle();
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mRecyclerView.smoothScrollToPosition(0);
			}
		});
	}


//	private void setTvLoc() {
//		tvLoc.setText("定位失败");
//	}


	private void initRvContent() {
		//添加ItemDecoration，item之间的间隔
		int leftRight = dip2px(10);
		int topBottom = dip2px(10);

		mRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom,0,8));
		//rv_content.addItemDecoration(new SpacesItemDecoration(dip2px(1), dip2px(1), Color.BLUE));
	}

	public int dip2px(float dpValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
	}


	private void setRuleDialog(int couponNumber, List<CouponListEntity.DataBean.UserPossessionBean.CouponsBean> coupons) {

		if (couponNumber != 0) {
			View pview = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_main_coupon_ticket, null);
			LinearLayout llCancel = (LinearLayout) pview.findViewById(R.id.dialog_coupon_ticket_cancel);
			RelativeLayout rlTicket = (RelativeLayout) pview.findViewById(R.id.dialog_coupon_ticket);
			TextView tvTicketNum = (TextView) pview.findViewById(R.id.dialog_coupon_ticket_num);
			TextView tvTicketOk = (TextView) pview.findViewById(R.id.dialog_coupon_ticket_finish);
			RelativeLayout rlTicketList = (RelativeLayout) pview.findViewById(R.id.dialog_coupon_ticket_list);
			RecyclerView mRecyclerView = (RecyclerView) pview.findViewById(R.id.xrecyclerView);
			LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
			layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
			mRecyclerView.setLayoutManager(layoutManager);

			if (couponNumber > 3) {
				rlTicket.setVisibility(View.VISIBLE);
				rlTicketList.setVisibility(View.GONE);
				tvTicketNum.setText(couponNumber + "张");
			} else {
				rlTicketList.setVisibility(View.VISIBLE);
				rlTicket.setVisibility(View.GONE);
				mAdapters = new MainCouponTicketAdapter(getActivity(), coupons);
				mRecyclerView.setAdapter(mAdapters);
			}
			builder = new CustomDialog(getActivity(), R.style.my_dialog).create(pview, false, 1f, 1f, 1.7f);
			builder.show();
			llCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					builder.dismiss();
				}
			});

			tvTicketOk.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					builder.dismiss();
				}
			});
//			Log.i(TAG, "setRuleDialog: " + preferencesUtil.getValuesBoolean("isGetCoupon"));
			/**
			 * 	领取全部优惠券
			 */
			doAsyncGetCoupon();
		}
	}


	/**
	 * 关闭Dialog
	 */
	class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO: This method is called when the BroadcastReceiver is receiving
			// an Intent broadcast.
			builder.dismiss();
		}
	}

	/**
	 * 通过继承 BroadcastReceiver建立动态广播接收器
	 */
	class DynamicReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			//通过土司验证接收到广播
			mRecyclerView.smoothScrollToPosition(0);
			if (!TextUtils.isEmpty(mUtils.getUid())) {
				refresh();
			} else {
				refresh();
//				setLocInfo();
			}

//			refresh();
			Intent intents = new Intent();
			intents.setAction("cn.yj.robust.getAddInfo");
			intents.putExtra("refreshData", "yes");
			getActivity().sendBroadcast(intents);
		}
	}


	private void refresh() {
		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				pageNum = 1;
				tempY = 0;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						// 1. 先从本地获取数据
						homejson1 = FileUtils.loadFromLocal(1);
						homejson2 = FileUtils.loadFromLocal(2);
						//  从本地获取数据为空   重新请求服务器
						if (homejson1 == null) {
//							json = loadFromServer(index);
							doAsyncGetData();
							// 如果服务器返回数据不为空   缓存数据
//							if (json != null) {
//								//缓存数据
//								save2Local(index, json);
//							}
						} else {
//							data = new Gson().fromJson(homejson1, HomeEntity.class).getData();
//							mList.clear();
//							if (homejson2 != null) {
//								mList.addAll(new Gson().fromJson(homejson2, HomeListEntity.class).getData().getProducts());
//							}
//							tvLoc.setText(data.getCity());
//							mAdapter.setData(data);
//							mProgressLayout.showContent();
							doAsyncGetData();
//							mProgressLayout.showContent();
//							mRecyclerView.refreshComplete();
							Log.d("%s", "复用缓存数据");
						}
					}
				}, 500);
			}

			@Override
			public void onLoadMore() {
				pageNum++;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mRecyclerView.setPullRefreshEnabled(false);
						loadMoreData();
					}
				}, 500);
			}
		});
		mRecyclerView.refresh();
	}

	@Override
	public void onResume() {
		if (mUtils.isLogin()) {
			doAsyncGetInfo();

//			String value = preferencesUtil.getValue("isTag", "false");
//			if (value.equals("true")) {
//				mAdapter.notifyDataSetChanged();
//			}
		} else {
			tvInfo.setVisibility(View.GONE);
		}
		super.onResume();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);

		if (!hidden) {
			if (mUtils.isLogin()) {
				doAsynCouponList();
//				if (mUtils.getAddInfo() != null)
//					tvLoc.setText(mUtils.getAddInfo());
			}
		} else {
		}
	}


	@TargetApi(21)
	private void setTopColor(int color) {
		if (Build.VERSION.SDK_INT >= 21) {
//			Log.i(TAG, "setTopColor: " + color);
			vHead.setBackgroundColor(color);
		}
	}

	@TargetApi(21)
	private void transTitle() {
		if (Build.VERSION.SDK_INT >= 21) {
			vHead.setVisibility(View.VISIBLE);
		}
	}

	@OnClick({R.id.frag_classify_rl_search, R.id.frag_my_info, /*R.id.frag_home_locinfo*/})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.frag_classify_rl_search:
				Intent intent = new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
				break;
			case R.id.frag_my_info:
				if (mUtils.isLogin()) {
					IntentUtils.IntentToInfoCenter(getActivity());
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
		/*	case R.id.frag_home_locinfo:

				if (mUtils.isLogin()) {
					intent = new Intent(getContext(), locAddressActivity.class);
					startActivityForResult(intent, 110);
				} else {
					IntentUtils.IntentToLogin(getContext());
				}
				break;*/
		}
	}

	/**
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @TODO 在首页内容里，点击定位选择用户地址选项，获取返回信息刷新店铺数据；（发送广播通知店铺）
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			Log.i(TAG, "onActivityResult: requestCode : "
					+ requestCode
					+ " resultCode : " + resultCode
					+ " addressId: " + data.getExtras().getString("addressId")
					+ " addressAreaDetail: " + data.getExtras().getString("addressAreaDetail"));
			mRecyclerView.smoothScrollToPosition(0);
//			tvLoc.setText(data.getExtras().getString("addressAreaDetail"));
			mUtils.saveAddselect(data.getExtras().getString("addressAreaDetail"));
			Intent intent = new Intent();
			intent.setAction("cn.yj.robust.getAddInfo");
			shopId = null;
			if (data.getExtras().getString("latitude") != null && data.getExtras().getString("longitude") != null) {

				String latitude = data.getExtras().getString("latitude");
				String longitude = data.getExtras().getString("longitude");
				String addressAreaDetail = data.getExtras().getString("addressAreaDetail");

				Log.i(TAG, "onActivityResult: ----latitude:" + latitude + "----longitude: " + longitude);

				intent.putExtra("latitude", latitude);
				intent.putExtra("longitude", longitude);
				intent.putExtra("addressAreaDetail", addressAreaDetail);
				doAsyncGetLoction(addressAreaDetail, latitude, longitude);


			} else {
				String addressId = data.getExtras().getString("addressId");
				Log.i(TAG, "onActivityResult: " + addressId);
				intent.putExtra("addressId", addressId);
				doAsyncGetAddressList(addressId);
			}
			getActivity().sendBroadcast(intent);
		}
	}


	/**
	 * @TODO -------------------- 店铺点击地址之后通知首页数据刷新
	 */
	class MyReceivers extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent != null) {
				mRecyclerView.smoothScrollToPosition(0);
				if (intent.getStringExtra("shopName") != null && intent.getStringExtra("shopId") != null) {
					String shopName = intent.getStringExtra("shopName");
					shopId = intent.getStringExtra("shopId");
//					String juli = intent.getStringExtra("juli");
					Log.i(TAG, "onReceive: shopName>>>>>" + shopName + " shopId: " + shopId);
					refresh();
					return;
				}


				if (intent.getStringExtra("latitude") != null && intent.getStringExtra("longitude") != null) {
					String latitude = intent.getStringExtra("latitude");
					String longitude = intent.getStringExtra("longitude");
					String addressAreaDetail = intent.getStringExtra("addressAreaDetail");
					Log.i(TAG, "onReceive: longitude>>>>>" + latitude + " longitude: " + longitude);
//					mRecyclerView.refresh();

					doAsyncGetLoction(addressAreaDetail, latitude, longitude);
				} else {
					String addressId = intent.getStringExtra("addressId");
					Log.e(TAG, "onReceive: " + addressId);
					doAsyncGetAddressList(addressId);
				}
			}
		}
	}


	private void doAsyncGetAddressList(String addressId) {
		Map<String, String> map = new HashMap<>();
		map.put("addressId", addressId);
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/yjDistance")
				.addParams("data", URLBuilder.format(map))
				.tag(getActivity()).build().execute(new Utils.MyResultCallback<StoreListAddressEntity>() {
			@Override
			public StoreListAddressEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("yjDistance -- json的值" + json);
				return new Gson().fromJson(json, StoreListAddressEntity.class);
			}

			@Override
			public void onResponse(StoreListAddressEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null) {
						mRecyclerView.refresh();
					}
				} else {
					LogUtils.i("我挂了" + response.getMsg());
				}
//
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("doAsyncGetList ---- 我故障了--" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}
		});
	}


	private void doAsyncGetData() {

		Map<String, String> map = new HashMap<>();
		if (!TextUtils.isEmpty(mUtils.getUid())) {
			map.put("usersId", mUtils.getUid() + "");
		}
		if (shopId != null) {
			map.put("shopId", shopId);
			shopId = null;
		}

		LogUtils.i(" homePage 传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/homePage")
				.addParams("data", URLBuilder.format(map))
				.tag(getActivity()).build().execute(new Utils.MyResultCallback<HomeEntity>() {

			@Override
			public HomeEntity parseNetworkResponse(Response response) throws Exception {
//				String s1 = response.toString();
//				Log.i(TAG, "parseNetworkResponse: " + s1 );
//				Headers headers = response.headers();
//				List<String> cookies = headers.values("Set-Cookie");
//				String session = cookies.get(0);
//				Log.d(TAG, "onResponse-size: " + cookies);
//				String s = session.substring(0, session.indexOf(";"));
//				Log.i(TAG, "homePage>>>>>>>>>>>>session is  :" + s);

				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetData -- json的值" + json);
				HomeEntity homeEntity = new Gson().fromJson(json, HomeEntity.class);

				Log.e(TAG, "parseNetworkResponse: getCity>>>" + homeEntity.getData().getCity() + " --- getShopName>> " + homeEntity.getData().getShopName());
				if (homeEntity.getCode() != null && homeEntity.getCode().equals(homeEntity.HTTP_OK)) {
					FileUtils.save2Local(1, json);
				}
				return homeEntity;
			}

			@Override
			public void onResponse(HomeEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
//
//					if (mUtils.getRefreshStore().equals("true")) {
//						Intent intents = new Intent();
//						intents.setAction("cn.yj.robust.getAddInfo");
//						intents.putExtra("refreshData", "yes");
//						getActivity().sendBroadcast(intents);
//						mUtils.saveRefreshStore("false");
//					}


					//返回值为200 说明请求成功
					if (response.getData() != null) {
						data = response.getData();
//						dealWithTimer();
						HomeEntity.HomeData.HomeClassify homeClassify = new HomeEntity.HomeData.HomeClassify();
						homeClassify.setClassify_name("全部");
						if (data.getProductClassifyList() != null) {
							data.getProductClassifyList().add(homeClassify);
						}
						mAdapter.setData(data);
//						mUtils.saveAddselect(data.getCity());
//						data.getCity();//@TODO ---------------------------------(代码逻辑有问题，需求确定后再做修改)
//						Log.e(TAG, "tvLoc.getText():>>>>> " + tvLoc.getText().toString().trim());
//						if (tvLoc.getText().toString().trim() == null) {
//							if (tvLoc.getText().toString().trim().equals(""))
//						if (data.getCity() != null)
//							tvLoc.setText(data.getCity());
//						}
						doAsyncGetList();
						mProgressLayout.showContent();
					} else {
						mProgressLayout.showNone(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
							}
						});
					}
				} else {
					mProgressLayout.showContent();
					mRecyclerView.refreshComplete();
					tempY = 0;

					LogUtils.i("我挂了" + response.getMsg());
					mProgressLayout.showNetError(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (mList != null && !mList.isEmpty()) {
								mList.clear();
								mAdapter.notifyDataSetChanged();
							}
							mRecyclerView.refresh();
						}
					});

				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				Log.i(TAG, "onError: " + e);
				LogUtils.i("doAsyncGetData ----我故障了--" + e);
				String homejson1 = FileUtils.loadFromLocal(1);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
				if (homejson1 == null) {

					mProgressLayout.showNetError(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (mList != null && !mList.isEmpty()) {
								mList.clear();
								mAdapter.notifyDataSetChanged();
							}
							mRecyclerView.refresh();
						}
					});
				} else {

					data = new Gson().fromJson(homejson1, HomeEntity.class).getData();
					mList.clear();
					if (homejson2 != null) {
						mList.addAll(new Gson().fromJson(homejson2, HomeListEntity.class).getData().getProducts());
					}
//					tvLoc.setText(data.getCity());
					mAdapter.setData(data);


					mProgressLayout.showContent();
					mRecyclerView.refreshComplete();
				}
				tempY = 0;
			}
		});
	}

	private void doAsyncGetList() {
		Map<String, String> map = new HashMap<>();
		map.put("pageNum", pageNum + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/productSort")
				.addParams("data", URLBuilder.format(map))
				.tag(getActivity()).build().execute(new Utils.MyResultCallback<HomeListEntity>() {

			@Override
			public HomeListEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetList -- json的值" + json);
				HomeListEntity homeListEntity = new Gson().fromJson(json, HomeListEntity.class);
				if (homeListEntity != null && homeListEntity.getCode().equals(homeListEntity.HTTP_OK)) {
					FileUtils.save2Local(2, json);
				}
				return homeListEntity;
			}

			@Override
			public void onResponse(HomeListEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null
							&& response.getData().getProducts() != null
							&& response.getData().getProducts().size() > 0) {
						mList.clear();
						mList.addAll(response.getData().getProducts());
						mAdapter.notifyDataSetChanged();
					}
				} else {
					LogUtils.i("我挂了" + response.getMsg());
				}
				mRecyclerView.refreshComplete();
				tempY = 0;
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				mRecyclerView.refreshComplete();
				LogUtils.i("doAsyncGetList ---- 我故障了--" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
				tempY = 0;
			}
		});
	}


	private void loadMoreData() {
		Map<String, String> map = new HashMap<>();
		map.put("pageNum", pageNum + "");
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/productSort")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<HomeListEntity>() {
			@Override
			public HomeListEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("loadMoreData --- json的值" + json);
				return new Gson().fromJson(json, HomeListEntity.class);
			}

			@Override
			public void onResponse(HomeListEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					if (response.getData() != null && response.getData().getProducts() != null && response.getData().getProducts().size() != 0) {
						mList.addAll(response.getData().getProducts());
						Log.i(TAG, "mList.size(): " + mList.size());
						mAdapter.notifyDataSetChanged();
						mRecyclerView.loadMoreComplete();
					} else if (response.getData().getProducts().size() == 0) {
						mRecyclerView.setNoMore(true);
						pageNum--;
					}
				} else {
					ToastUtils.showToast(getActivity(), "网络异常 :)" + response.getMsg());
					pageNum--;
					mRecyclerView.loadMoreComplete();
				}
				mRecyclerView.setPullRefreshEnabled(true);
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);

				mRecyclerView.refreshComplete();
				mRecyclerView.loadMoreComplete();
				mRecyclerView.setPullRefreshEnabled(true);
				if (call.isCanceled()) {
					LogUtils.i("我进入到加载更多cancel了");
					call.cancel();
				} else if (pageNum != 1) {
					LogUtils.i("加载更多的Log");
					ToastUtils.showToast(getActivity(), "网络故障,请稍后再试");
					pageNum--;
				}
			}
		});
	}

	private void doAsyncGetInfo() {
		Map<String, String> map = new HashMap<>();
		if (!TextUtils.isEmpty(mUtils.getUid())) {
			map.put("userId", mUtils.getUid());
		}
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/countMsg").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				tvInfo.setVisibility(View.GONE);
				if (call.isCanceled()) {
					call.cancel();
				} else {
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
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					if (response.getData() != null && Float.parseFloat(response.getData().toString()) > 0) {
						String text = response.getData().toString();
						tvInfo.setVisibility(View.VISIBLE);
						int i = (int) Float.parseFloat(text);
						if (i > 99) {
							tvInfo.setText("99+");
						} else {
							tvInfo.setText(i + "");
						}
					} else {
						tvInfo.setVisibility(View.GONE);
					}
				} else {
					tvInfo.setVisibility(View.GONE);
				}
			}
		});
	}

	/**
	 * @TODO 获取优惠券
	 */
	private void doAsynCouponList() {
		Map<String, String> map = new HashMap<>();
		if (!TextUtils.isEmpty(mUtils.getUid())) {
			map.put("userId", mUtils.getUid());
		}
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/selectUserPossession")
				.addParams("data", URLBuilder.format(map))
				.tag(getActivity()).build().execute(new Utils.MyResultCallback<CouponListEntity>() {
			@Override
			public CouponListEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsynCouponList -- json的值" + json);
				return new Gson().fromJson(json, CouponListEntity.class);
			}

			@Override
			public void onResponse(CouponListEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null && response.getData().toString() != null) {
						setRuleDialog(response.getData().getUserPossession().getCouponNumber(),
								response.getData().getUserPossession().getCoupons());
					}
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);

			}
		});
	}

	private void doAsyncGetLoction(String address, String latitude, String longitude) {
		Map<String, String> map = new HashMap<>();
		map.put("address", address);
		map.put("location", longitude + "," + latitude);
		LogUtils.i("sessionSaveLonLatName 传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/sessionSaveLonLatName").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				refresh();
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				Log.e(TAG, "sessionSaveLonLatName JSON>>>> : " + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					Log.i(TAG, "onResponse: " + response.getMsg());
					preferencesUtil.setBooleanValue("isGetCoupon", false);
					refresh();
				} else {
				}
			}
		});
	}


	private void doAsyncGetCoupon() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("type", "all");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePageTwo/receiveCoupon").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				tvInfo.setVisibility(View.GONE);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetCoupon json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					Log.i(TAG, "onResponse: " + response.getMsg());
					preferencesUtil.setBooleanValue("isGetCoupon", false);
				} else {

				}
			}
		});
	}


	class MyThread extends Thread {
		//用来停止线程
		boolean endThread = false;
		List<HomeEntity.HomeData.HomeSeckill> mRecommendActivitiesList;

		public MyThread(List<HomeEntity.HomeData.HomeSeckill> mRecommendActivitiesList) {
			this.mRecommendActivitiesList = mRecommendActivitiesList;
		}

		@Override
		public void run() {
			while (!endThread) {
				try {
					//线程每秒钟执行一次
					Thread.sleep(1000);
					//遍历商品列表
					for (int i = 0; i < mRecommendActivitiesList.size(); i++) {
						//拿到每件商品的时间差,转化为具体的多少天多少小时多少分多少秒
						//并保存在商品time这个属性内
						long counttime = mRecommendActivitiesList.get(i).getTime();
						long days = counttime / (1000 * 60 * 60 * 24);
						long hours = (counttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
						long hours_ = hours + days * 24;
						long minutes = (counttime - days * (1000 * 60 * 60 * 24)
								- hours * (1000 * 60 * 60)) / (1000 * 60);
						long second = (counttime - days * (1000 * 60 * 60 * 24)
								- hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
						//并保存在商品time这个属性内
						String finaltime = days + "天" + hours + "时" + minutes + "分" + second + "秒";
//                        Log.i(TAG, "MyThread: "+ finaltime);
						mRecommendActivitiesList.get(i).setFinalTime(finaltime);
						mRecommendActivitiesList.get(i).setHours((int) hours_);
						mRecommendActivitiesList.get(i).setMin((int) minutes);
						mRecommendActivitiesList.get(i).setSec((int) second);

						Log.i(TAG, "run: " + counttime + "-- days --" + days);
						//如果时间差大于1秒钟,将每件商品的时间差减去一秒钟,
						// 并保存在每件商品的counttime属性内
						if (counttime > 1000) {
							mRecommendActivitiesList.get(i).setTime(counttime - 1000);
						}
					}
					Message message = new Message();
					message.what = 1;
					//发送信息给handler
					handler.sendMessage(message);
				} catch (Exception e) {
				}
			}
		}

		public void close() {
			endThread = true;
		}
	}

	public void dealWithTimer() {
		if (data.getTimeLimitList() == null || data.getTimeLimitList().size() == 0) {
			return;
		}

		for (int i = 0; i < data.getTimeLimitList().size(); i++) {
			long counttime = getTime(data.getNewDate(), data.getTimeLimitList().get(i).getProduct_timeEnd());
			long days = counttime / (1000 * 60 * 60 * 24);
			long hours = (counttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			long hours_ = hours + days * 24;
			long minutes = (counttime - days * (1000 * 60 * 60 * 24)
					- hours * (1000 * 60 * 60)) / (1000 * 60);
			long second = (counttime - days * (1000 * 60 * 60 * 24)
					- hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
			//并保存在商品time这个属性内
			String finaltime = days + "天" + hours + "时" + minutes + "分" + second + "秒";
			data.getTimeLimitList().get(i).setHours((int) hours_);
			data.getTimeLimitList().get(i).setMin((int) minutes);
			data.getTimeLimitList().get(i).setSec((int) second);
			data.getTimeLimitList().get(i).setFinalTime(finaltime);
			data.getTimeLimitList().get(i).setTime(counttime);
		}
		myThread = new MyThread(data.getTimeLimitList());
		myThread.start();
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
	public void onDestroy() {
		super.onDestroy();
//		myThread.close();
//		OkHttpUtils.getInstance().cancelTag(this);
		getActivity().unregisterReceiver(myReceiver);
		getActivity().unregisterReceiver(myReceivers);
		getActivity().unregisterReceiver(dynamicReceiver);
	}
}
