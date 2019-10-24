package com.example.school_shopping.web.shop;

import com.example.school_shopping.model.ProductType;
import com.example.school_shopping.service.ProductService;
import com.example.school_shopping.service.ProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@Api(tags = "前台产品分类")
@RestController
@RequestMapping(value = "api/shop/producttype")
public class ProductTypeShopController {

    @Resource
    private ProductTypeService productTypeService;

    @ApiOperation(value = "获取所有产品分类信息", notes = "获取所有产品分类信息")
    @GetMapping(value = "/listProductType")
    public Map<String, Object> listProductType(HttpServletRequest request){
        Map<String, Object> map=new HashMap<String, Object>();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";//获取项目根目录网址
        List<ProductType> list=productTypeService.getProductTypeList(basePath);
        map.put("data",list);
        map.put("code", 0);
        return map;
    }

}
