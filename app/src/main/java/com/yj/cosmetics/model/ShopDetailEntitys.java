package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30 0030.
 */

public class ShopDetailEntitys {


	/**
	 * code : 200
	 * data : {"data":{"agreement":"/page/agreement.html","areaAddress":"河南,郑州,二七区,河南省郑州市金水区","shopCollectionType":"no","shopId":17,"shopLogo":"/upload/shop/2018/08/14/9f8bd534-628c-498a-adb4-9854b4d47da0.jpg","shopName":"家辉超市","shopNotice":"店铺是新开的","shopTel":["15963"]},"orderId":""}
	 * msg : 成功
	 */

	private String code;
	private DataBeanX data;
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public DataBeanX getData() {
		return data;
	}

	public void setData(DataBeanX data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static class DataBeanX {
		/**
		 * data : {"agreement":"/page/agreement.html","areaAddress":"河南,郑州,二七区,河南省郑州市金水区","shopCollectionType":"no","shopId":17,"shopLogo":"/upload/shop/2018/08/14/9f8bd534-628c-498a-adb4-9854b4d47da0.jpg","shopName":"家辉超市","shopNotice":"店铺是新开的","shopTel":["15963"]}
		 * orderId :
		 */

		private DataBean data;
		private String orderId;

		public DataBean getData() {
			return data;
		}

		public void setData(DataBean data) {
			this.data = data;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public static class DataBean {
			/**
			 * agreement : /page/agreement.html
			 * areaAddress : 河南,郑州,二七区,河南省郑州市金水区
			 * shopCollectionType : no
			 * shopId : 17
			 * shopLogo : /upload/shop/2018/08/14/9f8bd534-628c-498a-adb4-9854b4d47da0.jpg
			 * shopName : 家辉超市
			 * shopNotice : 店铺是新开的
			 * shopTel : ["15963"]
			 */

			private String agreement;
			private String areaAddress;
			private String shopCollectionType;
			private int shopId;
			private String shopLogo;
			private String shopName;
			private String shopNotice;
			private List<String> shopTel;

			public String getAgreement() {
				return agreement;
			}

			public void setAgreement(String agreement) {
				this.agreement = agreement;
			}

			public String getAreaAddress() {
				return areaAddress;
			}

			public void setAreaAddress(String areaAddress) {
				this.areaAddress = areaAddress;
			}

			public String getShopCollectionType() {
				return shopCollectionType;
			}

			public void setShopCollectionType(String shopCollectionType) {
				this.shopCollectionType = shopCollectionType;
			}

			public int getShopId() {
				return shopId;
			}

			public void setShopId(int shopId) {
				this.shopId = shopId;
			}

			public String getShopLogo() {
				return shopLogo;
			}

			public void setShopLogo(String shopLogo) {
				this.shopLogo = shopLogo;
			}

			public String getShopName() {
				return shopName;
			}

			public void setShopName(String shopName) {
				this.shopName = shopName;
			}

			public String getShopNotice() {
				return shopNotice;
			}

			public void setShopNotice(String shopNotice) {
				this.shopNotice = shopNotice;
			}

			public List<String> getShopTel() {
				return shopTel;
			}

			public void setShopTel(List<String> shopTel) {
				this.shopTel = shopTel;
			}
		}
	}
}
