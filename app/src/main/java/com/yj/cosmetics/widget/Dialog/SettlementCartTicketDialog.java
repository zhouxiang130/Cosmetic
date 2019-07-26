package com.yj.cosmetics.widget.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.model.SettlementsCartEntity;
import com.yj.cosmetics.ui.adapter.TicketDetailDialogAdapters;


/**
 * Created by Suo on 2017/4/19.
 */

public class SettlementCartTicketDialog extends Dialog {

	private static final String TAG = " CustomSizeDialog ";
	TicketDetailDialogAdapters dialogAdapter;
	private Activity context;

	RecyclerView recyclerView;
	private Button btnBuy;

	private int count = 1;

	/* private doCountClickListener doCountClickListener;*/

	//为防止出错 将style的dialog随意指向了一个style 如有需要可自己创建
	public SettlementCartTicketDialog(Activity context) {
		super(context, R.style.Goods_Size_Dialog);
		this.context = context;
	}

	public void setCustomDialog(SettlementsCartEntity.DataBean  coupons) {
		final View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ticket_layout_, null);
		btnBuy = (Button) mView.findViewById(R.id.dialog_size_buy);
		recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);

		LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
		dialogAdapter = new TicketDetailDialogAdapters(context, coupons);
		recyclerView.setAdapter(dialogAdapter);
		recyclerView.post(new Runnable() {
			@Override
			public void run() {
//				LogUtils.i("reycclerview的高" + recyclerView.getHeight());
				if (recyclerView.getHeight() > context.getResources().getDimension(R.dimen.dis160)) {
//					LogUtils.i("我进入大于180了" + recyclerView.getHeight());
					FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) recyclerView.getLayoutParams();
					layoutParams.height = Math.round(context.getResources().getDimension(R.dimen.dis350));
					recyclerView.setLayoutParams(layoutParams);
				}
			}
		});

		super.setContentView(mView);
	}

	public TextView getBtnFinish() {
		return btnBuy;
	}


	public int getCheckedPosition() {
		return dialogAdapter.getCheckedPosition();
	}

	public void setCheckPosition(int position){
		dialogAdapter.setCheckPosition(position);
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
