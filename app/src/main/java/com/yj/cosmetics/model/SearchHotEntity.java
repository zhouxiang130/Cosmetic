package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/26.
 */

public class SearchHotEntity {
    private String code;
    private String msg;
    private List<SearchHotData> data;

    public final String HTTP_OK ="200";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<SearchHotData> getData() {
        return data;
    }

    public void setData(List<SearchHotData> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public class SearchHotData{
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
