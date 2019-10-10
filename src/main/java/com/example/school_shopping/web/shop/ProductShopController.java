package com.example.school_shopping.web.shop;

import com.example.school_shopping.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//表示所有的请求都是@ResponseBody
@Api(tags = "前台产品模块")
@RestController
@RequestMapping(value = "api/shop/product")
public class ProductShopController {
    @Resource
    private ProductService productService;


}
