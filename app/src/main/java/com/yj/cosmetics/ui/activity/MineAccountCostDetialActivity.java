package com.yj.cosmetics.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.CostDetailEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2017/7/31.
 */

public class MineAccountCostDetialActivity extends BaseActivity {
    @BindView(R.id.pill_detial_success)
    TextView tvSuccess;
    @BindView(R.id.pill_detial_money)
    TextView tvMoney;
    @BindView(R.id.pill_detial_desc)
    TextView tvDesc;
    @BindView(R.id.pill_detial_account)
    TextView tvAccount;
    @BindView(R.id.pill_detial_type)
    TextView tvType;
    @BindView(R.id.pill_detial_create_time)
    TextView tvCreateTime;
    @BindView(R.id.pill_detial_solve_time)
    TextView tvSolveTime;
    @BindView(R.id.pill_detial_order_num)
    TextView tvOrderNum;
    @BindView(R.id.pill_detial_solve_rl)
    RelativeLayout rlSolve;
    @BindView(R.id.pill_detial_order_rl)
    RelativeLayout rlOrder;
    @BindView(R.id.pill_detial_iv)
    ImageView ivState;
    @BindView(R.id.pill_detial_fail)
    RelativeLayout rlFail;
    @BindView(R.id.pill_detial_fail_reason)
    TextView tvReason;
    @BindView(R.id.pill_detial_tv_state)
    TextView tvState;
    @BindView(R.id.pill_detial_rl_account)
    RelativeLayout rlAccount;
    @BindView(R.id.pill_detial_rl_type)
    RelativeLayout rlType;
    private String cashmoneyId;
    private String flag;
    private String consumeId;

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_account_cost_detail;
    }

    @Override
    protected void initView() {
        setTitleText("账单详情");
    }

    @Override
    protected void initData() {
        cashmoneyId = getIntent().getStringExtra("cashmoneyId");
        flag = getIntent().getStringExtra("flag");
        consumeId = getIntent().getStringExtra("consumeId");
        if(flag.equals("1")) {
            doAsyncGetWithdraw();
        }else{
            doAsyncGetConsume();
        }
    }

    private void doAsyncGetWithdraw() {
        Map<String, String> map = new HashMap<>();
        map.put("cashmoneyId", cashmoneyId);
        LogUtils.i("传输的值" + URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/backMoneyDetails").tag(this)
                .addParams(Key.data, URLBuilder.format(map))
                .build().execute(new Utils.MyResultCallback<CostDetailEntity>() {

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);

                if (call.isCanceled()) {
                    call.cancel();
                } else {
                    ToastUtils.showToast(MineAccountCostDetialActivity.this, "网络故障,请稍后再试");
                }

            }

            @Override
            public CostDetailEntity parseNetworkResponse(Response response) throws Exception {
                String json = response.body().string().trim();
                LogUtils.i("json的值" + json);
                return new Gson().fromJson(json, CostDetailEntity.class);
            }

            @Override
            public void onResponse(CostDetailEntity response) {
                if (response != null && response.HTTP_OK.equals(response.getCode())) {
                    setData(response.getData());
                } else {
                    ToastUtils.showToast(MineAccountCostDetialActivity.this, "故障" + response.getMsg());
                }
            }
        });
    }
    private void setData(CostDetailEntity.CostDetialData data) {
        String account = data.getAlipay().split(" ")[0];
        if(account.length()>=6) {
            tvAccount.setText(account.replace(account.substring(countAlilpay(account),
                    countAlilpay(account)+4), "****")+ "(*" +data.getAlipayName().substring(1,data.getAlipayName().length()) + ")");
        }else{
            tvAccount.setText(account + "(*" +data.getAlipayName().substring(1,data.getAlipayName().length()) + ")");
        }
        tvSuccess.setText("*"+data.getAlipayName().substring(1,data.getAlipayName().length()));
        tvMoney.setText("-"+data.getCashmoney());
        tvState.setText(data.getCashState());
        tvDesc.setText(data.getName());
        if(TextUtils.isEmpty(data.getReason())){
            rlFail.setVisibility(View.GONE);
        }else{
            rlFail.setVisibility(View.VISIBLE);
            tvReason.setText(data.getReason());
        }
        tvType.setText(data.getName());
        tvCreateTime.setText(data.getInsertTime());
        rlOrder.setVisibility(View.GONE);
    }
    private void doAsyncGetConsume() {
        Map<String, String> map = new HashMap<>();
        map.put("consumeId", consumeId);
        LogUtils.i("传输的值" + URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/usersMoneyConsumeDetails").tag(this)
                .addParams(Key.data, URLBuilder.format(map))
                .build().execute(new Utils.MyResultCallback<CostDetailEntity>() {

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                if (call.isCanceled()) {
                    call.cancel();
                } else {
                    ToastUtils.showToast(MineAccountCostDetialActivity.this, "网络故障,请稍后再试");
                }

            }

            @Override
            public CostDetailEntity parseNetworkResponse(Response response) throws Exception {
                String json = response.body().string().trim();
                LogUtils.i("json的值" + json);
                return new Gson().fromJson(json, CostDetailEntity.class);
            }

            @Override
            public void onResponse(CostDetailEntity response) {
                if (response != null && response.HTTP_OK.equals(response.getCode())) {
                    setConsumeData(response.getData());
                } else {
                    ToastUtils.showToast(MineAccountCostDetialActivity.this, "故障" + response.getMsg());
                }
            }
        });
    }

    private void setConsumeData(CostDetailEntity.CostDetialData data) {
        ivState.setVisibility(View.GONE);
        tvMoney.setText("-"+data.getConsumeMoney());
        tvState.setText(data.getConsumeState());
        tvDesc.setText(data.getName());
//        tvType.setText(data.getName());
        rlType.setVisibility(View.GONE);
        tvCreateTime.setText(data.getInsertTime());
        tvSuccess.setText("*"+data.getAlipayName().substring(0,data.getAlipayName().length()));
//        tvAccount.setText("robust官方平台");
        rlAccount.setVisibility(View.GONE);
        if(TextUtils.isEmpty(data.getReason())){
            rlFail.setVisibility(View.GONE);
        }else{
            rlFail.setVisibility(View.VISIBLE);
            tvReason.setText(data.getReason());
        }


        if(!TextUtils.isEmpty(data.getOrderNum())){
            rlOrder.setVisibility(View.VISIBLE);
            tvOrderNum.setText(data.getOrderNum());
        }else{
            rlOrder.setVisibility(View.GONE);
        }
    }
    private int countAlilpay(String alipay){
        if(alipay.length()%2 == 0){
            //长度为偶
            return (alipay.length() -4)/2 +1;
        }else{
            //长度为奇数
            return (alipay.length() - 3)/2 +1;
        }
    }

    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }

}
