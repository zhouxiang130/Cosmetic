package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/4 0004.
 */

public class MineCollectEntity {

	/**
	 * msg : 成功
	 * code : 200
	 * data : {"pageNumber":1,"list":[{"serviceStartime":20,"detailNumMonth":19,"shopName":"小草鸭脖店","shopImg":"http://img2.ph.126.net/vZ1r-IVk_j-cAi-noOuCEw==/760826862149437991.png","shopId":1,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":89,"detailNumMonth":2,"shopName":"普通店铺","shopImg":"/upload/shop/2018/07/31/72eb2241-2cb7-4edb-8081-bdb79a8178d8.png","shopId":7,"shopType":1,"shopTypeName":"普通"},{"serviceStartime":10,"detailNumMonth":0,"shopName":"小草零食店","shopImg":"/upload/shop/2018/07/28/04ca6321-747e-4e23-a114-e6542d3902a2.jpg","shopId":6,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":20,"detailNumMonth":4,"shopName":"家辉超市","shopImg":"/images/shopImg.png","shopId":9,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":20,"detailNumMonth":0,"shopName":"小王水果店","shopImg":"/upload/shop/2018/08/01/e4e5d78c-e465-4e53-8c6f-a1fe0e1da810.jpg","shopId":8,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":60,"detailNumMonth":3,"shopName":"嘟嘟小店","shopImg":"/upload/shop/2018/07/27/5bf7b04a-0296-49bc-b7b0-1038c58c7c4e.png","shopId":5,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":50,"detailNumMonth":0,"shopName":"sdf","shopImg":"/upload/shop/2018/07/27/7afe827b-d335-4a98-bc7e-170988737ae0.png","shopId":4,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":1,"detailNumMonth":0,"shopName":"优果分拨中心","shopImg":"/images/shopImg.png","shopId":10,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":30,"detailNumMonth":0,"shopName":"小草麻辣烫店","shopImg":"/images/shopImg.png","shopId":3,"shopType":1,"shopTypeName":"普通"},{"serviceStartime":20,"detailNumMonth":0,"shopName":"小草烧烤店","shopImg":"/upload/shop/2018/07/25/2d4cbacf-e87f-4518-8a61-c34167f8896b.jpg","shopId":2,"shopType":2,"shopTypeName":"优果"}],"type":1}
	 */

	private String msg;
	private String code;
	private DataBean data;
	public final String HTTP_OK = "200";

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
		 * pageNumber : 1
		 * list : [{"serviceStartime":20,"detailNumMonth":19,"shopName":"小草鸭脖店","shopImg":"http://img2.ph.126.net/vZ1r-IVk_j-cAi-noOuCEw==/760826862149437991.png","shopId":1,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":89,"detailNumMonth":2,"shopName":"普通店铺","shopImg":"/upload/shop/2018/07/31/72eb2241-2cb7-4edb-8081-bdb79a8178d8.png","shopId":7,"shopType":1,"shopTypeName":"普通"},{"serviceStartime":10,"detailNumMonth":0,"shopName":"小草零食店","shopImg":"/upload/shop/2018/07/28/04ca6321-747e-4e23-a114-e6542d3902a2.jpg","shopId":6,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":20,"detailNumMonth":4,"shopName":"家辉超市","shopImg":"/images/shopImg.png","shopId":9,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":20,"detailNumMonth":0,"shopName":"小王水果店","shopImg":"/upload/shop/2018/08/01/e4e5d78c-e465-4e53-8c6f-a1fe0e1da810.jpg","shopId":8,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":60,"detailNumMonth":3,"shopName":"嘟嘟小店","shopImg":"/upload/shop/2018/07/27/5bf7b04a-0296-49bc-b7b0-1038c58c7c4e.png","shopId":5,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":50,"detailNumMonth":0,"shopName":"sdf","shopImg":"/upload/shop/2018/07/27/7afe827b-d335-4a98-bc7e-170988737ae0.png","shopId":4,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":1,"detailNumMonth":0,"shopName":"优果分拨中心","shopImg":"/images/shopImg.png","shopId":10,"shopType":2,"shopTypeName":"优果"},{"serviceStartime":30,"detailNumMonth":0,"shopName":"小草麻辣烫店","shopImg":"/images/shopImg.png","shopId":3,"shopType":1,"shopTypeName":"普通"},{"serviceStartime":20,"detailNumMonth":0,"shopName":"小草烧烤店","shopImg":"/upload/shop/2018/07/25/2d4cbacf-e87f-4518-8a61-c34167f8896b.jpg","shopId":2,"shopType":2,"shopTypeName":"优果"}]
		 * type : 1
		 */

