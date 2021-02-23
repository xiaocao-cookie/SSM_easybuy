package com.easybuy.service;

import com.easybuy.entity.Order;
import com.easybuy.entity.User;
import com.easybuy.util.Page;
import com.easybuy.util.Page;
import com.easybuy.util.ShoppingCart;

import java.util.List;


public interface OrderService {
    //分页查询所有订单
    public Page queryPageOrders(Integer currentPage);
    //查询某个用户的订单
    public List<Order> queryOrderById(Integer userId);
    //生成订单
    public Order payShoppingCart(ShoppingCart cart, User user, String address);
}
