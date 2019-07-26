package com.yj.cosmetics.model;

/**
 * Created by Suo on 2017/6/14.
 */

public class NormalEntitys {

	/**
	 * code : 200
	 * data : {"code":200,"msg":"支付成功"}
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
		 * code : 200
		 * msg : 支付成功
		 */

		private int code;
		private String msg;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}
