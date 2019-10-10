package com.example.school_shopping.web.backstage;

import com.example.school_shopping.model.Customer;
import com.example.school_shopping.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@Api(tags = "后台客户模块")
@RestController
@RequestMapping(value = "api/backstage/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    ////跳转到客户模块页面
    @GetMapping
    @ApiOperation(value = "读取客户信息")
    public Map<String,Object> tocustomer(Integer page) {
        Map<String,Object> map=new HashMap<String,Object>();
        if (page==null){//如果page为null，默认为第一页
            page=1;
        }else {
            if (page<1){
                page=1;
            }
        }
        map.put("data", customerService.getCustomerList(page));//当前页显示的记录集合
        map.put("page",page);//当前页
        map.put("code",0);
        return map;
    }

    @PostMapping
    @ApiOperation(value = "添加客户")
    public Map<String,Object> SaveCustomer(Customer customer) {
        Map<String,Object> map=new HashMap<String,Object>();
        if (customer.getPassword().length() == 0) {
            map.put("msg", "账号密码不能为空!");
        } else if (customer.getUsername().length() == 0) {
            map.put("msg", "账号名不能为空");
        } else if(customerService.existsCustomer(customer.getUsername())==true){
            if(customerService.SaveCustomer(customer)){
                map.put("code","0");
                map.put("msg","用户注册成功,请登录！！！");
            }
        }else{
            map.put("msg","用户注册重名，请重试！！！");
            map.put("code","1");
        }
        return map;
    }


    //删除
    @ApiOperation(value = "删除客户")
    @DeleteMapping
    public Map<String,Object> DeleteCustomer(Integer id) {
        Map<String,Object> map=new HashMap<String,Object>();
        customerService.deleteCustomer(id);
        map.put("code",0);
        map.put("msg", "删除成功！！！");
        return map;
    }

    //修改
    @ApiOperation(value = "客户编辑")
    @PutMapping
    public Map<String,Object> CustomerUpdate(Customer customer) {
        Map<String,Object> map=new HashMap<String,Object>();
        customer.setName(customer.getName().trim());
        customer.setUsername(customer.getUsername().trim());
        if (customer.getUsername().equals("")) {
            map.put("msg", "编辑失败：客户账号名不能为空！");
        } else if (customer.getName().equals("")) {
            map.put("msg", "编辑失败:客户名字不能为空！");
        }  else if(customerService.existsCustomer(customer.getName())==true){
            if (customerService.updateCustomer(customer)) {
                map.put("code",0);
                map.put("msg", "客户信息编辑成功！");
            }
        } else {
                map.put("code",1);
                map.put("msg", "客户信息编辑失败！");
            }

        return map;

    }
}
