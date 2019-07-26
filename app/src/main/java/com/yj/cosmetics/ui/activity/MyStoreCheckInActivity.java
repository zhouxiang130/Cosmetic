package com.yj.cosmetics.ui.activity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.R;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/6/17 0017.
 */

public class MyStoreCheckInActivity extends BaseActivity {


	@BindView(R.id.tv_number_plate)
	RoundedImageView tvNumberPlate;
	@BindView(R.id.tv_store_nickname)
	TextView tvStoreNickname;
	@BindView(R.id.user_regist_et_tel)
	EditText userRegistEtTel;

	@BindView(R.id.et_store_loc)
	EditText etStoreLoc;

	@BindView(R.id.et_store_detail_add)
	EditText etStoreDetailAdd;

	@BindView(R.id.et_business_licence)
	EditText etBusinessLicence;

	@BindView(R.id.et_store_detail)
	EditText etStoreDetail;


	@BindView(R.id.et_store_lead_official)
	EditText etStoreLeadOfficial;

	@BindView(R.id.et_lead_official_phone)
	EditText etLeadOfficialPhone;

	@BindView(R.id.et_ID_card)
	EditText etIDCard;
	@BindView(R.id.btn_register)
	Button btnRegister;

	@Override
	protected int getContentView() {
		return R.layout.activity_store_check_in;
	}

	@Override
	protected void initView() {
		setTitleText("店铺入驻");
	}

	@Override
	protected void initData() {

	}



	@OnClick(R.id.btn_register)
	public void onViewClicked() {
	}
}
