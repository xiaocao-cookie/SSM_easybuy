package com.easybuy.dao;

import com.easybuy.entity.Secret;

public interface SecretMapper {
    //找回密码
    public String findPassword(String identityCode);
    //添加用户
    public int addSecret(Secret secret);
}
