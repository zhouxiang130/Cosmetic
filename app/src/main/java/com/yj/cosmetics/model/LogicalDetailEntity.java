package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2017/9/7.
 */

public class LogicalDetailEntity {
	private String msg;
	private String code;
	private LogicalDetialData data;
	public final String HTTP_OK = "200";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LogicalDetialData getData() {
		return data;
	}

	public void setData(LogicalDetialData data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public class LogicalDetialData {
		private String nu;
		private String orderState;
		private String ischeck;
		private String condition;
		private String orderShipstyle;
		private String img;
		private String state;
		private String tel;
		private String address;
		private String com;
		private String message;
		private String result;
		private String returnCode;

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getReturnCode() {
			return returnCode;
		}

		public void setReturnCode(String returnCode) {
			this.returnCode = returnCode;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		private List<LogicalDetialItem> data;

		public String getCom() {
			return com;
		}

		public void setCom(String com) {
			this.com = com;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getCondition() {
			return condition;
		}

		public void setCondition(String condition) {
			this.condition = condition;
		}

		public List<LogicalDetialItem> getData() {
			return data;
		}

		public void setData(List<LogicalDetialItem> data) {
			this.data = data;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getIscheck() {
			return ischeck;
		}

		public void setIscheck(String ischeck) {
			this.ischeck = ischeck;
		}

		public String getNu() {
			return nu;
		}

		public void setNu(String nu) {
			this.nu = nu;
		}

		public String getOrderShipstyle() {
			return orderShipstyle;
		}

		public void setOrderShipstyle(String orderShipstyle) {
			this.orderShipstyle = orderShipstyle;
		}

		public String getOrderState() {
			return orderState;
		}

		public void setOrderState(String orderState) {
			this.orderState = orderState;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public class LogicalDetialItem {
			private String time;
			private String context;
			private String ftime;

			public String getContext() {
				return context;
			}

			public void setContext(String context) {
				this.context = context;
			}

			public String getFtime() {
				return ftime;
			}

			public void setFtime(String ftime) {
				this.ftime = ftime;
			}

			public String getTime() {
				return time;
			}

			public void setTime(String time) {
				this.time = time;
			}
		}

	}
}