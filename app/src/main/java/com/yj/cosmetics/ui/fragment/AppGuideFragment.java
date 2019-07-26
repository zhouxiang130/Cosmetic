package com.yj.cosmetics.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.Constant;

/**
 * 作者: Administrator on 2016/7/18.
 * 邮箱:z416916003@163.com
 */
public class AppGuideFragment extends Fragment {

    int page = 0;

    private ImageView imageView;
    public static AppGuideFragment getInstance(int page) {
        AppGuideFragment fragment = new AppGuideFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            throw new IllegalArgumentException("Has no arguments founded~");
        }
        page = getArguments().getInt("page");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View convertView = inflater.inflate(R.layout.app_guide_item_layout, container, false);
        imageView = (ImageView)convertView.findViewById(R.id.imgView);
        initView(convertView);
        return convertView;
    }

    private void initView(View convertView) {
        Glide.with(getActivity()).load(Constant.appGuideRes[page])
                .asBitmap().centerCrop().into(imageView);
//        imageView.setBackgroundResource(Constant.appGuideRes[page]);
    }


}