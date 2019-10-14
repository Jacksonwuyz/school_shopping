package com.example.school_shopping.dao;

import com.example.school_shopping.model.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    /**分页显示数据库记录
     * 返回所有的管理账户集合
     * @return 以List方式返回
     */
    List<Admin> getAdminList();

        /*
        * 分页显示数据库记录
        *
        * */

    List<Admin> getPartlist(@Param(value = "offset")int offset,@Param(value = "length")int length);

    /*
     *  根据标识符获取相应的管理账户对象
     *  @param id
     *  @return null 表示没有找到
     * */
    Admin getAdmin(Integer id);

    /**
     * 根据标志符集合删除对应的记录信息集合
     * @param ids id集合
     * @return  删除的记录数，>=1表示删除成功，0表示删除失败
     */
    int deletes(java.io.Serializable[] ids);
}
