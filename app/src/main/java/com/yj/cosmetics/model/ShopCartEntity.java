package com.yj.cosmetics.model;

/**
 * Created by Suo on 2017/8/26.
 */

public class ShopCartEntity {
    private String msg;
    private String code;
    private ShopCartData data;
    public final String HTTP_OK = "200";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ShopCartData getData() {
        return data;
    }

    public void setData(ShopCartData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class ShopCartData{
        private String cartNumber;
        private String city;

        public String getCartNumber() {
            return cartNumber;
        }

        public void setCartNumber(String cartNumber) {
            this.cartNumber = cartNumber;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
