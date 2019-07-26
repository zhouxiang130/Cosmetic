package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.ShopListEntity;

import java.util.List;

/**
 * Created by Administrator on 2019/7/4 0004.
 */

public class MyGridViewAdapter extends BaseAdapter {


	private List<ShopListEntity.DataBean.ShopArrayBean.ProductlistBean> mData= null;
	private Context mContext = null;
	private LayoutInflater mlayoutInflater;

	public MyGridViewAdapter(Context mContext, List<ShopListEntity.DataBean.ShopArrayBean.ProductlistBean> productlist) {
		this.mContext = mContext;
		this.mData = productlist;
		mlayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return mData == null ? 0 : mData.size();
	}

	//写一个静态的class,把layout_grid_item的控件转移过来使用
	 class ViewHolder {
		public ImageView Grid_imageview;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			//填写ListView的图标和标题等控件的来源，来自于layout_list_item这个布局文件
			//把控件所在的布局文件加载到当前类中
			convertView = mlayoutInflater.inflate(R.layout.item_store_list_img, null);
			//生成一个ViewHolder的对象
			holder = new ViewHolder();
			//获取控件对象
			holder.Grid_imageview = convertView.findViewById(R.id.store_list_iv1);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//加载第三方网络图片
		Glide.with(mContext)
				.load(URLBuilder.getUrl(mData.get(position).getProduct_listImg()))
				.asBitmap()
				.centerCrop()
				.error(R.mipmap.default_goods)
				.into(holder.Grid_imageview);
		return convertView;
	}


}
