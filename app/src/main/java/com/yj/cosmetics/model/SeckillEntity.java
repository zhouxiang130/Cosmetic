package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/21.
 */

public class SeckillEntity {

    private String code;
    private String msg;
    private SeckillData data;
    public final String HTTP_OK ="200";


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SeckillData getData() {
        return data;
    }

    public void setData(SeckillData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class SeckillData{
        private List<SeckillList> timeLimitList;
        private String newDate;
        private String img;


        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNewDate() {
            return newDate;
        }

        public void setNewDate(String newDate) {
            this.newDate = newDate;
        }

        public List<SeckillList> getTimeLimitList() {
            return timeLimitList;
        }

        public void setTimeLimitList(List<SeckillList> timeLimitList) {
            this.timeLimitList = timeLimitList;
        }

        public class SeckillList{
            private String product_current;
            private String product_id;
            private String product_orginal;
            private String product_name;
            private String product_timeStart;
            private String product_abstract;
            private String product_timeEnd;
            private String product_stock;
            private String product_listImg;
            private String sold;

            //
            private long time;
            private String finalTime;
            private int hours;
            private int min;
            private int sec;

            public String getProduct_abstract() {
                return product_abstract;
            }

            public void setProduct_abstract(String product_abstract) {
                this.product_abstract = product_abstract;
            }

            public String getProduct_current() {
                return product_current;
            }

            public void setProduct_current(String product_current) {
                this.product_current = product_current;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getProduct_listImg() {
                return product_listImg;
            }

            public void setProduct_listImg(String product_listImg) {
                this.product_listImg = product_listImg;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getProduct_orginal() {
                return product_orginal;
            }

            public void setProduct_orginal(String product_orginal) {
                this.product_orginal = product_orginal;
            }

            public String getProduct_stock() {
                return product_stock;
            }

            public void setProduct_stock(String product_stock) {
                this.product_stock = product_stock;
            }

            public String getProduct_timeEnd() {
                return product_timeEnd;
            }

            public void setProduct_timeEnd(String product_timeEnd) {
                this.product_timeEnd = product_timeEnd;
            }

            public String getProduct_timeStart() {
                return product_timeStart;
            }

            public void setProduct_timeStart(String product_timeStart) {
                this.product_timeStart = product_timeStart;
            }

            public String getSold() {
                return sold;
            }

            public void setSold(String sold) {
                this.sold = sold;
            }



            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getFinalTime() {
                return finalTime;
            }

            public void setFinalTime(String finalTime) {
                this.finalTime = finalTime;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }

            public int getSec() {
                return sec;
            }

            public void setSec(int sec) {
                this.sec = sec;
            }
        }
    }
}