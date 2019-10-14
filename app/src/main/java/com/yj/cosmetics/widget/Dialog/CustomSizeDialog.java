package com.yj.cosmetics.widget.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.GoodsEntitys;
import com.yj.cosmetics.model.GoodsSaleEntity;
import com.yj.cosmetics.ui.adapter.SizeDialogAdapter;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.yj.cosmetics.widget.CustomViewGroup.CustomSizeDialogViewGroup;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;

import java.util.List;


/**
 * Created by Suo on 2017/4/19.
 */

public class CustomSizeDialog extends Dialog {
	SizeDialogAdapter dialogAdapter;
	private Context context;
	private GoodsEntitys.DataBeanX mData;
	private List<GoodsSaleEntity.GoodsSaleData> mSale;
	private CustomSizeDialogViewGroup.OnGroupItemClickListener listener;
	RecyclerView recyclerView;
	private View viewMinus;
	private View viewAdd;
	private TextView tvCount;
	private RoundedImageView ivHead;
	private TextView tvPrice;
	private TextView tvStock;
	private Button btnCart;
	private Button btnBuy;
	private Button btnSoldOut;
	private LinearLayout llBottom;
	private RelativeLayout rlChange;
	private LinearLayout llClose;
	private TextView tvDes;
	private TextView tvLimit;
	private int count = 1;
	private String productMaxpurchase;
	/* private doCountClickListener doCountClickListener;*/

	//为防止出错 将style的dialog随意指向了一个style 如有需要可自己创建
	public CustomSizeDialog(Context context, CustomSizeDialogViewGroup.OnGroupItemClickListener listener,
	                        GoodsEntitys.DataBeanX mData, List<GoodsSaleEntity.GoodsSaleData> mSale) {
		super(context, R.style.Goods_Size_Dialog);
		this.context = context;
		this.listener = listener;
		this.mData = mData;
		this.mSale = mSale;
		setCustomDialog();
	}

	private void setCustomDialog() {
		final View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_size_layout, null);
		ivHead = (RoundedImageView) mView.findViewById(R.id.dialog_size_iv);
		tvPrice = (TextView) mView.findViewById(R.id.dialog_size_price);
		tvStock = (TextView) mView.findViewById(R.id.dialog_size_stock);
		viewMinus = mView.findViewById(R.id.dialog_size_minus);
		viewAdd = mView.findViewById(R.id.dialog_size_add);
		tvCount = (TextView) mView.findViewById(R.id.dialog_size_tvCount);
		btnCart = (Button) mView.findViewById(R.id.dialog_size_cart);
		btnBuy = (Button) mView.findViewById(R.id.dialog_size_buy);
		btnSoldOut = (Button) mView.findViewById(R.id.dialog_size_soldout);
		llBottom = (LinearLayout) mView.findViewById(R.id.dialog_size_bottom);
		rlChange = (RelativeLayout) mView.findViewById(R.id.dialog_size_rl2);
		llClose = (LinearLayout) mView.findViewById(R.id.dialog_size_close);
		tvDes = (TextView) mView.findViewById(R.id.dialog_size_des);
		tvLimit = (TextView) mView.findViewById(R.id.dialog_size_tv_limit);
		recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
		View vFloatLayer = (View) mView.findViewById(R.id.ll_all_buttom_float_layer);
		LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
		//@TODO ----- bug修改处

//		if (!TextUtils.isEmpty(mData.getEndTimeStyle())) {
//			if (mData.getEndTimeStyle().equals("yes")) {//
//				btnCart.setVisibility(View.GONE);
//				btnBuy.setBackgroundResource(R.drawable.shape_corner_six);
//				btnBuy.setEnabled(false);
//				btnBuy.setText("活动结束");
//			} else {
//			}
//		}

		if (mData.getData().getProductState() != null && !mData.getData().getProductState().equals("")) {
			if (mData.getData().getProductState().equals("0")) {
				vFloatLayer.setVisibility(View.GONE);
				btnBuy.setEnabled(true);
				btnCart.setEnabled(true);
			} else {
				vFloatLayer.setVisibility(View.VISIBLE);
				btnBuy.setEnabled(false);
				btnCart.setEnabled(false);
			}
		}
//				vFloatLayer.setVisibility(View.VISIBLE);
//				btnBuy.setEnabled(false);
//				btnCart.setEnabled(false);

