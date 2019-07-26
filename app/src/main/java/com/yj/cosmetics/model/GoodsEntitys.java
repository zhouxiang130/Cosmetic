package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2019/6/13 0013.
 */

public class GoodsEntitys {
	/**
	 * code : 200
	 * data : {"comment":{"commArray":[],"commentCount":0},"data":{"cartNumber":0,"jsodetimgs":["http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/414ac3a1-4cdb-4b38-aeb1-74022c6740d2.jpg","http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/a1f12604-5e20-4410-b71f-e6abaf590494.jpg","http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/edae9d21-ef43-4fb3-b0c0-67f3be4aa13c.jpg"],"productAbstract":"女士各种肤质保湿补水紧肤淡皱深层清洁提拉紧致","productCurrent":"265.0","productDesc":"<p>自然堂（CHAND）面部护肤套装礼盒/单品套装（洁面霜+冰肌水+休眠霜 ）女士各种肤质保湿补水紧肤淡皱深层清洁提拉紧致","productId":215,"productListimg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/6478c599-c3ad-4464-be3e-fdc2da7e64e7.jpg","productMaxpurchase":10,"productName":"自然堂（CHAND）面部护肤套装礼盒/单品套装（洁面霜+冰肌水+休眠霜 ）女士各种肤质保湿补水紧肤淡皱深层清洁提拉紧致","productOrginal":"285.0","productSales":0,"productStock":100,"productType":0,"productUrl":"/weChat/homePage/productDetails?productId=215","whetcollection":"no"}}
	 * msg : 成功
	 */

	private String code;
	private DataBeanX data;
	private String msg;
	public final String HTTP_OK = "200";

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
		 * comment : {"commArray":[],"commentCount":0}
		 * data : {"cartNumber":0,"jsodetimgs":["http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/414ac3a1-4cdb-4b38-aeb1-74022c6740d2.jpg","http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/a1f12604-5e20-4410-b71f-e6abaf590494.jpg","http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/edae9d21-ef43-4fb3-b0c0-67f3be4aa13c.jpg"],"productAbstract":"女士各种肤质保湿补水紧肤淡皱深层清洁提拉紧致","productCurrent":"265.0","productDesc":"<p>自然堂（CHAND）面部护肤套装礼盒/单品套装（洁面霜+冰肌水+休眠霜 ）女士各种肤质保湿补水紧肤淡皱深层清洁提拉紧致","productId":215,"productListimg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/6478c599-c3ad-4464-be3e-fdc2da7e64e7.jpg","productMaxpurchase":10,"productName":"自然堂（CHAND）面部护肤套装礼盒/单品套装（洁面霜+冰肌水+休眠霜 ）女士各种肤质保湿补水紧肤淡皱深层清洁提拉紧致","productOrginal":"285.0","productSales":0,"productStock":100,"productType":0,"productUrl":"/weChat/homePage/productDetails?productId=215","whetcollection":"no"}
		 */

		private CommentBean comment;
		private DataBean data;

		public CommentBean getComment() {
			return comment;
		}

		public void setComment(CommentBean comment) {
			this.comment = comment;
		}

		public DataBean getData() {
			return data;
		}

		public void setData(DataBean data) {
			this.data = data;
		}

		public static class CommentBean {
			/**
			 * commArray : []
			 * commentCount : 0
			 */

			private String commentCount;
			private List<?> commArray;

			public String getCommentCount() {
				return commentCount;
			}

			public void setCommentCount(String commentCount) {
				this.commentCount = commentCount;
			}

			public List<?> getCommArray() {
				return commArray;
			}

			public void setCommArray(List<?> commArray) {
				this.commArray = commArray;
			}
		}

		public static class DataBean {
			/**
			 * cartNumber : 0
			 * jsodetimgs : ["http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/414ac3a1-4cdb-4b38-aeb1-74022c6740d2.jpg","http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/a1f12604-5e20-4410-b71f-e6abaf590494.jpg","http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/edae9d21-ef43-4fb3-b0c0-67f3be4aa13c.jpg"]
			 * productAbstract : 女士各种肤质保湿补水紧肤淡皱深层清洁提拉紧致
			 * productCurrent : 265.0
			 * productDesc : <p>自然堂（CHAND）面部护肤套装礼盒/单品套装（洁面霜+冰肌水+休眠霜 ）女士各种肤质保湿补水紧肤淡皱深层清洁提拉紧致
			 * productId : 215
			 * productListimg : http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/10/6478c599-c3ad-4464-be3e-fdc2da7e64e7.jpg
			 * productMaxpurchase : 10
			 * productName : 自然堂（CHAND）面部护肤套装礼盒/单品套装（洁面霜+冰肌水+休眠霜 ）女士各种肤质保湿补水紧肤淡皱深层清洁提拉紧致
			 * productOrginal : 285.0
			 * productSales : 0
			 * productStock : 100
			 * productType : 0
			 * productUrl : /weChat/homePage/productDetails?productId=215
			 * whetcollection : no
			 */

			private String cartNumber;
			private String productAbstract;
			private String productCurrent;
			private String productDesc;
			private String productId;
			private String productListimg;
			private String productMaxpurchase;
			private String productName;
			private String productOrginal;
			private String productSales;
			private String productStock;
			private String productType;
			private String productState;
			private String productUrl;
			private String whetcollection;
			private String shopId;
			private List<String> jsodetimgs;

			public String getShopId() {
				return shopId;
			}

			public void setShopId(String shopId) {
				this.shopId = shopId;
			}

			public String getProductState() {
				return productState;
			}

			public void setProductState(String productState) {
				this.productState = productState;
			}

			public String getCartNumber() {
				return cartNumber;
			}

			public void setCartNumber(String cartNumber) {
				this.cartNumber = cartNumber;
			}

			public String getProductAbstract() {
				return productAbstract;
			}

			public void setProductAbstract(String productAbstract) {
				this.productAbstract = productAbstract;
			}

			public String getProductCurrent() {
				return productCurrent;
			}

			public void setProductCurrent(String productCurrent) {
				this.productCurrent = productCurrent;
			}

			public String getProductDesc() {
				return productDesc;
			}

			public void setProductDesc(String productDesc) {
				this.productDesc = productDesc;
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

			public String getProductMaxpurchase() {
				return productMaxpurchase;
			}

			public void setProductMaxpurchase(String productMaxpurchase) {
				this.productMaxpurchase = productMaxpurchase;
			}

			public String getProductName() {
				return productName;
			}

			public void setProductName(String productName) {
				this.productName = productName;
			}

			public String getProductOrginal() {
				return productOrginal;
			}

			public void setProductOrginal(String productOrginal) {
				this.productOrginal = productOrginal;
			}

			public String getProductSales() {
				return productSales;
			}

			public void setProductSales(String productSales) {
				this.productSales = productSales;
			}

			public String getProductStock() {
				return productStock;
			}

			public void setProductStock(String productStock) {
				this.productStock = productStock;
			}

			public String getProductType() {
				return productType;
			}

			public void setProductType(String productType) {
				this.productType = productType;
			}

			public String getProductUrl() {
				return productUrl;
			}

			public void setProductUrl(String productUrl) {
				this.productUrl = productUrl;
			}

			public String getWhetcollection() {
				return whetcollection;
			}

			public void setWhetcollection(String whetcollection) {
				this.whetcollection = whetcollection;
			}

			public List<String> getJsodetimgs() {
				return jsodetimgs;
			}

			public void setJsodetimgs(List<String> jsodetimgs) {
				this.jsodetimgs = jsodetimgs;
			}
		}
	}
}
