package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.common.utils.ApiResponse;
import com.tencent.wxcloudrun.dao.TusRecordMapper;
import com.tencent.wxcloudrun.model.TusRecord;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.TusService;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class TusServiceImpl implements TusService {


    final TusRecordMapper tusRecordMapper;
    final UserService userService;

    public TusServiceImpl(@Autowired TusRecordMapper tusRecordMapper, UserService userService) {
        this.tusRecordMapper = tusRecordMapper;
        this.userService = userService;
    }

    @Override
    public ApiResponse send(String openid, String msg, String imgUrl, Integer tusId, Integer shopId) {
        User user = userService.getUserByOpenId(openid);
        if (user == null) {
            return ApiResponse.error("参数错误");
        }
        userService.updateUserLevel(user);
        TusRecord tusRecord = new TusRecord();
        tusRecord.setUserId(user.getId());
        tusRecord.setNickname(user.getNickname());
        tusRecord.setHeadBox(user.getHeadBox());
        tusRecord.setHeadImgUrl(user.getHeadImgUrl());
        tusRecord.setLevel(user.getLevel());
        tusRecord.setContent(msg);
        if(imgUrl != null && !imgUrl.isEmpty()) {
            String[] split = imgUrl.split(",");
            StringBuilder sb = new StringBuilder();
            for(String s : split) {
                try {
                    BufferedImage image = ImageIO.read(new URL(s));
                    if (image != null) {
                        String replace = s.replace("https://7072-prod-7gln35vf511d8e79-1326501488.tcb.qcloud.la", "");
                        int width = image.getWidth();
                        int height = image.getHeight();
                        replace += "?width=" + width + "&height=" + height;
                        sb.append(replace).append(",");
                    } else {
                        System.out.println("图片无法加载");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("读取图片时发生错误");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            tusRecord.setImgList(sb.toString());
        }
        tusRecord.setTusId(tusId);
        if(tusId == 1){
            tusRecord.setTusImgUrl("/tus/1.png");
        }else {
            tusRecord.setTusImgUrl("/tus/2.png");
        }
        tusRecord.setCreateTime(new Date());
        tusRecord.setType(1);
        tusRecord.setDuration(60);
        tusRecord.setShopId(shopId);
        tusRecord.setTypeee(1);
        tusRecordMapper.insert(tusRecord);
        return ApiResponse.ok(tusRecord);
    }

    @Override
    public List<TusRecord> getNewTusRecordList(Integer shopId) {
        QueryWrapper<TusRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", shopId);
        queryWrapper.eq("type",1);
        queryWrapper.eq("typeee",1);
        queryWrapper.orderByAsc("create_time");
        return tusRecordMapper.selectList(queryWrapper);
    }

    @Override
    public List<TusRecord> getLastTusRecordList(Integer shopId) {
        Page<TusRecord> page = new Page<>(1, 10);

        QueryWrapper<TusRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", shopId);
        queryWrapper.eq("type",2);
        queryWrapper.eq("typeee",1);
        queryWrapper.orderByDesc("create_time");

        IPage<TusRecord> iPage = tusRecordMapper.selectPage(page, queryWrapper);

        return iPage.getRecords();
    }

    @Override
    public void playComplete(Integer id) {
        TusRecord tusRecord = tusRecordMapper.selectById(id);
        tusRecord.setType(2);
        tusRecordMapper.updateById(tusRecord);
    }

    @Override
    public List<TusRecord> getTusRecordListForH5(Integer shopId, Integer lastId) {
        Page<TusRecord> page = new Page<>(1, 10);
        QueryWrapper<TusRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", shopId);
        if(lastId != null) {
            queryWrapper.lt("id", lastId);
        }
        queryWrapper.orderByDesc("create_time");

        IPage<TusRecord> tusRecordPage = tusRecordMapper.selectPage(page, queryWrapper);
        return tusRecordPage.getRecords();
    }

}
