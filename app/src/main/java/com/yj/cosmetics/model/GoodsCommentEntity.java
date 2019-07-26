package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2017/8/28.
 */

public class GoodsCommentEntity {
    private String code;
    private String msg;
    private GoodsCommentData data;
    public final String HTTP_OK = "200";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GoodsCommentData getData() {
        return data;
    }

    public void setData(GoodsCommentData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class GoodsCommentData{
        private String commentCount;
        private List<GoodsCommentList> commArray;

        public List<GoodsCommentList> getCommArray() {
            return commArray;
        }

        public void setCommArray(List<GoodsCommentList> commArray) {
            this.commArray = commArray;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }

        public class GoodsCommentList{
            private String commentContent;
            private List<String> commentImg;
            private String userId;
            private String inserttime;
            private String score;
            private String userName;
            private String userHeadimg;
            private String skuPropertiesName;

            public String getCommentContent() {
                return commentContent;
            }

            public void setCommentContent(String commentContent) {
                this.commentContent = commentContent;
            }

            public List<String> getCommentImg() {
                return commentImg;
            }

            public void setCommentImg(List<String> commentImg) {
                this.commentImg = commentImg;
            }

            public String getInserttime() {
                return inserttime;
            }

            public void setInserttime(String inserttime) {
                this.inserttime = inserttime;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getSkuPropertiesName() {
                return skuPropertiesName;
            }

            public void setSkuPropertiesName(String skuPropertiesName) {
                this.skuPropertiesName = skuPropertiesName;
            }

            public String getUserHeadimg() {
                return userHeadimg;
            }

            public void setUserHeadimg(String userHeadimg) {
                this.userHeadimg = userHeadimg;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
