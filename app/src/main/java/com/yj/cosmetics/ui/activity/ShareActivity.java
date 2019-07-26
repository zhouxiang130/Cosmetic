package com.yj.cosmetics.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.yj.cosmetics.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class ShareActivity extends AppCompatActivity implements PlatformActionListener, Handler.Callback {
    private static final int MSG_USERID_FOUND = 1;//用户已经存在
    private static final int MSG_LOGIN = 2;//登录中
    private static final int MSG_AUTH_CANCEL = 3;//取消授权
    private static final int MSG_AUTH_ERROR = 4;//授权出错
    private static final int MSG_AUTH_COMPLETE = 5;//授权成功
    private HashMap<String, Object> mapQQ = new HashMap<String, Object>();//授权信息
    private HashMap<String, Object> mapSina = new HashMap<String, Object>();//授权信息
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_share);
        ShareSDK.initSDK(this);
    }
    //第一个按钮，表示分享
    public void Btn1(View view) {
        showShare();
    }

    //第二个按钮表示qq登录
    public void Btn2(View view) {
        authorize(new QQ(this));
    }

    //第三个按钮表示微博登录
    public void Btn3(View view) {
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("title");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    private void authorize(Platform plat) {
        if (plat.isValid()) {
            String userId = plat.getDb().getUserId();
            Log.i("----id",userId);
            if (!TextUtils.isEmpty(userId)) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                if (plat.getName().equals("QQ")) {
                    login(plat.getName(), userId, mapQQ);

                }
                else if (plat.getName().equals("TencentWeibo")) {
                    login(plat.getName(), userId, mapSina);
                }
                return;
            }
        }
        //若本地没有授权过就请求用户数据
        plat.setPlatformActionListener(this);//
        plat.SSOSetting(false);//此处设置为false，则在优先采用客户端授权的方法，设置true会采用网页方式
        plat.showUser(null);//获得用户数据

    }

    private void login(String plat, String userId, HashMap<String, Object> userInfo) {

        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
        //跳转到第二个页面，获取到的数据就在这里
//        Intent intent = new Intent(ShareActivity.this, SecondActivity.class);
//        intent.putExtra("userinfo", "userinfo:" + userInfo.toString());
//        startActivity(intent);
    }

    //一定要停止
    @Override
    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }

    public void onComplete(Platform platform, int action,
                           HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            Log.e("ShareActivity", platform.getName());
            if (platform.getName().equals("QQ")) {
                mapQQ.clear();
                mapQQ.putAll(res);
            }
            else if (platform.getName().equals("TencentWeibo")) {
                mapSina.clear();
                mapSina.putAll(res);
            }
//            else if (platform.getName().equals("Facebook")) {
//                mapFB.clear();
//                mapFB = res;
//            }
//            else if (platform.getName().equals("GooglePlus")) {
//                mapGG.clear();
//                mapGG = res;
//            }
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            login(platform.getName(), platform.getDb().getUserId(), res);
        }
        System.out.println(res);
    }

    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        t.printStackTrace();
    }

    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MSG_USERID_FOUND: {
                Toast.makeText(this, "-----", Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_LOGIN: {
                String text = getString(R.string.logining, message.obj);
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_CANCEL: {
                Toast.makeText(this, "auth_cancel", Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_ERROR: {
                Toast.makeText(this, "auth_error", Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_COMPLETE: {
                Toast.makeText(this, "auth_complete", Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return false;
    }
}
