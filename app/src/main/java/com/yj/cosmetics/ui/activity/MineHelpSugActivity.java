package com.yj.cosmetics.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.base.Variables;
import com.yj.cosmetics.listener.JudgeCompressListener;
import com.yj.cosmetics.model.HelpSugEntity;
import com.yj.cosmetics.model.JudgeGoodsData;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.ui.adapter.MineHelpSugAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.OkhttpFileUtils;
import com.yj.cosmetics.util.PermissionUtils;
import com.yj.cosmetics.util.PicUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.util.luban.Luban;
import com.yj.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.yj.cosmetics.widget.JsonUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.valuesfeng.picker.utils.PicturePickerUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.yj.cosmetics.util.PermissionUtils.REQUEST_CODE_WRITE_EXTRONAL_STORAGE;


/**
 * Created by Suo on 2017/9/12.
 */

public class MineHelpSugActivity extends BaseActivity {
	@BindView(R.id.recyclerView)
	RecyclerView mRecyclerView;
	@BindView(R.id.title_rl_next)
	RelativeLayout rlPost;
	@BindView(R.id.title_tv_next)
	TextView tvPost;
	@BindView(R.id.title_view)
	View vLine;
	@BindView(R.id.title_layout)
	LinearLayout llTop;
	List<HelpSugEntity.JudgeGoodsItem> entity;
	private int position;
	private MineHelpSugAdapter.JudgeGoodsViewHolder holder;
	public int takePosition;
	private CustomProgressDialog mDialog;
	private Intent resultIntent;
	MineHelpSugAdapter mAdapter;
	private int count = 0;
	List<JudgeGoodsData> mScaledData = new ArrayList<>();
	private CustomNormalContentDialog infoDialog;

	@Override
	protected int getContentView() {
		return R.layout.activity_post_judge_goods;
	}

	@Override
	protected void initView() {
		entity = new ArrayList<>();
		setTitleText("意见反馈");
		rlPost.setVisibility(View.VISIBLE);
		tvPost.setText("提交");
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mAdapter = new MineHelpSugAdapter(this, entity);
		mRecyclerView.setAdapter(mAdapter);
		resultIntent = new Intent();
		setResult(Variables.REFRESH_ORDER_LIST, resultIntent);
	}

	@Override
	protected void initData() {
		String json = JsonUtils.getJson("find.txt", this);
		HelpSugEntity helpSugEntity = new Gson().fromJson(json, HelpSugEntity.class);
		setData(helpSugEntity);
	}

