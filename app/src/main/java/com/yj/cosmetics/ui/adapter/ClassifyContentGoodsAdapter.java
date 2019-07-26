package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.ClassifyContentEntity;

import java.util.List;

/**
 * Created by Suo on 2017/6/6.
 */

public class ClassifyContentGoodsAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    List<ClassifyContentEntity.ClassifyContentData.ClassifyContentList> data;

    public ClassifyContentGoodsAdapter(Context context, List<ClassifyContentEntity.ClassifyContentData.ClassifyContentList> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 数据总数
     */
    @Override
    public int getCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    /**
     * 获取当前数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.item_classify_content_goods, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.classify_content_goods_tv);
            ImageView ivGoods = (ImageView) view.findViewById(R.id.classify_content_goods_iv);

            tvTitle.setText(data.get(position).getClassify_name());
            //获取自定义的类实例
            Glide.with(context)
                    .load(URLBuilder.getUrl(data.get(position).getClassify_img()))
                    .asBitmap()
                    .error(R.mipmap.default_goods)
                    .centerCrop()
                    .into(ivGoods);
        }
        return view;
    }
}