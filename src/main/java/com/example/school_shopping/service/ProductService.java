package com.example.school_shopping.service;

import com.example.school_shopping.model.Product;
import com.example.school_shopping.model.base.PageObject;
import com.example.school_shopping.model.query.ProductQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {



    /**
     * 返回所有的管理账户集合
     *
     * @return 以List方式返回
     */
    List<Product> getProductList(String basePath);


    /*
      * 将账户信息存进数据库
      * @param  producttype
      * @return true表示保存成功，false表示保存失败
      * */
    boolean SaveProduct(Product product);

    /**
     * 删除指定账户
     *
     * @param id 被删除的账户id
     * @return true表示删除成功
     */
    boolean deleteProduct(Integer id);

    /**
     * 查找在数据库中和指定用户名重名的个数
     *
     * @param
     * @param
     * @return 返回重名的个数
     */
    boolean existsProduct(String name);

    /**
     * 修改账户的基本信息
     * 说明：
     * 1、修改后的账户名不能与其他账户的账户名重名
     *
     * @param
     * @param
     * @param
     * @return false表示修改失败，true表示修改成功
     */
    boolean updateProduct(Product product);

    /*分页
 * */
    List<Product> getProductList(Integer page);

    int maxPage();

    /*
      *
      * 获取前台分类产品
      * */
    List<Product> getShopProductTypelist(Integer productTypeId);
    /*
          *  根据标识符获取相应的管理账户对象
          *  @param id
          *  @return null 表示没有找到
          * */
    Product getProduct(Integer id);
    /**
     * 批量删除指定产品
     * 说明：
     * 1.如果产品存在订单则不允许删除
     * @param ids 多个产品的主键集合
     */
    void deleteProducts(Integer[] ids);


    List<Product> searchProducts(@Param(value = "name") String name);


    /**
     * 批量删除客户的头像文件，并将数据库对应的头像信息清空
     * 说明：无论该客户是否真的存在头像文件，都会一并删除不会出BUG
     * @param ids 多个账户的主键集合
     * @param basePath 项目根目录网址，用于删除账户对应的头像文件
     */
    void removeProductsProfilePicture(Integer[] ids,String basePath);
}
