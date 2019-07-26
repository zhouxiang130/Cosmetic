package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/26.
 */

public class InfoCenterEntity {

    private String code;
    private String msg;
    private List<InfoCenterData> data;

    public final String HTTP_OK ="200";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<InfoCenterData> getData() {
        return data;
    }

    public void setData(List<InfoCenterData> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class InfoCenterData{
        private String content;
        private String time;
        private String mid;
        private String title;
        private String type;
        private int isRead;

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}