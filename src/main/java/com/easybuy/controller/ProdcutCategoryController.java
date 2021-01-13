package com.easybuy.controller;

import com.easybuy.entity.ProductCategory;
import com.easybuy.service.ProductCategoryService;
import com.easybuy.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
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

    //删除分类
    @RequestMapping("/deleteProductCategory")
    public void deleteProductCategory(@RequestParam("id") Integer id,
                                      @RequestParam("type") Integer type,
                                      HttpServletResponse response)
                                      throws Exception{
        int i = proCategoryService.deleteProductCategoryById(id);
        PrintWriter out = response.getWriter();
        if (i > 0){
            out.println("{'status':'1','message':'删除成功'}");
        }else {
            out.println("{'status':'2','message':'该分类下仍有商品'}");
        }
    }

//    //分类管理之准备添加
//    @RequestMapping("/toAddProductCategory")
//    public void toAddProductCategory(HttpServletResponse response) throws Exception{
//        PrintWriter out = response.getWriter();
//        out.println("");
//    }
}
