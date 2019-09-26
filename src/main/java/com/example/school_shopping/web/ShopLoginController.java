package com.example.school_shopping.web;

import com.example.school_shopping.model.Customer;
import com.example.school_shopping.service.AdminService;
import com.example.school_shopping.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@Api(tags = "后台登录注册模块")
@RestController
@RequestMapping(value = "api/shop")
public class ShopLoginController {
    @Resource
    private CustomerService customerService;
    /*
         * 用于判断前台登录
         */

    @ApiOperation(value = "前台登录")
    @GetMapping("/login")
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
 /*执行注册页面*/
    @ApiOperation(value = "前台注册")
    @PostMapping("/registered")
    public Map<String,Object> SaveShopCustomer(Customer customer) {
        Map<String,Object> map=new HashMap<String,Object>();
        if (customer.getPassword().length() == 0) {
            map.put("msg", "账号密码不能为空!");
        } else if (customer.getUsername().length() == 0) {
            map.put("msg", "账号名不能为空");
        } else if (customer.getName().length() == 0) {
            map.put("msg", "姓名不能为空");
        }else if(customerService.existsCustomer(customer.getUsername())==true){
           if(customerService.SaveShopCustomer(customer)==true) {
            map.put("code", 1);
            map.put("msg", "注册成功！");
           }
        } else {
            map.put("code",-1);
            map.put("msg", "重名，注册失败！");
        }
        return map;
    }

}
