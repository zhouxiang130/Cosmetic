package com.yj.cosmetics.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.rollviewpager.HintView;
import com.yj.cosmetics.util.DensityUtil;

import static com.yj.cosmetics.R.dimen.dis2;
/**
 * Created by Suo on 2017/8/19.
 */

public abstract class CustomShapeHintView extends LinearLayout implements HintView {
    private ImageView[] mDots;
    private int length = 0;
    private int lastPosition = 0;
    private Context context;

    private Drawable dot_normal;
    private Drawable dot_focus;

    public CustomShapeHintView(Context context){
        super(context);
        this.context = context;
    }

    public CustomShapeHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    public abstract Drawable makeFocusDrawable();

    public abstract Drawable makeNormalDrawable();

    @Override
    public void initView(int length, int gravity) {
        removeAllViews();
        lastPosition = 0;
        setOrientation(HORIZONTAL);
        //需要让gravity为Right
        setGravity(Gravity.RIGHT| Gravity.CENTER_VERTICAL);
        /*switch (gravity) {
            case 0:
                setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
                break;
            case 1:
                setGravity(Gravity.CENTER);
                break;
            case 2:
                setGravity(Gravity.RIGHT| Gravity.CENTER_VERTICAL);
                break;
        }*/

        this.length = length;
        mDots = new ImageView[length];

        dot_focus = makeFocusDrawable();
        dot_normal = makeNormalDrawable();

        for (int i = 0; i < length; i++) {
            mDots[i]=new ImageView(getContext());
            LayoutParams dotlp = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            dotlp.setMargins(0, 0, DensityUtil.dip2px(context,context.getResources().getDimension(dis2)), 0);
            mDots[i].setLayoutParams(dotlp);
            mDots[i].setBackgroundDrawable(dot_normal);
            addView(mDots[i]);
        }

        setCurrent(0);
    }

    @Override
    public void setCurrent(int current) {
        if (current < 0 || current > length - 1) {
            return;
        }
        mDots[lastPosition].setBackgroundDrawable(dot_normal);
        mDots[current].setBackgroundDrawable(dot_focus);
        lastPosition = current;
    }
}
