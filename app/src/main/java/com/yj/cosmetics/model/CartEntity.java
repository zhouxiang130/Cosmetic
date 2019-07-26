package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/23.
 */

public class CartEntity {
    private String code;
    private String msg;
    private CartData data;
    public final String HTTP_OK ="200";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CartData getData() {
        return data;
    }

    public void setData(CartData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class CartData{
        private String systemValue;
        private List<CartItem> proCarts;

        public List<CartItem> getProCarts() {
            return proCarts;
        }

        public void setProCarts(List<CartItem> proCarts) {
            this.proCarts = proCarts;
        }

        public String getSystemValue() {
            return systemValue;
        }

        public void setSystemValue(String systemValue) {
            this.systemValue = systemValue;
        }

        public class CartItem{
            private String proId;
            private String num;
            private String skuNum;
            private String skuPropertiesName;
            private String skuPrice;
            private String proName;
            private String cartId;
            private String proImg;
            private String productAbstract;
            private boolean isChoosed;

            public String getCartId() {
                return cartId;
            }

            public void setCartId(String cartId) {
                this.cartId = cartId;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getProductAbstract() {
                return productAbstract;
            }

            public void setProductAbstract(String productAbstract) {
                this.productAbstract = productAbstract;
            }

            public boolean isChoosed() {
                return isChoosed;
            }

            public void setChoosed(boolean choosed) {
                isChoosed = choosed;
            }

            public String getProId() {
                return proId;
            }

            public void setProId(String proId) {
                this.proId = proId;
            }

            public String getProImg() {
                return proImg;
            }

            public void setProImg(String proImg) {
                this.proImg = proImg;
            }

            public String getProName() {
                return proName;
            }

            public void setProName(String proName) {
                this.proName = proName;
            }

            public String getSkuNum() {
                return skuNum;
            }

            public void setSkuNum(String skuNum) {
                this.skuNum = skuNum;
            }

            public String getSkuPrice() {
                return skuPrice;
            }

            public void setSkuPrice(String skuPrice) {
                this.skuPrice = skuPrice;
            }

            public String getSkuPropertiesName() {
                return skuPropertiesName;
            }

            public void setSkuPropertiesName(String skuPropertiesName) {
                this.skuPropertiesName = skuPropertiesName;
            }
        }
    }
}