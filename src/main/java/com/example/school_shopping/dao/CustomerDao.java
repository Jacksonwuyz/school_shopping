package com.example.school_shopping.dao;

import com.example.school_shopping.model.Customer;
import org.apache.ibatis.annotations.Param;

public interface CustomerDao {


    /*前台注册*/
    int SaveCustomer(Customer customer);


    /**
     * 查找在数据库中和指定用户名重名的个数
     * @param username
     * @return 返回重名的个数，0表示不重名
     */
    Customer existsCustomer(@Param(value = "username") String username);

    /*shop登录页面*/

    Customer login(@Param(value = "username") String username, @Param(value = "password") String password);


    //删除
    /**
     * 删除指定账户
     * @param id 关键字
     * @return 删除了多少条记录
     */
    int deleteCustomer(Integer id);

    /**
     * 修改账户的基本信息
     * @return 更改了多少条记录
     */
    int updateCustomer(Customer customer);

    /*
       *  根据标识符获取相应的管理账户对象
       *  @param id
       *  @return null 表示没有找到
       * */
    Customer getCustomer(Integer id);

}
