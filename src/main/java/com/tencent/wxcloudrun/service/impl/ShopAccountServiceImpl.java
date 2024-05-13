package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.CountersMapper;
import com.tencent.wxcloudrun.dao.ShopAccountMapper;
import com.tencent.wxcloudrun.model.ShopAccount;
import com.tencent.wxcloudrun.service.ShopAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopAccountServiceImpl implements ShopAccountService {

    final ShopAccountMapper shopAccountMapper;

    public ShopAccountServiceImpl(@Autowired ShopAccountMapper shopAccountMapper) {
        this.shopAccountMapper = shopAccountMapper;
    }

    @Override
    public Optional<ShopAccount> getAccount(String phone, String password) {
        return Optional.ofNullable(shopAccountMapper.getById(phone,password));
    }

    @Override
    public void insertAccount(ShopAccount shopAccount) {

    }

    @Override
    public void clearCount(Integer id) {

    }
}
