package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Constant;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.util.AESUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.MatchUtils;
import com.yj.cosmetics.util.RSAUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Suo on 2018/3/15.
 */

public class MinePersonalConfirmTelActivity extends BaseActivity {

    @BindView(R.id.change_alipay_tel)
    TextView tvTel;
    @BindView(R.id.mine_personal_confirm_tel_verify)
    EditText etVerify;
    @BindView(R.id.mine_personal_tel_verify)
    TextView tvVerify;
    @BindView(R.id.mine_personal_confirm_tel_btn)
    Button btnConfirm;

    boolean isSend = false;
    boolean isNetError = false;

    final int TAG_SMS = 0x11;
    private SMSThread mThread;
    private int countTime = 60;

    CustomProgressDialog mDialog;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what) {
                case TAG_SMS:
                    if (isNetError) {
                        isSend = false;
                        tvVerify.setText("获取验证码");
                    } else {
                        if (countTime == 60) {
                            tvVerify.setText(String.valueOf(countTime) + "s");
                            countTime--;
                        } else if (countTime < 60 & countTime > 0) {
                            tvVerify.setText(String.valueOf(countTime) + "s");
                            countTime--;
                        } else if (countTime == 0) {
                            //计时器已走完 验证码可以继续发送
                            isSend = false;
                            tvVerify.setEnabled(true);
                            tvVerify.setText("获取验证码");
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_personal_confirm_tel;
    }

    @Override
    protected void initView() {
        setTitleText("安全验证");
    }

    @Override
    protected void initData() {
        tvTel.setText(mUtils.getTel());

        mThread = new SMSThread();
        if (!mThread.isInterrupted()) {
            isSend = false;
        }
        mDialog = new CustomProgressDialog(this);
    }

    @OnClick({R.id.mine_personal_confirm_tel_btn,R.id.mine_personal_tel_verify})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.mine_personal_confirm_tel_btn:
                if(TextUtils.isEmpty(etVerify.getText().toString().trim())){
                    ToastUtils.showToast(this, "请输入验证码");
                    return;
                }
                if (!MatchUtils.isValidPhoneNumber(mUtils.getTel())) {
                    ToastUtils.showToast(this, "手机号格式不正确，请重新登陆");
                    return;
                } else {
                    doAsyncVerify();
                }
                break;

            case R.id.mine_personal_tel_verify:
                inValidate(mUtils.getTel());
                break;
        }
    }
    private void inValidate(String tel) {
        if (!MatchUtils.isValidPhoneNumber(tel)) {
            ToastUtils.showToast(this, "手机号格式不正确，请重新登陆");
        } else {
            //手机号格式正确
            if (isSend) {
                return;
            }
            doAsyncSendMS(tel);
        }
    }

    //使用SMSThread来更新界面.隐藏和显示发送验证码
    private class SMSThread extends Thread {
        @Override
        public void run() {
//            super.run();
            while (!Thread.currentThread().isInterrupted()) {
                if (isSend) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = TAG_SMS;
                    msg.sendToTarget();
                }
            }
        }
    }

    private void doAsyncSendMS(String tel) {
        Map<String, String> map = new HashMap<>();
        map.put(Key.tel, tel);
//        map.put(Key.type, Key.forgot);
        LogUtils.i("传输的值" + URLBuilder.format(map));
        try {
            OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.SendMsg).tag(this)
    //                .addParams(Key.data, URLBuilder.format(map))
                    .addParams(Key.data, AESUtils.encryptData(Constant.KEY, URLBuilder.format(map)))
                    .addParams("key", RSAUtils.encryptByPublicKey(Constant.KEY))
                    .build().execute
                    (new Utils.MyResultCallback<NormalEntity>() {

                        @Override
                        public void onBefore(Request request) {
                            LogUtils.i("我onBefore了");
                            super.onBefore(request);
                            isSend = true;
                            isNetError = false;

                            countTime = 60;
                            if (!mThread.isAlive()) {
                                if (!mThread.isInterrupted()) {
                                    mThread.start();
                                }
                            }
                        }

                        @Override
                        public void onError(Call call, Exception e) {
                            super.onError(call, e);
                            if (call.isCanceled()) {
                                call.cancel();
                            }else{
                                ToastUtils.showToast(MinePersonalConfirmTelActivity.this, "网络故障,请稍后再试");
                            }
                            LogUtils.i("我进入onError了" + e);
                            isSend = false;
                            isNetError = true;
    //                        mThread=null
                            //发送验证码按钮
                            tvVerify.setText("获取验证码");
                        }

                        //进行json解析.
                        @Override
                        public NormalEntity parseNetworkResponse(Response response) throws Exception {
                            String json = response.body().string().trim();
                            LogUtils.i("json的值" + json);
                            return new Gson().fromJson(json, NormalEntity.class);
                        }

                        //对返回结果进行判断.
                        @Override
                        public void onResponse(NormalEntity response) {
                            LogUtils.i("我onResponse了");
                            if (isFinishing()) {
                                return;
                            }
                            if (response.getCode().equals(response.HTTP_OK)) {
                                ToastUtils.showToast(MinePersonalConfirmTelActivity.this, "验证码已发送");
                                isNetError = false;
                            } else {
                                ToastUtils.showToast(MinePersonalConfirmTelActivity.this, response.getMsg() + "):" + response.getCode());
                                isNetError = true;
                                isSend = false;
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAsyncVerify() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", mUtils.getTel());
        map.put("phoneCode", etVerify.getText().toString().trim());
        LogUtils.i("传输的值" + URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/securityerification").tag(this)
                .addParams(Key.data, URLBuilder.format(map))
                .build().execute
                (new Utils.MyResultCallback<NormalEntity>() {

                    @Override
                    public void inProgress(float progress) {
                        super.inProgress(progress);
                        if (mDialog == null) {
                            mDialog = new CustomProgressDialog(MinePersonalConfirmTelActivity.this);
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
                        dismissDialog();
                        if (call.isCanceled()) {
                            call.cancel();
                        }else {
                            ToastUtils.showToast(MinePersonalConfirmTelActivity.this, "网络故障,请稍后再试");
                        }
                    }

                    //进行json解析.
                    @Override
                    public NormalEntity parseNetworkResponse(Response response) throws Exception {
                        String json = response.body().string().trim();
                        LogUtils.i("json的值" + json);
                        return new Gson().fromJson(json, NormalEntity.class);
                    }

                    //对返回结果进行判断.
                    @Override
                    public void onResponse(NormalEntity response) {
                        if (isFinishing()) {
                            return;
                        }
                        dismissDialog();
                        if (response != null && response.getCode().equals(response.HTTP_OK)) {
                            ToastUtils.showToast(MinePersonalConfirmTelActivity.this, "验证成功");
                            btnConfirm.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(MinePersonalConfirmTelActivity.this,MinePersonalAlipayActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 400);
                        } else {
                            ToastUtils.showToast(MinePersonalConfirmTelActivity.this, response.getMsg() + "):" + response.getCode());
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
        isSend = false;
        mThread.interrupt();
        dismissDialog();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
