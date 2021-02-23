package com.easybuy.dao;

import com.easybuy.entity.User;
import com.easybuy.entity.News;
import com.easybuy.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //查询所有用户信息
    public List<User> queryAllUser();
    //根据id和password查询用户
    public User findUser(
            @Param("loginName") String loginName,
            @Param("password") String password);
    //注册用户
    public int registerUser(User user);
    //添加用户
    public int addUser(User user);
    //分页查询
    public List<User> queryPageUser(
            @Param("from") Integer from,
            @Param("pageSize") Integer pageSize);
    //查询用户总数
    public int queryTotalCounts();
    //根据id删除用户
    public int deleteUserById(Integer id);
    //更新用户
    public int updateUserById(User user);
    //根据用户id查询用户
    public User queryUserById(Integer id);
    //根据用户名查询用户
    public User queryUserByLoginName(String loginName);
}
