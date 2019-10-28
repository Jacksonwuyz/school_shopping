package com.example.school_shopping.web.backstage;

import com.example.school_shopping.model.Admin;
import com.example.school_shopping.model.AdminPasswordEditForm;
import com.example.school_shopping.model.base.Constant;
import com.example.school_shopping.service.AdminService;
import com.example.school_shopping.util.file.MyFileOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@Api(tags = "后台管理员模块")
@RestController
@RequestMapping(value = "api/backstage/adminmanage")
public class AdminController {
    @Resource
    private AdminService adminService;
    @Value("${myFile.uploadFolder}")
    private String uploadFolder;//上传路径
    @ApiOperation(value = "读取全部管理员账号信息")
    @GetMapping
    public Map<String,Object> toManageAdmin(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";//获取项目根目录网址
        List<Admin> list=adminService.getAdminList(basePath);
        map.put("toal",list.size());
        map.put("data",list);
        map.put("code",0);
        return map;
    }
    @ApiOperation(value = "执行删除管理员操作")
    @DeleteMapping
    public Map<String,Object> DeleteAdmin(@PathVariable HttpSession session,Integer id) {
        Map<String,Object> map=new HashMap<String,Object>();
        Admin admin=(Admin)session.getAttribute("admin");
        adminService.deleteAdmin(id,admin.getId());
        map.put("code",0);
        map.put("msg", "删除成功！！！");
        return map;
    }

    //修改
    @ApiOperation(value = "修改")
    @PutMapping
    public Map<String,Object> UpdateAdmin(@RequestBody Admin admin){
        Map<String,Object> map=new HashMap<String,Object>();

        if (admin.getUsername().equals("")){
            map.put("msg","账户名不能为空！");
        }else if (admin.getName().equals("")){
            map.put("msg","姓名不能为空！");
        }else if (adminService.existsAdmin(admin.getUsername())==false){
            map.put("msg","账户名重名！");
        }
        else {
            if(adminService.updateAdmin(admin)){
                map.put("code",0);
                map.put("msg","基本信息修改成功！");
            }else{
                map.put("code",1);
                map.put("msg", "基本信息修改失败！");
            }


        }
        return map;
    }

    //修改密码
    @ApiOperation(value = "修改密码")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "oldPass", value = "原密码"),
