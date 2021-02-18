package com.easybuy.controller;

import com.easybuy.entity.Product;
import com.easybuy.entity.ProductCategoryVo;
import com.easybuy.service.ProductCategoryService;
import com.easybuy.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Product")
public class HomeProductController {

    @Resource
    public ProductService productService;
    @Resource
    public ProductCategoryService productCategoryService;

    //点击分类标签查询分类下的商品
    @RequestMapping("/queryProductList")
    public String queryProductList(@RequestParam("level") Integer level,
                                   @RequestParam("category") Integer category,
                                    Model model){
        List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryAllCategories();
        model.addAttribute("productCategoryVoList",productCategoryVoList);
        List<Product> productList = new ArrayList<>();
        //第1类商品查询
        if (level == 1){
            productList = productService.queryProductsByCate1Id(category);
            Integer total = productList.size();
            model.addAttribute("productList",productList);
            model.addAttribute("total",total);
            return "pre/product/queryProductList";
        }
        //第2类商品查询
        if (level == 2){
            productList = productService.queryProductsByCate2Id(category);
            Integer total = productList.size();
            model.addAttribute("total",total);
            model.addAttribute("productList",productList);
            return "pre/product/queryProductList";
        }
        //第3类商品查询
        if (level == 3){
            productList = productService.queryProductsByCate3Id(category);
            Integer total = productList.size();
            model.addAttribute("total",total);
            model.addAttribute("productList",productList);
            return "pre/product/queryProductList";
        }
        return "pre/index";
    }

    //点击主页图片时显示商品详情
    @RequestMapping("/queryProductDetail")
    public String queryProductDetail(@RequestParam("id") Integer id,Model model){
        List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryAllCategories();
        model.addAttribute("productCategoryVoList",productCategoryVoList);
        Product product = productService.queryProductById(id);
        model.addAttribute("product",product);
        return "pre/product/productDetail";
    }

    //搜索商品、searchBar
    @RequestMapping("/searchProduct")
    public String searchProduct(@RequestParam(value = "keyWord",defaultValue = "华为") String keyWord,Model model){
        List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryAllCategories();
        List<Product> productList = productService.searchProduct(keyWord);
        Integer total = productList.size();
        model.addAttribute("productCategoryVoList",productCategoryVoList);
        model.addAttribute("productList",productList);
        model.addAttribute("total",total);
        return "pre/product/queryProductList";
    }
}
