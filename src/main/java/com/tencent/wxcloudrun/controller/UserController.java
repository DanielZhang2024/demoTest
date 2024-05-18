package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.DscService;
import com.tencent.wxcloudrun.service.TusService;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.service.VeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    final UserService userService;
    final TusService tusService;
    final DscService dscService;
    final VeService veService;


    public UserController(@Autowired UserService userService, TusService tusService, DscService dscService, VeService veService) {
        this.userService = userService;
        this.tusService = tusService;
        this.dscService = dscService;
        this.veService = veService;
    }

    @GetMapping("/user/login")
    public ApiResponse login(@RequestParam String code, @RequestParam Integer shopId){
        ApiResponse loginRet = userService.login(code,shopId);
        if(!loginRet.isOk()){
            return loginRet;
        }
        User user = (User) loginRet.getData();
        //随机发送一个入场效果
        veService.send(user.getOpenid(),1,shopId);
        return loginRet;
    }

    //take up screen
    @PostMapping("/tus/send")
    public ApiResponse sendTusMsg(@RequestParam String openid, @RequestParam String msg, @RequestParam String imgUrl, @RequestParam Integer tusId, @RequestParam Integer shopId){
        return tusService.send(openid,msg,imgUrl,tusId,shopId);
    }

    //bullet screen chat
    @PostMapping("/dsc/send")
    public ApiResponse sendDscMsg(@RequestParam String openid, @RequestParam String msg, @RequestParam Integer dscId, @RequestParam Integer shopId){
        return dscService.send(openid,msg,dscId,shopId);
    }

    //Vehicle entry
    @PostMapping("/ve/send")
    public ApiResponse sendVeMsg(@RequestParam String openid, @RequestParam Integer veId, @RequestParam Integer shopId){
        return veService.send(openid,veId,shopId);
    }


}
