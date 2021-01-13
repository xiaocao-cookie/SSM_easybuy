package com.easybuy.service;

import com.easybuy.dao.UserMapper;
import com.easybuy.entity.User;
import com.easybuy.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    public UserMapper userMapper;
    
    @Override
    public List<User> queryAllUser() {
        List<User> userList = userMapper.queryAllUser();
        return userList;
    }

    @Override
    public User findUser(String loginName, String password) {
        User user = userMapper.findUser(loginName, password);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int registerUser(User user) {
       int i = userMapper.registerUser(user);
       return i;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Page queryPageUser(Integer currentPage) {
        Page page = new Page();

        //查询记录总数
        int totalCounts = userMapper.queryTotalCounts();
        page.setTotalCount(totalCounts);
        if(currentPage < 1){
            currentPage = 1;
        }else if(currentPage > page.getPageCount()){
            currentPage = page.getPageCount();
        }
        page.setUrl("/admin/user/queryUserList");
        page.setCurrentPage(currentPage);
        List<User> userList = userMapper.queryPageUser((currentPage-1)*page.getPageSize(),page.getPageSize());
        page.setUserList(userList);
        return page;
    }

    @Override
    public int deleteUserById(Integer id) {
        int i = userMapper.deleteUserById(id);
        return i;
    }

    @Override
    public int addUser(User user) {
        int i = userMapper.addUser(user);
        return i;
    }

    @Override
    public int updateUser(User user) {
        int i = userMapper.updateUserById(user);
        return i;
    }

    @Override
    public User queryUserById(Integer id) {
        User user = userMapper.queryUserById(id);
        return user;
    }

}
