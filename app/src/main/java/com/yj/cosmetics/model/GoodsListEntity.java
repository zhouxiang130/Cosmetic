package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/21.
 */

public class GoodsListEntity {
    public String code;
    public String msg;
    public DataBean data;

    public final String HTTP_OK ="200";
    public final String HTTP_OK_ ="400";


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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean{
        /**
         * code : 200
         * pageNumber : 1
         * productList : [{"classify_name":"新鲜水果","commentNum":0,"insert_time":"2019-06-11 17:06:00","product_current":10,"product_id":229,"product_label":"1","product_listImg":"http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/11/77e72c27-6d7d-4068-8665-ad2be2c5170d.jpg","product_name":"橙子","product_orginal":30,"product_sales":0,"product_sort":1,"product_state":0,"product_stock":300,"product_type":0,"shop_name":"电脑"}]
         */

        public int code;
        public int pageNumber;
        public String  shopId;
        public String shopLogo;
        public String shopName;
        public String shopNumber;
        public String shopNotice;
        public String numMonth;


        public String classify_name;
        public String detailNumMonth;
        public String insert_time;
        public String product_current;
        public String product_id;
        public String product_label;
        public String product_listImg;
        public String product_name;
        public String product_orginal;
        public String product_sales;
        public String product_sort;
        public String product_state;
        public String product_stock;
        public String product_type;
        public String shop_name;
        
        public List<ProductListBean> productList;

        public String getNumMonth() {
            return numMonth;
        }

        public void setNumMonth(String numMonth) {
            this.numMonth = numMonth;
        }

        public String getShopNotice() {
            return shopNotice;
        }

        public void setShopNotice(String shopNotice) {
            this.shopNotice = shopNotice;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopLogo() {
            return shopLogo;
        }

        public void setShopLogo(String shopLogo) {
            this.shopLogo = shopLogo;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopNumber() {
            return shopNumber;
        }

        public void setShopNumber(String shopNumber) {
            this.shopNumber = shopNumber;
        }

   

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public  class ProductListBean {
            /**
             * classify_name : 新鲜水果
             * commentNum : 0
             * insert_time : 2019-06-11 17:06:00
             * product_current : 10
             * product_id : 229
             * product_label : 1
             * product_listImg : http://wendi-imgs.oss-cn-beijing.aliyuncs.com//upload/head/productImg/2019/06/11/77e72c27-6d7d-4068-8665-ad2be2c5170d.jpg
             * product_name : 橙子
             * product_orginal : 30
             * product_sales : 0
             * product_sort : 1
             * product_state : 0
             * product_stock : 300
             * product_type : 0
             * shop_name : 电脑
             */

            private String detailNumMonth;
            private InsertTimeBean insert_time;


            public String classify_name;
            public String commentNum;
            public String product_current;
            public String product_id;
            public String product_label;
            public String product_listImg;
            public String product_name;
            public String product_orginal;
            public String product_sales;
            public String product_sort;
            public String product_state;
            public String product_stock;
            public String product_type;
            public String shop_name;

            public  class InsertTimeBean {
                /**
                 * date : 10
                 * day : 1
                 * hours : 14
                 * minutes : 40
                 * month : 5
                 * nanos : 0
                 * seconds : 31
                 * time : 1560148831000
                 * timezoneOffset : -480
                 * year : 119
                 */

                private String date;
                private String day;
                private String hours;
                private String minutes;
                private String month;
                private String nanos;
                private String seconds;

                private long time;
                private String timezoneOffset;
                private String year;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getDay() {
                    return day;
                }

                public void setDay(String day) {
                    this.day = day;
                }

                public String getHours() {
                    return hours;
                }

                public void setHours(String hours) {
                    this.hours = hours;
                }

                public String getMinutes() {
                    return minutes;
                }

                public void setMinutes(String minutes) {
                    this.minutes = minutes;
                }

                public String getMonth() {
                    return month;
                }

                public void setMonth(String month) {
                    this.month = month;
                }

                public String getNanos() {
                    return nanos;
                }

                public void setNanos(String nanos) {
                    this.nanos = nanos;
                }

                public String getSeconds() {
                    return seconds;
                }

                public void setSeconds(String seconds) {
                    this.seconds = seconds;
                }

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public String getTimezoneOffset() {
                    return timezoneOffset;
                }

                public void setTimezoneOffset(String timezoneOffset) {
                    this.timezoneOffset = timezoneOffset;
                }

                public String getYear() {
                    return year;
                }

                public void setYear(String year) {
                    this.year = year;
                }
            }

            public String getClassify_name() {
                return classify_name;
            }

            public void setClassify_name(String classify_name) {
                this.classify_name = classify_name;
            }

            public String getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(String commentNum) {
                this.commentNum = commentNum;
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

            public String getProduct_label() {
                return product_label;
            }

            public void setProduct_label(String product_label) {
                this.product_label = product_label;
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

            public String getProduct_sales() {
                return product_sales;
            }

            public void setProduct_sales(String product_sales) {
                this.product_sales = product_sales;
            }

            public String getProduct_sort() {
                return product_sort;
            }

            public void setProduct_sort(String product_sort) {
                this.product_sort = product_sort;
            }

            public String getProduct_state() {
                return product_state;
            }

            public void setProduct_state(String product_state) {
                this.product_state = product_state;
            }

            public String getProduct_stock() {
                return product_stock;
            }

            public void setProduct_stock(String product_stock) {
                this.product_stock = product_stock;
            }

            public String getProduct_type() {
                return product_type;
            }

            public void setProduct_type(String product_type) {
                this.product_type = product_type;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }
        }
    }

}