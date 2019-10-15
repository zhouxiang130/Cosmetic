package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Suo on 2017/8/7.
 */

public class PayResultActivity extends BaseActivity {

    @BindView(R.id.pay_result_iv_state)
    ImageView ivState;
    @BindView(R.id.pay_result_tv_state)
    TextView tvState;
    @BindView(R.id.pay_result_tv1)
    TextView tv1;
    @BindView(R.id.pay_result_btn_order)
    Button btnOrder;
    @BindView(R.id.pay_result_btn_shopping)
    Button btnShopping;

    private String oid;

    CustomProgressDialog mDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initView() {
        setTitleText("支付成功");
        oid  = mUtils.getPayOrder();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.pay_result_btn_order,R.id.pay_result_btn_shopping})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.pay_result_btn_order:
                Intent intent;
                if(!TextUtils.isEmpty(mUtils.getPayType()) && mUtils.getPayType().equals("goods")) {
                    intent = new Intent(this,MineOrderDetailActivity.class);
                    intent.putExtra("oid",oid);
                }else{
                    intent = new Intent(this,MineOrderActivity.class);
                }
                startActivity(intent);
                finish();
                break;
            case R.id.pay_result_btn_shopping:
                Intent intentShop = new Intent(this, MainActivity.class);
                intentShop.putExtra("page","0");
                startActivity(intentShop);
                finish();
                break;
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
        dismissDialog();
    }
}
