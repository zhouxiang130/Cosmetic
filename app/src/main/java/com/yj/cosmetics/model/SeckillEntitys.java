package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/3 0003.
 */

public class SeckillEntitys {
	/**
	 * msg : 成功
	 * code : 200
	 * data : {"timeLimitList":[],"img":"/upload/homeImg/2018/07/02/407f0ba8-2532-49f7-bfaa-8dcbeebefc18.jpg","newDate":"2018-07-03 19:57:55"}
	 */

	private String code;
	private String msg;
	private DataBean data;

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
		 * timeLimitList : []
		 * img : /upload/homeImg/2018/07/02/407f0ba8-2532-49f7-bfaa-8dcbeebefc18.jpg
		 * newDate : 2018-07-03 19:57:55
		 */

		private String img;
		private String newDate;
		private List<?> timeLimitList;

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getNewDate() {
			return newDate;
		}

		public void setNewDate(String newDate) {
			this.newDate = newDate;
		}

		public List<?> getTimeLimitList() {
			return timeLimitList;
		}

		public void setTimeLimitList(List<?> timeLimitList) {
			this.timeLimitList = timeLimitList;
		}
	}
}
