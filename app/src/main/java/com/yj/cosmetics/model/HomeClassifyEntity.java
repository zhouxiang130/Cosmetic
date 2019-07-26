package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/20.
 */

public class HomeClassifyEntity {

    private String code;
    private String msg;
    private HomeClassifyData data;

    public final String HTTP_OK = "200";


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

    public HomeClassifyData getData() {
        return data;
    }

    public void setData(HomeClassifyData data) {
        this.data = data;
    }

    public class HomeClassifyData {
        private List<HomeClassifyBanner> bannerList;
        private List<List<HomeClassifyList>> productArray;
        private List<HomeClassifySon> productClassifys;

        public List<HomeClassifyBanner> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<HomeClassifyBanner> bannerList) {
            this.bannerList = bannerList;
        }

        public List<List<HomeClassifyList>> getProductArray() {
            return productArray;
        }

        public void setProductArray(List<List<HomeClassifyList>> productArray) {
            this.productArray = productArray;
        }

        public List<HomeClassifySon> getProductClassifys() {
            return productClassifys;
        }

        public void setProductClassifys(List<HomeClassifySon> productClassifys) {
            this.productClassifys = productClassifys;
        }

        public class HomeClassifyBanner {
            private String banner_id;
            private String banner_url;
            private String product_id;
            private String banner_img;

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
        public class HomeClassifySon{
            private String classify_id;
            private String classify_name;
            private String classify_img;

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

        public class HomeClassifyList{
            private String classify_id;
            private String product_current;
            private String product_id;
            private String product_orginal;
            private String product_name;
            private String product_abstract;
            private String product_listImg;
            private String classifyName;

            public String getClassifyName() {
                return classifyName;
            }

            public void setClassifyName(String classifyName) {
                this.classifyName = classifyName;
            }

            public String getClassify_id() {
                return classify_id;
            }

            public void setClassify_id(String classify_id) {
                this.classify_id = classify_id;
            }

            public String getProduct_abstract() {
                return product_abstract;
            }

            public void setProduct_abstract(String product_abstract) {
                this.product_abstract = product_abstract;
            }

            public String getProduct_current() {
                return product_current;
            }

            public void setProduct_current(String product_current) {
                this.product_current = product_current;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getProduct_listImg() {
                return product_listImg;
            }

            public void setProduct_listImg(String product_listImg) {
                this.product_listImg = product_listImg;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getProduct_orginal() {
                return product_orginal;
            }

            public void setProduct_orginal(String product_orginal) {
                this.product_orginal = product_orginal;
            }
        }

    }
}
