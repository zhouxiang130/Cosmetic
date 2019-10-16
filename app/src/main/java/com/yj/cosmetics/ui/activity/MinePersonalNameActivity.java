package com.yj.cosmetics.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
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

public class MinePersonalNameActivity extends BaseActivity {
    @BindView(R.id.mine_personal_et_name)
    EditText etName;
    CustomProgressDialog loadingDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_personal_name;
    }

    @Override
    protected void initView() {
        setTitleText("修改昵称");
    }

    @Override
    protected void initData() {
        loadingDialog = new CustomProgressDialog(this);

    }

    @OnClick({R.id.mine_personal_name_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.mine_personal_name_btn:
                if(TextUtils.isEmpty(etName.getText().toString().trim())){
                    ToastUtils.showToast(MinePersonalNameActivity.this,"请输入要修改的昵称");
                    return;
                }
                doAsyncChangeName(etName.getText().toString().trim());
                break;
        }
    }

    private void doAsyncChangeName(final String name) {
        Map<String, String> map = new HashMap<>();
        map.put(Key.userId, mUtils.getUid());
        map.put("userName", name);
        LogUtils.i("changeName传输的值" + URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/updateUserName")
                .addParams(Key.data, URLBuilder.format(map))
                .tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);
                if (loadingDialog == null) {
                    loadingDialog = new CustomProgressDialog(MinePersonalNameActivity.this);
                    if (!isFinishing()) {
                        loadingDialog.show();
                    }
                } else {
                    if (!isFinishing()) {
                        loadingDialog.show();
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
                dismissDialog2();
                if (response != null) {
                    if (response.getCode().equals(response.HTTP_OK)) {
                        //返回值为200 说明请求成功
                        ToastUtils.showToast(MinePersonalNameActivity.this, "昵称修改成功");
                        mUtils.saveUserName(name);
                        etName.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                               finish();
                            }
                        },400);
                    } else {
                        ToastUtils.showToast(MinePersonalNameActivity.this, "验证失败 :)" + response.getMsg());
                    }
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                LogUtils.i("网络请求失败" + e);
                dismissDialog2();
                if(call.isCanceled()){
                    call.cancel();
                }else{
                    ToastUtils.showToast(MinePersonalNameActivity.this, "网络故障,请稍后再试");
                }

            }
        });
    }

    private void dismissDialog2() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDialog2();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
