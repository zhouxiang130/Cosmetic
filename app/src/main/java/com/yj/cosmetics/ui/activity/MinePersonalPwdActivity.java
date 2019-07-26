package com.yj.cosmetics.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.SecurityUtil.MD5Utils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2018/3/15.
 */

public class MinePersonalPwdActivity extends BaseActivity {

	@BindView(R.id.mine_personal_pwd_et_old)
	EditText etOld;
	@BindView(R.id.mine_personal_pwd_et_new)
	EditText etNew;
	@BindView(R.id.mine_personal_pwd_et_new_confirm)
	EditText etNewConfirm;
	@BindView(R.id.mine_personal_pwd_btn)
	Button btnConfirm;

	CustomProgressDialog mDialog;

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_personal_pwd;
	}

	@Override
	protected void initView() {
		setTitleText("修改密码");
		initEtView();
	}

	private void initEtView() {
		etOld.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				int index = etOld.getSelectionStart() - 1;
				if (index > 0) {
					if (com.yj.cosmetics.util.TextUtils.isEmojiCharacter(s.charAt(index))) {
						Editable edit = etOld.getText();
						edit.delete(s.length() - 2, s.length());
						ToastUtils.showToast(MinePersonalPwdActivity.this,"不支持输入表情符号");
					}
				}
			}
		});
		etNew.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				int index = etNew.getSelectionStart() - 1;
				if (index > 0) {
					if (com.yj.cosmetics.util.TextUtils.isEmojiCharacter(s.charAt(index))) {
						Editable edit = etNew.getText();
						edit.delete(s.length() - 2, s.length());
						ToastUtils.showToast(MinePersonalPwdActivity.this,"不支持输入表情符号");
					}
				}
			}
		});
		etNewConfirm.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				int index = etNewConfirm.getSelectionStart() - 1;
				if (index > 0) {
					if (com.yj.cosmetics.util.TextUtils.isEmojiCharacter(s.charAt(index))) {
						Editable edit = etNewConfirm.getText();
						edit.delete(s.length() - 2, s.length());
						ToastUtils.showToast(MinePersonalPwdActivity.this,"不支持输入表情符号");
					}
				}
			}
		});
	}

	@Override
	protected void initData() {
		mDialog = new CustomProgressDialog(this);
	}

	@OnClick({R.id.mine_personal_pwd_btn})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.mine_personal_pwd_btn:
				if (TextUtils.isEmpty(etOld.getText().toString().trim())) {
					ToastUtils.showToast(MinePersonalPwdActivity.this, "请输入旧密码");
					return;
				}
				if (TextUtils.isEmpty(etNew.getText().toString().trim())) {
					ToastUtils.showToast(MinePersonalPwdActivity.this, "请输入新密码");
					return;
				}
				if (TextUtils.isEmpty(etNewConfirm.getText().toString().trim())) {
					ToastUtils.showToast(MinePersonalPwdActivity.this, "请确认新密码");
					return;
				}
				if (!etNew.getText().toString().trim().equals(etNewConfirm.getText().toString().trim())) {
					ToastUtils.showToast(MinePersonalPwdActivity.this, "两次输入的密码不一致");
					return;
				}
				if (etNew.getText().toString().trim().equals(etOld.getText().toString().trim())) {
					ToastUtils.showToast(MinePersonalPwdActivity.this, "新密码与旧密码不能相同");
					return;
				}
				if (etOld.getText().toString().trim().length() < 6 || etNew.getText().toString().trim().length() < 6 ||
						etNewConfirm.getText().toString().trim().length() < 6) {
					ToastUtils.showToast(MinePersonalPwdActivity.this, "密码长度不能小于6位");
					return;
				}
				doAsyncChangePwd(etOld.getText().toString().trim(), etNew.getText().toString().trim());
				break;
		}
	}


	private void doAsyncChangePwd(String old, final String newPwd) {

		Map<String, String> map = new HashMap<>();
		map.put(Key.userId, mUtils.getUid());
		map.put(Key.pass, MD5Utils.MD5(old));
		map.put(Key.newpass, MD5Utils.MD5(newPwd));
		LogUtils.i("changeName传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/updatapass")
				.addParams(Key.data, URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void inProgress(float progress) {
				super.inProgress(progress);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(MinePersonalPwdActivity.this);
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
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (isFinishing()) {
					return;
				}
				dismissDialog();
				if (response != null) {
					if (response.getCode().equals(response.HTTP_OK)) {
						//返回值为200 说明请求成功
						ToastUtils.showToast(MinePersonalPwdActivity.this, "密码修改成功");
						btnConfirm.postDelayed(new Runnable() {
							@Override
							public void run() {
//                                Intent intent = new Intent(MinePersonalPwdActivity.this,UserLoginActivity.class);
//                                intent.putExtra("jump","jump");
//                                startActivity(intent);
//                                MyApplication.exit();
								finish();
							}
						}, 500);
					} else {
						ToastUtils.showToast(MinePersonalPwdActivity.this, "修改失败 :)" + response.getMsg());
					}
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络请求失败" + e);
				dismissDialog();
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(MinePersonalPwdActivity.this, "网络故障,请稍后再试");
				}

			}
		});
	}

	private void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissDialog();
		OkHttpUtils.getInstance().cancelTag(this);
	}

}
