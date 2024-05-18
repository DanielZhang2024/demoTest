package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.model.TusRecord;

import java.util.List;

public interface TusService {

    ApiResponse send(String openid, String msg, String imgUrl, Integer tusId, Integer shopId);

    List<TusRecord> getNewTusRecordList(Integer shopId);

    List<TusRecord> getLastTusRecordList(Integer shopId);

    void playComplete(Integer id);
}
