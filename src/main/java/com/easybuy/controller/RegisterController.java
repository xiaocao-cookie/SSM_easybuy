package com.easybuy.controller;

import com.easybuy.entity.Secret;
import com.easybuy.entity.User;
import com.easybuy.service.SecretService;
import com.easybuy.service.UserService;
import com.easybuy.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
@RequestMapping("/Register")
public class RegisterController {

    @Resource
    public UserService userService;
    @Resource
    public SecretService secretService;

    //跳转到注册页面
    @RequestMapping("/toRegister")
    public String readyRegister(){
        return "pre/register";
    }

    //用户注册
    @RequestMapping("/register")
    public void register (@RequestParam("loginName") String loginName,
                          @RequestParam("userName") String userName,
                          @RequestParam("password") String password,
                          @RequestParam("sex") Integer sex,
                          @RequestParam("identityCode") String identityCode,
                          @RequestParam("email") String email,
                          @RequestParam("mobile") String mobile,
                          HttpServletResponse response)throws Exception{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        User user1=new User(loginName, userName, SecurityUtils.md5Hex(password), sex, identityCode,  email,  mobile, 0);
        Secret secret = new Secret(loginName,password);
        int j = secretService.addSecret(secret);
        int i = userService.registerUser(user1);
        if (i > 0 && j > 0){
            out.println("{'status':'1','message':'注册成功'}");
        }else{
           out.println("{'status':'0','message':'注册失败'}");
        }
    }
}
