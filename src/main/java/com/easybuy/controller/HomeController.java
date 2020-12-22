package com.easybuy.controller;

import com.easybuy.entity.News;
import com.easybuy.entity.ProductCategoryVo;
import com.easybuy.service.NewsService;
import com.easybuy.service.ProductCategoryService;
import com.easybuy.service.NewsServiceImpl;
import com.easybuy.service.ProductCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

//首次进入的是index.jsp,他有一个路径response.sendRedirect(request.getContextPath()+"/Home?index.html");
@Controller
@RequestMapping("/Home")
public class HomeController {

    @Resource
    ProductCategoryService productCategoryService;
    @Resource
    NewsService newsService;
    //首页显示
    @RequestMapping("/index.html")
    public String index(Model model){
        //这里写首次加载首页时要显示的东西，因为pre/index.jsp这个页面中要获取到productCategoryVoList
        //所以必须执行以下两个方法
        //左侧分类栏
        List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryAllCategories();
        //新闻资讯
        List<News> newsList = newsService.queryAllNews();
        model.addAttribute("news",newsList);
        model.addAttribute("productCategoryVoList",productCategoryVoList);
        return "pre/index";
    }
}
