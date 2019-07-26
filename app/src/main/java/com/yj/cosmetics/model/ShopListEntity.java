package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/4 0004.
 */

public class ShopListEntity {
	/**
	 * code : 200
	 * data : {"pageNumber":1,"shopArray":[{"detailNumMonth":0,"juli":"0.08","serviceStartime":1,"shopId":10,"shopImg":"/images/shopImg.png","shopName":"优果分拨中心","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":0,"juli":"3.37","serviceStartime":50,"shopId":4,"shopImg":"/upload/shop/2018/07/27/7afe827b-d335-4a98-bc7e-170988737ae0.png","shopName":"sdf","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":3,"juli":"5.69","serviceStartime":60,"shopId":5,"shopImg":"/upload/shop/2018/07/27/5bf7b04a-0296-49bc-b7b0-1038c58c7c4e.png","shopName":"嘟嘟小店","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":0,"juli":"6.08","serviceStartime":20,"shopId":8,"shopImg":"/upload/shop/2018/08/01/e4e5d78c-e465-4e53-8c6f-a1fe0e1da810.jpg","shopName":"小王水果店","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":4,"juli":"9.86","serviceStartime":20,"shopId":9,"shopImg":"/images/shopImg.png","shopName":"家辉超市","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":0,"juli":"12.92","serviceStartime":30,"shopId":3,"shopImg":"/images/shopImg.png","shopName":"小草麻辣烫店","shopType":1,"shopTypeName":"普通"},{"detailNumMonth":2,"juli":"17.35","serviceStartime":89,"shopId":7,"shopImg":"/upload/shop/2018/07/31/72eb2241-2cb7-4edb-8081-bdb79a8178d8.png","shopName":"普通店铺","shopType":1,"shopTypeName":"普通"},{"detailNumMonth":0,"juli":"17.77","serviceStartime":10,"shopId":6,"shopImg":"/upload/shop/2018/07/28/04ca6321-747e-4e23-a114-e6542d3902a2.jpg","shopName":"小草零食店","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":19,"juli":"1277.2","serviceStartime":20,"shopId":1,"shopImg":"http://img2.ph.126.net/vZ1r-IVk_j-cAi-noOuCEw==/760826862149437991.png","shopName":"小草鸭脖店","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":0,"juli":"1277.2","serviceStartime":20,"shopId":2,"shopImg":"/upload/shop/2018/07/25/2d4cbacf-e87f-4518-8a61-c34167f8896b.jpg","shopName":"小草烧烤店","shopType":2,"shopTypeName":"优果"}]}
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
		 * pageNumber : 1
		 * shopArray : [{"detailNumMonth":0,"juli":"0.08","serviceStartime":1,"shopId":10,"shopImg":"/images/shopImg.png","shopName":"优果分拨中心","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":0,"juli":"3.37","serviceStartime":50,"shopId":4,"shopImg":"/upload/shop/2018/07/27/7afe827b-d335-4a98-bc7e-170988737ae0.png","shopName":"sdf","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":3,"juli":"5.69","serviceStartime":60,"shopId":5,"shopImg":"/upload/shop/2018/07/27/5bf7b04a-0296-49bc-b7b0-1038c58c7c4e.png","shopName":"嘟嘟小店","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":0,"juli":"6.08","serviceStartime":20,"shopId":8,"shopImg":"/upload/shop/2018/08/01/e4e5d78c-e465-4e53-8c6f-a1fe0e1da810.jpg","shopName":"小王水果店","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":4,"juli":"9.86","serviceStartime":20,"shopId":9,"shopImg":"/images/shopImg.png","shopName":"家辉超市","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":0,"juli":"12.92","serviceStartime":30,"shopId":3,"shopImg":"/images/shopImg.png","shopName":"小草麻辣烫店","shopType":1,"shopTypeName":"普通"},{"detailNumMonth":2,"juli":"17.35","serviceStartime":89,"shopId":7,"shopImg":"/upload/shop/2018/07/31/72eb2241-2cb7-4edb-8081-bdb79a8178d8.png","shopName":"普通店铺","shopType":1,"shopTypeName":"普通"},{"detailNumMonth":0,"juli":"17.77","serviceStartime":10,"shopId":6,"shopImg":"/upload/shop/2018/07/28/04ca6321-747e-4e23-a114-e6542d3902a2.jpg","shopName":"小草零食店","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":19,"juli":"1277.2","serviceStartime":20,"shopId":1,"shopImg":"http://img2.ph.126.net/vZ1r-IVk_j-cAi-noOuCEw==/760826862149437991.png","shopName":"小草鸭脖店","shopType":2,"shopTypeName":"优果"},{"detailNumMonth":0,"juli":"1277.2","serviceStartime":20,"shopId":2,"shopImg":"/upload/shop/2018/07/25/2d4cbacf-e87f-4518-8a61-c34167f8896b.jpg","shopName":"小草烧烤店","shopType":2,"shopTypeName":"优果"}]
		 */

