package com.example.school_shopping.service;

import com.example.school_shopping.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    /*
    * 将账户信息存进数据库
    * @param  producttype
    * @return true表示保存成功，false表示保存失败
    * */
    boolean SaveCustomer(Customer customer) ;


    /**
     * 判断账户名是否存在（用于创建新账户的时候）
     * @param username
     * @return true表示存在，false表示不存在
     */
    boolean existsCustomer(String username);

    /*shop登录*/
    Customer login(String username, String password);


    //删除
    /**
     * 删除指定账户
     * @param id 被删除的账户id
     * @return true表示删除成功
     */
    boolean deleteCustomer(Integer id);


    /**
     * 修改账户的基本信息
     * 说明：
     * 1、修改后的账户名不能与其他账户的账户名重名
     * @param
     * @param
     * @param
     * @return false表示修改失败，true表示修改成功
     */
    boolean updateCustomer(Customer customer);

    /*
    * 将账户信息存进数据库
    * @param  producttype
    * @return true表示保存成功，false表示保存失败
    * */
    boolean SaveShopCustomer(Customer customer) ;

    /**
     * 返回所有的管理账户集合
     * @return 以List方式返回
     */
    List<Customer> getCustomerList();

    /*分页
 * */
    List<Customer> getCustomerList(Integer page);

    int maxPage();

     /*
	 * 将新密码保存到数据库中
	 * @return true表示密码更改成功，false表示密码更改失败
	 */

    boolean updatePassword(String newPass, Integer id);

    /**
     * 根据id读取对象
     * @param id
     * @return
     */
    Customer getCustomer(Integer id);
}
