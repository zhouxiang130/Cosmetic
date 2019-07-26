package com.yj.cosmetics.util;


import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Suo on 2017/6/14.
 */

public class Utils {

    public static abstract class MyResultCallback<T> extends Callback<T> {

        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
        }

        @Override
        public void onAfter() {
            super.onAfter();
        }

        @Override
        public void onError(Call call, Exception e) {

        }
    }

}