		private String city;
		private int pageNumber;
		private List<ShopArrayBean> shopArray;

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}

		public List<ShopArrayBean> getShopArray() {
			return shopArray;
		}

		public void setShopArray(List<ShopArrayBean> shopArray) {
			this.shopArray = shopArray;
		}

		public static class ShopArrayBean {
			/**
			 * detailNumMonth : 0
			 * juli : 0.08
			 * serviceStartime : 1
			 * shopId : 10
			 * shopImg : /images/shopImg.png
			 * shopName : 优果分拨中心
			 * shopType : 2
			 * shopTypeName : 优果
			 */

			private String deliveryDistanceType;
			private String detailNumMonth;
			private String juli;
			private String serviceStartime;
			private String shopId;
			private String receipt;
			private String shopImg;
			private String shopName;
			private int shopType;
			private String shopTypeName;

			private String numMonth;
			private String shopLogo;
			private List<ProductlistBean> productlist;


			public List<ProductlistBean> getProductlist() {
				return productlist;
			}

			public void setProductlist(List<ProductlistBean> productlist) {
				this.productlist = productlist;
			}


			public static class ProductlistBean {
				/**
				 * product_current : 10
				 * product_id : 228
				 * product_listImg : http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/11/95e39a7b-4b42-47c6-b650-a6e9227ccead.jpg
				 */

				private String product_current;
				private String product_id;
				private String product_listImg;

				public String getProduct_current() {
					return product_current;
				}

				public void setProduct_current(String product_current) {
					this.product_current = product_current;
				}

				public String getProduct_id() {
					return product_id;
				}

				public void setProduct_id(String product_id) {
					this.product_id = product_id;
				}

				public String getProduct_listImg() {
					return product_listImg;
				}

				public void setProduct_listImg(String product_listImg) {
					this.product_listImg = product_listImg;
				}
			}


			public String getNumMonth() {
				return numMonth;
			}

			public void setNumMonth(String numMonth) {
				this.numMonth = numMonth;
			}

			public String getShopLogo() {
				return shopLogo;
			}

			public void setShopLogo(String shopLogo) {
				this.shopLogo = shopLogo;
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

			public String getDetailNumMonth() {
				return detailNumMonth;
			}

			public void setDetailNumMonth(String detailNumMonth) {
				this.detailNumMonth = detailNumMonth;
			}

			public String getJuli() {
				return juli;
			}

			public void setJuli(String juli) {
				this.juli = juli;
			}

			public String getServiceStartime() {
				return serviceStartime;
			}

			public void setServiceStartime(String serviceStartime) {
				this.serviceStartime = serviceStartime;
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

			public int getShopType() {
				return shopType;
			}

			public void setShopType(int shopType) {
				this.shopType = shopType;
			}

			public String getShopTypeName() {
				return shopTypeName;
			}

			public void setShopTypeName(String shopTypeName) {
				this.shopTypeName = shopTypeName;
			}
		}
	}
}
