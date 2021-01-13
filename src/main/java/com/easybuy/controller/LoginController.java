package com.easybuy.controller;

import com.easybuy.entity.User;
import com.easybuy.service.SecretService;
import com.easybuy.service.UserService;
import com.easybuy.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Controller
@RequestMapping("/Login")
public class LoginController {

    @Resource
    public UserService userService;
    @Resource
    public SecretService secretService;

    //跳转到登录页面
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "pre/login";
    }

    //用户登录
    @RequestMapping("/login")
    public String login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
                        HttpSession session){
        User user = userService.findUser(loginName, SecurityUtils.md5Hex(password));
        if (user != null) {
            session.setAttribute("loginUser", user);
            return "../index";
        } else {
            return "pre/login";
        }
    }

    //用户注销
    @RequestMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("loginUser");
        return "../index";
    }

    //跳转到找回密码界面
    @RequestMapping("/toFindPassword")
    public String toFindPassword(){
        return "pre/findPassword";
    }

    //找回密码
    @RequestMapping("/findPassword")
    public void findPassword(@RequestParam("loginName") String loginName,
                             HttpServletResponse response) throws Exception{
        String password = secretService.findPassword(loginName);
        PrintWriter out = response.getWriter();
        if ("".equals(password) || password == null){
            out.println("{'status':'1','message':'对不起，该用户未注册'}");
        }else {
            out.println("{'status':'2','message':"+"\""+password+"\"}");
        }
    }
}
