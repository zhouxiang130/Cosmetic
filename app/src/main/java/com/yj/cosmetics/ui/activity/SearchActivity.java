package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.base.Variables;
import com.yj.cosmetics.model.SearchHotEntity;
import com.yj.cosmetics.ui.activity.sotreList.StoreListActivity;
import com.yj.cosmetics.util.KeyBoardUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.CustomViewGroup.CustomSearchHistoryViewGroup;
import com.yj.cosmetics.widget.CustomViewGroup.CustomSearchHotViewGroup;
import com.yj.cosmetics.widget.Dialog.CustomNormalDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Suo on 2017/4/17.
 */

public class SearchActivity extends BaseActivity implements CustomSearchHotViewGroup.OnGroupItemClickListener, CustomSearchHistoryViewGroup.OnGroupItemClickListener {
	@BindView(R.id.search_layout)
	RelativeLayout title;
	@BindView(R.id.search_modify_hots)
	CustomSearchHotViewGroup hintHots;
	@BindView(R.id.search_modify_history)
	CustomSearchHistoryViewGroup hintHistory;
	@BindView(R.id.search_modify_et)
	EditText etContent;
	@BindView(R.id.search_modify_clean)
	ImageView ivClean;


	private String searchTag = "";

	private CustomNormalDialog mDialog;
	private String shopId, TAB;
	private String storeList;

	@Override
	protected int getContentView() {
		return R.layout.activity_search;
	}

	@Override
	protected void initView() {
		shopId = getIntent().getStringExtra("shopId");
		TAB = getIntent().getStringExtra("TAB");
		storeList = getIntent().getStringExtra("storeList");
		if (null != storeList) {
			etContent.setHint(" 搜索 店铺名称");
		} else {
			etContent.setHint(" 搜索 商品名称");
		}
		/*默认搜索列表*/
		getSearchHot();
		resetHistory();
		etContent.addTextChangedListener(new EditChangedListener());
		etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					searchTag = etContent.getText().toString();
					KeyBoardUtils.hintKb(SearchActivity.this);
					if (!searchTag.equals("")) {
						doSaveHistory();
					}
					intentToResult();
					return true;
				}
				return false;
			}
		});
	}

	@Override
	protected void initData() {
	}

	@Override
	public void onHotGroupItemClick(int item, String text) {
		etContent.setText(text);
		etContent.setSelection(text.length());
		searchTag = etContent.getText().toString();
		KeyBoardUtils.hintKb(this);
		if (!searchTag.equals("")) {
			doSaveHistory();
		}
		intentToResult();
	}

	@Override
	public void onHistoryGroupItemClick(int item, String text) {
		etContent.setText(text);
		etContent.setSelection(text.length());
		searchTag = etContent.getText().toString();
		KeyBoardUtils.hintKb(this);
		intentToResult();
	}

	private class EditChangedListener implements TextWatcher {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			LogUtils.i("我onTextChange了" + charSequence);
			if (!"".equals(charSequence.toString())) {
				ivClean.setVisibility(VISIBLE);
			} else {
				ivClean.setVisibility(GONE);
			}
		}

		@Override
		public void afterTextChanged(Editable editable) {
		}
	}

	@OnClick({R.id.search_modify_cancel, R.id.search_modify_clean, R.id.search_modify_clean_history, R.id.search_rl_back})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.search_modify_cancel:
				finish();
				break;
			case R.id.search_modify_clean:
				etContent.setText("");
				break;
			case R.id.search_modify_clean_history:
				LogUtils.i("我点击清空了");
				if (TextUtils.isEmpty(mUtils.getSearchHistory())) {
					return;
				}

				if (mDialog == null) {
					mDialog = new CustomNormalDialog(this);
				}
				if (!mDialog.isShowing()) {
					mDialog.show();
				}
				mDialog.getTvTitle().setText("确认清空历史记录吗?");
				mDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						mUtils.saveSearchHistory("");

						hintHistory.removeAllViews();
						mDialog.dismiss();
					}
				});

				break;
			case R.id.search_rl_back:
				onBackPressed();
				break;
		}
	}

	private void doSaveHistory() {
		LogUtils.i("searchTag的值" + searchTag);
		StringBuilder oldHistory = new StringBuilder();
		//商品
		if (!TextUtils.isEmpty(mUtils.getSearchHistory())) {
			if (mUtils.getSearchHistory().contains(searchTag)) {
				LogUtils.i("我有这条件,删了重新添加");
				oldHistory.append(mUtils.getSearchHistory().replaceAll(searchTag + "#,", ""));
			} else {
				oldHistory.append(mUtils.getSearchHistory());
			}
		}
		mUtils.saveSearchHistory(oldHistory.append(searchTag + "#,").toString());
		resetHistory();
	}

	private void resetHistory() {
		ArrayList<String> history = new ArrayList<>();
		String[] result = null;
		//票务
		LogUtils.i("history的值" + mUtils.getSearchHistory());
		if (!TextUtils.isEmpty(mUtils.getSearchHistory())) {
			result = mUtils.getSearchHistory().split("#,");
		}
		if (result != null && result.length > 0) {
			LogUtils.i("result的长度" + result.length);
			for (int i = result.length - 1; i >= 0; i--) {
				LogUtils.i("result的值" + result[i]);
				history.add(result[i]);
			}
			LogUtils.i("history的长度" + history.size());
			hintHistory.addItemViews(history, "TEVMODE");
			hintHistory.setGroupClickListener(this);
		}
	}

	private void intentToResult() {
		Intent intent = null;
		if (storeList != null) {
			intent = new Intent(this, StoreListActivity.class);
			intent.putExtra("name", searchTag);
			startActivity(intent);
		} else {
			etContent.setText(searchTag);
			intent = new Intent(this, HomeGoodsListActivity.class);
			intent.putExtra("shopId", shopId);
			intent.putExtra("name", searchTag);
			intent.putExtra("TAG", "1");
			if (TAB != null) {
				setResult(Variables.RESULT_CODE, intent);
				finish();
			} else {
				startActivity(intent);
			}
		}
	}

	private void getSearchHot() {
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/ homePage/popularSearch")
				.tag(this).build().execute(new Utils.MyResultCallback<SearchHotEntity>() {
			@Override
			public SearchHotEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, SearchHotEntity.class);
			}

			@Override
			public void onResponse(SearchHotEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null && response.getData().size() > 0) {
						ArrayList<String> text = new ArrayList<>();
						for (int i = 0; i < response.getData().size(); i++) {
							text.add(response.getData().get(i).getKey());
						}
						hintHots.addItemViews(text,"TEVMODE");
						hintHots.setGroupClickListener(SearchActivity.this);
					}
				} else {
					ToastUtils.showToast(SearchActivity.this, "无法获取热门推荐 :)" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(SearchActivity.this, "无法获取热门推荐,请稍后再试");
				}
			}
		});
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissDialog();
		OkHttpUtils.getInstance().cancelTag(this);
	}

	private void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}
}
