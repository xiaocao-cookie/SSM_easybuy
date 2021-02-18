package com.easybuy.service;

import com.easybuy.dao.ProductMapper;
import com.easybuy.entity.Product;
import com.easybuy.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    public ProductMapper productMapper;
    
    @Override
    public List<Product> queryAllProduct() {
        
        List<Product> productList =productMapper.queryAllProduct();
        
        return productList;
    }

    @Override
    public Page queryPageProduct(Integer currentPage) {
        
        Page page = new Page();

        //查记录总数
        int totalCounts =productMapper.queryTotalCounts();
        page.setTotalCount(totalCounts);

        //合理范围
        if(currentPage < 1){
            currentPage = 1;
        }else if(currentPage > page.getPageCount()){
            currentPage = page.getPageCount();
        }
        page.setUrl("/admin/product/index.html");
        page.setCurrentPage(currentPage);
        List<Product> productList = new ArrayList<>();
        productList = productMapper.queryPageProduct((currentPage-1)*page.getPageSize(),page.getPageSize());
        page.setProductList(productList);
        
        return page;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addProduct(Product product) {
        int i = productMapper.addProduct(product);
        return i;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateProductById(Product product) {
        
        int i =productMapper.updateProductById(product);
        return i;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteProductById(int id) {
       int i =productMapper.deleteProductById(id);
       return i;
    }

    @Override
    public Product queryProductById(int id) {
       Product product =productMapper.queryProductById(id);
       return product;
    }

    @Override
    public List<Product> queryProductsByCate1Id(int categoryLevel1Id) {
        List<Product> productList =productMapper.queryProductsByCate1Id(categoryLevel1Id);
        return productList;
    }

    @Override
    public List<Product> queryProductsByCate2Id(int categoryLevel2Id) {
        List<Product> productList =productMapper.queryProductsByCate2Id(categoryLevel2Id);
        return productList;
    }

    @Override
    public List<Product> queryProductsByCate3Id(int categoryLevel3Id) {
        List<Product> productList =productMapper.queryProductsByCate3Id(categoryLevel3Id);
        return productList;
    }

    @Override
    public List<Product> searchProduct(String keyWord) {
        List<Product> productList = productMapper.searchProduct(keyWord);
        return productList;
    }
}