	private void setData(HelpSugEntity helpSugEntity) {
		entity.addAll(helpSugEntity.getData());
		for (int i = 0; i < entity.size(); i++) {
			this.entity.get(i).setmSelected(new ArrayList());
			this.entity.get(i).setmImage(new ArrayList());
		}

		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void showShadow1() {
//		vLine.setVisibility(View.GONE);
//		llTop.setBackgroundColor(getResources().getColor(R.color.CF7_F9_FA));
	}

	@Override
	protected void onResume() {
		super.onResume();
		rlPost.setEnabled(true);
	}

	@OnClick({R.id.title_rl_next})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.title_rl_next:

//				rlPost.setEnabled(false);
				if (entity.get(position).getJudgeType() == 0) {
					Toast.makeText(this, "请选择反馈类型", Toast.LENGTH_SHORT).show();
					break;
				}

				if (TextUtils.isEmpty(entity.get(position).getJudgeContent())) {
					Toast.makeText(this, "请输入反馈内容", Toast.LENGTH_SHORT).show();
					return;
				}

//				rlPost.setEnabled(true);

				int size = 0;
				for (int i = 0; i < entity.size(); i++) {
					size += entity.get(i).getmSelected().size();
					LogUtils.i("size的值" + size);
				}
				if (size == 0) {
					LogUtils.i("我进入到没传图片了--------------------------------------------------------------");
					doAsyncPost(entity.get(position).getJudgeType(), entity.get(position).getJudgeContent());
					return;
				}
				//压缩图片
				for (int i = 0; i < entity.size(); i++) {
					entity.get(i).getmImage().clear();
					for (int x = 0; x < entity.get(i).getmSelected().size(); x++) {
						count++;
						String path = PicUtils.getPicPath(MineHelpSugActivity.this, entity.get(i).getmSelected().get(x));
						JudgeGoodsData data = new JudgeGoodsData();
						data.setPosition(i);
						data.setImgPosition(x);
						data.setImg(new File(path));
						LogUtils.i("path的值" + path);
						Luban.get(MineHelpSugActivity.this)
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
										rlPost.setEnabled(true);
										ToastUtils.showToast(MineHelpSugActivity.this, "图片已损坏,请重新选取");
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
											doAsyncPost(entity.get(position).getJudgeType(), entity.get(position).getJudgeContent());
										}
									}
								}).launchJudge(data);
					}
				}
				break;
		}
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

	public void setPosition(int position) {
		this.position = position;
	}

	public void setHolder(MineHelpSugAdapter.JudgeGoodsViewHolder holder) {
		this.holder = holder;
	}


	private void doAsyncPost(int judgeType, String judgeContent) {
		Map<String, ArrayList<File>> imgMap = new HashMap<>();
		Map<String, String> map = new HashMap<>();

		map.put("userId", mUtils.getUid());
		map.put("feedDirection", judgeType + "");
		map.put("feedContent", judgeContent);

		LogUtils.i("传输的值" + URLBuilder.format(map));
		RequestCall params ;

		for (int i = entity.size() - 1; i >= 0; i--) {
			if (entity.get(i).getmImage() != null && entity.get(i).getmImage().size() > 0) {
				if (entity.get(i).getmImage() != null && entity.get(i).getmImage().size() > 0) {
					LogUtils.i("我开始添加图片了=====" + entity.get(i).getPid() + "图片的长度是===" + entity.get(i).getmImage());
					imgMap.put(entity.get(i).getDetailId(), entity.get(i).getmImage());
				}
			}
		}

		LogUtils.i("im");
		params = OkhttpFileUtils.post().url(URLBuilder.URLBaseHeader + "/phone/userUp/userFeedback")
				.addParams("data", URLBuilder.format(map))
//                .addJudgeFiles(imgMap)
				.addJudgeFiles("files", imgMap)
				.tag(this).build();


		params.execute(new Utils.MyResultCallback<NormalEntity>() {
			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(MineHelpSugActivity.this);
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
					ToastUtils.showToast(MineHelpSugActivity.this, "反馈成功");
					resultIntent.putExtra("refresh", "refresh");
					tvPost.postDelayed(new Runnable() {
						@Override
						public void run() {
							finish();
						}
					}, 400);
				} else {
					LogUtils.i("异常 :)" + response.getMsg());
					ToastUtils.showToast(MineHelpSugActivity.this, "网络故障 " + response.getMsg());
				}
				dismissDialog();
				rlPost.setEnabled(true);
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				dismissDialog();
				if (call.isCanceled()) {
					call.cancel();
				} else {
					LogUtils.i("故障" + e);
					ToastUtils.showToast(MineHelpSugActivity.this, "网络故障,请稍后再试");
				}
				rlPost.setEnabled(true);
			}
		});
	}

	private void postAsynHttp() {
		OkHttpClient mOkHttpClient = new OkHttpClient();
		RequestBody formBody = new FormBody.Builder()
//                .add("data", URLBuilder.format(map))
				.build();
		Request request = new Request.Builder()
				.url("http://api.1-blog.com/biz/bizserver/article/list.do")
				.post(formBody)
				.build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String str = response.body().string();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
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

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case REQUEST_CODE_WRITE_EXTRONAL_STORAGE:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					//用户同意授权
					LogUtils.i("我进入到同意授权了");
					mAdapter.takePicture(takePosition, holder);
				} else {
					LogUtils.i("我在不同意授权");
					showDialog("为了您的正常使用,请允许相应权限");
				}
				break;
		}
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
				PermissionUtils.getAppDetailSettingIntent(MineHelpSugActivity.this);
				infoDialog.dismiss();
			}
		});
		infoDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.i("我cancel了");
				PermissionUtils.getAppDetailSettingIntent(MineHelpSugActivity.this);
				infoDialog.dismiss();
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
