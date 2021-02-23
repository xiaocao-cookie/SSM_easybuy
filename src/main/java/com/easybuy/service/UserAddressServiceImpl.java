package com.easybuy.service;

import com.easybuy.dao.UserAddressMapper;
import com.easybuy.entity.UserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.easybuy.param.UserAddressParam;

import java.util.Date;
import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService{
    @Autowired
    public UserAddressMapper userAddressMapper;

    //查询用户地址列表
    public List<UserAddress> queryUserAdressList(Integer id){
        List<UserAddress> userAddressList = null;
        UserAddressParam params = new UserAddressParam();
            params.setUserId(id);
            userAddressList = userAddressMapper.queryUserAddressList(params);

        return userAddressList;
    }
    /**
     * 添加用户地址
     *
     * @param id
     * @param address
     * @return
     */
    @Override
    public Integer addUserAddress(Integer id, String address,String remark) {
        Integer userAddressId = null;
        UserAddress userAddress=new UserAddress();
        userAddress.setUserId(id);
        userAddress.setAddress(address);
        userAddress.setRemark(remark);
        userAddress.setIsDefault(0);
        userAddress.setCreateTime(new Date());
        userAddressId = userAddressMapper.add(userAddress);
        return userAddressId;
    }

    @Override
    public UserAddress getUserAddressById(Integer id) {
        UserAddress userAddress= (UserAddress) userAddressMapper.getUserAddressById(id);
        return  userAddress;
    }
}
