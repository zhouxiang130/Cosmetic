package com.yj.cosmetics.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Suo on 2017/4/11.
 */

public class KeyBoardUtils {

    /**
     * 收起键盘
     */
    public static void hintKb(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (context.getCurrentFocus() != null && context.getCurrentFocus().getWindowToken() != null && imm != null) {
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 收起键盘
     */
    public static void showKb(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (context.getCurrentFocus() != null && context.getCurrentFocus().getWindowToken() != null && imm != null) {
            imm.showSoftInput(context.getCurrentFocus(),InputMethodManager.SHOW_FORCED);
        }
    }
    public static boolean isActive(Activity context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive(view);
    }


}
