package com.yj.cosmetics.widget.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yj.cosmetics.R;

/**
 * Created by Suo on 2018/3/19.
 */

public class CustomProgressDialog extends Dialog {
    private Context context = null;
    private View view;

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public CustomProgressDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_progress, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        setWindow();

    }

    private void setWindow() {
        Window window = this.getWindow();
        window.setLayout(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
//        window.setWindowAnimations(R.style.BottomAnimation);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    /**
     * @param strTitle 标题
     * @return
     */
    public View setTitile(String strTitle) {
        return view;
    }

   /* public View setMessage(String strMessage) {
        TextView tvMsg = (TextView) view.findViewById(R.id.id_tv_loadingmsg);

        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }
        return view;
    }*/

    public void onWindowFocusChanged(boolean hasFocus) {
        if (view == null) {
            return;
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView
                .getBackground();
        animationDrawable.start();
        setCanceledOnTouchOutside(false);
    }
}