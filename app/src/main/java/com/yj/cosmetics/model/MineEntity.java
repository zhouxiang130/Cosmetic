package com.yj.cosmetics.model;

/**
 * Created by Suo on 2018/3/23.
 */

public class MineEntity {
    private String code;
    private String msg;
    private MineData data;
    public final String HTTP_OK ="200";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MineData getData() {
        return data;
    }

    public void setData(MineData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class MineData{
        private String userMoney;
        private String userScore;
        private String serviceTel;
        private String orderpay;
        private String ordersend;
        private String orderget;
        private String orderjudge;
        private String upintegral;
        private String  userType;

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getUpintegral() {
            return upintegral;
        }

        public void setUpintegral(String upintegral) {
            this.upintegral = upintegral;
        }


        public String getOrderget() {
            return orderget;
        }

        public void setOrderget(String orderget) {
            this.orderget = orderget;
        }

        public String getOrderjudge() {
            return orderjudge;
        }

        public void setOrderjudge(String orderjudge) {
            this.orderjudge = orderjudge;
        }

        public String getOrderpay() {
            return orderpay;
        }

        public void setOrderpay(String orderpay) {
            this.orderpay = orderpay;
        }

        public String getOrdersend() {
            return ordersend;
        }

        public void setOrdersend(String ordersend) {
            this.ordersend = ordersend;
        }

        public String getServiceTel() {
            return serviceTel;
        }

        public void setServiceTel(String serviceTel) {
            this.serviceTel = serviceTel;
        }

        public String getUserMoney() {
            return userMoney;
        }

        public void setUserMoney(String userMoney) {
            this.userMoney = userMoney;
        }

        public String getUserScore() {
            return userScore;
        }

        public void setUserScore(String userScore) {
            this.userScore = userScore;
        }
    }
}
