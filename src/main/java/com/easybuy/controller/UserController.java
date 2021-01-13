package com.easybuy.controller;

import com.easybuy.entity.Secret;
import com.easybuy.entity.User;
import com.easybuy.service.SecretService;
import com.easybuy.service.UserService;
import com.easybuy.util.Page;
import com.easybuy.util.SecurityUtils;
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
@RequestMapping("/admin/user")
public class UserController {

    @Resource
    public UserService userService;
    @Resource
    public SecretService secretService;

    //leftBar中的个人用户信息
    @RequestMapping("/personalUserInfo")
    public String personalUserInfo(Model model){
        model.addAttribute("menu",2);
        return "backend/user/userInfo";
    }

    //leftBar中的用户列表
    @RequestMapping("/queryUserList")
    public String queryUserList(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                                Model model){
        Page pager = userService.queryPageUser(currentPage);
        List<User> userList = pager.getUserList();
        model.addAttribute("menu",8);
        model.addAttribute("userList",userList);
        model.addAttribute("pager",pager);
        return "backend/user/userList";
    }

    //用户列表之删除
    @RequestMapping("/deleteUserById")
    public void deleteUserById(@RequestParam("id") Integer id, HttpServletResponse response) throws Exception{
        PrintWriter out = response.getWriter();
        int i = userService.deleteUserById(id);
        if (i > 0){
            out.println("{'status':'1','message':'删除成功'}");
        }else {
            out.println("{'status':'2','message':'删除失败'}");
        }
    }

    //用户列表之准备添加用户
    @RequestMapping("/toAddUser")
    public String toAddUser(){
        return "backend/user/toUpdateUser";
    }

    //用户列表之准备更新用户
    @RequestMapping("/toUpdateUser")
    public String toUpdateUser(@RequestParam("id") Integer id,Model model){
        User user = userService.queryUserById(id);
        model.addAttribute("user",user);
        return "backend/user/toUpdateUser";
    }

    //用户列表之添加用户
    @RequestMapping("/updateUser")
    public void updateUser(@RequestParam(value = "id",defaultValue = "0") Integer id,
                           @RequestParam("loginName") String loginName,
                           @RequestParam("userName") String userName,
                           @RequestParam("password") String password,
                           @RequestParam("sex") Integer sex,
                           @RequestParam("identityCode") String identityCode,
                           @RequestParam("email") String email,
                           @RequestParam("mobile") String mobile,
                           @RequestParam("type") Integer type,
                           HttpServletResponse response, HttpServletRequest request
                          ) throws Exception{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //封装对象
        User user = new User();
        user.setLoginName(loginName);
        user.setUserName(userName);
        user.setPassword(SecurityUtils.md5Hex(password));
        user.setSex(sex);
        user.setIdentityCode(identityCode);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setType(type);

        Secret secret = new Secret(loginName,password);
        PrintWriter out = response.getWriter();
        if (id == 0){
            //调用增加方法
            int j = secretService.addSecret(secret);
            int i = userService.addUser(user);
            if (i > 0 && j > 0){
                out.println("<script>alert('添加成功');location.href='/spring_ebuy/admin/user/queryUserList';</script>");
            }else{
                out.println("<script>alert('添加失败');location.href='/spring_ebuy/backend/user/toUpdateUser.jsp';</script>");
            }
        }else{
            user.setId(id);
            //调用修改方法
            int i = userService.updateUser(user);
            if (i > 0){
                out.println("<script>alert('修改成功');location.href='/spring_ebuy/admin/user/queryUserList';</script>");
            }else{
                out.println("<script>alert('修改失败');location.href='/spring_ebuy/admin/user/queryUserList';</script>");
            }
        }
    }
}
