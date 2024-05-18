package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.ShopAccount;

import java.util.Optional;

public interface ShopAccountService {

    ApiResponse register(ShopAccount shopAccount);

    ApiResponse loginAccount(String phone, String password);
}
