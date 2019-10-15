package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.yj.cosmetics.util.MD5Utils;
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

public class UserForgotActivity extends BaseActivity {
    @BindView(R.id.user_forgot_tv_tel)
    TextView tvTel;
    @BindView(R.id.user_forgot_et_tel)
    EditText etTel;
    @BindView(R.id.user_forgot_tv_verify)
    TextView tvVerify;
    @BindView(R.id.user_forgot_et_verify)
    EditText etVerify;
    @BindView(R.id.user_forgot_tv_newpwd)
    TextView tvNewPwd;
    @BindView(R.id.user_forgot_et_newpwd)
    EditText etNewPwd;
    @BindView(R.id.user_forgot_tv_newconfirm)
    TextView tvNewConfirm;
    @BindView(R.id.user_forgot_et_newconfirm)
    EditText etNewConfirm;
    @BindView(R.id.user_forgot_btn_confirm)
    Button btnConfirm;
    @BindView(R.id.user_forgot_tv_send)
    TextView tvSend;
    @BindView(R.id.title_view)
    View vLine;
    @BindView(R.id.title_layout)
    LinearLayout llTop;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;

    boolean isSend = false;
    boolean isNetError = false;
    CustomProgressDialog mDialog;

    final int TAG_SMS = 0x11;
    private SMSThread mThread;
    private int countTime = 60;

