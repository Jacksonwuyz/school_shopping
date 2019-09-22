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
    int existsCustomer(@Param(value = "username")String username);
}
