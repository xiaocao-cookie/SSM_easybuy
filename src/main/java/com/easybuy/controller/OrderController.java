package com.easybuy.controller;

import com.easybuy.entity.Order;
import com.easybuy.entity.OrderDetail;
import com.easybuy.entity.Product;
import com.easybuy.service.OrderDetailService;
import com.easybuy.service.OrderService;
import com.easybuy.service.ProductService;
import com.easybuy.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class OrderController {

    @Resource
    public OrderService orderService;
    @Resource
    public OrderDetailService orderDetailService;
    @Resource
    public ProductService productService;
    //leftBar中的我的订单
    @RequestMapping("/personalOrder")
    public String personalOrders(@RequestParam("userId") Integer userId, Model model){
        List<Order> orderList = orderService.queryOrderById(userId);
        for (Order order : orderList) {
            //将订单详情封装进去
            List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetailByOrderId(order.getId());
            if (orderDetailList != null){
                order.setOrderDetailList(orderDetailList);
            }
            //封装产品信息
            for (OrderDetail orderDetail : orderDetailList) {
                Product product = productService.queryProductById(orderDetail.getProductId());
                orderDetail.setProduct(product);
            }
        }
        model.addAttribute("menu",1);
        model.addAttribute("orderList",orderList);
        return "backend/order/orderList";
    }

    //leftBar中的全部订单
    @RequestMapping("/queryAllOrders")
    public String queryAllOrders(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                                 Model model){
        Page pager = orderService.queryPageOrders(currentPage);
        List<Order> orderList = pager.getOrderList();
        for (Order order : orderList) {
            //将订单详情封装进去
            List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetailByOrderId(order.getId());
            if (orderDetailList != null){
                order.setOrderDetailList(orderDetailList);
            }
            //封装产品信息
            for (OrderDetail orderDetail : orderDetailList) {
                Product product = productService.queryProductById(orderDetail.getProductId());
                orderDetail.setProduct(product);
            }
        }
        model.addAttribute("menu",9);
        model.addAttribute("orderList",orderList);
        model.addAttribute("pager",pager);
        return "backend/order/orderList";
    }

    //订单详情列表
    @RequestMapping("/queryOrderDetail")
    public String queryOrderDetails(@RequestParam("orderId") Integer orderId
                                    ,Model model){
        List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetailByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetailList) {
            Product product = productService.queryProductById(orderDetail.getProductId());
            orderDetail.setProduct(product);
        }
        model.addAttribute("orderDetailList",orderDetailList);
        return "backend/order/orderDetailList";
    }
}
