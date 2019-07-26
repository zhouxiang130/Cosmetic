package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/21.
 */

public class GoodsEntity {
	private String code;
	private String msg;
	private GoodsData data;
	public final String HTTP_OK = "200";


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public GoodsData getData() {
		return data;
	}

	public void setData(GoodsData data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public class GoodsData {
		private String productListimg;
		private ProDetailCouponBean proDetailCoupon;
		private String detailNum;
		private String detailNumMonth;
		private String productMaxpurchase;
		private String productId;
		private String productUrl;
		private String productCurrent;
		private String consumerBackmoney;
		private String consumerBackscore;
		private String productStock;
		private String cartNumber;
		private String productOrginal;
		private String whetcollection;
		private String productAbstract;
		private String productName;
		private List<String> jsodetimgs;
		private String productTimeend;
		private String productHot;
		private String time;

		private String shopId;
		private String sproductId;

		private String productTimelimit;
		private String sold;
		private String ifStartTimeLimit;
		private String startTimeLimitMd;
		private String startTimeLimitHm;
		private String systemValue;
		private String detailJsp;
		private String productDesc;
		private String spiderUrl;
		private String receipt;
		private String endTimeStyle;
		//
		private long selfTime;
		private String finalTime;
		private int hours;
		private int min;
		private int sec;

		public String getReceipt() {
			return receipt;
		}

		public void setReceipt(String receipt) {
			this.receipt = receipt;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getSproductId() {
			return sproductId;
		}

		public void setSproductId(String sproductId) {
			this.sproductId = sproductId;
		}

		public String getEndTimeStyle() {
			return endTimeStyle;
		}

		public void setEndTimeStyle(String endTimeStyle) {
			this.endTimeStyle = endTimeStyle;
		}

		public ProDetailCouponBean getProDetailCoupon() {
			return proDetailCoupon;
		}

		public void setProDetailCoupon(ProDetailCouponBean proDetailCoupon) {
			this.proDetailCoupon = proDetailCoupon;
		}


		public class ProDetailCouponBean {
			/**
			 * coupons : [{"conditionLimitnum":3,"couponName":"折扣券一","couponType":1,"conditionDesc":"王企鹅无群111","couponRequire":"仅限无机水果分类可用","faceValue":1,"classifyId":7,"date":"不限-2018.07.02","couponId":6,"couponTypeMsg":"折扣券"},{"conditionLimitnum":2,"couponName":"兑换券","couponType":3,"productIds":"","conditionDesc":"请问","couponRequire":"仅限商品可用","faceValue":100,"date":"2018.06.21-2018.06.26","couponId":7,"couponTypeMsg":"兑换券"}]
			 * couponName : 折扣券一
			 * count : 2
			 */

			private String couponName;
			private int count;
			private List<CouponsBean> coupons;

			public String getCouponName() {
				return couponName;
			}

			public void setCouponName(String couponName) {
				this.couponName = couponName;
			}

			public int getCount() {
				return count;
			}

			public void setCount(int count) {
				this.count = count;
			}

			public List<CouponsBean> getCoupons() {
				return coupons;
			}

			public void setCoupons(List<CouponsBean> coupons) {
				this.coupons = coupons;
			}

			public class CouponsBean {
				/**
				 * conditionLimitnum : 3
				 * couponName : 折扣券一
				 * couponType : 1
				 * conditionDesc : 王企鹅无群111
				 * couponRequire : 仅限无机水果分类可用
				 * faceValue : 1
				 * classifyId : 7
				 * date : 不限-2018.07.02
				 * couponId : 6
				 * couponTypeMsg : 折扣券
				 * productIds :
				 */

				private int conditionLimitnum;
				private String couponName;
				private int couponType;
				private String conditionDesc;
				private String couponRequire;
				private String faceValue;
				private int classifyId;
				private String date;
				private int couponId;
				private String couponTypeMsg;
				private String productIds;

				public int getConditionLimitnum() {
					return conditionLimitnum;
				}

				public void setConditionLimitnum(int conditionLimitnum) {
					this.conditionLimitnum = conditionLimitnum;
				}

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

				public String getConditionDesc() {
					return conditionDesc;
				}

				public void setConditionDesc(String conditionDesc) {
					this.conditionDesc = conditionDesc;
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

				public int getClassifyId() {
					return classifyId;
				}

				public void setClassifyId(int classifyId) {
					this.classifyId = classifyId;
				}

				public String getDate() {
					return date;
				}

				public void setDate(String date) {
					this.date = date;
				}

				public int getCouponId() {
					return couponId;
				}

				public void setCouponId(int couponId) {
					this.couponId = couponId;
				}

				public String getCouponTypeMsg() {
					return couponTypeMsg;
				}

				public void setCouponTypeMsg(String couponTypeMsg) {
					this.couponTypeMsg = couponTypeMsg;
				}

				public String getProductIds() {
					return productIds;
				}

				public void setProductIds(String productIds) {
					this.productIds = productIds;
				}
			}
		}

		public String getSpiderUrl() {
			return spiderUrl;
		}

		public void setSpiderUrl(String spiderUrl) {
			this.spiderUrl = spiderUrl;
		}

		public String getProductUrl() {
			return productUrl;
		}

		public void setProductUrl(String productUrl) {
			this.productUrl = productUrl;
		}

		public String getStartTimeLimitMd() {
			return startTimeLimitMd;
		}

		public void setStartTimeLimitMd(String startTimeLimitMd) {
			this.startTimeLimitMd = startTimeLimitMd;
		}

		public String getIfStartTimeLimit() {
			return ifStartTimeLimit;
		}

		public void setIfStartTimeLimit(String ifStartTimeLimit) {
			this.ifStartTimeLimit = ifStartTimeLimit;
		}

		public String getConsumerBackscore() {
			return consumerBackscore;
		}

		public void setConsumerBackscore(String consumerBackscore) {
			this.consumerBackscore = consumerBackscore;
		}

		public String getStartTimeLimitHm() {
			return startTimeLimitHm;
		}

		public void setStartTimeLimitHm(String startTimeLimitHm) {
			this.startTimeLimitHm = startTimeLimitHm;
		}

		public String getFinalTime() {
			return finalTime;
		}

		public void setFinalTime(String finalTime) {
			this.finalTime = finalTime;
		}

		public int getHours() {
			return hours;
		}

		public void setHours(int hours) {
			this.hours = hours;
		}

		public int getMin() {
			return min;
		}

		public void setMin(int min) {
			this.min = min;
		}

		public int getSec() {
			return sec;
		}

		public void setSec(int sec) {
			this.sec = sec;
		}

		public long getSelfTime() {
			return selfTime;
		}

		public void setSelfTime(long selfTime) {
			this.selfTime = selfTime;
		}

		public String getDetailJsp() {
			return detailJsp;
		}

		public void setDetailJsp(String detailJsp) {
			this.detailJsp = detailJsp;
		}

		public String getProductDesc() {
			return productDesc;
		}

		public void setProductDesc(String productDesc) {
			this.productDesc = productDesc;
		}

		public String getSystemValue() {
			return systemValue;
		}

		public void setSystemValue(String systemValue) {
			this.systemValue = systemValue;
		}

		public String getSold() {
			return sold;
		}

		public void setSold(String sold) {
			this.sold = sold;
		}

		public String getDetailNumMonth() {
			return detailNumMonth;
		}

		public void setDetailNumMonth(String detailNumMonth) {
			this.detailNumMonth = detailNumMonth;
		}

		public String getProductHot() {
			return productHot;
		}

		public void setProductHot(String productHot) {
			this.productHot = productHot;
		}

		public String getProductTimeend() {
			return productTimeend;
		}

		public void setProductTimeend(String productTimeend) {
			this.productTimeend = productTimeend;
		}

		public String getProductTimelimit() {
			return productTimelimit;
		}

		public void setProductTimelimit(String productTimelimit) {
			this.productTimelimit = productTimelimit;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getCartNumber() {
			return cartNumber;
		}

		public void setCartNumber(String cartNumber) {
			this.cartNumber = cartNumber;
		}

		public String getConsumerBackmoney() {
			return consumerBackmoney;
		}

		public void setConsumerBackmoney(String consumerBackmoney) {
			this.consumerBackmoney = consumerBackmoney;
		}

		public String getDetailNum() {
			return detailNum;
		}

		public void setDetailNum(String detailNum) {
			this.detailNum = detailNum;
		}

		public List<String> getJsodetimgs() {
			return jsodetimgs;
		}

		public void setJsodetimgs(List<String> jsodetimgs) {
			this.jsodetimgs = jsodetimgs;
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

		public String getProductStock() {
			return productStock;
		}

		public void setProductStock(String productStock) {
			this.productStock = productStock;
		}

		public String getWhetcollection() {
			return whetcollection;
		}

		public void setWhetcollection(String whetcollection) {
			this.whetcollection = whetcollection;
		}
	}


}
