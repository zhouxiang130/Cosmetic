package com.yj.cosmetics.ui.fragment;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseFragment;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.CartsEntity;
import com.yj.cosmetics.model.HomeListEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.ui.activity.SettlementCartActivity;
import com.yj.cosmetics.ui.activity.GoodsDetailActivity;
import com.yj.cosmetics.ui.activity.StoreDetailActivity;
import com.yj.cosmetics.ui.adapter.CartLikeAdapter;
import com.yj.cosmetics.ui.adapter.CartListAdapter;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.yj.cosmetics.widget.Dialog.CustomProgressDialog;
import com.yj.cosmetics.widget.ProgressLayout;
import com.yj.cosmetics.widget.WrapContentGridView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Suo on 2017/11/18.
 *
 * @TODO 购物车
 */

public class CartFrag extends BaseFragment implements CartListAdapter.CheckInterface,
		CartListAdapter.ModifyCountInterface, CartListAdapter.CheckInterfaces {
	@BindView(R.id.frag_cart_gridview)
	WrapContentGridView mGridView;
	@BindView(R.id.recyclerView)
	RecyclerView mRecyclerView;
	@BindView(R.id.progress_layout)
	ProgressLayout mProgressLayout;
	@BindView(R.id.frag_cart_rl_nomore)
	RelativeLayout rlNomore;
	@BindView(R.id.shopping_cat_total)
	TextView tvTotal;
	@BindView(R.id.frag_cart_tv_edit)
	TextView tvEdit;
	@BindView(R.id.frag_cart_tv_solve)
	TextView tvSolve;
	@BindView(R.id.frag_cart_tv_delete)
	TextView tvDelete;
	@BindView(R.id.frag_cart_rl_money)
	RelativeLayout rlTotal;
	@BindView(R.id.shopping_cart_check)
	CheckBox cbAll;
	@BindView(R.id.frag_cart_head)
	View vHead;
	@BindView(R.id.shopping_cart_rlBottom)
	LinearLayout llBottom;
	@BindView(R.id.frag_cart_rl_edit)
	RelativeLayout rlEdit;
	CartListAdapter mListAdapter;
	CartLikeAdapter mLikeAdapter;
	CustomNormalContentDialog deleteDialog;
	CustomProgressDialog mDialog;
	private List<HomeListEntity.HomeListData.HomeListItem> mLike;
	private List<CartsEntity.DataBean.ProCartsBean> mList;
	private List<CartsEntity.DataBean.ProCartsBean> mData = new ArrayList<>();
	public List<Integer> changeList = new ArrayList<>();
	private float totalPrice = 0;// 购买的商品总价
	private int totalCount = 0;// 购买的商品总数量
	private int currentNum = 0;// 购买的商品总数量
	private boolean flag = true;
	private String totalPrices, sproductId, productId;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = createView(inflater.inflate(R.layout.fragment_cart, container, false));
		return view;
	}

	@Override
	protected void initData() {
		mProgressLayout.showContent();
		mLike = new ArrayList<>();
		mList = new ArrayList<>();
		mDialog = new CustomProgressDialog(getActivity());
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(layoutManager);
		mListAdapter = new CartListAdapter(getActivity(), mList);
		mRecyclerView.setAdapter(mListAdapter);
		mListAdapter.setCheckInterface(this);
		mListAdapter.setCheckInterfaces(this);
		mListAdapter.setModifyCountInterface(this);
		mListAdapter.setOnItemsClickListener(new CartListAdapter.ProductDetailClickListener() {
			@Override
			public void onItemClicks(View view, int pos, int postion) {
				//@TODO 这里是商品点击事件
				LogUtils.i( "mList.size(): " + mList.size());
				LogUtils.i("setOnItemsClickListener: " + pos + " postion : " + postion);
				if (mList.size() != 0) {
					sproductId = mList.get(postion).getShopProArray().get(pos).getSproductId();
					productId = mList.get(postion).getShopProArray().get(pos).getProId();
					if (productId != null && !productId.equals("")) {
						Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
						intent.putExtra("sproductId", sproductId);
						intent.putExtra("productId", productId);
						startActivity(intent);
					}
				}
			}
		});
		mListAdapter.setOnItemClickListener(new CartListAdapter.ProfitDetialClickListener() {
			@Override
			public void onItemClick(View view, int postion) {
//				if (postion > 0) {
				if (mList.size() != 0) {
					String shopId = mList.get(postion).getShopId();
					if (shopId != null) {
						Intent intent = new Intent(getContext(), StoreDetailActivity.class);
						intent.putExtra("shopId", shopId);
						startActivity(intent);
					}
				}
//				}
			}
		});
		mLikeAdapter = new CartLikeAdapter(getActivity(), mLike);
		mGridView.setAdapter(mLikeAdapter);
		mGridView.setFocusable(false);
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				IntentUtils.IntentToGoodsDetial(getActivity(), mLike.get(i).getProduct_id());
			}
		});
		doAsyncGetList();
		transTitle();
	}

	private boolean isTag = true;


	@Override
	public void onResume() {
		if (mUtils.isLogin()) {
			//联网获取数据
			doRefreshData();
			doAsyncGetList();
		} else {
			llBottom.setVisibility(View.GONE);
			rlNomore.setVisibility(View.VISIBLE);
			mRecyclerView.setVisibility(View.GONE);
		}
		cbAll.setChecked(false);
		super.onResume();
	}


	@TargetApi(21)
	private void transTitle() {
		if (Build.VERSION.SDK_INT >= 21) {
			vHead.setVisibility(View.VISIBLE);
		}
	}


	@OnClick({R.id.frag_cart_rl_edit, R.id.shopping_cart_check, R.id.frag_cart_tv_solve, R.id.frag_cart_tv_delete})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.frag_cart_rl_edit:
				if (flag) {
					tvEdit.setText("完成");
					mListAdapter.isShow(true);
//					mListAdapter.setNotifyDataSetChanged();
					rlTotal.setVisibility(View.INVISIBLE);
					tvSolve.setVisibility(View.GONE);
					tvDelete.setVisibility(View.VISIBLE);
				} else {

					tvEdit.setText("编辑");
					mListAdapter.isShow(false);
//					mListAdapter.setNotifyDataSetChanged();
					rlTotal.setVisibility(View.VISIBLE);
					tvSolve.setVisibility(View.VISIBLE);
					tvDelete.setVisibility(View.GONE);
					LogUtils.i("我点击完成了");
					if (changeList != null && changeList.size() > 0) {
						LogUtils.i("我进修改了");

					}
				}
				flag = !flag;
				break;
			case R.id.shopping_cart_check:

				if (mList.size() != 0) {
					if (cbAll.isChecked()) {
						for (int i = 0; i < mList.size(); i++) {
//							if (mList.get(i).getReceipt().equals("1")) {
							for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
								LogUtils.i("onClick: " + i + ">>>>>>>>" + j);
								if (mList.get(i).getShopProArray().get(j).getProductState() == 0) {
									mList.get(i).getShopProArray().get(j).setChoosed(true);
									mList.get(i).setChoosed(true);
								}
//									else {
//										mList.get(i).setChoosed(false);
//									}
//								if (mList.get(i).getReceipt())
//								mListAdapter.setAllCheck(true);
							}
//							} else {
//								mList.get(i).setChoosed(false);
//							}
						}
					} else {
						for (int i = 0; i < mList.size(); i++) {
							mList.get(i).setChoosed(false);
							for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
								LogUtils.i("onClick: " + i + ">>>>>>>>" + j);
								if (mList.get(i).getShopProArray().get(j).getProductState() == 0) {
									mList.get(i).getShopProArray().get(j).setChoosed(false);
								} else {

								}
//								mListAdapter.setAllCheck(false);
							}
						}
					}
					mListAdapter.notifyDataSetChanged();
