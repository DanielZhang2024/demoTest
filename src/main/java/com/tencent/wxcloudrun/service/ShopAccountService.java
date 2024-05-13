package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.ShopAccount;

import java.util.Optional;

public interface ShopAccountService {

  Optional<ShopAccount> getAccount(String phone, String password);

  void insertAccount(ShopAccount shopAccount);

  void clearCount(Integer id);
}
