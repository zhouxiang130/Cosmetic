package com.yj.cosmetics.ui.activity;

import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.InfoDetailEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2018/3/30.
 */

public class InfoDetailActivity extends BaseActivity {
    @BindView(R.id.info_detial_tv_content)
    TextView tvContent;
    @BindView(R.id.info_detial_tv_date)
    TextView tvDate;
    @BindView(R.id.info_detial_tv_state)
    TextView tvState;
    private String mid;

    @Override
    protected int getContentView() {
        return R.layout.activity_info_detail;
    }

    @Override
    protected void initView() {
        setTitleText("消息详情");
        mid = getIntent().getStringExtra("mid");
    }

    @Override
    protected void initData() {
        doAsyncGetData();
    }

    private void doAsyncGetData() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", mUtils.getUid());
        map.put("mid", mid);
        LogUtils.i("传输的值" + URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/msgDetail")
                .addParams("data", URLBuilder.format(map))
                .tag(this).build().execute(new Utils.MyResultCallback<InfoDetailEntity>() {

            @Override
            public InfoDetailEntity parseNetworkResponse(Response response) throws Exception {
                String json = response.body().string().trim();
                LogUtils.i("json的值" + json);
                return new Gson().fromJson(json, InfoDetailEntity.class);
            }

            @Override
            public void onResponse(InfoDetailEntity response) {
                if (response != null && response.getCode().equals(response.HTTP_OK)) {
                    //返回值为200 说明请求成功
                    setData(response.getData());
                } else {
                    ToastUtils.showToast(InfoDetailActivity.this, "网络故障 " + response.getMsg());
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                LogUtils.i("网络请求失败 获取轮播图错误" + e);
                if(call.isCanceled()){
                    call.cancel();
                }else {
                    ToastUtils.showToast(InfoDetailActivity.this, "网络故障,请稍后再试 ");
                }

            }
        });
    }

    private void setData(InfoDetailEntity.InfoDetialData data){
        tvContent.setText(data.getContent());
        tvState.setText(data.getTitle());
        tvDate.setText(data.getTime());
    }

    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
