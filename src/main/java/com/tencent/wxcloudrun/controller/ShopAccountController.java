package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.ShopAccount;
import com.tencent.wxcloudrun.service.ShopAccountService;
import com.tencent.wxcloudrun.weixin.WxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ShopAccountController {

    final ShopAccountService shopAccountService;
    final Logger logger;
    final WxService wxService;

    public ShopAccountController(@Autowired ShopAccountService shopAccountService, @Autowired WxService wxService) {
        this.shopAccountService = shopAccountService;
        this.logger = LoggerFactory.getLogger(ShopAccountController.class);
        this.wxService = wxService;
    }

    @GetMapping("/admin/login")
    public void loginAccount(@RequestParam String phone, @RequestParam String password){
        logger.info("/admin/login post request");
        Optional<ShopAccount> account = shopAccountService.getAccount(phone, password);
        if(account.isPresent()){
            //获取微信带参二维码
            String qrcode = wxService.getQrcode(account.get().getId());
            System.out.println(qrcode);
        }
//        return ApiResponse.ok(count);
    }

    @GetMapping("/polling/action")
    public ApiResponse pollingAction(){

        return null;
    }

    @PostMapping("/shopAccount")
    public String saveAccount(){
        return "添加用户";
    }

    @GetMapping("/shopAccount")
    public String getAccount(){
        return "添加用户";
    }
}
