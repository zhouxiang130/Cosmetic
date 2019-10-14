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
import com.yj.cosmetics.model.JudgeGoodsData;
import com.yj.cosmetics.model.JudgeGoodsDataEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.ui.adapter.PostJudgeGoodsAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.OkhttpFileUtils;
import com.yj.cosmetics.util.PermissionUtils;
import com.yj.cosmetics.util.PicUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.util.luban.Luban;
import com.yj.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
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

public class PostJudgeGoodsActivity extends BaseActivity {
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

	List<JudgeGoodsDataEntity.DataBean> entity;
	private int position;
	private PostJudgeGoodsAdapter.JudgeGoodsViewHolder holder;
	public int takePosition;
	private CustomProgressDialog mDialog;

	private String oid;
	private Intent resultIntent;
	PostJudgeGoodsAdapter mAdapter;
	//    private Subscription subScale;
	private int count = 0;
	List<JudgeGoodsData> mScaledData = new ArrayList<>();
	private CustomNormalContentDialog infoDialog;

	/* private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image*//*");

    private OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor(){
                @Override
                public Response intercept(Chain chain) throws IOException {
                    okhttp3.Request request = chain.request().newBuilder()
                            .build();
                    return chain.proceed(request);
                }
            }).readTimeout(15, TimeUnit.SECONDS)// 设置读取超时时间
            .writeTimeout(15, TimeUnit.SECONDS)// 设置写的超时时间
            .connectTimeout(15, TimeUnit.SECONDS)// 设置连接超时时间
            .build();
*/


	@Override
	protected int getContentView() {
		return R.layout.activity_post_judge_goods;
	}

	@Override
	protected void initView() {
		entity = new ArrayList<>();
		setTitleText("晒单评价");
		rlPost.setVisibility(View.VISIBLE);
		tvPost.setText("发布");
		oid = getIntent().getStringExtra("oid");
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mAdapter = new PostJudgeGoodsAdapter(this, entity);
		mRecyclerView.setAdapter(mAdapter);
		resultIntent = new Intent();
		setResult(Variables.REFRESH_ORDER_LIST, resultIntent);

	}

	@Override
	protected void initData() {
        /*Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );*/
		doAsyncGetData();
	}

