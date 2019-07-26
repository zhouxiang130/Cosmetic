package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/27.
 */

public class ScoringDetailEntity {
	private String code;
	private String msg;
	private List<ScoringDetialData> data;

	public final String HTTP_OK = "200";


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<ScoringDetialData> getData() {
		return data;
	}

	public void setData(List<ScoringDetialData> data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public class ScoringDetialData {
		private String time;
		private String name;
		private String userName;
		private String score;
		private String type;
		private String state;
		private String applyId;
		private String addAndSub;

		public String getAddAndSub() {
			return addAndSub;
		}

		public void setAddAndSub(String addAndSub) {
			this.addAndSub = addAndSub;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userNamename) {
			this.userName = userNamename;
		}

		public String getApplyId() {
			return applyId;
		}

		public void setApplyId(String applyId) {
			this.applyId = applyId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
}