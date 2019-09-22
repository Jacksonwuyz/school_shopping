package com.example.school_shopping.web;

import com.example.school_shopping.model.Admin;
import com.example.school_shopping.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@RestController

public class LoginController {
    @Resource
    private AdminService adminService;

    @GetMapping(value = "/api/login")
    public Map<String,Object> Login(String username,String password){
        Map<String,Object> map=new HashMap<String,Object>();
        username="user";
        password="123456";
        if (adminService.login(username, password)!=null){
            map.put("code",0);//自定义值：status如果为0表示登录成功，如果为-1表示登录失败
        }else {
            map.put("code",-1);
            map.put("msg","登录失败：请重新输入账户名或者密码！");
        }
        return map;
    }


    //执行管理员添加页面

    @PutMapping(value = "api/backstage/adminmanage/saveAdmin")
    public Map<String,Object> doSaveAdmin(HttpServletRequest request, Admin admin){
        Map<String,Object> map=new HashMap<String,Object>();
//        admin.setUsername(admin.getUsername().trim());
//        admin.setName(admin.getName().trim());
        if(admin.getUsername().length()==0){
            map.put("msg","账户创建失败:账户名不能为空");
        }else if(admin.getName().length()==0){
            map.put("msg","账户创建失败:网名不能为空");
    }  else if(adminService.existsAdmin(admin.getUsername())==true){
            if(adminService.saveAdmin(admin)==true){
                map.put("code","0");
                map.put("msg","账户创建成功");
           }
        }else{
            map.put("msg","账户创建失败:重名");
            map.put("code","-1");
        }
        return map;
    }
}
