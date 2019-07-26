package com.yj.cosmetics.model;

/**
 * Created by Suo on 2018/3/29.
 */

public class UpdateEntity {
    private String code;
    private String msg;
    private UpdateData data;

    public final String HTTP_OK ="200";


    public UpdateEntity(String code, String msg) {
        setCode(code);
        setMsg(msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UpdateData getData() {
        return data;
    }

    public void setData(UpdateData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class UpdateData{
        private String appId;
        private String appLink;
        private String appVersion;
        private String appDesc;
        private String appForce;
        private String appLen;

        public String getAppLen() {
            return appLen;
        }

        public void setAppLen(String appLen) {
            this.appLen = appLen;
        }

        public String getAppDesc() {
            return appDesc;
        }

        public void setAppDesc(String appDesc) {
            this.appDesc = appDesc;
        }

        public String getAppForce() {
            return appForce;
        }

        public void setAppForce(String appForce) {
            this.appForce = appForce;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppLink() {
            return appLink;
        }

        public void setAppLink(String appLink) {
            this.appLink = appLink;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }
    }
}





