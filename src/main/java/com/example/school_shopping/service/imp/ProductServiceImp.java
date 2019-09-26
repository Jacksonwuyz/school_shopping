package com.example.school_shopping.service.imp;

import com.example.school_shopping.dao.ProductDao;
import com.example.school_shopping.model.Product;
import com.example.school_shopping.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductServiceImp implements ProductService{
    @Resource
    private ProductDao productDao;


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

}
