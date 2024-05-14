package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.ShopAccount;

import java.util.Optional;

public interface UserService {

    //用户登录，根据openid获取用户
    Optional<ShopAccount> getAccount(String phone, String password);

    void insertAccount(ShopAccount shopAccount);

    void clearCount(Integer id);
}
