package com.yj.cosmetics.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自定义gridview，解决ListView中嵌套gridview显示不正常的问题（1行半） 
 */
public class NoScrollGridView extends GridView {
	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	/**
	 * 
	 * @param context
	 * @param
	 */
	public NoScrollGridView(Context context) {
		super(context);
	}
	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
