package com.example.school_shopping.dao;

import com.example.school_shopping.model.Product;
import org.apache.ibatis.annotations.Param;

public interface ProductDao {

//添加
    int SaveProduct(Product product);


    /**
     * 删除指定账户
     * @param id 关键字
     * @return 删除了多少条记录
     */
    int deleteProduct(Integer id);

    /**
     * 修改账户的基本信息
     * @return 更改了多少条记录
     */
    int updateProduct(Product product);

    /**
     * 查找在数据库中和指定用户名重名的个数
     * @param
     * @param
     * @return 返回重名的个数
     */
    Product existsProduct(@Param(value = "name") String name);


}
