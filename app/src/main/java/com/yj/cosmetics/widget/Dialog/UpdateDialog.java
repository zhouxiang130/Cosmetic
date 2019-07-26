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


public class UpdateDialog extends Dialog {

    private Context context = null;
    private View view;
    TextView tvUpdate;
    TextView tvPass;
    TextView tvForce;
    TextView tvTitle;
    TextView tvVersion;
    TextView tvDesc;
    LinearLayout llBottom;
    private boolean isCancelable = true;


    public UpdateDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public UpdateDialog(Context context) {
        super(context, R.style.UpdateDialog);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.update_dialog, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        tvUpdate = (TextView) view.findViewById(R.id.update_dialog_update);
        tvPass = (TextView) view.findViewById(R.id.update_dialog_pass);
        tvForce = (TextView) view.findViewById(R.id.update_dialog_force);
        tvTitle = (TextView) view.findViewById(R.id.update_dialog_title);
        tvVersion = (TextView) view.findViewById(R.id.update_dialog_version);
        tvDesc = (TextView) view.findViewById(R.id.update_dialog_content);
        llBottom = (LinearLayout) view.findViewById(R.id.update_dialog_bottom);
        setCanceledOnTouchOutside(false);
        setWindow();
    }

    public TextView getUpdate(){
        return tvUpdate;
    }
    public TextView getPass(){
        return tvPass;
    }
    public TextView getForce(){
        return tvForce;
    }
    public TextView getVersion(){
        return tvVersion;
    }
    public TextView getDesc(){
        return  tvDesc;
    }
    public LinearLayout getBottom(){
        return llBottom;
    }
    public void setDesc(CharSequence desc){
        tvDesc.setText(desc);
    }

    private void setWindow() {
        Window window = this.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
//        window.setWindowAnimations(R.style.BottomAnimation);
    }
    public void setIsCancelable(boolean isCancelable){
        this.isCancelable = isCancelable;
    }
    @Override
    public void onBackPressed() {
        if(isCancelable){
            super.onBackPressed();
        }else{
            return;
        }
    }
}
