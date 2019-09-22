package com.example.school_shopping.service;

import com.example.school_shopping.model.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    Admin login(String username,String password);


    /**
     * 查找在数据库中和指定用户名重名的个数
     *
     * @param username
     * @param id
     * @return 返回重名的个数
     */
    boolean existsAdmin(String username);



    boolean saveAdmin(Admin admin);
}