//					mListAdapter.setNotifyDataSetChanged();
				}
				statistics();
				break;
			case R.id.frag_cart_tv_solve:
				if (checkChoosed()) {
					StringBuffer stringBuffer = new StringBuffer();
					for (int i = 0; i < mList.size(); i++) {
						for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
							if (mList.get(i).getShopProArray().get(j).isChoosed()) {
								stringBuffer.append(mList.get(i).getShopProArray().get(j).getCartId() + ",");
							}
						}
					}
					stringBuffer = stringBuffer.replace(stringBuffer.toString().lastIndexOf(","), stringBuffer.toString().lastIndexOf(",") + 1, "");
					LogUtils.i("stringBuffer的值" + stringBuffer);
					Intent intent = new Intent(getActivity(), SettlementCartActivity.class);
					intent.putExtra("cartIdObj", stringBuffer.toString());
					startActivityForResult(intent, 192);

				} else {
//					ToastUtils.instant().init(getContext(), 400).custom("未选中购买的商品╭(╯^╰)╮");
					ToastUtils.custom(" 未选中购买的商品╭(╯^╰)╮", 400);
//					ToastUtils.showToast(getActivity(), "未选中购买的商品╭(╯^╰)╮");
				}
				break;
			case R.id.frag_cart_tv_delete:
				if (isChoosed()) {
					if (deleteDialog != null) {
						return;
					}
					showDeleteDialog(1, 0, 0);
				} else {
					ToastUtils.custom(" 未选中商品╭(╯^╰)╮", 400);
//					ToastUtils.showToast(getActivity(), "未选中商品╭(╯^╰)╮");
				}
				break;
		}
	}

	private boolean checkChoosed() {
		for (int i = 0; i < mList.size(); i++) {
			for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
				if (mList.get(i).getShopProArray().get(j).isChoosed()) {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * @param position  组元素位置
	 * @param isChecked 组元素选中与否
	 * @TODO 商品单选
	 */
	@Override
	public void checkGroup(int pos, int position, boolean isChecked) {
		//@TODO
		LogUtils.i("checkGroup: 店铺： " + pos + " 店铺商品 : " + position);
		if (mList.size() != 0) {
			//把对应的商品设置为true
			mList.get(pos).getShopProArray().get(position).setChoosed(isChecked);

			//@TODO
			if (setGroupCheck(pos)) {
				mList.get(pos).setChoosed(true);
//				mListAdapter.setCheck(pos, true);
			} else {
				mList.get(pos).setChoosed(false);
//				mListAdapter.setCheck(pos, false);

			}

			mListAdapter.notifyDataSetChanged();
			getAllCheck();
			statistics();
		}

	}


	public boolean isA = false;

	/**
	 * @param pos       当前店铺位置
	 * @param isChecked 店铺元素选中与否
	 * @TODO 店铺组选
	 */
	@Override
	public void checkGroup(int pos, boolean isChecked) {

		if (mList.size() != 0) {
			for (int i = 0; i < mList.get(pos).getShopProArray().size(); i++) {

				if (mList.get(pos).getShopProArray().get(i).getProductState() == 0) {
					mList.get(pos).getShopProArray().get(i).setChoosed(isChecked);
					mList.get(pos).setChoosed(isChecked);
				}
//				else {
//					mList.get(pos).setChoosed(false);
//				}

			}


			getAllCheck();
			statistics();
//			mListAdapter.setNewData(mList);
			mListAdapter.notifyDataSetChanged();

		}
	}


	public boolean setGroupCheck(int pos) {
		if (mList.size() != 0) {
			for (int i = 0; i < mList.get(pos).getShopProArray().size(); i++) {
				if (mList.get(pos).getShopProArray().get(i).getProductState() == 0) {
					if (!mList.get(pos).getShopProArray().get(i).isChoosed()) {
						return false;
					}
				}
			}
			return true;
		}

		return true;
	}


	private void getAllCheck() {
		if (isAllCheck()) {
			cbAll.setChecked(true);
		} else {
			cbAll.setChecked(false);
		}
	}

	/**
	 * 遍历list集合
	 *
	 * @return
	 */
	private boolean isAllCheck() {
		if (mList.size() != 0) {
			for (int i = 0; i < mList.size(); i++) {
				for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
					if (!mList.get(i).getShopProArray().get(j).isChoosed()) {
						return false;
					}
				}
			}
			return true;
		}
		return true;
	}

	/**
	 * 增加
	 *
	 * @param pos
	 * @param position      组元素位置
	 * @param showCountView 用于展示变化后数量的View
	 * @param isChecked     子元素选中与否
	 */
	@Override
	public void doIncrease(int pos, int position, View showCountView, boolean isChecked) {
		int currentCount = Integer.parseInt(mList.get(pos).getShopProArray().get(position).getNum());
		LogUtils.i("位置是" + pos + ">>>>>>>position : " + position + ".....currentCount是" + currentCount);
		currentCount++;
//		mList.get(pos).getShopProArray().get(position).setNum(String.valueOf(currentCount));
//		((TextView) showCountView).setText(currentCount + "");
		statistics();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(mList.get(pos).getShopProArray().get(position).getCartId() + ":" + currentCount + ";");
		LogUtils.i("onClick:>>>>> " + stringBuffer.toString());
		doChangeCount(stringBuffer.toString(), pos, position, currentCount, showCountView);
	}

	/**
	 * 删减
	 *
	 * @param pos
	 * @param position      组元素位置
	 * @param showCountView 用于展示变化后数量的View
	 * @param isChecked     子元素选中与否
	 */
	@Override
	public void doDecrease(int pos, int position, View showCountView, boolean isChecked) {
		int currentCount = Integer.parseInt(mList.get(pos).getShopProArray().get(position).getNum());
		if (currentCount == 1) {
			return;
		}
		 LogUtils.i("doDecrease: " + currentCount);
		currentCount--;
		 LogUtils.i(  "doDecrease: " + currentCount);
//		mList.get(pos).getShopProArray().get(position).setNum(String.valueOf(currentCount));
//		((TextView) showCountView).setText(currentCount + "");


		statistics();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(mList.get(pos).getShopProArray().get(position).getCartId() + ":" + currentCount + ";");
		LogUtils.i("onClick:>>>>> " + stringBuffer.toString());

		doChangeCount(stringBuffer.toString(), pos, position, currentCount, showCountView);
//		for (int i = 0; i < changeList.size(); i++) {
//			if (pos == changeList.get(i)) {
//				return;
//			}
//		}
//		changeList.add(pos);
	}

	/**
	 * 删除
	 *
	 * @param pos
	 * @param position
	 */
	@Override
	public void childDelete(int pos, int position) {
		showDeleteDialog(2, pos, position);
	}

	private boolean isChoosed() {
		boolean isChoosed = false;
		for (int i = 0; i < mList.size(); i++) {
			for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
				if (mList.get(i).getShopProArray().get(j).isChoosed()) {
					isChoosed = true;
				}
			}
		}
		return isChoosed;
	}

	/**
	 * 统计操作
	 * 1.先清空全局计数器<br>
	 * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
	 * 3.给底部的textView进行数据填充
	 */
	public void statistics() {
		totalCount = 0;
		totalPrice = 0;
		totalPrices = "0.00";
		for (int i = 0; i < mList.size(); i++) {
			for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
				if (mList.get(i).getShopProArray().get(j).isChoosed()) {
//					LogUtils.i((  "statistics: " + mList.get(i).getShopProArray().get(j).getSkuPrice() + "-------" + mList.get(i).getShopProArray().get(j).getNum());
					totalCount += Integer.parseInt(mList.get(i).getShopProArray().get(j).getNum());

					totalPrice += Float.parseFloat(mList.get(i).getShopProArray().get(j).getSkuPrice()) * Integer.parseInt(mList.get(i).getShopProArray().get(j).getNum());
					totalPrices = new DecimalFormat("##0.00").format(totalPrice);
				}
			}
		}
//		LogUtils.i((  "statistics: " + totalPrices);
		tvTotal.setText(totalPrices + "");
		tvSolve.setText("结算（" + totalCount + "）");
	}

	private void showDeleteDialog(final int i, final int pos, final int position) {
		deleteDialog = new CustomNormalContentDialog(getActivity());
		deleteDialog.show();
		deleteDialog.getTvTitle().setText("确认删除商品吗？");
		deleteDialog.getTvContent().setText("商品删除后，可重新挑选喜欢的商品下单");
		deleteDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				StringBuffer stringBuffer = new StringBuffer();
				if (i == 1) {
					for (int i = 0; i < mList.size(); i++) {
						for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
							if (mList.get(i).getShopProArray().get(j).isChoosed()) {
								stringBuffer.append(mList.get(i).getShopProArray().get(j).getCartId() + ";");
							}
						}
					}

				} else {
					stringBuffer.append(mList.get(pos).getShopProArray().get(position).getCartId() + ";");
				}
				doDeleteData(stringBuffer.toString(), false);
				dismissDialog();
				/*deleteDialog.dismiss();
				deleteDialog = null;*/
			}
		});
		deleteDialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismissDialog();

			}
		});
		deleteDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialogInterface) {
				dismissDialog();
				LogUtils.i("我在onCancel方法中");
			}
		});
	}

	private void doRefreshData() {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的值" + URLBuilder.format(map));
//		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/homePage/shopCarList")
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.NEWSHOPCARLIST)
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<CartsEntity>() {
			@Override
			public CartsEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("newShopCarList json的值 " + json);
				return new Gson().fromJson(json, CartsEntity.class);
			}

			@Override
			public void onResponse(CartsEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					if (response.getData() != null && response.getData().getProCarts() != null && response.getData().getProCarts().size() != 0) {
						//成功之后setData
						setData(response);
						rlEdit.setVisibility(View.VISIBLE);
					} else {
						rlEdit.setVisibility(View.GONE);
						mRecyclerView.setVisibility(View.GONE);
						rlNomore.setVisibility(View.VISIBLE);
						LogUtils.i("我进入到展示了===" + (llBottom.getVisibility() == View.VISIBLE));
						llBottom.setVisibility(View.GONE);

					}
					mProgressLayout.showContent();
				} else {
					LogUtils.i("返回错误了" + response.getMsg() + response.getCode());
					mProgressLayout.showNetError(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							doRefreshData();
							doAsyncGetList();
						}
					});
				}
			 /*   mRecyclerView.setPullRefreshEnabled(true);
			    mRecyclerView.refreshComplete();*/
			}


			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);

				mProgressLayout.showNetError(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						doRefreshData();
						doAsyncGetList();
					}
				});
				LogUtils.i("网络故障了" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {

				}
			}
		});
	}

	private void setData(CartsEntity response) {
		mRecyclerView.setVisibility(View.VISIBLE);
		rlNomore.setVisibility(View.GONE);
		LogUtils.i("我进入到展示了===" + (llBottom.getVisibility() == View.VISIBLE));
		llBottom.setVisibility(View.VISIBLE);

		List<CartsEntity.DataBean.ProCartsBean> result = response.getData().getProCarts();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result.size(); i++) {

//			if (Integer.parseInt(result.get(i).getShopProArray().get(i).getNum()) > Integer.parseInt(result.get(i).getSkuNum())) {
//				//说明要购买的数量大于库存 将其删掉并提示
//				sb.append(result.get(i).getCartId() + ";");
//				LogUtils.i("我进入到有问题的了" + i + "...." + result.get(i).getCartId());
//				result.remove(i);
//				i--;
//			}

		}
		if (!TextUtils.isEmpty(sb)) {
			//说明有数量不对的商品 删除
			LogUtils.i("sb的值" + sb);
			doDeleteData(sb.toString(), true);
			if (deleteDialog == null) {
				deleteDialog = new CustomNormalContentDialog(getActivity());
			}
			if (!deleteDialog.isShowing()) {
				deleteDialog.show();
			}
			deleteDialog.getTvTitle().setText("商品时效已过期");
			deleteDialog.getTvContent().setText("检测到您收藏的某商品数量不足,已将其删除,请重新添加");
			deleteDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					dismissDialog();
				}
			});
		}

		mListAdapter.setLimit(response.getData().getSystemValue());
		mList.clear();
