package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.util.GetInfoUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Suo on 2018/3/15.
 */

public class MineAboutActivity extends BaseActivity {

    @BindView(R.id.mine_about_tv_version)
    TextView tvVersion;

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_about;
    }

    @Override
    protected void initView() {
        setTitleText("关于我们");

    }

    @Override
    protected void initData() {
        tvVersion.setText(GetInfoUtils.getAPPVersion(this));
    }

    @OnClick({R.id.mine_about_rl_company,R.id.mine_about_rl_agreement})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.mine_about_rl_company:
                Intent intentCompany = new Intent(this,NormalWebViewActivity.class);
                intentCompany.putExtra("url", URLBuilder.URLBaseHeader+ URLBuilder.CompanyDes);
                intentCompany.putExtra("title","公司简介");
                startActivity(intentCompany);
                break;
            case R.id.mine_about_rl_agreement:
                Intent intentAgreement = new Intent(this,NormalWebViewActivity.class);
                intentAgreement.putExtra("url", URLBuilder.URLBaseHeader+ URLBuilder.Agreement);
                intentAgreement.putExtra("title","用户协议");
                startActivity(intentAgreement);
                break;
        }
    }
}
