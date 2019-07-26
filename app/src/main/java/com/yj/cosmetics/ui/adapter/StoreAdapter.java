package com.yj.cosmetics.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.ShopListEntity;
import com.yj.cosmetics.model.StoreClassEntity;
import com.yj.cosmetics.model.StoreListEntity;
import com.yj.cosmetics.ui.MainActivity;
import com.yj.cosmetics.ui.activity.sotreList.StoreListActivity;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.widget.CustomLinearLayoutManager;
import com.yj.cosmetics.widget.RoundedImageView.RoundedDrawable;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;
import com.yj.cosmetics.widget.WrapContentGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/24 0024.
 */

public class StoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "StoreAdapter";
	private List<ShopListEntity.DataBean.ShopArrayBean> mData = null;
//	private List<StoreListEntity.DataBean.ProductClassifyListBean> mList = null;
	private Activity mContext;
	private ShopListClickListener mItemClickListener;
	private int pos;
	private ViewInterfaces viewInterface;
	private StoreClassEntity storeClassEntity;
	private List<ImageView> points;

	public StoreAdapter(Activity mContext,
	                    /*List<StoreListEntity.DataBean.ProductClassifyListBean> mList,*/
	                    List<ShopListEntity.DataBean.ShopArrayBean> mData) {
		points = new ArrayList<>();//点的集合
		this.mContext = mContext;
//		this.mList = mList;
		this.mData = mData;
		storeClassEntity = new StoreClassEntity();
	}




	public void setOnItemClickListener(ShopListClickListener listener) {
		this.mItemClickListener = listener;
	}

	/**
	 * 单选接口
	 *
	 * @param viewInterfaces
	 */
	public void setViewInterfaces(ViewInterfaces viewInterfaces) {
		this.viewInterface = viewInterfaces;
	}


	/**
	 * 复选框接口
	 */
	public interface ViewInterfaces {
		/**
		 * 组选框状态改变触发的事件
		 */
		void onClicks(int viewId);
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		GridViewHolder gridViewHolder;
		ClassTitleViewHolder classTitleViewHolder;
		StoreItemViewHolder storeItemViewHolder;
		if (viewType == 0) {
			//图标分类
			gridViewHolder = new GridViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_home_gridview, parent, false));
			return gridViewHolder;
		} else if (viewType == 1) {
			//分类标题
			classTitleViewHolder = new ClassTitleViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_class_title, parent, false));
			return classTitleViewHolder;
		} else {
			storeItemViewHolder = new StoreItemViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_store_list_items, parent, false), mItemClickListener);
			return storeItemViewHolder;
		}
	}

	@Override
	public int getItemViewType(int position) {
//		switch (position) {
//			case 0:
//				return 0;
//			case 1:
//				return 1;
//			default:
//				return 2;
//		}
		return 2;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

		if (viewHolder instanceof GridViewHolder) {
/*			List<View> mPagerList = new ArrayList<>();
			//每页数量
			int pageSize = 10;
			LayoutInflater inflater = LayoutInflater.from(mContext);
			//页数
			final int pageCount = (int) Math.ceil(mList.size() * 1.0 / pageSize);

			for (int i = 0; i < pageCount; i++) {
				// 每个页面都是inflate出一个新实例
				WrapContentGridView gridView = (WrapContentGridView) inflater.inflate(R.layout.gridview, ((GridViewHolder) viewHolder).mViewpager, false);
				gridView.setAdapter(new StoreGridviewAdapter(mContext, mList, i, pageSize));
				mPagerList.add(gridView);

				gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

						if (TextUtils.isEmpty(mList.get((int) id).getClassify_id()) && TextUtils.isEmpty(mList.get((int) id).getClassify_img())) {
							Intent intent = new Intent(mContext, MainActivity.class);
							intent.putExtra("page", "1");
							mContext.startActivity(intent);
						} else {
							IntentUtils.IntentToGoodsList(mContext, mList.get((int) id).getClassify_id());
						}
					}
				});
				gridView.setFocusable(false);
			}
			//设置适配器
//			LogUtils.i("pagerlist的长度" + mPagerList.size());
			((GridViewHolder) viewHolder).mViewpager.setAdapter(new ViewPagerAdapter(mPagerList));
			((GridViewHolder) viewHolder).mViewpager.setFocusable(false);

			//设置适配器
//			LogUtils.i("pagerlist的长度" + mPagerList.size());
			if (pageCount > 1) {
				addLunBoPoints(pageCount, ((GridViewHolder) viewHolder).ivIndicator);//添加轮播图的点
			}
			((GridViewHolder) viewHolder).mViewpager.setAdapter(new ViewPagerAdapter(mPagerList));
			((GridViewHolder) viewHolder).mViewpager.setFocusable(false);
			((GridViewHolder) viewHolder).mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				}

				@Override
				public void onPageSelected(int position) {
					for (int i = 0; i < points.size(); i++) {
						if (i == position) {
							points.get(position).setEnabled(true);
						} else {
							points.get(i).setEnabled(false);
						}
					}
				}

				@Override
				public void onPageScrollStateChanged(int state) {
				}
			});*/


		} else if (viewHolder instanceof ClassTitleViewHolder) {

			((ClassTitleViewHolder) viewHolder).tvDefault.setSelected(storeClassEntity.isDefault());
			((ClassTitleViewHolder) viewHolder).tvHighest.setSelected(storeClassEntity.isHighest());
			((ClassTitleViewHolder) viewHolder).tvLately.setSelected(storeClassEntity.isLately());

			((ClassTitleViewHolder) viewHolder).llDefault.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
//					((ClassTitleViewHolder) viewHolder).tvDefault.setSelected(true);
//					((ClassTitleViewHolder) viewHolder).tvHighest.setSelected(false);
//					((ClassTitleViewHolder) viewHolder).tvLately.setSelected(false);
					storeClassEntity.setDefault(true);
					storeClassEntity.setHighest(false);
					storeClassEntity.setLately(false);
					viewInterface.onClicks(1);
				}
			});
			((ClassTitleViewHolder) viewHolder).llHighest.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
