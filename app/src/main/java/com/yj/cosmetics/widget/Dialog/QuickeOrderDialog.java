package com.yj.cosmetics.widget.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.cosmetics.R;


/**
 * Created by Suo on 2017/4/26.
 */



public class QuickeOrderDialog extends Dialog {
    TextView tvWeChat;
    TextView tvFriend;

    TextView tvCopy;
    TextView tvContent;

    private Context context = null;
    private View view;


    public QuickeOrderDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public QuickeOrderDialog(Context context) {
        super(context, R.style.ShoppingDeleteDialog);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_quick_content, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        setWindow();
        tvWeChat = (TextView)view.findViewById(R.id.tv_wChat);
        tvFriend = (TextView)view.findViewById(R.id.tv_friend);
        tvContent = (TextView)view.findViewById(R.id.dialog_normal_content);
        tvCopy = (TextView)view.findViewById(R.id.tv_copy_url);

//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
    }

    public  TextView getTvWeChat(){
        return tvWeChat;
    }
    public  TextView getTvFriend(){
        return tvFriend;
    }
    public TextView getTvCopyUrl(){
        return tvCopy;
    }
    public TextView getTvContent(){
        return tvContent;
    }
    private void setWindow() {
        Window window = this.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
//        window.setWindowAnimations(R.style.BottomAnimation);
    }
}
