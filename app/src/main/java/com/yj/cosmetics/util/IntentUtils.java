package com.yj.cosmetics.util;

import android.content.Context;
import android.content.Intent;

import com.yj.cosmetics.ui.activity.HomeGoodsListActivity;
import com.yj.cosmetics.ui.activity.InfoCenterActivity;
import com.yj.cosmetics.ui.activity.UserLoginActivity;
import com.yj.cosmetics.ui.activity.GoodsDetailActivity;
import com.yj.cosmetics.ui.activity.StoreDetailActivity;

/**
 * Created by Suo on 2017/9/11.
 */

public class IntentUtils {
    public static void IntentToLogin(Context context){
        Intent intent =  new Intent(context, UserLoginActivity.class);
        context.startActivity(intent);
    }
    public static void IntentToGoodsDetial(Context context,String proId){
        Intent intent =  new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("productId",proId);
        context.startActivity(intent);
    }
    public static void IntentToGoodsDetial(Context context,String proId,String sproductId){
        Intent intent =  new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("productId",proId);
        intent.putExtra("sproductId",sproductId);
        context.startActivity(intent);
    }

    public static void IntentToStoreDetial(Context context,String shopId){
        Intent intent =  new Intent(context, StoreDetailActivity.class);
        intent.putExtra("shopId",shopId);
        context.startActivity(intent);
    }


    public static void IntentToGoodsList(Context context,String classifyId){
        Intent intent =  new Intent(context, HomeGoodsListActivity.class);
        intent.putExtra("classifyId",classifyId);
        context.startActivity(intent);
    }

    public static void IntentToInfoCenter(Context context){
        Intent intent = new Intent(context, InfoCenterActivity.class);
        context.startActivity(intent);
    }
}
