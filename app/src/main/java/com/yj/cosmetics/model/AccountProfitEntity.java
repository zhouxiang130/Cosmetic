package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/27.
 */

public class AccountProfitEntity {

    private String code;
    private String msg;
    private List<AccountProfitData> data;
    public final String HTTP_OK ="200";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<AccountProfitData> getData() {
        return data;
    }

    public void setData(List<AccountProfitData> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class AccountProfitData{
        private String user_name;
        private String order_num;
        private String insert_time;
        private String user_headImg;
        private String user_id;
        private String back_mode;
        private String backmoney;
        private String productName;

        public String getBack_mode() {
            return back_mode;
        }

        public void setBack_mode(String back_mode) {
            this.back_mode = back_mode;
        }

        public String getBackmoney() {
            return backmoney;
        }

        public void setBackmoney(String backmoney) {
            this.backmoney = backmoney;
        }

        public String getInsert_time() {
            return insert_time;
        }

        public void setInsert_time(String insert_time) {
            this.insert_time = insert_time;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getUser_headImg() {
            return user_headImg;
        }

        public void setUser_headImg(String user_headImg) {
            this.user_headImg = user_headImg;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}