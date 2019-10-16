package com.yj.cosmetics.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.base.Constant;
import com.yj.cosmetics.ui.fragment.AppGuideFragment;
import com.yj.cosmetics.ui.fragment.AppGuideFragment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Suo on 2016/12/31.
 */

public class AppGuideActivity extends BaseActivity {
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    List<Integer> imgRes;
    List<Fragment> fragmentList;

    @Override
    protected int getContentView() {
        return R.layout.activity_app_guide;
    }

    @Override
    protected void initView() {
        imgRes = new ArrayList<Integer>();
        imgRes.clear();
        imgRes.addAll(Arrays.asList(Constant.appGuideRes));
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(AppGuideFragment.getInstance(0));
        fragmentList.add(AppGuideFragment.getInstance(1));
        fragmentList.add(AppGuideFragment1.getInstance(2));
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), imgRes, fragmentList));
    }

    @Override
    protected void initData() {

    }

    private class MyAdapter extends FragmentPagerAdapter {
        List<Integer> imgRes;
        List<Fragment> fragmentList;

        public MyAdapter(FragmentManager fm, List<Integer> imgRes, List<Fragment> fragmentList) {
            super(fm);
            this.imgRes = imgRes;
            this.fragmentList = fragmentList;
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return imgRes.size();
        }
    }

}
