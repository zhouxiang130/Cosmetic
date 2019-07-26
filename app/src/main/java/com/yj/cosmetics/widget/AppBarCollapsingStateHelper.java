package com.yj.cosmetics.widget;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;

/**
 * 监听可折叠 Toolbar 的折叠状态
 *
 * @author bakumon
 * @date 17-9-14
 */

public class AppBarCollapsingStateHelper {
    private AppBarLayout mAppBarLayout;

    private AppBarCollapsingStateHelper(@NonNull AppBarLayout appBarLayout) {
        this.mAppBarLayout = appBarLayout;
    }

    public static AppBarCollapsingStateHelper with(@NonNull AppBarLayout appBarLayout) {
        return new AppBarCollapsingStateHelper(appBarLayout);
    }

    public void listener(final AppBarStateListener listener) {
        if (listener == null) {
            return;
        }
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private int oldVerticalOffset;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    listener.onExpanded();
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    listener.onCollapsed();
                } else {
                    listener.onChanging(verticalOffset > oldVerticalOffset);
                    oldVerticalOffset = verticalOffset;

                }
            }
        });
    }

    interface AppBarStateListener {
        /**
         * 完全展开状态
         */
        void onExpanded();

        /**
         * 完全折叠
         */
        void onCollapsed();

        /**
         * 完全展开状态 和 完全折叠 的中间状态
         *
         * @param isBecomingExpanded 是否正在展开 true:正在展开 false:正在折叠
         */
        void onChanging(boolean isBecomingExpanded);
    }

    /**
     * @see AppBarStateListener 的空实现
     */
    public static class DefaultAppBarStateListener implements AppBarStateListener {
        @Override
        public void onExpanded() {

        }

        @Override
        public void onCollapsed() {

        }

        @Override
        public void onChanging(boolean isBecomingExpanded) {

        }
    }
}
