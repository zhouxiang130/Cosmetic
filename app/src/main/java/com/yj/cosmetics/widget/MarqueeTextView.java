package com.yj.cosmetics.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/9/21 0021.
 */

public class MarqueeTextView extends TextView {

	public MarqueeTextView(Context context) {
		this(context, null);
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//设置单行
		setSingleLine();
		//设置Ellipsize
		setEllipsize(TextUtils.TruncateAt.MARQUEE);
		//获取焦点
		setFocusable(true);
		//走马灯的重复次数，-1代表无限重复
		setMarqueeRepeatLimit(-1);
		//强制获得焦点
		setFocusableInTouchMode(true);
	}

	/*
	*这个属性这个View得到焦点,在这里我们设置为true,这个View就永远是有焦点的
	*/
	@Override
	public boolean isFocused() {
		return true;
	}
}

