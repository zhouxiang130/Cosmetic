package com.yj.cosmetics.model;

/**
 * Created by Suo on 2017/6/14.
 */

public class NormalEntity {

    private String code;
    private String msg;
    public final String HTTP_OK ="200";
    private Object data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "NormalEntity{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", HTTP_OK='" + HTTP_OK + '\'' +
                ", data=" + data +
                '}';
    }
}
