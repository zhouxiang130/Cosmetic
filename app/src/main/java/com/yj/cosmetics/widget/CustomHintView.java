package com.yj.cosmetics.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;

import com.jude.rollviewpager.Util;

/**
 * Created by Suo on 2017/8/19.
 */

public class CustomHintView extends CustomShapeHintView {

    private int focusResId;
    private int normalResId;
    private int size;


    public CustomHintView(Context context, @DrawableRes int focusResId, @DrawableRes int normalResId) {
        this(context,focusResId,normalResId, Util.dip2px(context,32));
    }
    public CustomHintView(Context context, @DrawableRes int focusResId, @DrawableRes int normalResId, int size) {
        super(context);
        this.focusResId = focusResId;
        this.normalResId = normalResId;
        this.size = size;
    }


    @Override
    public Drawable makeFocusDrawable() {
        Drawable drawable = getContext().getResources().getDrawable(focusResId);
        if (size>0){
            drawable = zoomDrawable(drawable,size,size);
        }
        return drawable;
    }

    @Override
    public Drawable makeNormalDrawable() {
        Drawable drawable = getContext().getResources().getDrawable(normalResId);
        if (size>0){
            drawable = zoomDrawable(drawable,size,size);
        }
        return drawable;
    }

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    private Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);

        return new BitmapDrawable(null, newbmp);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
}
