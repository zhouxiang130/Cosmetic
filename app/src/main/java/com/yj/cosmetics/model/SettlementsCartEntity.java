package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/26.
 */

public class SettlementsCartEntity {

	/**
	 * code : 200
	 * data : {"addressDefault":1,"addressId":129,"areaDetail":"河南省 郑州市 中原区  火车站","cartIds":"499,507","code":200,"coupons":[{"couponDiscount":"","couponName":"满100.0可用，当前可抵扣50.0元","couponRequire":100,"couponType":2,"faceValue":50,"userCouponId":1110}],"orderTotalMoney":"100.00","postage":"10.0","proArray":[{"shopId":5,"shopImg":"/upload/shop/2018/07/27/5bf7b04a-0296-49bc-b7b0-1038c58c7c4e.png","shopName":"迪纳普","shopProArray":[{"cartId":499,"num":1,"proImg":"/upload/head/productImg/2018/07/27/f171b309-d2df-43fc-bafb-6f25b3d2566a.jpg","proName":"浪琴(Longines)瑞士手表","productId":94,"skuPrice":55,"skuPropertiesName":"规格:银盘数字 间金;"}]},{"shopImg":"/images/shopImg.png","shopName":"自营","shopProArray":[{"cartId":507,"num":1,"proImg":"/upload/product/2018/06/29/d4af967d-0046-42ca-837e-8748b88aec9e.png","proName":"辣小生","skuPrice":35,"skuPropertiesName":"成色:1公斤;"}]}],"productTotal":"90.00","receiverName":"小草","receiverTel":"15136166750","systemValue":"500","userMoney":"9684.00"}
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
		 * addressDefault : 1
		 * addressId : 129
		 * areaDetail : 河南省 郑州市 中原区  火车站
		 * cartIds : 499,507
		 * code : 200
		 * coupons : [{"couponDiscount":"","couponName":"满100.0可用，当前可抵扣50.0元","couponRequire":100,"couponType":2,"faceValue":50,"userCouponId":1110}]
		 * orderTotalMoney : 100.00
		 * postage : 10.0
		 * proArray : [{"shopId":5,"shopImg":"/upload/shop/2018/07/27/5bf7b04a-0296-49bc-b7b0-1038c58c7c4e.png","shopName":"迪纳普","shopProArray":[{"cartId":499,"num":1,"proImg":"/upload/head/productImg/2018/07/27/f171b309-d2df-43fc-bafb-6f25b3d2566a.jpg","proName":"浪琴(Longines)瑞士手表","productId":94,"skuPrice":55,"skuPropertiesName":"规格:银盘数字 间金;"}]},{"shopImg":"/images/shopImg.png","shopName":"自营","shopProArray":[{"cartId":507,"num":1,"proImg":"/upload/product/2018/06/29/d4af967d-0046-42ca-837e-8748b88aec9e.png","proName":"辣小生","skuPrice":35,"skuPropertiesName":"成色:1公斤;"}]}]
		 * productTotal : 90.00
		 * receiverName : 小草
		 * receiverTel : 15136166750
		 * systemValue : 500
		 * userMoney : 9684.00
		 */

		private String addressDefault;
		private String addressId;
		private String areaDetail;
		private String cartIds;
		private int code;
		private String orderTotalMoney;
		private String postage;
		private String productTotal;
		private String receiverName;
		private String receiverTel;
		private String systemValue;
		private String userMoney;
		private List<CouponsBean> coupons;
		private List<ProArrayBean> proArray;

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

		public String getCartIds() {
			return cartIds;
		}

		public void setCartIds(String cartIds) {
			this.cartIds = cartIds;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
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

		public List<CouponsBean> getCoupons() {
			return coupons;
		}

		public void setCoupons(List<CouponsBean> coupons) {
			this.coupons = coupons;
		}

		public List<ProArrayBean> getProArray() {
			return proArray;
		}

		public void setProArray(List<ProArrayBean> proArray) {
			this.proArray = proArray;
		}

		public static class CouponsBean {
			/**
			 * couponDiscount :
			 * couponName : 满100.0可用，当前可抵扣50.0元
			 * couponRequire : 100
			 * couponType : 2
			 * faceValue : 50
			 * userCouponId : 1110
			 */

			private String couponDiscount;
			private String couponName;
			private String couponRequire;
			private int couponType;
			private String faceValue;
			private int userCouponId;
			private String faceValueZk;

			public String getFaceValueZk() {
				return faceValueZk;
			}

			public void setFaceValueZk(String faceValueZk) {
				this.faceValueZk = faceValueZk;
			}

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
		}

		public static class ProArrayBean {
			/**
			 * shopId : 5
			 * shopImg : /upload/shop/2018/07/27/5bf7b04a-0296-49bc-b7b0-1038c58c7c4e.png
			 * shopName : 迪纳普
			 * shopProArray : [{"cartId":499,"num":1,"proImg":"/upload/head/productImg/2018/07/27/f171b309-d2df-43fc-bafb-6f25b3d2566a.jpg","proName":"浪琴(Longines)瑞士手表","productId":94,"skuPrice":55,"skuPropertiesName":"规格:银盘数字 间金;"}]
			 */

			private int shopId;
			private String shopImg;
			private String shopName;
			private List<ShopProArrayBean> shopProArray;

			public int getShopId() {
				return shopId;
			}

			public void setShopId(int shopId) {
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

			public List<ShopProArrayBean> getShopProArray() {
				return shopProArray;
			}

			public void setShopProArray(List<ShopProArrayBean> shopProArray) {
				this.shopProArray = shopProArray;
			}

			public static class ShopProArrayBean {
				/**
				 * cartId : 499
				 * num : 1
				 * proImg : /upload/head/productImg/2018/07/27/f171b309-d2df-43fc-bafb-6f25b3d2566a.jpg
				 * proName : 浪琴(Longines)瑞士手表
				 * productId : 94
				 * skuPrice : 55
				 * skuPropertiesName : 规格:银盘数字 间金;
				 */

				private int cartId;
				private int num;
				private String proImg;
				private String proName;
				private String productId;
				private String skuPrice;
				private String skuPropertiesName;

				public int getCartId() {
					return cartId;
				}

				public void setCartId(int cartId) {
					this.cartId = cartId;
				}

				public int getNum() {
					return num;
				}

				public void setNum(int num) {
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

				public String getProductId() {
					return productId;
				}

				public void setProductId(String productId) {
					this.productId = productId;
				}

				public String  getSkuPrice() {
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
}
