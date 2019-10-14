package com.yj.cosmetics.widget.Dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.ShopDetailEntity;
import com.yj.cosmetics.ui.adapter.StoreTelAdapter;
import com.yj.cosmetics.util.AuthorUtils;
import com.yj.cosmetics.widget.MTextView;


/**
 * Created by Suo on 2017/4/19.
 */


public class StoreInfoDialogs extends Dialog {
	private Context context;
	private int count = 1;
	private LinearLayout llIntegralInfo;
	private TextView tvCancel;

	/**
	 * 需要进行检测的权限数组
	 */

	protected String[] needPermissions = {
			Manifest.permission.CALL_PHONE
	};


	/* private doCountClickListener doCountClickListener;*/

	//为防止出错 将style的dialog随意指向了一个style 如有需要可自己创建
	public StoreInfoDialogs(Context context) {
		super(context, R.style.Goods_Size_Dialog);
		this.context = context;
	}

	public void setCustomDialog(final ShopDetailEntity datas, int i) {
		final View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_storeinfo_layout, null);
		LinearLayout telAll = (LinearLayout) mView.findViewById(R.id.ll_tel_all);

		RecyclerView mRecyclerView = (RecyclerView) mView.findViewById(R.id.mRecyclerView);
		ScrollView viewById = (ScrollView) mView.findViewById(R.id.store_dialog_info_);
		TextView tvServices = (TextView) mView.findViewById(R.id.store_dialog_info_tv_services);
		tvCancel = (TextView) mView.findViewById(R.id.text_cancel);

		MTextView tvServicesContent = (MTextView) mView.findViewById(R.id.store_dialog_info_rl_services_content);//服务内容
		TextView tvServicesContent2 = (TextView) mView.findViewById(R.id.store_dialog_info_rl_services_content2);//配送
		TextView tvServicesContent3 = (TextView) mView.findViewById(R.id.store_dialog_info_rl_services_content3);//公告

		if (i == 1) {
			telAll.setVisibility(View.GONE);
			viewById.setVisibility(View.VISIBLE);
			tvServicesContent.setText(datas.getData().getShopName());
		/*	tvServicesContent.setDrawableRightListener(new MTextView.DrawableRightListener() {
				@Override
				public void onDrawableRightClick(View view) {

					Intent intentRule = new Intent(context, NormalWebViewActivity.class);
					intentRule.putExtra("url", URLBuilder.URLBaseHeader + "/weChat/upjsp/agreement.html");
					intentRule.putExtra("title", "用户服务协议");
					context.startActivity(intentRule);

				}
			});*/
			tvServicesContent2.setText(datas.getData().getAreaAddress());
			tvServicesContent3.setText(datas.getData().getShopNotice());

		} else {
			telAll.setVisibility(View.VISIBLE);
			viewById.setVisibility(View.GONE);
			LinearLayoutManager layoutManager = new LinearLayoutManager(context);
			layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
			mRecyclerView.setLayoutManager(layoutManager);
			StoreTelAdapter mAdapter = new StoreTelAdapter(context, datas);
			mRecyclerView.setAdapter(mAdapter);
			mAdapter.setOnItemClickListener(new StoreTelAdapter.SpendDetialClickListener() {
				@Override
				public void onItemClick(View view, int postion, int flag) {

					if (new AuthorUtils(context).checkPermissions(needPermissions)) {
						Intent callIntent = new Intent();
						callIntent.setAction(Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:" + datas.getData().getShopTel().get(postion)));
						context.startActivity(callIntent);
					}
				}
			});
		}

		super.setContentView(mView);
	}


	@Override
	public void setContentView(int layoutResID) {
	}

	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params) {
	}


	public TextView getTvCancel() {
		return tvCancel;
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
		WindowManager.LayoutParams layoutParams = getWindow().getAttributes(); // 获取对话框当前的参数值
		layoutParams.gravity = Gravity.BOTTOM;

		layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		getWindow().getDecorView().setPadding(0, 0, 0, 0);
		getWindow().setAttributes(layoutParams);
	}





    /*public void setDoCountClickListener(doCountClickListener doCountClickListener){
        this.doCountClickListener = doCountClickListener;
    }

    public interface doCountClickListener {
        void doMinus(TextView tvCount);
        void doAdd(TextView tvCount);
    }*/

}
