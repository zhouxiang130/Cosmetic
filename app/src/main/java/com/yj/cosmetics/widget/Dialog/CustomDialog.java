package com.yj.cosmetics.widget.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.yj.cosmetics.R;


public class CustomDialog extends Dialog {
	private static int default_width = 160; // 默认宽度
	private static int default_height = 120;// 默认高度
	private Activity mContext;
	private View dialogView;
	private CustomDialog dialog;
	private RelativeLayout.LayoutParams params;

	public CustomDialog(Activity context, int style) {
		super(context, style);
		mContext = context;
	}

	public CustomDialog(Activity context, View layout, int width, int height, int gravity, int style) {
		super(context, style);
		setContentView(layout);
		mContext = context;
		// initSocialSDK();
		// initPlatformMap();
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.height = height;
		params.width = width;
		params.gravity = gravity;
		window.setAttributes(params);
	}

	public CustomDialog(Activity context, View layout, int width, int height, int gravity) {
		super(context, R.style.dialog);
		setContentView(layout);
		mContext = context;
		// initSocialSDK();
		// initPlatformMap();
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.height = height;
		params.width = width;
		params.gravity = gravity;
		window.setAttributes(params);
	}

	@Override
	public void show() {
		super.show();
	}

	private void startShare() {
		// TODO Auto-generated method stub

		showCustomUI(false);
	}



	private void showCustomUI(final boolean isDirectShare) {

	}


	/**
	 * @param view                 布局View
	 * @param isTouchOutsideCancle 点击灰色部分dialog 是否消失
	 * @param w                    宽度
	 * @param h                    高度
	 * @param contentHeight        dialog 内容的高度
	 * @return
	 */
	public CustomDialog create(View view, boolean isTouchOutsideCancle, float w, float h, float contentHeight) {
		this.dialogView = view;
		// instantiate the dialog with the custom Theme
		CustomDialog dialog = new CustomDialog(mContext, R.style.Dialog);
		dialog.setCanceledOnTouchOutside(isTouchOutsideCancle);
		dialog.setCancelable(false);
		dialog.setContentView(view);
		View integral_scrollView_ = dialogView.findViewById(R.id.integral_scrollView_);
		if (integral_scrollView_ != null) {
			params = (RelativeLayout.LayoutParams) integral_scrollView_.getLayoutParams();
		}
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.CENTER);
		WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值

//		 WindowManager m = mContext.getWindowManager();
//		 Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//		 p.height = (int) (d.getHeight() * 0.65); // 高度设置为屏幕的0.6
//		 p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65

		DisplayMetrics metric = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		int height = metric.heightPixels;
		p.width = (int) (width * w); // 宽度设置为屏幕的0.65
//		p.height = (int) (height * h); // 高度设置为屏幕的0.6
		p.height = (int) (height * h); // 高度设置为屏幕的0.6
		//获取当前控件的布局对象
		if (params!=null){
			params.height = (int) (p.height / contentHeight);//设置当前控件布局的高度
			integral_scrollView_.setLayoutParams(params);//将设置好的布局参数应用到控件中
		}

//		p.y = (int) ((height - p.height) * gravity);
		dialogWindow.setAttributes(p);
		return dialog;
	}
}
