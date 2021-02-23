package com.easybuy.service;

import com.easybuy.dao.ProductCategoryMapper;
import com.easybuy.dao.ProductMapper;
import com.easybuy.entity.ProductCategoryVo;
import com.easybuy.entity.Product;
import com.easybuy.entity.ProductCategory;
import com.easybuy.param.ProductCategoryParam;
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
        page.setUrl("admin/productCategory/index.html");
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

    //删除商品分类
    @Override
    public int deleteProductCategoryById(Integer id) {
        //先判断是几级分类
        int type = productCategoryMapper.queryTypeById(id);
        List<Product> productList1 = new ArrayList<>();
        List<Product> productList2 = new ArrayList<>();
        List<Product> productList3 = new ArrayList<>();
        //在判断此分类下是否有产品
        if (type == 1){
            productList1 = productMapper.queryProductsByCate1Id(id);
        }else if (type == 2){
            productList2 = productMapper.queryProductsByCate2Id(id);
        }else if (type == 3){
            productList3 = productMapper.queryProductsByCate3Id(id);
        }
        int i = 0;
        //三个集合均为空时，才可删除此分类
        if (productList1.size() == 0 && productList2.size() ==0 && productList3.size() ==0 ){
            i = productCategoryMapper.deleteProductCategoryById(id);
        }
        return i;
    }

    @Override
    public List<Product> queryProductByCategoryId(Integer id) {
        List<Product> productList = productCategoryMapper.queryProductByCategoryId(id);
        return productList;
    }

    @Override
    public List<ProductCategory> queryProductCategoryList(ProductCategoryParam params) {
        List<ProductCategory> categoryList = productCategoryMapper.queryProductCategorylist(params);
        return categoryList;
    }

    @Override
    public Integer addProductCategory(ProductCategory productCategory) {
        Integer i = productCategoryMapper.add(productCategory);
        return i;
    }
}
