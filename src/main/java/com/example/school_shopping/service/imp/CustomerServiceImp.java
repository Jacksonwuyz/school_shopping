package com.example.school_shopping.service.imp;

import com.example.school_shopping.dao.CustomerDao;
import com.example.school_shopping.model.Admin;
import com.example.school_shopping.model.Customer;
import com.example.school_shopping.service.CustomerService;
import com.example.school_shopping.util.SHA;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {
    @Resource
    private CustomerDao customerDao;

    public List<Customer> getCustomerList(){
        return customerDao.getCustomerList();
    }



    //前端客户注册
    public boolean SaveCustomer(Customer customer) {
        boolean stsatus = false;
        customer.setPassword(SHA.getResult("123456"));
        customer.setCreateTime(new Date());//系统当前时间为创建日期
        if (customerDao.SaveCustomer(customer)>0){
            stsatus= true;
        }else {
            stsatus= false;
        }
        return stsatus;
    }
    //重名
    public boolean existsCustomer(String username){
        if(customerDao.existsCustomer(username)==null){
            return true;
        }else{
            return false;
        }
    }
    //登录
    public Customer login(String username, String password) {
        if (password.length()!=32){
            //将密码加密后再进行比对
            password = SHA.getResult(password);
        }
        Customer customer = customerDao.login(username, password);
        return customer;
    }


    //删除
    public boolean deleteCustomer(Integer id){
        boolean status=false;//存储修改结果
        int n=customerDao.deleteCustomer(id);
        if(n==1){
            status=true;
        }
        return status;
    }
    //修改
    public boolean updateCustomer(Customer customer){
        boolean status=false;//存储修改结果
        if(customerDao.updateCustomer(customer)==1){
            status=true;
        }else{
            status=false;
        }

        return status;
    }
//前台注册
    public boolean SaveShopCustomer(Customer customer) {
        boolean stsatus = false;//默认注册失败！
     customer.setPassword(SHA.getResult(customer.getPassword()));
        customer.setCreateTime(new Date());//系统当前时间为创建日期
        if (customerDao.SaveShopCustomer(customer)==1){
            stsatus= true;
        }else {
            stsatus= false;
        }
        return stsatus;
    }

    public List<Customer> getCustomerList(Integer page) {
        int pagesize = 10;//每页显示3条记录
        if (page==null){//如果page为null，默认为第一页
            page=1;
        }else {
            if (page<1){
                page=1;
            }
        }
        int offset = (page - 1) * pagesize + 1;//每页开始的记录数位置（仅在业务层使用，不考虑数据库）

        return customerDao.getPartlist(offset - 1, pagesize);//数据库记录位置从0数起）
    }
    @Override
    public int maxPage(){
        int maxPage=0;//默认为0
        int pagesize=10;//每页显示3记录
        int total=customerDao.total();//最大记录数
        if (total%pagesize==0){//%表示余数，比如35%5=5
            maxPage=total/pagesize;
        }else {
            maxPage=total/pagesize+1;
        }
        return  maxPage;
    }

    //修改密码
    public boolean updatePassword(String newPass, Integer id) {
        Boolean status = false;//默认编辑失败
        newPass = SHA.getResult(newPass);
        Customer customer = new Customer();
        customer.setId(id);
        customer.setPassword(newPass);
        if (customerDao.updateCustomer(customer) ==1) {
            status = true;
        }else {
            status = false;
        }
        return status;
    }
    @Override
    public Customer getCustomer(Integer id) {
        Customer customer=null;
        if(id!=null){
            customer=customerDao.getCustomer(id);
        }
        return customer;
    }
    @Override
    public void deleteCustomers(Integer[] ids){
        for(Integer id:ids){
            //删除账户对应的图片
            Customer customer=customerDao.getCustomer(id);//读取相应的记录
            String picUrl=customer.getPicUrl();//获取头像地址
           /* if(!StringUtils.isEmpty(picUrl)){//如果头像存在
                throw new MyServiceException("账户删除失败：请先删除头像，再执行账户删除");
            }*/
        }
        customerDao.deletes(ids);
    }

}
