package com.easybuy.dao;

import com.easybuy.entity.UserAddress;
import com.easybuy.param.UserAddressParam;

import java.util.List;

public interface UserAddressMapper {
    public List<UserAddress> queryUserAddressList(UserAddressParam params);
    //添加用户地址
    public Integer add(UserAddress userAddress);
    //通过addressId查询用户地址
    public UserAddress getUserAddressById(Integer addressId);
}
