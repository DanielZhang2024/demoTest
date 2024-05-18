package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.common.weixin.utils.WxUserInfo;
import com.tencent.wxcloudrun.model.User;

import java.util.List;

public interface UserService {

    void updateUserInfo(User user, WxUserInfo wxUserInfo);
    void createUser(User user, WxUserInfo wxUserInfo);
    ApiResponse login(String code, Integer shopId);
    User getUserByOpenId(String openId);
    User updateUserLevel(User user);
    List<User> getUserListRank3(Integer shopId);
}