//            @ApiImplicitParam(name = "newPass", value = "新密码"),
//            @ApiImplicitParam(name = "confirmPass", value = "确认密码")
//    })
    @PutMapping("/updatePassword")
   public Map<String,Object> UpdatePassword(@PathVariable @RequestBody AdminPasswordEditForm adminPasswordEditForm, HttpSession session){
        Map<String,Object> map=new HashMap<String,Object>();
       Admin admin=(Admin)session.getAttribute("admin");
        if(adminService.login(admin.getUsername(), adminPasswordEditForm.getPassword())!=null){//如果原密码正确
           if (adminPasswordEditForm.getNewPass().equals("")){
               map.put("msg", "密码修改失败：新密码不能为空！");
            } else if(adminPasswordEditForm.getNewPass().equals(adminPasswordEditForm.getRePass())){//如果新密码和确认密码相同
               //保存新密码
               map.put("code",0);
               admin.setPassword(adminPasswordEditForm.getNewPass());
               adminService.updateAdmin(admin);
               map.put("msg", "密码修改成功！");
            }else{//如果不相同
               map.put("code",1);
               map.put("msg", "密码修改失败：新密码和确认密码不一致");
           }
        }else{//如果原密码错误
            map.put("code",1);
            map.put("msg", "密码修改失败：原密码不正确");
        }
        return map;
   }


    //执行管理员（后台）添加页面

    @ApiOperation(value = "后台管理员添加")
    @PostMapping("/addAdmin")
    public Map<String,Object> doSaveAdmin(Admin admin){
        Map<String,Object> map=new HashMap<String,Object>();
        admin.setUsername(admin.getUsername().trim());
       admin.setName(admin.getName().trim());
        if(admin.getUsername().length()==0){
            map.put("msg","账户添加失败:账户名不能为空");
        }else if(admin.getName().length()==0){
            map.put("msg","账户添加失败:网名不能为空");
        }else if(admin.getPassword().length()==0){
            map.put("msg","账户添加失败:密码不能为空");
        }else if(adminService.existsAdmin(admin.getUsername())==true){
            if(adminService.saveAdmin(admin)==true){
                map.put("code","0");
                map.put("msg","账户添加成功");
            }
        }else{
            map.put("msg","账户添加失败:重名");
            map.put("code","1");
        }
        return map;
    }

    @ApiOperation(value = "读取指定账户", notes = "根据id的值读取指定账户")
    @ApiImplicitParam(name = "id", value = "要读取的账户id", paramType = "path",dataType="int", required = true,example="1")
    @GetMapping("/{id}")
    public Map<String, Object>  getAdmin(@PathVariable Integer id){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("code", 0);
        map.put("data",adminService.getAdmin(id));
        return map;
    }
    @ApiOperation(value = "批量删除账户", notes = "根据id的值删除指定账户")
    @ApiImplicitParam(name = "ids", value = "要删除的账户id集合", required = true,paramType = "path",example ="15,25,74" )
    @DeleteMapping("/deletes/{ids}")
    public Map<String, Object> deleteAdmins(@PathVariable("ids")Integer[] ids, HttpSession session){
        Map<String, Object> map=new HashMap<String, Object>();
        Admin admin=(Admin)session.getAttribute("admin");
        adminService.deleteAdmins(admin,ids);
        map.put("code", 0);
        map.put("msg", "删除成功！！！");
        return map;
    }
    @ApiOperation(value = "批量移除管理员头像", notes = "根据id的值删除管理员头像")
    @ApiImplicitParam(name = "ids", value = "要移除头像的管理员id集合", required = true,paramType = "path",example ="15,25,74" )
    @PatchMapping("/removeAdminsProfilePicture/{ids}")
    public Map<String, Object> removeCustomersProfilePicture(@PathVariable("ids")Integer[] ids){
        Map<String, Object> map=new HashMap<String, Object>();
        String basePath=uploadFolder;
        adminService.removeAdminsProfilePicture(ids,basePath);
        map.put("code", 0);//默认失败
        return map;
    }
    @ApiOperation(value = "上传指定管理员的头像", notes = "根据id的值上传指定管理员的头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "指定账户id", paramType = "path", required = true),
            @ApiImplicitParam(name = "file", value = "要上传的头像", paramType = "form", dataType="__file",required = true)
    })
    @PostMapping("/uploadPhoto/{id}")
    public Map<String, Object> uploadPhoto(@PathVariable Integer id,MultipartFile file) throws FileNotFoundException {
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("code", -1);//默认失败
        if(!file.isEmpty()){
            Admin admin=adminService.getAdmin(id);//获取账户对象
            if(admin!=null){//如果该账户存在，则执行上传
                //String basepath=ClassUtils.getDefaultClassLoader().getResource("").getPath();//获取项目的根目录(物理路径)，注意不能用JSP那套获取根目录，因为spring boot的tomcat为内置，每次都变
                String basepath=uploadFolder;
                String filePath=basepath+ Constant.ADMIN_PROFILE_PICTURE_UPLOAD_URL;//获取图片上传后保存的物理路径
                MyFileOperator.createDir(filePath);//创建存储目录
                String fileName=file.getOriginalFilename();//获取文件名
                String extensionName= MyFileOperator.getExtensionName(fileName);//获取文件扩展名
                fileName=id+"."+extensionName;//根据id重新生成文件名
                try {
                    file.transferTo(new File(filePath+fileName));
                    if(!fileName.equals(admin.getPicUrl())){//如果新上传的文件名和原来的不一样，则需要删除原来的文件；如果一样则直接覆盖，不需要处理
                        MyFileOperator.deleteFile(filePath+admin.getPicUrl());//删除原文件
                    }
                    admin=new Admin(id);
                    admin.setPicUrl(fileName);
                    adminService.updateAdmin(admin);//将新的图片信息存入数据库
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
