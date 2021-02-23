package com.easybuy.dao;

import com.easybuy.entity.Product;
import com.easybuy.entity.ProductCategory;
import com.easybuy.entity.ProductCategory;
import com.easybuy.param.ProductCategoryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryMapper {
    //查询商品分类，pre/index.jsp的左侧边栏，分页查询
    public List<ProductCategory> queryPageProCategory(
            @Param("from") Integer from,
            @Param("pageSize") Integer pageSize);
    //查询总页数
    public int queryTotalCounts();
    //通过父级id查询父级名称
    public String queryParentName(int parentId);
    //通过type（1,2,3）的值查询相关分类名
    public List<ProductCategory> queryNameByType(int type);
    //查询所有
    public List<ProductCategory> queryAllCategory();
    //通过父级id查分类名
    public List<ProductCategory> queryType(int parentId);
    //删除分类
    public int deleteProductCategoryById(Integer id);
    //根据id查询是几级分类
    public int queryTypeById(Integer id);
    //通过分类id查询商品
    public List<Product> queryProductByCategoryId(Integer id);
    //根据条件查询商品列表
    public List<ProductCategory> queryProductCategorylist(ProductCategoryParam param);
    //添加商品分类
    public Integer add(ProductCategory productCategory) ;
}
