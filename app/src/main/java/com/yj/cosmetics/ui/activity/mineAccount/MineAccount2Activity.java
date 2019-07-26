package com.yj.cosmetics.ui.activity.mineAccount;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.model.AccountEntity;
import com.yj.cosmetics.model.AccountProfitEntity;
import com.yj.cosmetics.ui.activity.MineAccountCostActivity;
import com.yj.cosmetics.ui.activity.MineAccountWithdrawActivity;
import com.yj.cosmetics.ui.adapter.AccountProfitAdapter;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.yj.cosmetics.widget.ProgressLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Suo on 2018/3/12.
 *
 * @TODO 个人中心我的账户  账户余额 2.0修改界面
 */

public class MineAccount2Activity extends BaseActivity implements MineAccount_contract.View {

	private static final String TAG = "MineAccount2Activity";
	@BindView(R.id.title_layout)
	LinearLayout lyTitle;
	@BindView(R.id.mine_Lifting_tv_money)
	TextView tvMoney;
	@BindView(R.id.mine_account_all_money)
	TextView tvAllMoney;
	@BindView(R.id.title_rl_next)
	RelativeLayout reLayout;
	@BindView(R.id.title_ll_iv)
	ImageView ivTitleIcon;

	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;

	@BindView(R.id.xrecyclerView)
	XRecyclerView mRecyclerView;


	AccountProfitAdapter mAdapter;
	List<AccountProfitEntity.AccountProfitData> mList;
	private int pageNum = 1;

	CustomProgressDialog mDialog;
	private MineAccount_contract.Presenter mineAccPresenter = new MineAccount_Presenter(this);
	private String upintegral;


	@Override
	protected int getContentView() {
		return R.layout.activity_mine_account1;
	}

	@Override
	protected void initView() {
		setTitleInfo();
		upintegral = getIntent().getStringExtra("upintegral");
		transTitle();
		initDatas();
	}

	private void initDatas() {
		mList = new ArrayList<>();
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
		mAdapter = new AccountProfitAdapter(this, mList);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickListener(new AccountProfitAdapter.ProfitDetialClickListener() {
			@Override
			public void onItemClick(View view, int postion) {

			}
		});
		mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				pageNum = 1;
				new Handler().postDelayed(new Runnable() {
					public void run() {
						mineAccPresenter.doRefreshData(pageNum, mProgressLayout, mRecyclerView, mList);
					}
				}, 500);
			}

			@Override
			public void onLoadMore() {
				pageNum++;
				new Handler().postDelayed(new Runnable() {
					public void run() {
						mRecyclerView.setPullRefreshEnabled(false);
						mineAccPresenter.doRequestData(pageNum, mProgressLayout, mRecyclerView, mList);
					}
				}, 500);
			}
		});
		mRecyclerView.refresh();
	}


	private void setTitleInfo() {
		setTitleText("账户余额");
//      setTitleLeftImg();

		ivTitleIcon.setImageResource(R.drawable.ic_keyboard_arrow_left_white_24dp);
		setTitleColor(getResources().getColor(R.color.white));
		lyTitle.setBackgroundColor(getResources().getColor(R.color.CE8_3C_3C));
		reLayout.setVisibility(View.VISIBLE);
	}


	@TargetApi(21)
	private void transTitle() {
		if (Build.VERSION.SDK_INT >= 21) {
			View decorView = getWindow().getDecorView();
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			decorView.setSystemUiVisibility(option);
			getWindow().setStatusBarColor(Color.TRANSPARENT);
		}
	}


	@Override
	protected void initData() {
		mDialog = new CustomProgressDialog(this);
		mineAccPresenter.doAsyncGetData(mUtils);
	}


	@OnClick({/*R.id.mine_account_profit,*/ R.id.mine_account_cost, R.id.mine_account_withdraw})
	public void onClick(View view) {
		switch (view.getId()) {
		/*	case R.id.mine_account_profit:
				if (mUtils.isLogin()) {
					Intent intentProfit = new Intent(this, MineAccountProfitActivity.class);
					startActivity(intentProfit);
				} else {
					IntentUtils.IntentToLogin(this);
				}
				break;*/
			case R.id.mine_account_cost:
				if (mUtils.isLogin()) {
					Intent intentCost = new Intent(this, MineAccountCostActivity.class);
					startActivity(intentCost);
				} else {
					IntentUtils.IntentToLogin(this);
				}
				break;
			case R.id.mine_account_withdraw:
				if (mUtils.isLogin()) {
					Intent intentWithdraw = new Intent(this, MineAccountWithdrawActivity.class);
					intentWithdraw.putExtra("upintegral", upintegral);
					startActivity(intentWithdraw);
				} else {
					IntentUtils.IntentToLogin(this);
				}
				break;
		}
	}


	@Override
	public void dismissDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

	@Override
	public void notifyDataSetChanged() {
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void pageNumReduce() {
		pageNum--;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissDialog();
		OkHttpUtils.getInstance().cancelTag(this);
	}

	@Override
	public void initDialog() {

		if (mDialog == null) {
			mDialog = new CustomProgressDialog(MineAccount2Activity.this);
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
	public void showToast(String s) {
		if (s != null) {
			ToastUtils.showToast(MineAccount2Activity.this, s);
		} else {
			ToastUtils.showToast(MineAccount2Activity.this, "网络故障,请稍后再试");
		}
	}

	@Override
	public void setDatas(AccountEntity.AccountData data) {
		Log.i(TAG, "setData: " + data.getUserMoney());
		tvMoney.setText(data.getUserMoney());
		tvAllMoney.setText("￥" + data.getBackmoney());
	}
}
