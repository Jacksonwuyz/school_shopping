package com.example.school_shopping.web.shop;

import com.example.school_shopping.model.base.PageObject;
import com.example.school_shopping.model.query.ProductQuery;
import com.example.school_shopping.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public static final Log log= LogFactory.getLog(ProductShopController.class);

    @Resource
    private ProductService productService;

    @ApiOperation(value = "获取所有产品分类下的产品信息", notes = "获取所有产品分类下的产品信息")
    @GetMapping(value = "/ShopProductTypeList")
    public Map<String, Object> shopProductTypelist(HttpServletRequest request,Integer productTypeId){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("data",productService.getShopProductTypelist(productTypeId));
        map.put("code",0);
        return map;
    }
    @ApiOperation(value = "搜索")
    @GetMapping(value = "/searchProducts")
    public Map<String, Object> searchProducts(String name){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("data",productService.searchProducts(name));
        map.put("code",0);
        return map;
    }

}
