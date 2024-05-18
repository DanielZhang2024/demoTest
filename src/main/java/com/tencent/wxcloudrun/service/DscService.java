package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.model.DscRecord;

import java.util.List;

public interface DscService {

    ApiResponse send(String openid, String msg, Integer dscId, Integer shopId);

    List<DscRecord> getNewDscRecordList(Integer shopId);

    List<DscRecord> getLastDscRecordList(Integer shopId);

    void playComplete(Integer id);
}
