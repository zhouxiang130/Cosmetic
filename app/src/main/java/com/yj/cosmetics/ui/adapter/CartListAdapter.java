package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.CartsEntity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class CartListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
		implements MineRefundListGoodsDetailAdapterss.CheckInterface,
		MineRefundListGoodsDetailAdapterss.ModifyCountInterface, MineRefundListGoodsDetailAdapterss.ProfitDetialClickListener {
	private Context mContext;
	List<CartsEntity.DataBean.ProCartsBean> mList;
	private String limit;
	List<MineRefundListGoodsDetailAdapterss> mAdapters;
	ProfitDetialClickListener mItemClickListener;
	ProductDetailClickListener mListener;
	private CheckInterface checkInterface;
	private CheckInterfaces checkInterfaces;
	private ModifyCountInterface modifyCountInterface;
	private MineRefundListGoodsDetailAdapterss adapters;
	private int position;
	private boolean isShow;
	private boolean check;
	private boolean isChecks = true;
	private int positions;
//	private String receipt;


	public CartListAdapter(Context mContext, List<CartsEntity.DataBean.ProCartsBean> mList) {
		this.mContext = mContext;
		this.mList = mList;
		mAdapters = new ArrayList();


	}

	public void setOnItemClickListener(ProfitDetialClickListener listener) {
		this.mItemClickListener = listener;
	}

	public void setOnItemsClickListener(ProductDetailClickListener listener) {
		this.mListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		CartHeadViewHolder cartHeadViewHolder;
		CartItemViewHolder cartItemViewHolder;
		if (viewType == 0) {
			cartHeadViewHolder = new CartHeadViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_cart_list_head, parent, false), mItemClickListener);
			return cartHeadViewHolder;
		} else {
			cartItemViewHolder = new CartItemViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_cart_gridview_item, parent, false), mItemClickListener);
			return cartItemViewHolder;
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (TextUtils.isEmpty(limit)) {
			return 1;
		} else {
			if (position == 0) {
				return 0;
			} else {
				return 1;
			}
		}
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof CartHeadViewHolder) {
			((CartHeadViewHolder) holder).tvLimit.setText("满" + limit + "元免运费");
		} else if (holder instanceof CartItemViewHolder) {
			final int pos;
			if (TextUtils.isEmpty(limit)) {
				pos = position;
			} else {
				pos = position - 1;
			}
			this.position = pos;

			if (mList.get(pos).getShopProArray().size() != 0) {

				((CartItemViewHolder) holder).tvShopName.setText(mList.get(pos).getShopName());

				if (mList.get(pos).getShopImg() != null) {
					Glide.with(mContext)
							.load(URLBuilder.getUrl(mList.get(pos).getShopImg()))
							.asBitmap()
							.centerCrop()
							.error(R.mipmap.default_goods)
							.into(((CartItemViewHolder) holder).ivShopIcon);
				}

				if (positions == pos) {
					((CartItemViewHolder) holder).cbCheck.setChecked(check);
				}

				if (mList.get(pos).getShopId() != null) {
					((CartItemViewHolder) holder).ivMore.setVisibility(View.VISIBLE);
				} else {
					((CartItemViewHolder) holder).ivMore.setVisibility(View.GONE);
				}

				if (mList.get(pos).getShopProArray().size() == 1) {
					if (mList.get(pos).getShopProArray().get(0).getProductState() == 0) {
						((CartItemViewHolder) holder).cbCheck.setEnabled(true);
					} else {
						((CartItemViewHolder) holder).cbCheck.setEnabled(false);
//						((CartItemViewHolder) holder).cbCheck.setButtonDrawable(R.drawable.selector_checkbox_orders);
					}
				}


			/*	if (mList.get(pos).getReceipt() != null && !mList.get(pos).getReceipt().equals("")) {
					if (mList.get(pos).getReceipt().equals("1")) {
						((CartItemViewHolder) holder).tvStoreState.setVisibility(View.GONE);
						((CartItemViewHolder) holder).cbCheck.setEnabled(true);

					} else {
						((CartItemViewHolder) holder).tvStoreState.setVisibility(View.VISIBLE);
						((CartItemViewHolder) holder).cbCheck.setEnabled(false);
//						((CartItemViewHolder) holder).cbCheck.setButtonDrawable(R.drawable.selector_checkbox_orders);
					}
				}*/

				((CartItemViewHolder) holder).cbCheck.setChecked(mList.get(pos).isChoosed());
				//选择按钮
				((CartItemViewHolder) holder).cbCheck.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						LogUtils.e("onClick: " + pos + ">>>>>>>" + mList.get(pos).getShopProArray().size());
						checkInterfaces.checkGroup(pos, ((CheckBox) view).isChecked());//向外暴露接口
					}
				});

				LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
				((CartItemViewHolder) holder).gridView.setLayoutManager(layoutManager);
