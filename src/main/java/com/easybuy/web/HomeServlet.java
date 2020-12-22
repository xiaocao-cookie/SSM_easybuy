package com.easybuy.web;

import com.easybuy.entity.News;
import com.easybuy.entity.ProductCategoryVo;
import com.easybuy.service.NewsService;
import com.easybuy.service.ProductCategoryService;
import com.easybuy.service.NewsServiceImpl;
import com.easybuy.service.ProductCategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//首次进入的是index.jsp,他有一个路径response.sendRedirect(request.getContextPath()+"/Home?action=index");
//这个类就是处理这个路径的action的，把所有的信息都放在24行的if语句内即可
//@WebServlet(name = "HomeServlet",urlPatterns = "/Home")
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        if ("index".equals(action)){
            //这里写首次加载首页时要显示的东西，因为pre/index.jsp这个页面中要获取到productCategoryVoList
            //所以必须执行以下两个方法
            //左侧分类栏
            ProductCategoryService proCategoryService = new ProductCategoryServiceImpl();
            List<ProductCategoryVo> productCategoryVoList = proCategoryService.queryAllCategories();
            //新闻资讯
            NewsService newsService = new NewsServiceImpl();
            List<News> newsList = newsService.queryAllNews();
            request.setAttribute("news",newsList);
            request.setAttribute("productCategoryVoList",productCategoryVoList);
            request.getRequestDispatcher("pre/index.jsp").forward(request,response);
        }
    }
}
