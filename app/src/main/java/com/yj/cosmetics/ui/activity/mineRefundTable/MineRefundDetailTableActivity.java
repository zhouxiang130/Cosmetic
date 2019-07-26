package com.yj.cosmetics.ui.activity.mineRefundTable;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.listener.JudgeCompressListener;
import com.yj.cosmetics.model.JudgeGoodsData;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.model.RefundDetailEntity;
import com.yj.cosmetics.model.RefundListEntity;
import com.yj.cosmetics.ui.adapter.MineRefundDetailAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.MatchUtils;
import com.yj.cosmetics.util.OkhttpFileUtils;
import com.yj.cosmetics.util.PermissionUtils;
import com.yj.cosmetics.util.PicUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.util.luban.Luban;
import com.yj.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.yj.cosmetics.widget.JsonUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.valuesfeng.picker.utils.PicturePickerUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.yj.cosmetics.util.PermissionUtils.REQUEST_CODE_WRITE_EXTRONAL_STORAGE;

/**
 * Created by Administrator on 2018/6/13 0013.
 *
 * @TODO 退款填写界面 2.0 添加页面
 */

public class MineRefundDetailTableActivity extends BaseActivity {


	private static final String TAG = "MineRefundDetailTableActivity";

	@BindView(R.id.recyclerView)
	RecyclerView mRecyclerView;

	//	@BindView(R.id.refund_tv_commit)
//	TextView tvCommit;
	@BindView(R.id.ll_view)
	LinearLayout llView;
	@BindView(R.id.title_rl_next)
	RelativeLayout rlNext;
	@BindView(R.id.title_tv_next)
	TextView tvNext;


	List<RefundDetailEntity.RefundDetailItem> entity;

	private static int refund_detial_tag, REFUND_DETIAL_TAG = 2;
	//		"未收到货","已收到货"
	private List<String> refund_dialog_desc;//= new String[]{"未收到货", "已收到货"};


	private String[] refund_dialog_desc_ = new String[]{"退款", "颜色/图案/款式与商品描述不符合", "功能/效果与商品描述不符合", "其他问题"};

	private MineRefundDetailAdapter mAdapter;
	List<JudgeGoodsData> mScaledData;
	private MineRefundDetailAdapter.JudgeGoodsViewHolder holder;
	private CustomNormalContentDialog infoDialog;
	private CustomProgressDialog mDialog;
	private int count = 0;
	public int goodStatus, takePosition, position;
	private String reason, price, phone, Money, flag, cause;
	private String orderId;
	private List<RefundListEntity.DataBean> data;
	//屏幕高度
	private int screenHeight = 0;
	//软件盘弹起后所占高度阀值
	private int keyHeight = 0;
	private int height;

	@Override
	protected int getContentView() {
		return R.layout.activity_mine_refund_detail_table;
	}

	@Override
	protected void initView() {
		entity = new ArrayList<>();
		mScaledData = new ArrayList<>();
		refund_dialog_desc = new ArrayList<>();
		refund_dialog_desc.add("未收到货");
		refund_dialog_desc.add("已收到货");
		setTitleText("申请退款");
		rlNext.setVisibility(View.VISIBLE);
		tvNext.setText("提交");

		refund_detial_tag = Integer.parseInt(getIntent().getStringExtra("REFUND_DETIAL_TAG"));//@TODO　（1：仅退款2：退货并退款）
		Money = getIntent().getStringExtra("Money");
		orderId = getIntent().getStringExtra("orderId");
		flag = getIntent().getStringExtra("flag");
		Log.i(TAG, "initView:orderId>>>>>>> " + orderId);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mAdapter = new MineRefundDetailAdapter(this, entity, refund_detial_tag, refund_dialog_desc, data, Money);
		mRecyclerView.setAdapter(mAdapter);

		//获取屏幕高度
//		screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
		//阀值设置为屏幕高度的1/3
//		keyHeight = screenHeight / 3;
//		llView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//			@Override
//			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//				height = tvCommit.getHeight();
//				if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
//					if (height != 0) {
//						tvCommit.setHeight(0);
//					}
//				} else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
//					if (height == 0) {
//						tvCommit.setHeight(140);
//					}
//				}
//			}
//		});
	}


	private void setData(RefundDetailEntity refundDetailEntity) {
		entity.addAll(refundDetailEntity.getData());
		for (int i = 0; i < entity.size(); i++) {
			this.entity.get(i).setmSelected(new ArrayList());
			this.entity.get(i).setmImage(new ArrayList());
		}
		mAdapter.notifyDataSetChanged();
		doAsyncGetRefundList();
	}


	@Override
	protected void initData() {
		String json = JsonUtils.getJson("find.txt", this);
		Log.i(TAG, "initData: " + json);
		RefundDetailEntity refundDetailEntity = new Gson().fromJson(json, RefundDetailEntity.class);
		setData(refundDetailEntity);
	}


	public void setPosition(int position) {
		this.position = position;
	}

	public void setHolder(MineRefundDetailAdapter.JudgeGoodsViewHolder holder) {
		this.holder = holder;
	}


