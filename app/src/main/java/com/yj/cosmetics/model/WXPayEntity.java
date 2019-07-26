package com.yj.cosmetics.model;

/**
 * Created by Suo on 2017/9/12.
 */

public class WXPayEntity {
    private String msg;
    private String code;
    private WXPayData data;

    public WXPayEntity(String code, String msg) {
        setCode(code);
        setMsg(msg);
    }

    public final String HTTP_OK = "200";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public WXPayData getData() {
        return data;
    }

    public void setData(WXPayData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class WXPayData {

        private String orderId;
        private String orderNum;
        private WXPayJson appPayJson;

        public WXPayJson getAppPayJson() {
            return appPayJson;
        }

        public void setAppPayJson(WXPayJson appPayJson) {
            this.appPayJson = appPayJson;
        }

        public class WXPayJson {
            private String timestamp;
            private String noncestr;
            private String partnerid;
            private String prepayid;
            private String appid;
            private String sign;

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }


        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }


        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }


    }
}
