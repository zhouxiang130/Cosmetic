package com.yj.cosmetics.model;

/**
 * Created by Administrator on 2018/8/4 0004.
 */

public class StoreListAddressEntity {

	/**
	 * msg : 成功
	 * code : 200
	 * data : {"code":200,"address":"绿地之窗","location":"113.770132,34.760545","info":"OK"}
	 */

	private String msg;
	private String code;
	private DataBean data;
	public final String HTTP_OK ="200";

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
		 * code : 200
		 * address : 绿地之窗
		 * location : 113.770132,34.760545
		 * info : OK
		 */

		private int code;
		private String address;
		private String location;
		private String info;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}
	}
}
