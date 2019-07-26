package com.yj.cosmetics.model;

/**
 * Created by Suo on 2018/3/19.
 */

public class UserInfoEntity {
    private String msg;
    private String code;
    public final String HTTP_OK = "200";
    private UserInfoData data;

    public UserInfoEntity(String code, String msg) {
        setCode(code);
        setMsg(msg);
    }

    public UserInfoEntity() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserInfoData getData() {
        return data;
    }

    public void setData(UserInfoData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class UserInfoData{

        private String headImg;
        private String userHeadimg;
        private String userPhone;
        private String userName;
        private String userId;
        private String qqOpenId;
        private String wechatId;
        private String alipayAccount;
        private String alipayName;
        private String agentNumber;
        private String phone;
        private String name;
        private String qqOpenid;
        private String img;
        private String wxOpenid;
        private String id;

        public String getUserHeadimg() {
            return userHeadimg;
        }

        public void setUserHeadimg(String userHeadimg) {
            this.userHeadimg = userHeadimg;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getQqOpenid() {
            return qqOpenid;
        }

        public void setQqOpenid(String qqOpenid) {
            this.qqOpenid = qqOpenid;
        }

        public String getWxOpenid() {
            return wxOpenid;
        }

        public void setWxOpenid(String wxOpenid) {
            this.wxOpenid = wxOpenid;
        }

        public String getAgentNumber() {
            return agentNumber;
        }

        public void setAgentNumber(String agentNumber) {
            this.agentNumber = agentNumber;
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

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getQqOpenId() {
            return qqOpenId;
        }

        public void setQqOpenId(String qqOpenId) {
            this.qqOpenId = qqOpenId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getWechatId() {
            return wechatId;
        }

        public void setWechatId(String wechatId) {
            this.wechatId = wechatId;
        }
    }
}
