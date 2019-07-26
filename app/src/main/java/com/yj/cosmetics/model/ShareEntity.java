package com.yj.cosmetics.model;

/**
 * Created by Suo on 2018/3/26.
 */

public class ShareEntity {

    private String code;
    private String msg;
    private ShareData data;

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

    public ShareData getData() {
        return data;
    }

    public void setData(ShareData data) {
        this.data = data;
    }

    public class ShareData{
        private String content;
        private String title;
        private String image;
        private String url;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}