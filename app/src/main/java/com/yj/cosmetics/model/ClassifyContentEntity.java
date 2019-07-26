package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/21.
 */

public class ClassifyContentEntity {
    private String code;
    private String msg;
    private ClassifyContentData data;
    public final String HTTP_OK ="200";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ClassifyContentData getData() {
        return data;
    }

    public void setData(ClassifyContentData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class ClassifyContentData{
        private List<ClassifyContentList> productClassifys;
        private ClassifyContentBanner banner;

        public ClassifyContentBanner getBanner() {
            return banner;
        }

        public void setBanner(ClassifyContentBanner banner) {
            this.banner = banner;
        }

        public List<ClassifyContentList> getProductClassifys() {
            return productClassifys;
        }

        public void setProductClassifys(List<ClassifyContentList> productClassifys) {
            this.productClassifys = productClassifys;
        }

        public class ClassifyContentList{
            private  String classify_id;
            private  String classify_name;
            private  String classify_img;

            public String getClassify_id() {
                return classify_id;
            }

            public void setClassify_id(String classify_id) {
                this.classify_id = classify_id;
            }

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
        }
        public class ClassifyContentBanner{
            private  String banner_id;
            private  String banner_url;
            private  String product_id;
            private  String banner_img;

            public String getBanner_id() {
                return banner_id;
            }

            public void setBanner_id(String banner_id) {
                this.banner_id = banner_id;
            }

            public String getBanner_img() {
                return banner_img;
            }

            public void setBanner_img(String banner_img) {
                this.banner_img = banner_img;
            }

            public String getBanner_url() {
                return banner_url;
            }

            public void setBanner_url(String banner_url) {
                this.banner_url = banner_url;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }
        }
    }
}
