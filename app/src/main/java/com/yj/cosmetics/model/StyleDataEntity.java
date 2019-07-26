package com.yj.cosmetics.model;

/**
 * Created by Suo on 2017/8/30.
 */

public class StyleDataEntity {
    private String msg;
    private String code;
    public final String HTTP_OK = "200";
    private StyleDataData data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StyleDataData getData() {
        return data;
    }

    public void setData(StyleDataData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class StyleDataData{
        private String img;
        private String price;
        private String num;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
