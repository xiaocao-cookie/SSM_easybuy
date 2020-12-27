package com.easybuy.controller;

import com.easybuy.entity.ProductCategory;
import com.easybuy.service.ProductCategoryService;
import com.easybuy.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin/productCategory")
public class ProdcutCategoryController {

    @Resource
    public ProductCategoryService proCategoryService;

    //leftBar中的分类管理
    @RequestMapping("/index.html")
    public String index(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage, Model model){
        Page pager = proCategoryService.queryPageProCategory(currentPage);
        List<ProductCategory> proCategoryList = pager.getProCategoryList();
        model.addAttribute("menu",4);
        model.addAttribute("productCategoryList",proCategoryList);
        model.addAttribute("pager",pager);
        return "backend/productCategory/productCategoryList";
    }
}
