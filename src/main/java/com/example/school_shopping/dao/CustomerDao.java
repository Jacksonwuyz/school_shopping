package com.example.school_shopping.dao;

import com.example.school_shopping.model.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerDao {

    /**
     * 返回所有的管理账户集合
     * @return 以List方式返回
     */
    List<Customer> getCustomerList();

    /*前台注册*/
    int SaveCustomer(Customer customer);

    /*前台注册*/
    int SaveShopCustomer(Customer customer);

    /**
     * 查找在数据库中和指定用户名重名的个数
     * @param username
     * @return 返回重名的个数，0表示不重名
     */
    Customer existsCustomer(@Param(value = "username") String username);

    /*shop登录页面*/

    Customer login(@Param(value = "username") String username, @Param(value = "password") String password);


    //删除
    /**
     * 删除指定账户
     * @param id 关键字
     * @return 删除了多少条记录
     */
    int deleteCustomer(Integer id);

    /**
     * 修改账户的基本信息
     * @return 更改了多少条记录
     */
    int updateCustomer(Customer customer);

    /*
     *  根据标识符获取相应的管理账户对象
     *  @param id
     *  @return null 表示没有找到
     * */
    Customer getCustomer(Integer id);

 /*  * 分页显示数据库记录
        *
        * */

    List<Customer> getPartlist(@Param(value = "offset")int offset,@Param(value = "length")int length);

    /*
          * 获取数据库总记录
          *
          * */
    int total();
    /**
     * 获取表中所有记录
     * @return
     */
    List<Customer> readAll();
    /**
     * 根据标志符集合删除对应的记录信息集合
     * @param ids id集合
     * @return  删除的记录数，>=1表示删除成功，0表示删除失败
     */
    int deletes(Integer[] ids);
    /**
     * 获取查询记录数，一般与query配合使用
     * @param objectQuery 查询条件类
     * @return
     */
    int querySize(@Param(value = "objectQuery")Object objectQuery);
}
