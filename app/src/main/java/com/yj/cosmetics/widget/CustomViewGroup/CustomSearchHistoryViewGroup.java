package com.yj.cosmetics.widget.CustomViewGroup;

/**
 * Created by Suo on 2017/4/19.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yj.cosmetics.R;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.WindowDisplayManager;

import java.util.ArrayList;

/**
 * Created by ShaoLin on 2016/8/22.
 * 这里是类似淘宝中商品尺寸按钮组（这里做了支持button,textview）
 */
public class CustomSearchHistoryViewGroup<X extends TextView> extends ViewGroup {


    public static final String BTN_MODE = "BTNMODE"; //按钮模式
    public static final String TEV_MODE = "TEVMODE"; //文本模式

    private static final String TAG = "IViewGroup";

    private int viewWidth;   //控件的宽度
    private int viewHeight;  //控件的高度

    private ArrayList<String> mTexts = new ArrayList<>();
    private Context mContext;

    private int HorInterval;//水平间隔
    private int VerInterval;//垂直间隔
    private int textModePadding;

    //正常样式
    private float itemTextSize = 14;
    private int itemBGResNor = R.drawable.shape_corner_ed_radius3;
    private int itemTextColorNor = Color.parseColor("#101010");

    //选中的样式
    private int itemBGResPre = R.drawable.shape_corner_ed_radius3;
    private int itemTextColorPre = Color.parseColor("#101010");

    public CustomSearchHistoryViewGroup(Context context) {
        this(context, null);
        init();
    }

    public CustomSearchHistoryViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        float ratio = WindowDisplayManager.getRatio(mContext);

