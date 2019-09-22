package com.example.school_shopping.service;

import com.example.school_shopping.model.Customer;
import org.springframework.stereotype.Service;

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
     * @return true表示存在，false表示存在
     */
    boolean existsCustomer(String username);

}
