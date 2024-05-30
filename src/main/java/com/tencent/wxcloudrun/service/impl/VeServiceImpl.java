package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.dao.DscRecordMapper;
import com.tencent.wxcloudrun.dao.TusRecordMapper;
import com.tencent.wxcloudrun.dao.VeRecordMapper;
import com.tencent.wxcloudrun.model.DscRecord;
import com.tencent.wxcloudrun.model.TusRecord;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.VeRecord;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.service.VeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VeServiceImpl implements VeService {
    final VeRecordMapper veRecordMapper;
    final UserService userService;
    final TusRecordMapper tusRecordMapper;

    public VeServiceImpl(@Autowired VeRecordMapper veRecordMapper, UserService userService, TusRecordMapper tusRecordMapper) {
        this.veRecordMapper = veRecordMapper;
        this.userService = userService;
        this.tusRecordMapper = tusRecordMapper;
    }

    @Override
    public ApiResponse send(String openid, Integer veId, Integer shopId) {
        User user = userService.getUserByOpenId(openid);
        if (user == null) {
            return ApiResponse.error("参数错误");
        }
        userService.updateUserLevel(user);
        VeRecord veRecord = new VeRecord();
        veRecord.setUserId(user.getId());
        veRecord.setNickname(user.getNickname());
        veRecord.setHeadBox(user.getHeadBox());
        veRecord.setHeadImgUrl(user.getHeadImgUrl());
        veRecord.setLevel(user.getLevel());
        veRecord.setVeId(veId);
        if (veId == 1) {
            veRecord.setVeImgUrl("23");
        } else {
            veRecord.setVeImgUrl("12");
        }
        veRecord.setCreateTime(new Date());
        veRecord.setType(1);
        veRecord.setShopId(shopId);
        veRecordMapper.insert(veRecord);


        TusRecord tusRecord = new TusRecord();
        tusRecord.setUserId(user.getId());
        tusRecord.setNickname(user.getNickname());
        tusRecord.setHeadBox(user.getHeadBox());
        tusRecord.setHeadImgUrl(user.getHeadImgUrl());
        tusRecord.setLevel(user.getLevel());
        tusRecord.setTusId(veId);
        tusRecord.setCreateTime(new Date());
        tusRecord.setType(1);
        tusRecord.setShopId(shopId);
        tusRecord.setTypeee(3);
        tusRecordMapper.insert(tusRecord);
        return ApiResponse.ok(tusRecord);
    }

    @Override
    public List<VeRecord> getNewVeRecordList(Integer shopId) {
        QueryWrapper<VeRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", shopId);
        queryWrapper.eq("type",1);
        queryWrapper.orderByAsc("create_time");
        return veRecordMapper.selectList(queryWrapper);
    }

    @Override
    public void playComplete(Integer id) {
        VeRecord veRecord = veRecordMapper.selectById(id);
        veRecord.setType(2);
        veRecordMapper.updateById(veRecord);
    }


}
