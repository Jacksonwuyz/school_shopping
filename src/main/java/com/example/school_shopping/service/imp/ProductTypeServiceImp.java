package com.example.school_shopping.service.imp;

import com.example.school_shopping.dao.ProductTypeDao;
import com.example.school_shopping.model.ProductType;
import com.example.school_shopping.service.ProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductTypeServiceImp implements ProductTypeService{
    @Resource
    private ProductTypeDao productTypeDao;

    //添加
    public boolean SaveProductType(ProductType productType) {
        boolean stsatus = false;
        if (productTypeDao.SaveProductType(productType) == 1) {
            stsatus = true;
        } else {
            stsatus = false;
        }
        return stsatus;
    }

    //修改
    public boolean updateProductType(ProductType productType) {
        boolean status = false;//存储修改结果
        if (productTypeDao.updateProductType(productType) == 1) {
            status = true;
        } else {
            status = false;
        }

        return status;
    }

    //删除
    public boolean deleteProductType(Integer id) {
        boolean status = false;//存储修改结果
        int n = productTypeDao.deleteProductType(id);
        if (n == 1) {
            status = true;
        }
        return status;
    }
//重名
    public boolean existsProductType(String name) {
        if (productTypeDao.existsProductType(name) == null) {
            return true;
        } else {
            return false;
        }
    }
}
