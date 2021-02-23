package com.easybuy.controller;

import com.easybuy.entity.*;
import com.easybuy.service.*;
import com.easybuy.util.*;
import org.aspectj.asm.IModelFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/Cart")
public class CartController {

    @Resource
    public CartService cartService;
    @Resource
    public ProductService productService;
    @Resource
    ProductCategoryService productCategoryService;
    @Resource
    public OrderService orderService;
    @Resource
    public UserAddressService userAddressService;
    //加入购物车
    @RequestMapping("/add")
    public void addCart(@RequestParam("entityId") Integer entityId,
                        @RequestParam(value = "quantity",defaultValue = "1") Integer quantity,
                        HttpServletResponse response, HttpSession session,
                        HttpServletRequest request)throws Exception {
        ReturnResult result = new ReturnResult();
        PrintWriter out = response.getWriter();
        //查询出商品
        Product product = productService.queryProductById(entityId);
        if(product.getStock()<quantity){
            out.println("{'status':'2','message':'商品数量不足'}");
        }
        //获取购物车
        ShoppingCart cart = getCartFromSession(request);
        //往购物车放置商品
        result=cart.addItem(product, quantity);
        if(result.getStatus()== Constants.ReturnResult.SUCCESS){
            cart.setSum((EmptyUtils.isEmpty(cart.getSum()) ? 0.0 : cart.getSum()) + (product.getPrice() * quantity * 1.0));
        }
        out.println("{'status':'1','message':'添加成功'}");
    }

    //刷新购物车
    @RequestMapping("/refreshCart")
    public String refreshCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        ShoppingCart cart = getCartFromSession(request);
        cart = cartService.calculate(cart);
        session.setAttribute("cart", cart);//全部的商品
        return "/common/pre/searchBar";
    }

    //去结算
    @RequestMapping("/toSettlement")
    public String toSettlement(Model model) throws Exception {
        List<ProductCategoryVo> productCategoryVoList = productCategoryService.queryAllCategories();
        //封装返回
        model.addAttribute("productCategoryVoList", productCategoryVoList);
        return "/pre/settlement/toSettlement";
    }


    //跳转到购物车页面
    @RequestMapping("/settlement1")
    public String settlement1(HttpServletRequest request,Model model) throws Exception {
        ShoppingCart cart = getCartFromSession(request);
        cart = cartService.calculate(cart);
        request.getSession().setAttribute("cart",cart);
        return "/pre/settlement/settlement1";
    }


    @RequestMapping("/settlement2")
    public String settlement2(HttpServletRequest request,Model model) throws Exception {
        User user = getUserFromSession(request);
        List<UserAddress> userAddressList = userAddressService.queryUserAdressList(user.getId());
        model.addAttribute("userAddressList", userAddressList);
        return "/pre/settlement/settlement2";
    }

    //生成订单
    @RequestMapping("/settlement3")
    public String settlement3(@RequestParam("addressId") String addressId,
                              @RequestParam("newAddress") String newAddress,
                              @RequestParam("newRemark") String newRemark,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Model model) throws Exception {
        ShoppingCart cart = getCartFromSession(request);
        cart = cartService.calculate(cart);
        User user = getUserFromSession(request);
        ReturnResult result=checkCart(request);
        PrintWriter out = response.getWriter();
        if(result.getStatus()==Constants.ReturnResult.FAIL){
            out.println("{'status':'-1'}");
        }
        //新增地址
        UserAddress userAddress=new UserAddress();
        if(addressId.equals("-1")){
            userAddress.setRemark(newRemark);
            userAddress.setAddress(newAddress);
            userAddress.setId(userAddressService.addUserAddress(user.getId(),newAddress,newRemark));
        }else{
            userAddress=userAddressService.getUserAddressById(Integer.parseInt(addressId));
        }

        //生成订单
        Order order = orderService.payShoppingCart(cart,user,userAddress.getAddress());
        clearCart(request, response);
        model.addAttribute("currentOrder", order);
        return "/pre/settlement/settlement3";
    }

    //从session中获取购物车
    private ShoppingCart getCartFromSession(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        return user;
    }

    private ReturnResult checkCart(HttpServletRequest request) throws Exception {
        ReturnResult result = new ReturnResult();
        HttpSession session = request.getSession();
        ShoppingCart cart = getCartFromSession(request);
        cart = cartService.calculate(cart);
        for (ShoppingCartItem item : cart.getItems()) {
            Product product=productService.queryProductById(item.getProduct().getId());
            if(product.getStock()<item.getQuantity()){
                return result.returnFail(product.getName()+"商品数量不足");
            }
        }
        return result.returnSuccess();
    }

    //清空购物车
    public ReturnResult clearCart(HttpServletRequest request,HttpServletResponse response) throws Exception {
        ReturnResult result = new ReturnResult();
        //结账后清空购物车
        request.getSession().removeAttribute("cart");
        result.returnSuccess(null);
        return result;
    }

    //修改购物车数量
    @RequestMapping("/modifyCart")
    public void modifyCart(@RequestParam("entityId") String id,
                           @RequestParam("quantity") String quantityStr,
                           HttpServletRequest request,HttpServletResponse response,
                           Model model)throws Exception{
        ReturnResult result = new ReturnResult();
        HttpSession session = request.getSession();
        ShoppingCart cart = getCartFromSession(request);
        Product product=productService.queryProductById(Integer.valueOf(id));
        PrintWriter out = response.getWriter();
        if(EmptyUtils.isNotEmpty(quantityStr)){
            if(Integer.parseInt(quantityStr)>product.getStock()){
                out.println("{'status':'2','message':'商品数量不足'}");
            }
        }
        cart = cartService.modifyShoppingCart(id, quantityStr, cart);
        model.addAttribute("cart", cart);//全部的商品
        out.println("{'status':'1'}");
    }

}
