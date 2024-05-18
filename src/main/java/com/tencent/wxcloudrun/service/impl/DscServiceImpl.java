package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.dao.DscRecordMapper;
import com.tencent.wxcloudrun.dao.TusRecordMapper;
import com.tencent.wxcloudrun.model.DscRecord;
import com.tencent.wxcloudrun.model.TusRecord;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.DscService;
import com.tencent.wxcloudrun.service.TusService;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DscServiceImpl implements DscService {

    final DscRecordMapper dscRecordMapper;
    final UserService userService;

    public DscServiceImpl(@Autowired DscRecordMapper dscRecordMapper, UserService userService) {
        this.dscRecordMapper = dscRecordMapper;
        this.userService = userService;
    }

    @Override
    public ApiResponse send(String openid, String msg, Integer dscId, Integer shopId) {
        User user = userService.getUserByOpenId(openid);
        if (user == null) {
            return ApiResponse.error("参数错误");
        }
        userService.updateUserLevel(user);
        DscRecord dscRecord = new DscRecord();
        dscRecord.setUserId(user.getId());
        dscRecord.setNickname(user.getNickname());
        dscRecord.setHeadBox(user.getHeadBox());
        dscRecord.setHeadImgUrl(user.getHeadImgUrl());
        dscRecord.setLevel(user.getLevel());
        dscRecord.setContent(msg);
        dscRecord.setDscId(dscId);
        if (dscId == 1) {
            dscRecord.setDscImgUrl("23");
        } else {
            dscRecord.setDscImgUrl("12");
        }
        dscRecord.setCreateTime(new Date());
        dscRecord.setType(1);
        dscRecord.setShopId(shopId);
        dscRecordMapper.insert(dscRecord);
        return ApiResponse.ok(dscRecord);
    }

    @Override
    public List<DscRecord> getNewDscRecordList(Integer shopId) {
        QueryWrapper<DscRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", shopId);
        queryWrapper.eq("type",1);
        queryWrapper.orderByAsc("create_time");
        return dscRecordMapper.selectList(queryWrapper);
    }

    @Override
    public List<DscRecord> getLastDscRecordList(Integer shopId) {
        Page<DscRecord> page = new Page<>(1, 10);

        QueryWrapper<DscRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", shopId);
        queryWrapper.eq("type",2);
        queryWrapper.orderByDesc("create_time");

        IPage<DscRecord> iPage = dscRecordMapper.selectPage(page, queryWrapper);

        return iPage.getRecords();
    }

    @Override
    public void playComplete(Integer id) {
        DscRecord dscRecord = dscRecordMapper.selectById(id);
        dscRecord.setType(2);
        dscRecordMapper.updateById(dscRecord);
    }
}
