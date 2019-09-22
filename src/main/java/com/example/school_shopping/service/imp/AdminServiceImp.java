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

    public Admin login(String username,String password){
        Admin admin=adminDao.login(username, password);
        return admin;
    }



    public boolean existsAdmin(String username) {
        if (adminDao.existsAdmin(username)==null) {
            return true;
        } else {
            return false;
        }
    }


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

}
