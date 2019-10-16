package com.yj.cosmetics.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yj.cosmetics.MyApplication;
import com.yj.cosmetics.R;
import com.yj.cosmetics.base.BaseActivity;
import com.yj.cosmetics.util.LogUtils;
import com.yj.cosmetics.util.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Suo on 2017/4/6.
 */

public class UpdateActivity extends BaseActivity {
    @BindView(R.id.tv_update_des)
    TextView tvDes;
    @BindView(R.id.tv_progress_percent)
    TextView tvProgress;
    @BindView(R.id.update_pb)
    ProgressBar pb;
    @BindView(R.id.ll_update_try)
    LinearLayout lTry;
    @BindView(R.id.ll_update_cancel)
    LinearLayout lCancel;
    private String des;
    private String url;
    private String target;
    private String appName;
    private File downloadfile;
    private int downPercent;

    @Override
    protected int getContentView() {
        return R.layout.activity_update;
    }

    @Override
    protected void initView() {
        LogUtils.i("我initeView了");
        try {
            des = getIntent().getStringExtra("content");
            url = getIntent().getStringExtra("url");
            tvDes.setText(des.replace("\\n", "\n"));
            appName = url.substring(url.lastIndexOf("/") + 1);
        }catch (Exception e){
            ToastUtils.showToast(UpdateActivity.this,"无法获取升级信息,请检查网络");
            Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i("我onResume了");
    }

    @Override
    protected void initData() {
        if(initAppDir()){
            //true 说明有文件,直接弹窗口
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addCategory(Intent.CATEGORY_DEFAULT);
            install.setDataAndType(Uri.fromFile(downloadfile), "application/vnd.android.package-archive");
            getApplicationContext().startActivity(install);
            finish();

        }else {
            //说明没有下载好.
            LogUtils.i("我开始升级了");
            if(appName != null) {
                if (appName.endsWith(".apk")) {
                    appName = appName.replace(".apk", ".temp");
                    LogUtils.i("appName的值" + appName);
                }
            }else{
                finish();
            }
            doUpdate();
        }
    }
    private boolean initAppDir(){
        /**
         * 创建路径的时候一定要用[/],不能使用[\],但是创建文件夹加文件的时候可以使用[\].
         * [/]符号是Linux系统路径分隔符,而[\]是windows系统路径分隔符 Android内核是Linux.
         */
        if (isHasSdcard())// 判断是否插入SD卡
        {
            target = Environment.getExternalStorageDirectory().getAbsolutePath() + "/robust/download/";// 保存到SD卡路径下
        }
        else{
            target = getApplicationContext().getFilesDir().getAbsolutePath() + "/robust/download/";// 保存到app的包名路径下
        }
        File destDir = new File(target);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        }
        downloadfile = new File(target + appName);
        if(!downloadfile.exists()){
            //文件不存在,创建
            LogUtils.i("我在文件不存在中");
            return  false;
        }else{
            //文件存在 返回true
            LogUtils.i("我在文件存在中");
            return true;
        }
    }
    private void doUpdate(){
        LogUtils.i("我开始下载APp了"+url);
        OkHttpUtils.get().url(url)
                .tag(this)
                .build()
                .connTimeOut(30000)
                .readTimeOut(30000)
                .writeTimeOut(30000)
                .execute(new FileCallBack(target,appName) {
            @Override
            public void inProgress(float v, long l) {
                //Math.round(v*100)
                downPercent = Math.round(v*100);
//                builder1.setContentText("正在下载:"+downPercent+"%"); //消息内容
                if(downPercent % 1 == 0 ) {

                    pb.setProgress(downPercent);
                    tvProgress.setText(downPercent+"%");
                }

            }


            @Override
            public void onError(Call call, Exception e) {
                LogUtils.i("我在Error中"+e);
                if(call.isCanceled()){
                    LogUtils.i("我进入cancel网络操作了");
                    call.cancel();
                }else {
                    tvProgress.setText("下载失败,请检查网络后重试");
                    //下载失败 应显示重新下载按钮
                    lTry.setVisibility(View.VISIBLE);
                    lCancel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onResponse(File file) {
                LogUtils.i("我在onResponse了"+file);
                if(appName.endsWith(".temp")){
                    appName = appName.replace(".temp",".apk");
                    LogUtils.i("response中 AppName的值"+appName);
                }
                File newFile = new File(target+appName);
                file.renameTo(newFile);
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setDataAndType(Uri.fromFile(newFile), "application/vnd.android.package-archive");
                //在BroadcastReceiver和Service中startActivity要加上此设置
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);// 下载完成之后自动弹出安装界面
                finish();
            }
        });
    }

    @OnClick({R.id.ll_update_try,R.id.ll_update_cancel})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_update_try :
                lTry.setVisibility(View.GONE);
                lCancel.setVisibility(View.GONE);
                doUpdate();
                break;
            case R.id.ll_update_cancel:
                MyApplication.exit();
                break;
        }
    }
    /**
     *
     * @Description:判断是否插入SD卡
     */
    private boolean isHasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.i("我在onPause中");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i("我在onStop中");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i("我在onDestory中");
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
