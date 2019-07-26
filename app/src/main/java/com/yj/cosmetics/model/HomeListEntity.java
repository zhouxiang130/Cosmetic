package com.yj.cosmetics.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Suo on 2018/3/20.
 */

public class HomeListEntity {

    private String code;
    private String msg;
    private HomeListData data;

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

    public HomeListData getData() {
        return data;
    }

    public void setData(HomeListData data) {
        this.data = data;
    }

    public class HomeListData{
        private List<HomeListItem> products;

        public List<HomeListItem> getProducts() {
            return products;
        }

        public void setProducts(List<HomeListItem> products) {
            this.products = products;
        }

        public class HomeListItem implements Serializable {
            private String classify_id;
            private String product_current;
            private String product_id;
            private String product_orginal;
            private String product_name;
            private String product_abstract;
            private String product_listImg;

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
