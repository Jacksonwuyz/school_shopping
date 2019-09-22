package com.example.school_shopping.web;

import com.example.school_shopping.model.Customer;
import com.example.school_shopping.service.CustomerService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@RestController
public class CustomerController {
    @Resource
    private CustomerService customerService;

    //执行客户添加页面d

    @RequestMapping(value = "api/backstage/customer/doSaveCustomer")
    public Map<String,Object> doSaveCustomer(Model model, ModelAndView modelAndView, Customer customer) {
        Map<String,Object> map=new HashMap<String,Object>();
        customer.setUsername(customer.getUsername().trim());
        customer.setName(customer.getName().trim());
        if (customer.getName().length() == 0) {
            model.addAttribute("myMessage", "客户添加:客户名字不能为空");
        } else if (customer.getUsername().length() == 0) {
            model.addAttribute("myMessage", "客户添加:客户账号名不能为空");
        }else if(customerService.existsCustomer(customer.getUsername())){
            model.addAttribute("myMessage","客户添加失败:客户账户名已存在，请选择其他的账户名");
        } else {
            if (customerService.SaveCustomer(customer)) {
                model.addAttribute("customer",null) ;
                model.addAttribute("myMessage", "客户添加成功！！！");
            } else {
                model.addAttribute("myMessage", "客户添加失败！！！");
            }
        }
        return map;
    }
}
