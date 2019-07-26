package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/6/22 0022.
 */

public class SignInEntity {
	/**
	 * msg : 成功
	 * code : 200
	 * data : {"signList":[{"val":"3.0","intro":"购买抵现","name":"余额","type":"5","content":"个人中心-我的账户查看","isSign":"0"},{"val":"3","intro":"额度提升","name":"积分","type":"4","content":"个人中心-我的账户查看","isSign":"0"},{"val":"888.0","intro":"购买抵现","name":"余额","type":"5","content":"个人中心-我的账户查看","isSign":"0"},{"val":"7","intro":"额度提升","name":"积分","type":"4","content":"个人中心-我的账户查看","isSign":"0"},{"val":"7.0","intro":"购买抵现","name":"余额","type":"5","content":"个人中心-我的账户查看","isSign":"0"},{"val":"150","intro":"任意金额","name":"超大代金券","type":"3","content":"个人中心-我的优惠券查看","isSign":"0"},{"val":"100","intro":"满150可用","name":"代金券","type":"2","content":"个人中心-我的优惠券查看","isSign":"0"}],"signDay":{"lastTime":"","days":"0","userId":"18"}}
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
		 * signList : [{"val":"3.0","intro":"购买抵现","name":"余额","type":"5","content":"个人中心-我的账户查看","isSign":"0"},{"val":"3","intro":"额度提升","name":"积分","type":"4","content":"个人中心-我的账户查看","isSign":"0"},{"val":"888.0","intro":"购买抵现","name":"余额","type":"5","content":"个人中心-我的账户查看","isSign":"0"},{"val":"7","intro":"额度提升","name":"积分","type":"4","content":"个人中心-我的账户查看","isSign":"0"},{"val":"7.0","intro":"购买抵现","name":"余额","type":"5","content":"个人中心-我的账户查看","isSign":"0"},{"val":"150","intro":"任意金额","name":"超大代金券","type":"3","content":"个人中心-我的优惠券查看","isSign":"0"},{"val":"100","intro":"满150可用","name":"代金券","type":"2","content":"个人中心-我的优惠券查看","isSign":"0"}]
		 * signDay : {"lastTime":"","days":"0","userId":"18"}
		 */

		private SignDayBean signDay;
		private List<SignListBean> signList;
		private int userScore;

		public int getUserScore() {
			return userScore;
		}

		public void setUserScore(int userScore) {
			this.userScore = userScore;
		}

		public SignDayBean getSignDay() {
			return signDay;
		}

		public void setSignDay(SignDayBean signDay) {
			this.signDay = signDay;
		}

		public List<SignListBean> getSignList() {
			return signList;
		}

		public void setSignList(List<SignListBean> signList) {
			this.signList = signList;
		}

		public static class SignDayBean {
			/**
			 * lastTime :
			 * days : 0
			 * userId : 18
			 */

			private String lastTime;
			private String days;
			private String userId;
			private String isSign;
			private String signRule;

			public String getSignRule() {
				return signRule;
			}

			public void setSignRule(String signRule) {
				this.signRule = signRule;
			}

			public String getIsSign() {
				return isSign;
			}

			public void setIsSign(String isSign) {
				this.isSign = isSign;
			}
			public String getLastTime() {
				return lastTime;
			}

			public void setLastTime(String lastTime) {
				this.lastTime = lastTime;
			}

			public String getDays() {
				return days;
			}

			public void setDays(String days) {
				this.days = days;
			}

			public String getUserId() {
				return userId;
			}

			public void setUserId(String userId) {
				this.userId = userId;
			}
		}

		public static class SignListBean {
			/**
			 * val : 3.0
			 * intro : 购买抵现
			 * name : 余额
			 * type : 5
			 * content : 个人中心-我的账户查看
			 * isSign : 0
			 */

			private String val;
			private String intro;
			private String name;
			private String type;
			private String content;
			private String isSign;

			public String getVal() {
				return val;
			}

			public void setVal(String val) {
				this.val = val;
			}

			public String getIntro() {
				return intro;
			}

			public void setIntro(String intro) {
				this.intro = intro;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public String getIsSign() {
				return isSign;
			}

			public void setIsSign(String isSign) {
				this.isSign = isSign;
			}
		}
	}
}
