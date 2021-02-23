package com.easybuy.controller;

import com.alibaba.fastjson.JSON;
import com.easybuy.entity.Product;
import com.easybuy.entity.ProductCategory;
import com.easybuy.param.ProductCategoryParam;
import com.easybuy.service.ProductCategoryService;
import com.easybuy.service.ProductService;
import com.easybuy.util.Page;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/admin/productCategory")
public class ProdcutCategoryController {

    @Resource
    public ProductCategoryService proCategoryService;
    @Resource
    public ProductService productService;

    //leftBar中的分类管理
    @RequestMapping("/index.html")
    public String index(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage, Model model){
        Page pager = proCategoryService.queryPageProCategory(currentPage);
        List<ProductCategory> proCategoryList = pager.getProCategoryList();
        model.addAttribute("menu",4);
        model.addAttribute("productCategoryList",proCategoryList);
        model.addAttribute("pager",pager);
        return "backend/productCategory/productCategoryList";
    }

    //删除分类
    @RequestMapping("/deleteProductCategory")
    public void deleteProductCategory(@RequestParam("id") Integer id,
                                      @RequestParam("type") Integer type,
                                      HttpServletResponse response) throws Exception
    {
        List<Product> productList = proCategoryService.queryProductByCategoryId(id);
        PrintWriter out = response.getWriter();
        if (productList.size() == 0) {
            int i = proCategoryService.deleteProductCategoryById(id);
            if (i > 0)
            {
                out.println("{'status':'1','message':'删除成功'}");
            }else {
                out.println("{'status':'2','message':'删除失败'}");
            }
        }else{
            out.println("{'status':'2','message':'删除失败，该分类下仍有商品'}");
        }
    }

    //分类管理之准备添加
    @RequestMapping("/toAddProductCategory")
    public String toAddProductCategory(Model model) throws Exception{
        //查询一级分类 全部
        List<ProductCategory> productCategoryList1=null;
        ProductCategoryParam params =new ProductCategoryParam();
        params.setType(1);
        productCategoryList1 = proCategoryService.queryProductCategoryList(params);
        model.addAttribute("productCategoryList1",productCategoryList1);
        model.addAttribute("productCategory",new ProductCategory());
        return "/backend/productCategory/toAddProductCategory";
    }

    //分类管理之添加
    @RequestMapping("/addProductCategory")
    public void addProductCategory(@RequestParam("type") String type,
                                    @RequestParam("productCategoryLevel1") String productCategoryLevel1,
                                    @RequestParam("productCategoryLevel2") String productCategoryLevel2,
                                    @RequestParam("name") String name,
                                    HttpServletResponse response)throws Exception{
        Integer parentId=0;
        if(type.equals("1")){
            parentId =0;
        }else if(type.equals("2")){
            parentId =Integer.parseInt(productCategoryLevel1);
        }else{
            parentId =Integer.parseInt(productCategoryLevel2);
        }
        ProductCategory productCategory =new ProductCategory();
        productCategory.setName(name);
        productCategory.setParentId(parentId);
        productCategory.setType(Integer.parseInt(type));
        productCategory.setIconClass("");
        Integer i = proCategoryService.addProductCategory(productCategory);
        PrintWriter out = response.getWriter();
        if (i>0){
            out.println("{'status':'1','message':'添加成功'}");
        }else{
            out.println("{'status':'2','message':'添加失败'}");
        }
    }

    //查询下级分类
    @RequestMapping("/queryProductCategoryList")
    public void queryProductCategoryList(@RequestParam("parentId") String parentId,
                                         HttpServletResponse response)throws Exception{
        response.setContentType("text/html;charset=utf-8");
        List<ProductCategory> productCategoryList=null;
        ProductCategoryParam params =new ProductCategoryParam();
        params.setParentId(parentId == null ? 0 : Integer.parseInt(parentId));
        productCategoryList=proCategoryService.queryProductCategoryList(params);
        String json = JSON.toJSONString(productCategoryList);
//        JSONArray jsonArray = JSONArray.fromObject(productCategoryList);
//        String jsonStr = json.substring(1,json.length()-1);
        PrintWriter out = response.getWriter();
        out.println("{\"status\":\"1\",\"data\":"+json+"}");
        System.out.println("=================");
        System.out.println(json);
    }
}
