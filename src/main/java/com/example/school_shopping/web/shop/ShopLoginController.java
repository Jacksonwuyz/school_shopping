package com.example.school_shopping.web.shop;

import com.example.school_shopping.model.Customer;
import com.example.school_shopping.service.AdminService;
import com.example.school_shopping.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@Api(tags = "前台登录注册模块")
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
            map.put("data",customer.getUsername());
            map.put("code",0);//自定义值：code如果为0表示登录成功，如果为1表示登录失败
            map.put("msg","登录成功！");
        }else{
            map.put("code",1);
            map.put("msg", "登录失败:密码错误");
        }
        return map;
    }

    /**
     * 读取指定账户
     * @return
     */
    @ApiOperation(value = "读取自己在当前登陆前台系统的管理账户信息", notes = "无需传入参数")
    @GetMapping
    public Map<String, Object>  getCustomer(HttpSession session){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("code",0);
        map.put("data",session.getAttribute("customer"));
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
            map.put("code", 0);
            map.put("msg", "注册成功！");
           }
        } else {
            map.put("code",1);
            map.put("msg", "重名，注册失败！");
        }
        return map;
    }

    //修改密码
    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPass", value = "原密码"),
            @ApiImplicitParam(name = "newPass", value = "新密码"),
            @ApiImplicitParam(name = "confirmPass", value = "确认密码")
    })
    @PutMapping("/updatePassword")
    public Map<String,Object> UpdatePassword(String oldPass,String newPass,String confirmPass,HttpSession session){
        Map<String,Object> map=new HashMap<String,Object>();
        Customer customer=(Customer)session.getAttribute("customer");
        if(customerService.login(customer.getUsername(), oldPass)!=null){//如果原密码正确
            if (newPass.equals("")){
                map.put("msg", "密码修改失败：新密码不能为空！");
            } else if(newPass.equals(confirmPass)){//如果新密码和确认密码相同
                //保存新密码
                map.put("code",0);
                customerService.updatePassword(newPass, customer.getId());
                map.put("msg", "密码修改成功！");
            }else{//如果不相同
                map.put("code",1);
                map.put("msg", "密码修改失败：新密码和确认密码不一致");
            }
        }else{//如果原密码错误
            map.put("code",1);
            map.put("msg", "密码修改失败：原密码不正确");
        }
        return map;
    }

    @ApiOperation(value = "前台客户注销")
    @GetMapping("/logout")
    public Map<String, Object> logout(HttpSession session){
        Map<String, Object> map=new HashMap<String, Object>();
        session.removeAttribute("customer");
        map.put("code", 0);
        map.put("msg", "成功注销用户");
        return map;
    }


}