		dialogAdapter = new SizeDialogAdapter(context, mSale, listener);
		recyclerView.setAdapter(dialogAdapter);
		recyclerView.post(new Runnable() {
			@Override
			public void run() {
				LogUtils.i("reycclerview的高" + recyclerView.getHeight());
				if (recyclerView.getHeight() > context.getResources().getDimension(R.dimen.dis180)) {
					LogUtils.i("我进入大于180了" + recyclerView.getHeight());
					LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
					layoutParams.height = Math.round(context.getResources().getDimension(R.dimen.dis180));
					recyclerView.setLayoutParams(layoutParams);
				}
			}
		});
		tvPrice.setText(mData.getData().getProductCurrent());
		tvStock.setText(mData.getData().getProductStock());
		productMaxpurchase = mData.getData().getProductMaxpurchase();
		if (productMaxpurchase != null && !productMaxpurchase.equals("")) {
			tvLimit.setText("限购" + mData.getData().getProductMaxpurchase() + "件");
		} else {
			tvLimit.setText("购买数量不限");

		}
		Glide.with(context)
				.load(URLBuilder.getUrl(mData.getData().getProductListimg()))
				.centerCrop()
				.error(R.mipmap.default_goods)
				.into(ivHead);
		viewMinus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (count <= 1) {
					return;
				}
				count--;
				tvCount.setText(count + "");
			}
		});
		viewAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (count >= Integer.parseInt(mData.getData().getProductStock())) {
					ToastUtils.showToast(context, "库存不够咯 ^.^");
					return;
				}
				String productMaxpurchase = mData.getData().getProductMaxpurchase();
				if (productMaxpurchase != null && !productMaxpurchase.equals("")) {
					if (count >= Integer.parseInt(mData.getData().getProductMaxpurchase())) {
						ToastUtils.showToast(context, "最多只能购买" + mData.getData().getProductMaxpurchase() + "件哦 ^.^");
						return;
					}
				}
				count++;
				tvCount.setText(count + "");
			}
		});
		llClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				cancel();
			}
		});
		if (mSale != null && mSale.size() > 0) {
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < mSale.size(); i++) {
				stringBuffer.append(mSale.get(i).getPropesName());
			}
			tvDes.setText("请选择" + stringBuffer);
		}
		checkView(mData.getData().getProductStock());
		super.setContentView(mView);
	}
	/*public void setOnGridItemClickListener(GridView.OnItemClickListener listener){
	    recyclerView.setOnItemClickListener(listener);
    }*/

	public Button getBtnCart() {
		return btnCart;
	}

	public Button getBtnBuy() {
		return btnBuy;
	}


	public CharSequence getGoodsCount() {
		return tvCount.getText();
	}

	@Override
	public void setContentView(int layoutResID) {
	}

	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params) {

	}

	@Override
	public void setContentView(View view) {
	}

	@Override
	public void show() {
		super.show();
		/**
		 * 设置宽度全屏，要设置在show的后面
		 */
		Window dialogWindow = this.getWindow();
		dialogWindow.setWindowAnimations(R.style.dialog_anim_style);
		WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
		layoutParams.gravity = Gravity.BOTTOM;
		layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		getWindow().getDecorView().setPadding(0, 0, 0, 0);

		getWindow().setAttributes(layoutParams);

	}

	public void resetView(String url, String price, final String num) {
		Glide.with(context)
				.load(URLBuilder.getUrl(url))
				.error(R.mipmap.default_goods)
				.centerCrop()
				.into(ivHead);
		tvPrice.setText(price);
		tvStock.setText(num);
		checkView(num);
		viewAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (count >= Integer.parseInt(num)) {
					ToastUtils.showToast(context, "库存不够咯 ^.^");
					return;
				}
				String productMaxpurchase = mData.getData().getProductMaxpurchase();
				if (productMaxpurchase != null && !productMaxpurchase.equals("")) {
					if (count >= Integer.parseInt(productMaxpurchase)) {
						ToastUtils.showToast(context, "最多只能购买" + mData.getData().getProductMaxpurchase() + "件哦 ^.^");
						return;
					}
				}
				count++;
				tvCount.setText(count + "");
			}
		});
	}

	private void checkView(String num) {
		if (Integer.parseInt(num) > 0) {
			llBottom.setVisibility(View.VISIBLE);
			btnSoldOut.setVisibility(View.GONE);
			rlChange.setVisibility(View.VISIBLE);
		} else {
			btnSoldOut.setVisibility(View.VISIBLE);
			llBottom.setVisibility(View.GONE);
			rlChange.setVisibility(View.GONE);
		}
	}

    /*public void setDoCountClickListener(doCountClickListener doCountClickListener){
        this.doCountClickListener = doCountClickListener;
    }

    public interface doCountClickListener {
        void doMinus(TextView tvCount);
        void doAdd(TextView tvCount);
    }*/

}