//		mData.clear();
		mList.addAll(response.getData().getProCarts());

		LogUtils.i("mList.size(): " + mList.size());
//		mData.addAll(response.getData().getProCarts());

		mListAdapter.setNewData(response.getData().getProCarts());
//		mListAdapter.notifyDataSetChanged();
//		mListAdapter.setNotifyDataSetChanged();

		statistics();
		isTag = false;


	}


	private void doAsyncGetList() {
		Map<String, String> map = new HashMap<>();
		map.put("pageNum", "1");
		LogUtils.i("传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.PRODUCTSORT)
				.addParams("data", URLBuilder.format(map))
				.tag(getActivity()).build().execute(new Utils.MyResultCallback<HomeListEntity>() {
			@Override
			public HomeListEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, HomeListEntity.class);
			}

			@Override
			public void onResponse(HomeListEntity response) {
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					//返回值为200 说明请求成功
					if (response.getData() != null && response.getData().getProducts() != null && response.getData().getProducts().size() > 0) {
						mLike.clear();
						if (response.getData().getProducts().size() > 6) {
							mLike.addAll(response.getData().getProducts().subList(0, 6));
						} else {
							mLike.addAll(response.getData().getProducts().subList(0, response.getData().getProducts().size()));
						}
						mLikeAdapter.notifyDataSetChanged();
					}
				} else {
					LogUtils.i("我挂了" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("我故障了" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
				}
			}
		});
	}

	private void doDeleteData(final String ids, final boolean isRefresh) {
		Map<String, String> map = new HashMap<>();
		map.put("ids", ids);
		LogUtils.i("删除购物车传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.DELETECARTBYIDS)
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(getActivity());
					if (!getActivity().isFinishing()) {
						mDialog.show();
					}
				} else {
					if (!getActivity().isFinishing()) {
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
					if (!isRefresh) {
						String splitId = ids.split(";")[0];
						for (int i = 0; i < mList.size(); i++) {
							for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
								 LogUtils.i(  "onResponse:>>>1111 " + mList.get(i).getShopProArray().size());
								if (mList.get(i).getShopProArray().get(j).getCartId() == Integer.parseInt(splitId)) {
									mList.get(i).getShopProArray().remove(j);
//									if (mList.get(i).getShopProArray().size() == 0) {
//										mList.remove(i);
//										break;
//									}
								}
							}
						}

						doRefreshData();
//						mListAdapter.setNewData(mList);

//						mListAdapter.setNotifyDataSetChanged();
						if (mList.size() == 0) {
							llBottom.setVisibility(View.GONE);
							mRecyclerView.setVisibility(View.GONE);
							rlNomore.setVisibility(View.VISIBLE);
						}
						statistics();
					}
				} else {
					ToastUtils.showToast(getActivity(), "网络故障 " + response.getMsg());
				}
				dismissDialog2();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(getActivity(), "网络故障,请稍后再试 ");
				}
				LogUtils.i("网络请求失败 获取轮播图错误" + e);

				dismissDialog2();
			}
		});
	}

	private void doChangeCount(String cartIdOrNum, final int pos, final int position, final int currentCount, final View showCountView) {
		Map<String, String> map = new HashMap<>();
		map.put("cartIdOrNum", cartIdOrNum);
		LogUtils.i("更改商品数量传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + URLBuilder.UPDATECARTNUM)
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {

			@Override
			public void onBefore(Request request) {
				super.onBefore(request);
				if (mDialog == null) {
					mDialog = new CustomProgressDialog(getActivity());
					if (!getActivity().isFinishing()) {
						mDialog.show();
					}
				} else {
					if (!getActivity().isFinishing()) {
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
					changeList.clear();
					mData.clear();
					mList.get(pos).getShopProArray().get(position).setNum(String.valueOf(currentCount));
					((TextView) showCountView).setText(currentCount + "");
					for (int i = 0; i < mList.size(); i++) {
						for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
							LogUtils.i("mList的值" + mList.get(i).getShopProArray().get(j).getProName());
						}
					}
					mData.addAll(mList);

					for (int i = 0; i < mData.size(); i++) {
						for (int j = 0; j < mData.get(i).getShopProArray().size(); j++) {
							LogUtils.i("mData的值" + mData.get(i).getShopProArray().get(j).getProName());
						}
					}
				   /* if(isSolve) {
				        if (checkChoosed()) {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i = 0; i < mList.size(); i++) {
                                if (mList.get(i).isChoosed()) {
                                    stringBuffer.append(mList.get(i).getCartId() + ",");
                                }
                            }
                            stringBuffer = stringBuffer.replace(stringBuffer.toString().lastIndexOf(","), stringBuffer.toString().lastIndexOf(",") + 1, "");
                            LogUtils.i("stringBuffer的值" + stringBuffer);
                            Intent intent = new Intent(ShoppingCartActivity.this, SettlementCartActivity.class);
                            intent.putExtra("cartIdObj", stringBuffer.toString());
                            startActivity(intent);
                        }else{
                            ToastUtils.showToast(ShoppingCartActivity.this,"未选中购买的商品╭(╯^╰)╮");
                        }
                    }*/
				} else {
					ToastUtils.showToast(getActivity(), "请求错误 :)" + response.getMsg());
					changeList.clear();
					for (int i = 0; i < mList.size(); i++) {
						for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
							LogUtils.i("mList的值" + mList.get(i).getShopProArray().get(j).getProName());
						}
					}

					mList.clear();
					mList.addAll(mData);
//					mListAdapter.notifyDataSetChanged();
					for (int i = 0; i < mList.size(); i++) {
						for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
							LogUtils.i("mList的值" + mList.get(i).getShopProArray().get(j).getProName());
						}
					}
				}
				dismissDialog2();
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络请求失败 获取轮播图错误" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(getActivity(), "网络故障,请稍后再试 ");
				}
				changeList.clear();
				for (int i = 0; i < mList.size(); i++) {
					for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
						LogUtils.i("mList的值" + mList.get(i).getShopProArray().get(j).getProName());
					}
				}
				mList.clear();
				mList.addAll(mData);
//				mListAdapter.notifyDataSetChanged();
				for (int i = 0; i < mList.size(); i++) {
					for (int j = 0; j < mList.get(i).getShopProArray().size(); j++) {
						LogUtils.i("mList的值" + mList.get(i).getShopProArray().get(j).getProName());
					}
				}
				dismissDialog2();
			}
		});
	}


	private void dismissDialog() {
		if (deleteDialog != null) {
			deleteDialog.dismiss();
			deleteDialog = null;
		}
	}

	private void dismissDialog2() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

	@Override
	public void onDestroy() {
		dismissDialog();
		dismissDialog2();
		super.onDestroy();
	}


}
