package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2019/6/20 0020.
 */

public class DataEntity {


	/**
	 * code : 200
	 * data : {"pageNumber":2,"shopArray":[{"numMonth":5,"productlist":[{"product_current":10,"product_id":228,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/11/95e39a7b-4b42-47c6-b650-a6e9227ccead.jpg"}],"shopId":33,"shopLogo":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/shop/2018/09/21/73c7b065-f543-49ee-8e44-63e0c5320a01.jpg","shopName":"shop2"},{"numMonth":8,"productlist":[{"product_current":1,"product_id":236,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/13/f9266984-d70e-4472-aa87-f8dfe02d3256.jpg"}],"shopId":46,"shopLogo":"/image/shoplmg.png","shopName":"15"},{"numMonth":9,"productlist":[{"product_current":1,"product_id":233,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/13/86f9a909-4c35-4e03-8347-bde9d43dfee2.jpg"}],"shopId":43,"shopLogo":"/image/shoplmg.png","shopName":"12"},{"numMonth":10,"productlist":[{"product_current":1,"product_id":241,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/14/b35e4ec7-8685-40c9-8a6d-a39532bb82d4.jpg"}],"shopId":50,"shopLogo":"/images/shoplmg.png","shopName":"20"},{"numMonth":0,"productlist":[{"product_current":15,"product_id":230,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/11/784e0271-6af9-4144-92dc-c23c0a45f537.jpg"}],"shopId":30,"shopLogo":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/shop/2018/09/07/9299bdc4-4a84-4f0c-bd7c-2607099e4f50.jpg","shopName":"ceshidianp"},{"numMonth":17,"productlist":[{"product_current":1,"product_id":238,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/13/4f49051c-39f4-44d9-ba17-4f2fbd666eae.jpg"}],"shopId":49,"shopLogo":"11","shopName":"18"},{"numMonth":34,"productlist":[{"product_current":1,"product_id":235,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/13/1fb19bf1-16d0-4260-ba6a-92c6617f9898.jpg"}],"shopId":45,"shopLogo":"/image/shoplmg.png","shopName":"14"}]}
	 * msg : 成功
	 */

	private String code;
	private DataBean data;
	private String msg;

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
		 * pageNumber : 2
		 * shopArray : [{"numMonth":5,"productlist":[{"product_current":10,"product_id":228,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/11/95e39a7b-4b42-47c6-b650-a6e9227ccead.jpg"}],"shopId":33,"shopLogo":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/shop/2018/09/21/73c7b065-f543-49ee-8e44-63e0c5320a01.jpg","shopName":"shop2"},{"numMonth":8,"productlist":[{"product_current":1,"product_id":236,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/13/f9266984-d70e-4472-aa87-f8dfe02d3256.jpg"}],"shopId":46,"shopLogo":"/image/shoplmg.png","shopName":"15"},{"numMonth":9,"productlist":[{"product_current":1,"product_id":233,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/13/86f9a909-4c35-4e03-8347-bde9d43dfee2.jpg"}],"shopId":43,"shopLogo":"/image/shoplmg.png","shopName":"12"},{"numMonth":10,"productlist":[{"product_current":1,"product_id":241,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/14/b35e4ec7-8685-40c9-8a6d-a39532bb82d4.jpg"}],"shopId":50,"shopLogo":"/images/shoplmg.png","shopName":"20"},{"numMonth":0,"productlist":[{"product_current":15,"product_id":230,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/11/784e0271-6af9-4144-92dc-c23c0a45f537.jpg"}],"shopId":30,"shopLogo":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/shop/2018/09/07/9299bdc4-4a84-4f0c-bd7c-2607099e4f50.jpg","shopName":"ceshidianp"},{"numMonth":17,"productlist":[{"product_current":1,"product_id":238,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/13/4f49051c-39f4-44d9-ba17-4f2fbd666eae.jpg"}],"shopId":49,"shopLogo":"11","shopName":"18"},{"numMonth":34,"productlist":[{"product_current":1,"product_id":235,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/13/1fb19bf1-16d0-4260-ba6a-92c6617f9898.jpg"}],"shopId":45,"shopLogo":"/image/shoplmg.png","shopName":"14"}]
		 */

		private String pageNumber;
		private List<ShopArrayBean> shopArray;

		public String getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(String pageNumber) {
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
			 * numMonth : 5
			 * productlist : [{"product_current":10,"product_id":228,"product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/11/95e39a7b-4b42-47c6-b650-a6e9227ccead.jpg"}]
			 * shopId : 33
			 * shopLogo : http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/shop/2018/09/21/73c7b065-f543-49ee-8e44-63e0c5320a01.jpg
			 * shopName : shop2
			 */

			private String numMonth;
			private String shopId;
			private String shopLogo;
			private String shopName;
			private List<ProductlistBean> productlist;

			public String getNumMonth() {
				return numMonth;
			}

			public void setNumMonth(String numMonth) {
				this.numMonth = numMonth;
			}

			public String getShopId() {
				return shopId;
			}

			public void setShopId(String shopId) {
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
		}
	}
}
