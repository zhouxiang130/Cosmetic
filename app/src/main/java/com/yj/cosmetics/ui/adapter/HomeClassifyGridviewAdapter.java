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
import com.yj.cosmetics.model.HomeClassifyEntity;
import com.yj.cosmetics.util.LogUtils;

import java.util.List;

/**
 * Created by Suo on 2017/6/6.
 */

public class HomeClassifyGridviewAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    List<HomeClassifyEntity.HomeClassifyData.HomeClassifySon> data;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    //每页显示的数量
    private int pageSize;

    public HomeClassifyGridviewAdapter(Context context, List<HomeClassifyEntity.HomeClassifyData.HomeClassifySon> data, int page, int pageSize) {
        this.context = context;
        this.data = data;
        this.curIndex = page;
        this.pageSize = pageSize;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 先判断数据集的大小是否足够显示满本页,如果够，则直接返回每一页显示的最大条目个数pageSize,如果不够，则有几项就返回几,(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        if(data != null) {
            return data.size() > (curIndex + 1) * pageSize ? pageSize : (data.size() - curIndex * pageSize);
        }
        return 0;

    }


    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }
    @Override
    public Object getItem(int position) {
        return data.get(position + curIndex * pageSize);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_home_classify_gridview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) convertView.findViewById(R.id.home_classify_gridview_tv);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.home_classify_gridview_iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize
         */
        int pos = position + curIndex * pageSize;
        viewHolder.tv.setText(data.get(pos).getClassify_name());
        Glide.with(context)
                .load(URLBuilder.getUrl(data.get(pos).getClassify_img()))
                .asBitmap()
                .centerCrop()
                .error(R.mipmap.default_goods)
                .into(viewHolder.iv);
        LogUtils.i("我return convertView了");
        return convertView;
    }

    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}