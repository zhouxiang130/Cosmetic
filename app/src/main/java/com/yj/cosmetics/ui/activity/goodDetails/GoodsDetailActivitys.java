package com.yj.cosmetics.ui.activity.goodDetails;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import com.sobot.chat.api.model.Information;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;

import java.util.ArrayList;

/**
 * Created by Suo on 2018/3/16.
 *
 * @TODO 商品详情界面(优化====)
 * @TODO 需要优化的为： 部分手机在查看商品详情的情况下 会出现卡顿现象
 */

public class GoodsDetailActivitys extends BaseActivity implements GoodDetails_contract.View {


	private Information userInfo;
	private ArrayList<Object> mTitleList;
	private String productId, sproductId;
	private CustomProgressDialog loadingDialog;

	private GoodDetails_contract.Presenter presenter = new GoodDetails_presenter(this);


	@Override
	protected int getContentView() {
		return R.layout.activity_goods_details;
	}

	@Override
	protected void initView() {
		userInfo = new Information();
		mTitleList = new ArrayList<>();
		productId = getIntent().getStringExtra("productId");
		sproductId = getIntent().getStringExtra("sproductId");
		transTitle();
	}

	@Override
	protected void initData() {
		presenter.doAsyncGetDetial(mUtils,productId,sproductId);
	}


	@TargetApi(21)
	private void transTitle() {
		if (Build.VERSION.SDK_INT >= 21) {
			View decorView = getWindow().getDecorView();
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			decorView.setSystemUiVisibility(option);
			getWindow().setStatusBarColor(Color.TRANSPARENT);
		}
	}

	@Override
	public void isAsynGetDetailBefore() {
		if (loadingDialog == null) {
			loadingDialog = new CustomProgressDialog(GoodsDetailActivitys.this);
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
	public void setData() {

	}


	@Override
	public void dismissDialog() {
		if (loadingDialog != null) {
			loadingDialog.dismiss();
			loadingDialog = null;
		}
	}
}
