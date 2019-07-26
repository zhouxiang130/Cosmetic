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



public class ValidateAddressDialogs extends Dialog {
    TextView tvConfirm;
    TextView tvCancel;
    TextView tvTitle;

    private Context context = null;
    private View view;


    public ValidateAddressDialogs(Context context, int themeResId) {
        super(context, themeResId);
    }


    public ValidateAddressDialogs(Context context) {
        super(context, R.style.ShoppingDeleteDialog);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_normals_, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        setWindow();
        tvConfirm = (TextView)view.findViewById(R.id.dialog_normal_confirm);
        tvCancel = (TextView)view.findViewById(R.id.dialog_normal_cancel);
        tvTitle = (TextView)view.findViewById(R.id.dialog_normal_title);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public  TextView getTvConfirm(){
        return tvConfirm;
    }
    public  TextView getTvCancel(){
        return tvCancel;
    }
    public TextView getTvTitle(){

        return tvTitle;
    }
    private void setWindow() {
        Window window = this.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
//        window.setWindowAnimations(R.style.BottomAnimation);
    }
}
