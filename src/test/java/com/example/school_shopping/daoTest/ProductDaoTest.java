package com.example.school_shopping.daoTest;

import com.example.school_shopping.SchoolShoppingApplicationTests;
import com.example.school_shopping.dao.ProductDao;
import com.example.school_shopping.model.Product;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class ProductDaoTest extends SchoolShoppingApplicationTests {

    @Resource
    private ProductDao productDao;

    @Test
    public void testGetProductList(){
        List<Product> list=productDao.getProductList();
        for (int i=0;i<list.size();i++){
            Product product=list.get(i);
            System.out.println(product.getName()+"\t");
            System.out.println();
        }
        System.out.println(productDao.getProductList());
    }
    @Test
    public void testSaveProduct(){
        Product product=new Product();
        product.setName("猕猴桃蛋糕");
        product.setProductTypeId(1);
        product.setNumber(100);
        product.setOnSale(true);
        product.setPrice(null);
        product.setOrderNum(100);
        //  product.setOriginalPrice();
        product.setClick(100);
        product.setCreateTime(new Date());

        System.out.println( productDao.SaveProduct(product));

    }
}
