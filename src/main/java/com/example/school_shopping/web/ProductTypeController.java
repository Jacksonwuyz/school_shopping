package com.example.school_shopping.web;

import com.example.school_shopping.model.ProductType;
import com.example.school_shopping.service.ProductTypeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@RestController
public class ProductTypeController {
    @Resource
    private ProductTypeService productTypeService;

    //执行产品分类添加页面

    @PostMapping(value = "api/backstage/producttype/SaveProductType")
    public Map<String,Object> SaveProductType(ProductType productType) {
        Map<String,Object> map=new HashMap<String,Object>();
        productType.setName(productType.getName().trim());
        if (productType.getName().length() == 0) {
            map.put("msg", "产品分类添加:产品名称不能为空");
        }else if (productType.getOrderNum() == null) {
            map.put("msg", "产品分类添加:产品优先级不能为空");
        } else if(productTypeService.existsProductType(productType.getName())==true){
            if (productTypeService.SaveProductType(productType)) {
                map.put("code", 1);//自定义值：code如果为1表示登录成功，如果为-1表示登录失败
                map.put("msg", "产品分类添加成功！！！");
            }
            } else {
                map.put("code", -1);
                map.put("msg", "重名,产品分类添加失败！！！");
            }

        return map;
    }

    //执行产品分类编辑操作   修改
    @PutMapping(value = "api/backstage/producttype/ProductTypeupdate")
    public Map<String,Object> ProductTypeupdate(ProductType productType) {
        Map<String,Object> map=new HashMap<String,Object>();
        productType.setName(productType.getName().trim());
        if (productType.getName().length() == 0) {
            map.put("msg", "编辑失败：产品分类名不能为空！");
        }else if (productType.getOrderNum() == null) {
            map.put("msg", "产品分类编辑:产品优先级不能为空");
        } else if(productTypeService.existsProductType(productType.getName())==true){
            if (productTypeService.updateProductType(productType)) {
                map.put("code", 1);
                map.put("msg", "产品分类信息编辑成功！");
            }
            } else {
                map.put("code", -1);
                map.put("msg", "重名，产品分类信息编辑失败！");
            }
        return map;

    }

    //执行删除产品分类操作
    @DeleteMapping(value = "api/backstage/producttype/deleteProductType")
    public Map<String, Object> deleteProductType(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        productTypeService.deleteProductType(id);
        map.put("status", 1);
        return map;
    }


}
