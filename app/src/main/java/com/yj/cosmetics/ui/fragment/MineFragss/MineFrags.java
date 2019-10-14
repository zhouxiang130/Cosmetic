package com.yj.cosmetics.ui.fragment.MineFragss;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseFragment;
import com.yj.cosmetics.base.Constant;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.ui.adapter.MineFragAdapter;
import com.yj.cosmetics.util.AuthorUtils;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.yj.cosmetics.widget.ProgressLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2017/11/18.
 *
 * @TODO 个人中心 3.0版本升级页面(添加下拉刷新)
 */

public class MineFrags extends BaseFragment {


	private static final String TAG = "MineFrags";
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;
	private MineFragAdapter mListAdapter;
	private MineEntity.MineData data;

	/**
	 * 需要进行检测的权限数组
	 */
	private static final int PERMISSON_REQUESTCODE = 0;
	private boolean isNeedCheck = true;
	protected String[] needPermissions = {
			Manifest.permission.CALL_PHONE
	};


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = createView(inflater.inflate(R.layout.fragment_mines, container, false));
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mUtils.isLogin()) {
			doAsyncGetData();
			doAsyncGetInfo();
		}
		if (mListAdapter != null) {
			mListAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void initData() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(layoutManager);
		mListAdapter = new MineFragAdapter(getContext(), mUtils);
		mRecyclerView.setAdapter(mListAdapter);
		mRecyclerView.setLoadingMoreEnabled(false);
		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					public void run() {
						if (mUtils.isLogin()) {
							doAsyncGetData();
							doAsyncGetInfo();
						} else {
							mProgressLayout.showContent();
							mRecyclerView.setPullRefreshEnabled(true);
							mRecyclerView.refreshComplete();
						}
					}
				}, 500);
			}

			@Override
			public void onLoadMore() {
			}
		});
		mRecyclerView.refresh();
		mListAdapter.setCheckInterface(new MineFragAdapter.CheckInterfaces() {
			@Override
			public void onChecks(int i) {
				if (i == 1) {//1 是在线客服
					//TODO 改成打电话
					if (mUtils.isLogin()) {
					} else {
						IntentUtils.IntentToLogin(getActivity());
					}
				} else {//2  呼叫客服
					setCallDialog();


				}

			}
		});
	}

	CustomNormalContentDialog mDialog;

	private void setCallDialog() {
		if (mDialog == null) {
			mDialog = new CustomNormalContentDialog(getActivity());
		}

		if (TextUtils.isEmpty(data.getServiceTel()) && TextUtils.isEmpty(mUtils.getServiceTel())) {
			ToastUtils.showToast(getActivity(), "无法获取联系电话，请检查网络稍后再试");
			return;
		}
		if (!mDialog.isShowing()) {
			mDialog.show();
		}
		mDialog.getTvTitle().setText("客服热线");
		if (!TextUtils.isEmpty(data.getServiceTel())) {
			mDialog.getTvContent().setText("拨打" + data.getServiceTel() + "热线，联系官方客服");
		} else if (!TextUtils.isEmpty(mUtils.getServiceTel())) {
			mDialog.getTvContent().setText("拨打" + mUtils.getServiceTel() + "热线，联系官方客服");
		}
		mDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (AuthorUtils.checkPermissions(needPermissions)) {
					setActionCall();

				}

			}
		});
		mDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismissDialog();
			}
		});
	}

	private void setActionCall() {
		//拨打电话
		Intent callIntent = new Intent();
		callIntent.setAction(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + data.getServiceTel()));
		startActivity(callIntent);
		dismissDialog();
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {

			case PERMISSON_REQUESTCODE:
				if (!AuthorUtils.verifyPermissions(grantResults)) {
//					showMissingPermissionDialog();
					isNeedCheck = false;
				} else {
					isNeedCheck = true;
				}
				if (isNeedCheck) {
					setActionCall();
				}
				Log.e(TAG, "onRequestPermissionsResult: " + isNeedCheck);
				break;
		}
	}

	public void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	private void doAsyncGetData() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		LogUtils.i("doAsyncGetData 传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/zoneOrder").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<MineEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				mProgressLayout.showContent();
				mRecyclerView.setPullRefreshEnabled(true);
				mRecyclerView.refreshComplete();
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}

			@Override
			public MineEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i(" zoneOrder json的值" + json);
				return new Gson().fromJson(json, MineEntity.class);
			}

			@Override
			public void onResponse(MineEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					data = response.getData();
					setData(response.getData());
					mListAdapter.setData(response.getData());
					mProgressLayout.showContent();
					mRecyclerView.setPullRefreshEnabled(true);
					mRecyclerView.refreshComplete();
				} else {
					mProgressLayout.showContent();
					mRecyclerView.setPullRefreshEnabled(true);
					mRecyclerView.refreshComplete();
				}
			}
		});
	}


	private void doAsyncGetInfo() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		LogUtils.i("doAsyncGetInfo 传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/countMsg").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
//				tvInfo.setVisibility(View.GONE);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("countMsg json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					if (response.getData() != null && Float.parseFloat(response.getData().toString()) > 0) {
						mListAdapter.setNewData(response.getData().toString());
//						tvInfo.setText(response.getData().toString());
					} else {
//						tvInfo.setVisibility(View.GONE);
					}
				} else {
//					tvInfo.setVisibility(View.GONE);
				}
			}
		});
	}

	public void setData(MineEntity.MineData data) {
		preferencesUtil.setValue("userType", data.getUserType());
	}
}
