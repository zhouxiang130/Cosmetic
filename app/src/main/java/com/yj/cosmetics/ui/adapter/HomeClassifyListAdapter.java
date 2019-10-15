package com.yj.cosmetics.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.URLBuilder;
import com.yj.cosmetics.model.HomeClassifyEntity;
import com.yj.cosmetics.ui.activity.NormalWebViewActivity;
import com.yj.cosmetics.ui.activity.GoodsDetailActivity;
import com.yj.cosmetics.util.IntentUtils;
import com.yj.cosmetics.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/17.
 */

public class HomeClassifyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    HomeClassifyEntity.HomeClassifyData data;
    SpendDetialClickListener mItemClickListener;

    public HomeClassifyListAdapter(Context mContext, HomeClassifyEntity.HomeClassifyData data) {
        this.mContext = mContext;
        this.data = data;
    }

    public void setOnItemClickListener(SpendDetialClickListener listener) {
        this.mItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HeadViewHolder headViewHolder;
        ItemViewHolder itemViewHolder;
        if (viewType == 0) {
            headViewHolder = new HeadViewHolder(LayoutInflater
                    .from(mContext).inflate(R.layout.item_home_classify_head, parent, false));
            return headViewHolder;
        } else {
            itemViewHolder = new ItemViewHolder(LayoutInflater
                    .from(mContext).inflate(R.layout.item_home_classify_item, parent, false), mItemClickListener);
            return itemViewHolder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeadViewHolder) {
            List<View> mPagerList = new ArrayList<>();
            //每页数量
            int pageSize = 8;
            LayoutInflater inflater = LayoutInflater.from(mContext);
            //页数
            int pageCount = (int) Math.ceil(data.getProductClassifys().size() * 1.0 / pageSize);
            for (int i = 0; i < pageCount; i++) {
                // 每个页面都是inflate出一个新实例
                GridView gridView = (GridView) inflater.inflate(R.layout.gridview_classify, ((HeadViewHolder) holder).mViewPager, false);
                gridView.setAdapter(new HomeClassifyGridviewAdapter(mContext, data.getProductClassifys(), i, pageSize));
                mPagerList.add(gridView);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LogUtils.i("pos的值====" + position + "=====id的值======" + id);
                        IntentUtils.IntentToGoodsList(mContext, data.getProductClassifys().get((int) id).getClassify_id());
                    }
                });
            }
            //设置适配器
            LogUtils.i("pagerlist的长度" + mPagerList.size());
            ((HeadViewHolder) holder).mViewPager.setAdapter(new ViewPagerAdapter(mPagerList));

            /*((HeadViewHolder) holder).mRollpagerView.setLayoutParams(
                    WindowDisplayManager.getBannerHeight(mContext, ((HeadViewHolder) holder).mRollPagerView));*/
            ((HeadViewHolder) holder).mRollpagerView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if(!TextUtils.isEmpty(data.getBannerList().get(position).getProduct_id())) {
                        IntentUtils.IntentToGoodsDetial(mContext, data.getBannerList().get(position).getProduct_id());
                    }else if(!TextUtils.isEmpty(data.getBannerList().get(position).getBanner_url())){
                        Intent intent = new Intent(mContext, NormalWebViewActivity.class);
                        intent.putExtra("url",data.getBannerList().get(position).getBanner_url());
                        intent.putExtra("title","no");
                        mContext.startActivity(intent);
                    }else{
                        return;
                    }
                }
            });
            /*((HeadViewHolder) holder).mRollpagerView.setHintView(new CustomHintView(mContext,
                    R.drawable.shape_round360_e83, R.drawable.shape_round360_e83_stroke0_5_trans, DensityUtil.dip2px(mContext, mContext.getResources().getDimension(R.dimen.dis6))));
            ((HeadViewHolder) holder).mRollpagerView.setAnimationDurtion(1000);
            ((HeadViewHolder) holder).mRollpagerView.setHintPadding(0, 0, (int) mContext.getResources().getDimension(R.dimen.dis15), (int) mContext.getResources().getDimension(R.dimen.dis5));*/
            HomeClassifyBannerAdapter mBannerAdapter = new HomeClassifyBannerAdapter(((HeadViewHolder) holder).mRollpagerView, data.getBannerList(), mContext);
            ((HeadViewHolder) holder).mRollpagerView.setAdapter(mBannerAdapter);
        } else if (holder instanceof ItemViewHolder) {
            if (data.getProductArray().get(position - 1) != null && data.getProductArray().get(position - 1).size()>0) {
                ((ItemViewHolder) holder).llAll.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).ll2.setVisibility(View.INVISIBLE);
                ((ItemViewHolder) holder).ll3.setVisibility(View.INVISIBLE);
                ((ItemViewHolder) holder).tvHead.setText(data.getProductArray().get(position - 1).get(0).getClassifyName());

                ((ItemViewHolder) holder).tvTitle1.setText(data.getProductArray().get(position - 1).get(0).getProduct_name());
                ((ItemViewHolder) holder).tvPrice1.setText("￥"+data.getProductArray().get(position - 1).get(0).getProduct_current());
                Glide.with(mContext)
                        .load(URLBuilder.getUrl(data.getProductArray().get(position - 1).get(0).getProduct_listImg()))
                        .asBitmap()
                        .centerCrop()
                        .error(R.mipmap.default_goods)
                        .into(((ItemViewHolder) holder).iv1);
                ((ItemViewHolder) holder).ll1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                        intent.putExtra("productId", data.getProductArray().get(position - 1).get(0).getProduct_id());
                        mContext.startActivity(intent);
                    }
                });
                if (data.getProductArray().get(position - 1).size()>1) {
                    ((ItemViewHolder) holder).ll2.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) holder).tvTitle2.setText(data.getProductArray().get(position - 1).get(1).getProduct_name());
                    ((ItemViewHolder) holder).tvPrice2.setText("￥"+data.getProductArray().get(position - 1).get(1).getProduct_current());
                    Glide.with(mContext)
                            .load(URLBuilder.getUrl( data.getProductArray().get(position - 1).get(1).getProduct_listImg()))
                            .asBitmap()
                            .centerCrop()
                            .error(R.mipmap.default_goods)
                            .into(((ItemViewHolder) holder).iv2);
                    ((ItemViewHolder) holder).ll2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                            intent.putExtra("productId", data.getProductArray().get(position - 1).get(1).getProduct_id());
                            mContext.startActivity(intent);
                        }
                    });
                    if (data.getProductArray().get(position - 1).size()>2) {
                        ((ItemViewHolder) holder).ll3.setVisibility(View.VISIBLE);
                        ((ItemViewHolder) holder).tvTitle3.setText(data.getProductArray().get(position - 1).get(2).getProduct_name());
                        ((ItemViewHolder) holder).tvPrice3.setText("￥"+data.getProductArray().get(position - 1).get(2).getProduct_current());
                        Glide.with(mContext)
                                .load(URLBuilder.getUrl( data.getProductArray().get(position - 1).get(2).getProduct_listImg()))
                                .asBitmap()
                                .centerCrop()
                                .error(R.mipmap.default_goods)
                                .into(((ItemViewHolder) holder).iv3);
                        ((ItemViewHolder) holder).ll3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                                intent.putExtra("productId", data.getProductArray().get(position - 1).get(2).getProduct_id());
                                mContext.startActivity(intent);
                            }
                        });
                    }
                }
                ((ItemViewHolder) holder).tvMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentUtils.IntentToGoodsList(mContext, data.getProductArray().get(position - 1).get(0).getClassify_id());
                    }
                });
            } else {
                ((ItemViewHolder) holder).llAll.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            if (data.getProductArray() != null && data.getProductArray().size() > 0) {
                return 1 + data.getProductArray().size();
            }
            return 1;
        }
        return 0;
    }

    public void setData(HomeClassifyEntity.HomeClassifyData data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rollPagerView)
        RollPagerView mRollpagerView;
        @BindView(R.id.viewpager)
        ViewPager mViewPager;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.home_classify_item_tv_head)
        TextView tvHead;
        @BindView(R.id.home_classify_item_iv1)
        ImageView iv1;
        @BindView(R.id.home_classify_item_iv2)
        ImageView iv2;
        @BindView(R.id.home_classify_item_iv3)
        ImageView iv3;
        @BindView(R.id.home_classify_item_tv_title1)
        TextView tvTitle1;
        @BindView(R.id.home_classify_item_tv_title2)
        TextView tvTitle2;
        @BindView(R.id.home_classify_item_tv_title3)
        TextView tvTitle3;
        @BindView(R.id.home_classify_item_tv_price1)
        TextView tvPrice1;
        @BindView(R.id.home_classify_item_tv_price2)
        TextView tvPrice2;
        @BindView(R.id.home_classify_item_tv_price3)
        TextView tvPrice3;
        @BindView(R.id.home_classify_item_ll1)
        LinearLayout ll1;
        @BindView(R.id.home_classify_item_ll2)
        LinearLayout ll2;
        @BindView(R.id.home_classify_item_ll3)
        LinearLayout ll3;
        @BindView(R.id.home_classify_item_more)
        TextView tvMore;
        @BindView(R.id.home_classify_ll_all)
        LinearLayout llAll;
        private SpendDetialClickListener mListener;

        public ItemViewHolder(View itemView, SpendDetialClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    }

    public interface SpendDetialClickListener {
        public void onItemClick(View view, int postion);
    }
}