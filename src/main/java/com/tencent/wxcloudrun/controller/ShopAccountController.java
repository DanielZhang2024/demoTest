package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ShopAccountController {

    final ShopAccountService shopAccountService;
    final Logger logger;
    final TusService tusService;
    final DscService dscService;
    final VeService veService;
    final UserService userService;

    public ShopAccountController(@Autowired ShopAccountService shopAccountService, TusService tusService, DscService dscService, VeService veService, UserService userService) {
        this.shopAccountService = shopAccountService;
        this.logger = LoggerFactory.getLogger(ShopAccountController.class);
        this.tusService = tusService;
        this.dscService = dscService;
        this.veService = veService;
        this.userService = userService;
    }

    @PostMapping("/account/register")
    public ApiResponse registerAccount(@RequestBody ShopAccount shopAccount){
        return shopAccountService.register(shopAccount);
    }

    @GetMapping("/account/login")
    public ApiResponse loginAccount(@RequestParam String phone, @RequestParam String password){
        ApiResponse apiResponse = shopAccountService.loginAccount(phone, password);
        if(!apiResponse.isOk()){
            return apiResponse;
        }
        Map<String, Object> data = new HashMap<>();
        ShopAccount shopAccount = (ShopAccount) apiResponse.getData();
        data.put("account", shopAccount);
        //获取最近10条霸屏历史记录
        List<TusRecord> lastTusRecordList = tusService.getLastTusRecordList(shopAccount.getId());
        data.put("lastTusRecordList", lastTusRecordList);
        //获取排行榜前3名用户
        List<User> userListRank3 = userService.getUserListRank3(shopAccount.getId());
        data.put("userListRank3", userListRank3);
        //获取最近10条弹幕
        List<DscRecord> lastDscRecordList = dscService.getLastDscRecordList(shopAccount.getId());
        data.put("lastDscRecordList", lastDscRecordList);

        return ApiResponse.ok(data);
    }

    @GetMapping("/tus/complete")
    public ApiResponse tusPlayComplete(@RequestParam Integer id){
        tusService.playComplete(id);
        return ApiResponse.ok();
    }

    @GetMapping("/dsc/complete")
    public ApiResponse dscPlayComplete(@RequestParam Integer id){
        dscService.playComplete(id);
        return ApiResponse.ok();
    }

    @GetMapping("/ve/complete")
    public ApiResponse vePlayComplete(@RequestParam Integer id){
        veService.playComplete(id);
        return ApiResponse.ok();
    }

    @GetMapping("/account/pollingStatus")
    public ApiResponse pollingStatus(@RequestParam Integer shopId){
        Map<String, Object> data = new HashMap<>();
        List<TusRecord> newTusRecordList = tusService.getNewTusRecordList(shopId);
        data.put("newTusRecordList", newTusRecordList);

        List<DscRecord> newDscRecordList = dscService.getNewDscRecordList(shopId);
        data.put("newDscRecordList", newDscRecordList);

        List<VeRecord> newVeRecordList = veService.getNewVeRecordList(shopId);
        data.put("newVeRecordList", newVeRecordList);

        List<User> userListRank3 = userService.getUserListRank3(shopId);
        data.put("userListRank3", userListRank3);
        return ApiResponse.ok(data);
    }
}
