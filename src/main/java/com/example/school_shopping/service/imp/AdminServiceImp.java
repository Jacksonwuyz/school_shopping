package com.example.school_shopping.service.imp;

import com.example.school_shopping.dao.AdminDao;
import com.example.school_shopping.dao.ProductDao;
import com.example.school_shopping.model.Admin;
import com.example.school_shopping.model.base.Constant;
import com.example.school_shopping.model.exception.MyServiceException;
import com.example.school_shopping.model.query.AdminQuery;
import com.example.school_shopping.model.query.ProductQuery;
import com.example.school_shopping.service.AdminService;
import com.example.school_shopping.util.SHA;
import com.example.school_shopping.util.file.MyFileOperator;
import org.springframework.stereotype.Service;
import com.example.school_shopping.model.exception.MyWebException;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImp implements AdminService {
    @Resource
    private AdminDao adminDao;
    @Resource
    private ProductDao productDao;
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

    //编辑（修改）
    public boolean updateAdmin(Admin admin) {
        boolean status = false;//存储修改结果
        if (admin.getPassword()!=null){
            admin.setPassword(SHA.getResult(admin.getPassword()));
        }
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
    public List<Admin> getAdminList(String basePath) {
        List<Admin> adminList=adminDao.readAll();
        AdminQuery adminQuery=null;//预设产品查询条件
        for(Admin admin:adminList){
            //将头像网址进行处理，变为完整的地址
            if(!StringUtils.isEmpty(admin.getPicUrl())){//只要有图片则加上绝对地址
                admin.setPicUrl(basePath+ Constant.ADMIN_PROFILE_PICTURE_URL+admin.getPicUrl());
            }
            //获取栏目下的产品数量
            adminQuery=new AdminQuery();
        }

        return adminList;
    }

  /*  @Override
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
    }*/
//修改密码
    public boolean updatePassword(String newPass, Integer id) {
        Boolean status = false;//默认编辑失败
        newPass = SHA.getResult(newPass);
        Admin admin = new Admin();
        admin.setId(id);
        admin.setPassword(newPass);
        if (adminDao.updateAdmin(admin) ==1) {
            status = true;
        }else {
            status = false;
        }
        return status;
    }
    @Override
    public Admin getAdmin(Integer id) {
        Admin admin=null;
        if(id!=null){
            admin=adminDao.getAdmin(id);
        }
        return admin;
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
/*批量删除*/
@Override
public void deleteAdmins(Admin admin,Integer[] ids) {
    //先进行验证
    for(Integer id:ids){
        Admin adminDelete=new Admin(id);
        if(admin.getId()==id.intValue()) {//如果登录账户的id与被删除账户的id一致
            throw new MyServiceException("删除失败：不允许删除自己的账户");
        }
        adminDelete=adminDao.getAdmin(id);//被删除账户的数据
        //检查有没有发布过产品
       ProductQuery productQuery=new ProductQuery();
        productQuery.setCreator(adminDelete);
        int saveProductNumber=productDao.querySize(productQuery);//该账户已经发布的产品数量
        if(saveProductNumber>0){
            throw new MyServiceException("删除失败：该账户（username）发布过number个产品"
                    .replace("username",admin.getUsername())
                    .replace("number",String.valueOf(saveProductNumber)));
        }
        //检查有没有编辑过产品（本项目中应该是有没有是最后编辑者）
         productQuery=new ProductQuery();
        productQuery.setFinalEditor(adminDelete);
        int updateProductNumber=productDao.querySize(productQuery);//该账户已经发布的产品数量
        if(updateProductNumber>0){
            throw new MyServiceException("删除失败：该账户(username)编辑（最后编辑者）过number个产品"
                    .replace("username",adminDelete.getUsername())
                    .replace("number",String.valueOf(updateProductNumber)));
        }
    }
    adminDao.deletes(ids);
}
    //批量移除头像
    @Override
    public void removeAdminsProfilePicture(Integer[] ids, String basePath) {
        for(Integer id:ids){
            //删除账户对应的图片
            Admin admin=adminDao.getAdmin(id);//读取相应的记录
            String picUrl=admin.getPicUrl();//获取头像地址
            if(!StringUtils.isEmpty(picUrl)){//如果头像存在
                admin.setPicUrl("");//清空图片地址
                adminDao.updateAdmin(admin);
                MyFileOperator.deleteFile(basePath+ Constant.ADMIN_PROFILE_PICTURE_UPLOAD_URL+picUrl);//删除图片
            }
        }
    }
}
