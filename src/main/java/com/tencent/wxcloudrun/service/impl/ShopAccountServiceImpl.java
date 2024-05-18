package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.common.weixin.WxService;
import com.tencent.wxcloudrun.dao.ShopAccountMapper;
import com.tencent.wxcloudrun.model.ShopAccount;
import com.tencent.wxcloudrun.service.ShopAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class ShopAccountServiceImpl implements ShopAccountService {

    private BCryptPasswordEncoder encoder;

    final ShopAccountMapper shopAccountMapper;
    private final WxService wxService;

    public ShopAccountServiceImpl(@Autowired ShopAccountMapper shopAccountMapper,
                                  @Autowired BCryptPasswordEncoder encoder, WxService wxService) {
        this.shopAccountMapper = shopAccountMapper;
        this.encoder = encoder;
        this.wxService = wxService;
    }

    protected ShopAccount getAccountByPhone(String phone) {
        QueryWrapper<ShopAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        return shopAccountMapper.selectOne(queryWrapper);
    }

    @Override
    public ApiResponse register(ShopAccount shopAccount) {
        String phone = shopAccount.getPhone();
        if(getAccountByPhone(phone) != null){
            return ApiResponse.error("该手机号已被注册");
        }
        String encodedPassword = encoder.encode(shopAccount.getPassword());
        shopAccount.setPassword(encodedPassword);
        shopAccount.setCreateTime(new Date());
        int insert =  shopAccountMapper.insert(shopAccount);
        if(insert>0){
            return ApiResponse.ok("注册成功");
        }
        return ApiResponse.error("注册失败");
    }

    @Override
    public ApiResponse loginAccount(String phone, String password){
        ShopAccount shopAccount = getAccountByPhone(phone);
        if(shopAccount == null){
            return ApiResponse.error("当前账号不存在");
        }
        if(!encoder.matches(password, shopAccount.getPassword())){
            return ApiResponse.error("密码错误");
        }
        if(shopAccount.getQrCode() == null || shopAccount.getQrCode().isEmpty()){
            //获取二维码存入表内
            String qrCodeUrl = wxService.getQrCodeUrl(shopAccount.getId());
            shopAccount.setQrCode(qrCodeUrl);
            shopAccountMapper.updateById(shopAccount);
        }
        return ApiResponse.ok(shopAccount);
    }



}
