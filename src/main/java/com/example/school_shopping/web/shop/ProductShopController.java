package com.example.school_shopping.web.shop;

import com.example.school_shopping.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//表示所有的请求都是@ResponseBody
@Api(tags = "前台产品模块")
@RestController
@RequestMapping(value = "api/shop/product")
public class ProductShopController {
    @Resource
    private ProductService productService;

    @ApiOperation(value = "获取所有产品分类信息", notes = "获取所有产品分类信息")
    @GetMapping(value = "/ShopProductTypeList")
    public Map<String, Object> shopProductTypelist(HttpServletRequest request,Integer productTypeId){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("data",productService.getShopProductTypelist(productTypeId));
        map.put("maxPage", productService.maxPage());//最大也
        map.put("code",0);
        return map;
    }

}
