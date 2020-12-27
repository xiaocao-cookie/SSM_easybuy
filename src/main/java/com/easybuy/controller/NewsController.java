package com.easybuy.controller;

import com.easybuy.entity.News;
import com.easybuy.service.NewsService;
import com.easybuy.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin/news")
public class NewsController {

    @Resource
    public NewsService newsService;

    //管理中心的leftBar资讯列表
    @RequestMapping("/queryNewsList")
    public String queryNewsList(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,Model model){
        Page pager = newsService.queryPageNews(currentPage);
        List<News> newsList = pager.getNewsList();
        model.addAttribute("menu",7);
        model.addAttribute("newsList",newsList);
        model.addAttribute("pager",pager);
        return "backend/news/newsList";
    }

    //点击资讯列表后的新闻详情页面
    @RequestMapping("queryNewsDetail")
    public String queryNewsDetail(@RequestParam("id") int id,Model model){
        News news = newsService.queryNewsById(id);
        model.addAttribute("news",news);
        return "backend/news/newsDetail";
    }
}
