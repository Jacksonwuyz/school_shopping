package com.example.school_shopping.model;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class ProductType {
    private Integer id;
    private String name;//产品类别名称
    @Length(max = 255, message = "产品类别的外部链接地址不能超过{max}个字符")
    private String linkUrl;//转向链接地址（如果存在则直接转向链接地址，否则打开本网站栏目）
    @Length(max = 200, message = "产品的图片地址不能超过{max}个字符")
    private String imageUrl;//栏目的标题图片地址
    private String intro;//栏目简介
    public ProductType() {
    }

    public ProductType(int id) {
        this.id=id;
    }

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
    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
