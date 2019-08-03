package com.yj.cosmetics.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yj.cosmetics.MyApplication;
import com.yj.cosmetics.base.Constant;
import com.yj.cosmetics.ui.activity.MineOrderActivity;
import com.yj.cosmetics.ui.activity.MineOrderDetailActivity;
import com.yj.cosmetics.ui.activity.PayResultActivity;
import com.yj.cosmetics.ui.activity.SettlementCartActivity;
import com.yj.cosmetics.ui.activity.SettlementGoodsActivity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.UserUtils;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private UserUtils mUtils;

    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
//		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		LogUtils.e("type的值"+resp.getType());
		LogUtils.e("PAYBYWX的值"+ConstantsAPI.COMMAND_PAY_BY_WX);
		LogUtils.e("resp的errCode的值"+resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode == 0){
				LogUtils.i("我进入errCode == 0 了"+resp.errCode);
				MyApplication.orderListRefresh = true;
				//支付成功,发送成功广播进行后续处理
				if(MyApplication.orderlistReceiver) {
					Intent broadcastIntent = new Intent();
					broadcastIntent.setAction("com.example.orderlistrefresh");
					broadcastIntent.putExtra("state","success");
					sendBroadcast(broadcastIntent);
				}

				Intent intent = new Intent(WXPayEntryActivity.this, PayResultActivity.class);
				startActivity(intent);
				finishAty();
			}else{
				//非详情及列表的失败 判断是否有相应的activity进行finish 好进行下一步跳转   但详情和列表失败后不需要做任何操作 但详情和列表支付
//				成功后无此限制,因为要跳转到新的支付结果详情里 要先删除后添加
				if(MyApplication.orderDetial) {
					finishAty();
				}
				//支付失败 需要发送失败广播进行注销
				if(MyApplication.orderlistReceiver) {
					Intent broadcastIntent = new Intent();
					broadcastIntent.setAction("com.example.orderlistrefresh");
					broadcastIntent.putExtra("state","fail");
					sendBroadcast(broadcastIntent);
				}
				if(MyApplication.orderDetial) {
					mUtils = UserUtils.getInstance(WXPayEntryActivity.this);
					if(!TextUtils.isEmpty(mUtils.getPayType()) && mUtils.getPayType().equals("goods")){
						Intent orderIntent = new Intent(WXPayEntryActivity.this, MineOrderDetailActivity.class);
						orderIntent.putExtra("oid", mUtils.getPayOrder());
						startActivity(orderIntent);
					}else{
						Intent orderIntent = new Intent(WXPayEntryActivity.this, MineOrderActivity.class);
						startActivity(orderIntent);
					}

					LogUtils.e("我是错误code" + resp.errCode);
					ToastUtils.showToast(WXPayEntryActivity.this, "微信支付失败");
				}
			}
			MyApplication.orderDetial = true;
			MyApplication.orderlistReceiver  = false;

		}
		LogUtils.i("我finish了");
		finish();
	}

	private void finishAty(){
		for(int i = 0;i< MyApplication.atyStack.size();i++){
			if( MyApplication.atyStack.get(i) instanceof SettlementCartActivity|| MyApplication.atyStack.get(i) instanceof SettlementGoodsActivity || MyApplication.atyStack.get(i) instanceof MineOrderDetailActivity
					){
				//这些activity还存在
				LogUtils.i("我remove了");
				MyApplication.atyStack.get(i).finish();
				break;
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtils.i("w onDestory了");
	}
}