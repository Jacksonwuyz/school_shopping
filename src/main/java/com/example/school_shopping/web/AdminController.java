package com.example.school_shopping.web;

import com.example.school_shopping.model.Admin;
import com.example.school_shopping.service.AdminService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@RestController
public class AdminController {
    @Resource
    private AdminService adminService;

    //执行删除管理员操作
    @DeleteMapping(value = "api/backstage/adminmanage/DeleteAdmin")
    public Map<String,Object> DeleteAdmin(HttpSession session,Integer id) {
        Map<String,Object> map=new HashMap<String,Object>();
        Admin admin=(Admin)session.getAttribute("admin");
        adminService.deleteAdmin(id,admin.getId());
        map.put("status",1);
        return map;
    }

    @PutMapping(value = "api/backstage/admin/UpdateAdmin")
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