//				receipt = mList.get(pos).getReceipt();
				adapters = new MineRefundListGoodsDetailAdapterss(mContext, mList.get(pos).getShopProArray(), pos, isShow);
				((CartItemViewHolder) holder).gridView.setAdapter(adapters);
				adapters.setCheckInterface(this);
				adapters.setModifyCountInterface(this);
				adapters.setOnItemClickListener(this);
			}
		}
	}

	public void setNewData(List<CartsEntity.DataBean.ProCartsBean> newData) {
//		mAdapters.clear();
		this.mList.clear();
		this.mList = newData;
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		if (mList != null && mList.size() > 0) {
			if (TextUtils.isEmpty(limit)) {
				return mList.size();
			} else {
				return mList.size() + 1;
			}
		}
		return 0;
	}


	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void setCheck(int pos, boolean check) {
		this.positions = pos;
		this.check = check;
	}


//	public void setNotifyDataSetChanged() {
//		for (MineRefundListGoodsDetailAdapterss adapter : mAdapters) {
//			adapter.notifyDataSetChanged();
//		}
//	}

	@Override
	public void checkGroup(int pos, int position, boolean isChecked) {
		checkInterface.checkGroup(pos, position, isChecked);//向外暴露接口
	}


	@Override
	public void doIncrease(int pos, int position, View showCountView, boolean isChecked) {
		modifyCountInterface.doIncrease(pos, position, showCountView, isChecked);
	}

	@Override
	public void doDecrease(int pos, int position, View showCountView, boolean isChecked) {
		modifyCountInterface.doDecrease(pos, position, showCountView, isChecked);
	}

	@Override
	public void childDelete(int pos, int position) {
		modifyCountInterface.childDelete(pos, position);
	}

	public int getPosition() {
		return position;
	}

	public int getGoodPosition() {
		return adapters.getPosition();
	}


	class CartHeadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.cart_list_head_tv_limit)
		TextView tvLimit;
		@BindView(R.id.cart_list_head_tv_tag)
		TextView tvTag;

		private ProfitDetialClickListener mListener;

		public CartHeadViewHolder(View itemView, ProfitDetialClickListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = listener;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onItemClick(v, getPosition());
			}
		}
	}

	@Override
	public void onItemClick(View view, int postion, int pos) {
		mListener.onItemClicks(view, postion, pos);

	}

	class CartItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.refound_detial_tv_normal_states)
		TextView tvShopName;
		@BindView(R.id.refound_detial_tv_store_state)
		TextView tvStoreState;

		@BindView(R.id.frag_mine_login_iv)
		RoundedImageView ivShopIcon;
		@BindView(R.id.item_cart_cb)
		CheckBox cbCheck;
		@BindView(R.id.gridView)
		RecyclerView gridView;
		@BindView(R.id.refound_detial_rl_normal_states)
		RelativeLayout rlState;
		@BindView(R.id.image_icon_more)
		ImageView ivMore;


		private ProfitDetialClickListener mListener;

		public CartItemViewHolder(View itemView, ProfitDetialClickListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.mListener = listener;
			rlState.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {

			if (mListener != null) {
				mListener.onItemClick(v, getPosition());
			}
		}
	}

	public interface ProfitDetialClickListener {
		void onItemClick(View view, int postion);
	}

	public interface ProductDetailClickListener {
		void onItemClicks(View view, int postion, int pos);
	}


	/**
	 * 单选接口
	 *
	 * @param checkInterface
	 */
	public void setCheckInterface(CheckInterface checkInterface) {
		this.checkInterface = checkInterface;
	}

	/**
	 * 单选接口
	 *
	 * @param checkInterfaces
	 */
	public void setCheckInterfaces(CheckInterfaces checkInterfaces) {
		this.checkInterfaces = checkInterfaces;
	}

	/**
	 * 改变商品数量接口
	 *
	 * @param modifyCountInterface
	 */
	public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
		this.modifyCountInterface = modifyCountInterface;
	}


	/**
	 * 是否显示可编辑
	 *
	 * @param flag
	 */
	public void isShow(boolean flag) {
		this.isShow = flag;
//		for (int i = 0; i < mList.size(); i++) {
//			mAdapters.get(i).isShow(flag);
//		}
		notifyDataSetChanged();
	}

	/**
	 * 复选框接口
	 */
	public interface CheckInterface {
		/**
		 * 组选框状态改变触发的事件
		 *
		 * @param position  元素位置
		 * @param isChecked 元素选中与否
		 */
		void checkGroup(int pos, int position, boolean isChecked);
	}

	/**
	 * 复选框接口
	 */
	public interface CheckInterfaces {
		/**
		 * 组选框状态改变触发的事件
		 *
		 * @param pos       元素位置
		 * @param isChecked 元素选中与否
		 */
		void checkGroup(int pos, boolean isChecked);
	}


	/**
	 * 改变数量的接口
	 */
	public interface ModifyCountInterface {
		/**
		 * 增加操作
		 *
		 * @param pos
		 * @param position      组元素位置
		 * @param showCountView 用于展示变化后数量的View
		 * @param isChecked     子元素选中与否
		 */
		void doIncrease(int pos, int position, View showCountView, boolean isChecked);

		/**
		 * 删减操作
		 *
		 * @param pos
		 * @param position      组元素位置
		 * @param showCountView 用于展示变化后数量的View
		 * @param isChecked     子元素选中与否
		 */
		void doDecrease(int pos, int position, View showCountView, boolean isChecked);

		/**
		 * 删除子item
		 *
		 * @param pos
		 * @param position
		 */
		void childDelete(int pos, int position);
	}
}