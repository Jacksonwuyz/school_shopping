package com.example.school_shopping.model;

import java.util.Date;

public class ProductType {
    private Integer id;
    private String name;//产品类别名称
    private String image;//栏目的标题图片地址
    private String intro;//栏目简介

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

//    public Integer getProductNumber() {
//        return productNumber;
//    }
//
//    public void setProductNumber(Integer productNumber) {
//        this.productNumber = productNumber;
//    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private Integer orderNum;//排序（默认0，规则由前台决定，一般排序为最大）
//    private Integer productNumber;//该栏目下的产品数量
    private Date createTime;//时间

}
