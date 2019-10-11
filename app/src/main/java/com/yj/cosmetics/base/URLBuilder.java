package com.yj.cosmetics.base;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Suo on 2017/4/12.
 */

public class URLBuilder {

//	public static final String URLBaseHeader = "http://www.szffxz.com";//正式服务器/
//	public static final String URLBaseHeaders = "https://www.wendiapp.com";//部分使用 https:处理加密操作


//	public static final String URLBaseHeader = "http://192.168.0.103:8080";//测试接口2
//	public static final String URLBaseHeader = "http://192.168.0.115";//测试接口2
public static final String URLBaseHeader = "http://192.168.1.176:8088";//测试接口
//	public static final String URLBaseHeader = "http://192.168.0.108";//测试接口3

	public static final String searchUserBill = "/phone/user/billCash";//我的免单列表
	public static final String billCash = "/phone/user/userSeller";//账单


	public static final String Login = "/phone/user/login";//登录
	public static final String Regist = "/phone/user/register";//注册
	public static final String USERMESSAGE = "/phone/user/userMessage";//获取个人信息

	public static final String UpdateHeader = "/phone/user/updateUserHeadimg";//上传头像
	public static final String ModifyPass = "/phone/user/findPassword";//忘记密码
	public static final String SendMsg = "/phone/user/verificationCodeSession";//获取验证码
	public static final String NEWSHOPCARLIST = "/phone/homePage/newShopCarList";//获取购物车数据
	public static final String PRODUCTSORT = "/phone/homePage/productSort";//
	// 获取购物车推荐列表
	public static final String DELETECARTBYIDS = "/phone/homePage/deleteCartByIds";//删除购物车商品
	public static final String UPDATECARTNUM = "/phone/homePage/updateCartNum";//更改商品数量传输的值


	public static final String defaultAddressToIndex = "/phone/userUp/defaultAddressToIndex";//默认地址

	public static final String sellerDt = "/phone/user/sellerDt";//排队商家详情

	public static final String OpenIdVerify = "/phone/user/quick";
	public static final String CompanyDes = "/page/desc.jsp";
	public static final String Agreement = "/page/serviceDesc.jsp";
	public static final String SjRz = "/weChat/homePageTwo/toApply";

	public static final String ScoreRule = "/phone/homePage/rule.do?type=1";
	public static final String InviteRule = "/phone/homePage/rule.do?type=2";

	public static String getUrl(String url) {
		if (url.startsWith("http")) {
			return url;
		} else {
//			if (url.startsWith("/")) {
//				url = url.subSequence(1, url.length()).toString();
//			}
			return URLBaseHeader + url;
		}
	}


	public static String getURLs(String url) {
		if (url.startsWith("/")) {
			return url;
		} else {
			url = new StringBuffer(url).insert(0, "/") + "";
		}
		return url;
	}


	public static String format(Map<String, String> obj) {
		JSONObject object = new JSONObject(obj);
		return object.toString().trim();
	}
}
