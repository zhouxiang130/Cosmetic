package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30 0030.
 */

public class ShopDetailEntity {


	/**
	 * msg : 成功
	 * code : 200
	 * data : {"shopTel":["15136166750","110"],"shopService":"上门服务","serviceStartime":"20.0","shopName":"小草鸭脖店","shopImg":"http://img2.imgtn.bdimg.com/it/u=1885130422,1818211389&fm=214&gp=0.jpg","areaAddress":"河南,郑州,金水区,绿地之窗云峰A座","shopId":1,"shopNotice":"一股莫名的力量","serviceTime":"2018-07-25 11:35:13"}
	 */

	private String msg;
	private String code;
	private DataBean data;
	public final String HTTP_OK ="200";

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

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

	public static class DataBean {
		/**
		 * shopTel : ["15136166750","110"]
		 * shopService : 上门服务
		 * serviceStartime : 20.0
		 * shopName : 小草鸭脖店
		 * shopImg : http://img2.imgtn.bdimg.com/it/u=1885130422,1818211389&fm=214&gp=0.jpg
		 * areaAddress : 河南,郑州,金水区,绿地之窗云峰A座
		 * shopId : 1
		 * shopNotice : 一股莫名的力量
		 * serviceTime : 2018-07-25 11:35:13
		 */
		private String agreement;
		private String shopService;
		private String serviceStartime;
		private String shopName;
		private String shopImg;
		private String orderId;
		private String receipt;
		private String areaAddress;
		private String deliveryDistanceType;
		private String shopProCount;


		private int shopId;
		private String shopNotice;
		private String serviceTime;
		private String shopCollectionType;
		private List<String> shopTel;

		private String shopLogo;

		public String getShopLogo() {
			return shopLogo;
		}

		public void setShopLogo(String shopLogo) {
			this.shopLogo = shopLogo;
		}
		public String getAgreement() {
			return agreement;
		}

		public void setAgreement(String agreement) {
			this.agreement = agreement;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getShopProCount() {
			return shopProCount;
		}

		public void setShopProCount(String shopProCount) {
			this.shopProCount = shopProCount;
		}

		public String getDeliveryDistanceType() {
			return deliveryDistanceType;
		}

		public void setDeliveryDistanceType(String deliveryDistanceType) {
			this.deliveryDistanceType = deliveryDistanceType;
		}

		public String getReceipt() {
			return receipt;
		}

		public void setReceipt(String receipt) {
			this.receipt = receipt;
		}

		public String getShopCollectionType() {
			return shopCollectionType;
		}

		public void setShopCollectionType(String shopCollectionType) {
			this.shopCollectionType = shopCollectionType;
		}

		public String getShopService() {
			return shopService;
		}

		public void setShopService(String shopService) {
			this.shopService = shopService;
		}

		public String getServiceStartime() {
			return serviceStartime;
		}

		public void setServiceStartime(String serviceStartime) {
			this.serviceStartime = serviceStartime;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public String getShopImg() {
			return shopImg;
		}

		public void setShopImg(String shopImg) {
			this.shopImg = shopImg;
		}

		public String getAreaAddress() {
			return areaAddress;
		}

		public void setAreaAddress(String areaAddress) {
			this.areaAddress = areaAddress;
		}

		public int getShopId() {
			return shopId;
		}

		public void setShopId(int shopId) {
			this.shopId = shopId;
		}

		public String getShopNotice() {
			return shopNotice;
		}

		public void setShopNotice(String shopNotice) {
			this.shopNotice = shopNotice;
		}

		public String getServiceTime() {
			return serviceTime;
		}

		public void setServiceTime(String serviceTime) {
			this.serviceTime = serviceTime;
		}

		public List<String> getShopTel() {
			return shopTel;
		}

		public void setShopTel(List<String> shopTel) {
			this.shopTel = shopTel;
		}
	}
}
