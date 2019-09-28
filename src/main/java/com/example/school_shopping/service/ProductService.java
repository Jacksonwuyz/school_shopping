package com.example.school_shopping.service;

import com.example.school_shopping.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {


    /**
     * 返回所有的管理账户集合
     *
     * @return 以List方式返回
     */
    List<Product> getProductList();


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

}
