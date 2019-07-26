package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/21.
 */

public class MineOrderEntity {

    /**
     * code : 200
     * data : {"code":200,"userOrderMaps":[{"orderId":1180,"orderNum":"20180627194347778839","orderPayamount":"4665.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1168,"orderNum":"20180627175718573161","orderPayamount":"4325.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1164,"orderNum":"20180627174832971619","orderPayamount":"0.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":48,"userOrderdetailMaps":[{"detailNum":1,"money":38,"productListimg":"/upload/head/productImg/2018/03/31/f0122437-f30b-48dd-a531-257ea4038d42.jpg","productName":"全民乐旱小麦全麦石磨面"}]},{"orderId":1165,"orderNum":"20180627174832971619","orderPayamount":"5604.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1157,"orderNum":"20180627174036276799","orderPayamount":"99.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":109,"userOrderdetailMaps":[{"detailNum":1,"money":99,"productListimg":"/upload/head/productImg/2018/03/31/c0efbfbb-41b3-4305-9dc7-e94d732421b7.jpg","productName":"极品红枸杞（秒杀）"}]},{"orderId":1156,"orderNum":"20180627174036276799","orderPayamount":"5566.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1155,"orderNum":"20180627173854480490","orderPayamount":"99.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":109,"userOrderdetailMaps":[{"detailNum":1,"money":99,"productListimg":"/upload/head/productImg/2018/03/31/c0efbfbb-41b3-4305-9dc7-e94d732421b7.jpg","productName":"极品红枸杞（秒杀）"}]},{"orderId":1154,"orderNum":"20180627173854480490","orderPayamount":"5566.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1152,"orderNum":"20180627173740440580","orderPayamount":"5566.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1153,"orderNum":"20180627173740440580","orderPayamount":"99.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":109,"userOrderdetailMaps":[{"detailNum":1,"money":99,"productListimg":"/upload/head/productImg/2018/03/31/c0efbfbb-41b3-4305-9dc7-e94d732421b7.jpg","productName":"极品红枸杞（秒杀）"}]}]}
     * msg : 成功
     */
    private String code;
    private DataBean data;
    private String msg;
    public final String HTTP_OK ="200";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * code : 200
         * userOrderMaps : [{"orderId":1180,"orderNum":"20180627194347778839","orderPayamount":"4665.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1168,"orderNum":"20180627175718573161","orderPayamount":"4325.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1164,"orderNum":"20180627174832971619","orderPayamount":"0.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":48,"userOrderdetailMaps":[{"detailNum":1,"money":38,"productListimg":"/upload/head/productImg/2018/03/31/f0122437-f30b-48dd-a531-257ea4038d42.jpg","productName":"全民乐旱小麦全麦石磨面"}]},{"orderId":1165,"orderNum":"20180627174832971619","orderPayamount":"5604.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1157,"orderNum":"20180627174036276799","orderPayamount":"99.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":109,"userOrderdetailMaps":[{"detailNum":1,"money":99,"productListimg":"/upload/head/productImg/2018/03/31/c0efbfbb-41b3-4305-9dc7-e94d732421b7.jpg","productName":"极品红枸杞（秒杀）"}]},{"orderId":1156,"orderNum":"20180627174036276799","orderPayamount":"5566.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1155,"orderNum":"20180627173854480490","orderPayamount":"99.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":109,"userOrderdetailMaps":[{"detailNum":1,"money":99,"productListimg":"/upload/head/productImg/2018/03/31/c0efbfbb-41b3-4305-9dc7-e94d732421b7.jpg","productName":"极品红枸杞（秒杀）"}]},{"orderId":1154,"orderNum":"20180627173854480490","orderPayamount":"5566.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1152,"orderNum":"20180627173740440580","orderPayamount":"5566.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":5666,"userOrderdetailMaps":[{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]},{"orderId":1153,"orderNum":"20180627173740440580","orderPayamount":"99.0","orderState":1,"orderStateName":"待付款","orderpayStyle":1,"proNumber":1,"productState":1,"shopImg":"/images/shopImg.png","shopName":"自营","upOrderMoney":109,"userOrderdetailMaps":[{"detailNum":1,"money":99,"productListimg":"/upload/head/productImg/2018/03/31/c0efbfbb-41b3-4305-9dc7-e94d732421b7.jpg","productName":"极品红枸杞（秒杀）"}]}]
         */

        private int code;
        private List<UserOrderMapsBean> userOrderMaps;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public List<UserOrderMapsBean> getUserOrderMaps() {
            return userOrderMaps;
        }

        public void setUserOrderMaps(List<UserOrderMapsBean> userOrderMaps) {
            this.userOrderMaps = userOrderMaps;
        }

        public static class UserOrderMapsBean {
            /**
             * orderId : 1180
             * orderNum : 20180627194347778839
             * orderPayamount : 4665.0
             * orderState : 1
             * orderStateName : 待付款
             * orderpayStyle : 1
             * proNumber : 1
             * productState : 1
             * shopImg : /images/shopImg.png
             * shopName : 自营
             * upOrderMoney : 5666
             * userOrderdetailMaps : [{"detailNum":1,"money":5666,"productListimg":"/upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png","productName":"ddd111"}]
             */

            private String orderId;
            private String orderNum;
            private String orderPayamount;
            private String  orderState;
            private String orderStateName;
            private int orderpayStyle;
            private int proNumber;
            private String productState;
            private String shopImg;
            private String shopId;
            private String shopName;
            private String  upOrderMoney;
            private String isAccelerate;
            private List<UserOrderdetailMapsBean> userOrderdetailMaps;

            public String getIsAccelerate() {
                return isAccelerate;
            }

            public void setIsAccelerate(String isAccelerate) {
                this.isAccelerate = isAccelerate;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getOrderPayamount() {
                return orderPayamount;
            }

            public void setOrderPayamount(String orderPayamount) {
                this.orderPayamount = orderPayamount;
            }

            public String getOrderState() {
                return orderState;
            }

            public void setOrderState(String orderState) {
                this.orderState = orderState;
            }

            public String getOrderStateName() {
                return orderStateName;
            }

            public void setOrderStateName(String orderStateName) {
                this.orderStateName = orderStateName;
            }

            public int getOrderpayStyle() {
                return orderpayStyle;
            }

            public void setOrderpayStyle(int orderpayStyle) {
                this.orderpayStyle = orderpayStyle;
            }

            public int getProNumber() {
                return proNumber;
            }

            public void setProNumber(int proNumber) {
                this.proNumber = proNumber;
            }

            public String getProductState() {
                return productState;
            }

            public void setProductState(String productState) {
                this.productState = productState;
            }

            public String getShopImg() {
                return shopImg;
            }

            public void setShopImg(String shopImg) {
                this.shopImg = shopImg;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getUpOrderMoney() {
                return upOrderMoney;
            }

            public void setUpOrderMoney(String upOrderMoney) {
                this.upOrderMoney = upOrderMoney;
            }

            public List<UserOrderdetailMapsBean> getUserOrderdetailMaps() {
                return userOrderdetailMaps;
            }

            public void setUserOrderdetailMaps(List<UserOrderdetailMapsBean> userOrderdetailMaps) {
                this.userOrderdetailMaps = userOrderdetailMaps;
            }

            public static class UserOrderdetailMapsBean {
                /**
                 * detailNum : 1
                 * money : 5666
                 * productListimg : /upload/head/productImg/2018/06/29/4abb0185-76ec-4063-be2d-2a53b49c4e3e.png
                 * productName : ddd111
                 */

                private int detailNum;
                private String money;
                private String productListimg;
                private String productName;
                private String skuPropertiesName;

                public String getSkuPropertiesName() {
                    return skuPropertiesName;
                }

                public void setSkuPropertiesName(String skuPropertiesName) {
                    this.skuPropertiesName = skuPropertiesName;
                }

                public int getDetailNum() {
                    return detailNum;
                }

                public void setDetailNum(int detailNum) {
                    this.detailNum = detailNum;
                }

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }

                public String getProductListimg() {
                    return productListimg;
                }

                public void setProductListimg(String productListimg) {
                    this.productListimg = productListimg;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }
            }
        }
    }
}