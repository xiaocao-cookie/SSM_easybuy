package com.easybuy.controller;

import com.easybuy.entity.Product;
import com.easybuy.entity.ProductCategory;
import com.easybuy.param.ProductCategoryParam;
import com.easybuy.service.ProductCategoryService;
import com.easybuy.service.ProductService;
import com.easybuy.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Resource
    public ProductService productService;
    @Resource
    public ProductCategoryService proCategoryService;

    //leftBar的商品管理
    @RequestMapping("/index.html")
    public String index(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                        Model model){
        //商品管理之分页显示
        Page pager = productService.queryPageProduct(currentPage);
        List<Product> productList = pager.getProductList();
        model.addAttribute("menu",5);
        model.addAttribute("pager",pager);
        model.addAttribute("productList",productList);
        return "backend/product/productList";
    }

    //leftBar中商品准备上架
    @RequestMapping("/toAddProduct")
    public String toAddProduct(Model model){
        //查询一级分类
        ProductCategoryParam params =new ProductCategoryParam();
        params.setType(1);
        List<ProductCategory> productCategoryList=proCategoryService.queryProductCategoryList(params);
        //一级分类列表
        model.addAttribute("productCategoryList1", productCategoryList);
        model.addAttribute("menu",6);
        model.addAttribute("product",new Product());
       return "backend/product/toAddProduct";
    }

    //商品上架
    @RequestMapping("/addProduct")
    public void addProduct(@RequestParam("name") String name,
                           @RequestParam("photo") String photo,
                           @RequestParam("price") Float price,
                           @RequestParam("stock") Integer stock,
                           @RequestParam("description") String description,
                           @RequestParam("categoryLevel1Id") Integer categoryLevel1Id,
                           @RequestParam("categoryLevel2Id") Integer categoryLevel2Id,
                           @RequestParam("categoryLevel3Id") Integer categoryLevel3Id,
                           @RequestParam(value = "pid",defaultValue = "1") Integer id,
                           HttpServletResponse response, HttpServletRequest request
                           ) throws Exception{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //封装对象
        Product product = new Product();
        product.setName(name);
        product.setFileName(photo);
        product.setPrice(price);
        product.setStock(stock);
        product.setDescription(description);
        product.setCategoryLevel1Id(categoryLevel1Id);
        product.setCategoryLevel2Id(categoryLevel2Id);
        product.setCategoryLevel3Id(categoryLevel3Id);

        PrintWriter out = response.getWriter();
        //如果id的值为空字符串，则添加商品,否则则修改商品
        if (id == 1){
            //调用添加方法
            int i = productService.addProduct(product);
            if (i > 0){
                out.println("<script>alert('添加成功');location.href='/spring_ebuy/admin/product/index.html';</script>");
            }else{
                out.println("<script>alert('添加失败');location.href='/spring_ebuy/backend/product/toAddProduct.jsp';</script>");
            }
        } else{
            product.setId(id);
            //调用修改方法
            int i = productService.updateProductById(product);
            if (i > 0){
                out.println("<script>alert('修改成功');location.href='/spring_ebuy/admin/product/index.html';</script>");
            }else{
                out.println("<script>alert('修改失败');location.href='/spring_ebuy/admin/product/index.html';</script>");
            }
        }
    }

    //商品管理之准备修改
    @RequestMapping("/toUpdateProduct")
    public String toUpdateProduct(Model model,@RequestParam("id") Integer id){
        List<ProductCategory> proCategoryList1 = proCategoryService.queryNameByType(1);
        List<ProductCategory> proCategoryList2 = proCategoryService.queryNameByType(2);
        List<ProductCategory> proCategoryList3 = proCategoryService.queryNameByType(3);
        model.addAttribute("productCategoryList1",proCategoryList1);
        model.addAttribute("productCategoryList2",proCategoryList2);
        model.addAttribute("productCategoryList3",proCategoryList3);
        Product product = productService.queryProductById(id);
        model.addAttribute("product",product);
        return "backend/product/toAddProduct";
    }

    //商品管理之删除
    @RequestMapping("/toDeleteProduct")
    public void toDeleteProduct(@RequestParam("id") Integer id,HttpServletResponse response) throws Exception{
        PrintWriter out = response.getWriter();
        int i = productService.deleteProductById(id);
        if (i > 0){
            out.println("{'status':'1','message':'删除成功'}");
        }else{
            out.println("{'status':'0','message':'删除失败'}");
        }
    }
}
