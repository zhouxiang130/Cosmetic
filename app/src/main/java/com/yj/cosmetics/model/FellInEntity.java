package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2019/5/21 0021.
 */

public class FellInEntity {

	/**
	 * code : 200
	 * data : {"accelerate":1,"arr":[{"insertTime":"2019-05-17 16:16:58","orderDescId":3123,"payMoney":5,"rowNum":1,"userHeadImg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com//upload/userHeadimg/2019/05/18/d1f6d320-27f1-4179-ba7b-b017d075e55c.png","userName":"梅子雨*"},{"insertTime":"2019-05-18 16:54:15","orderDescId":3128,"payMoney":5,"rowNum":2,"userHeadImg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com//upload/userHeadimg/2019/05/20/a6522710-4e94-4172-af5a-74d14fde3574.jpg","userName":"我的小世界"}],"orderDescId":3128,"orderId":3744,"payMoney":5,"poolFunds":"5.74","rowNum":2,"shopAddress":"城关花园","shopHeadImg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com//upload/head/sellersImg/2019/05/17/56d69225-adb0-4474-9f5c-d8ba2080af9c.jpg","shopName":"梅子超市","successMoney":"--0.74"}
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
		 * accelerate : 1
		 * arr : [{"insertTime":"2019-05-17 16:16:58","orderDescId":3123,"payMoney":5,"rowNum":1,"userHeadImg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com//upload/userHeadimg/2019/05/18/d1f6d320-27f1-4179-ba7b-b017d075e55c.png","userName":"梅子雨*"},{"insertTime":"2019-05-18 16:54:15","orderDescId":3128,"payMoney":5,"rowNum":2,"userHeadImg":"https://wjf-imgs.oss-cn-qingdao.aliyuncs.com//upload/userHeadimg/2019/05/20/a6522710-4e94-4172-af5a-74d14fde3574.jpg","userName":"我的小世界"}]
		 * orderDescId : 3128
		 * orderId : 3744
		 * payMoney : 5
		 * poolFunds : 5.74
		 * rowNum : 2
		 * shopAddress : 城关花园
		 * shopHeadImg : https://wjf-imgs.oss-cn-qingdao.aliyuncs.com//upload/head/sellersImg/2019/05/17/56d69225-adb0-4474-9f5c-d8ba2080af9c.jpg
		 * shopName : 梅子超市
		 * successMoney : --0.74
		 */

		private String accelerate;
		private String orderDescId;
		private String orderId;
		private String payMoney;
		private String poolFunds;
		private String rowNum;
		private String shopAddress;
		private String shopHeadImg;
		private String shopName;
		private String successMoney;
		private List<ArrBean> arr;

		public String getAccelerate() {
			return accelerate;
		}

		public void setAccelerate(String accelerate) {
			this.accelerate = accelerate;
		}

		public String getOrderDescId() {
			return orderDescId;
		}

		public void setOrderDescId(String orderDescId) {
			this.orderDescId = orderDescId;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getPayMoney() {
			return payMoney;
		}

		public void setPayMoney(String payMoney) {
			this.payMoney = payMoney;
		}

		public String getPoolFunds() {
			return poolFunds;
		}

		public void setPoolFunds(String poolFunds) {
			this.poolFunds = poolFunds;
		}

		public String getRowNum() {
			return rowNum;
		}

		public void setRowNum(String rowNum) {
			this.rowNum = rowNum;
		}

		public String getShopAddress() {
			return shopAddress;
		}

		public void setShopAddress(String shopAddress) {
			this.shopAddress = shopAddress;
		}

		public String getShopHeadImg() {
			return shopHeadImg;
		}

		public void setShopHeadImg(String shopHeadImg) {
			this.shopHeadImg = shopHeadImg;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public String getSuccessMoney() {
			return successMoney;
		}

		public void setSuccessMoney(String successMoney) {
			this.successMoney = successMoney;
		}

		public List<ArrBean> getArr() {
			return arr;
		}

		public void setArr(List<ArrBean> arr) {
			this.arr = arr;
		}

		public static class ArrBean {
			/**
			 * insertTime : 2019-05-17 16:16:58
			 * orderDescId : 3123
			 * payMoney : 5
			 * rowNum : 1
			 * userHeadImg : https://wjf-imgs.oss-cn-qingdao.aliyuncs.com//upload/userHeadimg/2019/05/18/d1f6d320-27f1-4179-ba7b-b017d075e55c.png
			 * userName : 梅子雨*
			 */

			private String insertTime;
			private String orderDescId;
			private String payMoney;
			private String rowNum;
			private String userHeadImg;
			private String userName;

			public String getInsertTime() {
				return insertTime;
			}

			public void setInsertTime(String insertTime) {
				this.insertTime = insertTime;
			}

			public String getOrderDescId() {
				return orderDescId;
			}

			public void setOrderDescId(String orderDescId) {
				this.orderDescId = orderDescId;
			}

			public String getPayMoney() {
				return payMoney;
			}

			public void setPayMoney(String payMoney) {
				this.payMoney = payMoney;
			}

			public String getRowNum() {
				return rowNum;
			}

			public void setRowNum(String rowNum) {
				this.rowNum = rowNum;
			}

			public String getUserHeadImg() {
				return userHeadImg;
			}

			public void setUserHeadImg(String userHeadImg) {
				this.userHeadImg = userHeadImg;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}
		}
	}
}
