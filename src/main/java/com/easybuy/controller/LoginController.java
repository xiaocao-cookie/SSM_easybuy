package com.easybuy.controller;

import com.easybuy.entity.User;
import com.easybuy.service.UserService;
import com.easybuy.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/Login")
public class LoginController {

    @Resource
    public UserService userService;


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


}
