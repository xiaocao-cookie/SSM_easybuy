package com.easybuy.service;

import com.easybuy.entity.*;
import com.easybuy.param.ProductCategoryParam;
import com.easybuy.util.Page;
import com.easybuy.entity.ProductCategory;
import com.easybuy.entity.ProductCategoryVo;
import com.easybuy.util.Page;

import java.util.List;

public interface ProductCategoryService {
    //分页查询
    public Page queryPageProCategory(Integer currentPage);
    //通过type（1,2,3）的值查询相关分类名
    public List<ProductCategory> queryNameByType(int type);
    //查询商品所有分类集合
    //也就是首页的左侧边栏的商品分类一级各个一级分类下的产品信息
    public List<ProductCategoryVo> queryAllCategories();
    //删除分类
    public int deleteProductCategoryById(Integer id);
    //通过分类id查询商品
    public List<Product> queryProductByCategoryId(Integer id);
    //查询商品分类列表
    public List<ProductCategory> queryProductCategoryList(ProductCategoryParam params);
    //添加商品分类
    public Integer addProductCategory(ProductCategory productCategory);
}
