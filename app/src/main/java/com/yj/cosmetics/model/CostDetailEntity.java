package com.yj.cosmetics.model;

/**
 * Created by Suo on 2018/3/28.
 */

public class CostDetailEntity {
    private String code;
    private String msg;
    private CostDetialData data;

    public final String HTTP_OK ="200";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CostDetialData getData() {
        return data;
    }

    public void setData(CostDetialData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class CostDetialData{
        private String cashmoney;
        private String alipay;
        private String insertTime;
        private String reason;
        private String name;
        private String alipayName;
        private String cashState;
        private String consumeMoney;
        private String consumeState;
        private String showReject;
        private String orderNum;

        public String getConsumeMoney() {
            return consumeMoney;
        }

        public void setConsumeMoney(String consumeMoney) {
            this.consumeMoney = consumeMoney;
        }

        public String getConsumeState() {
            return consumeState;
        }

        public void setConsumeState(String consumeState) {
            this.consumeState = consumeState;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getShowReject() {
            return showReject;
        }

        public void setShowReject(String showReject) {
            this.showReject = showReject;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getAlipayName() {
            return alipayName;
        }

        public void setAlipayName(String alipayName) {
            this.alipayName = alipayName;
        }

        public String getCashmoney() {
            return cashmoney;
        }

        public void setCashmoney(String cashmoney) {
            this.cashmoney = cashmoney;
        }

        public String getCashState() {
            return cashState;
        }

        public void setCashState(String cashState) {
            this.cashState = cashState;
        }

        public String getInsertTime() {
            return insertTime;
        }

        public void setInsertTime(String insertTime) {
            this.insertTime = insertTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}