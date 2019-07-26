package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class MineRefundDetailEntity {


	/**
	 * data : {"time":"2018-06-26 10:50:18","item":[{"num":1,"price":8,"productImg":"/upload/head/productImg/2018/05/05/ddac64e3-a910-4622-b15c-21f4618fecae.png","productSk":"规格:默认","productName":"fff"}],"isReturn":1,"handlingTime":"","orderNum":"20180612105655491003","returnNum":"20180626105013972143","serviceTel":"15137162645","orderId":956,"returnReason":"Dadjalkdjakldjalkjdaljdkd","returnMoney":8}
	 * code : 200
	 * msg : 成功
	 */

	private DataBean data;
	private String code;
	private String msg;
	public String HTTP_OK = "200";

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static class DataBean {
		/**
		 * time : 2018-06-26 10:50:18
		 * item : [{"num":1,"price":8,"productImg":"/upload/head/productImg/2018/05/05/ddac64e3-a910-4622-b15c-21f4618fecae.png","productSk":"规格:默认","productName":"fff"}]
		 * isReturn : 1
		 * handlingTime :
		 * orderNum : 20180612105655491003
		 * returnNum : 20180626105013972143
		 * serviceTel : 15137162645
		 * orderId : 956
		 * returnReason : Dadjalkdjakldjalkjdaljdkd
		 * returnMoney : 8
		 */

		private String time;
		private int isReturn;
		private String handlingTime;
		private String orderNum;
		private String returnNum;
		private String serviceTel;
		private int orderId;

		private String shopImg;
		private String shopId;
		private String shopName;

		private String returnReason;
		private String returnMoney;

		private String returnBack;
		private String accountMoney;

		private String backReason;
		private List<ItemBean> item;

		public String getAccountMoney() {
			return accountMoney;
		}

		public void setAccountMoney(String accountMoney) {
			this.accountMoney = accountMoney;
		}

		public String getReturnBack() {

			return returnBack;
		}

		public void setReturnBack(String returnBack) {
			this.returnBack = returnBack;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getShopImg() {
			return shopImg;
		}

		public void setShopImg(String shopImg) {
			this.shopImg = shopImg;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public String getBackReason() {
			return backReason;
		}

		public void setBackReason(String backReason) {
			this.backReason = backReason;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public int getIsReturn() {
			return isReturn;
		}

		public void setIsReturn(int isReturn) {
			this.isReturn = isReturn;
		}

		public String getHandlingTime() {
			return handlingTime;
		}

		public void setHandlingTime(String handlingTime) {
			this.handlingTime = handlingTime;
		}

		public String getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(String orderNum) {
			this.orderNum = orderNum;
		}

		public String getReturnNum() {
			return returnNum;
		}

		public void setReturnNum(String returnNum) {
			this.returnNum = returnNum;
		}

		public String getServiceTel() {
			return serviceTel;
		}

		public void setServiceTel(String serviceTel) {
			this.serviceTel = serviceTel;
		}

		public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}

		public String getReturnReason() {
			return returnReason;
		}

		public void setReturnReason(String returnReason) {
			this.returnReason = returnReason;
		}

		public String getReturnMoney() {
			return returnMoney;
		}

		public void setReturnMoney(String returnMoney) {
			this.returnMoney = returnMoney;
		}

		public List<ItemBean> getItem() {
			return item;
		}

		public void setItem(List<ItemBean> item) {
			this.item = item;
		}

		public static class ItemBean {
			/**
			 * num : 1
			 * price : 8
			 * productImg : /upload/head/productImg/2018/05/05/ddac64e3-a910-4622-b15c-21f4618fecae.png
			 * productSk : 规格:默认
			 * productName : fff
			 */

			private int num;
			private String price;
			private String productImg;
			private String productSk;
			private String productName;

			public int getNum() {
				return num;
			}

			public void setNum(int num) {
				this.num = num;
			}

			public String getPrice() {
				return price;
			}

			public void setPrice(String price) {
				this.price = price;
			}

			public String getProductImg() {
				return productImg;
			}

			public void setProductImg(String productImg) {
				this.productImg = productImg;
			}

			public String getProductSk() {
				return productSk;
			}

			public void setProductSk(String productSk) {
				this.productSk = productSk;
			}

			public String getProductName() {
				return productName;
			}

			public void setProductName(String productName) {
				this.productName = productName;
			}
		}
	}
}
