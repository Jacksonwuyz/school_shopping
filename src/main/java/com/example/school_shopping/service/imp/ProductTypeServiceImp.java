package com.example.school_shopping.service.imp;

import com.example.school_shopping.dao.ProductTypeDao;
import com.example.school_shopping.model.ProductType;
import com.example.school_shopping.service.ProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductTypeServiceImp implements ProductTypeService{
    @Resource
    private ProductTypeDao productTypeDao;


    public List<ProductType> getProductTypeList() {
        return productTypeDao.getProductTypeList();
    }

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

    public List<ProductType> getProductTypeList(Integer page) {
        int pagesize =10;//每页显示10条记录
        if (page==null){//如果page为null，默认为第一页
            page=1;
        }else {
            if (page<1){
                page=1;
            }
        }
        int offset = (page - 1) * pagesize + 1;//每页开始的记录数位置（仅在业务层使用，不考虑数据库）

        return productTypeDao.getPartlist(offset - 1, pagesize);//数据库记录位置从0数起）
    }

    public ProductType getProductType(Integer id) {
        ProductType productType = null;
        if (id != null) {
            productType = productTypeDao.getProductType(id);
        }
        return productType;
    }

    public List<ProductType> getProductTypeShopList(){
        return  productTypeDao.getProductTypeShopList();
    }

    public void deleteProductTypes(Integer[] ids) {
       /* for(Integer id:ids){
            //删除账户对应的图片
            ProductType productType=productTypeDao.getProductType(id);//读取相应的记录
            if(productType!=null){
                //获取栏目下产品数量
                ProductQuery productQuery=new ProductQuery();
                productQuery.setProductType(productType);
                int number=productDao.querySize(productQuery);
                if(number>0){
                    throw new MyServiceException("栏目批量删除失败：栏目（name)下有number个产品"
                            .replace("name",productType.getName())
                            .replace("number",String.valueOf(number)));
                }
                String imageUrl=productType.getImageUrl();//获取图片地址
                if(!StringUtils.isEmpty(imageUrl)){//如果图片地址存在
                    MyFileOperator.deleteFile(basePath+ Constant.PRODUCTTYPE_PICTURE_UPLOAD_URL+imageUrl);//删除图片
                }
            }
        }*/
        productTypeDao.deletes(ids);
    }

}
