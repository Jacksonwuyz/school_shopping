package com.example.school_shopping.web.backstage;

import com.example.school_shopping.model.ProductType;
import com.example.school_shopping.model.base.Constant;
import com.example.school_shopping.model.base.JsonCode;
import com.example.school_shopping.service.ProductTypeService;
import com.example.school_shopping.util.file.MyFileOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@Api(tags = "后台商品分类模块")
@RestController
@RequestMapping(value = "api/backstage/producttype")
public class ProductTypeController {
    @Resource
    private ProductTypeService productTypeService;

    @Value("${myFile.uploadFolder}")
    private String uploadFolder;//上传路径

    @ApiOperation(value = "读取商品信息")
    @GetMapping
    public Map<String,Object> Producttype(Integer page) {
        Map<String,Object> map=new HashMap<String,Object>();
        if (page == null) {//如果page为null，默认为第一页
            page = 1;
        } else {
            if (page < 1) {
                page = 1;
            }
        }
        map.put("data", productTypeService.getProductTypeList(page));//当前页显示的记录集合
        map.put("page", page);//当前页
        map.put("code",0);
        return map;
    }


    //执行产品分类添加页面

    @ApiOperation(value="产品分类添加")
    @PostMapping
    public Map<String,Object> SaveProductType(ProductType productType) {
        Map<String,Object> map=new HashMap<String,Object>();
        productType.setName(productType.getName().trim());
        if (productType.getName().length() == 0) {
            map.put("msg", "产品分类添加:产品名称不能为空");
        }else if (productType.getOrderNum() == null) {
            map.put("msg", "产品分类添加:产品优先级不能为空");
        } else if(productTypeService.existsProductType(productType.getName())==true){
            if (productTypeService.SaveProductType(productType)) {
                map.put("code",0);//自定义值：code如果为0表示登录成功，如果为1表示登录失败
                map.put("msg", "产品分类添加成功！！！");
            }
            } else {
                map.put("code", 1);
                map.put("msg", "重名,产品分类添加失败！！！");
            }

        return map;
    }

    //执行产品分类编辑操作   修改
    @ApiOperation(value = "产品分类编辑")
    @PutMapping
    public Map<String,Object> ProductTypeupdate(ProductType productType) {
        Map<String,Object> map=new HashMap<String,Object>();
        productType.setName(productType.getName().trim());
        if (productType.getName().length() == 0) {
            map.put("msg", "编辑失败：产品分类名不能为空！");
        }else if (productType.getOrderNum() == null) {
            map.put("msg", "产品分类编辑:产品优先级不能为空");
        } else if(productTypeService.existsProductType(productType.getName())==true){
            if (productTypeService.updateProductType(productType)) {
                map.put("code", 0);
                map.put("msg", "产品分类信息编辑成功！");
            }
            } else {
                map.put("code", 1);
                map.put("msg", "重名，产品分类信息编辑失败！");
            }
        return map;

    }

    //执行操作
    @ApiOperation(value = "删除产品分类")
    @DeleteMapping
    public Map<String, Object> deleteProductType(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        productTypeService.deleteProductType(id);
        map.put("codes", 0);
        map.put("msg", "删除成功！！！");
        return map;
    }

    @ApiOperation(value = "批量删除产品分类栏目信息", notes = "根据id的值删除产品栏目信息")
    @ApiImplicitParam(name = "ids", value = "要删除的产品栏目id集合", required = true,paramType = "path",example ="15,25,74" )
    @DeleteMapping("/{ids}")
    public  Map<String, Object> DeleteProductTypes(@PathVariable("ids")Integer[] ids){
        Map<String, Object> map=new HashMap<String, Object>();
        productTypeService.deleteProductTypes(ids);
        map.put("codes", 0);
        map.put("msg", "删除成功！！！");
        return map;
    }
    /**
     * 读取指定产品栏目
     * @param id 指定产品分类的主键
     * @return
     */
    @ApiOperation(value = "读取指定产品栏目信息", notes = "根据id的值读取指定产品栏目信息")
    @ApiImplicitParam(name = "id", value = "要读取的账户id", paramType = "path",dataType="int", required = true,example="1")
    @GetMapping("/{id}")
    public Map<String, Object>  getProductType(@PathVariable Integer id){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("codes", 0);
        map.put("data",productTypeService.getProductType(id));
        return map;
    }

    @ApiOperation(value = "上传指定产品栏目的图片", notes = "上传指定产品栏目的图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "指定产品栏目的id", paramType = "path", required = true,example="1"),
            @ApiImplicitParam(name = "file", value = "要上传的图片", paramType = "form", dataType="__file",required = true)
    })
    @PostMapping("/uploadPhoto/{id}")
    public Map<String, Object> uploadPhoto(@PathVariable Integer id, MultipartFile file){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("code", -1);//默认失败
        if(!file.isEmpty()){
            ProductType productType=productTypeService.getProductType(id);//获取对象
            if(productType!=null){//如果该对象存在，则执行上传
                //String basepath= ClassUtils.getDefaultClassLoader().getResource("").getPath();//获取项目的根目录，注意不能用JSP那套获取根目录，因为spring boot的tomcat为内置，每次都变
                String basepath=uploadFolder;;
                String filePath=basepath+Constant.PRODUCTTYPE_PICTURE_UPLOAD_URL;//获取图片上传后保存的物理路径
                MyFileOperator.createDir(filePath);//创建存储目录
                String fileName=file.getOriginalFilename();//获取文件名
                String extensionName=MyFileOperator.getExtensionName(fileName);//获取文件扩展名
                fileName=id+"."+extensionName;//根据id重新生成文件名
                try {
                    file.transferTo(new File(filePath+fileName));
                    if(!fileName.equals(productType.getImageUrl())){//如果新上传的文件名和原来的不一样，则需要删除原来的文件；如果一样则直接覆盖，不需要处理
                        MyFileOperator.deleteFile(filePath+productType.getImageUrl());//删除原文件
                    }
                    productType=new ProductType(id);
                    productType.setImageUrl(fileName);
                    productTypeService.updateProductType(productType);//将新的图片信息存入数据库
                    map.put("code", 0);
                    map.put("msg", "上传成功");
                } catch (IOException e) {
                    map.put("msg", "上传失败："+e.getMessage());
                }
            }
        }else{
            map.put("msg", "上传失败：请先选择文件");
        }
        return map;
    }
}