//					((ClassTitleViewHolder) viewHolder).tvDefault.setSelected(false);
//					((ClassTitleViewHolder) viewHolder).tvHighest.setSelected(true);
//					((ClassTitleViewHolder) viewHolder).tvLately.setSelected(false);
					storeClassEntity.setDefault(false);
					storeClassEntity.setHighest(true);
					storeClassEntity.setLately(false);
					viewInterface.onClicks(2);
				}
			});
			((ClassTitleViewHolder) viewHolder).llLately.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
//					((ClassTitleViewHolder) viewHolder).tvDefault.setSelected(false);
//					((ClassTitleViewHolder) viewHolder).tvHighest.setSelected(false);
//					((ClassTitleViewHolder) viewHolder).tvLately.setSelected(true);
					storeClassEntity.setDefault(false);
					storeClassEntity.setHighest(false);
					storeClassEntity.setLately(true);
					viewInterface.onClicks(3);
				}
			});


		} else if (viewHolder instanceof StoreItemViewHolder) {

			Log.i(TAG, "onBindViewHolder: " + position);
			pos = position - 0;

		/*	if (mData.get(pos).getReceipt() != null && !mData.get(pos).getReceipt().equals("")) {
				if (mData.get(pos).getReceipt().equals("1")) {
					Log.i(TAG, "Receipt:>>>>>>> " + mData.get(pos).getReceipt());
					if (mData.get(pos).getDeliveryDistanceType().equals("1")) {
						((StoreItemViewHolder) viewHolder).tvStoreStates.setVisibility(View.GONE);
					} else {
						((StoreItemViewHolder) viewHolder).tvStoreStates.setVisibility(View.VISIBLE);
						((StoreItemViewHolder) viewHolder).tvStoreStates.setText("超出配送范围");
					}
				} else {
					((StoreItemViewHolder) viewHolder).tvStoreStates.setVisibility(View.VISIBLE);
					((StoreItemViewHolder) viewHolder).tvStoreStates.setText("休息中");
				}
			}
*/

//			if (mData.get(pos).getShopType() == 1) {
//				((StoreItemViewHolder) viewHolder).tvTitle_.setVisibility(View.GONE);
//			} else {
//				((StoreItemViewHolder) viewHolder).tvTitle_.setVisibility(View.VISIBLE);
//				((StoreItemViewHolder) viewHolder).tvTitle_.setText(mData.get(pos).getShopTypeName());
//			}


			((StoreItemViewHolder) viewHolder).tvStoreName.setText(mData.get(pos).getShopName());
			((StoreItemViewHolder) viewHolder).tvMonSale.setText("销量:" + mData.get(pos).getDetailNumMonth() + "单");
			Glide.with(mContext)
					.load(URLBuilder.getUrl(mData.get(pos).getShopLogo()))
					.asBitmap()
					.centerCrop()
					.error(R.mipmap.default_goods)
					.into(((StoreItemViewHolder) viewHolder).storeIcon);

			if (mData.get(pos).getProductlist() != null && mData.get(pos).getProductlist().size() != 0) {

//				LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//				CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//				((StoreItemViewHolder) viewHolder).recyclerView.setLayoutManager(layoutManager);
//				((StoreItemViewHolder) viewHolder).recyclerView.setAdapter(new StoreGoodListAdapter(mContext,mData.get(pos).getProductlist(),pos));
				((StoreItemViewHolder) viewHolder).mGridView.setAdapter(new MyGridViewAdapter(mContext, mData.get(pos).getProductlist()));
				((StoreItemViewHolder) viewHolder).mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int positions, long id) {
//						Log.i(TAG, "onItemClick: " + mData.get(position).getProductlist().get(positions).getProduct_id());
						IntentUtils.IntentToGoodsDetial(mContext, mData.get(position).getProductlist().get(positions).getProduct_id());
					}
				});
	/*			if (mData.get(pos).getProductlist().size() > 0) {
					Glide.with(mContext)
							.load(URLBuilder.getUrl(mData.get(pos).getProductlist().get(0).getProduct_listImg()))
							.asBitmap()
							.centerCrop()
							.error(R.mipmap.default_goods)
							.into(((StoreItemViewHolder) viewHolder).goodImage1);
				}
				if (mData.get(pos).getProductlist().size() > 1) {
					Glide.with(mContext)
							.load(URLBuilder.getUrl(mData.get(pos).getProductlist().get(1).getProduct_listImg()))
							.asBitmap()
							.centerCrop()
							.error(R.mipmap.default_goods)
							.into(((StoreItemViewHolder) viewHolder).goodImage2);
				}
				if (mData.get(pos).getProductlist().size() > 2) {
					Glide.with(mContext)
							.load(URLBuilder.getUrl(mData.get(pos).getProductlist().get(2).getProduct_listImg()))
							.asBitmap()
							.centerCrop()
							.error(R.mipmap.default_goods)
							.into(((StoreItemViewHolder) viewHolder).goodImage3);
				}*/
			}
