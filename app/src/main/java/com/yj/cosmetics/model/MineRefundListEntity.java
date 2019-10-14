package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class MineRefundListEntity {

	/**
	 * code : 200
	 * data : {"list":[{"item":[{"num":1,"price":35,"productImg":"/upload/product/2018/06/29/d4af967d-0046-42ca-837e-8748b88aec9e.png","productName":"辣小生","productSku":"成色:1公斤;"}],"orderId":1677,"orderNum":"20180801184907756802","returnId":127,"returnState":1,"returnType":1,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":108,"productImg":"/upload/product/2018/07/04/89337fd0-0326-4ead-bb68-bbb9762efa36.jpg","productName":"确美同确美同确美同确美同确美同同确美同确美同","productSku":"选择产品:喷雾60g喷雾60g喷雾60g;"}],"orderId":1681,"orderNum":"20180801190136977455","returnId":126,"returnState":1,"returnType":1,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":110,"productImg":"/upload/head/productImg/2018/07/27/f171b309-d2df-43fc-bafb-6f25b3d2566a.jpg","productName":"炭烤鳕鱼片","productSku":"选择产品:喷雾60g喷雾60g喷雾60g;","sproductId":3}],"orderId":1693,"orderNum":"20180802155957987523","returnId":125,"returnState":1,"returnType":1,"shopId":1,"shopImg":"http://img2.ph.126.net/vZ1r-IVk_j-cAi-noOuCEw==/760826862149437991.png","shopName":"小草鸭脖店"},{"item":[{"num":1,"price":110,"productImg":"/upload/head/productImg/2018/07/27/f171b309-d2df-43fc-bafb-6f25b3d2566a.jpg","productName":"炭烤鳕鱼片","productSku":"选择产品:喷雾60g喷雾60g喷雾60g;","sproductId":3}],"orderId":1685,"orderNum":"20180801195458081863","returnId":124,"returnState":1,"returnType":1,"shopId":1,"shopImg":"http://img2.ph.126.net/vZ1r-IVk_j-cAi-noOuCEw==/760826862149437991.png","shopName":"小草鸭脖店"},{"item":[{"num":1,"price":35,"productImg":"/upload/product/2018/06/29/d4af967d-0046-42ca-837e-8748b88aec9e.png","productName":"辣小生","productSku":"成色:1公斤;"}],"orderId":1651,"orderNum":"20180719113121549895","returnId":120,"returnState":1,"returnType":1,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":777,"productImg":"/upload/head/productImg/2018/06/22/e856a702-4afe-43b4-b063-dfd08413a6e8.png","productName":"fdf","productSk":"规格:默认"}],"orderId":1422,"orderNum":"20180630155029926395","returnId":113,"returnState":3,"returnType":2,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":25,"productImg":"/upload/head/productImg/2018/06/30/e199fed3-3fb2-4486-934f-fb69f32bd51e.jpg","productName":"狼图腾-小狼","productSk":"规格:默认"}],"orderId":1463,"orderNum":"20180702101014193780","returnId":112,"returnState":3,"returnType":1,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":25,"productImg":"/upload/head/productImg/2018/06/30/5e19cff9-3cbd-4c57-a3cc-835af4c9b9c3.jpg","productName":"狼图腾-小狼","productSk":"规格:默认"}],"orderId":1521,"orderNum":"20180703154930139704","returnId":109,"returnState":1,"returnType":2,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":25,"productImg":"/upload/head/productImg/2018/06/30/e199fed3-3fb2-4486-934f-fb69f32bd51e.jpg","productName":"狼图腾-小狼","productSk":"规格:默认"}],"orderId":1462,"orderNum":"20180702100620561392","returnId":93,"returnState":2,"returnType":1,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":278,"productImg":"/upload/product/2018/06/29/3775d260-be20-4226-a5a7-6b338db4d350.jpg","productName":"阳澄湖大闸蟹","productSku":"种类:(公4.0两 母3.0两)8只;"}],"orderId":1459,"orderNum":"20180630203214854302","returnId":87,"returnState":1,"returnType":2,"shopImg":"/images/shopImg.png","shopName":"自营"}],"total":21}
	 * msg : 成功
	 */
	private String code;
	private DataBean data;
	private String msg;
	public  String HTTP_OK = "200";

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
		 * list : [{"item":[{"num":1,"price":35,"productImg":"/upload/product/2018/06/29/d4af967d-0046-42ca-837e-8748b88aec9e.png","productName":"辣小生","productSku":"成色:1公斤;"}],"orderId":1677,"orderNum":"20180801184907756802","returnId":127,"returnState":1,"returnType":1,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":108,"productImg":"/upload/product/2018/07/04/89337fd0-0326-4ead-bb68-bbb9762efa36.jpg","productName":"确美同确美同确美同确美同确美同同确美同确美同","productSku":"选择产品:喷雾60g喷雾60g喷雾60g;"}],"orderId":1681,"orderNum":"20180801190136977455","returnId":126,"returnState":1,"returnType":1,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":110,"productImg":"/upload/head/productImg/2018/07/27/f171b309-d2df-43fc-bafb-6f25b3d2566a.jpg","productName":"炭烤鳕鱼片","productSku":"选择产品:喷雾60g喷雾60g喷雾60g;","sproductId":3}],"orderId":1693,"orderNum":"20180802155957987523","returnId":125,"returnState":1,"returnType":1,"shopId":1,"shopImg":"http://img2.ph.126.net/vZ1r-IVk_j-cAi-noOuCEw==/760826862149437991.png","shopName":"小草鸭脖店"},{"item":[{"num":1,"price":110,"productImg":"/upload/head/productImg/2018/07/27/f171b309-d2df-43fc-bafb-6f25b3d2566a.jpg","productName":"炭烤鳕鱼片","productSku":"选择产品:喷雾60g喷雾60g喷雾60g;","sproductId":3}],"orderId":1685,"orderNum":"20180801195458081863","returnId":124,"returnState":1,"returnType":1,"shopId":1,"shopImg":"http://img2.ph.126.net/vZ1r-IVk_j-cAi-noOuCEw==/760826862149437991.png","shopName":"小草鸭脖店"},{"item":[{"num":1,"price":35,"productImg":"/upload/product/2018/06/29/d4af967d-0046-42ca-837e-8748b88aec9e.png","productName":"辣小生","productSku":"成色:1公斤;"}],"orderId":1651,"orderNum":"20180719113121549895","returnId":120,"returnState":1,"returnType":1,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":777,"productImg":"/upload/head/productImg/2018/06/22/e856a702-4afe-43b4-b063-dfd08413a6e8.png","productName":"fdf","productSk":"规格:默认"}],"orderId":1422,"orderNum":"20180630155029926395","returnId":113,"returnState":3,"returnType":2,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":25,"productImg":"/upload/head/productImg/2018/06/30/e199fed3-3fb2-4486-934f-fb69f32bd51e.jpg","productName":"狼图腾-小狼","productSk":"规格:默认"}],"orderId":1463,"orderNum":"20180702101014193780","returnId":112,"returnState":3,"returnType":1,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":25,"productImg":"/upload/head/productImg/2018/06/30/5e19cff9-3cbd-4c57-a3cc-835af4c9b9c3.jpg","productName":"狼图腾-小狼","productSk":"规格:默认"}],"orderId":1521,"orderNum":"20180703154930139704","returnId":109,"returnState":1,"returnType":2,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":25,"productImg":"/upload/head/productImg/2018/06/30/e199fed3-3fb2-4486-934f-fb69f32bd51e.jpg","productName":"狼图腾-小狼","productSk":"规格:默认"}],"orderId":1462,"orderNum":"20180702100620561392","returnId":93,"returnState":2,"returnType":1,"shopImg":"/images/shopImg.png","shopName":"自营"},{"item":[{"num":1,"price":278,"productImg":"/upload/product/2018/06/29/3775d260-be20-4226-a5a7-6b338db4d350.jpg","productName":"阳澄湖大闸蟹","productSku":"种类:(公4.0两 母3.0两)8只;"}],"orderId":1459,"orderNum":"20180630203214854302","returnId":87,"returnState":1,"returnType":2,"shopImg":"/images/shopImg.png","shopName":"自营"}]
		 * total : 21
		 */

		private int total;
		private List<ListBean> list;

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public List<ListBean> getList() {
			return list;
		}

		public void setList(List<ListBean> list) {
			this.list = list;
		}

		public static class ListBean {
			/**
			 * item : [{"num":1,"price":35,"productImg":"/upload/product/2018/06/29/d4af967d-0046-42ca-837e-8748b88aec9e.png","productName":"辣小生","productSku":"成色:1公斤;"}]
			 * orderId : 1677
			 * orderNum : 20180801184907756802
			 * returnId : 127
			 * returnState : 1
			 * returnType : 1
			 * shopImg : /images/shopImg.png
			 * shopName : 自营
			 * shopId : 1
			 */

			private int orderId;
			private String orderNum;
			private int returnId;
			private int returnState;//@ 1待处理 2退款成功 3退款驳回
			private int returnType; //@ 退款类型（1：仅退款2：退货并退款）
			private String shopImg;
			private String shopName;
			private String shopId;
			private List<ItemBean> item;

			public int getOrderId() {
				return orderId;
			}

			public void setOrderId(int orderId) {
				this.orderId = orderId;
			}

			public String getOrderNum() {
				return orderNum;
			}

			public void setOrderNum(String orderNum) {
				this.orderNum = orderNum;
			}

			public int getReturnId() {
				return returnId;
			}

			public void setReturnId(int returnId) {
				this.returnId = returnId;
			}

			public int getReturnState() {
				return returnState;
			}

			public void setReturnState(int returnState) {
				this.returnState = returnState;
			}

			public int getReturnType() {
				return returnType;
			}

			public void setReturnType(int returnType) {
				this.returnType = returnType;
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

			public String getShopId() {
				return shopId;
			}

			public void setShopId(String shopId) {
				this.shopId = shopId;
			}

			public List<ItemBean> getItem() {
				return item;
			}

			public void setItem(List<ItemBean> item) {
				this.item = item;
			}

			public static class ItemBean {
				/**
				 * num : 1
				 * price : 35
				 * productImg : /upload/product/2018/06/29/d4af967d-0046-42ca-837e-8748b88aec9e.png
				 * productName : 辣小生
				 * productSku : 成色:1公斤;
				 */

				private int num;
				private String price;
				private String productImg;
				private String productName;
				private String productSku;

				public int getNum() {
					return num;
				}

				public void setNum(int num) {
					this.num = num;
				}

				public String getPrice() {
					return price;
				}

				public void setPrice(String price) {
					this.price = price;
				}

				public String getProductImg() {
					return productImg;
				}

				public void setProductImg(String productImg) {
					this.productImg = productImg;
				}

				public String getProductName() {
					return productName;
				}

				public void setProductName(String productName) {
					this.productName = productName;
				}

				public String getProductSku() {
					return productSku;
				}

				public void setProductSku(String productSku) {
					this.productSku = productSku;
				}
			}
		}
	}
}
