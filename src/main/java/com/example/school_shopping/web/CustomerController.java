package com.example.school_shopping.web;

import com.example.school_shopping.model.Customer;
import com.example.school_shopping.service.CustomerService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@RestController
public class CustomerController {
    @Resource
    private CustomerService customerService;

    //执行前台（客户）注册页面d

    @PostMapping(value = "api/shop/customer/savecustomer")
    public Map<String,Object> SaveCustomer(Customer customer) {
        Map<String,Object> map=new HashMap<String,Object>();
        if (customer.getPassword().length() == 0) {
            map.put("msg", "账号密码不能为空!");
        } else if (customer.getUsername().length() == 0) {
            map.put("msg", "账号名不能为空");
        } else if(customerService.existsCustomer(customer.getUsername())==true){
            if(customerService.SaveCustomer(customer)==true){
                map.put("code","1");
                map.put("msg","用户注册成功,请登录！！！");
            }
        }else{
            map.put("msg","用户注册重名，请重试！！！");
            map.put("code","-1");
        }
        return map;
    }

    /*
         * 用于判断前台登录
         */

    @GetMapping(value = "api/shop/customer/login")
    public Map<String,Object> login(String username, String password, HttpSession session) {
        Map<String,Object> map=new HashMap<String,Object>();
        Customer customer=customerService.login(username, password);
        if(customer!=null){
            session.setAttribute("customer", customer);
            map.put("code",1);//自定义值：code如果为1表示登录成功，如果为-1表示登录失败
        }else{
            map.put("code",-1);
            map.put("msg", "登录失败:密码错误");
        }
        return map;
    }

    //删除
    @DeleteMapping(value = "api/backstage/customer/DeleteCustomer")
    public Map<String,Object> DeleteCustomer(Integer id) {
        Map<String,Object> map=new HashMap<String,Object>();
        customerService.deleteCustomer(id);
        map.put("code",1);
        return map;
    }

    //修改
    //执行客户编辑操作
    @PutMapping(value = "api/backstage/customer/doCustomerUpdate")
    public Map<String,Object> doCustomerUpdate(Customer customer) {
        Map<String,Object> map=new HashMap<String,Object>();
        customer.setName(customer.getName().trim());
        customer.setUsername(customer.getUsername().trim());
        if (customer.getUsername().equals("")) {
            map.put("msg", "编辑失败：客户账号名不能为空！");
        } else if (customer.getName().equals("")) {
            map.put("msg", "编辑失败:客户名字不能为空！");
        }  else {
            if (customerService.updateCustomer(customer)) {
                map.put("code",1);
                map.put("msg", "客户信息编辑成功！");
            } else {
                map.put("code",-1);
                map.put("msg", "客户信息编辑失败！");
            }
        }
        return map;

    }
}
