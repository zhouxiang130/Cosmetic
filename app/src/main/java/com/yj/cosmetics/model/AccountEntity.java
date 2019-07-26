package com.yj.cosmetics.model;

/**
 * Created by Suo on 2017/6/14.
 */

public class AccountEntity {

    private String code;
    private String msg;
    private AccountData data;

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

    public AccountData getData() {
        return data;
    }

    public void setData(AccountData data) {
        this.data = data;
    }

    public class AccountData{
        private String backmoney;
        private String alipay;
        private String code;
        private String userMoney;
        private String userId;
        private String alipayAccount;
        private String alipayName;
        private String alipayId;

        public String getAlipayAccount() {
            return alipayAccount;
        }

        public void setAlipayAccount(String alipayAccount) {
            this.alipayAccount = alipayAccount;
        }

        public String getAlipayId() {
            return alipayId;
        }

        public void setAlipayId(String alipayId) {
            this.alipayId = alipayId;
        }

        public String getAlipayName() {
            return alipayName;
        }

        public void setAlipayName(String alipayName) {
            this.alipayName = alipayName;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getBackmoney() {
            return backmoney;
        }

        public void setBackmoney(String backmoney) {
            this.backmoney = backmoney;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserMoney() {
            return userMoney;
        }

        public void setUserMoney(String userMoney) {
            this.userMoney = userMoney;
        }
    }
}
