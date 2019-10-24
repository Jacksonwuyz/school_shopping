package com.example.school_shopping.model;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class Admin {

    private Integer id;
    private String username;//用户名
    private String password;//用户密码
    private Date createTime;
    private String name;
    //非数据库字段
    @ApiModelProperty(hidden = true)
    private Integer saveProductNumber;//发布过的产品数量，用于传递给业务层其他对象或web层
    @Length(max = 255, message = "产品的图片地址不能超过{max}个字符")
    private String picUrl;//头像路径
    public Admin() {
    }
    public Admin(Integer id){
        super();
        this.id=id;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPicUrl() {
        return picUrl;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public Integer getSaveProductNumber() {
        return saveProductNumber;
    }

    public void setSaveProductNumber(Integer saveProductNumber) {
        this.saveProductNumber = saveProductNumber;
    }
}
