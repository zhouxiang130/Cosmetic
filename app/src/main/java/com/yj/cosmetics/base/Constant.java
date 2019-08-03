package com.yj.cosmetics.base;

import com.yj.cosmetics.R;

/**
 * Created by Suo on 2017/9/7.
 */
public class Constant {
	//崩溃日志文件
	public static final String LOG_FILE = "crash";

	public static final int USER_LOGIN_OUT = 0x002;
	//首页导航图三张
	public static final Integer[] appGuideRes = {R.drawable.leader_guide_page1, R.drawable.leader_guide_page2, R.drawable.leader_guide_page3};


	// APP_ID 替换为你的应用从官方网站申请到的合法appId
	public static final String APP_ID = "wx1e522f6d3ce6f599";
	public static final String ZC_appkey = "1add5fe6182c48fbb2c9d5c41211bfe6";
	public static final String KEY = "1864325941258664";

	public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";
	}
}
