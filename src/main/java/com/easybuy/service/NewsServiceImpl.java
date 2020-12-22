package com.easybuy.service;

import com.easybuy.dao.NewsMapper;
import com.easybuy.entity.News;
import com.easybuy.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl  implements NewsService {

    @Autowired
    public NewsMapper newsMapper;

    @Override
    public List<News> queryAllNews() {
        List<News> newsList = newsMapper.queryAllNews();
        return newsList;
    }

    @Override
    public News queryNewsById(Integer id) {
        News news = newsMapper.queryNewsById(id);
        return news;
    }

    @Override
    public Page queryPageNews(Integer currentPage) {
        Page page = new Page();
        //查记录总数
        int totalCounts = newsMapper.queryTotalCounts();
        page.setTotalCount(totalCounts);
        if(currentPage < 1){
            currentPage = 1;
        }else if(currentPage > page.getPageCount()){
            currentPage = page.getPageCount();
        }
        page.setUrl("admin/news?action=queryNewsList");
        page.setCurrentPage(currentPage);
        List<News> newsList = newsMapper.queryPageNews((currentPage-1)*page.getPageSize(),page.getPageSize());
        page.setNewsList(newsList);
        return page;
    }
}
