package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/26.
 */

public class MineCollectionEntity {
    private String code;
    private String msg;
    private MineCollectionData data;

    public final String HTTP_OK ="200";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MineCollectionData getData() {
        return data;
    }

    public void setData(MineCollectionData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class MineCollectionData{
        private List<MineCollectionItem> productCollectionMaps;

        public List<MineCollectionItem> getProductCollectionMaps() {
            return productCollectionMaps;
        }

        public void setProductCollectionMaps(List<MineCollectionItem> productCollectionMaps) {
            this.productCollectionMaps = productCollectionMaps;
        }

        public class MineCollectionItem{
            private String commentNum;
            private String productListimg;
            private String collectionId;
            private String productOrginal;
            private String productHot;
            private String productName;
            private String productTimelimit;
            private String productCurrent;
            private String productId;

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getCollectionId() {
                return collectionId;
            }

            public void setCollectionId(String collectionId) {
                this.collectionId = collectionId;
            }

            public String getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(String commentNum) {
                this.commentNum = commentNum;
            }

            public String getProductCurrent() {
                return productCurrent;
            }

            public void setProductCurrent(String productCurrent) {
                this.productCurrent = productCurrent;
            }

            public String getProductHot() {
                return productHot;
            }

            public void setProductHot(String productHot) {
                this.productHot = productHot;
            }

            public String getProductListimg() {
                return productListimg;
            }

            public void setProductListimg(String productListimg) {
                this.productListimg = productListimg;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductOrginal() {
                return productOrginal;
            }

            public void setProductOrginal(String productOrginal) {
                this.productOrginal = productOrginal;
            }

            public String getProductTimelimit() {
                return productTimelimit;
            }

            public void setProductTimelimit(String productTimelimit) {
                this.productTimelimit = productTimelimit;
            }
        }
    }
}
