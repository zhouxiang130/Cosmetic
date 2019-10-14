package com.yj.cosmetics.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.base.Variables;
import com.yj.cosmetics.model.AddressEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.ui.MainActivity;
import com.yj.cosmetics.ui.adapter.ManageAddressAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.yj.cosmetics.widget.Dialog.ValidateAddressDialog;
import com.yj.cosmetics.widget.Dialog.ValidateAddressDialogs;
import com.yj.cosmetics.widget.ProgressLayout;
import com.zhy.http.okhttp.OkHttpUtils;

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
 * Created by Suo on 2017/4/28.
 */

public class MineAddressManageActivity extends BaseActivity {
	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	@BindView(R.id.address_manage_ll)
	LinearLayout llNomore;
	@BindView(R.id.item_mine_manage_confirm)
	Button btnConfirm;
	ManageAddressAdapter mAdapter;
	List<AddressEntity.DataBean.ListBean> mList;
	Intent intent;
	private boolean isFinish = false;
	private String state, cartIdObj, shopId, productId;
	CustomProgressDialog mDialog;

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_address_manage;
	}

	@SuppressLint("LongLogTag")
	@Override
	protected void initView() {
		setTitleText("管理收货地址");
		mList = new ArrayList<>();
		state = getIntent().getStringExtra("state");
		cartIdObj = getIntent().getStringExtra("cartIdObj");
		shopId = getIntent().getStringExtra("shopId");
		productId = getIntent().getStringExtra("productId");
		LogUtils.e("initView: productId  " + productId);
		if (!TextUtils.isEmpty(state)) {
			intent = new Intent();
			if (state.equals("choose")) {
				setResult(Variables.CHOOSE_ADDRESS, intent);
			} else {
				setResult(Variables.NEW_ADDRESS, intent);
			}
		}
		mDialog = new CustomProgressDialog(this);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
		mRecyclerView.setLoadingMoreEnabled(false);
		mAdapter = new ManageAddressAdapter(this, mList, mUtils, mRecyclerView, llNomore);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(new ManageAddressAdapter.ManageAddressClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
				if (postion == mList.size() + 1) {
					return;
				}
				if (!TextUtils.isEmpty(getIntent().getStringExtra("state"))) {

					setFinish(postion);
//						doAsynGetCheckAddress(mList.get(postion - 1).getAddressId(), postion);
			/*		if (mList.get(postion - 1).getType().equals("1")) {
					} else {
						showDeleteDialog(2, "我们将切换您选择的地址并回到首页，请您重新进行下单操作.", mList.get(postion - 1).getAddressId());
//						Toast.makeText(MineAddressManageActivity.this, "选择地址超出配送范围", Toast.LENGTH_SHORT).show();
					}*/
				}
			}
		});

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
	}

	private void doAsynUpDefaultAddress(String addressId) {
		Map<String, String> map = new HashMap<>();
		map.put("addressId", addressId);
		map.put("usersId", mUtils.getUid());
		LogUtils.i("地址传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.defaultAddressToIndex).tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void inProgress(float progress) {
				super.inProgress(progress);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(MineAddressManageActivity.this);
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
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络故障" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(MineAddressManageActivity.this, "网络故障,请稍后再试");
				}
				dismissDialog2();
				deleteDialog.dismiss();
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("editAddressValidation json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				dismissDialog2();
				if (response.getCode().equals(response.HTTP_OK)) {
					Intent intents = new Intent();
					intents.setAction("CN.YJ.ROBUST.REFRESHDATA");
					sendBroadcast(intents);
					Intent intent = new Intent(MineAddressManageActivity.this, MainActivity.class);
					intent.putExtra("page", "0");
					startActivity(intent);
					deleteDialog.dismiss();

				} else {
					ToastUtils.showToast(MineAddressManageActivity.this, response.getCode() + " :) " + response.getMsg());
					deleteDialog.dismiss();
				}
			}
		});
	}


	private void doAsynAddress(final int postion) {
		Map<String, String> map = new HashMap<>();
		map.put("addressArea", mList.get(postion - 1).getAddressArea());
		map.put("addressDetail", mList.get(postion - 1).getAddressDetail());
		LogUtils.i("地址传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/editAddressValidation").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {
			@Override
			public void inProgress(float progress) {
				super.inProgress(progress);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(MineAddressManageActivity.this);
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
			public void onError(Call call, Exception e) {
				LogUtils.i("网络故障" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(MineAddressManageActivity.this, "网络故障,请稍后再试");
				}
				dismissDialog2();

				super.onError(call, e);
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("editAddressValidation json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				dismissDialog2();
				if (response.getCode().equals(response.HTTP_OK)) {

					if (response.getMsg() != null && !response.getMsg().equals("")) {
						if ("成功".equals(response.getMsg())) {
//							doAsyncEditAddress();
							setFinish(postion);
						} else {
							showDeleteDialogs(response.getMsg(), postion);
						}
					} else {

					}
				} else {
					ToastUtils.showToast(MineAddressManageActivity.this, response.getCode() + " :) " + response.getMsg());
				}
			}
		});
	}

	ValidateAddressDialogs deleteDialogs;

	private void showDeleteDialogs(String msg, final int postion) {
		if (deleteDialogs == null) {
			deleteDialogs = new ValidateAddressDialogs(MineAddressManageActivity.this);
		}
		if (!deleteDialogs.isShowing()) {
			deleteDialogs.show();
//			if (deleteDialogs.getContext() instanceof MineAddressManageActivity) {
//				if (!((MineAddressManageActivity) deleteDialogs.getContext()).isFinishing()) {
//				}
//			}
		}
		deleteDialogs.getTvTitle().setText(msg);

//		if (deleteDialogs.getTvTitle() != null) {
//		}


//		if (deleteDialogs.getTvConfirm() != null) {
		deleteDialogs.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setFinish(postion);
				deleteDialogs.dismiss();

			}
		});
