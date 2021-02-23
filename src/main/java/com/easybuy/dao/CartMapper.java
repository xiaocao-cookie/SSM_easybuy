package com.easybuy.dao;

import com.easybuy.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    //加入购物车
    public Integer addCart(@Param("entityId") Integer entityId,
                           @Param("quantity") Integer quantity);

    //查询购物车的内容
    public List<Cart> queryCartList();
}
