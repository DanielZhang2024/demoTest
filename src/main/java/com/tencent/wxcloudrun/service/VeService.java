package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.model.VeRecord;

import java.util.List;

public interface VeService {

    //今晚吃鸡
    ApiResponse send(String openid, Integer veId, Integer shopId);
    List<VeRecord> getNewVeRecordList(Integer shopId);
    void playComplete(Integer id);
}
