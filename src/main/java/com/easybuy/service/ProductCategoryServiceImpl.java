package com.easybuy.service;

import com.easybuy.dao.ProductCategoryMapper;
import com.easybuy.dao.ProductMapper;
import com.easybuy.entity.ProductCategoryVo;
import com.easybuy.entity.Product;
import com.easybuy.entity.ProductCategory;
import com.easybuy.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    public ProductCategoryMapper productCategoryMapper;
    @Override
    public Page queryPageProCategory(Integer currentPage) {
        Page page = new Page();
        //查询记录总数
        int totalCounts = productCategoryMapper.queryTotalCounts();
        page.setTotalCount(totalCounts);

        //合理范围
        if(currentPage < 1){
            currentPage = 1;
        }else if(currentPage > page.getPageCount()){
            currentPage = page.getPageCount();
        }
        page.setUrl("admin/productCategory?action=index");
        page.setCurrentPage(currentPage);
        List<ProductCategory> proCategoryList = productCategoryMapper.queryPageProCategory((currentPage-1)*page.getPageSize(),page.getPageSize());
        page.setProCategoryList(proCategoryList);
        for (ProductCategory proCategory : proCategoryList) {
            if (proCategory.getParentId() == 0){
                proCategory.setParentName(null);
            }else {
                String parentName = productCategoryMapper.queryParentName(proCategory.getParentId());
                proCategory.setParentName(parentName);
            }
        }
        
        return page;
    }

    @Override
    public List<ProductCategory> queryNameByType(int type) {
        List<ProductCategory> productCategoryList = productCategoryMapper.queryNameByType(type);
        return productCategoryList;
    }

    //查询商品所有分类集合
    //也就是首页的左侧边栏的商品分类一级各个一级分类下的产品信息
    @Override
    public List<ProductCategoryVo> queryAllCategories() {
        
        //查询一级分类的列表
        List<ProductCategoryVo> proCategory1VoList = new ArrayList<>();
        //查询一级分类
        List<ProductCategory> proCategory1List = productCategoryMapper.queryType(0);
        //查询二级分类
        for (ProductCategory productCategory1 : proCategory1List) {
            //封装一级分类
            ProductCategoryVo productCategoryVo1 = new ProductCategoryVo();
            productCategoryVo1.setProductCategory(productCategory1);
            //根据一级分类查询一级下的商品
            List<Product> productList = productMapper.queryProductsByCate1Id(productCategory1.getId());
            productCategoryVo1.setProductList(productList);
            List<ProductCategoryVo> proCategory1ChildVoList = new ArrayList<>();
            //根据一级分类查询二级分类
            List<ProductCategory> proCategory2List = productCategoryMapper.queryType(productCategory1.getId());
            for (ProductCategory productCategory2 : proCategory2List) {
                //封装二级分类
                ProductCategoryVo productCategoryVo2 = new ProductCategoryVo();
                proCategory1ChildVoList.add(productCategoryVo2);
                productCategoryVo2.setProductCategory(productCategory2);
                List<ProductCategoryVo> proCategory2ChildVoList = new ArrayList<>();
                productCategoryVo2.setProductCategoryVoList(proCategory2ChildVoList);
                //根据二级分类查询三级分类
                List<ProductCategory> proCategory3List = productCategoryMapper.queryType(productCategory2.getId());
                for (ProductCategory productCategory3 : proCategory3List) {
                    //封装三级分类
                    ProductCategoryVo productCategoryVo3 = new ProductCategoryVo();
                    productCategoryVo3.setProductCategory(productCategory3);
                    proCategory2ChildVoList.add(productCategoryVo3);
                }
            }
            productCategoryVo1.setProductCategoryVoList(proCategory1ChildVoList);
            proCategory1VoList.add(productCategoryVo1);
        }
        
        return proCategory1VoList;
    }
}
