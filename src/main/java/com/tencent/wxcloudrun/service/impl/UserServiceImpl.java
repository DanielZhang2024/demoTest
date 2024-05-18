package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.common.weixin.WxService;
import com.tencent.wxcloudrun.common.weixin.utils.WxUserInfo;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.model.TusRecord;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder encoder;

    final UserMapper userMapper;
    private final WxService wxService;

    public UserServiceImpl(@Autowired UserMapper userMapper, BCryptPasswordEncoder encoder, WxService wxService) {
        this.userMapper = userMapper;
        this.encoder = encoder;
        this.wxService = wxService;
    }
    @Override
    public void updateUserInfo(User user, WxUserInfo wxUserInfo) {
        user.setCity(wxUserInfo.getCity());
        user.setCountry(wxUserInfo.getCountry());
        user.setProvince(wxUserInfo.getProvince());
        user.setLanguage(wxUserInfo.getLanguage());
        user.setNickname(wxUserInfo.getNickname());
        user.setHeadImgUrl(wxUserInfo.getHeadimgurl());
        user.setSex(wxUserInfo.getSex());
        userMapper.updateById(user);
    }
    @Override
    public void createUser(User user, WxUserInfo wxUserInfo) {
        user.setOpenid(wxUserInfo.getOpenid());
        user.setCity(wxUserInfo.getCity());
        user.setCountry(wxUserInfo.getCountry());
        user.setProvince(wxUserInfo.getProvince());
        user.setLanguage(wxUserInfo.getLanguage());
        user.setNickname(wxUserInfo.getNickname());
        user.setHeadImgUrl(wxUserInfo.getHeadimgurl());
        user.setSex(wxUserInfo.getSex());
        user.setHeadBox("默认头像kuang");
        userMapper.insert(user);
    }

    @Override
    public ApiResponse login(String code, Integer shopId) {
        WxUserInfo userInfo = wxService.getUserInfo(code);
        if(userInfo == null){
            return ApiResponse.error("授权失败");
        }
        User user = getUserByOpenId(userInfo.getOpenid());
        if(user != null){
            user.setCurShopId(shopId);
            updateUserInfo(user,userInfo);
        }else {
            user = new User();
            user.setCurShopId(shopId);
            createUser(user,userInfo);
        }
        return ApiResponse.ok(user);
    }

    @Override
    public User getUserByOpenId(String openId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openId);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User updateUserLevel(User user) {
        user.setLevel(user.getLevel()+1);
        userMapper.updateById(user);
        return user;
    }

    @Override
    public List<User> getUserListRank3(Integer shopId) {
        Page<User> page = new Page<>(1, 3);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("curShopId",shopId);
        queryWrapper.orderByDesc("level");

        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);

        return iPage.getRecords();
    }




}
