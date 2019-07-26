package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2019/5/17 0017.
 */

public class AccountListEntity {
	/**
	 * code : 200
	 * data : {"list":[{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190409/06e08ba6-e60c-4234-9991-e68b45d06c82.png","insertTime":"2019-04-17 09:16:23","orderNums":"20190417091623343845","payMoney":0.1,"payTime":"2019-04-17 09:16:35","shopAddress":"西班牙小镇商业街","shopName":"玲禾心语瑜伽普拉提","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190407/54217164-bf0a-410e-ba31-7cd0082f9b4c.png","insertTime":"2019-04-07 16:19:30","orderNum":"20190407161930166990","orderNums":"20190407161930166990","payMoney":1,"payTime":"2019-04-07 16:19:40","shopAddress":"购物中心","shopName":"挑衫选饰","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190407/54217164-bf0a-410e-ba31-7cd0082f9b4c.png","insertTime":"2019-04-07 16:19:00","orderNum":"20190407161900830764","orderNums":"20190407161900830764","payMoney":1,"payTime":"2019-04-07 16:19:11","shopAddress":"购物中心","shopName":"挑衫选饰","userName":"Smile微笑丨"},{"address":"河南省,郑州市,管城回族区","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190404/83eefdc8-c9a4-43ae-b5fa-92febb541cea.png","insertTime":"2019-04-04 18:57:16","orderNum":"20190404185716113203","orderNums":"20190404185716113203","payMoney":1,"payTime":"2019-04-04 18:57:42","shopAddress":"万通街与农业南路交叉口","shopName":"都市女人心","userName":"Smile微笑丨"},{"address":"河南省,郑州市,管城回族区","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190404/83eefdc8-c9a4-43ae-b5fa-92febb541cea.png","insertTime":"2019-04-04 18:56:49","orderNum":"20190404185649821258","orderNums":"20190404185649821258","payMoney":1,"payTime":"2019-04-04 18:56:59","shopAddress":"万通街与农业南路交叉口","shopName":"都市女人心","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190401/a02aa599-d64a-4634-8f4c-0a1f7014fd1d.png","insertTime":"2019-04-01 15:27:31","orderNums":"20190401152731328783","payMoney":0.1,"payTime":"2019-04-01 15:27:42","shopAddress":"兰考县黄河路与健康路交叉口西六十米路南","shopName":"骆驼户外生活馆","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190131/f88e4b5e-1eb2-4357-b16a-cb4afbc62600.png","insertTime":"2019-03-29 11:16:57","orderNums":"20190329111657016584","payMoney":0.01,"payTime":"2019-03-29 11:17:11","shopAddress":"兰考县中山西街33号","shopName":"广汇名装","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190131/f88e4b5e-1eb2-4357-b16a-cb4afbc62600.png","insertTime":"2019-03-28 09:23:04","orderNums":"20190328092304678097","payMoney":0.01,"payTime":"2019-03-28 09:23:16","shopAddress":"兰考县中山西街33号","shopName":"广汇名装","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com//upload/head/sellersImg/2019/03/21/0766801f-d894-4c49-b312-9a27a25a4028.jpg","insertTime":"2019-03-23 09:55:05","orderNums":"20190323095505950735","payMoney":0.1,"payTime":"2019-03-23 09:55:16","shopAddress":"西班牙小镇","shopName":"兰考县桃子服装店","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190217/e7c4d377-e1ac-45bb-b88e-8b17188558e0.png","insertTime":"2019-03-21 09:50:31","orderNums":"20190321095031478073","payMoney":1,"payTime":"2019-03-21 09:50:42","shopAddress":"南湖公园","shopName":"豌豆馅丨玉米丨兰花豆","userName":"Smile微笑丨"}],"total":88}
	 * msg : 成功
	 */

	private String code;
	private DataBean data;
	private String msg;