        HorInterval = Math.round(16 * ratio);
        VerInterval = Math.round(10 * ratio);
        textModePadding = Math.round(20 * ratio);
    }

    /**
     * 计算控件的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = measureWidth(widthMeasureSpec);
        viewHeight = measureHeight(heightMeasureSpec);
        Log.e(TAG, "onMeasure:" + viewWidth + ":" + viewHeight);
        // 计算自定义的ViewGroup中所有子控件的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        // 设置自定义的控件MyViewGroup的大小
        setMeasuredDimension(viewWidth, getViewHeight());
    }

    private int measureWidth(int pWidthMeasureSpec) {
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);
        switch (widthMode) {
            /**
             * mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
             * MeasureSpec.AT_MOST。
             *
             *
             * MeasureSpec.EXACTLY是精确尺寸，
             * 当我们将控件的layout_width或layout_height指定为具体数值时如andorid
             * :layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
             *
             *
             * MeasureSpec.AT_MOST是最大尺寸，
             * 当控件的layout_width或layout_height指定为WRAP_CONTENT时
             * ，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可
             * 。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
             *
             *
             * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，
             * 通过measure方法传入的模式。
             */
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
    }

    private int measureHeight(int pHeightMeasureSpec) {
        int result = 0;
        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
                result = getSuggestedMinimumHeight();
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = heightSize;
                break;
        }
        return result;
    }

    /**
     * 覆写onLayout，其目的是为了指定视图的显示位置，方法执行的前后顺序是在onMeasure之后，因为视图肯定是只有知道大小的情况下，
     * 才能确定怎么摆放
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
       /* // 遍历所有子视图
//        int posLeft = HorInterval;
        int posLeft = 0;
        int posTop = VerInterval;
        int posRight;
        int posBottom;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            // 获取在onMeasure中计算的视图尺寸
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();
            if (posLeft + getNextHorLastPos(i) > viewWidth) {
//                posLeft = HorInterval;
                posLeft = 0;
                posTop += (measureHeight + VerInterval);
            }
            posBottom = posTop + measureHeight;
            if (posLeft >= measuredWidth) {
                posRight = posLeft + measuredWidth;
                childView.layout(posLeft, posTop, posRight, posBottom);
            } else {
                childView.layout(0, posTop, measuredWidth, posBottom);
            }
            posLeft += (measuredWidth + HorInterval);
        }*/
        int posLeft = HorInterval;
        int posTop = VerInterval;
        int posRight;
        int posBottom;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            // 获取在onMeasure中计算的视图尺寸
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();
            if (posLeft + getNextHorLastPos(i) > viewWidth) {
                posLeft = HorInterval;
                posTop += (measureHeight + VerInterval);
            }
            posRight = posLeft + measuredWidth;
            posBottom = posTop + measureHeight;
            childView.layout(posLeft, posTop, posRight, posBottom);
            posLeft += (measuredWidth + HorInterval);
        }
    }

    /**
     * 获取控件的自适应高度
     *
     * @return
     */
    private int getViewHeight() {
        int viewwidth = HorInterval;
        int viewheight = VerInterval;
        if (getChildCount() > 0) {
            viewheight = getChildAt(0).getMeasuredHeight() + VerInterval;
        }
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            // 获取在onMeasure中计算的视图尺寸
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();
            //------------当前按钮按钮是否在水平上够位置(2017/7/10)------------
            if (viewwidth + getNextHorLastPos(i) > viewWidth) {
                //------------修正没有计算所在行第一个所需宽度(2017/7/10)------------
                viewwidth = (measuredWidth + HorInterval * 2);
                viewheight += (measureHeight + VerInterval);
            } else {
                viewwidth += (measuredWidth + HorInterval);
            }
        }
        return viewheight;
    }

    /**
     * 当前按钮所需的宽度
     *
     * @param i
     * @return
     */
    private int getNextHorLastPos(int i) {
        return getChildAt(i).getMeasuredWidth() + HorInterval;
    }

    private OnGroupItemClickListener onGroupItemClickListener;

    public void setGroupClickListener(OnGroupItemClickListener listener) {
        onGroupItemClickListener = listener;
        for (int i = 0; i < getChildCount(); i++) {
            final X childView = (X) getChildAt(i);
            final int itemPos = i;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtils.i("itemPos的子"+itemPos);
                    onGroupItemClickListener.onHistoryGroupItemClick(itemPos,getChooseText(itemPos));
                    chooseItemStyle(itemPos);
                }
            });
        }
    }

    //选中那个的样式
    public void chooseItemStyle(int pos) {
        clearItemsStyle();
        if (pos < getChildCount()) {
            X childView = (X) getChildAt(pos);
            childView.setBackgroundResource(itemBGResPre);
            childView.setTextColor(itemTextColorPre);
            setItemPadding(childView);
        }
    }

    private void setItemPadding(X view) {
        if (view instanceof Button) {
            view.setPadding(textModePadding, 0, textModePadding, 0);
        } else {
            view.setPadding(textModePadding, textModePadding/3, textModePadding, textModePadding/3);
        }
    }

    //清除Group所有的样式
    private void clearItemsStyle() {
        for (int i = 0; i < getChildCount(); i++) {
            X childView = (X) getChildAt(i);
            childView.setBackgroundResource(itemBGResNor);
            childView.setTextColor(itemTextColorNor);
            setItemPadding(childView);
        }
    }

    public void addItemViews(ArrayList<String> texts, String mode) {
        mTexts = texts;
        removeAllViews();
        for (String text : texts) {
            addItemView(text, mode);
        }
    }

    private void addItemView(String text, String mode) {
        X childView = null;
        switch (mode) {
            case BTN_MODE:
                childView = (X) new Button(mContext);
                break;
            case TEV_MODE:
                childView = (X) new TextView(mContext);
                break;
        }
        childView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        childView.setTextSize(itemTextSize);
        childView.setBackgroundResource(itemBGResNor);
        setItemPadding(childView);
        childView.setTextColor(itemTextColorNor);
        childView.setText(text);
        this.addView(childView);
    }

    public String getChooseText(int itemID) {
        if (itemID >= 0) {
            return mTexts.get(itemID);
        }
        return null;
    }

    public void setItemTextSize(float itemTextSize) {
        this.itemTextSize = itemTextSize;
    }

    public void setItemBGResNor(int itemBGResNor) {
        this.itemBGResNor = itemBGResNor;
    }

    public void setItemTextColorNor(int itemTextColorNor) {
        this.itemTextColorNor = itemTextColorNor;
    }

    public void setItemBGResPre(int itemBGResPre) {
        this.itemBGResPre = itemBGResPre;
    }

    public void setItemTextColorPre(int itemTextColorPre) {
        this.itemTextColorPre = itemTextColorPre;
    }

    public interface OnGroupItemClickListener {
        void onHistoryGroupItemClick(int item, String text);
    }
}