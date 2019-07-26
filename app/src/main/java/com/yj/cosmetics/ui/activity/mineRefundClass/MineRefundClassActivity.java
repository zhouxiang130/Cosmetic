package com.yj.cosmetics.ui.activity.mineRefundClass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.OrderDetailEntity;
import com.yj.cosmetics.ui.activity.mineRefundTable.MineRefundDetailTableActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/13 0013.
 *
 * @TODO 选择服务类型 2.0 版本添加页面
 */

public class MineRefundClassActivity extends BaseActivity {


	private static final String TAG = "MineRefundClassActivity";
	@BindView(R.id.refund_rl_tuikuantuihuo)
	RelativeLayout rlRefundClassGoodInfo;
	@BindView(R.id.refund_rl_jintuikuan)
	RelativeLayout refundRlJintuikuan;
	private OrderDetailEntity.OrderDetialData.OrderDetialItem mList;

	@BindView(R.id.item_order_goods_iv)
	ImageView ivGoods;
	@BindView(R.id.item_order_goods_tv_title)
	TextView tvTitle;
	@BindView(R.id.item_order_goods_tv_style)
	TextView tvStyle;
	@BindView(R.id.item_order_goods_tv_price)
	TextView tvPrice;
	@BindView(R.id.item_order_goods_tv_number)
	TextView tvNum;
	private String orderId, Money, flag;

	private DynamicReceiver dynamicReceiver;

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_refund_class;
	}

	@Override
	protected void initView() {
		setTitleText("选择服务类型");
		mList = getIntent().getParcelableExtra("mList");
		orderId = getIntent().getStringExtra("orderId");
		Money = getIntent().getStringExtra("Money");
		flag = getIntent().getStringExtra("flag");
		Log.d(TAG, " mList.getProductId() ->" + orderId);

		//实例化IntentFilter对象
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.yj.robust.receiver_one");
		dynamicReceiver = new DynamicReceiver();
		//注册广播接收
		registerReceiver(dynamicReceiver, filter);


		tvTitle.setText(mList.getProductName());
		tvStyle.setText(mList.getSkuPropertiesName());
		tvNum.setText("X" + mList.getDetailNum());
		tvPrice.setText(mList.getMoney());
		Glide.with(MineRefundClassActivity.this)
				.load(URLBuilder.getUrl( mList.getProductListimg()))
				.error(R.mipmap.default_goods)
				.centerCrop()
				.into(ivGoods);

	}

	@Override
	protected void initData() {

	}

	//通过继承 BroadcastReceiver建立动态广播接收器
	class DynamicReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	}


	@OnClick({R.id.refund_rl_jintuikuan, R.id.refund_rl_tuikuantuihuo})
	public void onViewClicked(View view) {
		Intent intent = new Intent(MineRefundClassActivity.this, MineRefundDetailTableActivity.class);
		switch (view.getId()) {

			case R.id.refund_rl_jintuikuan:
				intent.putExtra("REFUND_DETIAL_TAG", "1");
				intent.putExtra("orderId", orderId);
				intent.putExtra("Money", Money);
				intent.putExtra("flag", flag);
				break;

			case R.id.refund_rl_tuikuantuihuo:
				intent.putExtra("REFUND_DETIAL_TAG", "2");
				intent.putExtra("orderId", orderId);
				intent.putExtra("Money", Money);
				intent.putExtra("flag", flag);
				break;

		}
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(dynamicReceiver);
	}
}
