package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/22.
 */

public class SettlementGoodsEntity {
	private String code;
	private String msg;
	private SettlementGoodsData data;
	public final String HTTP_OK = "200";


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public SettlementGoodsData getData() {
		return data;
	}

	public void setData(SettlementGoodsData data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public class SettlementGoodsData {
		private String num;
		private String skuPropertiesName;
		private String skuPrice;
		private String proName;
		private String orderTotalMoney;
		private String productTotal;
		private String proImg;
		private String userMoney;
		private String shopImg;
		private String shopName;

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

		private String addressDefault;
		private String receiverTel;
		private String areaDetail;
		private String receiverName;
		private String addressId;
		private String systemValue;
		private String postage;
		private List<CouponsBean> coupons;


		public class CouponsBean {
			/**
			 * couponDiscount :
			 * couponName : 满100.0可用，当前可抵扣10.0元
			 * couponRequire : 100
			 * couponType : 2
			 * faceValue : 10
			 * userCouponId : 520
			 * faceValueZk : 100
			 */

			private String couponDiscount;
			private String couponName;
			private String couponRequire;
			private int couponType;
			private String faceValue;
			private int userCouponId;
			private String faceValueZk;

			public String getCouponDiscount() {
				return couponDiscount;
			}

			public void setCouponDiscount(String couponDiscount) {
				this.couponDiscount = couponDiscount;
			}

			public String getCouponName() {
				return couponName;
			}

			public void setCouponName(String couponName) {
				this.couponName = couponName;
			}

			public String getCouponRequire() {
				return couponRequire;
			}

			public void setCouponRequire(String couponRequire) {
				this.couponRequire = couponRequire;
			}

			public int getCouponType() {
				return couponType;
			}

			public void setCouponType(int couponType) {
				this.couponType = couponType;
			}

			public String getFaceValue() {
				return faceValue;
			}

			public void setFaceValue(String faceValue) {
				this.faceValue = faceValue;
			}

			public int getUserCouponId() {
				return userCouponId;
			}

			public void setUserCouponId(int userCouponId) {
				this.userCouponId = userCouponId;
			}

			public String getFaceValueZk() {
				return faceValueZk;
			}

			public void setFaceValueZk(String faceValueZk) {
				this.faceValueZk = faceValueZk;
			}
		}

		public List<CouponsBean> getCoupons() {
			return coupons;
		}

		public void setCoupons(List<CouponsBean> coupons) {
			this.coupons = coupons;
		}

		public String getPostage() {
			return postage;
		}

		public void setPostage(String postage) {
			this.postage = postage;
		}

		public String getSystemValue() {
			return systemValue;
		}

		public void setSystemValue(String systemValue) {
			this.systemValue = systemValue;
		}

		public String getAddressDefault() {
			return addressDefault;
		}

		public void setAddressDefault(String addressDefault) {
			this.addressDefault = addressDefault;
		}

		public String getAddressId() {
			return addressId;
		}

		public void setAddressId(String addressId) {
			this.addressId = addressId;
		}

		public String getAreaDetail() {
			return areaDetail;
		}

		public void setAreaDetail(String areaDetail) {
			this.areaDetail = areaDetail;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getOrderTotalMoney() {
			return orderTotalMoney;
		}

		public void setOrderTotalMoney(String orderTotalMoney) {
			this.orderTotalMoney = orderTotalMoney;
		}

		public String getProductTotal() {
			return productTotal;
		}

		public void setProductTotal(String productTotal) {
			this.productTotal = productTotal;
		}

		public String getProImg() {
			return proImg;
		}

		public void setProImg(String proImg) {
			this.proImg = proImg;
		}

		public String getProName() {
			return proName;
		}

		public void setProName(String proName) {
			this.proName = proName;
		}

		public String getReceiverName() {
			return receiverName;
		}

		public void setReceiverName(String receiverName) {
			this.receiverName = receiverName;
		}

		public String getReceiverTel() {
			return receiverTel;
		}

		public void setReceiverTel(String receiverTel) {
			this.receiverTel = receiverTel;
		}

		public String getSkuPrice() {
			return skuPrice;
		}

		public void setSkuPrice(String skuPrice) {
			this.skuPrice = skuPrice;
		}

		public String getSkuPropertiesName() {
			return skuPropertiesName;
		}

		public void setSkuPropertiesName(String skuPropertiesName) {
			this.skuPropertiesName = skuPropertiesName;
		}

		public String getUserMoney() {
			return userMoney;
		}

		public void setUserMoney(String userMoney) {
			this.userMoney = userMoney;
		}
	}

}