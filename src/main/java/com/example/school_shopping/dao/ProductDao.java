package com.example.school_shopping.dao;

import com.example.school_shopping.model.Product;
import com.example.school_shopping.model.base.PageObject;
import com.example.school_shopping.model.query.ProductQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {


    /**
     * 返回所有的管理账户集合
     * @return 以List方式返回
     */
    List<Product> getProductList(String basePath);

    /**
     * 获取表中所有记录
     * @return
     */
    List<Product> readAll();
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
    /*
      *  根据标识符获取相应的管理账户对象
       *  @param id
       *  @return null 表示没有找到
       * */
    Product getProduct(int id);
       /*
        * 分页显示数据库记录
        *
        * */

    List<Product> getPartlist(@Param(value = "offset")int offset,@Param(value = "length")int length);

    /*
       * 获取数据库总记录
       *
       * */
    int total();
    /*
        *
        * 获取订单
        * */
    List<Product> getProductList(Integer pageSize);

    /*
       *  根据标识符获取相应的管理账户对象
       *  @param id
       *  @return null 表示没有找到
       * */
    Product getProduct(Integer id);
    /*
      *
      * 获取产品
      * */
    List<Product> getShopProductTypelist(Integer productTypeId);

    /**
     * 根据标志符集合删除对应的记录信息集合
     * @param ids id集合
     * @return  删除的记录数，>=1表示删除成功，0表示删除失败
     */
    int deletes(Integer[] ids);

    /**
     * 获取查询记录数，一般与query配合使用
     * @param objectQuery 查询条件类
     * @return
     */
    int querySize(@Param(value = "objectQuery")Object objectQuery);
//搜索
    List<Product> searchProducts(@Param(value = "name") String name);
    /**
     * 根据该对象的关键字，读取指定记录,不包含关联属性
     * @param id 关键字
     * @return null表示读取失败
     */
    Product getSimple(Integer id);
    /**
     * 读取部分记录，一般配合业务层分页方法展示
     * @param offset 第一条记录索引（从0开始）
     * @param length 显示记录个数（指从第一条记录开始，显示第N条）
     * @param objectQuery 查询条件类
     * @return
     */
    List<Product> query(@Param(value = "offset") final int offset, @Param(value = "length") final int length,@Param(value = "objectQuery")Object objectQuery);

}
