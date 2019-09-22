package com.example.school_shopping.dao;

import com.example.school_shopping.model.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {


    Admin login(@Param(value = "username")String username,@Param(value = "password")String password);


    int saveAdmin(Admin admin);

    /**
     * 查找在数据库中和指定用户名重名的个数
     * @param username
     * @return 返回重名的个数，0表示不重名
     */
    Admin existsAdmin(@Param(value = "username")String username);

}
