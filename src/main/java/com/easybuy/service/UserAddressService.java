package com.easybuy.service;

import com.easybuy.entity.UserAddress;

import java.util.List;

public interface UserAddressService {

    public List<UserAddress> queryUserAdressList(Integer id);
    public Integer addUserAddress(Integer id, String address,String remark);
    public UserAddress getUserAddressById(Integer id);
}
