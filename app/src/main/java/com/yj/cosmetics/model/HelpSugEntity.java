package com.yj.cosmetics.model;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yulu on 2018/6/24.
 */

public class HelpSugEntity {

	private List<JudgeGoodsItem> data;

	public List<JudgeGoodsItem> getData() {
		return data;
	}

	public void setData(List<JudgeGoodsItem> data) {
		this.data = data;
	}

	public class JudgeGoodsItem {

		public int JudgeType;
		public String judgeContent;
		private List<Uri> mSelected;
		private ArrayList<File> mImage;
		private String detailId;
		private String pid;


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
