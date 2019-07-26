package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/9 0009.
 */

public class judgeImgParamsAdapter extends BaseAdapter {
    private static final String TAG = "judgeImgParamsAdapter";
    private final List<String> rechargeBean;
    LayoutInflater layoutInflater;
    private Context context = null;

    public judgeImgParamsAdapter(Context context, List<String> rechargeBean) {
        this.rechargeBean = rechargeBean;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return rechargeBean == null ? 0 : rechargeBean.size();
    }

    @Override
    public Object getItem(int position) {
        return rechargeBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rechargeBean == null ? 0 : rechargeBean.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.layout_item_recharge, null);
            viewHolder.item_judge_img_ = (ImageView) convertView.findViewById(R.id.item_judge_img_);//充值金额
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.i(TAG, "getView: " + rechargeBean.size()+ " ----" +position  + "--------"+ URLBuilder.getUrl(rechargeBean.get(position)));

        Glide.with(context)
                .load(URLBuilder.getUrl(rechargeBean.get(position)))
                .asBitmap()
                .error(R.mipmap.default_goods)
                .centerCrop()
                .into(viewHolder.item_judge_img_);
        return convertView;
    }
    class ViewHolder {
        ImageView item_judge_img_;
    }
}