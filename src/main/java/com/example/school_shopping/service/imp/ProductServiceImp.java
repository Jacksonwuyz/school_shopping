package com.example.school_shopping.service.imp;

import com.example.school_shopping.dao.ProductDao;
import com.example.school_shopping.model.Product;
import com.example.school_shopping.model.base.Constant;
import com.example.school_shopping.model.base.PageObject;
import com.example.school_shopping.model.query.ProductQuery;
import com.example.school_shopping.service.ProductService;
import com.example.school_shopping.util.file.MyFileOperator;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService{
    @Resource
    private ProductDao productDao;


    public List<Product> getProductList(String basePath) {
        List<Product> productList=productDao.readAll();
        ProductQuery productQuery=null;//预设产品查询条件
        for(Product product:productList){
            //将头像网址进行处理，变为完整的地址
            if(!StringUtils.isEmpty(product.getPicUrl())){//只要有图片则加上绝对地址
                product.setPicUrl(basePath+ Constant.PRODUCT_PICTURE_URL+product.getPicUrl());
            }
            //获取栏目下的产品数量
            productQuery=new ProductQuery();
            int number=productDao.querySize(productQuery);
        }

        return productList;
    }
//分页
    @Override
    public PageObject priceProducts(Integer page, Integer limit, ProductQuery productQuery) {
        PageObject pageObject = new PageObject(limit,page,productDao.querySize(productQuery));
        pageObject.setList(productDao.query(pageObject.getOffset(),pageObject.getLimit(),productQuery));
        return pageObject;
    }
    @Override
    public List<Product> getShopProductTypelist(Integer productTypeId,String basePath) {
        List <Product> products=  productDao.getShopProductTypelist(productTypeId);
        for(Product product:products){
            //删除账户对应的图片
            if (!StringUtils.isEmpty(product.getPicUrl())) {//只要有图片则加上绝对地址
                product.setPicUrl(basePath+ Constant.PRODUCT_PICTURE_URL+product.getPicUrl());
            }

        }
        return products;
    }


    public boolean SaveProduct(Product product) {
        boolean stsatus = false;
        if (productDao.SaveProduct(product) ==1) {
            stsatus = true;
        } else {
            stsatus = false;
        }
        return stsatus;
    }

    public boolean deleteProduct(Integer id) {
        boolean status = false;//存储修改结果
        int n = productDao.deleteProduct(id);
        if (n == 1) {
            status = true;
        }
        return status;
    }


    public boolean existsProduct(String name) {
        if (productDao.existsProduct(name) == null) {
            return true;
        } else {
            return false;
        }
    }


    public boolean updateProduct(Product product) {
        boolean status = false;//存储修改结果
        if (productDao.updateProduct(product) == 1) {
            status = true;
        } else {
            status = false;
        }

        return status;
    }

    @Override
    public List<Product> getProductList(Integer page) {
        int pagesize = 6;//每页显示10条记录
        if (page==null){//如果page为null，默认为第一页
            page=1;
        }else {
            if (page<1){
                page=1;
            }
        }
        int offset = (page - 1) * pagesize + 1;//每页开始的记录数位置（仅在业务层使用，不考虑数据库）

        return productDao.getPartlist(offset - 1, pagesize);//数据库记录位置从0数起）
    }
    @Override
    public int maxPage(){
        int maxPage=0;//默认为0
        int pagesize=6;//每页显示6记录
        int total=productDao.total();//最大记录数
        if (total%pagesize==0){//%表示余数，比如35%5=5
            maxPage=total/pagesize;
        }else {
            maxPage=total/pagesize+1;
        }
        return  maxPage;
    }


    @Override
    public Product getProduct(Integer id) {
        Product product=null;
        if(id!=null){
            product=productDao.getProduct(id);
        }
        return product;
    }
    @Override
    public void deleteProducts(Integer[] ids){
        //如果商品有订单不允许删除
        productDao.deletes(ids);
    }
    @Override
    public  List<Product> searchProducts(String name){


        return productDao.searchProducts(name);
    }
    //批量移除头像
    @Override
    public void removeProductsProfilePicture(Integer[] ids, String basePath) {
        for(Integer id:ids){
            //删除账户对应的图片
            Product product=productDao.getProduct(id);//读取相应的记录
            String picUrl=product.getPicUrl();//获取头像地址
            if(!StringUtils.isEmpty(picUrl)){//如果头像存在
                product.setPicUrl("");//清空图片地址
                productDao.updateProduct(product);
                MyFileOperator.deleteFile(basePath+ Constant.PRODUCT_PICTURE_UPLOAD_URL+picUrl);//删除图片
            }
        }
    }
}
