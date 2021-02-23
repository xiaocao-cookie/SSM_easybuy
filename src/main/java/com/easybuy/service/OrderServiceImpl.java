package com.easybuy.service;

import com.easybuy.dao.OrderDetailMapper;
import com.easybuy.dao.OrderMapper;
import com.easybuy.dao.ProductMapper;
import com.easybuy.entity.Order;
import com.easybuy.entity.OrderDetail;
import com.easybuy.entity.User;
import com.easybuy.util.Page;
import com.easybuy.util.ShoppingCart;
import com.easybuy.util.ShoppingCartItem;
import com.easybuy.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    public OrderMapper orderMapper;
    @Autowired
    public ProductMapper productMapper;
    @Autowired
    public OrderDetailMapper orderDetailMapper;

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

    @Override
    public Order payShoppingCart(ShoppingCart cart, User user, String address) {
        Order order = new Order();
        //增加订单
        order.setUserId(user.getId());
        order.setLoginName(user.getLoginName());
        order.setUserAddress(address);
        order.setCreateTime(new Date());
        order.setCost(cart.getTotalCost());
        order.setSerialNumber(StringUtils.randomUUID());
        int i = orderMapper.add(order);
        Integer id = orderMapper.queryIdBySerialNum(order.getSerialNumber());
        order.setId(id);
        //增加订单对应的明细信息
        for (ShoppingCartItem item : cart.getItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId());
            orderDetail.setCost(item.getCost());
            orderDetail.setProductId(item.getProduct().getId());
            orderDetail.setProduct(item.getProduct());
            orderDetail.setQuantity(item.getQuantity());
            orderDetailMapper.add(orderDetail);
            //更新商品表的库存
            productMapper.updateStock(item.getProduct().getId(), item.getQuantity());
        }
        return order;
    }

}