//			((StoreItemViewHolder) viewHolder).tvExpenses.setText("起送价: ￥" + mData.get(pos).getServiceStartime());
//			((StoreItemViewHolder) viewHolder).tvDistance.setText("距离:" + mData.get(pos).getJuli());

		}
	}


	@Override
	public int getItemCount() {
		return mData.size();
	}

	public void setData(List<ShopListEntity.DataBean.ShopArrayBean> shopArray) {
		this.mData = shopArray;
		notifyDataSetChanged();
	}

	class GridViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.viewpager)
		ViewPager mViewpager;
		@BindView(R.id.icon_img_indicator)
		LinearLayout ivIndicator;


		public GridViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	class ClassTitleViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.store_list_ll_default)
		LinearLayout llDefault;
		@BindView(R.id.store_list_ll_lately)
		LinearLayout llLately;
		@BindView(R.id.store_list_ll_highest)
		LinearLayout llHighest;


		@BindView(R.id.store_list_tv_default)
		TextView tvDefault;
		@BindView(R.id.store_list_tv_lately)
		TextView tvLately;
		@BindView(R.id.store_list_tv_highest)
		TextView tvHighest;


		public ClassTitleViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	/**
	 * 添加轮播图的点
	 *
	 * @param pageCount
	 * @param ivIndicator
	 */
	private void addLunBoPoints(int pageCount, LinearLayout ivIndicator) {
		ivIndicator.removeAllViews();
		points.clear();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.leftMargin = 15;//设置点的间距
		params.bottomMargin = 5;//设置点距离底部的间距
		params.topMargin = 5;//设置点距离底部的间距

		for (int i = 0; i < pageCount; i++) {
			ImageView imageView = new ImageView(mContext);
//            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//            layoutParams.width = 10;
//            layoutParams.height = 10;
//            imageView.setLayoutParams(layoutParams);
			imageView.setImageResource(R.drawable.lunbo_points_big_images);
			if (i == 0) {
				imageView.setEnabled(true);
			} else {
				imageView.setEnabled(false);
			}
			ivIndicator.addView(imageView, params);
			points.add(imageView);//添加到点的集合
		}
	}

	class StoreItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	/*	@BindView(R.id.store_list_tv_title)
		TextView tvTitle;
		@BindView(R.id.store_list_tv_sales)
		TextView tvSales;
		@BindView(R.id.store_list_tv_expenses)
		TextView tvExpenses;
		@BindView(R.id.store_list_tv_wuliu)
		TextView tvWulliu;
		@BindView(R.id.store_list_tv_distance)
		TextView tvDistance;
		@BindView(R.id.store_list_tv_title_)
		TextView tvTitle_;
		@BindView(R.id.item_tv_store_states)
		TextView tvStoreStates;
		@BindView(R.id.store_list_iv)
		ImageView ivIcon;*/


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

		/*@BindView(R.id.store_list_iv1)
		ImageView goodImage1;
		@BindView(R.id.store_list_iv2)
		ImageView goodImage2;
		@BindView(R.id.store_list_iv3)
		ImageView goodImage3;*/

//		public List<ImageView> goodList = new ArrayList<>();


		private ShopListClickListener mListener;

		public StoreItemViewHolder(View itemView, ShopListClickListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = listener;
//			goodList.add(goodImage1);
//			goodList.add(goodImage2);
//			goodList.add(goodImage3);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			if (mListener != null) {
				mListener.onItemClick(view, getPosition());
			}
		}
	}

	public interface ShopListClickListener {
		void onItemClick(View view, int postion);
	}

}
