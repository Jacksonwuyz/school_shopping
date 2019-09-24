package com.example.school_shopping.dao;

import com.example.school_shopping.model.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {


    Admin login(@Param(value = "username") String username, @Param(value = "password") String password);


    int saveAdmin(Admin admin);

    /**
     * 查找在数据库中和指定用户名重名的个数
     * @param username
     * @return 返回重名的个数，0表示不重名
     */
    Admin existsAdmin(@Param(value = "username") String username);


    /**
     * 删除指定账户
     * @param id 关键字
     * @return 删除了多少条记录
     */
    int deleteAdmin(Integer id);


//    修改
       int updateAdmin(Admin admin);

}
