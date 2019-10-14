package com.yj.cosmetics.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yj.cosmetics.R;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.SharedPreferencesUtil;
import com.yj.cosmetics.util.UserUtils;

import butterknife.ButterKnife;

/**
 * Created by Suo on 2017/4/11.
 */

public abstract class BaseFragment extends Fragment {
	LinearLayout loca;
	RelativeLayout bell;
	protected View mView;
	RelativeLayout title;
	public UserUtils mUtils;
	public SharedPreferencesUtil preferencesUtil;

	protected abstract void initData();

	protected View createView(View view) {
		this.mView = view;
		ButterKnife.bind(this, view);
		preferencesUtil = new SharedPreferencesUtil(getActivity());
		mUtils = UserUtils.getInstance(getActivity());
//        initTitlebar();
		initData();
		return mView;
	}

	/*protected void initTitlebar(){
		mUtils = UserUtils.getInstance(getActivity());
		loca = (LinearLayout) findView(R.id.frag_title_loca);
		bell = (RelativeLayout) findView(R.id.frag_title_rl1);
		title = (RelativeLayout)findView(R.id.frag_title_layout);
		tvLocate = (TextView)findView(R.id.frag_title_tvlocate);
		if (loca != null) {
			loca.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					Intent intent = new Intent(getActivity(), LocationNewActivity.class);
					startActivity(intent);
				}
			});
		}
		if (bell != null) {
			bell.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if(mUtils.isLogin()) {
						Intent intent = new Intent(getActivity(), NewsBellActivity.class);
						startActivity(intent);
					}else{
						IntentUtils.IntentToLogin(getActivity());
					}
				}
			});
		}
		if(title != null){
			showShadow();
		}

	}*/
	public void showShadow() {
		if (Build.VERSION.SDK_INT >= 21) {
			LogUtils.i("我在frag的showShadow");
			title.setElevation(getResources().getDimension(R.dimen.dis2));
			title.setOutlineProvider(ViewOutlineProvider.BOUNDS);
		}
	}
   /* public void setTitleText(CharSequence text){
        TextView tv = (TextView) findView(R.id.frag_title_tv);
        if(tv != null){
            tv.setText(text);
        }
    }*/

	public void finishFragment() {
		(this.getActivity()).getSupportFragmentManager().popBackStack();
	}

	protected void doBackEvent() {
		finishFragment();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		LogUtils.i("outState的值" + outState);
//        super.onSaveInstanceState(outState);
	}
}
