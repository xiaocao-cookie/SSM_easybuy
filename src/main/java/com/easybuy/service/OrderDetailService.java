package com.easybuy.service;

import com.easybuy.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    //根据orderId查询订单信息
    public List<OrderDetail> queryOrderDetailByOrderId(Integer orderId);
}
