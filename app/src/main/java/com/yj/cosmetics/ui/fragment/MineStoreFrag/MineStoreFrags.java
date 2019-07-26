package com.yj.cosmetics.ui.fragment.MineStoreFrag;

import android.view.View;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.base.LazyLoadFragment;
import com.yj.cosmetics.model.ShopDetailEntity;
import com.yj.cosmetics.widget.Dialog.StoreInfoDialogs;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Suo on 2016/12/12.
 */

public class MineStoreFrags extends LazyLoadFragment {

	private static MineStoreFrags fragment;
	@BindView(R.id.tv_stores_address)
	TextView tvAddress;
	@BindView(R.id.tv_store_services_title)
	TextView tvSerTitle;
	@BindView(R.id.tv_store_services_time)
	TextView tvSerTime;
	@BindView(R.id.tv_store_services_content)
	TextView tvSerContent;
	private ShopDetailEntity datas = null;


	public static MineStoreFrags instant(String shopId) {
		if (fragment == null) {
			fragment = new MineStoreFrags();
		}
		return fragment;
	}

	@Override
	protected int setContentView() {
		return R.layout.fragment_mine_stores;
	}

	@Override
	protected void initView() {

	}

	public void setData(ShopDetailEntity data) {
		this.datas = data;
		if (data != null) {
			tvAddress.setText(data.getData().getAreaAddress());
			tvSerTitle.setText("配送服务：" + data.getData().getShopService());
			tvSerTime.setText("配送时间: " + data.getData().getServiceTime());
			tvSerContent.setText("起送：￥ " + data.getData().getServiceStartime());
		}
	}

	@Override
	protected void initData() {
	}

	//@TODO　　
	@Override
	protected void lazyLoad() {

	}


	@OnClick(R.id.mine_personal_name)
	public void onViewClicked() {
		if (datas != null) {
			showDialog(datas);
		}
	}

	StoreInfoDialogs TicketDialog;

	public void showDialog(ShopDetailEntity datas) {
		if (TicketDialog == null) {
			TicketDialog = new StoreInfoDialogs(getContext());
		}

		TicketDialog.setCustomDialog(datas, 2);

		if (!TicketDialog.isShowing()) {
			TicketDialog.show();
		}

		TicketDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TicketDialog.dismiss();
			}
		});
	}



	private void dismissDialog() {
		if (TicketDialog != null) {
			TicketDialog.dismiss();
			TicketDialog = null;
		}
	}

	@Override
	public void onDestroy() {
		dismissDialog();
		super.onDestroy();
	}
}
