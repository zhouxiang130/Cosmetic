package com.yj.cosmetics.ui.activity.locAddress;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.AddressEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.ui.activity.MineAddressNewActivity;
import com.yj.cosmetics.ui.adapter.StoreAddressAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.ProgressLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/8/1 0001.
 */

public class locAddressActivity extends BaseActivity implements StoreAddressAdapter.Interfaces {
	@BindView(R.id.title_rl_next)
	RelativeLayout reLayout;
	@BindView(R.id.title_tv_next)
	TextView tvRightTitle;
	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	StoreAddressAdapter mAdapter;
	List<AddressEntity.DataBean.ListBean> mList;
	private String address;
	private String latitude;
	private String longitude;

	/**
	 * 需要进行检测的权限数组
	 */
	protected String[] needPermissions = {
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION,
//			Manifest.permission.CAMERA,
//			Manifest.permission.WRITE_EXTERNAL_STORAGE,
//			Manifest.permission.READ_EXTERNAL_STORAGE,
//			Manifest.permission.READ_PHONE_STATE

	};

	private static final int PERMISSON_REQUESTCODE = 0;
	/**
	 * 判断是否需要检测，防止不停的弹框
	 */
	private boolean isNeedCheck = true;
	private ProgressBar progressBar;

	@Override
	protected int getContentView() {
		return R.layout.activity_store_details_address;
	}


