package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.AddressEntity;
import com.yj.cosmetics.util.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class StoreAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private String locCity = null;
	private Context mContext;
	AddressClickListener mItemClickListener;
	LocAddressClickListener mAddInterfaces;
	private Interfaces Interfaces;

	private UserUtils mUtils;
	private List<AddressEntity.DataBean.ListBean> mList = new ArrayList<>();

	public StoreAddressAdapter(Context mContext, List<AddressEntity.DataBean.ListBean> mList, UserUtils mUtils, String locCity) {
		this.mContext = mContext;
		this.locCity = locCity;
		this.mList = mList;
		this.mUtils = mUtils;


	}

	public void setOnItemClickListener(AddressClickListener listener) {
		this.mItemClickListener = listener;
	}

	public void setOnLocAddItemClickListener(LocAddressClickListener listener){
		this.mAddInterfaces = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		AddressTitleViewHolder addressViewHolder;
		AddressLocInfoViewHolder addressLocInfoViewHolder;
		AddressInfoViewHolder addressInfoViewHolder;

		if (viewType == 0) {
			addressViewHolder = new AddressTitleViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_store_address_title, parent, false));
			return addressViewHolder;
		} else if (viewType == 1) {
			addressLocInfoViewHolder = new AddressLocInfoViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_store_address_info, parent, false),mAddInterfaces);
			return addressLocInfoViewHolder;
		} else {
			addressInfoViewHolder = new AddressInfoViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_store_address_infos, parent, false), mItemClickListener);
			return addressInfoViewHolder;
		}
	}

	@Override
	public int getItemViewType(int position) {

		if (position == 0) {
			return 0;
		} else if (position == 1) {
			return 1;
		} else if (position == 2) {
			return 0;
		}
		return 2;
	}

	private String title_name = null;

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof AddressTitleViewHolder) {
			if (position == 0) {
				title_name = "当前地址";
			} else {
				title_name = "收货地址";
			}
			((AddressTitleViewHolder) holder).tvTitle.setText(title_name);
		} else if (holder instanceof AddressLocInfoViewHolder) {
			((AddressLocInfoViewHolder) holder).tvTitle.setText(locCity);

			((AddressLocInfoViewHolder) holder).tvLoc.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Interfaces.onClicks(((AddressLocInfoViewHolder) holder).progressBar);//向外暴露接口

				}
			});

		} else if (holder instanceof AddressInfoViewHolder) {



			((AddressInfoViewHolder) holder).tvName.setText(mList.get(position - 3).getReceiverName());
			((AddressInfoViewHolder) holder).tvPhoneNum.setText(mList.get(position - 3).getReceiverTel());
			((AddressInfoViewHolder) holder).tvAddress.setText(mList.get(position - 3).getAddressArea() + mList.get(position - 3).getAddressDetail());
		}
	}

	/**
	 *
	 */
	public interface Interfaces {
		/**
		 *
		 * @param progressBar
		 */
		void onClicks(ProgressBar progressBar);
	}



	/**
	 * @param
	 */
	public void setInterfaces(Interfaces Interfaces) {
		this.Interfaces = Interfaces;
	}




	@Override
	public int getItemCount() {
		if (mList != null && mList.size() > 0) {
			return mList.size() + 3;
		}
		return 2;
	}

	class AddressTitleViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.store_address_tv_title)
		TextView tvTitle;

		public AddressTitleViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);

		}
	}


	class AddressLocInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.store_address_tv_title)
		TextView tvTitle;
		@BindView(R.id.store_address_tv_loc)
		TextView tvLoc;
		@BindView(R.id.progressBar)
		ProgressBar progressBar;

		private  LocAddressClickListener locAddressClickListener;

		public AddressLocInfoViewHolder(View itemView,LocAddressClickListener locAddressClickListener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			this.locAddressClickListener = locAddressClickListener;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			locAddressClickListener.onItemClick(view);
		}
	}


	class AddressInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.store_address_tv_name)
		TextView tvName;
		@BindView(R.id.store_address_tv_phoneNum)
		TextView tvPhoneNum;

		@BindView(R.id.store_address_tv_address)
		TextView tvAddress;
		private AddressClickListener mListener;


		public AddressInfoViewHolder(View itemView, AddressClickListener listener) {
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


	public interface AddressClickListener {
		void onItemClick(View view, int postion);
	}

	public interface LocAddressClickListener {
		void onItemClick(View view);
	}


	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		super.onDetachedFromRecyclerView(recyclerView);
	}
}