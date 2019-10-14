package com.yj.cosmetics.ui.fragment.HomeSeckill;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.LazyLoadFragment;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.SeckillEntity;
import com.yj.cosmetics.ui.MainActivity;
import com.yj.cosmetics.ui.adapter.HomeSeckillActivityAdapters;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
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
 * Created by Suo on 2018/3/24.
 */

public class HomeSeckillFrag extends LazyLoadFragment {

	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	@BindView(R.id.home_seckill_iv_top)
	ImageView ivTop;

	HomeSeckillActivityAdapters mAdapter;
	LinearLayoutManager layoutManager;
	private List<SeckillEntity.SeckillData.SeckillList> mList;
	private String newDate;

	private int pageNum = 1;
	private int flag;

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


	public static HomeSeckillFrag instant(int flag) {
		HomeSeckillFrag fragment = new HomeSeckillFrag();
		fragment.flag = flag;
		return fragment;
	}


	@Override
	protected int setContentView() {
		return R.layout.fragment_home_seckill;
	}


	@Override
	protected void initView() {
		mList = new ArrayList<>();
		layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
		mAdapter = new HomeSeckillActivityAdapters(getActivity(), mList, flag);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(new HomeSeckillActivityAdapters.ProfitDetialClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
//				if (flag == 0) {
//					LogUtils.i("onItemClick: " +  postion);
//				} else {
					IntentUtils.IntentToGoodsDetial(getActivity(), mList.get(postion - 2).getProduct_id());
//				}
			}
		});
		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				pageNum = 1;
				new Handler().postDelayed(new Runnable() {
					public void run() {
						doRefreshData();

					}
				}, 500);            //refresh data here
			}

			@Override
			public void onLoadMore() {
				pageNum++;
				new Handler().postDelayed(new Runnable() {
					public void run() {
						mRecyclerView.setPullRefreshEnabled(false);
						doRequestData();
					}
				}, 500);
			}
		});
		mRecyclerView.refresh();
	}

	@Override
	protected void initData() {
		mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if (layoutManager.findLastVisibleItemPosition() > 5) {
					ivTop.setVisibility(View.VISIBLE);
				} else {
					ivTop.setVisibility(View.GONE);
				}
				super.onScrolled(recyclerView, dx, dy);
			}
		});
	}

	@Override
	protected void lazyLoad() {
	}

	@OnClick({R.id.home_seckill_iv_top, R.id.home_seckill_iv_cart})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.home_seckill_iv_top:
				mRecyclerView.smoothScrollToPosition(0);
				break;
			case R.id.home_seckill_iv_cart:
				if (mUtils.isLogin()) {
					Intent intent = new Intent(getActivity(), MainActivity.class);
					intent.putExtra("page", "2");
					startActivity(intent);
				} else {
					IntentUtils.IntentToLogin(getActivity());
				}
				break;
		}
	}


	private void doRefreshData() {
		mProgressLayout.showContent();
		Map<String, String> map = new HashMap<>();
		map.put("pageNum", pageNum + "");
		map.put("limitType", flag + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/timeLimit")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<SeckillEntity>() {
			@Override
			public SeckillEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值。" + json);
				return new Gson().fromJson(json, SeckillEntity.class);
			}

			@Override
			public void onResponse(SeckillEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//@TODO 获取秒杀数据之后  将顶部图片的URL　发送广播
					Intent broadcastIntent = new Intent();
					broadcastIntent.setAction("COM.EXAMPLE.BANNER.IMAGEVIEW.URL");
					broadcastIntent.putExtra("BANNER_IMAGE_URL", response.getData().getImg());
					getActivity().sendBroadcast(broadcastIntent);
					if (response.getData() != null && response.getData().getTimeLimitList().size() != 0) {
						mList.clear();
						mList.addAll(response.getData().getTimeLimitList());
						if (flag == 0) {
							newDate = response.getData().getNewDate();
							dealWithTimer();
						}
						mAdapter.notifyDataSetChanged();
						mProgressLayout.showContent();
					} else {
						mProgressLayout.showNone(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
							}
						});
					}
				} else {
					LogUtils.i("返回错误了" + response.getMsg() + response.getCode());
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
				mRecyclerView.setPullRefreshEnabled(true);
				mRecyclerView.refreshComplete();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络故障了" + e);
				mRecyclerView.refreshComplete();
				mRecyclerView.setPullRefreshEnabled(true);
				if (call.isCanceled()) {
					call.cancel();
				} else {
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
		});
	}

	private void doRequestData() {
		Map<String, String> map = new HashMap<>();
		map.put("pageNum", pageNum + "");
		map.put("limitType", flag + "");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/timeLimit")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<SeckillEntity>() {
			@Override
			public SeckillEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值。" + json);
				return new Gson().fromJson(json, SeckillEntity.class);
			}

			@Override
			public void onResponse(SeckillEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					if (response.getData() != null) {
						if (response.getData().getTimeLimitList().size() != 0) {
							mList.addAll(response.getData().getTimeLimitList());
							if (flag == 0) {
								newDate = response.getData().getNewDate();
								dealWithTimer();
							}
							mAdapter.notifyDataSetChanged();
							mRecyclerView.loadMoreComplete();
						} else if (response.getData().getTimeLimitList().size() == 0) {
							mRecyclerView.setNoMore(true);
							pageNum--;
						}
					}
					mProgressLayout.showContent();
				} else {
					ToastUtils.showToast(getActivity(), "网络异常");
					pageNum--;
					mRecyclerView.loadMoreComplete();
				}
				mRecyclerView.setPullRefreshEnabled(true);
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
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
//                disMissDialog();
			}
		});
	}


	class MyThread extends Thread {
		//用来停止线程
		boolean endThread;
		List<SeckillEntity.SeckillData.SeckillList> mRecommendActivitiesList;

		public MyThread(List<SeckillEntity.SeckillData.SeckillList> mRecommendActivitiesList) {
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
						LogUtils.e("run: " + finaltime);
						mRecommendActivitiesList.get(i).setFinalTime(finaltime);
						mRecommendActivitiesList.get(i).setHours((int) hours_);
						mRecommendActivitiesList.get(i).setMin((int) minutes);
						mRecommendActivitiesList.get(i).setSec((int) second);
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
	}

	public void dealWithTimer() {
		if (mList == null || mList.size() == 0) {
			return;
		}

		for (int i = 0; i < mList.size(); i++) {
			long counttime = getTime(newDate, mList.get(i).getProduct_timeEnd());
			long days = counttime / (1000 * 60 * 60 * 24);
			long hours = (counttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			long hours_ = hours + days * 24;
			long minutes = (counttime - days * (1000 * 60 * 60 * 24)
					- hours * (1000 * 60 * 60)) / (1000 * 60);
			long second = (counttime - days * (1000 * 60 * 60 * 24)
					- hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
			//并保存在商品time这个属性内
			String finaltime = days + "天" + hours + "时" + minutes + "分" + second + "秒";
			mList.get(i).setHours((int) hours_);
			mList.get(i).setMin((int) minutes);
			mList.get(i).setSec((int) second);
			mList.get(i).setFinalTime(finaltime);
			mList.get(i).setTime(counttime);
		}
		myThread = new MyThread(mList);
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
		if (myThread != null) {
			myThread.endThread = true;
			myThread = null;
		}
		super.onDestroy();
	}
}
