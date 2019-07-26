package com.yj.cosmetics.util;

import android.os.Handler;
import android.os.Looper;

import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.HeadBuilder;
import com.zhy.http.okhttp.builder.OtherRequestBuilder;
import com.zhy.http.okhttp.builder.PostFileBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.CookieStore;
import com.zhy.http.okhttp.cookie.store.HasCookieStore;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import com.zhy.http.okhttp.request.RequestCall;
import com.zhy.http.okhttp.utils.Exceptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Suo on 2017/9/21.
 */

public class OkhttpFileUtils {
    public static final long DEFAULT_MILLISECONDS = 10000L;
    private static OkhttpFileUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    public OkhttpFileUtils(OkHttpClient okHttpClient) {
        if(okHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            this.mOkHttpClient = okHttpClientBuilder.build();
        } else {
            this.mOkHttpClient = okHttpClient;
        }

        this.init();
    }

    private void init() {
        this.mDelivery = new Handler(Looper.getMainLooper());
    }

    public OkhttpFileUtils debug(String tag) {
        this.mOkHttpClient = this.getOkHttpClient().newBuilder().addInterceptor(new LoggerInterceptor(tag, false)).build();
        return this;
    }

    public OkhttpFileUtils debug(String tag, boolean showResponse) {
        this.mOkHttpClient = this.getOkHttpClient().newBuilder().addInterceptor(new LoggerInterceptor(tag, showResponse)).build();
        return this;
    }

    public static OkhttpFileUtils getInstance(OkHttpClient okHttpClient) {
        if(mInstance == null) {
            Class var1 = OkhttpFileUtils.class;
            synchronized(OkhttpFileUtils.class) {
                if(mInstance == null) {
                    mInstance = new OkhttpFileUtils(okHttpClient);
                }
            }
        }

        return mInstance;
    }

    public static OkhttpFileUtils getInstance() {
        if(mInstance == null) {
            Class var0 = OkhttpFileUtils.class;
            synchronized(OkhttpFileUtils.class) {
                if(mInstance == null) {
                    mInstance = new OkhttpFileUtils((OkHttpClient)null);
                }
            }
        }

        return mInstance;
    }

    public Handler getDelivery() {
        return this.mDelivery;
    }

    public OkHttpClient getOkHttpClient() {
        return this.mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static OkhttpFileFormBuilder post() {
        return new OkhttpFileFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder("PUT");
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder("DELETE");
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder("PATCH");
    }

    public void execute(RequestCall requestCall, Callback callback) {

        if(callback == null) {
            callback = Callback.CALLBACK_DEFAULT;
        }
        final Callback callback1 = callback;
        requestCall.getCall().enqueue(new okhttp3.Callback() {
            public void onFailure(Call call, IOException e) {
                OkhttpFileUtils.this.sendFailResultCallback(call, e, callback1);
            }

            public void onResponse(Call call, Response response) {
                if(response.code() >= 400 && response.code() <= 599) {
                    try {
                        OkhttpFileUtils.this.sendFailResultCallback(call, new RuntimeException(response.body().string()), callback1);
                    } catch (IOException var4) {
                        var4.printStackTrace();
                    }

                } else {
                    try {
                        Object e = callback1.parseNetworkResponse(response);
                        OkhttpFileUtils.this.sendSuccessResultCallback(e, callback1);
                    } catch (Exception var5) {
                        OkhttpFileUtils.this.sendFailResultCallback(call, var5, callback1);
                    }

                }
            }
        });
    }

    public CookieStore getCookieStore() {
        CookieJar cookieJar = this.mOkHttpClient.cookieJar();
        if(cookieJar == null) {
            Exceptions.illegalArgument("you should invoked okHttpClientBuilder.cookieJar() to set a cookieJar.", new Object[0]);
        }

        return cookieJar instanceof HasCookieStore ?((HasCookieStore)cookieJar).getCookieStore():null;
    }

    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback) {
        if(callback != null) {
            this.mDelivery.post(new Runnable() {
                public void run() {
                    callback.onError(call, e);
                    callback.onAfter();
                }
            });
        }
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback) {
        if(callback != null) {
            this.mDelivery.post(new Runnable() {
                public void run() {
                    callback.onResponse(object);
                    callback.onAfter();
                }
            });
        }
    }

    public void cancelTag(Object tag) {
        Iterator i$ = this.mOkHttpClient.dispatcher().queuedCalls().iterator();

        Call call;
        while(i$.hasNext()) {
            call = (Call)i$.next();
            if(tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

        i$ = this.mOkHttpClient.dispatcher().runningCalls().iterator();

        while(i$.hasNext()) {
            call = (Call)i$.next();
            if(tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

    }

    public void setCertificates(InputStream... certificates) {
        SSLSocketFactory sslSocketFactory = HttpsUtils.getSslSocketFactory(certificates, (InputStream)null, (String)null);
        OkHttpClient.Builder builder = this.getOkHttpClient().newBuilder();
        builder = builder.sslSocketFactory(sslSocketFactory);
        this.mOkHttpClient = builder.build();
    }

    public void setCertificates(InputStream[] certificates, InputStream bksFile, String password) {
        this.mOkHttpClient = this.getOkHttpClient().newBuilder().sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, bksFile, password)).build();
    }

    public void setHostNameVerifier(HostnameVerifier hostNameVerifier) {
        this.mOkHttpClient = this.getOkHttpClient().newBuilder().hostnameVerifier(hostNameVerifier).build();
    }

    public void setConnectTimeout(int timeout, TimeUnit units) {
        this.mOkHttpClient = this.getOkHttpClient().newBuilder().connectTimeout((long)timeout, units).build();
    }

    public void setReadTimeout(int timeout, TimeUnit units) {
        this.mOkHttpClient = this.getOkHttpClient().newBuilder().readTimeout((long)timeout, units).build();
    }

    public void setWriteTimeout(int timeout, TimeUnit units) {
        this.mOkHttpClient = this.getOkHttpClient().newBuilder().writeTimeout((long)timeout, units).build();
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";

        public METHOD() {
        }
    }
}
