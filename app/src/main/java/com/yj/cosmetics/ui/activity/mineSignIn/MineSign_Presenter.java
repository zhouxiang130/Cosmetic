package com.yj.cosmetics.ui.activity.mineSignIn;

import android.util.Log;

import com.google.gson.Gson;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.SignInEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class MineSign_Presenter implements MineSign_contract.Presenter {

	private static final String TAG = "MineSignInActivity";
	private MineSign_contract.View mView = null;

	public MineSign_Presenter(MineSign_contract.View view) {
		this.mView = view;
	}



	@Override
	public void subscribe() {

	}

	@Override
	public void unsubscribe() {

	}
	@Override
	public void doAsyncGetSignIn(String uid) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", uid);
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/searchUserSign").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<SignInEntity>() {

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {

				}
			}

			@Override
			public SignInEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetSignIn json的值" + json);
				return new Gson().fromJson(json, SignInEntity.class);
			}

			@Override
			public void onResponse(SignInEntity response) {
				if (response != null && response.HTTP_OK.equals(response.getCode())) {
					Log.i(TAG, "onResponse: " + response.getMsg());
					SignInEntity.DataBean data = response.getData();
					mView.setSignInData(data);
				} else {

				}
			}
		});
	}
}
