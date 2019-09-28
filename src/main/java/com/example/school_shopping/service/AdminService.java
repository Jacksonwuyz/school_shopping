package com.example.school_shopping.service;

import com.example.school_shopping.model.Admin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {


    //登录
    Admin login(String username, String password);


    /**
     * 查找在数据库中和指定用户名重名的个数
     *
     * @param username
     * @param id
     * @return 返回重名的个数
     */
    boolean existsAdmin(String username);


//添加
    boolean saveAdmin(Admin admin);

    /**
     * 删除指定账户
     *
     * @param id      被删除的账户id
     * @param adminId 执行删除的管理账户
     * @return true表示删除成功
     */
    boolean deleteAdmin(Integer id, Integer adminId);


    /**
     * 修改账户的基本信息
     * 说明：
     * 1、修改后的账户名不能与其他账户的账户名重名
     *
     * @param
     * @param
     * @param
     * @return false表示修改失败，true表示修改成功
     */
    boolean updateAdmin(Admin admin);

    /**
     * 返回所有的管理账户集合
     *
     * @return 以List方式返回
     */
    List<Admin> getAdminList();

    /*分页
  * */
    List<Admin> getPartlist(Integer page);
}
