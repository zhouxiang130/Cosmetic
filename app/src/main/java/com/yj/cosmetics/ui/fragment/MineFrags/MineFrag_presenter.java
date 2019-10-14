package com.yj.cosmetics.ui.fragment.MineFrags;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.model.UserInfoEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public class MineFrag_presenter implements MineFrag_contract.Presenter {
	private MineFrag_contract.View mView = null;
	private UserUtils mUtils;

	private String serviceTel = null;

	public MineFrag_presenter(MineFrag_contract.View view) {
		this.mView = view;
	}

	@Override
	public void subscribe() {
	}

	@Override
	public void unsubscribe() {
	}


	@Override
	public void setMineHeadInfo(UserUtils mUtils) {
		this.mUtils = mUtils;
		if (!TextUtils.isEmpty(mUtils.getUserName())) {
			mView.setLoginName(true);
			if (!TextUtils.isEmpty(mUtils.getTel())) {
				if (mUtils.getTel().length() > 8) {
					mView.setTel(true, mUtils.getTel().replace(mUtils.getTel().substring(4, 8), "****"));
//					tvTel.setText();
				} else {
					mView.setTel(true, mUtils.getTel());
				}
			}
//			mView.setTel(true, null);
		} else {
			mView.setLoginName(false);
		}
		LogUtils.e("onResume: " + " 头像信息： " + mUtils.getAvatar());
		if (!TextUtils.isEmpty(mUtils.getAvatar())) {
			LogUtils.i("avatar的值" + mUtils.getAvatar());
			mView.isLoginHeadImg(true);
		} else {
			mView.isLoginHeadImg(false);
		}
		if (mUtils.isLogin()) {
			doAsyncGetData(mUtils);
			doAsyncGetInfo();
			if (TextUtils.isEmpty(mUtils.getAvatar())) {
				doAsyncGetMyInfo();
			}
		} else {
			mView.isNoLogin();
		}
	}

	private void doAsyncGetMyInfo() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		LogUtils.i("获取个人信息 传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.USERMESSAGE).tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<UserInfoEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				}
			}

			@Override
			public UserInfoEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("userMessage json的值" + json);
				return new Gson().fromJson(json, UserInfoEntity.class);
			}

			@Override
			public void onResponse(UserInfoEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
//					setData(response.getData());
					mUtils.saveAvatar(response.getData().getUserHeadimg());
				} else {

				}
			}
		});
	}

	public void doAsyncGetData(UserUtils mUtils) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		LogUtils.i("zoneOrder 传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/zoneOrder").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<MineEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}

			@Override
			public MineEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("zoneOrder json的值" + json);
				return new Gson().fromJson(json, MineEntity.class);
			}

			@Override
			public void onResponse(MineEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					setData(response.getData());
				} else {
				}
			}
		});
	}


	public void setData(MineEntity.MineData data) {
		mView.setDatas(data);
	}

	private void doAsyncGetInfo() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		LogUtils.i("countMsg 传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/countMsg").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				mView.setMineNewNum(false, null);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("countMsg>>> json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					if (response.getData() != null && Float.parseFloat(response.getData().toString()) > 0) {
						mView.setMineNewNum(true, response);
					} else {
						mView.setMineNewNum(false, null);
					}
				} else {
					mView.setMineNewNum(false, null);
				}
			}
		});
	}
}
