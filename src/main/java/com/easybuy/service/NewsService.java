package com.easybuy.service;

import com.easybuy.entity.News;
import com.easybuy.util.Page;
import com.easybuy.entity.News;
import com.easybuy.util.Page;

import java.util.List;

public interface NewsService {
    //查询所有新闻，新闻资讯栏显示
    public List<News> queryAllNews();
    //根据id查询新闻的内容和标题
    public News queryNewsById(Integer id);
    //分页查询
    public Page queryPageNews(Integer currentPage);
}
