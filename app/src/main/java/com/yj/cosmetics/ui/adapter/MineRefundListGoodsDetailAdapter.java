package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.MineRefundDetailEntity;

import java.util.List;

/**
 * Created by Suo on 2017/6/6.
 */

public class MineRefundListGoodsDetailAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
//    private List<MineOrderEntity.MineOrderData.MineOrderList> data;
    List<MineRefundDetailEntity.DataBean.ItemBean> data;

    public MineRefundListGoodsDetailAdapter(Context context, List<MineRefundDetailEntity.DataBean.ItemBean> data) {
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
            view = layoutInflater.inflate(R.layout.item_mine_order_list_goods_, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.item_settle_title);
            TextView tvPrice = (TextView) view.findViewById(R.id.item_settle_price);
            TextView tvStyle = (TextView) view.findViewById(R.id.item_settle_style);
            TextView tvNum = (TextView) view.findViewById(R.id.item_settle_num);
            ImageView ivGoods = (ImageView) view.findViewById(R.id.item_settle_iv1);

            tvTitle.setText(data.get(position).getProductName());
            if(!TextUtils.isEmpty(data.get(position).getProductName())){
                tvStyle.setText(data.get(position).getProductName());
            }else{
                tvStyle.setText("");
            }
            tvNum.setText("X"+data.get(position).getNum());
            tvPrice.setText(data.get(position).getPrice());
            //获取自定义的类实例
            Glide.with(context)
                    .load(URLBuilder.getUrl(data.get(position).getProductImg()))
                    .error(R.mipmap.default_goods)
                    .centerCrop()
                    .into(ivGoods);
        }
        return view;
    }
}