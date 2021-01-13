package com.easybuy.service;

import com.easybuy.entity.Secret;

public interface SecretService {
    public String findPassword(String identityCode);
    public int addSecret(Secret secret);
}
