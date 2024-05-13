package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.ShopAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ShopAccountMapper {

  ShopAccount getById(@Param("phone") String phone,@Param("password") String password);

//  void upsertCount(ShopAccountMapper shopAccountMapper);
//
//  void clearCount(@Param("id") Integer id);
}
