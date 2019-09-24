package com.example.school_shopping.service;

import com.example.school_shopping.model.ProductType;
import org.springframework.stereotype.Service;

@Service
public interface ProductTypeService {


    /*
       * 将账户信息存进数据库
       * @param  producttype
       * @return true表示保存成功，false表示保存失败
       * */
    boolean SaveProductType(ProductType productType);

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
    boolean updateProductType(ProductType productType);
//   删除指定账户
//      @param id 被删除的账户id
//     * @return true表示删除成功

    boolean deleteProductType(Integer id);


    /**
     * 查找在数据库中和指定用户名重名的个数
     *
     * @param
     * @param
     * @return 返回重名的个数
     */
    boolean existsProductType(String name);

}