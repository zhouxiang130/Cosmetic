package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineCollectEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomNormalContentDialog;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Suo on 2017/4/17.
 */

public class MineCollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private int flag = 0;
	private Context mContext;
	List<MineCollectEntity.DataBean.ListBean> mList;
	SpendDetialClickListener mItemClickListener;
	CustomNormalContentDialog mDeleteDialog;
	UserUtils mUtils;

	public MineCollectionAdapter(Context mContext, List<MineCollectEntity.DataBean.ListBean> mList, UserUtils mUtils, int flag) {
		this.mContext = mContext;
		this.mList = mList;
		this.mUtils = mUtils;
		this.flag = flag;
		mDeleteDialog = new CustomNormalContentDialog(mContext);
	}

	public void setOnItemClickListener(SpendDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		CollectionViewHolder collectionViewHolder;
		collectionViewHolder = new CollectionViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mine_collection, parent, false), mItemClickListener);
		return collectionViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof CollectionViewHolder) {
			if (flag == 1) {
				((CollectionViewHolder) holder).mllall.setVisibility(View.GONE);
				((CollectionViewHolder) holder).rlCollStore.setVisibility(View.VISIBLE);
				((CollectionViewHolder) holder).tvFreight.setVisibility(View.VISIBLE);


				((CollectionViewHolder) holder).tvStoreName.setText(mList.get(position).getShopName());
				((CollectionViewHolder) holder).tvMonSale.setText("销量:" + mList.get(position).getDetailNumMonth() + "单");
				Glide.with(mContext)
						.load(URLBuilder.getUrl(mList.get(position).getShopImg()))
						.asBitmap()
						.centerCrop()
						.error(R.mipmap.default_goods)
						.into(((CollectionViewHolder) holder).storeIcon);

				if (mList.get(position).getProductlist() != null && mList.get(position).getProductlist().size() != 0) {

					((CollectionViewHolder) holder).mGridView.setAdapter(new MyCollStoreListAdapter(mContext, mList.get(position).getProductlist()));
					((CollectionViewHolder) holder).mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int positions, long id) {
//						Log.i(TAG, "onItemClick: " + mData.get(position).getProductlist().get(positions).getProduct_id());
							IntentUtils.IntentToGoodsDetial(mContext, mList.get(position).getProductlist().get(positions).getProduct_id());
						}
					});
				}

			} else {
				((CollectionViewHolder) holder).mllall.setVisibility(View.VISIBLE);
				((CollectionViewHolder) holder).rlCollStore.setVisibility(View.GONE);
//				((CollectionViewHolder) holder).tvMonthlySales.setVisibility(View.GONE);
//				((CollectionViewHolder) holder).tvuWuLiu.setVisibility(View.GONE);
				((CollectionViewHolder) holder).llPicInfo.setVisibility(View.VISIBLE);

				((CollectionViewHolder) holder).tvOp.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				((CollectionViewHolder) holder).tvTitle.setText(mList.get(position).getProductName());
				((CollectionViewHolder) holder).tvOp.setText("￥" + mList.get(position).getProductOrginal());
				((CollectionViewHolder) holder).tvPrice.setText(mList.get(position).getProductCurrent());
				((CollectionViewHolder) holder).tvJudge.setText("销量:" + mList.get(position).getDetailNumMonth());
//				if (mList.get(position).getProductHot().equals("2")) {
//					//2是 1否
				((CollectionViewHolder) holder).tvHot.setVisibility(View.VISIBLE);
				((CollectionViewHolder) holder).tvHot.setText(mList.get(position).getShopName());
//				} else {
//					((CollectionViewHolder) holder).tvHot.setVisibility(View.GONE);
//				}
				Glide.with(mContext)
						.load(URLBuilder.getUrl(mList.get(position).getProductListimg()))
						.asBitmap()
						.centerCrop()
						.error(R.mipmap.default_goods)
						.into(((CollectionViewHolder) holder).iv);

			}
			((CollectionViewHolder) holder).llDelete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (flag == 1) {
						showDeleteDialog(mList.get(position).getShopId(), position, flag);
					} else {
						showDeleteDialog(mList.get(position).getProductId(), position, flag);
					}
				}
			});
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	class CollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.item_mine_collection_tvop)
		TextView tvOp;
		@BindView(R.id.mine_collection_rl_all)
		RelativeLayout rlAll;
		@BindView(R.id.item_mine_collection_iv)
		ImageView iv;
		@BindView(R.id.item_mine_collection_title)
		TextView tvTitle;
		@BindView(R.id.item_mine_collection_tvhot)
		TextView tvHot;
		@BindView(R.id.item_mine_collection_judge)
		TextView tvJudge;
		@BindView(R.id.item_mine_collection_price)
		TextView tvPrice;
		@BindView(R.id.mine_collection_ll_delete)
		LinearLayout llDelete;
		@BindView(R.id.mine_collection_layout)
		LinearLayout mllall;
		@BindView(R.id.ll_product_price_info)
		LinearLayout llPicInfo;

		@BindView(R.id.rl_store_gride)
		RelativeLayout rlCollStore;

		@BindView(R.id.item_mine_collection_freight)
		TextView tvFreight;


		@BindView(R.id.frag_mine_login_iv)
		RoundedImageView storeIcon;
		@BindView(R.id.tv_store_name)
		TextView tvStoreName;
		@BindView(R.id.tv_store_mon_sale)
		TextView tvMonSale;
		@BindView(R.id.tv_go_to_soter_detail)
		TextView tvGoDetail;
		//		@BindView(R.id.store_recyle_view)