//		}
//		if (deleteDialogs.getTvCancel() != null) {
		deleteDialogs.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				deleteDialogs.dismiss();
			}
		});
//		}
	}


	private void doAsynGetCheckAddress(String addressId, final int postion) {
		Map<String, String> map = new HashMap<>();
		map.put("addressId", addressId);
		if (!TextUtils.isEmpty(cartIdObj)) {
			map.put("cartIdObj", cartIdObj);
		}
		if (!TextUtils.isEmpty(productId)) {
			map.put("productId", productId);
		}

		LogUtils.i("xzAddressValidation 获取地址验证的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/xzAddressValidation").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(MineAddressManageActivity.this);
					if (!MineAddressManageActivity.this.isFinishing()) {
						mDialog.show();
					}
				} else {
					if (!MineAddressManageActivity.this.isFinishing()) {
						mDialog.show();
					}
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {

				}
				dismissDialog2();
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("xzAddressValidation --json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response.getCode().equals(response.HTTP_OK)) {
					doAsynAddress(postion);
//					setFinish(postion);
				} else {
					showDeleteDialog(1, response.getMsg(), null);
				}
				dismissDialog2();
			}
		});
	}

	ValidateAddressDialog deleteDialog;

	private void showDeleteDialog(final int i, String msg, final String addressId) {

		if (deleteDialog == null) {
			deleteDialog = new ValidateAddressDialog(MineAddressManageActivity.this);
		}
		if (!deleteDialog.isShowing()) {
			deleteDialog.show();
		}
		deleteDialog.getTvTitle().setText(msg);
		if (i == 1) {
			deleteDialog.getTvConfirm().setVisibility(View.GONE);
		} else {
			deleteDialog.getTvConfirm().setVisibility(View.VISIBLE);
		}
		deleteDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				deleteDialog.dismiss();
			}
		});

		deleteDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (i == 1) {
					deleteDialog.dismiss();
				} else {
					doAsynUpDefaultAddress(addressId);
					deleteDialog.dismiss();
				}
			}
		});
	}

	private void setFinish(int postion) {
		intent.putExtra("area", mList.get(postion - 1).getAddressArea());
		intent.putExtra("name", mList.get(postion - 1).getReceiverName());
		intent.putExtra("tel", mList.get(postion - 1).getReceiverTel());
		intent.putExtra("detial", mList.get(postion - 1).getAddressDetail());
		intent.putExtra("addressId", mList.get(postion - 1).getAddressId());
		finish();
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void onResume() {
		mRecyclerView.refresh();
		super.onResume();
	}

	@OnClick({R.id.address_manage_new_address})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.address_manage_new_address:
				Intent intent = new Intent(this, MineAddressNewActivity.class);
				intent.putExtra("tag", "new");
				startActivity(intent);
				break;
		}
	}


	private void doAsyncGetAddress() {
		mProgressLayout.showContent();
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		if (!TextUtils.isEmpty(productId)) {
			map.put("proId", productId);
		}

		if (!TextUtils.isEmpty(cartIdObj)) {
			map.put("cartIdObj", cartIdObj);
		}
		if (!TextUtils.isEmpty(shopId)) {
			map.put("shopId", shopId);
		}


		LogUtils.i("获取地址传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/searchAddress").tag(this)
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
						llNomore.setVisibility(View.GONE);
//						btnConfirm.setVisibility(View.VISIBLE);
						isFinish = true;
						mList.clear();
						mList.addAll(response.getData().getUserAddressMaps());
						mAdapter.notifyDataSetChanged();
					} else if (response.getData().getUserAddressMaps().size() == 0) {
						llNomore.setVisibility(View.VISIBLE);
//						btnConfirm.setVisibility(View.GONE);
						mRecyclerView.setVisibility(View.GONE);
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
	protected void onBackClick(Object o) {
		LogUtils.i("我onbackClick了");
		if (!isFinish) {
			finish();
			return;
		}
		if (mList == null || mList.size() == 0) {
			//地址删了
			if (intent != null) {
				LogUtils.i(" 地址为0的时候delete为true");
				intent.putExtra("delete", "true");
			}
		} else {
			if (intent != null && TextUtils.isEmpty(intent.getStringExtra("name")) && getIntent().getStringExtra("state").equals("new")) {
				LogUtils.i("我在intent里");
				if (!TextUtils.isEmpty(getIntent().getStringExtra("aid"))) {
					for (int i = 0; i < mList.size(); i++) {
						if (mList.get(i).getAddressId().equals(getIntent().getStringExtra("aid"))) {
							//有aid
//							intent.putExtra("area", mList.get(i).getAddressArea());
//							intent.putExtra("name", mList.get(i).getReceiverName());
//							intent.putExtra("tel", mList.get(i).getReceiverTel());
//							intent.putExtra("detial", mList.get(i).getAddressAreaDetail());
//							intent.putExtra("addressId", mList.get(i).getAddressId());
//							LogUtils.i("intent有值了" + intent.getStringExtra("name"));

							doAsynGetCheckAddress(mList.get(i).getAddressId(), i + 1);

						} else {
							finish();
						}
					}
				} else {
					finish();
				}
				if (intent != null && TextUtils.isEmpty(intent.getStringExtra("name"))) {
					for (int i = 0; i < mList.size(); i++) {
						if (mList.get(i).getAddressDefault().equals("1")) {
							if (mList.get(i).getType().equals("1")) {

//								doAsynGetCheckAddress(mList.get(i).getAddressId(), i + 1);
//								intent.putExtra("area", mList.get(i).getAddressArea());
//								intent.putExtra("name", mList.get(i).getReceiverName());
//								intent.putExtra("tel", mList.get(i).getReceiverTel());
//								intent.putExtra("detial", mList.get(i).getAddressAreaDetail());
//								intent.putExtra("addressId", mList.get(i).getAddressId());
//								LogUtils.i("intent有值了" + intent.getStringExtra("name"));
							}
						}
					}
				} else {
					finish();
				}

//				if (intent != null && TextUtils.isEmpty(intent.getStringExtra("name"))) {
//					//没有默认地址,返回时展示添加新地址
//					LogUtils.i("我返回没默认地址了delete为true");
//					intent.putExtra("delete", "true");
//				}

			} else {
				finish();
			}
		}
//		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	private void dismissDialog() {
		if (deleteDialog != null) {
			deleteDialog.dismiss();
			deleteDialog = null;

		}
	}

	private void dismissDialogs() {
		if (deleteDialogs != null) {
			deleteDialogs.dismiss();
			deleteDialogs = null;
		}
	}


	private void dismissDialog2() {
		if (mDialog != null) {
			mDialog.dismiss();
//			mDialog = null;
		}
	}

	@Override
	protected void onDestroy() {
		dismissDialog();
		dismissDialogs();
		dismissDialog2();
		OkHttpUtils.getInstance().cancelTag(this);
		super.onDestroy();
	}
}
