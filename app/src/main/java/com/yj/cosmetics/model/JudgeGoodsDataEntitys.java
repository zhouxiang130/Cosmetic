package com.yj.cosmetics.model;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suo on 2017/9/14.
 */
public class JudgeGoodsDataEntitys {
	private String msg;
	private String code;
	private List<JudgeGoodsItem> data;

	public final String HTTP_OK = "200";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<JudgeGoodsItem> getData() {
		return data;
	}

	public void setData(List<JudgeGoodsItem> data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public class JudgeGoodsItem {
		private String comment;
		private String stars;
		private String pid;
		private String detailId;
		private String pimg;
		private String money;
		private String pname;
		private String nums;
		private String psku;

		public String JudgeType;
		public String judgeContent;
		private List<Uri> mSelected;
		private ArrayList<File> mImage;


		public String getJudgeContent() {
			return judgeContent;
		}

		public void setJudgeContent(String judgeContent) {
			this.judgeContent = judgeContent;
		}

		public String getJudgeType() {

			return JudgeType;
		}

		public void setJudgeType(String judgeType) {
			JudgeType = judgeType;
		}


		public String getDetailId() {
			return detailId;
		}

		public void setDetailId(String detailId) {
			this.detailId = detailId;
		}

		public String getMoney() {
			return money;
		}

		public void setMoney(String money) {
			this.money = money;
		}

		public String getNums() {
			return nums;
		}

		public void setNums(String nums) {
			this.nums = nums;
		}

		public String getPimg() {
			return pimg;
		}

		public void setPimg(String pimg) {
			this.pimg = pimg;
		}

		public String getPname() {
			return pname;
		}

		public void setPname(String pname) {
			this.pname = pname;
		}

		public String getPsku() {
			return psku;
		}

		public void setPsku(String psku) {
			this.psku = psku;
		}

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public List<Uri> getmSelected() {
			return mSelected;
		}

		public void setmSelected(List<Uri> mSelected) {
			this.mSelected = mSelected;
		}

		public String getStars() {
			return stars;
		}

		public void setStars(String stars) {
			this.stars = stars;
		}

		public ArrayList<File> getmImage() {
			return mImage;
		}

		public void setmImage(ArrayList<File> mImage) {
			this.mImage = mImage;
		}
	}

}
