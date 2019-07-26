package com.yj.cosmetics.widget.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.yj.cosmetics.R;


/**
 * Created by Chu on 2016/12/19.
 */
public class CustomPostDialog extends Dialog {
    private Context context = null;
    private View view;

    public CustomPostDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public CustomPostDialog(Context context) {
        super(context, R.style.CustomPostDialog);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_post, null);
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
    /*public void showFinish(){
        ImageView ivAnim = (ImageView)view.findViewById(R.id.loadingImageView);
        ivAnim.clearAnimation();
        ivAnim.setVisibility(View.GONE);
        TextView tvMessage = (TextView)view.findViewById(R.id.progressMessage);
        tvMessage.setVisibility(View.VISIBLE);
        ImageView ivImg = (ImageView)view.findViewById(R.id.progressImg);
        ivImg.setVisibility(View.VISIBLE);

    }*/

}