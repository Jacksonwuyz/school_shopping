package com.example.school_shopping.web.backstage;

import com.example.school_shopping.model.Admin;
import com.example.school_shopping.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@Api(tags = "后台登录注册模块")
@RestController
@RequestMapping(value = "api/backstage")
public class LoginController {
    @Resource
    private AdminService adminService;
    //
    @ApiOperation(value = "后台登录")
    @GetMapping(value = "/login")
    public Map<String,Object> Login(String username,String password,HttpSession session){
        Map<String,Object> map=new HashMap<String,Object>();
        Admin admin=adminService.login(username,password);
        if (adminService.login(username, password)!=null){
            session.setAttribute("admin", admin);
            map.put("code",0);//自定义值：code如果为0表示登录成功，如果为1表示登录失败
            map.put("data",admin.getUsername());
            map.put("msg","登录成功！");
        }else {
            map.put("code",1);
            map.put("msg","登录失败：请重新输入账户名或者密码！");
        }
        return map;
    }

    /**
     * 读取指定账户
     * @return
     */
    @ApiOperation(value = "读取自己在当前登陆后台系统的管理账户信息", notes = "无需传入参数")
    @GetMapping
    public Map<String, Object>  getAdmin(HttpSession session){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("code",0);
        map.put("data",session.getAttribute("admin"));
        return map;
    }

    //执行管理员（后台）添加页面

    @ApiOperation(value = "后台注册")
    @PostMapping("/registered")
    public Map<String,Object> doSaveAdmin(Admin admin){
        Map<String,Object> map=new HashMap<String,Object>();
//        admin.setUsername(admin.getUsername().trim());
//        admin.setName(admin.getName().trim());
        if(admin.getUsername().length()==0){
            map.put("msg","账户添加失败:账户名不能为空");
        }else if(admin.getName().length()==0){
            map.put("msg","账户添加失败:网名不能为空");
        }else if(admin.getPassword().length()==0){
            map.put("msg","账户添加失败:密码不能为空");
        }else if(adminService.existsAdmin(admin.getUsername())==true){
            if(adminService.saveAdmin(admin)==true){
                map.put("code","0");
                map.put("msg","账户添加成功");
            }
        }else{
            map.put("msg","账户添加失败:重名");
            map.put("code","1");
        }
        return map;
    }

    /**
     * 注销
     * @return
     */
    @ApiOperation(value = "注销")
    @GetMapping("/logout")
    public Map<String, Object> logout(HttpSession session){
        Map<String, Object> map=new HashMap<String, Object>();
        session.removeAttribute("admin");
        map.put("code",0);
        map.put("msg", "成功注销用户");
        return map;
    }



}
