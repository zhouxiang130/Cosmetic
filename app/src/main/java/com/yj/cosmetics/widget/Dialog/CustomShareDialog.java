package com.yj.cosmetics.widget.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.yj.cosmetics.R;
import com.yj.cosmetics.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Suo on 2017/4/19.
 */

public class CustomShareDialog extends Dialog {

	GridView gridView;
	SimpleAdapter mSimpleAdapter;
	private List<Map<String, Object>> data_list;
	private int[] icon = {R.mipmap.qq, R.mipmap.wechat,
			R.mipmap.micro_blog, R.mipmap.circle_of_friends,
	};
	private String[] iconName = {"QQ", "微信", "新浪微博", "朋友圈"};
	private Context context;

	//为防止出错 将style的dialog随意指向了一个style 如有需要可自己创建
	public CustomShareDialog(Context context) {
		super(context, R.style.ShoppingDeleteDialog);
		this.context = context;
		setCustomDialog();
	}

	private void setCustomDialog() {
		View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_share_layout, null);
		gridView = (GridView) mView.findViewById(R.id.dialog_share_grid);
		data_list = new ArrayList<Map<String, Object>>();
		getGridData();
		String[] from = {"img", "text"};
		int[] to = {R.id.share_grid_iv, R.id.share_grid_tv};
		mSimpleAdapter = new SimpleAdapter(context, data_list, R.layout.item_share_grid, from, to);
		gridView.setAdapter(mSimpleAdapter);
        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtils.showToast(context,"我点击了地"+i+"个位置iconName的值"+iconName[i]);
            }
        });*/
		Button btnCancel = (Button) mView.findViewById(R.id.dialog_share_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isShowing()) {
					dismiss();
				}
			}
		});
		super.setContentView(mView);
	}

	public void setOnGridItemClickListener(GridView.OnItemClickListener listener) {
		gridView.setOnItemClickListener(listener);
	}

	private List<Map<String, Object>> getGridData() {
		//cion和iconName的长度是相同的，这里任选其一都可以
		for (int i = 0; i < icon.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("img", icon[i]);
			map.put("text", iconName[i]);
			data_list.add(map);
		}
		LogUtils.i("GridView的长度" + data_list.size());
		return data_list;
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
}
