package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/4 0004.
 */

public class StoreListEntity {
	/**
	 * msg : 成功
	 * code : 200
	 * data : {"productClassifyList":[{"classify_img":"/upload/productClassify/2018/07/24/ae153435-00ff-4dd0-b05c-15d5ebebf950.jpg","classify_name":"手表","classify_id":72},{"classify_img":"/upload/productClassify/2018/05/31/71c597dc-5dad-4a65-918c-f2ce640a41c2.png","classify_name":"坚果类","classify_id":34},{"classify_img":"/upload/productClassify/2018/03/31/38c00a4a-bdfa-4c26-aedf-268961546aee.png","classify_name":"应季食材","classify_id":21},{"classify_img":"/upload/productClassify/2018/03/31/226b2e67-4f76-4bb2-9f6b-fd5506628bfd.png","classify_name":"美容护肤","classify_id":20},{"classify_img":"/upload/productClassify/2018/03/31/034d45eb-3098-4398-9080-52f9477ec036.png","classify_name":"生鲜水果","classify_id":19},{"classify_img":"/upload/productClassify/2018/03/31/226b2e67-4f76-4bb2-9f6b-fd5506628bfd.png","classify_name":"进口好货","classify_id":18},{"classify_img":"/upload/productClassify/2018/03/31/38c00a4a-bdfa-4c26-aedf-268961546aee.png","classify_name":"家清家居","classify_id":17},{"classify_img":"/upload/productClassify/2018/03/31/034d45eb-3098-4398-9080-52f9477ec036.png","classify_name":"个人护理","classify_id":16},{"classify_img":"/upload/productClassify/2018/03/31/38c00a4a-bdfa-4c26-aedf-268961546aee.png","classify_name":"家用电器","classify_id":15},{"classify_img":"/upload/productClassify/2018/03/31/034d45eb-3098-4398-9080-52f9477ec036.png","classify_name":"奶品饮水","classify_id":14},{"classify_img":"/upload/productClassify/2018/06/26/4d6a766f-0bda-45dc-b371-602b9fea3c9c.jpg","classify_name":"熟食类","classify_id":61},{"classify_img":"/upload/productClassify/2018/03/31/226b2e67-4f76-4bb2-9f6b-fd5506628bfd.png","classify_name":"中外名酒","classify_id":12},{"classify_img":"/upload/productClassify/2018/06/26/583dae8a-86f3-4459-845d-a714b494400a.jpg","classify_name":"电子类","classify_id":11},{"classify_img":"/weChat/img/cherry.png","classify_name":"衣服类","classify_id":8},{"classify_img":"/upload/productClassify/2018/06/19/8b0627af-e09f-4aad-b1de-bc12edc775dd.png","classify_name":"家居","classify_id":39},{"classify_img":"/upload/productClassify/2018/03/31/38c00a4a-bdfa-4c26-aedf-268961546aee.png","classify_name":"休闲零食","classify_id":13},{"classify_img":"/upload/productClassify/2018/06/27/1e07f160-7b1d-4d3a-aca9-9de411ae6f0d.jpg","classify_name":"美容美妆","classify_id":65},{"classify_img":"/upload/productClassify/2018/06/26/383b70ec-9e93-4424-a72e-a45c187faf62.png","classify_name":"电子产品","classify_id":37},{"classify_img":"/upload/productClassify/2018/05/30/69736b06-9630-424d-b47b-a31e782256e1.png","classify_name":"进口水果","classify_id":32}]}
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
		private List<ProductClassifyListBean> productClassifyList;

		public List<ProductClassifyListBean> getProductClassifyList() {
			return productClassifyList;
		}

		public void setProductClassifyList(List<ProductClassifyListBean> productClassifyList) {
			this.productClassifyList = productClassifyList;
		}

		public static class ProductClassifyListBean {
			/**
			 * classify_img : /upload/productClassify/2018/07/24/ae153435-00ff-4dd0-b05c-15d5ebebf950.jpg
			 * classify_name : 手表
			 * classify_id : 72
			 */

			private String classify_img;
			private String classify_name;
			private String classify_id;

			public String getClassify_img() {
				return classify_img;
			}

			public void setClassify_img(String classify_img) {
				this.classify_img = classify_img;
			}

			public String getClassify_name() {
				return classify_name;
			}

			public void setClassify_name(String classify_name) {
				this.classify_name = classify_name;
			}

			public String getClassify_id() {
				return classify_id;
			}

			public void setClassify_id(String classify_id) {
				this.classify_id = classify_id;
			}
		}
	}
}
