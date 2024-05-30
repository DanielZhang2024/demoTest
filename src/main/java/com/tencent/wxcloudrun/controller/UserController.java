package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.model.TusRecord;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.VeRecord;
import com.tencent.wxcloudrun.service.DscService;
import com.tencent.wxcloudrun.service.TusService;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.service.VeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @PostMapping("/user/login")
    public ApiResponse login(@RequestBody JSONObject jsonObject){
        String code = jsonObject.getString("code");
        int shopId = jsonObject.getInt("shopId");
        ApiResponse loginRet = userService.login(code,shopId);
        if(!loginRet.isOk()){
            return loginRet;
        }
        User user = (User) loginRet.getData();
        //随机发送一个入场效果
        //每日一次
        List<VeRecord> newVeRecordList = veService.getNewVeRecordList(shopId);
        boolean today = false;
        if(!newVeRecordList.isEmpty()){
            VeRecord veRecord = newVeRecordList.get(newVeRecordList.size() - 1);
            Date createTime = veRecord.getCreateTime();
            today = isToday(createTime);
        }
        if(!today){
            veService.send(user.getOpenid(),1,shopId);
        }


        return loginRet;
    }

    public static boolean isToday(Date date) {
        Calendar today = Calendar.getInstance();
        Calendar specifiedDate = Calendar.getInstance();
        specifiedDate.setTime(date);

        return today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR)
                && today.get(Calendar.MONTH) == specifiedDate.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) == specifiedDate.get(Calendar.DAY_OF_MONTH);
    }

    //take up screen
    @PostMapping("/tus/send")
    public ApiResponse sendTusMsg(@RequestBody JSONObject jsonObject){
        String openid = jsonObject.getString("openid");
        Integer shopId = jsonObject.getInt("shopId");
        String msg = jsonObject.getString("msg");
        String imgUrl = jsonObject.getString("imgUrl");
        Integer tusId = jsonObject.getInt("tusId");
        if(openid == null || msg == null || imgUrl == null){
            return ApiResponse.error("参数错误");
        }
        return tusService.send(openid,msg,imgUrl,tusId,shopId);
    }

    //bullet screen chat
    @PostMapping("/dsc/send")
    public ApiResponse sendDscMsg(@RequestBody JSONObject jsonObject){
        String openid = jsonObject.getString("openid");
        Integer shopId = jsonObject.getInt("shopId");
        String msg = jsonObject.getString("msg");
        Integer dscId = jsonObject.getInt("dscId");
        if(openid == null || msg == null){
            return ApiResponse.error("参数错误");
        }
        return dscService.send(openid,msg,dscId,shopId);
    }

    //Vehicle entry
    @PostMapping("/ve/send")
    public ApiResponse sendVeMsg(@RequestBody JSONObject jsonObject){
        String openid = jsonObject.getString("openid");
        Integer shopId = jsonObject.getInt("shopId");
        Integer veId = jsonObject.getInt("veId");
        if(openid == null){
            return ApiResponse.error("参数错误");
        }
        return veService.send(openid,veId,shopId);
    }

    @PostMapping("/chat/his")
    public ApiResponse chatHis(@RequestBody JSONObject jsonObject){
        Integer lastId = jsonObject.getInt("lastId");
        Integer shopId = jsonObject.getInt("shopId");
        List<TusRecord> tusRecordListForH5 = tusService.getTusRecordListForH5(shopId, lastId);
        System.out.println("请求的参数为lastId"+lastId+tusRecordListForH5.toString());
        return ApiResponse.ok(tusRecordListForH5);
    }
}
