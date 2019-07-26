package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.util.LogUtils;

import butterknife.BindView;

/**
 * Created by Suo on 2017/12/13.
 */

public class NormalWebViewActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.title_layout)
    LinearLayout llTop;

    private String url;
    private String title;
    @Override
    protected int getContentView() {
        return R.layout.activity_webview_normal;
    }

    @Override
    protected void initView() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        if(!TextUtils.isEmpty(title) ){
            if(title.equals("no")){
                llTop.setVisibility(View.GONE);
            }else {
                llTop.setVisibility(View.VISIBLE);
                setTitleText(title);
            }
        }
        webViewInit();
    }

    @Override
    protected void initData() {
        LogUtils.i("url的值"+url);
        mWebView.loadUrl(url);
    }

    private void webViewInit() {
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtils.i("pageStarted"+url);
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            /*mProgressLayout.showFailed(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    initData();
                }
            });*/
            LogUtils.i("收到错误了errCode"+errorCode+".....description"+description+"...错误地址"+failingUrl);
        }
    }
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            setTitleText(title);
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressbar.setProgress(newProgress);

            if (newProgress == 100) {
                mProgressbar.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mProgressbar.setVisibility(View.GONE);
                    }
                }, 1000);

            } else {
                mProgressbar.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onBackPressed() {
        LogUtils.i("可以goback吗"+mWebView.canGoBack());
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止mebview里有别的内容 由于导致没有释放完毕出错 所以重定向
        destroyWebView();
    }
    public void destroyWebView() {

        if(mWebView != null) {
            mWebView.clearHistory();
            mWebView.clearCache(true);
            mWebView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            mWebView.freeMemory();
            mWebView.pauseTimers();
            mWebView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }

    }
}
