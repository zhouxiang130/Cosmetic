package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/23 0023.
 */

public class CouponListDataEntity {
	/**
	 * msg : 成功
	 * code : 200
	 * data : {"total":3,"countUserPossession":0,"list":[{"date":"2018.06.22-2018.07.01","couponRequire":"平台通用","couponUsingRangeMsg":"平台通用","faceValue":2,"couponType":1,"couponShare":1,"couponTypeMsg":"折扣券","cosureState":2,"userCouponId":286,"couponId":5},{"date":"2018.06.14-2018.06.24","couponRequire":"满150.0元可用","couponUsingRangeMsg":"仅限手表分类可用","classifyId":29,"faceValue":100,"couponType":2,"couponShare":1,"couponTypeMsg":"代金券","cosureState":2,"userCouponId":288,"couponId":8},{"date":"2018.06.23-2018.07.02","couponRequire":"平台通用","couponUsingRangeMsg":"平台通用","faceValue":2,"couponType":1,"couponShare":1,"couponTypeMsg":"折扣券","cosureState":1,"userCouponId":319,"couponId":5}]}
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
		 * total : 3
		 * countUserPossession : 0
		 * list : [{"date":"2018.06.22-2018.07.01","couponRequire":"平台通用","couponUsingRangeMsg":"平台通用","faceValue":2,"couponType":1,"couponShare":1,"couponTypeMsg":"折扣券","cosureState":2,"userCouponId":286,"couponId":5},{"date":"2018.06.14-2018.06.24","couponRequire":"满150.0元可用","couponUsingRangeMsg":"仅限手表分类可用","classifyId":29,"faceValue":100,"couponType":2,"couponShare":1,"couponTypeMsg":"代金券","cosureState":2,"userCouponId":288,"couponId":8},{"date":"2018.06.23-2018.07.02","couponRequire":"平台通用","couponUsingRangeMsg":"平台通用","faceValue":2,"couponType":1,"couponShare":1,"couponTypeMsg":"折扣券","cosureState":1,"userCouponId":319,"couponId":5}]
		 */

		private int total;
		private int countUserPossession;
		private List<ListBean> list;

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public int getCountUserPossession() {
			return countUserPossession;
		}

		public void setCountUserPossession(int countUserPossession) {
			this.countUserPossession = countUserPossession;
		}

		public List<ListBean> getList() {
			return list;
		}

		public void setList(List<ListBean> list) {
			this.list = list;
		}

		public static class ListBean {
			/**
			 * date : 2018.06.22-2018.07.01
			 * couponRequire : 平台通用
			 * couponUsingRangeMsg : 平台通用
			 * faceValue : 2
			 * couponType : 1
			 * couponShare : 1
			 * couponTypeMsg : 折扣券
			 * cosureState : 2
			 * userCouponId : 286
			 * couponId : 5
			 * classifyId : 29
			 */

			private String date;
			private String couponRequire;
			private String couponUsingRangeMsg;
			private String couponName;
			private String conditionDesc;


			private String closureNum;
			private String faceValue;
			private int couponType;
			private int couponShare;
			private String couponTypeMsg;
			private int cosureState;
			private int userCouponId;
			private int couponId;
			private String classifyId;

			public String getClosureNum() {
				return closureNum;
			}

			public void setClosureNum(String closureNum) {
				this.closureNum = closureNum;
			}
			public String getCouponName() {
				return couponName;
			}

			public void setCouponName(String couponName) {
				this.couponName = couponName;
			}

			public String getConditionDesc() {
				return conditionDesc;
			}

			public void setConditionDesc(String conditionDesc) {
				this.conditionDesc = conditionDesc;
			}


			public String getDate() {
				return date;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public String getCouponRequire() {
				return couponRequire;
			}

			public void setCouponRequire(String couponRequire) {
				this.couponRequire = couponRequire;
			}

			public String getCouponUsingRangeMsg() {
				return couponUsingRangeMsg;
			}

			public void setCouponUsingRangeMsg(String couponUsingRangeMsg) {
				this.couponUsingRangeMsg = couponUsingRangeMsg;
			}

			public String getFaceValue() {
				return faceValue;
			}

			public void setFaceValue(String faceValue) {
				this.faceValue = faceValue;
			}

			public int getCouponType() {
				return couponType;
			}

			public void setCouponType(int couponType) {
				this.couponType = couponType;
			}

			public int getCouponShare() {
				return couponShare;
			}

			public void setCouponShare(int couponShare) {
				this.couponShare = couponShare;
			}

			public String getCouponTypeMsg() {
				return couponTypeMsg;
			}

			public void setCouponTypeMsg(String couponTypeMsg) {
				this.couponTypeMsg = couponTypeMsg;
			}

			public int getCosureState() {
				return cosureState;
			}

			public void setCosureState(int cosureState) {
				this.cosureState = cosureState;
			}

			public int getUserCouponId() {
				return userCouponId;
			}

			public void setUserCouponId(int userCouponId) {
				this.userCouponId = userCouponId;
			}

			public int getCouponId() {
				return couponId;
			}

			public void setCouponId(int couponId) {
				this.couponId = couponId;
			}

			public String getClassifyId() {
				return classifyId;
			}

			public void setClassifyId(String classifyId) {
				this.classifyId = classifyId;
			}
		}
	}
}
