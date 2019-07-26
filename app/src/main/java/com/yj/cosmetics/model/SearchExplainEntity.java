package com.yj.cosmetics.model;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class SearchExplainEntity {


	/**
	 * data : {"intro":"暂不开放"}
	 * code : 200
	 * msg : 成功
	 */

	private DataBean data;
	private String code;
	private String msg;
	public final String HTTP_OK ="200";

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
		 * intro : 暂不开放
		 */

		private String intro;

		public String getIntro() {
			return intro;
		}

		public void setIntro(String intro) {
			this.intro = intro;
		}
	}
}
