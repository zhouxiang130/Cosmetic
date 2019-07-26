package com.yj.cosmetics.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yj.cosmetics.R;


/**
 * Created by Suo on 2017/4/11.
 */

public class CustomToast extends Toast {

	private TextView tvContent;

	public CustomToast(Context context,int i) {
		super(context);
		setGravity(Gravity.BOTTOM , 0, i);
		setDuration(LENGTH_SHORT);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.widget_custom_toast, null);
		tvContent = (TextView) view.findViewById(R.id.custom_tv);
		setView(view);
	}

	public void showMsg(CharSequence text) {
		tvContent.setText(text);
	}
}
