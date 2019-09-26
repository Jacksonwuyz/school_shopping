package com.example.school_shopping.service.imp;

import com.example.school_shopping.dao.AdminDao;
import com.example.school_shopping.model.Admin;
import com.example.school_shopping.service.AdminService;
import com.example.school_shopping.util.SHA;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Date;

@Service
public class AdminServiceImp implements AdminService {
    @Resource
    private AdminDao adminDao;
//登录
    public Admin login(String username,String password){
        if (password.length()!=32){
            //将密码加密后再进行比对
            password = SHA.getResult(password);
        }
        Admin admin=adminDao.login(username, password);
        return admin;
    }

//重名

    public boolean existsAdmin(String username) {
        if (adminDao.existsAdmin(username)==null) {
            return true;
        } else {
            return false;
        }
    }

//注册
    public boolean saveAdmin(Admin admin) {
        boolean stsatus = false;
        admin.setPassword(SHA.getResult("123456"));
        admin.setCreateTime(new Date());//系统当前时间为创建日期
        if (adminDao.saveAdmin(admin)>0) {
            stsatus = true;
        } else {
            stsatus = false;
        }
        return stsatus;
    }

    //删除
    public boolean deleteAdmin(Integer id, Integer adminId) {
        boolean status = false;//存储修改结果
        if (id != null && adminId != null) {
            if (adminId != id.intValue()) {//如果不是自己删除自己
                int n = adminDao.deleteAdmin(id);
                if (n == 1) {
                    status = true;
                }
            }
        }
        return status;
    }

    //编辑（修改）
    public boolean updateAdmin(Admin admin) {
        boolean status = false;//存储修改结果
        if (adminDao.existsAdmin(admin.getUsername())==null) {//如果不重名
            if (adminDao.updateAdmin(admin) == 1) {
                status = true;
            } else {
                status = false;
            }
        }
        return status;
    }
}
