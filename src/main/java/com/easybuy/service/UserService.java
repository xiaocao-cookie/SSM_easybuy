package com.easybuy.service;

import com.easybuy.entity.User;
import com.easybuy.util.Page;

import java.util.List;

public interface UserService {
    //查询所有
    public List<User> queryAllUser();
    //根据id和password查询用户
    public User findUser(String loginName, String password);
    //注册用户
    public int registerUser(User user);
    //分页查询
    public Page queryPageUser(Integer currentPage);
    //根据id删除用户
    public int deleteUserById(Integer id);
    //添加用户
    public int addUser(User user);
    //更新用户
    public int updateUser(User user);
    //根据id查询用户
    public User queryUserById(Integer id);
}
