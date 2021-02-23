package com.easybuy.dao;

import com.easybuy.entity.Order;
import com.easybuy.entity.OrderDetail;

import java.util.List;

public interface OrderDetailMapper {

    //根据orderId查询订单详情
    public List<OrderDetail> queryOrderDetailByOrderId(Integer orderId);
    //查询所有订单
    public List<OrderDetail> queryAllOrderDetail();
    //生成订单
    public int add(OrderDetail detail);
}
