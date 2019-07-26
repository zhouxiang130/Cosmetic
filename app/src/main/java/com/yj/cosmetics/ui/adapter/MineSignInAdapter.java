package com.yj.cosmetics.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.SignInEntity;
import com.yj.cosmetics.ui.activity.mineSignIn.MineSignInActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class MineSignInAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final String TAG = "MineSignInAdapter";
	//	private final List<String> mData;
	private List<SignInEntity.DataBean.SignListBean> mData;
	private MineSignInActivity mContext;
	ProfitDetialClickListener mItemClickListener;
	//	private int mdays;


	public MineSignInAdapter(MineSignInActivity mContext, List<SignInEntity.DataBean.SignListBean> mDatas) {
		this.mContext = mContext;
		this.mData = mDatas;
	}


	public void setOnItemClickListener(ProfitDetialClickListener listener) {
		this.mItemClickListener = listener;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ProfitViewHolder profitViewHolder = null;
		if (viewType == 0) {
			profitViewHolder = new ProfitViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mine_sigin_ticket, parent, false), mItemClickListener);

		}
		return profitViewHolder;
	}

	@Override
	public int getItemViewType(int position) {
		Log.e(TAG, "getItemViewType: " + position);
		return 0;
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

		Log.i(TAG, "onBindViewHolder: " + position + " >>>>> " + mData.get(position).getContent() + " >>>>>>  " + mData.size());

		if (holder instanceof ProfitViewHolder) {
			if (position == 0) {
				((ProfitViewHolder) holder).llLine.setPadding(0, 30, 0, 0);
			}
			if (position == mData.size() - 1) {
				((ProfitViewHolder) holder).buttomLine.setVisibility(View.VISIBLE);
				((ProfitViewHolder) holder).llLine.setPadding(0, 0, 0, 30);
			} else {
				((ProfitViewHolder) holder).buttomLine.setVisibility(View.GONE);
			}

			String isSign = mData.get(position).getIsSign();
			if (!isSign.equals("0")) {
				mContext.setPosition(position);
				((ProfitViewHolder) holder).ivCircle.setBackgroundResource(R.drawable.mine_circle_bg_grey_yellow);
				((ProfitViewHolder) holder).tvTicketClass.setBackgroundResource(R.drawable.shape_corner_oval_three);
				((ProfitViewHolder) holder).ivTicketClass.setBackgroundResource(R.mipmap.qd_jl_true);
			}

			switch (mData.get(position).getType()) {//1折扣券 2代金券3：兑换券 4：积分券5： 金额券
				case "1":
					((ProfitViewHolder) holder).tvTicketPriceIntegral.setVisibility(View.GONE);
					((ProfitViewHolder) holder).tvTicketClass.setText("折扣券");
					break;
				case "2":
					((ProfitViewHolder) holder).tvTicketPriceIntegral.setVisibility(View.GONE);
					((ProfitViewHolder) holder).tvTicketClass.setText("代金券");
					break;
				case "3":
					((ProfitViewHolder) holder).tvTicketPriceIntegral.setVisibility(View.GONE);
					((ProfitViewHolder) holder).tvTicketClass.setText("兑换券");
					break;
				case "4":
					((ProfitViewHolder) holder).tvTicketPriceSymbol.setVisibility(View.GONE);
					((ProfitViewHolder) holder).tvTicketPriceIntegral.setVisibility(View.VISIBLE);
					((ProfitViewHolder) holder).tvTicketPriceIntegral.setText("积分");
					((ProfitViewHolder) holder).tvTicketClass.setText("积分券");
					break;
				case "5":
					((ProfitViewHolder) holder).tvTicketPriceIntegral.setVisibility(View.GONE);
					((ProfitViewHolder) holder).tvTicketClass.setText("金额券");
					break;
			}
			((ProfitViewHolder) holder).tvTicketPrice.setText(mData.get(position).getVal());
			((ProfitViewHolder) holder).tvTicketPriceStandard.setText(mData.get(position).getIntro());
			((ProfitViewHolder) holder).tvTicketClassContent.setText(mData.get(position).getName());
			((ProfitViewHolder) holder).tvTicketClassContents.setText(mData.get(position).getContent());


		}
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	public void setData(int position) {
		if (mData.size() != 0 && mData.size() >= 7) {
			mData.get(position).setIsSign("1");
			notifyDataSetChanged();

		}
	}

	class ProfitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.mine_sign_img_ticket_line)
		ImageView ivCircle;
		@BindView(R.id.mine_sign_img_ticket_class_)
		ImageView ivTicketClass;
		@BindView(R.id.mine_sign_text_ticket_price_symbol)
		TextView tvTicketPriceSymbol;
		@BindView(R.id.mine_sign_text_ticket_price_integral)
		TextView tvTicketPriceIntegral;
		@BindView(R.id.mine_sign_text_ticket_price)
		TextView tvTicketPrice;
		@BindView(R.id.mine_sign_text_ticket_price_standard)
		TextView tvTicketPriceStandard;
		@BindView(R.id.mine_sign_text_ticket_class)
		TextView tvTicketClass;
		@BindView(R.id.mine_sign_text_ticket_class_content)
		TextView tvTicketClassContent;
		@BindView(R.id.mine_sign_text_ticket_class_contents)
		TextView tvTicketClassContents;
		@BindView(R.id.item_mine_sigin_buttom_line)
		View buttomLine;
		@BindView(R.id.item_sign_ll_line)
		LinearLayout llLine;
		private ProfitDetialClickListener mListener;

		public ProfitViewHolder(View itemView, ProfitDetialClickListener listener) {
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

	public interface ProfitDetialClickListener {
		void onItemClick(View view, int postion);
	}
}