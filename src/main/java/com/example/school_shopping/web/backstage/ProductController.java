package com.example.school_shopping.web.backstage;

import com.example.school_shopping.dao.ProductDao;
import com.example.school_shopping.model.Product;
import com.example.school_shopping.model.ProductType;
import com.example.school_shopping.model.base.Constant;
import com.example.school_shopping.service.ProductService;
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
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@Api(tags = "后台商品模块")
@RestController
@RequestMapping(value = "api/backstage/product")
public class ProductController {
    @Resource
    private ProductService productService;
    @Resource
    private ProductTypeService productTypeService;
    @Value("${myFile.uploadFolder}")
    private String uploadFolder;//上传路径

    //跳转到产品模块页面
    @ApiOperation(value = "读取商品信息")
   @GetMapping
    public Map<String,Object> toProduct(HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String,Object>();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";//获取项目根目录网址
        List<Product> list=productService.getProductList(basePath);
        map.put("toal",list.size());
        map.put("data",list);
        map.put("code",0);
        return map;
    }


//执行产品添加页面

    @ApiOperation(value = "产品添加")
    @PostMapping
    public Map<String,Object> SaveProduct(Product product) {
        Map<String,Object> map=new HashMap<String,Object>();
        product.setName(product.getName().trim());
        if (product.getName().length() == 0) {
            map.put("myMessage", "产品添加：产品名称不能为空！");
        } else if (product.getProductType()==null) {
            map.put("myMessage", "产品添加:产品分类不能为空");
        }else if (product.getOrderNum() == null) {
            map.put("myMessage", "产品添加:产品优先级不能为空");
        } else if (product.getPrice() == null) {
           map.put("myMessage", "产品添加:产品现价不能为空");
        } else if (product.getOriginalPrice() == null) {
            map.put("myMessage", "产品添加:产品原价不能为空");
        } else if (product.getClick() == null) {
            map.put("myMessage", "产品添加:产品点击数不能为空");
        } else if(productService.existsProduct(product.getName())==true){
            if (productService.SaveProduct(product)) {
                map.put("product", null);
                map.put("code", 0);
                map.put("msg", "产品添加成功！！！");
              }
        } else {
            map.put("code", 1);
            map.put("msg", "重名，产品添加失败！！！");

         }
        return map;
    }


    //执行删除产品分类操作
    @ApiOperation(value = "删除产品")
    @DeleteMapping
    public Map<String, Object> DeleteProduct(Integer id) {
        Map<String,Object> map=new HashMap<String,Object>();
        productService.deleteProduct(id);
        map.put("code", 0);
        map.put("msg", "删除成功！！！");
        return map;
    }

    //执行产品编辑操作
    @ApiOperation(value = "产品编辑操作")
    @PutMapping
    public Map<String, Object> UpdateProduct(Product product) {
        Map<String,Object> map=new HashMap<String,Object>();
        product.setName(product.getName().trim());
        if (product.getName().length() == 0) {
            map.put("msg", "产品分类编辑：产品名称不能为空！");
        }else if (product.getOrderNum() == null) {
            map.put("msg", "产品分类编辑:产品优先级不能为空");
        } else if (product.getPrice() == null) {
            map.put("msg", "产品分类编辑:产品现价不能为空");
        } else if (product.getOriginalPrice() == null) {
            map.put("msg", "产品分类编辑:产品原价不能为空");
        } else if (product.getClick() == null) {
            map.put("msg", "产品分类编辑:产品点击数不能为空");
        } else if (product.getOnSale() == null) {
            map.put("msg", "产品分类编辑:产品是否上架不能为空");
        } else  if(productService.existsProduct(product.getName())==true){
            if (productService.updateProduct(product)==true) {
                map.put("code", 0);
                map.put("msg", "产品分类信息编辑成功！");
            }
        }else {
                map.put("code", 1);
                map.put("msg", "重名,产品分类信息编辑失败！");
            }

        return map;

    }

    @ApiOperation(value = "读取指定产品信息", notes = "根据id的值读取指定产品信息")
    @ApiImplicitParam(name = "id", value = "要读取的产品信息id", paramType = "path", required = true,example="1")
    @GetMapping("/{id}")
    public Map<String, Object>  getProduct(@PathVariable Integer id){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("code", 0);
        map.put("data",productService.getProduct(id));
        return map;
    }
    @ApiOperation(value = "批量删除指定的多个产品", notes = "批量删除指定的多个产品")
    @ApiImplicitParam(name = "ids", value = "要删除的产品id集合", required = true,paramType = "path",example ="15,25,74" )
    @DeleteMapping("/{ids}")
    public Map<String, Object> deleteProducts(@PathVariable("ids")Integer[] ids){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("code", 0);
        productService.deleteProducts(ids);
        map.put("msg", "删除成功！！！");
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
            Product product=productService.getProduct(id);//获取对象
            if(product!=null){//如果该对象存在，则执行上传
                //String basepath= ClassUtils.getDefaultClassLoader().getResource("").getPath();//获取项目的根目录，注意不能用JSP那套获取根目录，因为spring boot的tomcat为内置，每次都变
                String basepath=uploadFolder;;
                String filePath=basepath+ Constant.PRODUCT_PICTURE_UPLOAD_URL;//获取图片上传后保存的物理路径
                MyFileOperator.createDir(filePath);//创建存储目录
                String fileName=file.getOriginalFilename();//获取文件名
                String extensionName=MyFileOperator.getExtensionName(fileName);//获取文件扩展名
                fileName=id+"."+extensionName;//根据id重新生成文件名
                try {
                    file.transferTo(new File(filePath+fileName));
                    if(!fileName.equals(product.getPicUrl())){//如果新上传的文件名和原来的不一样，则需要删除原来的文件；如果一样则直接覆盖，不需要处理
                        MyFileOperator.deleteFile(filePath+product.getPicUrl());//删除原文件
                    }
                    product=new Product(id);
                    product.setPicUrl(fileName);
                    productService.updateProduct(product);//将新的图片信息存入数据库
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
