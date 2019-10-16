package com.yj.cosmetics.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.util.LogUtils;
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

public class MinePersonalAlipayActivity extends BaseActivity {
    @BindView(R.id.alipay_et_account)
    EditText etAccount;
    @BindView(R.id.alipay_et_name)
    EditText etName;
    @BindView(R.id.mine_personal_alipay_btn)
    Button btnConfirm;
    CustomProgressDialog mDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_personal_alipay;
    }

    @Override
    protected void initView() {
        setTitleText("支付宝账号");
    }

    @Override
    protected void initData() {
        mDialog = new CustomProgressDialog(this);
    }

    @OnClick({R.id.mine_personal_alipay_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.mine_personal_alipay_btn:
                if(TextUtils.isEmpty(etAccount.getText().toString().trim())){
                    ToastUtils.showToast(this,"请输入支付宝账号");
                    return;
                }
                if(TextUtils.isEmpty(etName.getText().toString().trim())){
                    ToastUtils.showToast(this,"请输入支付宝真实姓名");
                    return;
                }
                doBindAlipay();
                break;
        }
    }
    private void doBindAlipay() {
        Map<String,String> map = new HashMap<>();
        map.put("userId", mUtils.getUid());
        map.put("name",etName.getText().toString().trim());
        map.put("account",etAccount.getText().toString().trim());
        LogUtils.i("传输的值"+ URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/bindingAlipay")
                .addParams("data", URLBuilder.format(map))
                .tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);
                if (mDialog == null) {
                    mDialog = new CustomProgressDialog(MinePersonalAlipayActivity.this);
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
                if(isFinishing()){
                    return;
                }
                dismissDialog();
                if (response != null && response.getCode().equals(response.HTTP_OK)) {
                    ToastUtils.showToast(MinePersonalAlipayActivity.this,"绑定成功");
                    mUtils.saveAlipay(etAccount.getText().toString().trim());
                    mUtils.saveAlipayName(etName.getText().toString().trim());
                    btnConfirm.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },400);
                } else {
                    ToastUtils.showToast(MinePersonalAlipayActivity.this, "网络故障 :)" + response.getMsg());
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                dismissDialog();
                if (call.isCanceled()) {
                    call.cancel();
                }else {
                    ToastUtils.showToast(MinePersonalAlipayActivity.this, "网络故障,请稍后再试");
                }
            }
        });
    }
    private void dismissDialog(){
        if(mDialog != null){
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
