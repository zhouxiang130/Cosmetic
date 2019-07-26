package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/20 0020.
 */

public class CartsEntity {

	/**
	 * msg : 成功
	 * code : 200
	 * data : {"proCarts":[{"shopProArray":[{"proId":52,"num":1,"cartId":389,"productAbstract":"简介","skuPrice":234,"proName":"sfga","skuPropertiesName":"默认","productState":1,"skuNum":976,"proImg":"/upload/head/productImg/2018/06/29/b57c7335-c8f0-42a5-9f6a-2bcfd92262e6.png"},{"proId":72,"num":1,"cartId":390,"productAbstract":"产自于江苏，舌尖上的美味","skuPrice":278,"proName":"阳澄湖大闸蟹","skuPropertiesName":"种类:(公4.0两 母3.0两)8只;","productState":1,"skuNum":81,"proImg":"/upload/product/2018/06/29/3775d260-be20-4226-a5a7-6b338db4d350.jpg"}],"shopName":"自营","shopImg":"/images/shopImg.png"}],"systemValue":"500"}
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
		 * proCarts : [{"shopProArray":[{"proId":52,"num":1,"cartId":389,"productAbstract":"简介","skuPrice":234,"proName":"sfga","skuPropertiesName":"默认","productState":1,"skuNum":976,"proImg":"/upload/head/productImg/2018/06/29/b57c7335-c8f0-42a5-9f6a-2bcfd92262e6.png"},{"proId":72,"num":1,"cartId":390,"productAbstract":"产自于江苏，舌尖上的美味","skuPrice":278,"proName":"阳澄湖大闸蟹","skuPropertiesName":"种类:(公4.0两 母3.0两)8只;","productState":1,"skuNum":81,"proImg":"/upload/product/2018/06/29/3775d260-be20-4226-a5a7-6b338db4d350.jpg"}],"shopName":"自营","shopImg":"/images/shopImg.png"}]
		 * systemValue : 500
		 */

		private String systemValue;
		private List<ProCartsBean> proCarts;

		public String getSystemValue() {
			return systemValue;
		}

		public void setSystemValue(String systemValue) {
			this.systemValue = systemValue;
		}

		public List<ProCartsBean> getProCarts() {
			return proCarts;
		}

		public void setProCarts(List<ProCartsBean> proCarts) {
			this.proCarts = proCarts;
		}

		public static class ProCartsBean {
			/**
			 * shopProArray : [{"proId":52,"num":1,"cartId":389,"productAbstract":"简介","skuPrice":234,"proName":"sfga","skuPropertiesName":"默认","productState":1,"skuNum":976,"proImg":"/upload/head/productImg/2018/06/29/b57c7335-c8f0-42a5-9f6a-2bcfd92262e6.png"},{"proId":72,"num":1,"cartId":390,"productAbstract":"产自于江苏，舌尖上的美味","skuPrice":278,"proName":"阳澄湖大闸蟹","skuPropertiesName":"种类:(公4.0两 母3.0两)8只;","productState":1,"skuNum":81,"proImg":"/upload/product/2018/06/29/3775d260-be20-4226-a5a7-6b338db4d350.jpg"}]
			 * shopName : 自营
			 * shopImg : /images/shopImg.png
			 */

			private String shopName;
//			private String receipt;
			private String shopId;
			private String shopImg;
			private int type;
			private boolean isChoosed;

//			public String getReceipt() {
//				return receipt;
//			}

//			public void setReceipt(String receipt) {
//				this.receipt = receipt;
//			}

			public String getShopId() {
				return shopId;
			}

			public void setShopId(String shopId) {
				this.shopId = shopId;
			}

			public boolean isChoosed() {
				return isChoosed;
			}

			public void setChoosed(boolean choosed) {
				isChoosed = choosed;
			}

			public int getType() {
				return type;
			}

			public void setType(int type) {
				this.type = type;
			}

			private List<ShopProArrayBean> shopProArray;

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

			public List<ShopProArrayBean> getShopProArray() {
				return shopProArray;
			}

			public void setShopProArray(List<ShopProArrayBean> shopProArray) {
				this.shopProArray = shopProArray;
			}

			public static class ShopProArrayBean {
				/**
				 * proId : 52
				 * num : 1
				 * cartId : 389
				 * productAbstract : 简介
				 * skuPrice : 234
				 * proName : sfga
				 * skuPropertiesName : 默认
				 * productState : 1
				 * skuNum : 976
				 * proImg : /upload/head/productImg/2018/06/29/b57c7335-c8f0-42a5-9f6a-2bcfd92262e6.png
				 */

				private String proId;
				private int type;
				private String num;
				private int cartId;
				private String productAbstract;
				private String skuPrice;
				private String proName;
				private String skuPropertiesName;
				private int productState;
				private int hide;
				private String skuNum;
				private String proImg;
				private String sproductId;
				private boolean isChoosed;


				public int getHide() {
					return hide;
				}

				public void setHide(int hide) {
					this.hide = hide;
				}

				public String getSproductId() {
					return sproductId;
				}

				public void setSproductId(String sproductId) {
					this.sproductId = sproductId;
				}

				public int getType() {
					return type;
				}

				public void setType(int type) {
					this.type = type;
				}
				public boolean isChoosed() {
					return isChoosed;
				}

				public void setChoosed(boolean choosed) {
					isChoosed = choosed;
				}

				public String getProId() {
					return proId;
				}

				public void setProId(String proId) {
					this.proId = proId;
				}

				public String getNum() {
					return num;
				}

				public void setNum(String num) {
					this.num = num;
				}

				public int getCartId() {
					return cartId;
				}

				public void setCartId(int cartId) {
					this.cartId = cartId;
				}

				public String getProductAbstract() {
					return productAbstract;
				}

				public void setProductAbstract(String productAbstract) {
					this.productAbstract = productAbstract;
				}

				public String getSkuPrice() {
					return skuPrice;
				}

				public void setSkuPrice(String skuPrice) {
					this.skuPrice = skuPrice;
				}

				public String getProName() {
					return proName;
				}

				public void setProName(String proName) {
					this.proName = proName;
				}

				public String getSkuPropertiesName() {
					return skuPropertiesName;
				}

				public void setSkuPropertiesName(String skuPropertiesName) {
					this.skuPropertiesName = skuPropertiesName;
				}

				public int getProductState() {
					return productState;
				}

				public void setProductState(int productState) {
					this.productState = productState;
				}

				public String getSkuNum() {
					return skuNum;
				}

				public void setSkuNum(String skuNum) {
					this.skuNum = skuNum;
				}

				public String getProImg() {
					return proImg;
				}

				public void setProImg(String proImg) {
					this.proImg = proImg;
				}
			}
		}
	}
}
