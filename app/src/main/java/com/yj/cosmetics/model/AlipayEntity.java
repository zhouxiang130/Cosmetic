package com.yj.cosmetics.model;

/**
 * Created by Suo on 2017/9/13.
 */

public class AlipayEntity {
    private String msg;
    private String code;
    private AlipayData data;

    public AlipayEntity(String code, String msg) {
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

    public AlipayData getData() {
        return data;
    }

    public void setData(AlipayData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class AlipayData {
        private AlipayJson appPayJson;
        private String orderId;
        private String orderNum;
        private String code;
        private String orderString;

        public String getOrderString() {
            return orderString;
        }

        public void setOrderString(String orderString) {
            this.orderString = orderString;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }


        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public AlipayJson getAppPayJson() {
            return appPayJson;
        }

        public void setAppPayJson(AlipayJson appPayJson) {
            this.appPayJson = appPayJson;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public class AlipayJson{
            private String body;
            private String subject;
            private String notify_url;
            private String out_trade_no;
            private String return_url;
            private String _input_charset;
            private String exter_invoke_ip;
            private String total_fee;
            private String service;
            private String partner;
            private String seller_email;
            private String anti_phishing_key;
            private String payment_type;
            private String show_url;
            private String APPID;
            private String SIGNTYPE;
            private String timeStamp;
            private String RSA_PRIVATE_KEY;
            private String orderNum;

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }


            public String getRSA_PRIVATE_KEY() {
                return RSA_PRIVATE_KEY;
            }

            public void setRSA_PRIVATE_KEY(String RSA_PRIVATE_KEY) {
                this.RSA_PRIVATE_KEY = RSA_PRIVATE_KEY;
            }

            public String getTimeStamp() {
                return timeStamp;
            }

            public void setTimeStamp(String timeStamp) {
                this.timeStamp = timeStamp;
            }


            public String getSIGNTYPE() {
                return SIGNTYPE;
            }

            public void setSIGNTYPE(String SIGNTYPE) {
                this.SIGNTYPE = SIGNTYPE;
            }

            public String getAPPID() {
                return APPID;
            }

            public void setAPPID(String APPID) {
                this.APPID = APPID;
            }

            public String get_input_charset() {
                return _input_charset;
            }

            public void set_input_charset(String _input_charset) {
                this._input_charset = _input_charset;
            }

            public String getAnti_phishing_key() {
                return anti_phishing_key;
            }

            public void setAnti_phishing_key(String anti_phishing_key) {
                this.anti_phishing_key = anti_phishing_key;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public String getExter_invoke_ip() {
                return exter_invoke_ip;
            }

            public void setExter_invoke_ip(String exter_invoke_ip) {
                this.exter_invoke_ip = exter_invoke_ip;
            }

            public String getNotify_url() {
                return notify_url;
            }

            public void setNotify_url(String notify_url) {
                this.notify_url = notify_url;
            }

            public String getOut_trade_no() {
                return out_trade_no;
            }

            public void setOut_trade_no(String out_trade_no) {
                this.out_trade_no = out_trade_no;
            }

            public String getPartner() {
                return partner;
            }

            public void setPartner(String partner) {
                this.partner = partner;
            }

            public String getPayment_type() {
                return payment_type;
            }

            public void setPayment_type(String payment_type) {
                this.payment_type = payment_type;
            }

            public String getReturn_url() {
                return return_url;
            }

            public void setReturn_url(String return_url) {
                this.return_url = return_url;
            }

            public String getSeller_email() {
                return seller_email;
            }

            public void setSeller_email(String seller_email) {
                this.seller_email = seller_email;
            }

            public String getService() {
                return service;
            }

            public void setService(String service) {
                this.service = service;
            }

            public String getShow_url() {
                return show_url;
            }

            public void setShow_url(String show_url) {
                this.show_url = show_url;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(String total_fee) {
                this.total_fee = total_fee;
            }
        }

    }
}
