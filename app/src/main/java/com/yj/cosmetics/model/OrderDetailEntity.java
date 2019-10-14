package com.yj.cosmetics.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Suo on 2018/3/23.
 */

public class OrderDetailEntity {

	private String code;
	private String msg;
	private OrderDetialData data;
	public final String HTTP_OK = "200";


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public OrderDetialData getData() {
		return data;
	}

	public void setData(OrderDetialData data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static class OrderDetialData {


		private String orderPaystyle;
		private String orderState;
		private String refundType;//@ 下单之后，不能发起退款


		private String isAccelerate;
		private String orderSendCosts;
		private String receiverTel;
		private String orderNum;
		private String orderId;
		private String couponMoney;
		private String payTime;
		private String productState;
		private String upOrderMoney;

		private String shopId;
		private String shopImg;
		private String shopName;
		private String sendTime;
		private String sproductId;


		private String orderBalance;
		private String orderPaymoney;
		private String time;
		private String address;
		private String receiverName;
		private String orderStateName;
		private String orderMoney;
		private String proNumber;
		private List<OrderDetialItem> items;
		private String content;
		private String ftime;
		private String proMoneyAll;



		public String getIsAccelerate() {
			return isAccelerate;
		}

		public void setIsAccelerate(String isAccelerate) {
			this.isAccelerate = isAccelerate;
		}

		public String getRefundType() {
			return refundType;
		}

		public void setRefundType(String refundType) {
			this.refundType = refundType;
		}

		public String getSproductId() {
			return sproductId;
		}

		public void setSproductId(String sproductId) {
			this.sproductId = sproductId;
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

		public String getSendTime() {
			return sendTime;
		}

		public void setSendTime(String sendTime) {
			this.sendTime = sendTime;
		}

		public String getProMoneyAll() {
			return proMoneyAll;
		}

		public void setProMoneyAll(String proMoneyAll) {
			this.proMoneyAll = proMoneyAll;
		}

		public String getUpOrderMoney() {
			return upOrderMoney;
		}

		public void setUpOrderMoney(String upOrderMoney) {
			this.upOrderMoney = upOrderMoney;
		}

		public String getPayTime() {
			return payTime;
		}

		public void setPayTime(String payTime) {
			this.payTime = payTime;
		}

		public String getCouponMoney() {
			return couponMoney;
		}

		public void setCouponMoney(String couponMoney) {
			this.couponMoney = couponMoney;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getFtime() {
			return ftime;
		}

		public void setFtime(String ftime) {
			this.ftime = ftime;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public List<OrderDetialItem> getItems() {
			return items;
		}

		public void setItems(List<OrderDetialItem> items) {
			this.items = items;
		}

		public String getOrderBalance() {
			return orderBalance;
		}

		public void setOrderBalance(String orderBalance) {
			this.orderBalance = orderBalance;
		}

		public String getOrderMoney() {
			return orderMoney;
		}

		public void setOrderMoney(String orderMoney) {
			this.orderMoney = orderMoney;
		}

		public String getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(String orderNum) {
			this.orderNum = orderNum;
		}

		public String getOrderPaymoney() {
			return orderPaymoney;
		}

		public void setOrderPaymoney(String orderPaymoney) {
			this.orderPaymoney = orderPaymoney;
		}

		public String getOrderPayStyle() {
			return orderPaystyle;
		}

		public void setOrderPayStyle(String orderPayStyle) {
			this.orderPaystyle = orderPayStyle;
		}

		public String getOrderSendCosts() {
			return orderSendCosts;
		}

		public void setOrderSendCosts(String orderSendCosts) {
			this.orderSendCosts = orderSendCosts;
		}

		public String getOrderState() {
			return orderState;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}


		public void setOrderState(String orderState) {
			this.orderState = orderState;
		}

		public String getOrderStateName() {
			return orderStateName;
		}

		public void setOrderStateName(String orderStateName) {
			this.orderStateName = orderStateName;
		}

		public String getProductState() {
			return productState;
		}

		public void setProductState(String productState) {
			this.productState = productState;
		}

		public String getProNumber() {
			return proNumber;
		}

		public void setProNumber(String proNumber) {
			this.proNumber = proNumber;
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

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public static class OrderDetialItem implements Parcelable {
			private String productListimg;
			private String skuPropertiesName;
			private String productId;
			private String proNumber;
			private String money;
			private String productName;


			public static final Creator<OrderDetialItem> CREATOR = new Creator<OrderDetialItem>() {

				@Override
				public OrderDetialItem createFromParcel(Parcel source) {
					OrderDetialItem orderDetialItem = new OrderDetialItem();
					orderDetialItem.productListimg = source.readString();
					orderDetialItem.skuPropertiesName = source.readString();
					orderDetialItem.productId = source.readString();
					orderDetialItem.proNumber = source.readString();
					orderDetialItem.money = source.readString();
					orderDetialItem.productName = source.readString();
					return orderDetialItem;
				}

				@Override
				public OrderDetialItem[] newArray(int size) {
					return new OrderDetialItem[size];
				}
			};


			public String getDetailNum() {
				return proNumber;
			}

			public void setDetailNum(String detailNum) {
				this.proNumber = detailNum;
			}

			public String getMoney() {
				return money;
			}

			public void setMoney(String money) {
				this.money = money;
			}

			public String getProductListimg() {
				return productListimg;
			}

			public void setProductListimg(String productListimg) {
				this.productListimg = productListimg;
			}

			public String getProductName() {
				return productName;
			}

			public void setProductName(String productName) {
				this.productName = productName;
			}

			public String getSkuPropertiesName() {
				return skuPropertiesName;
			}

			public void setSkuPropertiesName(String skuPropertiesName) {
				this.skuPropertiesName = skuPropertiesName;
			}

			public String getProductId() {
				return productId;
			}

			public void setProductId(String productId) {
				this.productId = productId;
			}

			@Override
			public int describeContents() {
				return 0;
			}

			@Override
			public void writeToParcel(Parcel dest, int flags) {
				dest.writeString(productListimg);
				dest.writeString(skuPropertiesName);
				dest.writeString(productId);
				dest.writeString(proNumber);
				dest.writeString(money);
				dest.writeString(productName);
			}
		}
	}
}