	@Override
	protected void initView() {
		setTitleText("选择地址");
		reLayout.setVisibility(View.VISIBLE);
		tvRightTitle.setText("新增地址");
		mList = new ArrayList<>();
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
		mRecyclerView.setLoadingMoreEnabled(false);

		mAdapter = new StoreAddressAdapter(this, mList, mUtils, address);
		mRecyclerView.setAdapter(mAdapter);

		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					public void run() {
						doAsyncGetAddress();
					}
				}, 500);            //refresh data here
			}

			@Override
			public void onLoadMore() {
			}
		});

		mAdapter.setOnItemClickListener(new StoreAddressAdapter.AddressClickListener() {
			@Override
			public void onItemClick(View view, int position) {
//				if (isNeedCheck) {
//					checkPermissions(needPermissions);
//				}


				String addressId = mList.get(position - 4).getAddressId();
				String addressAreaDetail = mList.get(position - 4).getAddressDetail();
				LogUtils.i("onItemClick: " + addressId);
				//数据是使用Intent返回
				Intent intent = new Intent();
				//把返回数据存入Intent
				intent.putExtra("addressId", addressId);
				intent.putExtra("addressAreaDetail", addressAreaDetail);
				//设置返回数据
				setResult(RESULT_OK, intent);
				//关闭Activity
				finish();


			}
		});
		mAdapter.setInterfaces(this);
		mAdapter.setOnLocAddItemClickListener(new StoreAddressAdapter.LocAddressClickListener() {
			@Override
			public void onItemClick(View view) {

				//数据是使用Intent返回
				if (!String.valueOf(latitude).equals("null") && !String.valueOf(longitude).equals("null")) {
					doAsyncGetLoction(address, latitude, longitude);
				}

				new Handler().postDelayed(new Runnable() {
					public void run() {
						Intent intent = new Intent();
						intent.putExtra("addressAreaDetail", address);
						intent.putExtra("latitude", latitude);
						intent.putExtra("longitude", longitude);
						//设置返回数据
						setResult(RESULT_OK, intent);
						finish();
					}
				}, 300);
			}
		});

	}


	/**
	 * @since 2.5.0
	 */
	private void checkPermissions(String... permissions) {
		List<String> needRequestPermissonList = findDeniedPermissions(permissions);
		if (null != needRequestPermissonList && needRequestPermissonList.size() > 0) {
			ActivityCompat.requestPermissions(this, needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]), PERMISSON_REQUESTCODE);
		}
		if (PERMISSON_REQUESTCODE == needRequestPermissonList.size()) {
			againLoc();
		}
	}

	/**
	 * 获取权限集中需要申请权限的列表
	 *
	 * @param permissions
	 * @return
	 * @since 2.5.0
	 */
	private List<String> findDeniedPermissions(String[] permissions) {
		List<String> needRequestPermissonList = new ArrayList<String>();
		for (String perm : permissions) {
			if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
				needRequestPermissonList.add(perm);
			}
		}
		return needRequestPermissonList;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case PERMISSON_REQUESTCODE:
				if (!verifyPermissions(grantResults)) {
//					showMissingPermissionDialog();
					isNeedCheck = false;
				}
				if (isNeedCheck) {
					againLoc();
				} else {
					againLoc();
//					checkPermissions(needPermissions);
				}
				LogUtils.i("onRequestPermissionsResult: " + isNeedCheck);
				break;
		}
	}

	private void againLoc() {
		if (!String.valueOf(latitude).equals("null") && !String.valueOf(longitude).equals("null")) {
			doAsyncGetLoction(address, latitude, longitude);
		}
//		doAsyncGetLoction(address, latitude, longitude);
		new Handler().postDelayed(new Runnable() {
			public void run() {

				progressBar.setVisibility(View.INVISIBLE);

				Intent intent = new Intent();
				intent.putExtra("addressAreaDetail", address);
				intent.putExtra("latitude", latitude);
				intent.putExtra("longitude", longitude);
				//设置返回数据
				setResult(RESULT_OK, intent);
				finish();
			}
		}, 3000);
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 检测是否说有的权限都已经授权
	 *
	 * @param grantResults
	 * @return
	 * @since 2.5.0
	 */
	private boolean verifyPermissions(int[] grantResults) {
		for (int result : grantResults) {
			if (result != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 重新执行定位的操作
	 *
	 * @param progressBar
	 */
	@Override
	public void onClicks(final ProgressBar progressBar) {
		this.progressBar = progressBar;
		progressBar.setVisibility(View.VISIBLE);
		if (isNeedCheck) {
			checkPermissions(needPermissions);
		}


//		doAsyncGetLoction(address, latitude, longitude);
//		new Handler().postDelayed(new Runnable() {
//			public void run() {
//				instance.onDestroy();
//				progressBar.setVisibility(View.INVISIBLE);
//
//				Intent intent = new Intent();
//				intent.putExtra("addressAreaDetail", address);
//				intent.putExtra("latitude", latitude);
//				intent.putExtra("longitude", longitude);
//				//设置返回数据
//				setResult(RESULT_OK, intent);
//				finish();
//
//
//			}
//		}, 3000);
//		mAdapter.notifyDataSetChanged();
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
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("sessionSaveLonLatName json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					LogUtils.i("onResponse: " + response.getMsg());
					preferencesUtil.setBooleanValue("isGetCoupon", false);
				} else {
				}
			}
		});
	}


	@Override
	protected void onResume() {
		mRecyclerView.refresh();
		super.onResume();
	}


	@OnClick({R.id.title_rl_next})
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.title_rl_next:
				Intent intent = new Intent(this, MineAddressNewActivity.class);
				intent.putExtra("tag", "new");
				startActivity(intent);
				break;
		}
	}


	@Override
	protected void initData() {

	}


	private void doAsyncGetAddress() {

		mProgressLayout.showContent();
		Map<String, String> map = new HashMap<>();
		map.put("usersId", mUtils.getUid());
		LogUtils.i("获取地址传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/userAddressList").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<AddressEntity>() {
			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
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

			@Override
			public AddressEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetAddress --json的值" + json);
				return new Gson().fromJson(json, AddressEntity.class);
			}

			@Override
			public void onResponse(AddressEntity response) {
				if (response.getCode().equals(response.HTTP_OK) && response.getData() != null) {

					if (response.getData().getUserAddressMaps().size() != 0) {
						mRecyclerView.setVisibility(View.VISIBLE);
						mList.clear();
						mList.addAll(response.getData().getUserAddressMaps());
						mAdapter.notifyDataSetChanged();

					} else if (response.getData().getUserAddressMaps().size() == 0) {

						mRecyclerView.setVisibility(View.VISIBLE);
					}

					mProgressLayout.showContent();
				} else {
					LogUtils.i("故障" + response.getMsg());
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
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		OkHttpUtils.getInstance().cancelTag(this);
	}


}
