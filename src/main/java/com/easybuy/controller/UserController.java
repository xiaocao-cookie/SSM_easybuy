package com.easybuy.controller;

import com.easybuy.entity.User;
import com.easybuy.service.UserService;
import com.easybuy.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Resource
    public UserService userService;


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
}