	public final String HTTP_OK = "200";


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static class DataBean {
		/**
		 * list : [{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190409/06e08ba6-e60c-4234-9991-e68b45d06c82.png","insertTime":"2019-04-17 09:16:23","orderNums":"20190417091623343845","payMoney":0.1,"payTime":"2019-04-17 09:16:35","shopAddress":"西班牙小镇商业街","shopName":"玲禾心语瑜伽普拉提","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190407/54217164-bf0a-410e-ba31-7cd0082f9b4c.png","insertTime":"2019-04-07 16:19:30","orderNum":"20190407161930166990","orderNums":"20190407161930166990","payMoney":1,"payTime":"2019-04-07 16:19:40","shopAddress":"购物中心","shopName":"挑衫选饰","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190407/54217164-bf0a-410e-ba31-7cd0082f9b4c.png","insertTime":"2019-04-07 16:19:00","orderNum":"20190407161900830764","orderNums":"20190407161900830764","payMoney":1,"payTime":"2019-04-07 16:19:11","shopAddress":"购物中心","shopName":"挑衫选饰","userName":"Smile微笑丨"},{"address":"河南省,郑州市,管城回族区","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190404/83eefdc8-c9a4-43ae-b5fa-92febb541cea.png","insertTime":"2019-04-04 18:57:16","orderNum":"20190404185716113203","orderNums":"20190404185716113203","payMoney":1,"payTime":"2019-04-04 18:57:42","shopAddress":"万通街与农业南路交叉口","shopName":"都市女人心","userName":"Smile微笑丨"},{"address":"河南省,郑州市,管城回族区","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190404/83eefdc8-c9a4-43ae-b5fa-92febb541cea.png","insertTime":"2019-04-04 18:56:49","orderNum":"20190404185649821258","orderNums":"20190404185649821258","payMoney":1,"payTime":"2019-04-04 18:56:59","shopAddress":"万通街与农业南路交叉口","shopName":"都市女人心","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190401/a02aa599-d64a-4634-8f4c-0a1f7014fd1d.png","insertTime":"2019-04-01 15:27:31","orderNums":"20190401152731328783","payMoney":0.1,"payTime":"2019-04-01 15:27:42","shopAddress":"兰考县黄河路与健康路交叉口西六十米路南","shopName":"骆驼户外生活馆","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190131/f88e4b5e-1eb2-4357-b16a-cb4afbc62600.png","insertTime":"2019-03-29 11:16:57","orderNums":"20190329111657016584","payMoney":0.01,"payTime":"2019-03-29 11:17:11","shopAddress":"兰考县中山西街33号","shopName":"广汇名装","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190131/f88e4b5e-1eb2-4357-b16a-cb4afbc62600.png","insertTime":"2019-03-28 09:23:04","orderNums":"20190328092304678097","payMoney":0.01,"payTime":"2019-03-28 09:23:16","shopAddress":"兰考县中山西街33号","shopName":"广汇名装","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com//upload/head/sellersImg/2019/03/21/0766801f-d894-4c49-b312-9a27a25a4028.jpg","insertTime":"2019-03-23 09:55:05","orderNums":"20190323095505950735","payMoney":0.1,"payTime":"2019-03-23 09:55:16","shopAddress":"西班牙小镇","shopName":"兰考县桃子服装店","userName":"Smile微笑丨"},{"address":"河南省,开封市,兰考县","headimg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190217/e7c4d377-e1ac-45bb-b88e-8b17188558e0.png","insertTime":"2019-03-21 09:50:31","orderNums":"20190321095031478073","payMoney":1,"payTime":"2019-03-21 09:50:42","shopAddress":"南湖公园","shopName":"豌豆馅丨玉米丨兰花豆","userName":"Smile微笑丨"}]
		 * total : 88
		 */

		private String total;
		private String mMoney;
		private String mcash;
		private List<ListBean> list;

		public String getMcash() {
			return mcash;
		}

		public void setMcash(String mcash) {
			this.mcash = mcash;
		}

		public String getmMoney() {
			return mMoney;
		}

		public void setmMoney(String mMoney) {
			this.mMoney = mMoney;
		}


		public String getTotal() {
			return total;
		}

		public void setTotal(String total) {
			this.total = total;
		}

		public List<ListBean> getList() {
			return list;
		}

		public void setList(List<ListBean> list) {
			this.list = list;
		}

		public static class ListBean {
			/**
			 * address : 河南省,开封市,兰考县
			 * headimg : https://wjf-imgs.oss-cn-qingdao.aliyuncs.com/upload/user/20190409/06e08ba6-e60c-4234-9991-e68b45d06c82.png
			 * insertTime : 2019-04-17 09:16:23
			 * orderNums : 20190417091623343845
			 * payMoney : 0.1
			 * payTime : 2019-04-17 09:16:35
			 * shopAddress : 西班牙小镇商业街
			 * shopName : 玲禾心语瑜伽普拉提
			 * userName : Smile微笑丨
			 * orderNum : 20190407161930166990
			 */

			private String headimg;
			private String insertTime;
			private String orderNums;
			private String payMoney;
			private String payTime;
			private String shopAddress;
			private String shopName;
			private String userName;
			private String orderNum;
			private int sellerId;
			private String  shopId;
			private String orderDescId;
			private int rowNum;

			public void setOrderDescId(String orderDescId) {
				this.orderDescId = orderDescId;
			}

			public int getRowNum() {
				return rowNum;
			}

			public void setRowNum(int rowNum) {
				this.rowNum = rowNum;
			}

			public String getOrderDescId() {
				return orderDescId;
			}


			public String getHeadimg() {
				return headimg;
			}

			public void setHeadimg(String headimg) {
				this.headimg = headimg;
			}

			public String getInsertTime() {
				return insertTime;
			}

			public void setInsertTime(String insertTime) {
				this.insertTime = insertTime;
			}

			public String getOrderNums() {
				return orderNums;
			}

			public void setOrderNums(String orderNums) {
				this.orderNums = orderNums;
			}

			public String getPayMoney() {
				return payMoney;
			}

			public void setPayMoney(String payMoney) {
				this.payMoney = payMoney;
			}

			public String getPayTime() {
				return payTime;
			}

			public void setPayTime(String payTime) {
				this.payTime = payTime;
			}

			public String getShopAddress() {
				return shopAddress;
			}

			public void setShopAddress(String shopAddress) {
				this.shopAddress = shopAddress;
			}

			public String getShopName() {
				return shopName;
			}

			public void setShopName(String shopName) {
				this.shopName = shopName;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}

			public String getOrderNum() {
				return orderNum;
			}

			public void setOrderNum(String orderNum) {
				this.orderNum = orderNum;
			}

			public int getSellerId() {
				return sellerId;
			}

			public void setSellerId(int sellerId) {
				this.sellerId = sellerId;
			}

			public String  getShopId() {
				return shopId;
			}

			public void setShopId(String shopId) {
				this.shopId = shopId;
			}
		}
	}
}
