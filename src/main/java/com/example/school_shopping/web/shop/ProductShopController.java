package com.example.school_shopping.web.shop;

import com.example.school_shopping.model.Product;
import com.example.school_shopping.model.ProductType;
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
import java.util.List;
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
    public Map<String, Object> shopProductlist(HttpServletRequest request,Integer productTypeId){
        Map<String,Object> map=new HashMap<String,Object>();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";//获取项目根目录网址
        List<Product> list=productService.getShopProductTypelist(productTypeId,basePath);
        map.put("total",list.size());
        map.put("data",list);
        map.put("code",0);
        return map;
    }

    @ApiOperation(value = "按价格从低到高，分页查询指定栏目（或所有栏目）已经上架的产品信息", notes = "如果page为空则默认是第一页;如果limit为空则采用服务器的默认数值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = false, dataType = "int",example="1"),
            @ApiImplicitParam(name = "limit", value = "每页最多展示的记录数", required = false, dataType = "int",example="10"),
            @ApiImplicitParam(name = "productTypeId", value = "所属产品栏目，如果不传值则查询所有栏目", required = false, dataType = "int",example="1")
    })
    @GetMapping(value = "/priceAscProducts")
    public Map<String, Object> priceAscProducts(Integer page, Integer limit,Integer productTypeId){
        Map<String, Object> map=new HashMap<String, Object>();
        //创建查询条件
        ProductQuery productQuery=new ProductQuery();
        productQuery.setOnSale(true);//前台只能访问上架的产品
        productQuery.setOrderType(8);//按当前价格降序排序排列
        if(productTypeId!=null){//如果有产品栏目
            productQuery.setProductType(new ProductType(productTypeId));
        }
        PageObject pageObject =productService.priceProducts(page,limit,productQuery);
        map.put("total",pageObject.getTotalRecords());
        map.put("data",pageObject.getList());
        map.put("code", 0);
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
