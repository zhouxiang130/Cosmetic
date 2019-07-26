package com.yj.cosmetics.model;

import java.io.File;

/**
 * Created by Suo on 2017/9/19.
 */

public class JudgeGoodsData implements Comparable<JudgeGoodsData>{
    private File img;
    private int position;
    private int imgPosition;
    private String path;
    private File scaledImg;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getScaledImg() {
        return scaledImg;
    }

    public void setScaledImg(File scaledImg) {
        this.scaledImg = scaledImg;
    }

    public int getImgPosition() {
        return imgPosition;
    }

    public void setImgPosition(int imgPosition) {
        this.imgPosition = imgPosition;
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int compareTo(JudgeGoodsData o) {
        int i = this.getPosition() - o.getPosition();//先按照年龄排序
        if(i == 0){
            return this.getImgPosition() - o.getImgPosition();//如果年龄相等了再用分数进行排序
        }
        return i;
    }
}
