package com.easybuy.dao;

import com.easybuy.entity.Order;
import com.easybuy.entity.Order;
import org.apache.ibatis.annotations.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface OrderMapper {
    public List<Order> queryPageOrders(
            @Param("from") Integer from,
            @Param("pageSize") Integer pageSize
    );

    public int queryTotalCounts();

    public List<Order> queryOrdersById(Integer userId);
}
