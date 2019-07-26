package com.yj.cosmetics.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yj.cosmetics.R;


/**
 * Created by Suo on 2017/4/26.
 */

public class WindowDisplayManager {

    private static float RATIO;
    private static float OFFSET_LEFT;
    private static float OFFSET_TOP;

    public static float getRatio(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        float ratioWidth = (float) screenWidth / 480;
        float ratioHeight = (float) screenHeight / 800;

        RATIO = Math.min(ratioWidth, ratioHeight);
        /*if (ratioWidth != ratioHeight) {
            if (RATIO == ratioWidth) {
                OFFSET_LEFT = 0;
                OFFSET_TOP = Math.round((screenHeight - 800 * RATIO) / 2);
            }else {
                OFFSET_LEFT = Math.round((screenWidth - 480 * RATIO) / 2);
                OFFSET_TOP = 0;
            }
        }*/
        return RATIO;
    }

    public static LinearLayout.LayoutParams getBannerHeight(Context context, View view) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        //获取当前控件的布局对象
        params.height = (int) (width * 1 / 2+0.5f);//设置当前控件布局的高度
        LogUtils.i("height的值" + params.height);
        return params;
    }

    public static RelativeLayout.LayoutParams getRlBannerHeight(Context context, View view) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        //获取当前控件的布局对象
        params.height = (int) (width * 1 / 2+0.5f);//设置当前控件布局的高度
        LogUtils.i("height的值" + params.height);
        return params;
    }

    public static FrameLayout.LayoutParams getViewPagerParams(Context context, View view) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int viewWidth = Math.round((width - context.getResources().getDimension(R.dimen.dis15) * 2) * 5/9);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.height = viewWidth * 5/ 7;//设置当前控件布局的高度
        return params;
    }

    public static LinearLayout.LayoutParams getCommunityBannerHeight(Context context, View view) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.height = width * 3 / 10;//设置当前控件布局的高度
        LogUtils.i("height的值" + params.height);
        return params;
    }
    public static LinearLayout.LayoutParams getFourThreeParams(Context context, View view) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.height = width * 3 / 4;//设置当前控件布局的高度
        LogUtils.i("height的值" + params.height);
        return params;
    }
    public static RelativeLayout.LayoutParams getFourThreeRelParams(Context context, View view) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.height = width * 3 / 4;//设置当前控件布局的高度
        LogUtils.i("height的值" + params.height);
        return params;
    }
    public static RelativeLayout.LayoutParams getEqualRelParams(Context context, View view) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.height = width ;//设置当前控件布局的高度
        LogUtils.i("height的值" + params.height);
        return params;
    }
    public static LinearLayout.LayoutParams getGoodsParams(Context context, View view) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.height = (width - DensityUtil.dip2px(context,45))* 3 / 8;//设置当前控件布局的高度
        LogUtils.i("height的值" + params.height);
        return params;
    }
    public static LinearLayout.LayoutParams getOneSecondParams(Context context,View view){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();
        params.height = width/2;
        return params;
    }
}
