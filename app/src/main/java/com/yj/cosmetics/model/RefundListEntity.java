package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/3 0003.
 */

public class RefundListEntity {
	/**
	 * data : [{"refundReason":"发货速度慢","refundId":"3"},{"refundReason":"包装损坏","refundId":"4"},{"refundReason":"与产品描述不符","refundId":"5"},{"refundReason":"产品质量问题","refundId":"6"},{"refundReason":"产品本身损坏","refundId":"7"},{"refundReason":"其他","refundId":"8"}]
	 * code : 200
	 * msg : 成功
	 */

	private String code;
	private String msg;
	private List<DataBean> data;
	public final String HTTP_OK ="200";
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

	public List<DataBean> getData() {
		return data;
	}

	public void setData(List<DataBean> data) {
		this.data = data;
	}

	public static class DataBean {
		/**
		 * refundReason : 发货速度慢
		 * refundId : 3
		 */

		private String refundReason;
		private String refundId;

		public String getRefundReason() {
			return refundReason;
		}

		public void setRefundReason(String refundReason) {
			this.refundReason = refundReason;
		}

		public String getRefundId() {
			return refundId;
		}

		public void setRefundId(String refundId) {
			this.refundId = refundId;
		}
	}
}
