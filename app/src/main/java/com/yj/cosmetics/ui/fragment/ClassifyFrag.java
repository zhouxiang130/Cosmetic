package com.yj.cosmetics.ui.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseFragment;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.ClassifyContentEntity;
import com.yj.cosmetics.model.ClassifyEntity;
import com.yj.cosmetics.ui.activity.SearchActivity;
import com.yj.cosmetics.ui.adapter.ClassifyContentAdapter;
import com.yj.cosmetics.ui.adapter.ClassifyTitleAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.ProgressLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Suo on 2017/11/18.
 *
 * @TODO 分类界面
 */

public class ClassifyFrag extends BaseFragment {
	@BindView(R.id.recyclerView)
	RecyclerView titleRecyclerView;
	@BindView(R.id.contentRecyclerview)
	RecyclerView contentRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	@BindView(R.id.frag_classify_rl_top)
	RelativeLayout rlTop;
	@BindView(R.id.frag_classify_head)
	View vHead;
	ClassifyTitleAdapter mTitleAdapter;
	ClassifyContentAdapter mContentAdapter;
	private GridLayoutManager gridLayoutManager;
	private List<ClassifyEntity.ClassifyData.ClassifyItem> mTitle;
	private ClassifyContentEntity.ClassifyContentData data;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = createView(inflater.inflate(R.layout.fragment_classify, container, false));
		return view;
	}

	@Override
	protected void initData() {
		mTitle = new ArrayList<>();
		LinearLayoutManager titleLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
		titleRecyclerView.setLayoutManager(titleLayoutManager);
		mTitleAdapter = new ClassifyTitleAdapter(getActivity(), mTitle);
		titleRecyclerView.setAdapter(mTitleAdapter);
//		LinearLayoutManager contentLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//		contentRecyclerView.setLayoutManager(contentLayoutManager);
		gridLayoutManager = new GridLayoutManager(getActivity(), 3);
		contentRecyclerView.setLayoutManager(gridLayoutManager);
		mContentAdapter = new ClassifyContentAdapter(getActivity(), data);
		contentRecyclerView.setAdapter(mContentAdapter);
		mTitleAdapter.setOnItemClickListener(new ClassifyTitleAdapter.SpendDetialClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
				LogUtils.i("点击了======" + postion);
				if (mTitleAdapter.mPosition == postion) {
					return;
				}
				doAsyncGetContent(mTitle.get(postion).getClassify_id());
				mTitleAdapter.mPosition = postion;
				mTitleAdapter.notifyDataSetChanged();
				int windowHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
				titleRecyclerView.smoothScrollBy(0, (int) (view.getY() + view.getHeight() + rlTop.getHeight() - windowHeight / 2));
			}
		});
		doAsyncGetTitle();
		transTitle();
	}

	@TargetApi(21)
	private void transTitle() {
		if (Build.VERSION.SDK_INT >= 21) {
			vHead.setVisibility(View.VISIBLE);
		}
	}

	@OnClick({R.id.frag_classify_rl_search})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.frag_classify_rl_search:
				Intent intentSearch = new Intent(getActivity(), SearchActivity.class);
				startActivity(intentSearch);
				break;
		}
	}

	private void doAsyncGetTitle() {
		mProgressLayout.showContent();
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/productClassifyList")
				.tag(getActivity()).build().execute(new Utils.MyResultCallback<ClassifyEntity>() {
			@Override
			public ClassifyEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetTitle --json的值" + json);
				return new Gson().fromJson(json, ClassifyEntity.class);
			}

			@Override
			public void onResponse(ClassifyEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null && response.getData().getProductClassifyList() != null && response.getData().getProductClassifyList().size() != 0) {
						mTitle.addAll(response.getData().getProductClassifyList());
						mTitleAdapter.notifyDataSetChanged();
						doAsyncGetContent(response.getData().getProductClassifyList().get(0).getClassify_id());
					} else {
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
							doAsyncGetTitle();
						}
					});
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("我故障了" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					mProgressLayout.showNetError(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							doAsyncGetTitle();
						}
					});
				}
			}
		});
	}

	private void doAsyncGetContent(final String classifyId) {
		mProgressLayout.showContent();
		Map<String, String> map = new HashMap<>();
		map.put("classifyId", classifyId);
		LogUtils.i("传输的值hi++=" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/productClassifyByParIdList")
				.addParams("data", URLBuilder.format(map))
				.tag(getActivity()).build().execute(new Utils.MyResultCallback<ClassifyContentEntity>() {
			@Override
			public ClassifyContentEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("doAsyncGetTitle json的值" + json);
				return new Gson().fromJson(json, ClassifyContentEntity.class);
			}

			@Override
			public void onResponse(ClassifyContentEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null) {
						data = response.getData();
						mContentAdapter.setData(data);
						mProgressLayout.showContent();
					} else {
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
							doAsyncGetContent(classifyId);
						}
					});
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("我故障了" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					mProgressLayout.showNetError(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							doAsyncGetContent(classifyId);
						}
					});
				}
			}
		});
	}
}
