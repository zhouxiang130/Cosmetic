package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.Key;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.AddressEntity;
import com.yj.cosmetics.model.NormalEntity;
import com.yj.cosmetics.ui.activity.MineAddressNewActivity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.util.UserUtils;
import com.yj.cosmetics.util.Utils;
import com.yj.cosmetics.widget.Dialog.CustomNormalDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
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

public class ManageAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context mContext;
	XRecyclerView mRecyclerView;
	LinearLayout llNomore;

	ManageAddressClickListener mItemClickListener;
	CustomNormalDialog deleteDialog;
	private UserUtils mUtils;
	private List<AddressEntity.DataBean.ListBean> mList = new ArrayList<>();

	public ManageAddressAdapter(Context mContext, List<AddressEntity.DataBean.ListBean> mList, UserUtils mUtils,
	                            XRecyclerView mRecyclerView, LinearLayout llNomore) {
		this.mContext = mContext;
		this.mList = mList;
		this.mUtils = mUtils;
		this.mRecyclerView = mRecyclerView;
		this.llNomore = llNomore;
		deleteDialog = new CustomNormalDialog(mContext);
	}

	public void setOnItemClickListener(ManageAddressClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		AddressViewHolder addressViewHolder;
		ConfirmViewHolder confirmViewHolder;
		if (viewType == 0) {
			addressViewHolder = new AddressViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_mine_address_manage, parent, false), mItemClickListener);
			return addressViewHolder;
		} else {
			confirmViewHolder = new ConfirmViewHolder(LayoutInflater
					.from(mContext).inflate(R.layout.item_mine_address_manage_confirm, parent, false));
			return confirmViewHolder;
		}
	}

	@Override
	public int getItemViewType(int position) {

		if (position == mList.size()) {
			if (mList != null && mList.size() > 0) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof AddressViewHolder) {
			((AddressViewHolder) holder).tvName.setText(mList.get(position).getReceiverName());
			((AddressViewHolder) holder).tvTel.setText(mList.get(position).getReceiverTel());
			((AddressViewHolder) holder).tvAddress.setText("收货地址：" + mList.get(position).getAddressArea() + mList.get(position).getAddressDetail());

			if (mList.get(position).getAddressDefault().equals("1")) {
				((AddressViewHolder) holder).cbCheck.setChecked(true);
			} else {
				((AddressViewHolder) holder).cbCheck.setChecked(false);
			}

//			if (mList.get(position).getType() != null && mList.get(position).getType().equals("1")) {
			((AddressViewHolder) holder).rlMask.setVisibility(View.GONE);
			((AddressViewHolder) holder).rlCheck.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (((AddressViewHolder) holder).cbCheck.isChecked()) {
						LogUtils.i("我进入isChecked了");
						return;
					}
					setDefaultAddress(position);
				}
			});

			((AddressViewHolder) holder).tvEdit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(mContext, MineAddressNewActivity.class);
					intent.putExtra("tag", "edit");
					intent.putExtra("name", mList.get(position).getReceiverName());
					intent.putExtra("tel", mList.get(position).getReceiverTel());
					intent.putExtra("area", mList.get(position).getAddressArea());
					intent.putExtra("detial", mList.get(position).getAddressDetail());
					intent.putExtra("default", mList.get(position).getAddressDefault());
					intent.putExtra("addressId", mList.get(position).getAddressId());
					mContext.startActivity(intent);
				}
			});
			((AddressViewHolder) holder).tvDelete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					showDeleteDialog(position);

				}
			});
