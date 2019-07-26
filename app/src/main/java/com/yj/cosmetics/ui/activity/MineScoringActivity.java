package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2018/3/12.
 */

public class MineScoringActivity extends BaseActivity {

    @BindView(R.id.mine_scoring_tv_score)
    TextView tvScore;

    private String score;
    private String money;
    private String upintegral;

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_scoring;
    }

    @Override
    protected void initView() {
        setTitleText("我的积分");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        doAsyncGetData();
        super.onResume();
    }

    @OnClick({R.id.mine_scoring_rl_accord, R.id.mine_scoring_rl_detial, R.id.mine_scoring_btn_confirm,R.id.mine_scoring_tv_rule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_scoring_rl_accord:
                Intent intent = new Intent(this, MineScoringAccordActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_scoring_rl_detial:
                Intent intentDetial = new Intent(this, MineScoringDetailActivity.class);
                startActivity(intentDetial);
                break;
            case R.id.mine_scoring_btn_confirm:
                if(TextUtils.isEmpty(money)){
                    ToastUtils.showToast(this,"无法获取额度信息，请检查网络稍后再试");
                    return;
                }
                if(Float.parseFloat(score) < 500){
                    ToastUtils.showToast(this,"积分少于500，无法提升额度");
                    return;
                }
                Intent intentPost = new Intent(this, MineScoringPostActivity.class);
                intentPost.putExtra("money",money);
                intentPost.putExtra("upintegral", upintegral);
                startActivity(intentPost);
                break;
            case R.id.mine_scoring_tv_rule:
                Intent intentRule = new Intent(this,NormalWebViewActivity.class);
                intentRule.putExtra("title","积分规则");
                intentRule.putExtra("url", URLBuilder.URLBaseHeader+ URLBuilder.ScoreRule);
                startActivity(intentRule);
                break;
        }
    }


    private void doAsyncGetData() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", mUtils.getUid());
        LogUtils.i("传输的值" + URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/zoneOrder").tag(this)
                .addParams(Key.data, URLBuilder.format(map))
                .build().execute(new Utils.MyResultCallback<MineEntity>() {

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                if (call.isCanceled()) {
                    call.cancel();
                } else {
                }
            }

            @Override
            public MineEntity parseNetworkResponse(Response response) throws Exception {
                String json = response.body().string().trim();
                LogUtils.i("json的值" + json);
                return new Gson().fromJson(json, MineEntity.class);
            }

            @Override
            public void onResponse(MineEntity response) {
                if (response != null && response.HTTP_OK.equals(response.getCode())) {
                    setData(response.getData());
                } else {
                }
            }
        });
    }

    private void setData(MineEntity.MineData data) {
        score = data.getUserScore();
        money = data.getUserMoney();
        upintegral = data.getUpintegral();
        tvScore.setText(data.getUserScore() + "积分");
    }

    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
