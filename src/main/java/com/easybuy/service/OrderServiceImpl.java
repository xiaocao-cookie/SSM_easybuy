package com.easybuy.service;

import com.easybuy.dao.OrderMapper;
import com.easybuy.entity.Order;
import com.easybuy.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    public OrderMapper orderMapper;

    @Override
    public Page queryPageOrders(Integer currentPage) {
        Page page = new Page();

        //查询总记录数
        int totalCounts = orderMapper.queryTotalCounts();
        page.setTotalCount(totalCounts);

        if (currentPage < 1){
            currentPage = 1;
        }else if (currentPage > page.getPageCount()){
            currentPage = page.getPageCount();
        }
        page.setUrl("/admin/order/queryAllOrders");
        page.setCurrentPage(currentPage);
        List<Order> orderList = orderMapper.queryPageOrders((currentPage-1)*page.getPageSize(),page.getPageSize());
        page.setOrderList(orderList);
        return page;
    }

    @Override
    public List<Order> queryOrderById(Integer userId) {
        List<Order> orderList = orderMapper.queryOrdersById(userId);
        return orderList;
    }

}
