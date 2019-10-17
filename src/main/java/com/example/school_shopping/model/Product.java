package com.example.school_shopping.model;

import java.util.Date;

public class Product {

    private Integer id;
    private ProductType productType;//所属产品类别
    private Integer productTypeId;//所属产品类别id
    private String name;//产品名称
    private Integer orderNum;//优先级
    private String description;//产品描述
    private String content;//产品详细描述
    private Float price;//产品现价
    private Float originalPrice;//产品原价
    private String picUrl;//产品图片路径
    private Integer number;//库存数量
    private Integer click;//点击数
    private Boolean onSale;//是否上架（true表示上架，但是要考虑上架时间；false表示不上架）
    private java.util.Date createTime;//创建时间
    private Admin creator;//创建产品管理员
    public Product() {
    }

    public Product(int id) {
        this.id=id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Admin getCreator() {
        return creator;
    }

    public void setCreator(Admin creator) {
        this.creator = creator;
    }
/*

    public ProductType getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(ProductType creatorId) {
        this.creatorId = creatorId;
    }
*/

    public Admin getFinalEditor() {
        return finalEditor;
    }

    public void setFinalEditor(Admin finalEditor) {
        this.finalEditor = finalEditor;
    }

    public ProductType getFinalEditorId() {
        return finalEditorId;
    }

    public void setFinalEditorId(ProductType finalEditorId) {
        this.finalEditorId = finalEditorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private Admin finalEditor;//最后编辑管理员
    private ProductType finalEditorId;//最后编辑管理员Id
    private java.util.Date updateTime;//最后编辑时间


}
