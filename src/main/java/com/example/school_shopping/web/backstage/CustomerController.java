package com.example.school_shopping.web.backstage;

import com.example.school_shopping.model.Customer;
import com.example.school_shopping.model.Product;
import com.example.school_shopping.model.base.Constant;
import com.example.school_shopping.model.base.JsonCode;
import com.example.school_shopping.model.base.PageObject;
import com.example.school_shopping.service.CustomerService;
import com.example.school_shopping.util.file.MyFileOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@Api(tags = "后台客户模块")
@RestController
@RequestMapping(value = "api/backstage/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @Value("${myFile.uploadFolder}")
    private String uploadFolder;//上传路径

    ////跳转到客户模块页面
    @GetMapping
    @ApiOperation(value = "读取客户信息")
    public Map<String,Object> tocustomer(HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String,Object>();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";//获取项目根目录网址
        List<Customer> list=customerService.getCustomerList(basePath);
        map.put("toal",list.size());
        map.put("data",list);
        map.put("code",0);
        return map;
    }

    @PostMapping
    @ApiOperation(value = "添加客户")
    public Map<String,Object> SaveCustomer(HttpSession session,Customer customer) {
        Map<String,Object> map=new HashMap<String,Object>();
        Customer customer1=(Customer)session.getAttribute("customer1");
        if (customer.getPassword().length() == 0) {
            map.put("msg", "账号密码不能为空!");
        } else if (customer.getUsername().length() == 0) {
            map.put("msg", "账号名不能为空");
        } else if(customerService.existsCustomer(customer.getUsername())==true){
            if(customerService.SaveCustomer(customer)){
                map.put("code","0");
                map.put("msg","用户添加成功,请登录！！！");
            }
        }else{
            map.put("msg","用户添加重名，请重试！！！");
            map.put("code","1");
        }
        return map;
    }


    //删除
    @ApiOperation(value = "删除客户")
    @DeleteMapping
    public Map<String,Object> DeleteCustomer(HttpSession session,Integer id) {
        Map<String,Object> map=new HashMap<String,Object>();
        Customer customer=(Customer)session.getAttribute("customer");
        customerService.deleteCustomer(id);
        map.put("code",0);
        map.put("msg", "删除成功！！！");
        return map;
    }

    //修改
    @ApiOperation(value = "客户编辑")
    @PutMapping
    public Map<String,Object> CustomerUpdate(HttpSession session,Customer customer) {
        Map<String,Object> map=new HashMap<String,Object>();
        customer.setName(customer.getName().trim());
        customer.setUsername(customer.getUsername().trim());
        if (customer.getUsername().equals("")) {
            map.put("msg", "编辑失败：客户账号名不能为空！");
        } else if (customer.getName().equals("")) {
            map.put("msg", "编辑失败:客户名字不能为空！");
        }  else if(customerService.existsCustomer(customer.getName())==true){
            if (customerService.updateCustomer(customer)) {
                map.put("code",0);
                map.put("msg", "客户信息编辑成功！");
            }
        } else {
                map.put("code",1);
                map.put("msg", "客户信息编辑失败！");
            }

        return map;
    }
    @ApiOperation(value = "批量删除客户信息", notes = "根据id的值删除客户信息")
    @ApiImplicitParam(name = "ids", value = "要删除的客户id集合", required = true,paramType = "path",example ="15,25,74" )
    @DeleteMapping("/{ids}")
    public Map<String, Object> deleteCustomers(@PathVariable("ids")Integer[] ids){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("code",0);
        map.put("msg", "删除成功！！！");
        customerService.deleteCustomers(ids);
        return map;
    }
    @ApiOperation(value = "上传指定客户的头像", notes = "根据id的值上传指定客户的头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "指定账户id", paramType = "path", required = true),
            @ApiImplicitParam(name = "file", value = "要上传的头像", paramType = "form", dataType="__file",required = true)
    })
    @PostMapping("/uploadPhoto/{id}")
    public Map<String, Object> uploadPhoto(@PathVariable Integer id,MultipartFile file) throws FileNotFoundException {
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("code", -1);//默认失败
        if(!file.isEmpty()){
            Customer customer=customerService.getCustomer(id);//获取账户对象
            if(customer!=null){//如果该账户存在，则执行上传
                //String basepath=ClassUtils.getDefaultClassLoader().getResource("").getPath();//获取项目的根目录(物理路径)，注意不能用JSP那套获取根目录，因为spring boot的tomcat为内置，每次都变
                String basepath=uploadFolder;
                String filePath=basepath+ Constant.CUSTOMER_PROFILE_PICTURE_UPLOAD_URL;//获取图片上传后保存的物理路径
                MyFileOperator.createDir(filePath);//创建存储目录
                String fileName=file.getOriginalFilename();//获取文件名
                String extensionName= MyFileOperator.getExtensionName(fileName);//获取文件扩展名
                fileName=id+"."+extensionName;//根据id重新生成文件名
                try {
                    file.transferTo(new File(filePath+fileName));
                    if(!fileName.equals(customer.getPicUrl())){//如果新上传的文件名和原来的不一样，则需要删除原来的文件；如果一样则直接覆盖，不需要处理
                        MyFileOperator.deleteFile(filePath+customer.getPicUrl());//删除原文件
                    }
                    customer=new Customer(id);
                    customer.setPicUrl(fileName);
                    customerService.updateCustomer(customer);//将新的图片信息存入数据库
                    map.put("code", 0);
                    map.put("msg", "上传成功");
                } catch (IOException e) {
                    map.put("msg", "头像上传失败："+e.getMessage());
                }
            }
        }else{
            map.put("msg", "上传失败：请先选择文件");
        }
        return map;
    }

}
