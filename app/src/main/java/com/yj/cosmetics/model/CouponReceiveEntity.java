package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class CouponReceiveEntity {
	/**
	 * data : {"coupons":[{"conditionLimitnum":1,"couponName":"折扣","couponType":1,"conditionDesc":"wqeqweqw","couponRequire":"平台通用","faceValue":10,"date":"2018.06.18-2018.06.28","couponId":12,"couponTypeMsg":"折扣券"}],"count":1,"tel":"15137162645","homeimg":"/upload/homeImg/2018/06/26/65282fdd-24c6-4d4a-9475-18104a92829c.jpg"}
	 * code : 200
	 * msg : 成功
	 */

	private DataBean data;
	private String code;
	private String msg;
	public final String HTTP_OK = "200";

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static class DataBean {
		/**
		 * coupons : [{"conditionLimitnum":1,"couponName":"折扣","couponType":1,"conditionDesc":"wqeqweqw","couponRequire":"平台通用","faceValue":10,"date":"2018.06.18-2018.06.28","couponId":12,"couponTypeMsg":"折扣券"}]
		 * count : 1
		 * tel : 15137162645
		 * homeimg : /upload/homeImg/2018/06/26/65282fdd-24c6-4d4a-9475-18104a92829c.jpg
		 */

		private int count;
		private String tel;
		private String homeimg;
		private List<CouponsBean> coupons;

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getHomeimg() {
			return homeimg;
		}

		public void setHomeimg(String homeimg) {
			this.homeimg = homeimg;
		}

		public List<CouponsBean> getCoupons() {
			return coupons;
		}

		public void setCoupons(List<CouponsBean> coupons) {
			this.coupons = coupons;
		}

		public static class CouponsBean {
			/**
			 * conditionLimitnum : 1
			 * couponName : 折扣
			 * couponType : 1
			 * conditionDesc : wqeqweqw
			 * couponRequire : 平台通用
			 * faceValue : 10
			 * date : 2018.06.18-2018.06.28
			 * couponId : 12
			 * couponTypeMsg : 折扣券
			 */

			private int conditionLimitnum;
			private String couponName;
			private int couponType;
			private String conditionDesc;
			private String couponRequire;
			private String faceValue;
			private String date;
			private int couponId;
			private String productIds;
			private String couponTypeMsg;

			public String getProductIds() {
				return productIds;
			}

			public void setProductIds(String productIds) {
				this.productIds = productIds;
			}


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
		}
	}
}