	@Override
	protected void showShadow1() {
		vLine.setVisibility(View.GONE);
//        llTop.setBackgroundColor(getResources().getColor(R.color.CF7_F9_FA));
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
				rlPost.setEnabled(false);
				for (int i = 0; i < entity.size(); i++) {
					if (TextUtils.isEmpty(entity.get(i).getComment())) {
						ToastUtils.showToast(PostJudgeGoodsActivity.this, "请输入评论内容");
						rlPost.setEnabled(true);
						return;
					}
				}
				int size = 0;
				for (int i = 0; i < entity.size(); i++) {
					size += entity.get(i).getmSelected().size();
					LogUtils.i("size的值" + size);
				}
				if (size == 0) {
					LogUtils.i("我进入到没传图片了");
					doAsyncPost();
					return;
				}
				//压缩图片
				for (int i = 0; i < entity.size(); i++) {
					entity.get(i).getmImage().clear();
					for (int x = 0; x < entity.get(i).getmSelected().size(); x++) {
						count++;
						String path = PicUtils.getPicPath(PostJudgeGoodsActivity.this, entity.get(i).getmSelected().get(x));
						JudgeGoodsData data = new JudgeGoodsData();
						data.setPosition(i);
						data.setImgPosition(x);
						data.setImg(new File(path));
						LogUtils.i("path的值" + path);
						Luban.get(PostJudgeGoodsActivity.this)
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
										LogUtils.e("我鲁班onError了" + e);
										rlPost.setEnabled(true);
										ToastUtils.showToast(PostJudgeGoodsActivity.this, "图片已损坏,请重新选取");
									}

									@Override
									public void onJudgeSuccess(JudgeGoodsData data) {
										LogUtils.e("data的position" + data.getPosition());
										LogUtils.e("data的imgPosition" + data.getImgPosition());
										LogUtils.e("file的值" + data.getScaledImg().getAbsolutePath());
										mScaledData.add(data);
										if (mScaledData.size() == count) {
											Collections.sort(mScaledData);
											for (int i = 0; i < mScaledData.size(); i++) {
												LogUtils.e("position的值" + mScaledData.get(i).getPosition() + ".....imgPosition的值" + mScaledData.get(i).getImgPosition() + "...img" + mScaledData.get(i).getScaledImg());
												entity.get(mScaledData.get(i).getPosition()).getmImage().add(mScaledData.get(i).getScaledImg());
											}
											count = 0;
											mScaledData.clear();
											doAsyncPost();
										}
									}
								}).launchJudge(data);
					}
				}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mAdapter.setPicker(true);
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case 200:
				if (resultCode == Activity.RESULT_OK) {
					List<Uri> mList = PicturePickerUtils.obtainResult(data);
					entity.get(position).getmSelected().addAll(mList);
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

	public void setHolder(PostJudgeGoodsAdapter.JudgeGoodsViewHolder holder) {
		this.holder = holder;
	}

	private void setData(JudgeGoodsDataEntity data) {
		entity.add(data.getData());
		for (int i = 0; i < entity.size(); i++) {
			entity.get(i).setmSelected(new ArrayList());
			entity.get(i).setmImage(new ArrayList());
		}
		mAdapter.notifyDataSetChanged();
	}

	private void doAsyncGetData() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("oid", oid);
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/toProComment")
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<JudgeGoodsDataEntity>() {

			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(PostJudgeGoodsActivity.this);
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
			public JudgeGoodsDataEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.e("json的值" + json);
				return new Gson().fromJson(json, JudgeGoodsDataEntity.class);
			}

			@Override
			public void onResponse(JudgeGoodsDataEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					setData(response);
				} else {
					ToastUtils.showToast(PostJudgeGoodsActivity.this, "请求错误 :)" + response.getMsg());
				}
				dismissDialog();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.e("网络请求失败 获取轮播图错误" + e);

				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(PostJudgeGoodsActivity.this, "网络故障,请稍后再试 ");
				}
				dismissDialog();
			}
		});
	}

	private void doAsyncPost() {
		Map<String, ArrayList<File>> imgMap = new HashMap<>();
		StringBuffer pids = new StringBuffer();
		StringBuffer detailId = new StringBuffer();
		StringBuffer comment = new StringBuffer();
		StringBuffer score = new StringBuffer();
		for (int i = 0; i < entity.size(); i++) {
			pids.append(entity.get(i).getPid() + ",");
			detailId.append(entity.get(i).getDetailId() + ",");
			comment.append(entity.get(i).getComment() + "&,!@");
			score.append(entity.get(i).getStars() + ",");
		}
		Map<String, String> map = new HashMap<>();
		map.put("detailId", detailId.toString());
		map.put("pids", pids.toString());
		map.put("userId", mUtils.getUid());
		map.put("oid", oid);
		map.put("comment", comment.toString());
		map.put("score", score.toString());
		RequestCall params = null;
		for (int i = entity.size() - 1; i >= 0; i--) {
			if (entity.get(i).getmImage() != null && entity.get(i).getmImage().size() > 0) {
				imgMap.put("fils", entity.get(i).getmImage());
			}
		}
		LogUtils.e("传输的值" + URLBuilder.format(map));
		params = OkhttpFileUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/productAppraise")
				.addParams("data", URLBuilder.format(map))
//                .addJudgeFiles(imgMap)
				.addJudgeFiles("files", imgMap)
				.tag(this).build();

		params.execute(new Utils.MyResultCallback<NormalEntity>() {
			@Override
			public void onBefore(Request request) {
				super.onBefore(request);

				if (mDialog == null) {
					mDialog = new CustomProgressDialog(PostJudgeGoodsActivity.this);
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
				LogUtils.e("jj" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					ToastUtils.showToast(PostJudgeGoodsActivity.this, "评价成功");
					resultIntent.putExtra("refresh", "refresh");
					tvPost.postDelayed(new Runnable() {
						@Override
						public void run() {
							finish();
						}
					}, 400);
				} else {
					ToastUtils.showToast(PostJudgeGoodsActivity.this, "网络故障 :)" + response.getMsg());
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
					LogUtils.e("故障" + e);
					ToastUtils.showToast(PostJudgeGoodsActivity.this, "网络故障,请稍后再试");
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

	private void uploadFile() {
//        OkHttpUtils.
	}

  /*  private void sendMultipart(){
        mOkHttpClient = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "wangshu")
                .addFormDataPart("image", "wangshu.jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
                .addFormDataPart("files","xxx",requestbo)
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }
*/
   /* private void postAsynFile() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        File file = new File("/sdcard/wangshu.txt");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }*/

	// 上传图片公有方法
  /*  private  void uploadImgAndParameter(Map<String, Object> map,
                                                    String url) {
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue() instanceof File) {
                        File f = (File) entry.getValue();
                        builder.addFormDataPart(entry.getKey(), f.getName(),
                                RequestBody.create(MEDIA_TYPE_PNG, f));
                    } else {
                        builder.addFormDataPart(entry.getKey(), entry
                                .getValue().toString());
                    }
                }
            }
        }
        // 创建RequestBody
        RequestBody body = builder.build();
        final okhttp3.Request request = new okhttp3.Request.Builder().url(url)// 地址
                .post(body)// 添加请求体
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, final Response response)
                    throws IOException {
                final String data = response.body().string();
                call.cancel();// 上传成功取消请求释放内存
            }
            @Override
            public void onFailure(Call call, final IOException e) {
                call.cancel();// 上传失败取消请求释放内存
            }

        });

    }*/
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
					LogUtils.e("我进入到同意授权了");
					mAdapter.takePicture(takePosition, holder);
				} else {
					LogUtils.e("我在不同意授权");
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
				LogUtils.e("我confirm了");
				PermissionUtils.getAppDetailSettingIntent(PostJudgeGoodsActivity.this);
				infoDialog.dismiss();
			}
		});
		infoDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.e("我cancel了");
				PermissionUtils.getAppDetailSettingIntent(PostJudgeGoodsActivity.this);
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