	public void isWritePermissionAllowed() {
		if (PermissionUtils.isPermissionAllowed(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			//为true 表示有权限
			mAdapter.takePicture(takePosition, holder);
		} else {
			//没有相关权限
			if (PermissionUtils.isRemindAllowed(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_WRITE_EXTRONAL_STORAGE)) {
				//是否能弹窗提示
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTRONAL_STORAGE);
			} else {
				//弹窗提示已被关闭
				showDialog("为了您的正常使用,请允许相应权限");
				ToastUtils.showToast(this, "您已关闭读写功能,相关功能将不可用.");
			}
		}
	}


	private void doAsyncGetRefundList() {
		Map<String, String> map = new HashMap<>();
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/searchRefund.act")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<RefundListEntity>() {
			@Override
			public RefundListEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetRefundList -- json的值" + json);
				return new Gson().fromJson(json, RefundListEntity.class);
			}

			@Override
			public void onResponse(RefundListEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null && response.getData().size() > 0) {
						data = response.getData();
						mAdapter.setData(data);
						mAdapter.notifyDataSetChanged();
					}
				} else {
					LogUtils.i("我挂了" + response.getMsg());
				}

			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("doAsyncGetRefundList ---- 我故障了--" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}

			}
		});
	}


	private void showDialog(String text) {
		if (infoDialog == null) {
			infoDialog = new CustomNormalContentDialog(this);
		}
		if (!infoDialog.isShowing()) {
			infoDialog.show();
		}
		infoDialog.getTvTitle().setText(text);
		infoDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.i("我confirm了");
				PermissionUtils.getAppDetailSettingIntent(MineRefundDetailTableActivity.this);
				infoDialog.dismiss();
			}
		});
		infoDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.i("我cancel了");
				PermissionUtils.getAppDetailSettingIntent(MineRefundDetailTableActivity.this);
				infoDialog.dismiss();
			}
		});
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mAdapter.setPicker(true);
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case 200:
				LogUtils.i("我在ActivityResult中");
				if (resultCode == Activity.RESULT_OK) {
					LogUtils.i("我在resultOk中");
					LogUtils.i(data.getExtras().toString());
					List<Uri> mList = PicturePickerUtils.obtainResult(data);
					entity.get(position).getmSelected().addAll(mList);
					LogUtils.i("长度" + entity.get(position).getmSelected().size());
					switch (requestCode) {
						case 200:
//                            path = PicUtils.getPicPath(JudgeShowActivity.this, mSelected.get(0));
//                            showImg();
							mAdapter.glideImg(position, holder);
							break;
					}
				}
				break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		rlNext.setEnabled(true);
	}

	@OnClick({R.id.title_rl_next})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.title_rl_next:

				goodStatus = entity.get(position).getGoodStatus();
				cause = entity.get(position).getCause();
				if (refund_detial_tag != 2) {
					if (goodStatus == 0) {
						Toast.makeText(this, "请选择货物状态", Toast.LENGTH_SHORT).show();
						return;
					}
				}

				if (cause.equals("0")) {
					Toast.makeText(this, "请选择退款类型", Toast.LENGTH_SHORT).show();
					return;
				}

				Log.i(TAG, "onViewClicked: productState -- : " + goodStatus + " handlingWay : " + cause + " 价钱 : " + price + " 理由: " + reason + " 电话号码: " + phone);

				price = entity.get(position).getPrice();
				if (price.equals("")) {

					Toast.makeText(this, "请输入退款价格", Toast.LENGTH_SHORT).show();
					return;
				}
				Log.i(TAG, "onViewClicked: productState -- : " + goodStatus + " handlingWay : " + cause + " 价钱 : " + price + " 理由: " + reason + " 电话号码: " + phone);
				reason = entity.get(position).getReason();
				phone = entity.get(position).getPhone();
				if (phone.equals("")) {
					Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT).show();
					return;
				}
				Log.i(TAG, "onViewClicked: productState -- : " + goodStatus + " handlingWay : " + cause + " 价钱 : " + price + " 理由: " + reason + " 电话号码: " + phone);
				if (!MatchUtils.isValidPhoneNumber(phone)) {
					ToastUtils.showToast(this, "请输入正确的手机号码");
					return;
				}


				Log.i(TAG, "onViewClicked: productState -- : " + goodStatus + " handlingWay : " + cause + " 价钱 : " + price + " 理由: " + reason + " 电话号码: " + phone);

				int size = 0;
				for (int i = 0; i < entity.size(); i++) {
					size += entity.get(i).getmSelected().size();
					LogUtils.i("size的值" + size);
				}
				if (size == 0) {
					LogUtils.i("我进入到没传图片了--------------------------------------------------------------");
					doAsyncPost(goodStatus, cause, price, reason, phone);
					return;
				}


				//压缩图片
				for (int i = 0; i < entity.size(); i++) {
					entity.get(i).getmImage().clear();
					for (int x = 0; x < entity.get(i).getmSelected().size(); x++) {
						count++;
						String path = PicUtils.getPicPath(MineRefundDetailTableActivity.this, entity.get(i).getmSelected().get(x));
						JudgeGoodsData data = new JudgeGoodsData();
						data.setPosition(i);
						data.setImgPosition(x);
						data.setImg(new File(path));
						LogUtils.i("path的值" + path);
						Luban.get(MineRefundDetailTableActivity.this)
								.load(new File(path))
								.putGear(Luban.THIRD_GEAR)
								.setJudgeCompressListener(new JudgeCompressListener() {
									@Override
									public void onStart() {
									}

									@Override
									public void onSuccess(File file) {
									}

									@Override
									public void onError(Throwable e) {
										LogUtils.i("我鲁班onError了" + e);
										rlNext.setEnabled(true);
										ToastUtils.showToast(MineRefundDetailTableActivity.this, "图片已损坏,请重新选取");
									}

									@Override
									public void onJudgeSuccess(JudgeGoodsData data) {
										LogUtils.i("data的position" + data.getPosition());
										LogUtils.i("data的imgPosition" + data.getImgPosition());
										LogUtils.i("file的值" + data.getScaledImg().getAbsolutePath());
										mScaledData.add(data);
										if (mScaledData.size() == count) {
											Collections.sort(mScaledData);
											for (int i = 0; i < mScaledData.size(); i++) {
												LogUtils.i("position的值" + mScaledData.get(i).getPosition() + ".....imgPosition的值" + mScaledData.get(i).getImgPosition() + "...img" + mScaledData.get(i).getScaledImg());
												entity.get(mScaledData.get(i).getPosition()).getmImage().add(mScaledData.get(i).getScaledImg());
											}
											count = 0;
											mScaledData.clear();
											rlNext.setEnabled(false);
											doAsyncPost(goodStatus, cause, price, reason, phone);
										}
									}
								}).launchJudge(data);
					}
				}
				break;
		}
	}


	private void doAsyncPost(int productState, String handlingWay, String returnMoney, String returnReason, String phone) {
		Map<String, ArrayList<File>> imgMap = new HashMap<>();
		Map<String, String> map = new HashMap<>();

		map.put("userId", mUtils.getUid());
		map.put("returnType", refund_detial_tag + "");
		if (refund_detial_tag == 1) {//@TODO 仅退款
			map.put("productState", productState + "");
		}
		if (handlingWay.equals("0")) {
			map.put("returnContent", "8");
		} else {
			map.put("returnContent", handlingWay);
		}
//		map.put("returnContent", "0");

		map.put("returnMoney", returnMoney);
		map.put("returnReason", returnReason);
		map.put("phone", phone);
		map.put("orderId", orderId);

		LogUtils.i("传输的值" + URLBuilder.format(map));

		RequestCall params = null;
		for (int i = entity.size() - 1; i >= 0; i--) {
			if (entity.get(i).getmImage() != null && entity.get(i).getmImage().size() > 0) {
				if (entity.get(i).getmImage() != null && entity.get(i).getmImage().size() > 0) {
					LogUtils.i("我开始添加图片了=====" + entity.get(i).getPid() + "图片的长度是===" + entity.get(i).getmImage());
					imgMap.put(entity.get(i).getDetailId(), entity.get(i).getmImage());
				}
			}
		}

		for (int i = 0; i < imgMap.size(); i++) {
		   /* for ( int x = 0;x<imgMap.get(i).size();x++) {
		        LogUtils.i("imagMap的值=====" + imgMap.get(i).get(x).getAbsolutePath()+"===========name是=="+imgMap.get(i).get(x).getName());
            }*/
			LogUtils.i("imagemap的长度==" + imgMap.size());
		}


		LogUtils.i("im");
		params = OkhttpFileUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/orderReturn.act")
				.addParams("data", URLBuilder.format(map))
//                .addJudgeFiles(imgMap)
				.addJudgeFiles("files", imgMap)
				.tag(this).build();

		params.execute(new Utils.MyResultCallback<NormalEntity>() {
			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(MineRefundDetailTableActivity.this);
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
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					ToastUtils.showToast(MineRefundDetailTableActivity.this, "提交成功");
//					resultIntent.putExtra("refresh", "refresh");

					Intent intent = new Intent();
					intent.setAction("com.yj.robust.receiver_one");
					intent.putExtra("flag", flag);
					MineRefundDetailTableActivity.this.sendBroadcast(intent);


					rlNext.postDelayed(new Runnable() {
						@Override
						public void run() {
							finish();
						}
					}, 400);
				} else {
					LogUtils.i("异常 :)" + response.getMsg());
					ToastUtils.showToast(MineRefundDetailTableActivity.this, "网络故障 " + response.getMsg());
				}
				dismissDialog();
				rlNext.setEnabled(true);
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				dismissDialog();
				if (call.isCanceled()) {
					call.cancel();
				} else {
					LogUtils.i("故障" + e);
					ToastUtils.showToast(MineRefundDetailTableActivity.this, "网络故障,请稍后再试");
				}
				rlNext.setEnabled(true);
			}
		});
	}

	private void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

	private void dismissDialog2() {
		if (infoDialog != null) {
			infoDialog.dismiss();
			infoDialog = null;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		OkhttpFileUtils.getInstance().cancelTag(this);
		dismissDialog();
		dismissDialog2();
	}
}
