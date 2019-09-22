package com.example.school_shopping.service.imp;

import com.example.school_shopping.dao.CustomerDao;
import com.example.school_shopping.model.Customer;
import com.example.school_shopping.service.CustomerService;
import com.example.school_shopping.util.SHA;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CustomerServiceImp implements CustomerService {
    @Resource
    private CustomerDao customerDao;

    public boolean SaveCustomer(Customer customer) {
        boolean stsatus = false;
        customer.setPassword(SHA.getResult("123456"));
        customer.setCreateTime(new Date());//系统当前时间为创建日期
        if (customerDao.SaveCustomer(customer)==1){
            stsatus= true;
        }else {
            stsatus= false;
        }
        return stsatus;
    }
    public boolean existsCustomer(String username){
        if(customerDao.existsCustomer(username)==0){
            return false;
        }else{
            return true;
        }
    }
}
