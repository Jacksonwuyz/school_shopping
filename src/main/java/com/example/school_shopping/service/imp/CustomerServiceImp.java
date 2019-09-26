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
//前端客户注册
    public boolean SaveCustomer(Customer customer) {
        boolean stsatus = false;
        customer.setPassword(SHA.getResult("123456"));
        customer.setCreateTime(new Date());//系统当前时间为创建日期
        if (customerDao.SaveCustomer(customer)>0){
            stsatus= true;
        }else {
            stsatus= false;
        }
        return stsatus;
    }
    //重名
    public boolean existsCustomer(String username){
        if(customerDao.existsCustomer(username)==null){
            return true;
        }else{
            return false;
        }
    }
    //登录
    public Customer login(String username, String password) {
        if (password.length()!=32){
            //将密码加密后再进行比对
            password = SHA.getResult(password);
        }
        Customer customer = customerDao.login(username, password);
        return customer;
    }


    //删除
    public boolean deleteCustomer(Integer id){
        boolean status=false;//存储修改结果
        int n=customerDao.deleteCustomer(id);
        if(n==1){
            status=true;
        }
        return status;
    }
    //修改
    public boolean updateCustomer(Customer customer){
        boolean status=false;//存储修改结果
        if(customerDao.updateCustomer(customer)==1){
            status=true;
        }else{
            status=false;
        }

        return status;
    }

    public boolean SaveShopCustomer(Customer customer) {
        boolean stsatus = false;//默认注册失败！
     customer.setPassword(SHA.getResult(customer.getPassword()));
        customer.setCreateTime(new Date());//系统当前时间为创建日期
        if (customerDao.SaveShopCustomer(customer)==1){
            stsatus= true;
        }else {
            stsatus= false;
        }
        return stsatus;
    }

}
