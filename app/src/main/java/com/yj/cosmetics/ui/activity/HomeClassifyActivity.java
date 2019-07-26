package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.HomeClassifyEntity;
import com.yj.cosmetics.ui.MainActivity;
import com.yj.cosmetics.ui.adapter.HomeClassifyListAdapter;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.ProgressLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Suo on 2018/3/14.
 */

public class HomeClassifyActivity extends BaseActivity {

    /*@BindView(R.id.recyclerView_title)
    RecyclerView recyclerViewTitle;*/
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_layout)
    ProgressLayout mProgressLayout;
    @BindView(R.id.home_classify_iv_top)
    ImageView ivTop;

  /*  HomeClassifyTitleAdapter mTitleAdapter;
    private List<String> mTitleList;
    private int screenWidth;
    private int scrollWidth ;*/

    HomeClassifyListAdapter mListAdapter;
    LinearLayoutManager layoutManager;


    private  HomeClassifyEntity.HomeClassifyData data;

    private boolean isClick = false;

    private String classifyId;


    @Override
    protected int getContentView() {
        return R.layout.activity_home_classify;
    }

    @Override
    protected void initView() {
        setTitleText(getIntent().getStringExtra("classifyName"));
        classifyId = getIntent().getStringExtra("classifyId");
       /* mTitleList = new ArrayList<>();
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        final LinearLayoutManager titlelayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTitle.setLayoutManager(titlelayoutManager);
        mTitleAdapter = new HomeClassifyTitleAdapter(this, mTitleList);
        recyclerViewTitle.setAdapter(mTitleAdapter);*/

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mListAdapter = new HomeClassifyListAdapter(this, data);
        mRecyclerView.setAdapter(mListAdapter);

      /*  mTitleAdapter.setOnItemClickListener(new HomeClassifyTitleAdapter.SpendDetialClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                isClick = true;
                mTitleAdapter.mPosition = postion;
                mTitleAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(postion + 1);
                int[] pos = new int[2];
                view.getLocationOnScreen(pos);
//                LogUtils.i("xxxxxx======" + pos[0] + "yyyyyyyyy------" + pos[1]);
//                LogUtils.i("结果是=====" + (pos[0] + view.getWidth() / 2 - screenWidth / 2));
                recyclerViewTitle.smoothScrollBy((pos[0] + view.getWidth() / 2 - screenWidth / 2), 0);
            }
        });
*/

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                /*if (!isClick) {
                int firstPos = layoutManager.findFirstVisibleItemPosition();
//                LogUtils.i("firstPos====="+firstPos);
                if (mTitleAdapter.mPosition != firstPos) {
                        mTitleAdapter.mPosition = firstPos;
                        mTitleAdapter.notifyDataSetChanged();
                        View view = titlelayoutManager.findViewByPosition(firstPos);
                        int[] pos = new int[2];
//                    LogUtils.i("view的值。"+view);
                        if (view != null) {
                            view.getLocationOnScreen(pos);
//                                LogUtils.i("xxxxxx======" + pos[0] );
//                                LogUtils.i("结果是=====" + (pos[0] + view.getWidth() / 2 - screenWidth / 2));
                            recyclerViewTitle.smoothScrollBy((pos[0] + view.getWidth() / 2 - screenWidth / 2), 0);

                            //下边的思路是由于滑动过快，标题控件加载过慢来不及复用会有空的情况导致少滑动了一段距离
                            //通过如下方式判断滑动方向，并将需要再次滑动的值推迟一段时间进行滑动。可以解决问题。
                            if ((pos[0] + view.getWidth() / 2 - screenWidth / 2) < 0) {
                                //大致可以判断是向左滑动
                                scrollWidth = -view.getWidth() * 2;
                            } else {
                                //大致是向右滑动
                                scrollWidth = view.getWidth() * 2;
                            }
                        } else {
                            recyclerViewTitle.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    LogUtils.i("为空了，滑动width====" + scrollWidth);
                                    recyclerViewTitle.smoothScrollBy(scrollWidth, 0);
                                }
                            }, 500);
                        }
                    }
                }*/
                LogUtils.i("位置===="+layoutManager.findLastVisibleItemPosition());
                if(layoutManager.findLastVisibleItemPosition()>2){
                    ivTop.setVisibility(View.VISIBLE);
                }else{
                    ivTop.setVisibility(View.GONE);
                }
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    isClick = false;
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    protected void initData() {
        doAsyncGetList();
    }
    @OnClick({R.id.home_classify_iv_top,R.id.home_classify_iv_cart})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.home_classify_iv_top:
                mRecyclerView.smoothScrollToPosition(0);
                break;
            case R.id.home_classify_iv_cart:
                if(mUtils.isLogin()){
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("page","2");
                    startActivity(intent);
                }else{
                    IntentUtils.IntentToLogin(this);
                }
                break;
        }
    }

    private void doAsyncGetList() {
        mProgressLayout.showContent();
        Map<String,String> map = new HashMap<>();
        map.put("classifyId",classifyId);
        LogUtils.i("传输的值"+ URLBuilder.format(map));
        OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/productClassifySonList")
                .addParams("data", URLBuilder.format(map))
                .tag(this).build().execute(new Utils.MyResultCallback<HomeClassifyEntity>() {
            @Override
            public HomeClassifyEntity parseNetworkResponse(Response response) throws Exception {
                String json = response.body().string().trim();
                LogUtils.i("json的值" + json);
                return new Gson().fromJson(json, HomeClassifyEntity.class);
            }

            @Override
            public void onResponse(HomeClassifyEntity response) {
                if (response != null && response.getCode().equals(response.HTTP_OK)) {
                    //返回值为200 说明请求成功
                    if(response.getData() != null){
                        data = response.getData();
                        /*for (int i =0;i<data.getProductArray().size();i++){
                            mTitleList.add(data.getProductArray().get(i).get(0).getClassifyName());
                        }
                        mTitleAdapter.notifyDataSetChanged();*/
                        mListAdapter.setData(data);
                        mProgressLayout.showContent();
                    }else{
                        mProgressLayout.showNone(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                    }
                } else {
                    LogUtils.i("我挂了" + response.getMsg());
                    mProgressLayout.showNetError(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            doAsyncGetList();
                        }
                    });
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                LogUtils.i("我故障了"+e);
                if (call.isCanceled()) {
                    call.cancel();
                }else{
                    mProgressLayout.showNetError(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            doAsyncGetList();
                        }
                    });
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