		private int pageNumber;
		private int type;
		private List<ListBean> list;

		public int getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public List<ListBean> getList() {
			return list;
		}

		public void setList(List<ListBean> list) {
			this.list = list;
		}

		public static class ListBean {
			/**
			 * serviceStartime : 20
			 * detailNumMonth : 19
			 * shopName : 小草鸭脖店
			 * shopImg : http://img2.ph.126.net/vZ1r-IVk_j-cAi-noOuCEw==/760826862149437991.png
			 * shopId : 1
			 * shopType : 2
			 * shopTypeName : 优果
			 */

			private String serviceStartime;
			private String detailNumMonth;
			private String shopName;
			private String shopImg;
			private String shopId;
			private int shopType;
			private String shopTypeName;
			public List<ProductlistBean> productlist;

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

			/**
			 * commentNum : 5
			 * productHot : 1
			 * productTimelimit : 1
			 * productId : 61
			 * productListimg : /upload/head/productImg/2018/06/22/cd532c5e-c889-4bea-9a7b-6124edb6c2ec.png
			 * productOrginal : 99
			 * productCurrent : 88
			 * collectionId : 134
			 * productName : 积分
			 */

			private String commentNum;
			private String productHot;
			private String productTimelimit;
			private String productId;
			private String productListimg;
			private String productOrginal;
			private String productCurrent;
			private String collectionId;
			private String productName;
			private String sproductId;

			public String getSproductId() {
				return sproductId;
			}

			public void setSproductId(String sproductId) {
				this.sproductId = sproductId;
			}

			public String getCommentNum() {
				return commentNum;
			}

			public void setCommentNum(String commentNum) {
				this.commentNum = commentNum;
			}

			public String getProductHot() {
				return productHot;
			}

			public void setProductHot(String productHot) {
				this.productHot = productHot;
			}

			public String getProductTimelimit() {
				return productTimelimit;
			}

			public void setProductTimelimit(String productTimelimit) {
				this.productTimelimit = productTimelimit;
			}

			public String getProductId() {
				return productId;
			}

			public void setProductId(String productId) {
				this.productId = productId;
			}

			public String getProductListimg() {
				return productListimg;
			}

			public void setProductListimg(String productListimg) {
				this.productListimg = productListimg;
			}

			public String getProductOrginal() {
				return productOrginal;
			}

			public void setProductOrginal(String productOrginal) {
				this.productOrginal = productOrginal;
			}

			public String getProductCurrent() {
				return productCurrent;
			}

			public void setProductCurrent(String productCurrent) {
				this.productCurrent = productCurrent;
			}

			public String getCollectionId() {
				return collectionId;
			}

			public void setCollectionId(String collectionId) {
				this.collectionId = collectionId;
			}

			public String getProductName() {
				return productName;
			}

			public void setProductName(String productName) {
				this.productName = productName;
			}


			public String getServiceStartime() {
				return serviceStartime;
			}

			public void setServiceStartime(String serviceStartime) {
				this.serviceStartime = serviceStartime;
			}

			public String getDetailNumMonth() {
				return detailNumMonth;
			}

			public void setDetailNumMonth(String detailNumMonth) {
				this.detailNumMonth = detailNumMonth;
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

			public String getShopId() {
				return shopId;
			}

			public void setShopId(String shopId) {
				this.shopId = shopId;
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
