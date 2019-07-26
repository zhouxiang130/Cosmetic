package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/26.
 */

public class SettlementCartEntity {
	private String code;
	private String msg;
	private SettlementCartData data;
	public final String HTTP_OK = "200";


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public SettlementCartData getData() {
		return data;
	}

	public void setData(SettlementCartData data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public class SettlementCartData {
		private String addressDefault;
		private String receiverTel;
		private String productTotal;
		private String orderTotalMoney;
		private String areaDetail;
		private String receiverName;
		private String addressId;
		private String userMoney;
		private String postage;
		private String systemValue;
		private List<SettlementCartItem> proArray;
		private List<CouponsBean> coupons;


		public class CouponsBean {
			/**
			 * couponName : 满100.0可用，当前可抵扣10.0元
			 * couponType : 2
			 * couponRequire : 100
			 * faceValue : 10
			 * couponDiscount :
			 * userCouponId : 520
			 * faceValueZk : 100
			 */

			private String couponName;
			private int couponType;
			private String couponRequire;
			private String faceValue;
			private String couponDiscount;
			private int userCouponId;
			private String faceValueZk;

			public String getCouponName() {
				return couponName;
			}

			public void setCouponName(String couponName) {
				this.couponName = couponName;
			}

			public int getCouponType() {
				return couponType;
			}

			public void setCouponType(int couponType) {
				this.couponType = couponType;
			}

			public String getCouponRequire() {
				return couponRequire;
			}

			public void setCouponRequire(String couponRequire) {
				this.couponRequire = couponRequire;
			}

			public String getFaceValue() {
				return faceValue;
			}

			public void setFaceValue(String faceValue) {
				this.faceValue = faceValue;
			}

			public String getCouponDiscount() {
				return couponDiscount;
			}

			public void setCouponDiscount(String couponDiscount) {
				this.couponDiscount = couponDiscount;
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

		public String getOrderTotalMoney() {
			return orderTotalMoney;
		}

		public void setOrderTotalMoney(String orderTotalMoney) {
			this.orderTotalMoney = orderTotalMoney;
		}

		public String getPostage() {
			return postage;
		}

		public void setPostage(String postage) {
			this.postage = postage;
		}

		public List<SettlementCartItem> getProArray() {
			return proArray;
		}

		public void setProArray(List<SettlementCartItem> proArray) {
			this.proArray = proArray;
		}

		public String getProductTotal() {
			return productTotal;
		}

		public void setProductTotal(String productTotal) {
			this.productTotal = productTotal;
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

		public String getSystemValue() {
			return systemValue;
		}

		public void setSystemValue(String systemValue) {
			this.systemValue = systemValue;
		}

		public String getUserMoney() {
			return userMoney;
		}

		public void setUserMoney(String userMoney) {
			this.userMoney = userMoney;
		}

		public class SettlementCartItem {
			private String skuPropertiesName;
			private String num;
			private String skuPrice;
			private String proName;
			private String cartId;
			private String proImg;

			public String getCartId() {
				return cartId;
			}

			public void setCartId(String cartId) {
				this.cartId = cartId;
			}

			public String getNum() {
				return num;
			}

			public void setNum(String num) {
				this.num = num;
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
		}
	}
}
