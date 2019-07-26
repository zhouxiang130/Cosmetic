package com.yj.cosmetics.model;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yulu on 2018/6/24.
 */

public class RefundDetailEntity {

	private List<RefundDetailItem> data;

	public List<RefundDetailItem> getData() {
		return data;
	}

	public void setData(List<RefundDetailItem> data) {
		this.data = data;
	}

	public class RefundDetailItem {

		public int JudgeType;
		public String judgeContent;
		private List<Uri> mSelected;
		private ArrayList<File> mImage;
		private String detailId;
		private String pid;
		private String price;
		private String reason;
		private String phone;
		private int GoodStatus;
		private String Cause;

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getCause() {
			return Cause;
		}

		public void setCause(String cause) {
			Cause = cause;
		}

		public int getGoodStatus() {
			return GoodStatus;
		}

		public void setGoodStatus(int goodStatus) {
			GoodStatus = goodStatus;
		}

		public List<Uri> getmSelected() {
			return mSelected;
		}

		public void setmSelected(List<Uri> mSelected) {
			this.mSelected = mSelected;
		}

		public ArrayList<File> getmImage() {
			return mImage;
		}

		public void setmImage(ArrayList<File> mImage) {
			this.mImage = mImage;
		}

		public String getJudgeContent() {
			return judgeContent;
		}

		public void setJudgeContent(String judgeContent) {
			this.judgeContent = judgeContent;
		}

		public void setDetailId(String detailId) {
			this.detailId = detailId;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public int getJudgeType() {

			return JudgeType;
		}

		public void setJudgeType(int judgeType) {
			JudgeType = judgeType;
		}

		public String getDetailId() {
			return detailId;
		}

		public String getPid() {
			return pid;
		}


	}


}