//		RecyclerView recyclerView;
		@BindView(R.id.gridView)
		GridView mGridView;

		@BindView(R.id.item_mine_collection_monthly_sales)
		TextView tvMonthlySales;

		@BindView(R.id.item_mine_collection_wuliu)
		TextView tvuWuLiu;

		private SpendDetialClickListener mListener;

		public CollectionViewHolder(View itemView, SpendDetialClickListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = listener;
			//布局文件 添加点击事件
			mllall.setOnClickListener(this);
			rlCollStore.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onItemClick(v, getPosition(), flag);
			}
		}
	}

	public interface SpendDetialClickListener {
		void onItemClick(View view, int postion, int flag);
	}

	private void showDeleteDialog(final String Id, final int position, final int flag) {
		if (mDeleteDialog == null) {
			mDeleteDialog = new CustomNormalContentDialog(mContext);
		}
		if (!mDeleteDialog.isShowing()) {
			mDeleteDialog.show();
		}
		if (flag == 1) {
			mDeleteDialog.getTvTitle().setText("确定删除该店铺吗?");
			mDeleteDialog.getTvContent().setText("删除后可再次添加哦~");
		} else {
			mDeleteDialog.getTvTitle().setText("确定删除该商品吗?");
			mDeleteDialog.getTvContent().setText("删除后可重新进行下单哦~");
		}
		mDeleteDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doAsyncCollect(Id, position, flag);
				dismissDialog();
			}
		});
	}

	private String POSTFIX = null;

	public void doAsyncCollect(String Id, final int position, int flag) {
		Map<String, String> map = new HashMap<>();
		if (flag == 1) {
			map.put("shopId", Id);
			POSTFIX = "/phone/homePageTwo/saveDeleteShopCollection";
		} else {
			map.put("proId", Id);
			POSTFIX = "/phone/homePage/saveDeleteCollection";
		}

		map.put("userId", mUtils.getUid());
		LogUtils.i("传输的网络数据" + URLBuilder.format(map));

		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + POSTFIX)
				.addParams("data", URLBuilder.format(map))
				.tag(this).build().execute(new Utils.MyResultCallback<NormalEntity>() {

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
					if (response.getData() != null) {
						if (response.getData().equals("1")) {
						} else {
							ToastUtils.showToast(mContext, "取消收藏");
							mList.remove(position);
							notifyDataSetChanged();
						}
					}
				} else {
					ToastUtils.showToast(mContext, "网络故障 :)" + response.getMsg());
				}
			}

			@Override
			public void onError(Call call, Exception e) {
				super.onError(call, e);
				LogUtils.i("网络请求失败 " + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(mContext, "网络故障,请稍后再试 ");
				}
			}
		});
	}

	private void dismissDialog() {
		if (mDeleteDialog != null) {
			mDeleteDialog.dismiss();
			mDeleteDialog = null;
		}
	}

	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		dismissDialog();
		super.onDetachedFromRecyclerView(recyclerView);
	}
}