//			} else {
//				((AddressViewHolder) holder).rlMask.setVisibility(View.VISIBLE);
//			}


		} else if (holder instanceof ConfirmViewHolder) {
			((ConfirmViewHolder) holder).btnConfirm.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, MineAddressNewActivity.class);
					intent.putExtra("tag", "new");
					mContext.startActivity(intent);
				}
			});
		}
	}

	@Override
	public int getItemCount() {
		if (mList != null && mList.size() > 0) {
			return mList.size() + 1;
		}
		return 0;
	}

	class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.manage_address_checkrl)
		RelativeLayout rlCheck;
		@BindView(R.id.manage_address_cb)
		CheckBox cbCheck;
		@BindView(R.id.manage_address_name)
		TextView tvName;
		@BindView(R.id.manage_address_tel)
		TextView tvTel;
		@BindView(R.id.manage_address_address)
		TextView tvAddress;
		@BindView(R.id.manage_address_delete)
		TextView tvDelete;
		@BindView(R.id.manage_address_edit)
		TextView tvEdit;
		@BindView(R.id.rl_address_masking)
		RelativeLayout rlMask;

		private ManageAddressClickListener mListener;

		public AddressViewHolder(View itemView, ManageAddressClickListener listener) {
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

	class ConfirmViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.item_mine_manage_confirm)
		Button btnConfirm;

		public ConfirmViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

	}

	public interface ManageAddressClickListener {
		void onItemClick(View view, int postion);
	}

	private void doAsyncDeleteAddress(final int position) {
		Map<String, String> map = new HashMap<>();
		map.put("addressId", mList.get(position).getAddressId());
		LogUtils.i("删除地址传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/userAddressDelete").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {
			@Override
			public void onError(Call call, Exception e) {
				LogUtils.i("网络故障" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(mContext, "网络故障,请稍后再试");
				}

				super.onError(call, e);
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				if (response.getCode().equals(response.HTTP_OK)) {
					mList.remove(position);
					notifyDataSetChanged();
					if (mList.size() == 0) {
						mRecyclerView.setVisibility(View.GONE);
						llNomore.setVisibility(View.VISIBLE);
					}
					ToastUtils.showToast(mContext, "删除成功");
				} else {
					ToastUtils.showToast(mContext, response.getCode() + " :) " + response.getMsg());
				}
			}
		});
	}

	private void setDefaultAddress(final int position) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", mUtils.getUid());
		map.put("addressId", mList.get(position).getAddressId());
		LogUtils.i("设置默认地址传输的值" + URLBuilder.format(map));
		OkHttpUtils.post().url(URLBuilder.URLBaseHeader + "/phone/user/updateAddressDefault").tag(this)
				.addParams(Key.data, URLBuilder.format(map))
				.build().execute(new Utils.MyResultCallback<NormalEntity>() {
			@Override
			public void onError(Call call, Exception e) {
				LogUtils.i("网络故障" + e);
				if (call.isCanceled()) {
					call.cancel();
				} else {
					ToastUtils.showToast(mContext, "网络故障,请稍后再试");
				}
				super.onError(call, e);
			}

			@Override
			public NormalEntity parseNetworkResponse(Response response) throws Exception {
				String json = response.body().string().trim();
				LogUtils.i("json的值" + json);
				return new Gson().fromJson(json, NormalEntity.class);
			}

			@Override
			public void onResponse(NormalEntity response) {
				LogUtils.i("我进入response了");
				if (response != null && response.getCode().equals(response.HTTP_OK)) {
					LogUtils.i("我在response里边了");
					for (int i = 0; i < mList.size(); i++) {
						if (mList.get(i).getAddressDefault().equals("1")) {
							mList.get(i).setAddressDefault("2");
							break;
						}
					}
					mList.get(position).setAddressDefault("1");
					notifyDataSetChanged();
					ToastUtils.showToast(mContext, "默认地址修改成功");
					LogUtils.i("我地址修改成功了");
				} else {
					ToastUtils.showToast(mContext, response.getCode() + " :) " + response.getMsg());
				}
			}
		});
	}

	private void showDeleteDialog(final int position) {

		if (deleteDialog == null) {
			deleteDialog = new CustomNormalDialog(mContext);
		}
		if (!deleteDialog.isShowing()) {
			deleteDialog.show();
		}
		deleteDialog.getTvTitle().setText("确认删除该收货地址?");
		deleteDialog.getTvConfirm().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				doAsyncDeleteAddress(position);
				deleteDialog.dismiss();
			}
		});

	}

	private void dismissDialog() {
		if (deleteDialog != null) {
			deleteDialog.dismiss();
			deleteDialog = null;
		}
	}

	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		dismissDialog();
		super.onDetachedFromRecyclerView(recyclerView);
	}
}