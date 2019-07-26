package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2017/8/28.
 */

public class GoodsSaleEntity {
    private String msg;
    private String code;
    private List<GoodsSaleData> data;
    public final String HTTP_OK = "200";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<GoodsSaleData> getData() {
        return data;
    }

    public void setData(List<GoodsSaleData> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class GoodsSaleData{

        private String propesId;
        private String propesName;
        private List<GoodsSaleList> jsonArray;

        public List<GoodsSaleList> getJsonArray() {
            return jsonArray;
        }

        public void setJsonArray(List<GoodsSaleList> jsonArray) {
            this.jsonArray = jsonArray;
        }

        public String getPropesId() {
            return propesId;
        }

        public void setPropesId(String propesId) {
            this.propesId = propesId;
        }

        public String getPropesName() {
            return propesName;
        }

        public void setPropesName(String propesName) {
            this.propesName = propesName;
        }

        public class GoodsSaleList{
            private String provalueId;
            private String provalue;

            public String getProvalue() {
                return provalue;
            }

            public void setProvalue(String provalue) {
                this.provalue = provalue;
            }

            public String getProvalueId() {
                return provalueId;
            }

            public void setProvalueId(String provalueId) {
                this.provalueId = provalueId;
            }
        }
    }
}
