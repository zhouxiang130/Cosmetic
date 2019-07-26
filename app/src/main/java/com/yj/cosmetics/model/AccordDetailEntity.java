package com.yj.cosmetics.model;

/**
 * Created by Suo on 2018/3/28.
 */

public class AccordDetailEntity {

    private String code;
    private String msg;
    private AccordDetialData data;

    public final String HTTP_OK ="200";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AccordDetialData getData() {
        return data;
    }

    public void setData(AccordDetialData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class AccordDetialData{
        private String time;
        private AccordDetialContent consume;

        public AccordDetialContent getConsume() {
            return consume;
        }

        public void setConsume(AccordDetialContent consume) {
            this.consume = consume;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public class AccordDetialContent{
            private String applyId;
            private String applyMoney;
            private String applyReject;
            private String applyScore;
            private String applyState;
            private String insertTime;
            private String userId;

            public String getApplyId() {
                return applyId;
            }

            public void setApplyId(String applyId) {
                this.applyId = applyId;
            }

            public String getApplyMoney() {
                return applyMoney;
            }

            public void setApplyMoney(String applyMoney) {
                this.applyMoney = applyMoney;
            }

            public String getApplyReject() {
                return applyReject;
            }

            public void setApplyReject(String applyReject) {
                this.applyReject = applyReject;
            }

            public String getApplyScore() {
                return applyScore;
            }

            public void setApplyScore(String applyScore) {
                this.applyScore = applyScore;
            }

            public String getApplyState() {
                return applyState;
            }

            public void setApplyState(String applyState) {
                this.applyState = applyState;
            }

            public String getInsertTime() {
                return insertTime;
            }

            public void setInsertTime(String insertTime) {
                this.insertTime = insertTime;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }
}