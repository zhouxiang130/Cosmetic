package com.yj.cosmetics.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Suo on 2016/12/12.
 */

public class MineOrderTabAdapters extends FragmentPagerAdapter {


    private List<Fragment> views;

    public MineOrderTabAdapters(FragmentManager fm, List<Fragment> views){

        super(fm);

        this.views = views;

    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
