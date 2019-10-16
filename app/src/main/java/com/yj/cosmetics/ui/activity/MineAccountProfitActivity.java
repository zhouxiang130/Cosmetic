package com.yj.cosmetics.ui.activity;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.AccountProfitEntity;
import com.yj.cosmetics.ui.adapter.AccountProfitAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.ProgressLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2017/7/31.
 */

public class MineAccountProfitActivity extends BaseActivity {
    @BindView(R.id.xrecyclerView)
    XRecyclerView mRecyclerView;
    @BindView(R.id.progress_layout)
    ProgressLayout mProgressLayout;
    AccountProfitAdapter mAdapter;
    List<AccountProfitEntity.AccountProfitData> mList;
    private int pageNum = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_account_profit;
    }

    @Override
    protected void initView() {
        setTitleText("收益记录");
        mList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        mAdapter = new AccountProfitAdapter(this,mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AccountProfitAdapter.ProfitDetialClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

            }
        });
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNum =1;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        doRefreshData();
                    }
                }, 500);
            }

            @Override
            public void onLoadMore() {
                pageNum++;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mRecyclerView.setPullRefreshEnabled(false);
                        doRequestData();
                    }
                }, 500);
            }
        });
        mRecyclerView.refresh();
    }

    @Override
    protected void initData() {

    }

    private void doRefreshData(){
        mProgressLayout.showContent();
        Map<String,String> map = new HashMap<>();
        map.put("userId",mUtils.getUid());
        map.put("pageNum",pageNum+"");
        LogUtils.i("传输的值"+ URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader+"/phone/user/revenueRecord").tag(this)
                .addParams(Key.data, URLBuilder.format(map))
                .build().execute(new Utils.MyResultCallback<AccountProfitEntity>() {

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                mRecyclerView.refreshComplete();
                if (call.isCanceled()) {
                    call.cancel();
                }else{
                    mProgressLayout.showNetError(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(mList != null && !mList.isEmpty()){
                                mList.clear();
                                mAdapter.notifyDataSetChanged();
                            }
                            mRecyclerView.refresh();
                        }
                    });
                }
            }
            @Override
            public AccountProfitEntity parseNetworkResponse(Response response) throws Exception {
                String json = response.body().string().trim();
                LogUtils.i("json的值"+json);
                return new Gson().fromJson(json,AccountProfitEntity.class);
            }
            @Override
            public void onResponse(AccountProfitEntity response) {

                if (response != null && response.HTTP_OK.equals(response.getCode())) {
                    if (response.getData().size() != 0) {
                        mList.clear();
                        mList.addAll(response.getData());
                        mAdapter.notifyDataSetChanged();
                        mProgressLayout.showContent();
                    }else if (response.getData().size() == 0) {
                        mProgressLayout.showNone(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                    }
                } else {
//                    ToastUtils.showToast(MineAccountProfitActivity.this,"请求失败：）"+response.getMsg());
                    mProgressLayout.showNetError(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(mList != null && !mList.isEmpty()){
                                mList.clear();
                                mAdapter.notifyDataSetChanged();
                            }
                            mRecyclerView.refresh();
                        }
                    });
                }
                mRecyclerView.refreshComplete();
            }
        });
    }
    private void doRequestData(){
        Map<String,String> map = new HashMap<>();
        map.put("userId",mUtils.getUid());
        map.put("pageNum",pageNum+"");
        LogUtils.i("传输的值"+ URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader+"/phone/user/revenueRecord").tag(this)
                .addParams("data", URLBuilder.format(map))
                .build().execute(new Utils.MyResultCallback<AccountProfitEntity>()  {
            @Override
            public AccountProfitEntity parseNetworkResponse(Response response) throws Exception {
                String json = response.body().string().trim();
                LogUtils.i("json的值"+json);
                return new Gson().fromJson(json,AccountProfitEntity.class);
            }
            @Override
            public void onResponse(AccountProfitEntity info) {
                if (info != null && info.HTTP_OK.equals(info.getCode())) {
                    if (info.getData().size() != 0) {
                        mList.addAll(info.getData());
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setPullRefreshEnabled(true);
                        mRecyclerView.loadMoreComplete();
                    } else if (info.getData().size() == 0 ) {
                        mRecyclerView.setNoMore(true);
                        mRecyclerView.setPullRefreshEnabled(true);
                        pageNum--;
                    }
                    mProgressLayout.showContent();
                } else {
                    ToastUtils.showToast(MineAccountProfitActivity.this,"网络异常");
                    pageNum --;
                    mRecyclerView.setPullRefreshEnabled(true);
                    mRecyclerView.loadMoreComplete();
                }
            }
            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                mRecyclerView.loadMoreComplete();
                mRecyclerView.setPullRefreshEnabled(true);
                if (call.isCanceled()) {
                    LogUtils.i("我进入到加载更多cancel了");
                    call.cancel();
                }else if(pageNum !=1 ){
                    LogUtils.i("加载更多的Log");
                    ToastUtils.showToast(MineAccountProfitActivity.this,"网络故障,请稍后再试");
                    pageNum --;
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
