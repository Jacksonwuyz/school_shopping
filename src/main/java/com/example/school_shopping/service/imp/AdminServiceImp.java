package com.example.school_shopping.service.imp;

import com.example.school_shopping.dao.AdminDao;
import com.example.school_shopping.model.Admin;
import com.example.school_shopping.service.AdminService;
import com.example.school_shopping.util.SHA;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    //查询
    public List<Admin> getAdminList() {
        return adminDao.getAdminList();
    }

    @Override
    public List<Admin> getPartlist(Integer page) {
        int pagesize = 10;//每页显示10条记录
        if (page==null){//如果page为null，默认为第一页
            page=1;
        }else {
            if (page<1){
                page=1;
            }
        }
        int offset = (page - 1) * pagesize + 1;//每页开始的记录数位置（仅在业务层使用，不考虑数据库）

        return adminDao.getPartlist(offset - 1, pagesize);//数据库记录位置从0数起）
    }
}
