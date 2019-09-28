package com.example.school_shopping.web;

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
@Api(tags = "后台管理员模块")
@RestController
@RequestMapping(value = "api/backstage/adminmanage")
public class AdminController {
    @Resource
    private AdminService adminService;

    @ApiOperation(value = "读取管理员账号信息")
    @GetMapping
    public Map<String,Object> toManageAdmin(Integer page){
        Map<String,Object> map=new HashMap<String,Object>();
        if (page==null){
            page=1;
        }else {
            if (page<1){
                page=1;
            }
        }
        map.put("list",adminService.getAdminList());
        map.put("list",adminService.getPartlist(page));
        map.put("page",page);
        return map;
    }

    @ApiOperation(value = "执行删除管理员操作")
    @DeleteMapping
    public Map<String,Object> DeleteAdmin(HttpSession session,Integer id) {
        Map<String,Object> map=new HashMap<String,Object>();
        Admin admin=(Admin)session.getAttribute("admin");
        adminService.deleteAdmin(id,admin.getId());
        map.put("status",1);
        return map;
    }

    //修改
    @ApiOperation(value = "修改")
    @PutMapping
    public Map<String,Object> UpdateAdmin(Admin admin,HttpSession session){
        Map<String,Object> map=new HashMap<String,Object>();

        if (admin.getUsername().equals("")){
            map.put("msg","账户名不能为空！");
        }else if (admin.getName().equals("")){
            map.put("msg","姓名不能为空！");
        }else if (adminService.existsAdmin(admin.getUsername())==false){
            map.put("msg","账户名重名！");
        }
        else {
            if(adminService.updateAdmin(admin)){
                map.put("code",1);
                map.put("msg","基本信息修改成功！");
            }else{
                map.put("code",-1);
                map.put("msg", "基本信息修改失败！");
            }


        }
        return map;
    }
}
