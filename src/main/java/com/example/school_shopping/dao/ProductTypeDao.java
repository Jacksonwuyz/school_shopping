package com.example.school_shopping.dao;


import com.example.school_shopping.model.Product;
import com.example.school_shopping.model.ProductType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductTypeDao {

    /**
     * 返回所有的管理账户集合
     * @return 以List方式返回
     */
    List<ProductType> getProductTypeList(String basePath);
    /**
     * 获取表中所有记录
     * @return
     */
    List<ProductType> readAll();
//添加
    int SaveProductType(ProductType productType);
    /**
     * 修改账户的基本信息
     * @return 更改了多少条记录
     */
    int updateProductType(ProductType productType);

    /**
     * 删除指定账户
     * @param id 关键字
     * @return 删除了多少条记录
     */
    int deleteProductType(Integer id);

    /**
     * 查找在数据库中和指定用户名重名的个数
     * @param
     * @param
     * @return 返回重名的个数
     */
    ProductType existsProductType(@Param(value = "name") String name);

     /*
        * 分页显示数据库记录
        *
        * */

    List<ProductType> getPartlist(@Param(value = "offset")int offset,@Param(value = "length")int length);

    /*
       *  根据标识符获取相应的管理账户对象
       *  @param id
       *  @return null 表示没有找到
       * */
    ProductType getProductType(int id);


    /**
     * 获取查询记录数，一般与query配合使用
     * @param objectQuery 查询条件类
     * @return
     */
    int querySize(@Param(value = "objectQuery")Object objectQuery);
    /**
     * 根据标志符集合删除对应的记录信息集合
     * @param ids id集合
     * @return  删除的记录数，>=1表示删除成功，0表示删除失败
     */
    int deletes(Integer[] ids);

}
