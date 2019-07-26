package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public class CouponListEntity {
	/**
	 * code : 200
	 * data : {"userPossession":{"couponNumber":7,"coupons":[{"classifyId":29,"couponId":8,"couponTypeMsg":"代金券","couponUsingRangeMsg":"仅限手表分类可用","date":"2018.06.14-2018.06.19","faceValue":100,"productIds":"51,50,"}]}}
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
		 * userPossession : {"couponNumber":7,"coupons":[{"classifyId":29,"couponId":8,"couponTypeMsg":"代金券","couponUsingRangeMsg":"仅限手表分类可用","date":"2018.06.14-2018.06.19","faceValue":100,"productIds":"51,50,"}]}
		 */

		private UserPossessionBean userPossession;

		public UserPossessionBean getUserPossession() {
			return userPossession;
		}

		public void setUserPossession(UserPossessionBean userPossession) {
			this.userPossession = userPossession;
		}

		public static class UserPossessionBean {
			/**
			 * couponNumber : 7
			 * coupons : [{"classifyId":29,"couponId":8,"couponTypeMsg":"代金券","couponUsingRangeMsg":"仅限手表分类可用","date":"2018.06.14-2018.06.19","faceValue":100,"productIds":"51,50,"}]
			 */

			private int couponNumber;
			private List<CouponsBean> coupons;

			public int getCouponNumber() {
				return couponNumber;
			}

			public void setCouponNumber(int couponNumber) {
				this.couponNumber = couponNumber;
			}

			public List<CouponsBean> getCoupons() {
				return coupons;
			}

			public void setCoupons(List<CouponsBean> coupons) {
				this.coupons = coupons;
			}

			public static class CouponsBean {
				/**
				 * classifyId : 29
				 * couponId : 8
				 * couponTypeMsg : 代金券
				 * couponUsingRangeMsg : 仅限手表分类可用
				 * date : 2018.06.14-2018.06.19
				 * faceValue : 100
				 * productIds : 51,50,
				 */

				private int classifyId;
				private int couponId;
				private String couponRequire;
				private String couponTypeMsg;
				private String couponUsingRangeMsg;
				private String date;
				private String faceValue;
				private int couponType;
				private String productIds;

				public String getCouponName() {
					return couponName;
				}

				public void setCouponName(String couponName) {
					this.couponName = couponName;
				}

				private String couponName;


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

				public int getClassifyId() {
					return classifyId;
				}

				public void setClassifyId(int classifyId) {
					this.classifyId = classifyId;
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

				public String getCouponUsingRangeMsg() {
					return couponUsingRangeMsg;
				}

				public void setCouponUsingRangeMsg(String couponUsingRangeMsg) {
					this.couponUsingRangeMsg = couponUsingRangeMsg;
				}

				public String getDate() {
					return date;
				}

				public void setDate(String date) {
					this.date = date;
				}

				public String getFaceValue() {
					return faceValue;
				}

				public void setFaceValue(String faceValue) {
					this.faceValue = faceValue;
				}

				public String getProductIds() {
					return productIds;
				}

				public void setProductIds(String productIds) {
					this.productIds = productIds;
				}
			}
		}
	}
}
