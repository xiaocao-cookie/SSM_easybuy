package com.easybuy.service;

import com.easybuy.dao.SecretMapper;
import com.easybuy.entity.Secret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecretServiceImpl implements SecretService {

    @Autowired
    public SecretMapper secretMapper;
    @Override
    public String findPassword(String identityCode) {
        String password = secretMapper.findPassword(identityCode);
        return password;
    }

    @Override
    public int addSecret(Secret secret) {
        int i = secretMapper.addSecret(secret);
        return i;
    }
}
