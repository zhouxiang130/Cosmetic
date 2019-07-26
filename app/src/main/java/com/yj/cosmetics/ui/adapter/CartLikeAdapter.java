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
import com.yj.cosmetics.model.HomeListEntity;

import java.util.List;

/**
 * Created by Suo on 2017/6/6.
 */

public class CartLikeAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
//    private List<MineOrderEntity.MineOrderData.MineOrderList> data;
    List<HomeListEntity.HomeListData.HomeListItem> data;

    public CartLikeAdapter(Context context, List<HomeListEntity.HomeListData.HomeListItem> data) {
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
            view = layoutInflater.inflate(R.layout.item_cart_like, null);
            TextView tvTitle = (TextView)view.findViewById(R.id.cart_like_title);
            TextView tvDetial = (TextView)view.findViewById(R.id.cart_like_detial);
            TextView tvPrice = (TextView)view.findViewById(R.id.cart_like_price);
            ImageView iv = (ImageView) view.findViewById(R.id.cart_like_iv);
            tvTitle.setText(data.get(position).getProduct_name());
            tvDetial.setText(data.get(position).getProduct_abstract());
            tvPrice.setText("￥"+data.get(position).getProduct_current());
            //获取自定义的类实例
            Glide.with(context)
                    .load(URLBuilder.getUrl(data.get(position).getProduct_listImg()))
                    .error(R.mipmap.default_goods)
                    .centerCrop()
                    .into(iv);
        }
        return view;
    }
}