    private boolean isScroll = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what) {
                case TAG_SMS:
                    if (isNetError) {
                        isSend = false;
                        tvSend.setText("获取");
                    } else {
                        if (countTime == 60) {
                            tvSend.setText(String.valueOf(countTime) + "s");
                            countTime--;
                        } else if (countTime < 60 & countTime > 0) {
                            tvSend.setText(String.valueOf(countTime) + "s");
                            countTime--;
                        } else if (countTime == 0) {
                            //计时器已走完 验证码可以继续发送
                            isSend = false;
                            tvSend.setEnabled(true);
                            tvSend.setText("获取");
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_user_forgot;
    }

    @Override
    protected void initView() {
        setTitleText("找回密码");
    }

    @Override
    protected void initData() {
        mThread = new SMSThread();
        if (!mThread.isInterrupted()) {
            isSend = false;
        }
        mDialog = new CustomProgressDialog(this);
        initEtView();


      /*  etTel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollVertical(mScrollView.getHeight());
                return false;
            }
        });*/
        etNewPwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollVertical(mScrollView.getHeight());
                return false;
            }
        });
        etVerify.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollVertical(mScrollView.getHeight());
                return false;
            }
        });
        etNewConfirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollVertical(mScrollView.getHeight());
                return false;
            }
        });
    }
    @Override
    protected void showShadow1() {
//        vLine.setVisibility(View.GONE);
//        llTop.setBackgroundColor(getResources().getColor(R.color.CF7_F9_FA));
    }

    @OnClick({R.id.user_forgot_btn_confirm, R.id.user_forgot_tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_forgot_btn_confirm:
                if(TextUtils.isEmpty(etTel.getText())){
                    ToastUtils.showToast(UserForgotActivity.this,"请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(etVerify.getText())){
                    ToastUtils.showToast(UserForgotActivity.this,"请输入验证码");
                    return;
                }
                if(TextUtils.isEmpty(etNewPwd.getText())){
                    ToastUtils.showToast(UserForgotActivity.this,"请输入密码");
                    return;
                }
                if(TextUtils.isEmpty(etNewConfirm.getText())){
                    ToastUtils.showToast(UserForgotActivity.this,"请输入密码");
                    return;
                }
                if(etNewPwd.getText().toString().length() < 6){
                    ToastUtils.showToast(UserForgotActivity.this,"密码不小于6位");
                    return;
                }
                if(!etNewConfirm.getText().toString().trim().equals(etNewPwd.getText().toString().trim())){
                    ToastUtils.showToast(UserForgotActivity.this,"两次输入的密码不一致");
                    return;
                }
                if (!MatchUtils.isValidPhoneNumber(etTel.getText().toString().trim())) {
                    ToastUtils.showToast(this, "手机号格式不正确");
                    return;
                } else {
                    doAsyncModify(etTel.getText().toString(), etNewPwd.getText().toString(),
                            etVerify.getText
                                    ().toString());
                }
                break;
            case R.id.user_forgot_tv_send:
                String tel = etTel.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    ToastUtils.showToast(this, "请输入手机号");
                    return;
                }
                inValidate(tel);
                break;
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
                                ToastUtils.showToast(UserForgotActivity.this, "网络故障,请稍后再试");
                            }
                            LogUtils.i("我进入onError了" + e);

                            isSend = false;
                            isNetError = true;
    //                        mThread=null
                            //发送验证码按钮
                            tvSend.setText("获取");
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
                                ToastUtils.showToast(UserForgotActivity.this, "验证码已发送");
                                isNetError = false;
                            } else {
                                ToastUtils.showToast(UserForgotActivity.this, response.getMsg() + "):" + response.getCode());
                                isNetError = true;
                                isSend = false;
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAsyncModify(final String userName, final String passWord, String vertifyCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userPhone", userName);
        map.put("password", MD5Utils.MD5(passWord));
        map.put("code", vertifyCode);
        LogUtils.i("传输的值" + URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.ModifyPass).tag(this)
                .addParams(Key.data, URLBuilder.format(map))
                .build()
                .execute(new Utils.MyResultCallback<NormalEntity>() {

                    @Override
                    public void inProgress(float progress) {
                        super.inProgress(progress);
                        if (mDialog == null) {
                            mDialog = new CustomProgressDialog(UserForgotActivity.this);
                            if (!isFinishing()) {
                                mDialog.show();
                            }
                        } else {
                            if (!isFinishing()) {
                                mDialog.show();
                            }
                        }
                    }

                    //网络请求错误时进行的操作
                    @Override
                    public void onError(Call call, Exception e) {
                        super.onError(call, e);
                        dismissDialog();
                        if (call.isCanceled()) {
                            call.cancel();
                        }else{
                            ToastUtils.showToast(UserForgotActivity.this, "网络故障,请稍后再试");
                        }

                        LogUtils.i("异常" + e);
                    }

                    //对返回的数据进行json解析.
                    public NormalEntity parseNetworkResponse(Response response) throws Exception {
                        String json = response.body().string().trim();
                        LogUtils.i("json的值" + json);
                        return new Gson().fromJson(json, NormalEntity.class);
                    }

                    //请求成功后对返回的resoponseCode进行判断.
                    @Override
                    public void onResponse(NormalEntity response) {
                        if (isFinishing()) {
                            return;
                        }
                        dismissDialog();
                        if (response.getCode().equals(response.HTTP_OK)) {
                            ToastUtils.showToast(UserForgotActivity.this, "修改成功");
//                    saveInfo();
                            btnConfirm.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(UserForgotActivity.this, UserLoginActivity.class);
                                    intent.putExtra("tel", userName);
                                    intent.putExtra("pass", passWord);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 400);
                        } else {
                            ToastUtils.showToast(UserForgotActivity.this, response.getMsg() + "):" + response.getCode());
                        }
                    }
                });
    }
    private void inValidate(String tel) {
        if (!MatchUtils.isValidPhoneNumber(tel)) {
            ToastUtils.showToast(this, "请输入正确的手机号码");
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


    private void initEtView(){
        etTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    if(tvTel.getVisibility() == View.VISIBLE){
                        tvTel.setVisibility(View.GONE);
                    }
                }else{
                    tvTel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    if(tvNewPwd.getVisibility() == View.VISIBLE){
                        tvNewPwd.setVisibility(View.GONE);
                    }
                }else{
                    tvNewPwd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                int index = etNewPwd.getSelectionStart() - 1;
                if (index > 0) {
                    if (com.yj.cosmetics.util.TextUtils.isEmojiCharacter(s.charAt(index))) {
                        Editable edit = etNewPwd.getText();
                        edit.delete(s.length() - 2, s.length());
                        ToastUtils.showToast(UserForgotActivity.this,"不支持输入表情符号");
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
                if(s.length()>0){
                    if(tvNewConfirm.getVisibility() == View.VISIBLE){
                        tvNewConfirm.setVisibility(View.GONE);
                    }
                }else{
                    tvNewConfirm.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                int index = etNewConfirm.getSelectionStart() - 1;
                if (index > 0) {
                    if (com.yj.cosmetics.util.TextUtils.isEmojiCharacter(s.charAt(index))) {
                        Editable edit = etNewConfirm.getText();
                        edit.delete(s.length() - 2, s.length());
                        ToastUtils.showToast(UserForgotActivity.this,"不支持输入表情符号");
                    }
                }
            }
        });
        etVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    if(tvVerify.getVisibility() == View.VISIBLE){
                        tvVerify.setVisibility(View.GONE);
                    }
                }else{
                    tvVerify.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 使滚动条滚动至指定位置（垂直滚动）
     *
     * @param to 滚动到的位置
     */
    protected void scrollVertical(final int to) {
        if(!isScroll) {
            isScroll = true;
            mScrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LogUtils.i("弹出后的高度是"+mScrollView.getHeight());
                    mScrollView.scrollTo(0, to);
                    isScroll = false;
                }
            }, 500);
        }
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
        isSend = false;
        mThread.interrupt();
        dismissDialog();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
