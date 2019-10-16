package com.yj.cosmetics.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
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

public class MinePersonalTelActivity extends BaseActivity {
    @BindView(R.id.mine_personal_tel_ettel)
    EditText etTel;
    @BindView(R.id.mine_personal_tel_verify)
    TextView tvSend;
    @BindView(R.id.mine_personal_tel_etverify)
    EditText etVerify;
    boolean isSend = false;
    boolean isNetError = false;
    CustomProgressDialog mDialog;
    final int TAG_SMS = 0x11;
    private SMSThread mThread;
    private int countTime = 60;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what) {
                case TAG_SMS:
                    if (isNetError) {
                        isSend = false;
                        tvSend.setText("获取验证码");
                    } else {
                        if (countTime == 60) {
                            tvSend.setText(String.valueOf(countTime)+"秒");
                            countTime--;
                        } else if (countTime < 60 & countTime > 0) {
                            tvSend.setText(String.valueOf(countTime)+"秒");
                            countTime--;
                        } else if (countTime == 0) {
                            //计时器已走完 验证码可以继续发送
                            isSend = false;
                            /*tvSendCode.setEnabled(true);*/
                            tvSend.setText("获取验证码");
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_personal_tel;
    }

    @Override
    protected void initView() {
        setTitleText("绑定手机号");
    }

    @Override
    protected void initData() {

        mThread = new SMSThread();
        if (!mThread.isInterrupted()) {
            isSend = false;
        }
        if (!TextUtils.isEmpty(mUtils.getTel())) {
            if(mUtils.getTel().length()>8) {
                etTel.setHint(mUtils.getTel().replace(mUtils.getTel().substring(4, 8), "****"));
            }else{
                etTel.setHint(mUtils.getTel());
            }
        }
        mDialog = new CustomProgressDialog(this);
    }

    @OnClick({R.id.mine_personal_tel_btn,R.id.mine_personal_tel_verify})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.mine_personal_tel_btn:
                if(TextUtils.isEmpty(etTel.getText().toString().trim())){
                    ToastUtils.showToast(MinePersonalTelActivity.this,"请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(etVerify.getText().toString().trim())){
                    ToastUtils.showToast(MinePersonalTelActivity.this,"请输入验证码");
                    return;
                }
                if(MatchUtils.isValidPhoneNumber(etTel.getText().toString().trim())){
                    //手机格式正确,做网络操作
                    if(mUtils.getUid()!= null){
                        doAsyncChange();
                    }
                }else{
                    //什么都不做
                    ToastUtils.showToast(this,"请输入正确的手机号");
                }

                break;
            case R.id.mine_personal_tel_verify:
                if(TextUtils.isEmpty(etTel.getText().toString().trim())){
                    ToastUtils.showToast(this,"请输入手机号");
                    return;
                }
                inValidate(etTel.getText().toString().trim());
                break;
        }
    }
    private void inValidate(String tel){
        if (!MatchUtils.isValidPhoneNumber(tel)) {
            ToastUtils.showToast(this, "请输入正确的手机号码");
        }else{
            //手机号格式正确
            if(isSend){
                return;
            }
            doAsyncSendMS(tel);
        }
    }

    private void doAsyncSendMS(String tel) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(Key.tel, tel);
//        map.put(Key.type,Key.forgot);
        LogUtils.i("传输的值" + URLBuilder.format(map));
        try {
            OkHttpUtils.post().url(URLBuilder.URLBaseHeader+ URLBuilder.SendMsg).tag(this)
    //                .addParams(Key.data, URLBuilder.format(map))
    //                .addParams("tel","18338764293")
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
                                ToastUtils.showToast(MinePersonalTelActivity.this, "网络故障,请稍后再试");
                            }
                            LogUtils.i("我进入onError了" + e);

                            isSend = false;
                            isNetError = true;
    //                        mThread=null
                            //发送验证码按钮
                            tvSend.setText("获取验证码");
                        }

                        //进行json解析.
                        @Override
                        public NormalEntity parseNetworkResponse(Response response) throws Exception {
                            String json = response.body().string().trim();
                            LogUtils.i("json的值"+json);
                            return new Gson().fromJson(json,NormalEntity.class);
                        }

                        //对返回结果进行判断.
                        @Override
                        public void onResponse(NormalEntity response) {
                            LogUtils.i("我onResponse了");
                            if (isFinishing()) {
                                return;
                            }
                            if (response.getCode().equals(response.HTTP_OK)) {
                                ToastUtils.showToast(MinePersonalTelActivity.this, "验证码已发送");
                                isNetError = false;
                            } else {
                                ToastUtils.showToast(MinePersonalTelActivity.this, response.getMsg() + "):" + response.getCode());
                                isNetError = true;
                                isSend = false;
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAsyncChange(){

        Map<String,String> map = new HashMap<>();
        map.put(Key.userId,mUtils.getUid());
        map.put(Key.tel,etTel.getText().toString().trim());
        map.put(Key.code,etVerify.getText().toString().trim());
        LogUtils.i("传输的值"+ URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader+"/phone/user/updatePhone").tag(this)
                .addParams(Key.data, URLBuilder.format(map))
                .build().execute(new Utils.MyResultCallback<NormalEntity>() {

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);
                if (mDialog == null) {
                    mDialog = new CustomProgressDialog(MinePersonalTelActivity.this);
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
                LogUtils.i("json的值"+json);
                return new Gson().fromJson(json,NormalEntity.class);
            }

            @Override
            public void onResponse(NormalEntity response) {
                if(isFinishing()){
                    return;
                }
                dismissDialog();
                if(response.getCode().equals(response.HTTP_OK)){
                    ToastUtils.showToast(MinePersonalTelActivity.this,"手机绑定成功");
                    mUtils.saveTel(etTel.getText().toString().trim());
                    etTel.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },400);
                }else{
                    ToastUtils.showToast(MinePersonalTelActivity.this,"请求失败:)"+response.getMsg());
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                LogUtils.i("网络请求错误"+e);
                dismissDialog();
                if(call.isCanceled()){
                    call.cancel();
                }else{
                    ToastUtils.showToast(MinePersonalTelActivity.this,"网络请求失败,请稍后再试");
                }

            }
        });
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
