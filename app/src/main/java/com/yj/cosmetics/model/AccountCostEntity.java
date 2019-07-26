package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/27.
 */

public class AccountCostEntity {
    private String code;
    private String msg;
    private List<AccountCostData> data;

    public final String HTTP_OK ="200";

    public List<AccountCostData> getData() {
        return data;
    }

    public void setData(List<AccountCostData> data) {
        this.data = data;
    }

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

    public class AccountCostData{
            private String cashmoney;
            private String cashmoneyId;
            private String name;
            private String type;
            private String time;
            private String account;
            private String alipayAccount;
            private String alipayName;
            private String consumeId;
            private String consumeMoney;
            private String consumeState;
            private String deductibleMode;
            private String orderId;
            private String showReject;
            private String userId;
            private String insertTime;
            private String releaseTime;
            private String cid;
            private String cashState;
            private String userType;
            private String productName;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getAlipayAccount() {
                return alipayAccount;
            }

            public void setAlipayAccount(String alipayAccount) {
                this.alipayAccount = alipayAccount;
            }

            public String getAlipayName() {
                return alipayName;
            }

            public void setAlipayName(String alipayName) {
                this.alipayName = alipayName;
            }

            public String getCashState() {
                return cashState;
            }

            public void setCashState(String cashState) {
                this.cashState = cashState;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getConsumeId() {
                return consumeId;
            }

            public void setConsumeId(String consumeId) {
                this.consumeId = consumeId;
            }

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

            public String getDeductibleMode() {
                return deductibleMode;
            }

            public void setDeductibleMode(String deductibleMode) {
                this.deductibleMode = deductibleMode;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getReleaseTime() {
                return releaseTime;
            }

            public void setReleaseTime(String releaseTime) {
                this.releaseTime = releaseTime;
            }

            public String getShowReject() {
                return showReject;
            }

            public void setShowReject(String showReject) {
                this.showReject = showReject;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getCashmoney() {
                return cashmoney;
            }

            public void setCashmoney(String cashmoney) {
                this.cashmoney = cashmoney;
            }

            public String getCashmoneyId() {
                return cashmoneyId;
            }

            public void setCashmoneyId(String cashmoneyId) {
                this.cashmoneyId = cashmoneyId;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
    }
}

