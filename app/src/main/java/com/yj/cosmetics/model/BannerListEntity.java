package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/6 0006.
 */

public class BannerListEntity {
	/**
	 * code : 200
	 * data : {"bannerList":[{"banner_id":5,"banner_img":"/upload/banner/2018/06/28/f3c76b0c-ae69-479d-8193-e76b9fed6faa.jpg","product_id":72},{"banner_id":4,"banner_img":"/upload/banner/2018/06/28/bd957aba-8777-4489-bd89-9faf159a72ca.jpg","product_id":74}]}
	 * msg : 成功
	 */

	private String code;
	private DataBean data;
	private String msg;

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
		private List<BannerListBean> bannerList;

		public List<BannerListBean> getBannerList() {
			return bannerList;
		}

		public void setBannerList(List<BannerListBean> bannerList) {
			this.bannerList = bannerList;
		}

		public static class BannerListBean {
			/**
			 * banner_id : 5
			 * banner_img : /upload/banner/2018/06/28/f3c76b0c-ae69-479d-8193-e76b9fed6faa.jpg
			 * product_id : 72
			 */

			private int banner_id;
			private String banner_img;
			private int product_id;

			public int getBanner_id() {
				return banner_id;
			}

			public void setBanner_id(int banner_id) {
				this.banner_id = banner_id;
			}

			public String getBanner_img() {
				return banner_img;
			}

			public void setBanner_img(String banner_img) {
				this.banner_img = banner_img;
			}

			public int getProduct_id() {
				return product_id;
			}

			public void setProduct_id(int product_id) {
				this.product_id = product_id;
			}
		}
	}
}
