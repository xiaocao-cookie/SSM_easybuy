package com.easybuy.service;

import com.easybuy.entity.Cart;
import com.easybuy.util.ShoppingCart;

import java.util.List;

public interface CartService {
    //核算购物车的金额
    public ShoppingCart calculate(ShoppingCart cart);
    //修改购物车
    public ShoppingCart modifyShoppingCart(String productId, String quantityStr, ShoppingCart cart);
}
