package com.example.school_shopping.web;

import com.example.school_shopping.model.Admin;
import com.example.school_shopping.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public Map<String,Object> Login(String username,String password){
        Map<String,Object> map=new HashMap<String,Object>();
        if (adminService.login(username, password)!=null){
            map.put("code",1);//自定义值：code如果为1表示登录成功，如果为-1表示登录失败
        }else {
            map.put("code",-1);
            map.put("msg","登录失败：请重新输入账户名或者密码！");
        }
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
                map.put("code","1");
                map.put("msg","账户添加成功");
            }
        }else{
            map.put("msg","账户添加失败:重名");
            map.put("code","-1");
        }
        return map;
    }

    //修改密码

//    @RequestMapping(value = "/backstage/admin/doUpdatePassword", method = RequestMethod.POST)
//    public String doUpdatePassword(String oldPass,String newPass,String confirmPass,HttpSession session
//            ,HttpServletRequest request){
//        Admin admin=(Admin)session.getAttribute("admin");
//        if(adminService.login(admin.getUsername(), oldPass)!=null){//如果原密码正确
//            if (newPass.equals("")){
//                request.setAttribute("myMessage", "密码修改失败：新密码不能为空！");
//            } else if(newPass.equals(confirmPass)){//如果新密码和确认密码相同
//                //保存新密码
//                adminService.updatePassword(newPass, admin.getId());
//                request.setAttribute("myMessage", "密码修改成功！");
//            }else{//如果不相同
//                request.setAttribute("myMessage", "密码修改失败：新密码和确认密码不一致");
//            }
//        }else{//如果原密码错误
//            request.setAttribute("myMessage", "密码修改失败：原密码不正确");
//        }
//        return "/jsp/backstage/admin/passwordupdate.jsp";
//    }


}
