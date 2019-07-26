package com.yj.cosmetics.widget.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class Myctr extends ListView {
	public Myctr(Context context) {
		super(context);
	}

	public Myctr(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Myctr(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightMeasure = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasure);
	}
}
