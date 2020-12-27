package com.easybuy.service;

import com.easybuy.dao.OrderDetailMapper;
import com.easybuy.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    public OrderDetailMapper orderDetailMapper;

    @Override
    public List<OrderDetail> queryOrderDetailByOrderId(Integer orderId) {
        List<OrderDetail> orderDetailList = orderDetailMapper.queryOrderDetailByOrderId(orderId);
        return orderDetailList;
    }
}
