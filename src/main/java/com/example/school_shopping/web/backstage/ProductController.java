package com.example.school_shopping.web.backstage;

import com.example.school_shopping.dao.ProductDao;
import com.example.school_shopping.model.Product;
import com.example.school_shopping.service.ProductService;
import com.example.school_shopping.service.ProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
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


    //跳转到产品模块页面
    @ApiOperation(value = "读取商品信息")
   @GetMapping
    public Map<String,Object> toProduct(Integer page) {
        Map<String,Object> map=new HashMap<String,Object>();
        if (page == null) {//如果page为null，默认为第一页
            page = 1;
        } else {
            if (page < 1) {
                page = 1;
            }
        }
        map.put("data", productService.getProductList(page));//当前页显示的记录集合
        map.put("page", page);//当前页
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

}
