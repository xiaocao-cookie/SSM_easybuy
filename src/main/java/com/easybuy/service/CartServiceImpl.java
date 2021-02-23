package com.easybuy.service;

import com.easybuy.dao.CartMapper;
import com.easybuy.entity.Cart;
import com.easybuy.util.EmptyUtils;
import com.easybuy.util.ShoppingCart;
import com.easybuy.util.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    public CartMapper cartMapper;

    @Override
    public ShoppingCart calculate(ShoppingCart cart) {
        double sum = 0.0;
        for (ShoppingCartItem item : cart.getItems()) {
            sum = sum + item.getQuantity() * item.getProduct().getPrice();
            item.setCost(item.getQuantity() * item.getProduct().getPrice());
        }
        cart.setSum(sum);
        return cart;
    }

    @Override
    public ShoppingCart modifyShoppingCart(String productId, String quantityStr, ShoppingCart cart) {
        Integer quantity = 0;
        if (!EmptyUtils.isEmpty(quantityStr))
            quantity = Integer.parseInt(quantityStr);
        //便利购物车寻找该商品 修改其数量
        for (ShoppingCartItem item : cart.getItems()) {
            if (item.getProduct().getId().toString().equals(productId)) {
                if (quantity == 0 || quantity < 0) {
                    cart.getItems().remove(item);
                    break;
                } else {
                    item.setQuantity(quantity);
                }
            }
        }
        //重新计算金额
        calculate(cart);
        return cart;
    }
}
