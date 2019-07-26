package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.StoreListEntity;
import com.yj.cosmetics.widget.RoundedImageView.RoundedImageView;

import java.util.List;

/**
 * Created by Suo on 2017/6/6.
 */

public class StoreGridviewAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private Context context;
	//    private List<MineOrderEntity.MineOrderData.MineOrderList> data;
	List<StoreListEntity.DataBean.ProductClassifyListBean> data;
	/**
	 * 页数下标,从0开始(当前是第几页)
	 */
	private int curIndex;
	//每页显示的数量
	private int pageSize;

	public StoreGridviewAdapter(Context context, List<StoreListEntity.DataBean.ProductClassifyListBean> mList, int page, int pageSize) {
		this.context = context;
		this.data = mList;
		this.curIndex = page;
		this.pageSize = pageSize;
		layoutInflater = LayoutInflater.from(context);
	}

	/**
	 * 先判断数据集的大小是否足够显示满本页,如果够，则直接返回每一页显示的最大条目个数pageSize,如果不够，则有几项就返回几,(也就是最后一页的时候就显示剩余item)
	 */
	@Override
	public int getCount() {
		if (data != null) {
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
			convertView = layoutInflater.inflate(R.layout.item_home_gridview_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tv = (TextView) convertView.findViewById(R.id.home_gridview_item_tv);
			viewHolder.iv = (RoundedImageView) convertView.findViewById(R.id.home_gridview_item_iv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		/**
		 * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize
		 */
		int pos = position + curIndex * pageSize;
		viewHolder.tv.setText(data.get(pos).getClassify_name());
		if (TextUtils.isEmpty(data.get(pos).getClassify_id()) && TextUtils.isEmpty(data.get(pos).getClassify_img())) {
			viewHolder.iv.setImageResource(R.mipmap.diary);
		} else {
			Glide.with(context)
					.load(URLBuilder.getUrl(data.get(pos).getClassify_img()))
					.asBitmap()
					.centerCrop()
					.error(R.mipmap.default_goods)
					.into(viewHolder.iv);
		}
		return convertView;
	}

	class ViewHolder {
		public TextView tv;
		public RoundedImageView iv;
	}
}